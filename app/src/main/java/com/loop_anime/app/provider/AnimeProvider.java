package com.loop_anime.app.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.loop_anime.app.db.DbHelper;
import com.loop_anime.app.db.Table;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeProvider extends ContentProvider {

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static final int ANIME = 100;

	private static final int ANIME_WITH_ID = 101;

	private static final int ANIME_WITH_SERVER_ID = 102;

	private DbHelper mAnimeHelper;


	private static final SQLiteQueryBuilder sAnimeByServerIdQueryBuilder;

	static {
		sAnimeByServerIdQueryBuilder = new SQLiteQueryBuilder();
		sAnimeByServerIdQueryBuilder.setTables(Table.AnimeEntry.TABLE_NAME);
	}

	private static final String sAnimeSelection = Table.AnimeEntry._ID + " = ?";

	private static final String sAnimeByServerIdSelection = Table.AnimeEntry.COLUMN_SERVER_ID + " = ?";


	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = Table.ANIME_CONTENT_AUTHORITY;
		matcher.addURI(authority, Table.PATH_ANIME, ANIME);
		matcher.addURI(authority, Table.PATH_ANIME + "/#", ANIME_WITH_ID);
		matcher.addURI(authority, Table.PATH_ANIME + "/" + Table.AnimeEntry.COLUMN_SERVER_ID + "/#", ANIME_WITH_SERVER_ID);
		return matcher;
	}

	@Override
	public boolean onCreate() {
		mAnimeHelper = new DbHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
		Cursor cursor;
		switch (sUriMatcher.match(uri)) {
			case ANIME:
				cursor = sAnimeByServerIdQueryBuilder.query(mAnimeHelper.getReadableDatabase(),
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sort);
				break;
			case ANIME_WITH_ID:
				cursor = getAnimeById(uri, projection, sort);
				break;
			case ANIME_WITH_SERVER_ID:
				cursor = getAnimeByServerId(uri, projection, sort);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	private Cursor getAnimeById(Uri uri, String[] projection, String sort) {
		String _id = String.valueOf(ContentUris.parseId(uri));
		String[] selectionArgs = new String[]{_id};
		return sAnimeByServerIdQueryBuilder.query(
				mAnimeHelper.getReadableDatabase(),
				projection,
				sAnimeSelection,
				selectionArgs,
				null,
				null,
				sort
		);
	}

	private Cursor getAnimeByServerId(Uri uri, String[] projection, String sort) {
		String serverId = Table.AnimeEntry.getServerIdFromUri(uri);
		String[] selectionArgs = new String[]{serverId};
		return sAnimeByServerIdQueryBuilder.query(
				mAnimeHelper.getReadableDatabase(),
				projection,
				sAnimeByServerIdSelection,
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
			case ANIME:
				return Table.AnimeEntry.CONTENT_TYPE;
			case ANIME_WITH_ID:
				return Table.AnimeEntry.CONTENT_ITEM_TYPE;
			case ANIME_WITH_SERVER_ID:
				return Table.AnimeEntry.CONTENT_ITEM_TYPE;
			default:
				throw new UnsupportedOperationException("Unknown uri:" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		Uri returnUri = null;
		switch (sUriMatcher.match(uri)) {
			case ANIME:
				long id = mAnimeHelper.getWritableDatabase().insert(Table.AnimeEntry.TABLE_NAME, null, contentValues);
				if (id > 0) {
					returnUri = Table.AnimeEntry.buildAnimeUri(id);
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
		final SQLiteDatabase db = mAnimeHelper.getWritableDatabase();
		int deletedCount;
		switch (sUriMatcher.match(uri)) {
			case ANIME:
				deletedCount = db.delete(Table.AnimeEntry.TABLE_NAME, selection, selectionArgs);
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
	public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mAnimeHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int rowsUpdated;
		switch (match) {
			case ANIME:
				rowsUpdated = db.update(Table.AnimeEntry.TABLE_NAME, contentValues, selection,
						selectionArgs);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		if (rowsUpdated != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return rowsUpdated;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = mAnimeHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
			case ANIME:
				db.beginTransaction();
				int returnCount = 0;
				try {
					for (ContentValues value : values) {
						long _id = db.insert(Table.AnimeEntry.TABLE_NAME, null, value);
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
