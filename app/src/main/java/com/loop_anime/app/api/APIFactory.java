package com.loop_anime.app.api;

import com.loop_anime.app.Settings;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by allan on 14/7/28.
 */
public class APIFactory {

	public static String BASE_URL = "http://www.loop-anime.com:8080/api";

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
		return "/v" + String.valueOf(Settings.Api.API_VERSION);
	}

}
