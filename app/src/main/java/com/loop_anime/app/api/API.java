package com.loop_anime.app.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by allan on 14/7/27.
 */
public interface API {

    @GET("/animes/list-animes.json")
    AnimeResponse animes(@Query("page") int page, @Query("maxr") int limit);

    @POST("/animes/{id}.json")
    AnimeResponse animesByServerId(@Path("id") int serverId);

    @GET("/episodes/get-episodes.json")
    EpisodeResponse episode(@Query("maxr") int limit, @Query("page") int page, @Query("typeEpisode") String type);

    @FormUrlEncoded
    @POST("/episodes/list-episodes.json")
    EpisodeResponse episode(@Field("anime") int animeId, @Field("season") int season);

    @GET("/links/get-links.json")
    LinkResponse getLinks(@Query("episode") int episodeId);
}
