package com.loop_anime.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.loop_anime.app.db.Table.AnimeEntry;
import static com.loop_anime.app.db.Table.EpisodeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class DbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "loopanime.db";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_ANIME_TABLE = "CREATE TABLE " + AnimeEntry.TABLE_NAME + " (" +
				AnimeEntry._ID + " INTEGER PRIMARY KEY," +
				AnimeEntry.COLUMN_SERVER_ID + " INTEGER NOT NULL, " +
				AnimeEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_GENRES + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_START_TIME + " TEXT , " +
				AnimeEntry.COLUMN_END_TIME + " TEXT , " +
				AnimeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_PLOT_SUMMERY + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_RATING + " INTEGER NOT NULL, " +
				AnimeEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_RUNNING_TIME + " TEXT NOT NULL, " +
				AnimeEntry.COLUMN_RATING_UP + " INTEGER NOT NULL, " +
				AnimeEntry.COLUMN_RATING_DOWN + " INTEGER NOT NULL, " +
				AnimeEntry.COLUMN_TOTAL_SEASONS + " INTEGER NOT NULL, " +
				"UNIQUE (" + AnimeEntry.COLUMN_SERVER_ID + ") ON CONFLICT IGNORE" +
				" );";

		final String SQL_CREATE_EPISODE_TABLE = "CREATE TABLE " + EpisodeEntry.TABLE_NAME + " (" +
				EpisodeEntry._ID + " INTEGER PRIMARY KEY," +
				EpisodeEntry.COLUMN_ANIME_SERVER_ID + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_ANIME_TITLE + " TEXT NOT NULL, " +
				EpisodeEntry.COLUMN_SEASON_SERVER_ID + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_SEASON + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_SERVER_ID + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
				EpisodeEntry.COLUMN_AIR_DATE + " TEXT NOT NULL, " +
				EpisodeEntry.COLUMN_TIME_ZONE_TYPE + " INTEGER, " +
				EpisodeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
				EpisodeEntry.COLUMN_EPISODE_NUMBER + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_SUMMERY + " TEXT, " +
				EpisodeEntry.COLUMN_RATING + " INTEGER, " +
				EpisodeEntry.COLUMN_TIME_ZONE + " INTEGER, " +
				EpisodeEntry.COLUMN_ABSOLUTE_NUMBER + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_VIEWS + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_RATING_UP + " INTEGER NOT NULL, " +
				EpisodeEntry.COLUMN_RATING_DOWN + " INTEGER NOT NULL, " +
				"UNIQUE (" + EpisodeEntry.COLUMN_SERVER_ID + ") ON CONFLICT IGNORE" +
				" );";

		sqLiteDatabase.execSQL(SQL_CREATE_ANIME_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_EPISODE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AnimeEntry.TABLE_NAME);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EpisodeEntry.TABLE_NAME);
		onCreate(sqLiteDatabase);
	}
}
