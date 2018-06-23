package com.allin.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.events.ImageUpdateEvent;
import com.allin.events.NewsCountUpdateEvent;
import com.allin.events.NotificationCountUpdateEvent;
import com.allin.events.ProfileUpdateEvent;
import com.allin.events.VideoUploadEvent;
import com.allin.imagepicker.GifSizeFilter;
import com.allin.imagepicker.Matisse;
import com.allin.imagepicker.MimeType;
import com.allin.imagepicker.engine.impl.GlideEngine;
import com.allin.imagepicker.filter.Filter;
import com.allin.imagepicker.internal.entity.CaptureStrategy;
import com.allin.response.ResponseFailInfo;
import com.allin.response.UserInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.util.Utils;
import com.allin.view.CircleImageView;
import com.allin.view.CircularProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.marchinram.rxgallery.RxGallery;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements OnClickListener, OnViewMethodInterface {

    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_PERMISSION_CAMERA = 2;
    public static final int REQUEST_PERMISSION_CAMERA_WRITE_EXTERNAL_STORAGE = 3;
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_VIDEO = 4;
    public static final int REQUEST_CODE_IMAGE = 3;

    private ImageView ivSetting;
    private CircleImageView civProfilePhoto;
    private TextView tvUserName;
    private CircularProgressBar circularProgressBar;
    private TextView tvMonthlyGoal;
    private TextView tvNotifications;
    private TextView tvNewsUpdate;
    private LinearLayout llPhoto;
    private LinearLayout llVideo;
    private LinearLayout llNote;
    private TextView tvFeeds;
    private TextView tvLeaderboard;
    private TextView tvMoreToGo;
    private TextView tvAssetCount;
    private ImageView ivCompanyLogo;
    private CompositeDisposable subscription;
    private TextView tvMonthlyGoalText;
    private TextView tvParticaipatorText;
    private TextView tvNotificationText;
    private TextView tvNewsUpdateText;
    private TextView tvPhotoText;
    private TextView tvVideoText;
    private TextView tvNoteText;
    private TextView tvParticipatorValue;

    private void setFonts() {
        tvFeeds.setTypeface(fontUtils.getMontserratRegular());
        tvUserName.setTypeface(fontUtils.getMontserratRegular());
        tvMoreToGo.setTypeface(fontUtils.getMontserratRegular());
        tvNoteText.setTypeface(fontUtils.getMontserratRegular());
        tvPhotoText.setTypeface(fontUtils.getMontserratRegular());
        tvVideoText.setTypeface(fontUtils.getMontserratRegular());
        tvAssetCount.setTypeface(fontUtils.getMontserratRegular());
        tvNewsUpdate.setTypeface(fontUtils.getMontserratRegular());
        tvMonthlyGoal.setTypeface(fontUtils.getMontserratRegular());
        tvLeaderboard.setTypeface(fontUtils.getMontserratRegular());
        tvNotifications.setTypeface(fontUtils.getMontserratRegular());
        tvNewsUpdateText.setTypeface(fontUtils.getMontserratRegular());
        tvMonthlyGoalText.setTypeface(fontUtils.getMontserratRegular());
        tvNotificationText.setTypeface(fontUtils.getMontserratRegular());
        tvParticipatorValue.setTypeface(fontUtils.getMontserratRegular());
        tvParticaipatorText.setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivity(this);

        subscription = new CompositeDisposable();

        initViews();

        SharedPreferences prefs = PreferenceManager.getSharedPref(mContext);

        prefs.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(
                            SharedPreferences prefs, String key) {
                        setDataFromPreference();
                    }
                });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Timber.e("MEASURE WIDTH : " + displayMetrics.widthPixels + " : " + Utils.pxToDp(displayMetrics.widthPixels)
                + " : " + displayMetrics.xdpi + " : " + displayMetrics.density
                + " : " + displayMetrics.densityDpi + " : " + displayMetrics.scaledDensity);
        Timber.e("MEASURE HEIGHT : " + displayMetrics.heightPixels + " : " + Utils.pxToDp(displayMetrics.heightPixels) + " : " + displayMetrics.ydpi + " : " + " : " + " : ");

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        Timber.e("MEASURE NEW : " + width + " : " + height);

        String displaySize = "MEASURE WIDTH : " + displayMetrics.widthPixels + " : " + Utils.pxToDp(displayMetrics.widthPixels)
                + " : " + displayMetrics.xdpi + " : " + displayMetrics.density
                + " : " + displayMetrics.densityDpi + " : " + displayMetrics.scaledDensity
                + " ::: " + "MEASURE HEIGHT : " + displayMetrics.heightPixels + " : " + Utils.pxToDp(displayMetrics.heightPixels) + " : " + displayMetrics.ydpi
                + " ::: " + "MEASURE NEW : " + width + " : " + height;
