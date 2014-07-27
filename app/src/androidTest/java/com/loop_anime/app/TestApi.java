package com.loop_anime.app;

import android.test.AndroidTestCase;
import android.util.Log;

import com.loop_anime.app.api.API;
import com.loop_anime.app.api.model.Anime;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by allan on 14/7/27.
 */
public class TestApi extends AndroidTestCase {

    private static final String BASE_URL = "http://www.loop-anime.com:8080";

    public void testAnime() throws Throwable {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        API api = restAdapter.create(API.class);

        List<Anime> animes = api.animes().getPayload().getAnimes();

        for (Anime anime : animes) {
            Log.v(this.getClass().getSimpleName(), "Anime: " + anime.getTitle());
        }
    }
}
