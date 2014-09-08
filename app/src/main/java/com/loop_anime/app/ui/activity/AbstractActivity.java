package com.loop_anime.app.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.loop_anime.app.service.ServiceReceiver;
import com.loop_anime.app.util.BitmapLruCache;

/**
 * Created by allan on 14/7/28.
 */
public abstract class AbstractActivity extends Activity implements ServiceReceiver.Receiver {

	private ImageLoader imageLoaderMemoryCache;

	private RequestQueue requestQueue;

	public ServiceReceiver mReceiver;

	public abstract boolean enableReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestQueue = Volley.newRequestQueue(this);
		if (enableReceiver()) {
			mReceiver = new ServiceReceiver(new Handler());
			mReceiver.setReceiver(this);
		}
	}

	public ImageLoader getImageLoaderMemoryCache() {
		if (imageLoaderMemoryCache == null)
			imageLoaderMemoryCache = new ImageLoader(requestQueue, new BitmapLruCache());
		return imageLoaderMemoryCache;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
	}

	;

}
