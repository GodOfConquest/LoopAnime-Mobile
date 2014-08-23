package com.loop_anime.app.ui.fragment;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loop_anime.app.R;
import com.loop_anime.app.service.EpisodeService;
import com.loop_anime.app.service.ServiceReceiver;
import com.loop_anime.app.ui.adapter.EpisodesAdapter;
import com.loop_anime.app.ui.listener.EndlessScrollListener;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import static com.loop_anime.app.db.Table.EpisodeEntry;

/**
 * Created by allan on 14/8/6.
 */
public class EpisodesFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnRefreshListener {

    private static final int EPISODE_LOADER_RECENT = 0;

    private static final String[] EPISODE_PROJECTION = new String[] {
            EpisodeEntry._ID,
            EpisodeEntry.COLUMN_SERVER_ID,
            EpisodeEntry.COLUMN_POSTER,
            EpisodeEntry.COLUMN_SEASON,
            EpisodeEntry.COLUMN_AIR_DATE,
            EpisodeEntry.COLUMN_TIME_ZONE,
            EpisodeEntry.COLUMN_TITLE,
            EpisodeEntry.COLUMN_SUMMERY,
            EpisodeEntry.COLUMN_VIEWS,
            EpisodeEntry.COLUMN_ABSOLUTE_NUMBER,
            EpisodeEntry.COLUMN_RATING,
            EpisodeEntry.COLUMN_RATING_UP,
            EpisodeEntry.COLUMN_RATING_DOWN,
            EpisodeEntry.COLUMN_ANIME_SERVER_ID,
            EpisodeEntry.COLUMN_ANIME_TITLE,
    };


    public static final int COL_ID = 0;
    public static final int COL_SERVER_ID = 1;
    public static final int COL_POSTER = 2;
    public static final int COL_ID_SEASON = 3;
    public static final int COL_AIR_DATE = 4;
    public static final int COL_TIMEZONE = 5;
    public static final int COL_TITLE = 6;
    public static final int COL_SUMMERY = 7;
    public static final int COL_VIEWS = 8;
    public static final int COL_EPISODE_NUMBER = 9;
    public static final int COL_RATING = 10;
    public static final int COL_RATING_UP = 11;
    public static final int COL_RATING_DOWN = 12;
    public static final int COL_ANIME_ID = 13;
    public static final int COL_ANIME_TITLE = 14;

    private static final int ITEM_PER_PAGE = 10;
    private View mView;
    private ListView mListView;
    private EpisodesAdapter mAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private View mEmptyView;
    private EndlessScrollListener mEndlessListListener;

    @Override
    public boolean enableReceiver() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_episodes, null, false);
        mPullToRefreshLayout = (PullToRefreshLayout) mView.findViewById(R.id.ptr_layout);
        mListView = (ListView) mView.findViewById(R.id.list_episodes);
        mEmptyView = mView.findViewById(android.R.id.empty);
        mListView.setEmptyView(mEmptyView);
        mEndlessListListener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                EpisodeService.requestEpisodes(getActivity(), ITEM_PER_PAGE, page, mReceiver);
                mPullToRefreshLayout.setRefreshing(true);
            }
        };
        mListView.setOnScrollListener(mEndlessListListener);
        getLoaderManager().initLoader(EPISODE_LOADER_RECENT, null, this);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);
        mAdapter = new EpisodesAdapter(getActivity(), null, 0);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                EpisodeLinksFragment episodeLinksFragment = new EpisodeLinksFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(EpisodeLinksFragment.EXTRA_EPISODE_ID, cursor.getInt(COL_SERVER_ID));
                episodeLinksFragment.setArguments(bundle);
                episodeLinksFragment.show(getFragmentManager(), null);
            }
        });
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case ServiceReceiver.STATUS_ERROR:
            case ServiceReceiver.STATUS_FINISHED:
                mPullToRefreshLayout.setRefreshComplete();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case EPISODE_LOADER_RECENT:
                EpisodeService.requestEpisodes(getActivity(), ITEM_PER_PAGE, 1, mReceiver);
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        EpisodeEntry.CONTENT_URI,
                        EPISODE_PROJECTION,
                        null,
                        null,
                        null);
                return cursorLoader;
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onRefreshStarted(View view) {
        EpisodeService.requestEpisodes(getActivity(), ITEM_PER_PAGE, 1, mReceiver);
    }
}
