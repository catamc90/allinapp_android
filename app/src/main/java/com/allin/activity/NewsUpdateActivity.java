package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.adapter.NewsUpdateAdapter;
import com.allin.adapter.delegate.NewsUpdateDelegate;
import com.allin.adapter.interfaces.OnNewsUpdateEventListener;
import com.allin.response.NewsInfo;
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

public class NewsUpdateActivity extends BaseActivity implements OnViewMethodInterface, OnNewsUpdateEventListener {

    private AToolbar aToolbar;
    private RecyclerView rvNewsUpdate;
    private NewsUpdateAdapter newsUpdateAdapter;
    private ArrayList<NewsInfo> alData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_update);
        setActivity(this);

        alData = new ArrayList<>();

        initToolbar();
        initViews();
        setData();

        if (MyApplication.getMyApplcation().newsInfos != null) {
            alData.clear();
            alData.addAll(Arrays.asList(MyApplication.getMyApplcation().newsInfos));
            Collections.sort(alData, new Comparator<NewsInfo>() {
                public int compare(NewsInfo obj1, NewsInfo obj2) {
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
            newsUpdateAdapter.notifyDataSetChanged();
        }
        getNewsAPI();
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, NewsUpdateActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.news));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        rvNewsUpdate = findViewById(R.id.rv_news_update);
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

        newsUpdateAdapter = new NewsUpdateAdapter.NewsUpdateAdapterBuilder()
                .with(mContext)
                .setData(alData)
                .setNewsUpdateDelegate(new NewsUpdateDelegate())
                .setNewsUpdateEventListener(this)
                .create();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvNewsUpdate.setLayoutManager(linearLayoutManager);
        rvNewsUpdate.setAdapter(newsUpdateAdapter);
    }

    @Override
    public void onNewsClick(int position) {
        callNewsReadAPI(position);
        NewsDetailActivity.startActivity(mContext, alData.get(position));
    }

    private void callNewsReadAPI(int position) {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();

            Timber.e("NEWS READ : API_START");
            Timber.e("NEWS READ URL : %s", String.format(Constants.URLs.URL_NEWS_READ, alData.get(position).newsId));
            Timber.e("NEWS READ PARAMS : %s", params.toString());

            apiManager.patchWithAuth(mContext, String.format(Constants.URLs.URL_NEWS_READ, alData.get(position).newsId), params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("NEWS READ SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("NEWS READ : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("NEWS READ FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("NEWS READ : API_FINISH");
                }
            });

        }
    }
}
