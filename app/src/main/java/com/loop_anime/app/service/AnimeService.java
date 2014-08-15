package com.loop_anime.app.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.loop_anime.app.api.API;
import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.api.model.Anime;

import java.util.List;

import static com.loop_anime.app.db.Table.AnimeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeService extends AbstractIntentService {


    private API api;
    private static final String EXTRA_SKIP = "EXTRA_SKIP";
    private static final String EXTRA_PAGE = "EXTRA_PAGE";
    private static final String EXTRA_LIMIT = "EXTRA_LIMIT";
    private static final String EXTRA_REQUEST_TYPE = "EXTRA_REQUEST_TYPE";
    private static final String EXTRA_SERVER_ID = "EXTRA_SERVER_ID";

    public AnimeService() {
        super("AnimeService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        api = APIFactory.instence();
    }

    public static Intent requestAnimes(Context context, int page, int limit, ServiceReceiver receiver) {
        final Intent intent = new Intent(context, AnimeService.class);
        if (receiver != null) {
            intent.putExtra(EXTRA_RECEIVER, receiver);
        }
        intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.ANIME);
        intent.putExtra(EXTRA_PAGE, page);
        intent.putExtra(EXTRA_LIMIT, limit);
        context.startService(intent);
        return intent;
    }

    public static Intent requestAnimeWithServerId(Context context, ServiceReceiver receiver, int serverId) {
        final Intent intent = new Intent(context, AnimeService.class);
        intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.ANIME_BY_SERVER_ID);
        intent.putExtra(EXTRA_SERVER_ID, serverId);
        context.startService(intent);
        return intent;
    }

    @Override
    protected void onHandleAPIIntent(Intent intent) {
        REQUESTS request = (REQUESTS) intent.getSerializableExtra(EXTRA_REQUEST_TYPE);
        switch (request) {
            case ANIME:
                requestAnimes(intent.getIntExtra(EXTRA_PAGE, 1), intent.getIntExtra(EXTRA_LIMIT, 0));
                break;
            case ANIME_BY_SERVER_ID:
                requestAnimeWithServerId(intent.getIntExtra(EXTRA_SERVER_ID, -1));
            default:
                throw new UnsupportedOperationException("Unknown request: " + request);
        }
    }

    private void requestAnimeWithServerId(int serverId) {
        //TODO: api request
        List<Anime> animes = api.animesByServerId(serverId).getPayload().getAnimes();
        Anime anime = animes.get(0);
        ContentValues values = getAnimeContentValues(anime);
        getContentResolver().insert(AnimeEntry.CONTENT_URI, values);
    }

    private void requestAnimes(int page, int limit) {
        List<Anime> animes = api.animes(page, limit).getPayload().getAnimes();
        if (page <= 1) {
            getContentResolver().delete(AnimeEntry.CONTENT_URI, null, null);
        }
        ContentValues[] contentValueses = new ContentValues[animes.size()];
        for (int i = 0; i < animes.size(); i++ ) {
            ContentValues values = getAnimeContentValues(animes.get(i));
            contentValueses[i] = values;
        }
        getContentResolver().bulkInsert(AnimeEntry.CONTENT_URI, contentValueses);
    }

    private ContentValues getAnimeContentValues(Anime anime) {
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
        values.put(AnimeEntry.COLUMN_TOTAL_SEASONS, anime.getTotalSeasons());
        return values;
    }

    private enum REQUESTS {ANIME_BY_SERVER_ID, ANIME};
}
