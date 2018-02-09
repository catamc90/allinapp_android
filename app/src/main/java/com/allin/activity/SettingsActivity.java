package com.allin.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.events.ImageUpdateEvent;
import com.allin.events.ProfileUpdateEvent;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.AlertMessages;
import com.allin.util.Constants;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.view.AToolbar;
import com.allin.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import timber.log.Timber;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SettingsActivity extends BaseActivity implements OnViewMethodInterface, View.OnClickListener, ImageChooserListener {

    private AToolbar aToolbar;
    private LinearLayout llYourName;
    private LinearLayout llEmail;
    private LinearLayout llPassword;
    private LinearLayout llPhoto;
    private CircleImageView civProfilePhoto;
    private LinearLayout lLReportConcern;
    private LinearLayout llLogout;
    private EditText etYourName;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvYourName;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvYourNameIndicator;
    private TextView tvEmailIndicator;
    private TextView tvPassworIndicator;
    private TextView tvPhotoText;
    private TextView tvReportConcernText;
    private TextView tvLogoutText;

    private boolean isActivityVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setActivity(this);

        isActivityVisible = true;

        initToolbar();
        initViews();
        setData();
//        getProfileData();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.your_profile));
        aToolbar.setToolbarBackButtonVisibility(true);
//        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setToolbarBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileDataAPI();
                Timber.e("PROFILE 1");
                onBackPressed();
                Timber.e("PROFILE 2");
            }
        });
        aToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.black));
    }

    @Override
    public void initViews() {
        llYourName = findViewById(R.id.ll_your_name);
        llEmail = findViewById(R.id.ll_email);
        llPassword = findViewById(R.id.ll_password);
        llPhoto = findViewById(R.id.ll_photo);
        civProfilePhoto = findViewById(R.id.civ_profile_photo);
        lLReportConcern = findViewById(R.id.lL_report_concern);
        llLogout = findViewById(R.id.ll_logout);

        etYourName = findViewById(R.id.et_your_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvYourNameIndicator = findViewById(R.id.tv_your_name_indicator);
        tvEmailIndicator = findViewById(R.id.tv_email_indicator);
        tvPassworIndicator = findViewById(R.id.tv_password_indicator);
        tvYourName = findViewById(R.id.tv_your_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPassword = findViewById(R.id.tv_password);
        tvPhotoText = findViewById(R.id.tv_photo_text);
        tvReportConcernText = findViewById(R.id.tv_report_concern_text);
        tvLogoutText = findViewById(R.id.tv_logout_text);

        setFonts();
        setEvents();
    }

    private void setFonts() {
        etYourName.setTypeface(fontUtils.getMontserratRegular());
        etEmail.setTypeface(fontUtils.getMontserratRegular());
        etPassword.setTypeface(fontUtils.getMontserratRegular());
        tvYourNameIndicator.setTypeface(fontUtils.getMontserratRegular());
        tvEmailIndicator.setTypeface(fontUtils.getMontserratRegular());
        tvPassworIndicator.setTypeface(fontUtils.getMontserratRegular());
        tvYourName.setTypeface(fontUtils.getMontserratRegular());
        tvEmail.setTypeface(fontUtils.getMontserratRegular());
        tvPassword.setTypeface(fontUtils.getMontserratRegular());
        tvPhotoText.setTypeface(fontUtils.getMontserratRegular());
        tvReportConcernText.setTypeface(fontUtils.getMontserratRegular());
        tvLogoutText.setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvTitleCenter().setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    public void setEvents() {
        llYourName.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        llPassword.setOnClickListener(this);
        llPhoto.setOnClickListener(this);
        lLReportConcern.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        tvPassword.setOnClickListener(this);

        etYourName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateProfileDataAPI();
                    return true;
                }
                return false;
            }
        });

        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateProfileDataAPI();
                    return true;
                }
                return false;
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateProfileDataAPI();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void setData() {
        etYourName.setText("" + PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_DISPLAY_NAME));
        etEmail.setText("" + PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_EMAIL));
        etPassword.setText("" + PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_PASSWORD_ORIG));

        tvYourName.setText("" + etYourName.getText().toString());
        tvEmail.setText("" + etEmail.getText().toString());
        tvPassword.setText("" + etPassword.getText().toString());

        if (etYourName.hasFocus() && etYourName.getText().toString().length() > 0)
            etYourName.setSelection(etYourName.getText().toString().length());

        if (etEmail.hasFocus() && etEmail.getText().toString().length() > 0)
            etEmail.setSelection(etEmail.getText().toString().length());

        if (etPassword.hasFocus() && etPassword.getText().toString().length() > 0)
            etPassword.setSelection(etPassword.getText().toString().length());

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.img_profile);
        Glide.with(mContext)
                .load(new GlideUrl(String.format(Constants.URLs.URL_GET_USER_IMAGE, PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_USER_ID)), new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .apply(options)
                .into(civProfilePhoto);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityVisible = false;
    }

    private void getProfileData() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
            if (etYourName.getVisibility() == View.VISIBLE) {
                setYourNameActive(false);
            } else if (etEmail.getVisibility() == View.VISIBLE) {
                setEmailActive(false);
            } else if (etPassword.getVisibility() == View.VISIBLE) {
                setPasswordActive(false);
            }
        }
    }

    private void setEditTextActive(EditText edittext) {
        edittext.requestFocusFromTouch();
        if (edittext.getText().toString().length() > 0)
            edittext.setSelection(edittext.getText().toString().length());
        edittext.setFocusableInTouchMode(true);
        edittext.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edittext, 0);
    }

    private void setYourNameActive(boolean setActive) {
        if (setActive) {
            tvYourNameIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.blue));
            tvYourName.setVisibility(View.GONE);
            etYourName.setVisibility(View.VISIBLE);
            setEmailActive(false);
            setPasswordActive(false);
