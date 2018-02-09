package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.adapter.FeedAdapter;
import com.allin.adapter.delegate.FeedDelegate;
import com.allin.adapter.interfaces.OnFeedEventListener;
import com.allin.enums.AssetType;
import com.allin.enums.StatusType;
import com.allin.response.AssetInfo;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
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

public class FeedActivity extends BaseActivity implements OnViewMethodInterface, OnFeedEventListener {

    private AToolbar aToolbar;
    private RecyclerView rvFeeds;
    private FeedAdapter feedAdapter;
    private ArrayList<AssetInfo> alData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setActivity(this);

        alData = new ArrayList<>();

        initToolbar();
        initViews();
        setData();

        getFeedAPI();
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, FeedActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.feed));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        rvFeeds = findViewById(R.id.rv_feeds);

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
        feedAdapter = new FeedAdapter.FeedAdapterBuilder()
                .with(mContext)
                .setData(alData)
                .setFeedDelegate(new FeedDelegate())
                .setFeedEventListener(this)
                .create();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvFeeds.setLayoutManager(linearLayoutManager);
        rvFeeds.setAdapter(feedAdapter);
    }

    @Override
    public void onClick(int position) {
//        Toast.makeText(mContext, "CLICK : " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavouriteClick(int position, boolean isStar) {
        callFavouriteAPI(isStar, alData.get(position).assetId);
    }

    private void getFeedAPI() {
        if (isNetworkAvailable()) {
            RequestParams params = new RequestParams();
            params.put(Constants.Params.PARAMS_TYPE, AssetType.PHOTO.getAssetValue());
            params.put(Constants.Params.PARAMS_STATUS, StatusType.LIKE.getStatusType());
            params.put(Constants.Params.PARAMS_STARRED, 1);
            params.put(Constants.Params.PARAMS_RANKING, 1);
            params.put(Constants.Params.PARAMS_VOTES, 1);

            Timber.e("FEED : API_START");
            Timber.e("FEED URL : %s", Constants.URLs.URL_GET_ASSET);
            Timber.e("FEED PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_GET_ASSET, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("FEED SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            AssetInfo[] assetInfos = new Gson().fromJson(result, AssetInfo[].class);

                            alData.clear();
                            alData.addAll(Arrays.asList(assetInfos));
                            Collections.sort(alData, new Comparator<AssetInfo>() {
                                public int compare(AssetInfo obj1, AssetInfo obj2) {
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
                            feedAdapter.notifyDataSetChanged();

                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("FEED : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("FEED FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("FEED : API_FINISH");
                }
            });
        }
    }

    private void callFavouriteAPI(Boolean isStar, int assetId) {
        if (isNetworkAvailable()) {
            RequestParams params = new RequestParams();

            String url = null;
            if (isStar)
                url = String.format(Constants.URLs.URL_ASSET_STAR, assetId);
            else
                url = String.format(Constants.URLs.URL_ASSET_UNSTAR, assetId);

            Timber.e("FAVOURITE : API_START");
            Timber.e("FAVOURITE URL : %s", url);
            Timber.e("FAVOURITE PARAMS : %s", params.toString());

            apiManager.patchWithAuth(mContext, url, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("FAVOURITE SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
//                            AssetInfo[] assetInfos = new Gson().fromJson(result, AssetInfo[].class);

//                            alData.clear();
//                            alData.addAll(Arrays.asList(assetInfos));
//                            feedAdapter.notifyDataSetChanged();

                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("FAVOURITE : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("FAVOURITE FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("FAVOURITE : API_FINISH");
                }
            });
        }
    }
}
