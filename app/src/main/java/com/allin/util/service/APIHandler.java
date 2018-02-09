package com.allin.util.service;

/**
 * Created by harry on 12/5/17.
 */

public class APIHandler {

    public APIHandler() {

    }

    /****** USER METHODS *******/

    public void registerUser() {

    }

    public void loginUser(String username, String password) {

    }

    public void post(String url, int taskId) {

    }

    public class APIManagerHelper {

        /**** AUTHENTICATION ****/
        public static final int LOGIN = 1;
        public static final int LOGOUT = 2;
        public static final int VALIDATE = 3;

        /**** USER ****/
        public static final int USER = 4;
        public static final int USER_ME = 5;
        public static final int USER_ME_AVATAR = 6;
        public static final int USER_ID_PATCH = 7;
        public static final int USER_ID_AVATAR_POST = 8;
        public static final int USER_ID = 9;
        public static final int USER_ID_AVATAR = 10;
        public static final int LEADERBOARD = 11;
        public static final int CONCERN = 12;

        /**** DIGITAL ASSET ****/
        public static final int ASSET = 13;
        public static final int ASSET_ID = 14;
        public static final int ASSET_LIKE = 15;
        public static final int ASSET_ARCHIVE = 16;
        public static final int ASSET_STAR = 17;
        public static final int ASSET_UNSTAR = 18;
        public static final int NOTE_ASSET = 19;
        public static final int PHOTO_ASSET = 20;
        public static final int VIDEO_ASSET = 21;
        public static final int LOGO = 22;
        public static final int NOTE = 23;
        public static final int PHOTO = 24;
        public static final int VIDEO = 25;

        /**** MESSAGE ****/
        public static final int MESSAGE = 26;
        public static final int MESSAGE_READ = 27;

        /**** NEWS ****/
        public static final int NEWS = 28;
        public static final int NEWS_IMAGE = 29;
        public static final int NEWS_READ = 30;


        /**** AUTHENCTICATION ****/
        public static final String URL_LOGIN = "login";


    }

    public interface APIOnLoginCallback {
        public void onLoginSuccess();

        public void onLoginFailed();
    }

    public class ErrorManager {

        public static final int SUCCESS = 0;
        public static final int ERROR_UNKNOWN = 1;
    }

}
