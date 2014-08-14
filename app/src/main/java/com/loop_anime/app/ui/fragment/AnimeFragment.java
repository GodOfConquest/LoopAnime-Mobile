package com.loop_anime.app.ui.fragment;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.loop_anime.app.R;
import com.loop_anime.app.db.Table;
import com.loop_anime.app.service.AnimeService;
import com.loop_anime.app.ui.activity.AbstractActivity;
import com.loop_anime.app.ui.listener.NotifyingScrollViewListener;
import com.loop_anime.app.ui.view.NotifyingScrollView;
import com.loop_anime.app.util.ImageUtil;
import com.loop_anime.app.util.UiUtil;

/**
 * Created by allan on 14/7/28.
 */
public class AnimeFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>,NotifyingScrollViewListener {

    private static final String LOG_TAG = AnimeFragment.class.getSimpleName();

    private static final int LOAD_ANIME_SERVER_ID = 1;

    private static final String EXTRA_ANIME_SERVER_ID = "EXTRA_ANIME_SERVER_ID";
    private static final String[] ANIME_PROJECTION = {
            Table.AnimeEntry.COLUMN_POSTER,
            Table.AnimeEntry.COLUMN_TITLE,
            Table.AnimeEntry.COLUMN_PLOT_SUMMERY,
            Table.AnimeEntry.COLUMN_START_TIME,
            Table.AnimeEntry.COLUMN_END_TIME,
            Table.AnimeEntry._ID
    };

    private int animeServerId = -1;
    private TextView mTitleView;
    private TextView mDescriptionView;
    private TextView mStartDateView;
    private TextView mEndDateView;
    private NetworkImageView mPosterImageView;
    private NotifyingScrollView mScrollView;
    private View mHeaderBox;
    private View mHeaderActionbarBackground;
    private int actionbarHeight;
    private boolean isHeaderBackgroundShown;

    @Override
    public boolean enableReceiver() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            animeServerId = arguments.getInt(EXTRA_ANIME_SERVER_ID, -1);
        }
        if (animeServerId == -1) {
            throw new IllegalArgumentException("Anime Fragment don't receive Anime Server Id");
        }
        actionbarHeight = UiUtil.getActionBarHeight(getActivity());
        getLoaderManager().initLoader(LOAD_ANIME_SERVER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_anime, container, false);
        mPosterImageView = (NetworkImageView) rootView.findViewById(R.id.image_anime_poster);
        mTitleView = (TextView) rootView.findViewById(R.id.text_anime_title);
        mDescriptionView = (TextView) rootView.findViewById(R.id.text_anime_description);
        mStartDateView = (TextView) rootView.findViewById(R.id.text_anime_start_date);
        mEndDateView = (TextView) rootView.findViewById(R.id.text_anime_end_date);
        mScrollView = (NotifyingScrollView) rootView.findViewById(R.id.scrollview_anime);
        mScrollView.setNotifyingScrollViewListener(this);
        mHeaderBox = rootView.findViewById(R.id.header_box);
        mHeaderActionbarBackground = rootView.findViewById(R.id.header_actionbar_background);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup.LayoutParams layoutParams = mHeaderActionbarBackground.getLayoutParams();
        layoutParams.height = actionbarHeight;
        mHeaderActionbarBackground.setLayoutParams(layoutParams);
        mHeaderActionbarBackground.setScaleY(0f);
        onScrolled(0, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case LOAD_ANIME_SERVER_ID:
                AnimeService.requestAnimeWithServerId(getActivity(), mReceiver, animeServerId);
                return new CursorLoader(getActivity(), Table.AnimeEntry.buildAnimeByServerIdUri(animeServerId),
                        ANIME_PROJECTION,
                        null,
                        null,
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()) {
            case LOAD_ANIME_SERVER_ID:
                if (cursor.moveToFirst()) {
                    mPosterImageView.setImageUrl(ImageUtil.getFullImageUrl(cursor.getString(0)),
                            ((AbstractActivity)getActivity()).getImageLoaderMemoryCache());
                    mTitleView.setText(cursor.getString(1));
                    mDescriptionView.setText(cursor.getString(2));
                    mStartDateView.setText(cursor.getString(3));
                    mEndDateView.setText(cursor.getString(4));
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onScrolled(int l, int t) {
        int scrollY = mScrollView.getScrollY();

        mPosterImageView.setTranslationY(scrollY * 0.5f);

        float newTop = Math.max(getResources().getDimensionPixelSize(R.dimen.anime_poster_image_height)  - actionbarHeight, scrollY);
        mHeaderBox.setTranslationY(newTop);

        boolean toShowHeaderBackground = scrollY >= newTop;
        float computedScaleY = toShowHeaderBackground ? 1f : 0f;
        mHeaderActionbarBackground.setPivotY(actionbarHeight);
        if (isHeaderBackgroundShown != toShowHeaderBackground) {
            mHeaderActionbarBackground.animate()
                    .scaleY(computedScaleY)
                    .setDuration(250)
                    .setInterpolator(new DecelerateInterpolator(2f))
                    .start();
        }
        isHeaderBackgroundShown = toShowHeaderBackground;

    }
}
