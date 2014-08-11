package com.loop_anime.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.test.AndroidTestCase;

import com.loop_anime.app.db.AnimeDbHelper;
import com.loop_anime.app.db.EpisodeDbHelper;
import com.loop_anime.app.db.Table;

import java.util.Map;
import java.util.Set;

import static com.loop_anime.app.db.Table.AnimeEntry;

/**
 * Created by allan on 14/7/27.
 */
public class TestDatabase extends AndroidTestCase{

    public static final String LOG_TAG = TestDatabase.class.getSimpleName();


    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(AnimeDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new AnimeDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
        db = new EpisodeDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsert() throws Throwable {
        SQLiteDatabase db = new AnimeDbHelper(mContext).getWritableDatabase();
        ContentValues values = createAnimeValues();
        long insertRowId;
        insertRowId = db.insertOrThrow(AnimeEntry.TABLE_NAME, null, values);

        assertTrue(insertRowId != -1);

        Cursor cursor = db.query(AnimeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        validateCursor(cursor, values);
        cursor.close();
    }

    private ContentValues createAnimeValues() {
        ContentValues values = new ContentValues();
        values.put(AnimeEntry.COLUMN_SERVER_ID, 29);
        values.put(AnimeEntry.COLUMN_POSTER, "/img/episodes/thetvdb/posters/80814-3.jpg");
        values.put(AnimeEntry.COLUMN_GENRES, "Animation,Science-Fiction");
        values.put(AnimeEntry.COLUMN_START_TIME, "2006-10-06");
        values.put(AnimeEntry.COLUMN_END_TIME, "");
        values.put(AnimeEntry.COLUMN_TITLE, "009-1");
        values.put(AnimeEntry.COLUMN_PLOT_SUMMERY, "In a world where the Cold War never ended, East and West continue to battle for technological and political supremacy.\\n\\nMylene Hoffman, field commander of the elite Double Zero intelligence division, exists in this world with her eyes open and her body always ready to do battle. She puts the intelligence into â€œintelligence agentâ€\u009D and her body into â€œbody of evidence!â€\u009D Liberating benevolent scientists, tagging along with would-be monster-slayers, meeting her match in the worldâ€™s most hard-boiled assassin and navigating a deadly labyrinth of horrors are all in a dayâ€™s work for Mylene. Thereâ€™s no problem she canâ€™t solve with the proper application of high explosives, fast-talk, deceptive jewelry, make-up and the right moves behind closed doors! In a world of spy mystery and intrigue, discover who she kisses one minute and kills the next.");
        values.put(AnimeEntry.COLUMN_RATING, 8);
        values.put(AnimeEntry.COLUMN_STATUS, "Ended");
        values.put(AnimeEntry.COLUMN_RUNNING_TIME, "25");
        values.put(AnimeEntry.COLUMN_RATING_UP, 2);
        values.put(AnimeEntry.COLUMN_RATING_DOWN, 0);
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
