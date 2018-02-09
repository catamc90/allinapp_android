package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable {

    @SerializedName("role")
    public int role;

    @SerializedName("company_id")
    public int companyId;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("active")
    public int active;

    @SerializedName("asset_goal")
    public int assetGoal;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("display_name")
    public String displayName;

    @SerializedName("is_manager")
    public boolean isManager;

    @SerializedName("asset_count")
    public int assetCount;

    @SerializedName("password")
    public String password;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("update_time")
    public String updateTime;

    @SerializedName("score")
    public int score;

    @SerializedName("feed")
    public int feed;

    @SerializedName("social")
    public int social;


}