package com.loop_anime.app.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loop_anime.app.R;
import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.api.DirectLinkResponse;
import com.loop_anime.app.api.model.Link;

import java.util.List;

/**
 * Created by allan on 14/8/23.
 */
public class EpisodeLinksFragment extends DialogFragment {

	private int episodeId;

	public static final String EXTRA_EPISODE_ID = "EXTRA_EPISODE_ID";

	private ListView mListView;

	private GetLinksFromEpisodes getLinksTask;

	private GetDirectLinkTask getDirectLinkTask;

	private TextView mErrorView;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if (arguments != null) {
			episodeId = arguments.getInt(EXTRA_EPISODE_ID, 0);
		}
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_episode_links, null, false);
		mListView = (ListView) view.findViewById(R.id.list_links);
		mErrorView = (TextView) view.findViewById(R.id.error_text);
		getLinksTask = new GetLinksFromEpisodes();
		if (episodeId > 0) {
			getLinksTask.execute(episodeId);
		}
		return new AlertDialog.Builder(getActivity()).setTitle(R.string.episode_links_dialog_title)
				.setView(view)
				.create();
	}

	@Override
	public void onPause() {
		super.onPause();
		getLinksTask.cancel(true);
	}

	private class GetLinksFromEpisodes extends AsyncTask<Integer, Void, LinksAdapter> {

		@Override
		protected void onPostExecute(final LinksAdapter linksAdapter) {
			super.onPostExecute(linksAdapter);
			if (linksAdapter != null) {
				mListView.setAdapter(linksAdapter);
				mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						int serverId = linksAdapter.getItem(position).getId();
						startDirectLinkTask(serverId);
					}
				});
			} else {
				setErrorView();
			}
		}

		@Override
		protected LinksAdapter doInBackground(Integer... params) {
			LinksAdapter linksList = null;
			try {
				if (params.length > 0) {
					List<Link> links = APIFactory.instence().getLinks(params[0]).getPayload().getLinks();
					linksList = new LinksAdapter(getActivity(), R.layout.link_item_row, links);
				}
			} catch (Exception e) {

			} finally {
				return linksList;
			}
		}
	}

	private void startDirectLinkTask(int linkId) {
		getDirectLinkTask = new GetDirectLinkTask();
		getDirectLinkTask.execute(linkId);
	}

	private void setErrorView() {
		mErrorView.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.GONE);
	}

	private class LinksAdapter extends ArrayAdapter<Link> {

		public LinksAdapter(Context context, int resource, List<Link> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.link_item_row, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView.findViewById(R.id.text_link_title);
				viewHolder.subtitle = (TextView) convertView.findViewById(R.id.text_link_subtitle);
				convertView.setTag(viewHolder);
			}
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			Link link = getItem(position);
			viewHolder.linkId = link.getId();
			viewHolder.title.setText(link.getHoster());
			viewHolder.subtitle.setText(link.getSubtitlesLanguage());
			return convertView;
		}

		public class ViewHolder {

			public int linkId;

			public TextView title;

			public TextView subtitle;

		}

	}

	private class GetDirectLinkTask extends AsyncTask<Integer, Void, String> {

		@Override
		protected void onPostExecute(String directLink) {
			super.onPostExecute(directLink);
			if (directLink != null && directLink.length() > 0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(directLink), "video/*");
				getActivity().startActivity(intent);
			} else {
				Toast.makeText(getActivity(), getActivity().getString(R.string.error_load_address), Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected String doInBackground(Integer... params) {
			String directLink = null;
			try {
				DirectLinkResponse.DirectLinkPayload payload = APIFactory.instence().getDirectLink(params[0]).getPayload();
				directLink = payload.getDirectLinks().get(0).getDirectLink();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return directLink;
			}
		}
	}
}