//            etYourName.performClick();
            setEditTextActive(etYourName);
        } else {
            tvYourNameIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.black));
            if (!tvYourName.getText().toString().equals(etYourName.getText().toString()))
                updateProfileDataAPI();
            tvYourName.setText("" + etYourName.getText().toString());
            tvYourName.setVisibility(View.VISIBLE);
            etYourName.setVisibility(View.GONE);
        }
    }

    private void setEmailActive(boolean setActive) {
        if (setActive) {
            tvEmailIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.blue));
            tvEmail.setVisibility(View.GONE);
            etEmail.setVisibility(View.VISIBLE);
            setYourNameActive(false);
            setPasswordActive(false);
            setEditTextActive(etEmail);
        } else {
            tvEmailIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.black));
            if (!tvEmail.getText().toString().equals(etEmail.getText().toString()))
                updateProfileDataAPI();
            tvEmail.setText("" + etEmail.getText().toString());
            tvEmail.setVisibility(View.VISIBLE);
            etEmail.setVisibility(View.GONE);
        }
    }

    private void setPasswordActive(boolean setActive) {
        if (setActive) {
            tvPassworIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.blue));
            tvPassword.setVisibility(View.GONE);
            etPassword.setVisibility(View.VISIBLE);
            setYourNameActive(false);
            setEmailActive(false);
            setEditTextActive(etPassword);
        } else {
            tvPassworIndicator.setTextColor(MyApplication.getMyApplcation().getColorResource(R.color.black));
            if (!tvPassword.getText().toString().equals(etPassword.getText().toString()))
                updateProfileDataAPI();
            tvPassword.setText("" + etPassword.getText().toString());
            tvPassword.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_your_name:
                setYourNameActive(true);
                break;
            case R.id.ll_email:
                setEmailActive(true);
                break;
            case R.id.ll_password:
            case R.id.tv_password:
                setPasswordActive(true);
                break;
            case R.id.ll_photo:
                setYourNameActive(false);
                setEmailActive(false);
                setPasswordActive(false);
                checkPermission();
                break;
            case R.id.lL_report_concern:
                ReportConcernActivity.startActivity(mContext);
                break;
            case R.id.ll_logout:
                showLogoutDialog();
                break;
        }
    }

    private void showLogoutDialog() {
        messages.showMessage("", getString(R.string.are_you_ready_to_logout), getResources().getString(R.string.logout), getResources().getString(R.string.cancel), "", new AlertMessages.AlertDialogButtonClick() {
            @Override
            public void onButtonClick(String s) {
                if (s.equals(getResources().getString(R.string.logout))) {
                    logoutAPI();
                }
            }
        });
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
        } else {
            showImageSelectionDialog();
        }
    }

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select Option");
        String[] items = new String[]{"Take photo", "Photo Library", "Clear Photo", "Cancel"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        takePicture();
                        break;
                    case 1:
                        chooseImage();
                        break;
                    case 2:
                        clearPhoto();
                        break;
                    case 3:
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearPhoto() {
        civProfilePhoto.setImageResource(R.drawable.img_profile);
        isClearPhoto = true;
        filePath = null;
    }

    private void logoutAPI() {

        if (isNetworkAvailable()) {
            RequestParams params = new RequestParams();

            Timber.e("LOGOUT : API_START");
            Timber.e("LOGOUT URL : %s", Constants.URLs.URL_LOGOUT);
            Timber.e("LOGOUT PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_LOGOUT, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("LOGOUT SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                        } else {
                        }
                        LoginActivity.startActivity(mContext, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("LOGOUT : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("LOGOUT FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("LOGOUT : API_FINISH");
                }
            });
        }
    }

    private void updateProfileDataAPI() {
        if (isNetworkAvailable()) {

            RequestParams params = new RequestParams();
            params.put(Constants.Params.PARAMS_DISPLAY_NAME, etYourName.getText().toString());
            params.put(Constants.Params.PARAMS_EMAIL, etEmail.getText().toString());
            params.put(Constants.Params.PARAMS_PASSWORD, etPassword.getText().toString());

            Timber.e("UPDATE PROFILE : API_START");
            Timber.e("UPDATE PROFILE URL : %s", String.format(Constants.URLs.URL_UPDATE_PROFILE, PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_USER_ID)));
            Timber.e("UPDATE PROFILE PARAMS : %s", params.toString());

            apiManager.patchWithAuth(mContext, String.format(Constants.URLs.URL_UPDATE_PROFILE, PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_USER_ID)), params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("UPDATE PROFILE SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            PreferenceManager.saveString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_DISPLAY_NAME, etYourName.getText().toString());
                            PreferenceManager.saveString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_EMAIL, etEmail.getText().toString());
                            PreferenceManager.saveString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_PASSWORD_ORIG, etPassword.getText().toString());

                            try {
                                if (isActivityVisible)
                                    setData();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);
