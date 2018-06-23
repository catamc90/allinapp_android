package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.adapter.NotificationAdapter;
import com.allin.adapter.delegate.NotificationDelegate;
import com.allin.adapter.interfaces.OnNotificationEventListener;
import com.allin.response.NotificationInfo;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.util.MyApplication;
import com.allin.view.AToolbar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import timber.log.Timber;

public class NotificationActivity extends BaseActivity implements OnViewMethodInterface, OnNotificationEventListener {

    private AToolbar aToolbar;
    private RecyclerView rvNotification;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationInfo> alData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setActivity(this);

        alData = new ArrayList<>();

        initToolbar();
        initViews();
        setData();

        if (MyApplication.getMyApplcation().newsInfos != null) {
            alData.clear();
            alData.addAll(Arrays.asList(MyApplication.getMyApplcation().notificationInfos));
            Collections.sort(alData, new Comparator<NotificationInfo>() {
                public int compare(NotificationInfo obj1, NotificationInfo obj2) {
                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                        Date date1 = sdf.parse(obj1.createTime);
                        Date date2 = sdf.parse(obj2.createTime);
                        if (date1.compareTo(date2) > 0) {
                            return -1;
                        } else if (date1.compareTo(date2) < 0) {
                            return 1;
                        } else if (date1.compareTo(date2) == 0) {
                            return 0;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            notificationAdapter.notifyDataSetChanged();
        }
        getNotificationAPI();
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, NotificationActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.notifications));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        rvNotification = findViewById(R.id.rv_notification);
        setFonts();
    }

    private void setFonts() {
        aToolbar.getTvTitleCenter().setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void setData() {
        notificationAdapter = new NotificationAdapter.NotificationAdapterBuilder()
                .with(mContext)
                .setData(alData)
                .setNotificationDelegate(new NotificationDelegate())
                .setNotificationEventListener(this)
                .create();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(linearLayoutManager);
        rvNotification.setAdapter(notificationAdapter);
    }


    @Override
    public void onNotificationClick(int position) {
        notificationReadAPI(position);
    }

    private void notificationReadAPI(int position) {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();

            Timber.e("NOTIFICATION READ : API_START");
            Timber.e("NOTIFICATION READ URL : %s", Constants.URLs.URL_ALERTS_READ);
            Timber.e("NOTIFICATION READ PARAMS : %s", params.toString());


            apiManager.patchWithAuth(mContext, String.format(Constants.URLs.URL_ALERTS_READ, alData.get(position).messageId), params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("NOTIFICATION READ SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("NOTIFICATION READ : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("NOTIFICATION READ FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("NOTIFICATION READ : API_FINISH");
                }
            });

        } else {
//            messages.showErrorInConnection();
        }
    }
}
