package com.loop_anime.app.api;

import com.google.gson.annotations.SerializedName;
import com.loop_anime.app.api.model.Episode;

import java.util.List;

/**
 * Created by allan on 14/8/6.
 */
public class EpisodeResponse {

    @SerializedName("payload")
    EpisodePayload payload;

    public EpisodePayload getPayload() {
        return payload;
    }

    public class EpisodePayload {

        @SerializedName("animes")
        AnimesPayload animesPayload;

        public AnimesPayload getAnimesPayload() {
            return animesPayload;
        }

        public class AnimesPayload {

            List<Episode> episodes;

            public List<Episode> getEpisodes() {
                return episodes;
            }

        }
    }
}
