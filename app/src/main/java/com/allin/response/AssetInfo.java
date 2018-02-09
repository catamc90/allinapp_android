package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssetInfo implements Serializable{

    @SerializedName("company_id")
    public int companyId;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("social")
    public int social;

    @SerializedName("caption")
    public String caption;

    @SerializedName("asset_id")
    public int assetId;

    @SerializedName("used")
    public int used;

    @SerializedName("filename")
    public String filename;

    @SerializedName("starred")
    public boolean starred;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("asset_type")
    public int assetType;

    @SerializedName("votes")
    public int votes;

    @SerializedName("category")
    public String category;

    @SerializedName("user_display_name")
    public String userDisplayName;

    @SerializedName("user_ranking")
    public int userRanking;

    @SerializedName("status")
    public int status;
}