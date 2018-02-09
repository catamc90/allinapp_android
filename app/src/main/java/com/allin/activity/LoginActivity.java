package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.response.LoginInfo;
import com.allin.response.ResponseFailInfo;
import com.allin.response.UserInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.util.PreferenceManager;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import timber.log.Timber;

public class LoginActivity extends BaseActivity implements OnViewMethodInterface {

    public static final String TAG = LoginActivity.class.getName();

    private LinearLayout llContainerLogin;
    private AppCompatTextView tvTitle;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActivity(this);
        Timber.tag(TAG);

        initViews();

        if (PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_ACCESS_TOKEN) != null && !PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_ACCESS_TOKEN).equals("")) {
            validateAccessToken();
        } else {
            llContainerLogin.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void initViews() {
        llContainerLogin = findViewById(R.id.ll_container_login);
        tvTitle = findViewById(R.id.tv_title);
        etUsername = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);
        setEvents();
    }

    @Override
    public void setEvents() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateViews();
            }
        });
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tvLogin.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setData() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            tvTitle.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
//        } else {
//            TextViewCompat.setAutoSizeTextTypeWithDefaults(tvTitle, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
//        }
    }

    private void validateViews() {
        if (etUsername.getText().toString().equals("") || etUsername.getText().toString().length() <= 0) {
            tvTitle.setText(R.string.enter_your_username);
        } else if (etPassword.getText().toString().equals("") || etPassword.getText().toString().length() <= 0) {
            tvTitle.setText(R.string.enter_your_password);
        } else {
            loginAPI();
        }
    }

    private void loginAPI() {
        if (isNetworkAvailable()) {
            final RequestParams params = new RequestParams();
            params.put(Constants.Params.PARAMS_USERNAME, etUsername.getText().toString());
            params.put(Constants.Params.PARAMS_PASSWORD, etPassword.getText().toString());

            showProgress();
            Timber.e("LOGIN : API_START");
            Timber.e("LOGIN_URL : " + Constants.URLs.URL_LOGIN);
            Timber.e("LOGIN_PARAMS : " + params.toString());

            apiManager.post(mContext, Constants.URLs.URL_LOGIN, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("LOGIN_SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    dismissProgress();
                    try {
                        if (statusCode == 200) {
                            LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
                            PreferenceManager.saveLoginData(mContext, loginInfo);
                            PreferenceManager.saveString(mContext, Constants.PrefsKey.PREFS_PASSWORD_ORIG,etPassword.getText().toString());
                            getUserData(true);
//                            MainActivity.startActivity(mContext);
//                            finish();
                        } else {
                            tvTitle.setText(R.string.something_went_wrong_try_again);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Timber.e("LOGIN : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("LOGIN_FAIL_RESPONSE : " + statusCode + " : " + error);
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                tvTitle.setText(R.string.login_failed_enter_your_all_in_username_or_password);
                            }
                            dismissProgress();
                            Timber.e("LOGIN : API_FINISH");
                        } catch (Exception e) {
                            e.printStackTrace();
                            dismissProgress();
                            Timber.e("LOGIN : API_FINISH");
                        }
                    }
                }


            });

        } else {
            tvTitle.setText(R.string.error_no_network);
        }
    }

    private void validateAccessToken() {
        if (isNetworkAvailable()) {
            final RequestParams params = new RequestParams();

            Timber.e("VALIDATE : API_START");
            Timber.e("VALIDATE URL : %s", Constants.URLs.URL_VALIDATE);
            Timber.e("VALIDATE PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_VALIDATE, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("VALIDATE SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
                            PreferenceManager.saveString(mContext, Constants.PrefsKey.PREFS_REAL_NAME, loginInfo.realname);
                            PreferenceManager.saveBoolean(mContext, Constants.PrefsKey.PREFS_IS_MANAGER, loginInfo.isManager);
                            PreferenceManager.saveBoolean(mContext, Constants.PrefsKey.PREFS_HAS_ADMIN, loginInfo.hasAdmin);
                            getUserData(false);
//                            MainActivity.startActivity(mContext);
//                            finish();
                        } else {
                            llContainerLogin.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("VALIDATE : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("VALIDATE FAIL_RESPONSE : " + statusCode + " : " + error);
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                llContainerLogin.setVisibility(View.VISIBLE);
                            }

                            Timber.e("VALIDATE : API_FINISH");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Timber.e("VALIDATE : API_FINISH");
                        }
                    }
                }


            });

        } else {
            llContainerLogin.setVisibility(View.VISIBLE);
        }
    }

    private void getUserData(final boolean isFromLogin) {
        if (isNetworkAvailable()) {
            final RequestParams params = new RequestParams();

            if (isFromLogin)
                showProgress();
            Timber.e("USER_ME : API_START");
            Timber.e("USER_ME URL : %s", Constants.URLs.URL_VALIDATE);
            Timber.e("USER_ME PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_USER_ME, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("USER_ME SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    if (isFromLogin)
                        dismissProgress();
                    try {
                        if (statusCode == 200) {
                            UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);
                            PreferenceManager.saveUserData(mContext, userInfo);

                            MainActivity.startActivity(mContext);
                            finish();
                        } else {
                            llContainerLogin.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dismissProgress();
                    Timber.e("USER_ME : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("USER_ME FAIL_RESPONSE : " + statusCode + " : " + error);
                    if (isFromLogin)
                        dismissProgress();
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                llContainerLogin.setVisibility(View.VISIBLE);
                                tvTitle.setText("" + responseFailInfo.message);
                            }

                            Timber.e("USER_ME : API_FINISH");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Timber.e("USER_ME : API_FINISH");
                        }
                    }
                }


            });

        } else {
            llContainerLogin.setVisibility(View.VISIBLE);
        }
    }

    public static void startActivity(Context mContext, boolean isNotAuthorized) {
//        if (isNotAuthorized)
//            PreferenceManager.clearKey(mContext, Constants.PrefsKey.PREFS_ACCESS_TOKEN);
        PreferenceManager.clearAll(mContext);
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);

    }
}
