package com.loop_anime.app.api;

import com.google.gson.annotations.SerializedName;
import com.loop_anime.app.api.model.Anime;

import java.util.List;

/**
 * Created by allan on 14/7/27.
 */
public class AnimeResponse {

    @SerializedName("payload")
    animePayload payload;

    public animePayload getPayload() {
        return payload;
    }

    public class animePayload {
        List<Anime> animes;

        public List<Anime> getAnimes() {
            return animes;
        }
    }
}
