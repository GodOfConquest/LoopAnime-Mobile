package com.loop_anime.app.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeService extends IntentService{

    public AnimeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
