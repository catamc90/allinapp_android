package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.util.MyApplication;
import com.allin.view.AToolbar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import timber.log.Timber;

public class AddNoteActivity extends BaseActivity implements OnViewMethodInterface, View.OnClickListener {

    private AToolbar aToolbar;
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setActivity(this);

        initToolbar();
        initViews();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setToolbarBackButtonVisibility(false);

        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getResources().getString(R.string.note));
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));

        aToolbar.setTvLeftVisibility(true);
        aToolbar.setTvLeftTitle(getResources().getString(R.string.cancel));
        aToolbar.setTvLeftColor(getResources().getColor(R.color.blue));
        aToolbar.setTvLeftClickListener(this);

        aToolbar.setTvRightVisibility(true);
        aToolbar.setTvRightTitle(getString(R.string.send));
        aToolbar.setTvRightColor(getResources().getColor(R.color.blue));
        aToolbar.setTvRightClickListener(this);
    }

    @Override
    public void initViews() {
        etNote = (EditText) findViewById(R.id.et_note);

        setFonts();
    }

    private void setFonts() {
        etNote.setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvTitleCenter().setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvLeft().setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvRight().setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvToolbarLeft:
                finish();
                break;

            case R.id.tvToolbarRight:
                sendNote();
                break;
        }
    }

    private void sendNote() {
        if (!etNote.getText().toString().equals("") && etNote.getText().toString().length() > 0) {
            callSendNoteAPI();
            finish();
        } else {
            messages.showOnlyMessage(MyApplication.getMyApplcation().getStringResource(R.string.oops), MyApplication.getMyApplcation().getStringResource(R.string.please_enter_content_to_send));
        }
    }

    private void callSendNoteAPI() {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();
            params.put(Constants.Params.PARAMS_CAPTION, "");
            params.put(Constants.Params.PARAMS_CONTENTS, etNote.getText().toString().trim());

            Timber.e("NOTE : API_START");
            Timber.e("NOTE URL : %s", Constants.URLs.URL_ASSET_NOTE);
            Timber.e("NOTE PARAMS : %s", params.toString());


            apiManager.postWithAuth(mContext, Constants.URLs.URL_ASSET_NOTE, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("NOTE SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("NOTE : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("NOTE FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("NOTE : API_FINISH");
                }
            });

        } else {
//            messages.showErrorInConnection();
        }
    }
}
