package com.loop_anime.app.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.loop_anime.app.R;
import com.loop_anime.app.ui.fragment.EpisodesFragment;

/**
 * Created by allan on 14/8/17.
 */
public class EpisodesAdapter extends CursorAdapter {

	private static final String LOG_TAG = EpisodesAdapter.class.getSimpleName();

	private Context context;

	public EpisodesAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		this.context = context;
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View view = LayoutInflater.from(context).inflate(R.layout.episodes_list_item_row, null, false);
		ViewHolder viewHolder = new ViewHolder(view);
		viewHolder.serverId = cursor.getInt(EpisodesFragment.COL_SERVER_ID);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		String episodeTitle = "";
		try {
			episodeTitle = cursor.getString(EpisodesFragment.COL_TITLE);
		} catch (Exception e) {
			Log.e(LOG_TAG, "No Episode Title: " + e.toString());
		}
		viewHolder.titleText.setText(episodeTitle);
		String animeTitle = "";
		try {
			animeTitle = cursor.getString(EpisodesFragment.COL_ANIME_TITLE);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Episode doesn't have anime title: " + e.toString());
		}
		viewHolder.subtitleText.setText(animeTitle);
	}

	private static class ViewHolder {

		public int serverId;

		public TextView titleText;

		public TextView subtitleText;

		public ViewHolder(View view) {
			titleText = (TextView) view.findViewById(R.id.text_episode_title);
			subtitleText = (TextView) view.findViewById(R.id.text_episode_subtitle);
		}
	}
}
