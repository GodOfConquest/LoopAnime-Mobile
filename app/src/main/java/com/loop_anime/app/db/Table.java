package com.loop_anime.app.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by allan on 14/7/27.
 */
public class Table {

    public static final String ANIME_CONTENT_AUTHORITY = "com.loop_anime.app.provider.AnimeProvider";

    public static final String EPISODE_CONTENT_AUTHORITY = "com.loop_anime.app.provider.EpisodeProvider";

    public static final String PATH_ANIME = "anime";

    public static final String PATH_EPISODE = "episode";

    public static final class AnimeEntry implements BaseColumns {


        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + ANIME_CONTENT_AUTHORITY);

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ANIME).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + ANIME_CONTENT_AUTHORITY + "/" + PATH_ANIME;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + ANIME_CONTENT_AUTHORITY + "/" + PATH_ANIME;

        public static final String TABLE_NAME = "anime";

        public static final String COLUMN_SERVER_ID = "server_id";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_GENRES = "genres";

        public static final String COLUMN_START_TIME = "start_time";

        public static final String COLUMN_END_TIME = "end_time";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_PLOT_SUMMERY = "plot_summery";

        public static final String COLUMN_RATING = "rating";

        public static final String COLUMN_STATUS = "status";

        public static final String COLUMN_RUNNING_TIME = "running_time";

        public static final String COLUMN_RATING_UP = "rating_up";

        public static final String COLUMN_RATING_DOWN = "rating_down";

        public static final String COLUMN_TOTAL_SEASONS = "total_seasons";

        public static Uri buildAnimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAnimeByServerIdUri(long serverId) {
            Uri build = CONTENT_URI.buildUpon().appendPath(COLUMN_SERVER_ID).appendPath(String.valueOf(serverId)).build();
            return build;
        }

        public static String getServerIdFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }

    public static final class EpisodeEntry implements BaseColumns {

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + EPISODE_CONTENT_AUTHORITY);

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EPISODE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + EPISODE_CONTENT_AUTHORITY + "/" + PATH_EPISODE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + EPISODE_CONTENT_AUTHORITY + "/" + PATH_EPISODE;

        public static final String TABLE_NAME = "episode";

        public static final String COLUMN_ANIME_SERVER_ID = "anime_server_id";

        public static final String COLUMN_ANIME_TITLE = "anime_title";

        public static final String COLUMN_SEASON_SERVER_ID = "season_server_id";

        public static final String COLUMN_SEASON = "season";

        public static final String COLUMN_SERVER_ID = "server_id";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_AIR_DATE = "air_date";

        public static final String COLUMN_TIME_ZONE_TYPE = "time_zone_type";

        public static final String COLUMN_TIME_ZONE = "time_zone";

        public static final String COLUMN_ABSOLUTE_NUMBER = "absolute_number";

        public static final String COLUMN_VIEWS = "views";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_EPISODE_NUMBER = "episode_number";

        public static final String COLUMN_RATING = "rating";

        public static final String COLUMN_SUMMERY = "summery";

        public static final String COLUMN_RATING_UP = "rating_up";

        public static final String COLUMN_RATING_DOWN = "rating_down";

        public static Uri buildEpisodeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildEpisodeByServerIdUri(long serverId) {
            Uri build = CONTENT_URI.buildUpon().appendPath(COLUMN_SERVER_ID).appendPath(String.valueOf(serverId)).build();
            return build;
        }

        public static String getServerIdFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }
}
