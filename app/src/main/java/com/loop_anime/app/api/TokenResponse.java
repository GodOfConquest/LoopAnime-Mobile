package com.loop_anime.app.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allan on 14-9-10.
 */
public class TokenResponse {

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("expires_in")
	private int exprireIn;

	@SerializedName("token_type")
	private String tokenType;

	private String scope;

	@SerializedName("refresh_token")
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExprireIn() {
		return exprireIn;
	}

	public void setExprireIn(int exprireIn) {
		this.exprireIn = exprireIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
