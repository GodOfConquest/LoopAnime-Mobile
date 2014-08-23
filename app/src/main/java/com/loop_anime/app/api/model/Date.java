package com.loop_anime.app.api.model;

import com.google.gson.annotations.SerializedName;

/**
* Created by allan on 14/8/23.
*/
public class Date {

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
