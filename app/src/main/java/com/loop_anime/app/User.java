package com.loop_anime.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by allan on 14-9-10.
 */
public class User {

	private static final String USER_ACCESS_TOKEN = "com.loop_anime.app.user.access_token";

	private static final String USER_REFRESH_TOKEN = "com.loop_anime.app.user.refresh_token";

	private static final String USER_TOKEN_EXPIRE_TIME = "com.loop_anime.app.user.token_expire_time";

	private static User instance;

	private User(Context context) {
		load(context);
	}

	public static User instance(Context context) {
		if (instance == null && context != null) {
			instance = new User(context);
		}
		return instance;
	}

	private String accessToken;

	private String refreshToken;

	private int tokenExpireTime;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getTokenExpireTime() {
		return tokenExpireTime;
	}

	public void setTokenExpiredIn(int tokenExpireTime) {
		this.tokenExpireTime = (int) (System.currentTimeMillis() / 1000) + tokenExpireTime;
	}

	public void setTokenExpireTime(int tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}

	public boolean isLoggedIn() {
		return accessToken != null && accessToken != "";
	}

	public void save(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(USER_ACCESS_TOKEN, accessToken);
		editor.putString(USER_REFRESH_TOKEN, refreshToken);
		editor.putInt(USER_TOKEN_EXPIRE_TIME, tokenExpireTime);
		editor.commit();
	}

	public void load(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		accessToken = preferences.getString(USER_ACCESS_TOKEN, "");
		refreshToken = preferences.getString(USER_REFRESH_TOKEN, "");
		tokenExpireTime = preferences.getInt(USER_TOKEN_EXPIRE_TIME, 0);
	}

	public void clean(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		preferences.edit().clear().commit();
		//load empty user
		load(context);
	}

	private SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
