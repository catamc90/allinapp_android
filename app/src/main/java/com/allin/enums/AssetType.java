package com.allin.enums;

/**
 * Created by harry on 12/8/17.
 */

public enum AssetType {
    NONE(0),
    PHOTO(1),
    VIDEO(2),
    NOTE(3);

    private int assetType;

     AssetType(int assetType) {
        this.assetType = assetType;
    }

    public static AssetType getAssetType(int i) {
        for (AssetType at : values()) {
            if (at.assetType == i)
                return at;
        }
        return NONE;
    }

    public int getAssetValue() {
        return this.assetType;
    }

}
