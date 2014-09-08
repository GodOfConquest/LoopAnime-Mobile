package com.loop_anime.app.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allan on 14/7/27.
 */
public class Anime {

	@SerializedName("id")
	private int serverId;

	@SerializedName("poster")
	private String poster;

	@SerializedName("genres")
	private String generes;

	@SerializedName("startTime")
	private String startTime;

	@SerializedName("endTime")
	private String endTime;

	@SerializedName("title")
	private String title;

	@SerializedName("plotSummary")
	private String plotSummary;

	@SerializedName("rating")
	private int rating;

	@SerializedName("status")
	private String status;

	@SerializedName("runningTime")
	private String runningTime;

	@SerializedName("ratingUp")
	private int ratingUp;

	@SerializedName("ratingDown")
	private int ratingDown;

	@SerializedName("total_seasons")
	private int totalSeasons;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getGeneres() {
		return generes;
	}

	public void setGeneres(String generes) {
		this.generes = generes;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlotSummary() {
		return plotSummary;
	}

	public void setPlotSummary(String plotSummary) {
		this.plotSummary = plotSummary;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public int getRatingUp() {
		return ratingUp;
	}

	public void setRatingUp(int ratingUp) {
		this.ratingUp = ratingUp;
	}

	public int getRatingDown() {
		return ratingDown;
	}

	public void setRatingDown(int ratingDown) {
		this.ratingDown = ratingDown;
	}

	public int getTotalSeasons() {
		return totalSeasons;
	}

	public void setTotalSeasons(int totalSeasons) {
		this.totalSeasons = totalSeasons;
	}
}
