package com.loop_anime.app;

import android.test.AndroidTestCase;
import android.util.Log;

import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.api.EpisodeResponse;
import com.loop_anime.app.api.model.Episode;
import com.loop_anime.app.service.AnimeService;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by allan on 14/7/27.
 */
public class TestApi extends AndroidTestCase {

    public void testEpisode() throws Throwable {
        EpisodeResponse recent = APIFactory.instence().episode(10, 0, "mostrated");
        List<Episode> episodes = recent.getPayload().getAnimesPayload().getEpisodes();
        for (Episode episode : episodes) {
            Log.v("EPISODE API", episode.getAnimeInfo().getAnimeTitle());
        }
        Assert.assertEquals(10,episodes.size());
    }
}
