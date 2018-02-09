package com.allin.util;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.allin.BuildConfig;
import com.allin.response.NewsInfo;
import com.allin.response.NotificationInfo;

import timber.log.Timber;

/**
 * Created by harry on 10/2/17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static Context mContext;

    public NotificationInfo[] notificationInfos;
    public NewsInfo[] newsInfos;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        setContext(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public static MyApplication getMyApplcation() {
        return myApplication;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getAppContext() {
        return mContext;
    }

    public String getStringResource(int id) {
        if (mContext == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getString(id);
        }
        return mContext.getResources().getString(id);
    }

    public int getColorResource(int id) {
        if (mContext == null)
            return 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getColor(id);
        }
        return mContext.getResources().getColor(id);
    }

    public Drawable getDrawableResource(int id) {
        if (mContext == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getDrawable(id);
        }
        return mContext.getResources().getDrawable(id);
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    logError(t);
                } else if (priority == Log.WARN) {
                    logWarning(t);
                }
            }
        }
    }

    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    public static void logWarning(Throwable t) {
        // TODO report non-fatal warning.
    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }

}
