package com.loop_anime.app.api;

import com.loop_anime.app.api.model.Anime;

import java.util.List;

import retrofit.http.POST;

/**
 * Created by allan on 14/7/27.
 */
public interface API {

    @POST("/animes/list-animes.json")
    AnimeResponse animes();

    class AnimeResponse {

        Payload payload;

        public Payload getPayload() {
            return payload;
        }


    }

    class Payload {
            List<Anime> animes;
            public List<Anime> getAnimes() {
                return animes;
            }
    }
}
