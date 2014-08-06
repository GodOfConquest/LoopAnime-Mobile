package com.loop_anime.app.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by allan on 14/7/27.
 */
public interface API {

    @FormUrlEncoded
    @POST("/animes/list-animes.json")
    AnimeResponse animes(@Field("skip") int skip, @Field("limit") int limit);


    @POST("/animes/{id}.json")
    AnimeResponse animesByServerId(@Path("id") int serverId);

    @FormUrlEncoded
    @POST("/episodes/get-episodes.json")
    EpisodeResponse episode(@Field("maxr") int limit, @Field("skip") int skip, @Field("typeEpisode") String type);

}
