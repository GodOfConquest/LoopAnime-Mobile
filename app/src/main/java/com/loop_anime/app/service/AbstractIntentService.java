package com.loop_anime.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.loop_anime.app.R;
import com.loop_anime.app.User;
import com.loop_anime.app.api.API;
import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.util.NetworkUtils;

/**
 * Created by allan on 14/7/28.
 */
abstract public class AbstractIntentService extends IntentService {


	protected API api;

	private static final String LOG_TAG = "Service";

	public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";

	protected static final String EXTRA_REQUEST_TYPE = "EXTRA_REQUEST_TYPE";

	public AbstractIntentService(String name) {
		super(name);
	}


	@Override
	public void onCreate() {
		super.onCreate();
		api = APIFactory.instance();
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		updateTokenIfNecessary();
		if (intent.hasExtra(EXTRA_RECEIVER)) {
			final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
			Bundle bundle = new Bundle();
			receiver.send(ServiceReceiver.STATUS_RUNNING, bundle);
			if (NetworkUtils.isNetworkConnected(this)) {
				try {
					this.onHandleAPIIntent(intent);
					receiver.send(ServiceReceiver.STATUS_FINISHED, bundle);
				} catch (Exception e) {
					bundle.putString(ServiceReceiver.EXTRA_ERROR_MESSAGE, e.toString());
					receiver.send(ServiceReceiver.STATUS_ERROR, bundle);
				}
			} else {
				Log.e(LOG_TAG, "No Internet connection; Service exited.");
				bundle.putString(ServiceReceiver.EXTRA_ERROR_MESSAGE, getResources().getString(R.string.no_internet));
				receiver.send(ServiceReceiver.STATUS_ERROR, bundle);
			}
		} else {
			if (NetworkUtils.isNetworkConnected(this)) {
				try {
					this.onHandleAPIIntent(intent);
				} catch (Exception e) {
					Log.e(LOG_TAG, e.toString());
				}
			} else {
				Log.e(LOG_TAG, "No Internet connection; Service exited.");
			}
		}
	}

	private void updateTokenIfNecessary() {
		if (!(this instanceof UserService)
				&& User.instance(getBaseContext()).isLoggedIn()
				&& User.instance(getBaseContext()).getTokenExpireTime() <= System.currentTimeMillis() / 1000) {
			try {
				UserService.requestRefreshToken(
						getBaseContext(),
						User.instance(getBaseContext()).getRefreshToken(),
						null
				);
			} catch (Exception e) {
				Log.e(LOG_TAG, "Failed to refresh token: " + e.toString());
			}
		}
	}

	protected abstract void onHandleAPIIntent(Intent intent);

}
