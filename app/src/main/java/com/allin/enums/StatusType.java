package com.allin.enums;

/**
 * Created by harry on 12/8/17.
 */

public enum StatusType {
    NONE(9999),
    INBOX(0),
    LIKE(1),
    ARCHIEVE(2);

    private int statusType;

    StatusType(int statusType) {
        this.statusType = statusType;
    }

    public static StatusType getStatusType(int i) {
        for (StatusType st : values()) {
            if (st.statusType == i)
                return st;
        }
        return NONE;
    }

    public int getStatusType() {
        return this.statusType;
    }

}
