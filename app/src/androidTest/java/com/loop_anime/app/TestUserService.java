package com.loop_anime.app;

import android.test.AndroidTestCase;
import android.util.Log;

import com.loop_anime.app.service.UserService;

import junit.framework.Assert;

import java.util.Date;

/**
 * Created by allan on 14-9-10.
 */
public class TestUserService extends AndroidTestCase {

	private static final String LOG_TAG = TestUserService.class.getSimpleName();

	public void testEmailLogin() throws Throwable {
		UserService.requestEmailLogin(getContext(), "test", "test", null);
		String accessToken = User.instance(getContext()).getAccessToken();
		Assert.assertNotNull(accessToken);
		Assert.assertFalse("".equals(accessToken));
		Log.v(LOG_TAG, "Access Token: " + accessToken);
		String refreshToken = User.instance(getContext()).getRefreshToken();
		Assert.assertNotNull(refreshToken);
		Assert.assertFalse("".equals(refreshToken));
		Log.v(LOG_TAG, "Refresh Token: " + refreshToken);
		int expiredTime = User.instance(getContext()).getTokenExpireTime();
		Assert.assertTrue(expiredTime > System.currentTimeMillis() / 1000 + 3000);
		Log.v(LOG_TAG, "Expired Time: " + new Date(expiredTime * 1000));
	}

	public void testRefreshToken() throws Throwable {
		if (!User.instance(getContext()).isLoggedIn()) {
			throw new Throwable("User not logged in");
		}
		UserService.requestRefreshToken(getContext(), User.instance(getContext()).getRefreshToken(), null);
		String accessToken = User.instance(getContext()).getAccessToken();
		Assert.assertNotNull(accessToken);
		Assert.assertFalse("".equals(accessToken));
		Log.v(LOG_TAG, "Access Token: " + accessToken);
		String refreshToken = User.instance(getContext()).getRefreshToken();
		Assert.assertNotNull(refreshToken);
		Assert.assertFalse("".equals(refreshToken));
		Log.v(LOG_TAG, "Refresh Token: " + refreshToken);
		int expiredTime = User.instance(getContext()).getTokenExpireTime();
		Assert.assertTrue(expiredTime > System.currentTimeMillis() / 1000 + 3000);
		Log.v(LOG_TAG, "Expired Time: " + new Date(expiredTime * 1000));
	}

}
