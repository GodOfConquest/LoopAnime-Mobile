package com.loop_anime.app.api;

import android.util.Log;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by allan on 14/7/28.
 */
public class APIFactory {

    public static String BASE_URL = "http://www.loop-anime.com:8080";

    private static API instance;

    public static API instence() {
        if (instance == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("Retrofit"))
                    .build();
            instance = restAdapter.create(API.class);
        }
        return instance;
    }

}
