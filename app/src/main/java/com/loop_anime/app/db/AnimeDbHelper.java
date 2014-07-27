package com.loop_anime.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.loop_anime.app.db.Table.AnimeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "anime.db";

    public AnimeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ANIME_TABLE = "CREATE TABLE " + AnimeEntry.TABLE_NAME + " (" +
                AnimeEntry._ID + " INTEGER PRIMARY KEY," +
                AnimeEntry.COLUMN_SERVER_ID + " INTEGER NOT NULL, " +
                AnimeEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_GENRES + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_START_TIME + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_END_TIME + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_PLOT_SUMMERY + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_RATING + " INTEGER NOT NULL, " +
                AnimeEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_RUNNING_TIME + " TEXT NOT NULL, " +
                AnimeEntry.COLUMN_RATING_UP + "INTEGER NOT NULL, " +
                AnimeEntry.COLUMN_RATING_DOWN + "INTEGER NOT NULL, " +
                "UNIQUE (" + AnimeEntry.COLUMN_SERVER_ID + ") ON CONFLICT REPLACE" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_ANIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AnimeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
