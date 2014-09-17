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

	/**
	 * Auth
	 */
	@GET("/token")
	TokenResponse emailLogin(
			@Query("client_id") String clientId,
			@Query("client_secret") String clientSecret,
			@Query("grant_type") String grantType,
			@Query("username") String username,
			@Query("password") String password);

	@GET("/token")
	TokenResponse refreshToken(
			@Query("client_id") String clientId,
			@Query("client_secret") String clientSecret,
			@Query("grant_type") String grantType,
			@Query("refresh_token") String refreshToken);


	@GET("/animes.json")
	AnimeResponse animes(
			@Query("access_token") String accessToken,
			@Query("page") int page, @Query("maxr") int limit);

	@POST("/animes/{id}.json")
	AnimeResponse animesByServerId(@Path("id") int serverId);

	@GET("/episodes/get-episodes.json")
	EpisodeResponse episode(@Query("maxr") int limit, @Query("page") int page, @Query("typeEpisode") String type);

	@FormUrlEncoded
	@POST("/episodes/list-episodes.json")
	EpisodeResponse episode(@Field("anime") int animeId, @Field("season") int season);

	@GET("/links/get-links.json")
	LinkResponse getLinks(@Query("episode") int episodeId);

	@GET("/links/{id}.json")
	DirectLinkResponse getDirectLink(@Path("id") int linkId);
}
