package com.loop_anime.app.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.loop_anime.app.api.API;
import com.loop_anime.app.api.model.Anime;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import static com.loop_anime.app.db.Table.AnimeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeService extends IntentService{


    private static final String EXTRA_SKIP = "EXTRA_SKIP";
    private static final String EXTRA_LIMIT = "EXTRA_LIMIT";
    private static final String EXTRA_REQUEST_TYPE = "EXTRA_REQUEST_TYPE";

    public AnimeService() {
        super("AnimeService");
    }


    public static Intent requestAnimes(Context context, int skip, int limit) {
        final Intent intent = new Intent(context, AnimeService.class);
        intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.PRODUCTS);
        intent.putExtra(EXTRA_SKIP, skip);
        intent.putExtra(EXTRA_LIMIT, limit);
        context.startService(intent);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        REQUESTS request = (REQUESTS) intent.getSerializableExtra(EXTRA_REQUEST_TYPE);
        switch (request) {
            case PRODUCTS:
                requestAnimes(intent.getIntExtra(EXTRA_SKIP, 0), intent.getIntExtra(EXTRA_LIMIT, 0));
                break;
            default:
                throw new UnsupportedOperationException("Unknown request: " + request);
        }

    }

    private void requestAnimes(int skip, int limit) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API.BASE_URL)
                .build();
        API api = restAdapter.create(API.class);
        List<Anime> animes = api.animes(skip, limit).getPayload().getAnimes();
        Log.v(getClass().getSimpleName(), "Anime Received: " + animes.size());
        ArrayList<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for (Anime anime : animes) {
            ContentValues values = new ContentValues();
            values.put(AnimeEntry.COLUMN_SERVER_ID, anime.getServerId());
            values.put(AnimeEntry.COLUMN_POSTER, anime.getPoster());
            values.put(AnimeEntry.COLUMN_GENRES, anime.getGeneres());
            values.put(AnimeEntry.COLUMN_START_TIME, anime.getStartTime());
            values.put(AnimeEntry.COLUMN_END_TIME, anime.getEndTime());
            values.put(AnimeEntry.COLUMN_TITLE, anime.getTitle());
            values.put(AnimeEntry.COLUMN_PLOT_SUMMERY, anime.getPlotSummary());
            values.put(AnimeEntry.COLUMN_RATING, anime.getRating());
            values.put(AnimeEntry.COLUMN_STATUS, anime.getStatus());
            values.put(AnimeEntry.COLUMN_RUNNING_TIME, anime.getRunningTime());
            values.put(AnimeEntry.COLUMN_RATING_UP, anime.getRatingUp());
            values.put(AnimeEntry.COLUMN_RATING_DOWN, anime.getRatingDown());

            getContentResolver().insert(AnimeEntry.CONTENT_URI, values);
        }
    }

    private enum REQUESTS {PRODUCTS};
}
