package com.loop_anime.app.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.loop_anime.app.db.DbHelper;
import com.loop_anime.app.db.Table;

/**
 * Created by allan on 14/8/15.
 */
public class EpisodeProvider extends ContentProvider {

	private static final UriMatcher sUriMatcher = buildUriMatcher();


	private static final int EPISODE = 100;

	private static final int EPISODE_WITH_ID = 101;

	private static final int EPISODE_WITH_SERVER_ID = 102;

	private DbHelper mEpisodeDBHelper;


	private static final SQLiteQueryBuilder sEpisodeByServerIdQueryBuilder;

	static {
		sEpisodeByServerIdQueryBuilder = new SQLiteQueryBuilder();
		sEpisodeByServerIdQueryBuilder.setTables(Table.EpisodeEntry.TABLE_NAME);
	}

	private static final String sEpisodeSelection = Table.EpisodeEntry._ID + " = ?";

	private static final String sEpisodeByServerIdSelection = Table.EpisodeEntry.COLUMN_SERVER_ID + " = ?";


	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = Table.EPISODE_CONTENT_AUTHORITY;
		matcher.addURI(authority, Table.PATH_EPISODE, EPISODE);
		matcher.addURI(authority, Table.PATH_EPISODE + "/#", EPISODE_WITH_ID);
		matcher.addURI(authority, Table.PATH_EPISODE + "/" + Table.EpisodeEntry.COLUMN_SERVER_ID + "/#", EPISODE_WITH_SERVER_ID);
		return matcher;
	}

	@Override
	public boolean onCreate() {
		mEpisodeDBHelper = new DbHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
		Cursor cursor;
		switch (sUriMatcher.match(uri)) {
			case EPISODE:
				cursor = sEpisodeByServerIdQueryBuilder.query(mEpisodeDBHelper.getReadableDatabase(),
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sort);
				break;
			case EPISODE_WITH_ID:
				cursor = getEpisodeById(uri, projection, sort);
				break;
			case EPISODE_WITH_SERVER_ID:
				cursor = getEpisodeByServerId(uri, projection, sort);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	private Cursor getEpisodeByServerId(Uri uri, String[] projection, String sort) {
		String serverId = Table.EpisodeEntry.getServerIdFromUri(uri);
		String[] selectionArgs = new String[]{serverId};
		return sEpisodeByServerIdQueryBuilder.query(
				mEpisodeDBHelper.getReadableDatabase(),
				projection,
				sEpisodeByServerIdSelection,
				selectionArgs,
				null,
				null,
				sort
		);
	}

	private Cursor getEpisodeById(Uri uri, String[] projection, String sort) {
		String _id = String.valueOf(ContentUris.parseId(uri));
		String[] selectionArgs = new String[]{_id};
		return sEpisodeByServerIdQueryBuilder.query(
				mEpisodeDBHelper.getReadableDatabase(),
				projection,
				sEpisodeSelection,
				selectionArgs,
				null,
				null,
				sort
		);
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case EPISODE:
				return Table.EpisodeEntry.CONTENT_TYPE;
			case EPISODE_WITH_ID:
				return Table.EpisodeEntry.CONTENT_ITEM_TYPE;
			case EPISODE_WITH_SERVER_ID:
				return Table.EpisodeEntry.CONTENT_ITEM_TYPE;
			default:
				throw new UnsupportedOperationException("Unknown uri:" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		Uri returnUri = null;
		switch (sUriMatcher.match(uri)) {
			case EPISODE:
				long id = mEpisodeDBHelper.getWritableDatabase().insert(Table.EpisodeEntry.TABLE_NAME, null, contentValues);
				Cursor query = mEpisodeDBHelper.getReadableDatabase().query(Table.EpisodeEntry.TABLE_NAME, null, null, null, null, null, null, null);
				int count = query.getCount();
				Log.v("Count", String.valueOf(count));
				if (id > 0) {
					returnUri = Table.EpisodeEntry.buildEpisodeUri(id);
				}
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return returnUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mEpisodeDBHelper.getWritableDatabase();
		int deletedCount;
		switch (sUriMatcher.match(uri)) {
			case EPISODE:
				deletedCount = db.delete(Table.EpisodeEntry.TABLE_NAME, selection, selectionArgs);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri:" + uri);
		}
		if (selection == null || deletedCount != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return deletedCount;
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {
		final SQLiteDatabase db = mEpisodeDBHelper.getWritableDatabase();
		int updateCount;
		switch (sUriMatcher.match(uri)) {
			case EPISODE:
				updateCount = db.update(Table.EpisodeEntry.TABLE_NAME, contentValues, where, whereArgs);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri:" + uri);
		}
		if (updateCount != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return updateCount;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = mEpisodeDBHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
			case EPISODE:
				db.beginTransaction();
				int returnCount = 0;
				try {
					for (ContentValues value : values) {
						long _id = db.insert(Table.EpisodeEntry.TABLE_NAME, null, value);
						if (_id != -1) {
							returnCount++;
						}
					}
					db.setTransactionSuccessful();
				} finally {
					db.endTransaction();
				}
				getContext().getContentResolver().notifyChange(uri, null);
				return returnCount;
			default:
				return super.bulkInsert(uri, values);
		}
	}
}
