package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	@SerializedName("access_token")
	public String accessToken;

	@SerializedName("has_admin")
	public boolean hasAdmin;

	@SerializedName("is_manager")
	public boolean isManager;

	@SerializedName("username")
	public String username;

	@SerializedName("realname")
	public String realname;
}