package com.loop_anime.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.loop_anime.app.util.NetworkUtil;

/**
 * Created by allan on 14/7/28.
 */
abstract public class AbstractIntentService extends IntentService{

    private static final String LOG_TAG = "Service";

    public AbstractIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                this.onHandleAPIIntent(intent);
            } catch (Exception e) {
                //TODO: send to receiver with ERROR_MSG
            }
        } else {
            Log.e(LOG_TAG, "No Internet connection; Service exited.");
        }
    }

    protected abstract void onHandleAPIIntent(Intent intent);

}
