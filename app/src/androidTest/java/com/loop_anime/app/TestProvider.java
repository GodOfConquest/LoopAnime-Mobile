package com.loop_anime.app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.loop_anime.app.db.DbHelper;

import java.util.Map;
import java.util.Set;

import static com.loop_anime.app.db.Table.AnimeEntry;
import static com.loop_anime.app.db.Table.EpisodeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class TestProvider extends AndroidTestCase {

	public static final String LOG_TAG = TestProvider.class.getSimpleName();


	public void testCreateDb() throws Throwable {
		mContext.deleteDatabase(DbHelper.DATABASE_NAME);
		SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();
		assertEquals(true, db.isOpen());
		db.close();
	}

	public void testInsert() throws Throwable {
		SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();
		ContentValues values = createAnimeValues();
		long insertRowId;
		Uri uri = mContext.getContentResolver().insert(AnimeEntry.CONTENT_URI, values);
		insertRowId = ContentUris.parseId(uri);

		assertTrue(insertRowId != -1);

		Cursor cursor = mContext.getContentResolver().query(AnimeEntry.buildAnimeByServerIdUri(29),
				null,
				null,
				null,
				null);

		validateCursor(cursor, values);
		cursor.close();

		cursor = mContext.getContentResolver().query(AnimeEntry.buildAnimeUri(insertRowId),
				null,
				null,
				null,
				null);

		validateCursor(cursor, values);
		cursor.close();

		int deletedCount = mContext.getContentResolver().delete(AnimeEntry.CONTENT_URI, null, null);
		assertTrue(deletedCount == 1);
	}

	public void testEpisodeInsert() throws Throwable {
		SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();
		ContentValues values = createEpisodeValues();
		long insertRowId;
		Uri uri = mContext.getContentResolver().insert(EpisodeEntry.CONTENT_URI, values);
		insertRowId = ContentUris.parseId(uri);

		assertTrue(insertRowId != -1);

		Cursor cursor = mContext.getContentResolver().query(EpisodeEntry.buildEpisodeByServerIdUri(29),
				null,
				null,
				null,
				null);

		validateCursor(cursor, values);
		cursor.close();

		cursor = mContext.getContentResolver().query(EpisodeEntry.buildEpisodeUri(insertRowId),
				null,
				null,
				null,
				null);

		validateCursor(cursor, values);
		cursor.close();

		int deletedCount = mContext.getContentResolver().delete(EpisodeEntry.CONTENT_URI, null, null);
		assertTrue(deletedCount == 1);
	}

	private ContentValues createEpisodeValues() {
		ContentValues values = new ContentValues();
		values.put(EpisodeEntry.COLUMN_ANIME_SERVER_ID, 48);
		values.put(EpisodeEntry.COLUMN_ANIME_TITLE, "Captain Earth");
		values.put(EpisodeEntry.COLUMN_SEASON_SERVER_ID, 622);
		values.put(EpisodeEntry.COLUMN_SEASON, 1);
		values.put(EpisodeEntry.COLUMN_SERVER_ID, 29);
		values.put(EpisodeEntry.COLUMN_POSTER, "http://slurm.trakt.us/images/fanart/32053-940.4.jpg");
		values.put(EpisodeEntry.COLUMN_AIR_DATE, "2014-08-10 00:00:00");
		values.put(EpisodeEntry.COLUMN_TIME_ZONE_TYPE, 3);
		values.put(EpisodeEntry.COLUMN_TIME_ZONE, "Europe/London");
		values.put(EpisodeEntry.COLUMN_ABSOLUTE_NUMBER, 19);
		values.put(EpisodeEntry.COLUMN_VIEWS, 1);
		values.put(EpisodeEntry.COLUMN_TITLE, "Episode 19");
		values.put(EpisodeEntry.COLUMN_EPISODE_NUMBER, 19);
		values.put(EpisodeEntry.COLUMN_RATING, 0);
		values.put(EpisodeEntry.COLUMN_SUMMERY, "");
		values.put(EpisodeEntry.COLUMN_RATING_UP, 0);
		values.put(EpisodeEntry.COLUMN_RATING_DOWN, 0);
		return values;
	}

	private ContentValues createAnimeValues() {
		ContentValues values = new ContentValues();
		values.put(AnimeEntry.COLUMN_SERVER_ID, 29);
		values.put(AnimeEntry.COLUMN_POSTER, "/img/episodes/thetvdb/posters/80814-3.jpg");
		values.put(AnimeEntry.COLUMN_GENRES, "Animation,Science-Fiction");
		values.put(AnimeEntry.COLUMN_START_TIME, "2006-10-06");
		values.put(AnimeEntry.COLUMN_END_TIME, "");
		values.put(AnimeEntry.COLUMN_TITLE, "009-1");
		values.put(AnimeEntry.COLUMN_PLOT_SUMMERY, "In a world where the Cold War never ended, " +
				"East and West continue to battle for technological and political supremacy.\\n\\n" +
				"Mylene Hoffman, field commander of the elite Double Zero intelligence division, " +
				"exists in this world with her eyes open and her body always ready to do battle. " +
				"She puts the intelligence into â€œintelligence agentâ€\u009D and her body into " +
				"â€œbody of evidence!â€\u009D Liberating benevolent scientists, tagging along with " +
				"would-be monster-slayers, meeting her match in the worldâ€™s most hard-boiled " +
				"assassin and navigating a deadly labyrinth of horrors are all in a dayâ€™s work " +
				"for Mylene. Thereâ€™s no problem she canâ€™t solve with the proper application of " +
				"high explosives, fast-talk, deceptive jewelry, make-up and the right moves behind " +
				"closed doors! In a world of spy mystery and intrigue, discover who she kisses one " +
				"minute and kills the next.");
		values.put(AnimeEntry.COLUMN_RATING, 8);
		values.put(AnimeEntry.COLUMN_STATUS, "Ended");
		values.put(AnimeEntry.COLUMN_RUNNING_TIME, "25");
		values.put(AnimeEntry.COLUMN_RATING_UP, 2);
		values.put(AnimeEntry.COLUMN_RATING_DOWN, 0);
		values.put(AnimeEntry.COLUMN_TOTAL_SEASONS, 2);
		return values;
	}

	static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

		assertTrue(valueCursor.moveToFirst());

		Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
		for (Map.Entry<String, Object> entry : valueSet) {
			String columnName = entry.getKey();
			int idx = valueCursor.getColumnIndex(columnName);
			assertFalse(idx == -1);
			String expectedValue = entry.getValue().toString();
			assertEquals(expectedValue, valueCursor.getString(idx));
		}
		valueCursor.close();
	}
}
