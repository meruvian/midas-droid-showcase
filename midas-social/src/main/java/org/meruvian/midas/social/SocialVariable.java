package org.meruvian.midas.social;

/**
 * Created by ludviantoovandi on 30/09/14.
 */
public class SocialVariable {
    public static final String TWITTER_API_KEY = "HbetTZ7QOBJK62noY7ub45qbS";
    public static final String TWITTER_SECRET_KEY = "iuuxWlYKlsjlEB7n8wOtUsWOv3a70axdScOcIpk9b269clebAj";
    public static final String TWITTER_CALLBACK = "midas://social_login";

    public static final String MERVID_APP_ID = "ffdc6020-4139-46d3-b82c-b31bef98c7ca";
    public static final String MERVID_API_SECRET = "uh6ZNFZEyG+n20ebJ/lhHTjGXdD5FjHg1sMLc6AtEr9DIwcTYmqI/n5KLb5e/Nkj";
    public static final String MERVID_AUTH_URL = "http://merv.id/oauth/authorize";
    public static final String MERVID_REQUEST_TOKEN = "http://merv.id/oauth/token";
    public static final String MERVID_REQUEST_ME = "http://api.merv.id/v1/users/me";
    public static final String MERVID_CALLBACK = "midas://social_login";

    public static final String YAMA_APP_ID = "4c08e503-b818-4591-b46f-e425d78bdeb5";
    public static final String YAMA_API_SECRET = "lyl4Wf4U3JwPtPxaQ1oNTsWUAV5TwaHoqdoegiAa9yduZz1cj0QebY5opn9Y260C";
    public static final String YAMA_AUTH_URL = "http://yama2.meruvian.org/oauth/authorize";
    public static final String YAMA_REQUEST_TOKEN = "http://yama2.meruvian.org/oauth/token";
    public static final String YAMA_REQUEST_ME = "http://yama2.meruvian.org/api/users/me";

    //local
    /*public static final String YAMA_APP_ID = "91a170f2-2c72-4ea9-8a50-8085bb40b5df";
    public static final String YAMA_API_SECRET = "nk6Dg0AA3nDA4AWAdnmp1jCTnCZfnRbK7Kri1n1yc5y9AE9ZLydlMPASVzqPtEnk";
    public static final String YAMA_AUTH_URL = "http://192.168.2.102:8080/oauth/authorize";
    public static final String YAMA_REQUEST_TOKEN = "http://192.168.2.102:8080/oauth/token";
    public static final String YAMA_REQUEST_ME = "http://192.168.2.102:8080/api/users/me";*/

    public static final String YAMA_CALLBACK = "midas://social_login";


    public static final int MERVID_REQUEST_ACCESS = 11;
    public static final int MERVID_REQUEST_TOKEN_TASK = 12;
    public static final int MERVID_REQUEST_ME_TASK = 13;
    public static final int MERVID_REFRESH_TOKEN_TASK = 14;

    public static final int TWITTER_REQUEST_TOKEN_TASK = 21;
    public static final int TWITTER_REQUEST_ACCESS_TASK  = 22;
    public static final int TWITTER_REQUEST_ME_TASK = 23;
    public static final int TWITTER_REFRESH_TOKEN_TASK = 24;

    public static final int GOOGLE_REQUEST_ACCESS = 31;
    public static final int GOOGLE_REQUEST_PLAY_TASK = 32;
    public static final int GOOGLE_REQUEST_TOKEN_TASK = 33;
    public static final int GOOGLE_REFRESH_TOKEN_TASK = 34;

    public static final int FACEBOOK_REQUEST_ME_TASK = 41;
    public static final int FACEBOOK_REQUEST_TOKEN_TASK = 42;
    public static final int FACEBOOK_REQUEST_ACCESS = 43;
    public static final int FACEBOOK_REFRESH_TOKEN_TASK = 44;

    public static final int YAMAID_REQUEST_ACCESS = 51;
    public static final int YAMAID_REQUEST_TOKEN_TASK = 52;
    public static final int YAMAID_REQUEST_ME_TASK = 53;
    public static final int YAMAID_REFRESH_TOKEN_TASK = 54;

}
