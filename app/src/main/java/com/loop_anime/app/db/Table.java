package com.loop_anime.app.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by allan on 14/7/27.
 */
public class Table {

    public static final String CONTENT_AUTHORITY = "com.loop_anime.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ANIME = "anime";

    public static final class AnimeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ANIME).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ANIME;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ANIME;

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
}
