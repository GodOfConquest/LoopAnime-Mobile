package com.loop_anime.app;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.loop_anime.app.db.AnimeDbHelper;

/**
 * Created by allan on 14/7/27.
 */
public class TestDatabase extends AndroidTestCase{

    public static final String LOG_TAG = TestDatabase.class.getSimpleName();


    public void testCreateDb() throws Throwable{
        mContext.deleteDatabase(AnimeDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new AnimeDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }
}
