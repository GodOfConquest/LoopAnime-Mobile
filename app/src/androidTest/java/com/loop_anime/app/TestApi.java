package com.loop_anime.app;

import android.test.AndroidTestCase;
import android.util.Log;

import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.api.AnimeResponse;
import com.loop_anime.app.api.EpisodeResponse;
import com.loop_anime.app.api.LinkResponse;
import com.loop_anime.app.api.model.Anime;
import com.loop_anime.app.api.model.Episode;
import com.loop_anime.app.api.model.Link;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by allan on 14/7/27.
 */
public class TestApi extends AndroidTestCase {

	public void testAnime() throws Throwable {
		AnimeResponse animesResponse = APIFactory.instance().animes(3, 10);
		List<Anime> animes = animesResponse.getPayload().getAnimes();
		for (Anime anime : animes) {
			Log.v("EPISODE API", String.valueOf(anime.getTotalSeasons()));
		}
		Assert.assertEquals(10, animes.size());
	}

	public void testEpisode() throws Throwable {
		EpisodeResponse recent = APIFactory.instance().episode(20, 0, null);
		List<Episode> episodes = recent.getPayload().getEpisodes();
		for (Episode episode : episodes) {
			Log.v("EPISODE API", episode.getAnimeInfo().getAnimeTitle());
		}
		Assert.assertEquals(20, episodes.size());
	}


	public void testEpisodeByAnimeId() throws Throwable {
		EpisodeResponse recent = APIFactory.instance().episode(29, 0);
		List<Episode> episodes = recent.getPayload().getEpisodes();
		for (Episode episode : episodes) {
			Log.v("EPISODE API", String.valueOf(episode.getSeasonInfo().getSeason()));
		}
		Assert.assertEquals(10, episodes.size());
	}

	public void testGetLink() throws Throwable {
		LinkResponse linkResponse = APIFactory.instance().getLinks(337);
		List<Link> links = linkResponse.getPayload().getLinks();
		for (Link link : links) {
			Log.v("Get Link API", link.getLink());
		}
		Assert.assertTrue(links.size() > 0);
	}
}