//        messages.showOnlyMessage("DISPLAY", "" + displaySize);

        setData();

    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initToolbar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationAPI();
        getNewsAPI();
        getUserData();
        subscription.add(Observable.interval(10000, 60000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getNotificationAPI();
                        getNewsAPI();
                        getUserData();
                    }
                }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
//        subscription.dispose();

        subscription.clear();


    }

    @Override
    public void initViews() {
        ivSetting = findViewById(R.id.iv_setting);
        civProfilePhoto = findViewById(R.id.civ_profile_photo);
        tvUserName = findViewById(R.id.tv_user_name);
        circularProgressBar = findViewById(R.id.circular_progress_bar);
        tvMonthlyGoal = findViewById(R.id.tv_monthly_goal);
        tvNotifications = findViewById(R.id.tv_notifications);
        tvNewsUpdate = findViewById(R.id.tv_news_update);
        llPhoto = findViewById(R.id.ll_photo);
        llVideo = findViewById(R.id.ll_video);
        llNote = findViewById(R.id.ll_note);
        tvFeeds = findViewById(R.id.tv_feeds);
        tvLeaderboard = findViewById(R.id.tv_leaderboard);
        tvAssetCount = findViewById(R.id.tv_asset_count);
        tvMoreToGo = findViewById(R.id.tv_more_to_go);
        ivCompanyLogo = findViewById(R.id.iv_company_logo);
        tvMonthlyGoalText = findViewById(R.id.tv_monthly_goal_text);
        tvParticaipatorText = findViewById(R.id.tv_participator_score_text);
        tvNotificationText = findViewById(R.id.tv_notification_text);
        tvNewsUpdateText = findViewById(R.id.tv_news_update_text);
        tvPhotoText = findViewById(R.id.tv_photo_text);
        tvVideoText = findViewById(R.id.tv_video_text);
        tvNoteText = findViewById(R.id.tv_note_text);
        tvParticipatorValue=findViewById(R.id.tv_participator_score);

        setFonts();
        setEvents();
    }

    @Override
    public void setEvents() {
        ivSetting.setOnClickListener(this);
        tvFeeds.setOnClickListener(this);
        tvLeaderboard.setOnClickListener(this);
        llPhoto.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llNote.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        tvNewsUpdate.setOnClickListener(this);
    }

    @Override
    public void setData() {
//        circularProgressBar.setProgress(progress);

        //Border Color
//        circularProgressBar.setProgressBarWidth(19 * getResources().getDisplayMetrics().density);
        //Background Border
//        circularProgressBar.setBackgroundProgressBarWidth(progress * getResources().getDisplayMetrics().density);
        //Color
//        circularProgressBar.setColor(color);
//        circularProgressBar.setBackgroundColor(adjustAlpha(color, 0.3f));

        Log.e("TOKEN", "" + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN));
        Log.e("COMPANY ID", "" + PreferenceManager.getInt(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_COMPANY_ID));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
