package com.loop_anime.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.loop_anime.app.util.NetworkUtil;

/**
 * Created by allan on 14/7/28.
 */
abstract public class AbstractIntentService extends IntentService{

    private static final String LOG_TAG = "Service";

    public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";

    public AbstractIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(EXTRA_RECEIVER)) {
            final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
            Bundle bundle = new Bundle();
            receiver.send(ServiceReceiver.STATUS_RUNNING, bundle);
            if (NetworkUtil.isNetworkConnected(this)) {
                try {
                    this.onHandleAPIIntent(intent);
                    receiver.send(ServiceReceiver.STATUS_FINISHED, bundle);
                } catch (Exception e) {
                    bundle.putString(ServiceReceiver.EXTRA_ERROR_MESSAGE, e.toString());
                    receiver.send(ServiceReceiver.STATUS_ERROR, bundle);
                }
            } else {
                Log.e(LOG_TAG, "No Internet connection; Service exited.");
            }
        }
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
