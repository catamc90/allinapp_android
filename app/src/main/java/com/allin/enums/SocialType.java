package com.allin.enums;

/**
 * Created by harry on 12/8/17.
 */

public enum SocialType {
    NONE(0),
    FACEBOOK(1),
    TWITTER(2),
    INSTAGRAM(4);

    private int socialType;

    SocialType(int socialType) {
        this.socialType = socialType;
    }

    public static SocialType getSocialType(int i) {
        for (SocialType st : values()) {
            if (st.socialType == i)
                return st;
        }
        return NONE;
    }

    public int getSocialType() {
        return this.socialType;
    }
}