//                .override(Target.SIZE_ORIGINAL, R.dimen.logo_height);
        Glide.with(mContext)
                .load(new GlideUrl(Constants.URLs.URL_GET_LOGO, new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .apply(options)
                .into(ivCompanyLogo);

        setDataFromPreference();
    }

    private void setDataFromPreference() {
        setNotificationCount();
        setNewsUpdateCount();

        int assetCount = PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_ASSET_COUNT);
        int assetGoal = PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_ASSET_GOAL);

        tvUserName.setText("" + PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_DISPLAY_NAME));

        int status = assetCount - assetGoal;
        if (status == 0) {
            tvMonthlyGoal.setText(MyApplication.getMyApplcation().getStringResource(R.string.achieved));
            circularProgressBar.setColor(MyApplication.getMyApplcation().getColorResource(R.color.green));
            tvMoreToGo.setText(MyApplication.getMyApplcation().getStringResource(R.string.keep_going));
        } else if (status > 0) {
            tvMonthlyGoal.setText("+" + status);
            circularProgressBar.setColor(MyApplication.getMyApplcation().getColorResource(R.color.green));
            tvMoreToGo.setText(MyApplication.getMyApplcation().getStringResource(R.string.keep_going));
        } else {
            tvMonthlyGoal.setText("" + (assetGoal - assetCount));
            circularProgressBar.setColor(MyApplication.getMyApplcation().getColorResource(R.color.dark_blue));
            tvMoreToGo.setText(MyApplication.getMyApplcation().getStringResource(R.string.more_to_go));
        }
        tvAssetCount.setText(assetCount + "/" + assetGoal);

        circularProgressBar.setProgressWithAnimation((int) ((assetCount * 100) / assetGoal));

        tvParticipatorValue.setText(""+PreferenceManager.getInt(mContext,Constants.PrefsKey.PREFS_SCORE));

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

    private void setNotificationCount() {
        if (PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_NOTIFICATION_COUNT) == 0) {
            tvNotifications.setText("0");
            tvNotifications.setBackgroundResource(R.drawable.bg_circular_dark_red);
        } else {
            tvNotifications.setText("" + PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_NOTIFICATION_COUNT));
            tvNotifications.setBackgroundResource(R.drawable.bg_circular_green);
        }
    }

    private void setNewsUpdateCount() {
        if (PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_NEWS_COUNT) == 0) {
            tvNewsUpdate.setText("0");
            tvNewsUpdate.setBackgroundResource(R.drawable.bg_circular_dark_red);
        } else {
            tvNewsUpdate.setText("" + PreferenceManager.getInt(mContext, Constants.PrefsKey.PREFS_NEWS_COUNT));
            tvNewsUpdate.setBackgroundResource(R.drawable.bg_circular_green);
        }
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_setting:
                SettingsActivity.startActivity(mContext);
                break;
            case R.id.tv_notifications:
                NotificationActivity.startActivity(mContext);
                break;
            case R.id.tv_news_update:
                NewsUpdateActivity.startActivity(mContext);
                break;
            case R.id.ll_photo:
//                Toast.makeText(mContext, "Under Development.", Toast.LENGTH_SHORT).show();
                checkPermission();
                break;
            case R.id.ll_video:
//                Toast.makeText(mContext, "Under Development.", Toast.LENGTH_SHORT).show();
                checkStoragePermission();
                break;
            case R.id.ll_note:
