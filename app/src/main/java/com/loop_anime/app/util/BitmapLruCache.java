package com.loop_anime.app.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by allan on 14/7/27.
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

	private static final String LOG_TAG = "Cache";

	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	public BitmapLruCache() {
		this(getDefaultLruCacheSize());
	}

	public BitmapLruCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Log.v(LOG_TAG, "put " + url);
		Log.d(LOG_TAG, "Memory cache at " + (100.0 * size() / maxSize()) + "%");
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        TODO: better way to downsize images according to the given size
//        float scaleWidth = .5f;
//        float scaleHeight = .5f;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		put(url, bitmap);
	}
}
