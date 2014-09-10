package com.loop_anime.app.api;

import com.loop_anime.app.Settings;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by allan on 14-9-10.
 */
public class APIAuthFactory {

	public static String BASE_URL = "http://www.loop-anime.com:8080/oauth";

	private static API instance;

	public static API instance() {
		if (instance == null) {
			RestAdapter restAdapter = new RestAdapter.Builder()
					.setEndpoint(BASE_URL + getVersion())
					.setLogLevel(RestAdapter.LogLevel.FULL)
					.setLog(new AndroidLog("Retrofit"))
					.build();
			instance = restAdapter.create(API.class);
		}
		return instance;
	}

	private static String getVersion() {
		return "/v" + String.valueOf(Settings.Api.API_AUTH_VERSION);
	}

}
