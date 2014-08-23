package com.loop_anime.app.api;

import com.google.gson.annotations.SerializedName;
import com.loop_anime.app.api.model.Link;

import java.util.List;

/**
 * Created by allan on 14/8/23.
 */
public class LinkResponse {

    @SerializedName("payload")
    LinkPayload payload;

    public LinkPayload getPayload() {
        return payload;
    }

    public class LinkPayload {

        @SerializedName("links")
        List<Link> links;

        public List<Link> getLinks() {
            return links;
        }

    }
}
