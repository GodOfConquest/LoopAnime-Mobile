package com.loop_anime.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.loop_anime.app.db.Table.EpisodeEntry;

/**
 * Created by allan on 14/8/11.
 */
public class EpisodeDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "episode.db";

    public EpisodeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ANIME_TABLE = "CREATE TABLE " + EpisodeEntry.TABLE_NAME + " (" +
                EpisodeEntry._ID + " INTEGER PRIMARY KEY," +
                EpisodeEntry.COLUMN_ANIME_SERVER_ID + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_ANIME_TITLE + " TEXT NOT NULL, " +
                EpisodeEntry.COLUMN_SEASON_SERVER_ID + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_SEASON + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_SERVER_ID + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                EpisodeEntry.COLUMN_AIR_DATE + " TEXT, " +
                EpisodeEntry.COLUMN_TIME_ZONE_TYPE + " INTEGER, " +
                EpisodeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                EpisodeEntry.COLUMN_SUMMERY + " TEXT NOT NULL, " +
                EpisodeEntry.COLUMN_RATING + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_VIEWS + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_RATING_UP + " INTEGER NOT NULL, " +
                EpisodeEntry.COLUMN_RATING_DOWN + " INTEGER NOT NULL, " +
                "UNIQUE (" + EpisodeEntry.COLUMN_SERVER_ID +") ON CONFLICT IGNORE" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_ANIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EpisodeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