//                            PreferenceManager.saveUserData(MyApplication.getMyApplcation().getAppContext(), userInfo);
                            EventBus.getDefault().post(new ProfileUpdateEvent("UPDATED"));
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("UPDATE PROFILE : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("UPDATE PROFILE FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("UPDATE PROFILE : API_FINISH");
                }
            });

//            uploadPhoto();
        }
//        onBackPressed();
    }

    private void uploadPhoto() {
        try {
            if (filePath != null) {


                RequestParams params1 = new RequestParams();
                params1.put(Constants.Params.PARAMS_FILE, new File(filePath));

                Timber.e("UPDATE IMAGE : API_START");
                Timber.e("UPDATE IMAGE URL : %s", String.format(Constants.URLs.URL_UPDATE_USER_IMAGE, PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_USER_ID)));
                Timber.e("UPDATE IMAGE PARAMS : %s", params1.toString());

                APIManager apiManagerNew = new APIManager(mContext);
                apiManagerNew.postWithAuth(mContext, String.format(Constants.URLs.URL_UPDATE_USER_IMAGE, PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_USER_ID)), params1, new APIManager.APIResponse() {
                    @Override
                    public void onSuccess(int statusCode, String result) {
                        Timber.e("UPDATE IMAGE SUCCESS_RESPONSE : " + statusCode + " : " + result);
                        try {
                            if (statusCode == 200) {
                                EventBus.getDefault().post(new ImageUpdateEvent("UPDATED"));
                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Timber.e("UPDATE IMAGE : API_FINISH");
                    }

                    @Override
                    public void onFail(int statusCode, String error) {
                        Timber.e("UPDATE IMAGE FAIL_RESPONSE : " + statusCode + " : " + error);
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
                        Timber.e("UPDATE IMAGE : API_FINISH");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private boolean isClearPhoto = false;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED && grantResults[1] == PERMISSION_GRANTED) {
                showImageSelectionDialog();
            } else {
                messages.showOnlyMessage("Permission", "You denied the required permission.");
            }
        } else if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                showImageSelectionDialog();
            } else {
                messages.showOnlyMessage("Permission", "You denied the required permission.");
            }
        } else if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                showImageSelectionDialog();
            } else {
                messages.showOnlyMessage("Permission", "You denied the required permission.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
    }

    private void chooseImage() {

        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, "Lifewatch", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
            Log.e("", "filepathsss" + filePath);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {

        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, "Lifewatch", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    isClearPhoto = false;
                    filePath = image.getFilePathOriginal();
                    Glide.with(SettingsActivity.this)
                            .load(image.getFilePathOriginal())
                            .into(civProfilePhoto);

                }
            }
        });

        if (image != null) {
            uploadPhoto();
        }
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onImageChosen(images.getImage(0));
            }
        });
    }


    @Override
    public void onError(final String reason) {

    }

    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

}
