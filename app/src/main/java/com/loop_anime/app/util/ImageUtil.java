package com.loop_anime.app.util;

/**
 * Created by allan on 14/7/27.
 */
public class ImageUtil {

	public static final String SERVER_BASE_URL = "http://www.loop-anime.com:8080";

	public static String getFullImageUrl(String url) {
		return SERVER_BASE_URL + url;
	}
}
