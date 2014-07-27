package com.loop_anime.app.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by allan on 14/7/27.
 */
public interface API {

    public static final String BASE_URL = "http://www.loop-anime.com:8080";

    @FormUrlEncoded
    @POST("/animes/list-animes.json")
    AnimeResponse animes(@Field("skip") int skip, @Field("limit") int limit);


}
