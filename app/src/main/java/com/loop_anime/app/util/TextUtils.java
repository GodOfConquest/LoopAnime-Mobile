package com.loop_anime.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by allan on 14-9-8.
 */
public class TextUtils {

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isEmailValid(final String email) {
		final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		final Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}


}
