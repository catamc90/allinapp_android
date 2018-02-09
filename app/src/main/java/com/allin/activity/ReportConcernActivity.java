package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.view.AToolbar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import timber.log.Timber;

public class ReportConcernActivity extends BaseActivity implements OnViewMethodInterface, View.OnClickListener {

    private AToolbar aToolbar;
    private EditText etMessage;
    private TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_concern);
        setActivity(this);

        initToolbar();
        initViews();

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ReportConcernActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.report_a_concern));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        etMessage = findViewById(R.id.et_message);
        tvSend = findViewById(R.id.tv_send);

        setFonts();
        setEvents();
    }

    private void setFonts() {
        etMessage.setTypeface(fontUtils.getMontserratRegular());
        tvSend.setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvTitleCenter().setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    public void setEvents() {
        tvSend.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_send:
                sendConcernAPI();
                break;
        }
    }

    private void sendConcernAPI() {
        if (isNetworkAvailable()) {
            RequestParams params = new RequestParams();
            params.put("message", "" + etMessage.getText().toString());

            Timber.e("SEND CONCERN : API_START");
            Timber.e("SEND CONCERN URL : %s", Constants.URLs.URL_VALIDATE);
            Timber.e("SEND CONCERN PARAMS : %s", params.toString());
//            showProgress();
            apiManager.postWithAuth(mContext, Constants.URLs.URL_SEND_CONCERN, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("SEND CONCERN SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
//                        dismissProgress();
                        if (statusCode == 200) {
                            onBackPressed();
                        } else {
                            messages.showOnlyMessage("Error", "Something went wrong.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("SEND CONCERN : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("SEND CONCERN FAIL_RESPONSE : " + statusCode + " : " + error);
//                    dismissProgress();
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
                    Timber.e("SEND CONCERN : API_FINISH");
                }
            });
        } else {
            messages.showErrorInConnection();
        }
    }
}
