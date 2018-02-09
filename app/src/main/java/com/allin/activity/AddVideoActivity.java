package com.allin.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.events.VideoUploadEvent;
import com.allin.response.ResponseFailInfo;
import com.allin.util.APIManager;
import com.allin.util.Constants;
import com.allin.util.Utils;
import com.allin.videoplayer.EasyVideoCallback;
import com.allin.videoplayer.EasyVideoPlayer;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;

import timber.log.Timber;

public class AddVideoActivity extends BaseActivity implements OnViewMethodInterface, EasyVideoCallback {

    public static String EXTRA_VIDEO_URI = "extra_video_uri";

    private EasyVideoPlayer player;

    private Uri videoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        setActivity(this);

        if (getIntent().hasExtra(EXTRA_VIDEO_URI)) {
            videoUri = Uri.parse(getIntent().getStringExtra(EXTRA_VIDEO_URI));
        }

        initToolbar();
        initViews();
        setData();
    }

    public static void startActivity(Context context, String videoUri) {
        Intent intent = new Intent(context, AddVideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_URI, videoUri);
        context.startActivity(intent);
    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void initViews() {
        player = (EasyVideoPlayer) findViewById(R.id.player);
        player.setVisibility(View.GONE);
        setEvents();
    }

    @Override
    public void setEvents() {
        player.setCallback(this);
    }

    @Override
    public void setData() {
        try {
            player.setVisibility(View.VISIBLE);
            player.setSource(videoUri);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {
    }

    @Override
    public void onPaused(EasyVideoPlayer player) {
    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onPreparing()");
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onPrepared()");
    }

    @Override
    public void onBuffering(int percent) {
        Log.d("EVP-Sample", "onBuffering(): " + percent + "%");
    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        Log.d("EVP-Sample", "onError(): " + e.getMessage());
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        Log.d("EVP-Sample", "onCompletion()");
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {
        finish();
    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {
//        Toast.makeText(mContext, "Video Selected", Toast.LENGTH_SHORT).show();
        callVideoUploadAPI(source);
        finish();
    }

    @Override
    public void onClickVideoFrame(EasyVideoPlayer player) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callVideoUploadAPI(Uri uri) {
        try {
            if (isNetworkAvailable()) {

                File file = new File(Utils.getPath(mContext, uri));
                ContentResolver cR = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String type = mime.getExtensionFromMimeType(cR.getType(uri));

                String filename = "video.";
                filename += Calendar.getInstance().getTimeInMillis();
                filename += type;
                RequestParams params = new RequestParams();
                params.put(Constants.Params.PARAMS_FILE, filename, file);

                Timber.e("VIDEO : API_START");
                Timber.e("VIDEO URL : %s", Constants.URLs.URL_ASSET_VIDEO);
                Timber.e("VIDEO PARAMS : %s", params.toString());


                apiManager.postWithAuth(mContext, Constants.URLs.URL_ASSET_VIDEO, params, new APIManager.APIResponse() {
                    @Override
                    public void onSuccess(int statusCode, String result) {
                        Timber.e("VIDEO SUCCESS_RESPONSE : " + statusCode + " : " + result);
                        try {
                            if (statusCode == 200) {
                                EventBus.getDefault().post(new VideoUploadEvent("UPDATED"));
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Timber.e("VIDEO : API_FINISH");
                    }

                    @Override
                    public void onFail(int statusCode, String error) {
                        Timber.e("VIDEO FAIL_RESPONSE : " + statusCode + " : " + error);
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
                        Timber.e("VIDEO : API_FINISH");
                    }
                });

            } else {
//            messages.showErrorInConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}