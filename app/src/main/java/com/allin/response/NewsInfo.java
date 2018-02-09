package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsInfo implements Serializable {

    @SerializedName("image")
    public String image;

    @SerializedName("company_id")
    public int companyId;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("read_time")
    public String readTime;

    @SerializedName("r_news_id")
    public int rNewsId;

    @SerializedName("writer")
    public String writer;

    @SerializedName("video")
    public String video;

    @SerializedName("body")
    public String body;

    @SerializedName("headline")
    public String headline;

    @SerializedName("news_id")
    public int newsId;
}