//                Toast.makeText(mContext, "Under Development.", Toast.LENGTH_SHORT).show();
                AddNoteActivity.startActivity(mContext);
                break;
            case R.id.tv_feeds:
                FeedActivity.startActivity(mContext);
                break;
            case R.id.tv_leaderboard:
                LeaderboardActivity.startACtivity(mContext);
                break;
        }
    }

    private void startPhotoActivity() {
        try {
            Matisse.from(MainActivity.this)
                    .choose(MimeType.ofImage())
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "com.allin.fileprovider"))
                    .maxSelectable(5)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_IMAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            String caption = "";
            if (data.hasExtra(Constants.Extras.EXTRA_CAPTION)) {
                caption = data.getStringExtra(Constants.Extras.EXTRA_CAPTION);
            }
            if (mSelected.size() > 0) {
                uploadPhoto(caption);
            }
        }
    }

    private void startGalleryForVideo() {
        RxGallery.gallery(this, false, RxGallery.MimeType.VIDEO)
                .subscribe(new Consumer<List<Uri>>() {
                    @Override
                    public void accept(List<Uri> uris) throws Exception {
                        if (uris != null && uris.size() > 0) {
                            AddVideoActivity.startActivity(mContext, uris.get(0).toString());
                        } else {
                            Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ADD_VIDEO_ACTIVITY", throwable.getMessage());
                        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CAMERA_WRITE_EXTERNAL_STORAGE);
        } else if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
        } else if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            startPhotoActivity();
        }
    }

    private void checkStoragePermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startGalleryForVideo();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_VIDEO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPhotoActivity();
            } else {
                messages.showOnlyMessage(getString(R.string.permission_denied), getString(R.string.error_permision_denied_external_storage));
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPhotoActivity();
            } else {
                messages.showOnlyMessage(getString(R.string.permission_denied), getString(R.string.error_permision_denied_camera_storage));
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startPhotoActivity();
            } else {
                messages.showOnlyMessage(getString(R.string.permission_denied), getString(R.string.error_permision_denied_camera_external_storage));
            }
        } else if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_VIDEO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryForVideo();
            } else {
                messages.showOnlyMessage(getString(R.string.permission_denied), getString(R.string.error_permision_denied_external_storage));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageUpdateEvent event) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProfileUpdateEvent event) {
        tvUserName.setText("" + PreferenceManager.getString(mContext, Constants.PrefsKey.PREFS_DISPLAY_NAME));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NotificationCountUpdateEvent event) {
        setNotificationCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsCountUpdateEvent event) {
        setNewsUpdateCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(VideoUploadEvent event) {
        getUserData();
    }

    private void uploadPhoto(String caption) {
        try {
            for (Uri uri : mSelected) {
                callUploadAPI(uri, caption);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callUploadAPI(Uri uri, String caption) throws Exception {
        if (isNetworkAvailable()) {

            File file = new File(Utils.getPath(mContext, uri));
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(uri));

            String filename = "image.";
            filename += Calendar.getInstance().getTimeInMillis();
            filename += ".";
            filename += type;

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Bitmap newBitmap = Utils.resizeImage(bitmap, Constants.IMAGE_MAX_SIZE, true);

            boolean isPNG = false;
            if (type.contains("PNG") || type.contains("png"))
                isPNG = true;
            else if (type.contains("jpeg") || type.contains("JPEG") || type.contains("jpg") || type.contains("JPG"))
                isPNG = false;

            File newFile = Utils.saveImageToExternalStorage(newBitmap, filename, isPNG);

            RequestParams params = new RequestParams();
            params.put(Constants.Params.PARAMS_FILE, filename, (newFile == null ? file : newFile));
            params.put(Constants.Params.PARAMS_CAPTION, caption);

            Timber.e("PHOTO : API_START");
            Timber.e("PHOTO URL : %s", Constants.URLs.URL_ASSET_PHOTO);
            Timber.e("PHOTO PARAMS : %s", params.toString());

            apiManager.postWithAuth(mContext, Constants.URLs.URL_ASSET_PHOTO, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("PHOTO SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            getUserData();
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Timber.e("PHOTO : API_FINISH");
                }

                @Override
                public void onFail(int statusCode, String error) {
                    Timber.e("PHOTO FAIL_RESPONSE : " + statusCode + " : " + error);
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
                    Timber.e("PHOTO : API_FINISH");
                }
            });

        } else {
//            messages.showErrorInConnection();
        }
    }

    private void getUserData() {
        if (isNetworkAvailable()) {
            final RequestParams params = new RequestParams();

            Timber.e("USER_ME : API_START");
            Timber.e("USER_ME URL : %s", Constants.URLs.URL_VALIDATE);
            Timber.e("USER_ME PARAMS : %s", params.toString());

            apiManager.getWithAuth(mContext, Constants.URLs.URL_USER_ME, params, new APIManager.APIResponse() {
                @Override
                public void onSuccess(int statusCode, String result) {
                    Timber.e("USER_ME SUCCESS_RESPONSE : " + statusCode + " : " + result);
                    try {
                        if (statusCode == 200) {
                            UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);
                            PreferenceManager.saveUserData(mContext, userInfo);
                            setDataFromPreference();
                        } else {
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
                    if (statusCode == 401) {
                        try {
                            ResponseFailInfo responseFailInfo = new Gson().fromJson(error, ResponseFailInfo.class);
                            if (responseFailInfo.name.equals(getString(R.string.unauthorized))) {
                                LoginActivity.startActivity(mContext, true);
                                finish();
                            }

                            Timber.e("USER_ME : API_FINISH");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Timber.e("USER_ME : API_FINISH");
                        }
                    }
                }


            });

        }
    }

}
