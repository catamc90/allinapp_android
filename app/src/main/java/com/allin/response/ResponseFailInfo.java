package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseFailInfo implements Serializable {

	@SerializedName("code")
	public int code;

	@SerializedName("name")
	public String name;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;
}