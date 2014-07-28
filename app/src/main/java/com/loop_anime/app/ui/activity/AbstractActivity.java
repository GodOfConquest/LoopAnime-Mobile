package com.loop_anime.app.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.loop_anime.app.util.BitmapLruCache;

/**
 * Created by allan on 14/7/28.
 */
public class AbstractActivity extends Activity {

    private ImageLoader imageLoaderMemoryCache;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
    }

    public ImageLoader getImageLoaderMemoryCache() {
        if (imageLoaderMemoryCache == null)
            imageLoaderMemoryCache = new ImageLoader(requestQueue, new BitmapLruCache());
        return imageLoaderMemoryCache;
    }
}
