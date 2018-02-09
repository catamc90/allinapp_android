package com.allin.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import timber.log.Timber;

/**
 * Created by harry on 10/2/17.
 */

public class APIManager {

    Context context;

    public static final int TIMEOUT = 60 * 1000;

    APIResponse apiResponse;

    public APIManager(Context c) {

        this.context = c;

    }

    public interface APIResponse {

        public void onSuccess(int statusCode, String result);

        public void onFail(int statusCode, String error);

    }

    public void get(Context context, String URL, RequestParams params, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);

        client.get(context, URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void getWithAuth(Context context, String URL, RequestParams params, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);
        client.addHeader("Authorization", "Bearer " + PreferenceManager.getAccessToken(context));

        Timber.d(PreferenceManager.getString(context, Constants.PrefsKey.PREFS_ACCESS_TOKEN));

        client.get(context, URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void post(Context context, String URL, RequestParams params, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);

        client.post(context, URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (UnsupportedEncodingException ue) {
                    apiResponse.onFail(statusCode, "");
                    ue.printStackTrace();
                } catch (Exception e) {
                    apiResponse.onFail(statusCode, "");
                    e.printStackTrace();
                }

            }
        });

    }

    public void post(Context context, String URL, StringEntity entity, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);

        client.post(context, URL, entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (UnsupportedEncodingException ue) {
                    apiResponse.onFail(statusCode, "");
                    ue.printStackTrace();
                } catch (Exception e) {
                    apiResponse.onFail(statusCode, "");
                    e.printStackTrace();
                }

            }
        });

    }

    public void postWithAuth(Context context, String URL, RequestParams params, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);
        client.addHeader("Authorization", "Bearer " + PreferenceManager.getAccessToken(context));

        client.post(context, URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (UnsupportedEncodingException ue) {
                    apiResponse.onFail(statusCode, "");
                    ue.printStackTrace();
                } catch (Exception e) {
                    apiResponse.onFail(statusCode, "");
                    e.printStackTrace();
                }

            }
        });

    }

    public void patchWithAuth(Context context, String URL, RequestParams params, final APIResponse apiResponse) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);
        client.addHeader("Authorization", "Bearer " + PreferenceManager.getAccessToken(context));

        client.patch(context, URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onSuccess(statusCode, content);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {

                    String content = new String(responseBody, "UTF-8");
                    apiResponse.onFail(statusCode, content);

                } catch (UnsupportedEncodingException ue) {
                    apiResponse.onFail(statusCode, "");
                    ue.printStackTrace();
                } catch (Exception e) {
                    apiResponse.onFail(statusCode, "");
                    e.printStackTrace();
                }

            }
        });

    }

}
