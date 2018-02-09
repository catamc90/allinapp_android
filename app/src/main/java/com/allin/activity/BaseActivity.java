package com.allin.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.allin.R;
import com.allin.events.NewsCountUpdateEvent;
import com.allin.events.NotificationCountUpdateEvent;
import com.allin.response.NewsInfo;
import com.allin.response.NotificationInfo;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.AlertMessages;
import com.allin.util.Constants;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.util.ProgressHUD;
import com.allin.util.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

/**
 * Created by harry on 11/29/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;
    protected APIManager apiManager;
    protected AlertMessages messages;
    protected ProgressHUD pd;
    protected FontUtils fontUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new APIManager(this);
        messages = new AlertMessages(this);
        fontUtils = new FontUtils(this);
        setContext(this);

    }

    protected void setContext(Context context) {
        this.mContext = context;
    }

    protected void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    protected boolean isNetworkAvailable() {
        return Utils.isNetworkAvailable(mContext);
    }

    protected void showProgress() {
        if (pd == null)
            pd = ProgressHUD.show(mContext, "Loading...", true, false);
    }

    protected void dismissProgress() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    protected void getNotificationAPI() {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();

            Timber.e("NOTIFICATION : API_START");
            Timber.e("NOTIFICATION URL : %s", Constants.URLs.URL_MESSAGE);
            Timber.e("NOTIFICATION PARAMS : %s", params.toString());


            apiManager.getWithAuth(mContext, Constants.URLs.URL_MESSAGE, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("NOTIFICATION SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            NotificationInfo[] alNotifications = new Gson().fromJson(result, NotificationInfo[].class);
                            MyApplication.getMyApplcation().notificationInfos = alNotifications;
//                            alData.clear();
//                            alData.addAll(Arrays.asList(alNotifications));
//                            notificationAdapter.notifyDataSetChanged();
                            int count = 0;
                            for (int i = 0; i < MyApplication.getMyApplcation().notificationInfos.length; i++) {
                                NotificationInfo notificationInfo = MyApplication.getMyApplcation().notificationInfos[i];
                                if (notificationInfo.readTime == null)
                                    count++;
                            }
                            PreferenceManager.saveInt(mContext, Constants.PrefsKey.PREFS_NOTIFICATION_COUNT, count);
                            EventBus.getDefault().post(new NotificationCountUpdateEvent("UPDATED"));

                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("NOTIFICATION : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("NOTIFICATION FAIL_RESPONSE : " + statusCode + " : " + error);
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                LoginActivity.startActivity(mContext, true);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Timber.e("NOTIFICATION : API_FINISH");
                }
            });

        } else {
//            messages.showErrorInConnection();
        }
    }

    protected void getNewsAPI() {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();

            Timber.e("NEWS : API_START");
            Timber.e("NEWS URL : %s", Constants.URLs.URL_NEWS);
            Timber.e("NEWS PARAMS : %s", params.toString());


            apiManager.getWithAuth(mContext, Constants.URLs.URL_NEWS, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("NEWS SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            NewsInfo[] newsInfos = new Gson().fromJson(result, NewsInfo[].class);
                            MyApplication.getMyApplcation().newsInfos = newsInfos;
//                            alData.clear();
//                            alData.addAll(Arrays.asList(newsInfos));
//                            newsUpdateAdapter.notifyDataSetChanged();
                            int count = 0;
                            for (int i = 0; i < MyApplication.getMyApplcation().newsInfos.length; i++) {
                                NewsInfo newsInfo = MyApplication.getMyApplcation().newsInfos[i];
                                if (newsInfo.readTime == null)
                                    count++;
                            }
                            PreferenceManager.saveInt(mContext, Constants.PrefsKey.PREFS_NEWS_COUNT, count);
                            EventBus.getDefault().post(new NewsCountUpdateEvent("UPDATED"));
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("NEWS : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("NEWS FAIL_RESPONSE : " + statusCode + " : " + error);
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                LoginActivity.startActivity(mContext, true);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Timber.e("NEWS : API_FINISH");
                }
            });

        } else {
//            messages.showErrorInConnection();
        }
    }

}
