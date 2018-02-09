package com.allin.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by harry on 2/2/18.
 */

public class FontUtils {

    private Context mContext;

    public FontUtils(Context mContext) {
        this.mContext = mContext;
    }

    public Typeface getMontserratBlack() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Black.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratBold() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Bold.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Typeface getMontserratExtraBold() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_ExtraBold.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratExtraLight() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_ExtraLight.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratLight() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Light.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratMedium() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Medium.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratRegular() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Regular.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratSemiBold() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_SemiBold.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getMontserratThin() {
        try {
            return Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat_Thin.otf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
