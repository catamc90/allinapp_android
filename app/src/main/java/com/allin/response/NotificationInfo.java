package com.allin.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationInfo implements Serializable {

    @SerializedName("r_message_id")
    public int rMessageId;

    @SerializedName("company_id")
    public int companyId;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("read_time")
    public String readTime;

    @SerializedName("subject")
    public String subject;

    @SerializedName("message_id")
    public int messageId;

    @SerializedName("from")
    public String from;

    @SerializedName("body")
    public String body;
}