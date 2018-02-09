package com.allin.util;

/**
 * Created by harry on 12/5/17.
 */

public class Constants {

    public static final int IMAGE_MAX_SIZE=600;
    public final static String APP_PATH_SD_CARD = "/Allin";

    public class URLs {

        public static final String BASE_URL = "https://allinapi.blueheronlabs.net/api/";

        private static final String URL_AUTH = BASE_URL + "auth/";
        private static final String URL_USER = BASE_URL + "user/";
        private static final String URL_ASSET = BASE_URL + "asset/";
        //        private static final String URL_MESSAGE = BASE_URL + "message/";
        private static final String URL_NEWS_BASE = BASE_URL + "news/";


        public static final String URL_LOGIN = URL_AUTH + "login";
        public static final String URL_LOGOUT = URL_AUTH + "logout";
        public static final String URL_VALIDATE = URL_AUTH + "validate";

        public static final String URL_NEWS = BASE_URL + "news";
        public static final String URL_MESSAGE = BASE_URL + "message";
        public static final String URL_GET_ASSET = BASE_URL + "asset";
        public static final String URL_MESSAGE_READ = URL_MESSAGE + "/%s/read";

        public static final String URL_USER_ME = URL_USER + "me";
        public static final String URL_LEADERBOARD = URL_USER + "leaderboard";
        public static final String URL_GET_USER_IMAGE = URL_USER + "%s/avatar";
        public static final String URL_SEND_CONCERN = URL_USER + "concern";
        public static final String URL_UPDATE_PROFILE = URL_USER + "%s";
        public static final String URL_UPDATE_USER_IMAGE = URL_USER + "%s/avatar";

        public static final String URL_NEWS_IMAGE = URL_NEWS_BASE + "%s/image";
        public static final String URL_NEWS_READ = URL_NEWS_BASE + "%s/read";

        public static final String URL_GET_ASSET_IMAGE = URL_ASSET + "photo/";
        public static final String URL_GET_LOGO = URL_ASSET + "logo";
        public static final String URL_ASSET_STAR = URL_ASSET + "%s/star";
        public static final String URL_ASSET_UNSTAR = URL_ASSET + "%s/unstar";
        public static final String URL_ASSET_NOTE = URL_ASSET + "note";
        public static final String URL_ASSET_PHOTO = URL_ASSET + "photo";
        public static final String URL_ASSET_VIDEO = URL_ASSET + "video";

    }

    public class Params {

        public static final String PARAMS_USERNAME = "username";
        public static final String PARAMS_PASSWORD = "password";
        public static final String PARAMS_TYPE = "type";
        public static final String PARAMS_STATUS = "status";
        public static final String PARAMS_STARRED = "starred";
        public static final String PARAMS_VOTES = "votes";
        public static final String PARAMS_RANKING = "ranking";
        public static final String PARAMS_DISPLAY_NAME = "display_name";
        public static final String PARAMS_EMAIL = "email";
        public static final String PARAMS_FILE = "file";
        public static final String PARAMS_CAPTION = "caption";
        public static final String PARAMS_CONTENTS = "contents";
    }

    public class Extras {
        public static final String EXTRA_NEWS_INFO = "news_info";
        public static final String EXTRA_CAPTION = "caption";
    }

    public class PrefsKey {

        public static final String PREFS_ACCESS_TOKEN = "access_token";
        public static final String PREFS_USER_NAME = "username";
        public static final String PREFS_REAL_NAME = "realname";
        public static final String PREFS_IS_MANAGER = "is_manager";
        public static final String PREFS_HAS_ADMIN = "has_admin";
        public static final String PREFS_DISPLAY_NAME = "display_name";
        public static final String PREFS_AVATAR = "avatar";
        public static final String PREFS_EMAIL = "email";
        public static final String PREFS_CREATION_TIME = "create_time";
        public static final String PREFS_USER_ID = "user_id";
        public static final String PREFS_COMPANY_ID = "company_id";
        public static final String PREFS_ASSET_COUNT = "asset_count";
        public static final String PREFS_ROLE = "role";
        public static final String PREFS_ACTIVE = "active";
        public static final String PREFS_ASSET_GOAL = "asset_goal";
        public static final String PREFS_PASSWORD_ORIG = "password";
        public static final String PREFS_UPDATE_TIME = "update_time";
        public static final String PREFS_SCORE = "score";
        public static final String PREFS_NOTIFICATION_COUNT = "notification_count";
        public static final String PREFS_NEWS_COUNT = "news_count";
    }
}
