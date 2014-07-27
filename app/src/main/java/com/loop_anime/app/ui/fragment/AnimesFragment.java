package com.loop_anime.app.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.loop_anime.app.R;
import com.loop_anime.app.adapter.AnimesAdapter;
import com.loop_anime.app.service.AnimeService;

import static com.loop_anime.app.db.Table.AnimeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class AnimesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ANIME_LOADER = 0;
    private static final String[] ANIME_PROJECTION = new String[] {
            AnimeEntry._ID,
            AnimeEntry.COLUMN_SERVER_ID,
            AnimeEntry.COLUMN_POSTER,
            AnimeEntry.COLUMN_GENRES,
            AnimeEntry.COLUMN_START_TIME,
            AnimeEntry.COLUMN_END_TIME,
            AnimeEntry.COLUMN_TITLE,
            AnimeEntry.COLUMN_PLOT_SUMMERY,
            AnimeEntry.COLUMN_RATING,
            AnimeEntry.COLUMN_STATUS,
            AnimeEntry.COLUMN_RUNNING_TIME,
            AnimeEntry.COLUMN_RATING_UP,
            AnimeEntry.COLUMN_RATING_DOWN,
    };

    public static final int COL_ID = 0;
    public static final int COL_SERVER_ID_ = 1;
    public static final int COL_POSTER = 2;
    public static final int COL_GENRES = 3;
    public static final int COL_START_TIME = 4;
    public static final int COL_END_TIME = 5;
    public static final int COL_TITLE = 6;
    public static final int COL_PLOT_SUMMERY = 7;
    public static final int COL_RATING = 8;
    public static final int COL_STATUS = 9;
    public static final int COL_RUNNING_TIME = 10;
    public static final int COL_RATING_UP = 11;
    public static final int COL_RATING_DOWN = 12;
    
    private String[] mFromColumns = {
            AnimeEntry.COLUMN_TITLE,
            AnimeEntry.COLUMN_START_TIME
    };
    private int[] mToFields ={
            R.id.text_anime_title,
            R.id.text_anime_description
    };


    private AnimesAdapter mAdapter;

    private View mView;
    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_animes, null, false);
        mListView = (ListView) mView.findViewById(R.id.list_animes);
        getLoaderManager().initLoader(ANIME_LOADER, null, this);
        mAdapter = new AnimesAdapter(getActivity(), null, 0);
        mListView.setAdapter(mAdapter);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        AnimeService.requestAnimes(getActivity(), 0, 0);
        super.onStart();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case ANIME_LOADER:
                return new CursorLoader(
                        getActivity(),
                        AnimeEntry.CONTENT_URI,
                        ANIME_PROJECTION,
                        null,
                        null,
                        null
                );
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
}
