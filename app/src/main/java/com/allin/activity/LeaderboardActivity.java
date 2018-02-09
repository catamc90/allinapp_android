package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.adapter.LeaderboardAdapter;
import com.allin.adapter.delegate.LeaderboardDelegate;
import com.allin.adapter.interfaces.OnLeaderboardEventListener;
import com.allin.response.ResponseFailInfo;
import com.allin.response.UserInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.view.AToolbar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Arrays;

import timber.log.Timber;

public class LeaderboardActivity extends BaseActivity implements OnViewMethodInterface, OnLeaderboardEventListener {

    private AToolbar aToolbar;
    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;
    private ArrayList<UserInfo> alData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setActivity(this);

        alData = new ArrayList<>();

        initToolbar();
        initViews();
        setData();

        getLeaderBoardAPI();
    }

    public static void startACtivity(Context mContext) {
        Intent intent = new Intent(mContext, LeaderboardActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.leaderboard));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        rvLeaderboard = findViewById(R.id.rv_leaderboard);

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
        leaderboardAdapter = new LeaderboardAdapter.LeaderboardAdapterBuilder()
                .with(mContext)
                .setData(alData)
                .setLeaderboardDelegate(new LeaderboardDelegate())
                .setLeaderboardEventListener(this)
                .create();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvLeaderboard.setLayoutManager(linearLayoutManager);
        rvLeaderboard.setAdapter(leaderboardAdapter);

    }

    private void getLeaderBoardAPI() {
        if (isNetworkAvailable()) {
            RequestParams params = new RequestParams();

            Timber.e("LEADERBOARD : API_START");
            Timber.e("LEADERBOARD URL : %s", Constants.URLs.URL_VALIDATE);
            Timber.e("LEADERBOARD PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_LEADERBOARD, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("LEADERBOARD SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            UserInfo[] alNotifications = new Gson().fromJson(result, UserInfo[].class);

                            alData.clear();
                            alData.addAll(Arrays.asList(alNotifications));
                            leaderboardAdapter.notifyDataSetChanged();

                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("LEADERBOARD : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("LEADERBOARD FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("LEADERBOARD : API_FINISH");
                }
            });
        }
    }
}
