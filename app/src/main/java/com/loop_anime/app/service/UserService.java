package com.loop_anime.app.service;

import android.content.Context;
import android.content.Intent;

import com.loop_anime.app.Settings;
import com.loop_anime.app.User;
import com.loop_anime.app.api.APIAuthFactory;
import com.loop_anime.app.api.TokenResponse;

/**
 * Created by allan on 14-9-10.
 */
public class UserService extends AbstractIntentService {

	private static final String EXTRA_USERNAME = "EXTRA_USERNAME";

	private static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";

	private static final String EXTRA_REFRESH_TOKEN = "EXTRA_REFRESH_TOKEN";

	public static enum REQUESTS {
		EMAIL_LOGIN,
		REFRESH_TOKEN,
		USER_LOGOUT
	}

	public UserService() {
		super("UserService");
	}

	public static Intent requestEmailLogin(Context context, String username, String password, ServiceReceiver receiver) {
		final Intent intent = new Intent(context, UserService.class);
		if (receiver != null) {
			intent.putExtra(EXTRA_RECEIVER, receiver);
		}
		intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.EMAIL_LOGIN);
		intent.putExtra(EXTRA_USERNAME, username);
		intent.putExtra(EXTRA_PASSWORD, password);
		context.startService(intent);
		return intent;
	}

	public static Intent logout(Context context, ServiceReceiver receiver) {
		final Intent intent = new Intent(context, UserService.class);
		if (receiver != null) {
			intent.putExtra(EXTRA_RECEIVER, receiver);
		}
		intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.USER_LOGOUT);
		context.startService(intent);
		return intent;
	}


	public static Intent requestRefreshToken(Context context, String refreshToken, ServiceReceiver receiver) {
		final Intent intent = new Intent(context, UserService.class);
		if (receiver != null) {
			intent.putExtra(EXTRA_RECEIVER, receiver);
		}
		intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.REFRESH_TOKEN);
		intent.putExtra(EXTRA_REFRESH_TOKEN, refreshToken);
		context.startService(intent);
		return intent;
	}

	@Override
	protected void onHandleAPIIntent(Intent intent) {
		REQUESTS request = (REQUESTS) intent.getSerializableExtra(EXTRA_REQUEST_TYPE);
		switch (request) {
			case EMAIL_LOGIN:
				requestEmailLogin(intent.getStringExtra(EXTRA_USERNAME), intent.getStringExtra(EXTRA_PASSWORD));
				break;
			case REFRESH_TOKEN:
				requestRefreshToken(intent.getStringExtra(EXTRA_REFRESH_TOKEN));
				break;
			case USER_LOGOUT:
				logout();
				break;
		}
	}

	private void requestEmailLogin(String username, String password) {
		TokenResponse response = APIAuthFactory.instance().emailLogin(Settings.Api.CLIENT_ID, Settings.Api.CLIENT_SECREAT, "password", username, password);
		updateUserTokens(response);
	}


	private void requestRefreshToken(String refreshToken) {
		TokenResponse response = APIAuthFactory.instance().refreshToken(Settings.Api.CLIENT_ID, Settings.Api.CLIENT_SECREAT, "refresh_token", refreshToken);
		updateUserTokens(response);
	}


	private void updateUserTokens(TokenResponse response) {
		User user = User.instance(this);
		user.setAccessToken(response.getAccessToken());
		user.setRefreshToken(response.getRefreshToken());
		user.setTokenExpiredIn(response.getExpiredIn());
		user.save(this);
	}

	private void logout() {
		User user = User.instance(this);
		if (user.isLoggedIn()) {
			user.clean(this);
		}
	}


}
