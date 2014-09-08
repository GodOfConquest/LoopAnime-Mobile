package com.loop_anime.app.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allan on 14/8/6.
 */
public class Episode {

	@SerializedName("id")
	private int serverId;

	@SerializedName("poster")
	private String poster;

	@SerializedName("idSeason")
	private int idSeason;

	@SerializedName("airDate")
	private Date airdate;

	@SerializedName("absoluteNumber")
	private int absoluteNumber;

	private int views;

	private String title;

	private int episodeNumber;

	private int rating;

	private String summary;

	private int ratingUp;

	private int ratingDown;

	@SerializedName("anime")
	private AnimeInfo animeInfo;

	@SerializedName("season")
	private SeasonInfo seasonInfo;

	public int getServerId() {
		return serverId;
	}

	public String getPoster() {
		return poster;
	}

	public int getIdSeason() {
		return idSeason;
	}

	public Date getAirdate() {
		return airdate;
	}

	public int getAbsoluteNumber() {
		return absoluteNumber;
	}

	public int getViews() {
		return views;
	}

	public String getTitle() {
		return title;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public int getRating() {
		return rating;
	}

	public String getSummary() {
		return summary;
	}

	public int getRatingUp() {
		return ratingUp;
	}

	public int getRatingDown() {
		return ratingDown;
	}

	public AnimeInfo getAnimeInfo() {
		return animeInfo;
	}

	public SeasonInfo getSeasonInfo() {
		return seasonInfo;
	}

	public class AnimeInfo {

		@SerializedName("id")
		private int animeServerId;

		@SerializedName("title")
		private String animeTitle;

		public String getAnimeTitle() {
			return animeTitle;
		}

		public int getAnimeServerId() {
			return animeServerId;
		}
	}

	public class SeasonInfo {

		@SerializedName("id")
		private int seasonServerId;

		@SerializedName("season")
		private int season;

		public int getSeasonServerId() {
			return seasonServerId;
		}

		public int getSeason() {
			return season;
		}
	}
}
