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
    private AirDate airdate;

    @SerializedName("absoluteNumber")
    private int absoluteNumber;

    private int views;

    private String title;

    private int episodeNumber;

    private int rating;

    private String summary;

    private int ratingUp;

    private int ratingDown;

    public int getServerId() {
        return serverId;
    }

    public String getPoster() {
        return poster;
    }

    public int getIdSeason() {
        return idSeason;
    }

    public AirDate getAirdate() {
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

    public class AirDate {

        private String date;

        @SerializedName("timezone_type")
        private int timezoneType;

        private String timezone;

        public String getDate() {
            return date;
        }

        public int getTimezoneType() {
            return timezoneType;
        }

        public String getTimezone() {
            return timezone;
        }
    }
}
