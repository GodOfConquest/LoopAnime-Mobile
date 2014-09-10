package com.loop_anime.app.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.loop_anime.app.api.APIFactory;
import com.loop_anime.app.api.EpisodeResponse;
import com.loop_anime.app.api.LinkResponse;
import com.loop_anime.app.api.model.Episode;
import com.loop_anime.app.api.model.Link;

import java.util.List;

import static com.loop_anime.app.db.Table.EpisodeEntry;

/**
 * Created by allan on 14/8/16.
 */
public class EpisodeService extends AbstractIntentService {

	private static final String EXTRA_PAGE = "EXTRA_PAGE";

	private static final String EXTRA_MAXR = "EXTRA_MAXR";

	private static final String EXTRA_LIMIT = "EXTRA_LIMIT";

	private static final String EXTRA_TYPE_EPISODE = "EXTRA_TYPE_EPISODE";

	private static final String EXTRA_SERVER_ID = "EXTRA_SERVER_ID";

	private enum REQUESTS {EPISODE_BY_SERVER_ID, EPISODE, GET_LINK}

	;

	public EpisodeService() {
		super("EpisodeService");
	}

	public static Intent requestEpisodes(Context context, int maxr, int page, ServiceReceiver receiver) {
		final Intent intent = new Intent(context, EpisodeService.class);
		if (receiver != null) {
			intent.putExtra(EXTRA_RECEIVER, receiver);
		}
		intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.EPISODE);
		intent.putExtra(EXTRA_MAXR, maxr);
		intent.putExtra(EXTRA_PAGE, page);
		context.startService(intent);
		return intent;
	}

	public static Intent getLink(Context context, int episode, ServiceReceiver receiver) {
		final Intent intent = new Intent(context, EpisodeService.class);
		if (receiver != null) {
			intent.putExtra(EXTRA_RECEIVER, receiver);
		}
		intent.putExtra(EXTRA_REQUEST_TYPE, REQUESTS.GET_LINK);
		intent.putExtra(EXTRA_SERVER_ID, episode);
		context.startService(intent);
		return intent;
	}


	private void getEpisodes(int maxr, int page, String typeEpisode) {
		if (page <= 1) {
			getContentResolver().delete(EpisodeEntry.CONTENT_URI, null, null);
		}
		EpisodeResponse episodeResponse = api.episode(maxr, page, typeEpisode);
		List<Episode> episodes = episodeResponse.getPayload().getEpisodes();
		ContentValues[] contentValues = new ContentValues[episodes.size()];
		for (int i = 0; i < episodes.size(); i++) {
			ContentValues values = getEpisodeContentValues(episodes.get(i));
			contentValues[i] = values;
		}
		getContentResolver().bulkInsert(EpisodeEntry.CONTENT_URI, contentValues);
	}


	private void getLink(int episodeId) {
		if (episodeId > 0) {
			LinkResponse linkResponse = APIFactory.instance().getLinks(episodeId);
			List<Link> links = linkResponse.getPayload().getLinks();
			//TODO link provider
		}
	}


	private ContentValues getEpisodeContentValues(Episode episode) {
		ContentValues values = new ContentValues();
		values.put(EpisodeEntry.COLUMN_ANIME_SERVER_ID, episode.getAnimeInfo().getAnimeServerId());
		values.put(EpisodeEntry.COLUMN_ANIME_TITLE, episode.getAnimeInfo().getAnimeTitle());
		values.put(EpisodeEntry.COLUMN_SEASON_SERVER_ID, episode.getSeasonInfo().getSeasonServerId());
		values.put(EpisodeEntry.COLUMN_SEASON, episode.getSeasonInfo().getSeason());
		values.put(EpisodeEntry.COLUMN_SERVER_ID, episode.getServerId());
		values.put(EpisodeEntry.COLUMN_POSTER, episode.getPoster());
		values.put(EpisodeEntry.COLUMN_AIR_DATE, episode.getAirdate().getDate());
		values.put(EpisodeEntry.COLUMN_TIME_ZONE_TYPE, episode.getAirdate().getTimezoneType());
		values.put(EpisodeEntry.COLUMN_TIME_ZONE, episode.getAirdate().getTimezone());
		values.put(EpisodeEntry.COLUMN_ABSOLUTE_NUMBER, episode.getAbsoluteNumber());
		values.put(EpisodeEntry.COLUMN_VIEWS, episode.getViews());
		values.put(EpisodeEntry.COLUMN_TITLE, episode.getTitle());
		values.put(EpisodeEntry.COLUMN_EPISODE_NUMBER, episode.getEpisodeNumber());
		values.put(EpisodeEntry.COLUMN_RATING, episode.getRating());
		values.put(EpisodeEntry.COLUMN_SUMMERY, episode.getSummary());
		values.put(EpisodeEntry.COLUMN_RATING_UP, episode.getRating());
		values.put(EpisodeEntry.COLUMN_RATING_DOWN, episode.getRatingDown());
		return values;
	}


	@Override
	protected void onHandleAPIIntent(Intent intent) {
		REQUESTS request = (REQUESTS) intent.getSerializableExtra(EXTRA_REQUEST_TYPE);
		switch (request) {
			case EPISODE:
				getEpisodes(intent.getIntExtra(EXTRA_MAXR, 10), intent.getIntExtra(EXTRA_PAGE, 1), intent.getStringExtra(EXTRA_TYPE_EPISODE));
				break;
			case EPISODE_BY_SERVER_ID:
				break;
			case GET_LINK:
				getLink(intent.getIntExtra(EXTRA_SERVER_ID, -1));
			default:
				throw new UnsupportedOperationException("Unknown request: " + request);
		}
	}

}
