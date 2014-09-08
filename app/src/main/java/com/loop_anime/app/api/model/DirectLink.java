package com.loop_anime.app.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allan on 14/8/24.
 */
public class DirectLink {

	@SerializedName("direct_link")
	private String directLink;

	@SerializedName("id")
	private int id;

	@SerializedName("lang")
	private String language;

	@SerializedName("createTime")
	private Date createTime;

	@SerializedName("fileServer")
	private String fileServer;

	@SerializedName("fileSize")
	private String fileSize;

	@SerializedName("hoster")
	private String hoster;

	@SerializedName("subtitles")
	private int subtitles;

	@SerializedName("subtitlesLang")
	private String subtitlesLanguage;

	@SerializedName("qualityType")
	private String qualityType;

	@SerializedName("fileType")
	private String fileType;

	@SerializedName("link")
	private String link;

	@SerializedName("used")
	private int used;

	@SerializedName("usedTimes")
	private int usedTimes;

	@SerializedName("status")
	private int status;

	public int getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getFileServer() {
		return fileServer;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getHoster() {
		return hoster;
	}

	public int getSubtitles() {
		return subtitles;
	}

	public String getSubtitlesLanguage() {
		return subtitlesLanguage;
	}

	public String getQualityType() {
		return qualityType;
	}

	public String getFileType() {
		return fileType;
	}

	public String getLink() {
		return link;
	}

	public int getUsed() {
		return used;
	}

	public int getUsedTimes() {
		return usedTimes;
	}

	public int getStatus() {
		return status;
	}

	public String getDirectLink() {
		return directLink;
	}
}
