package com.studysiba.config;

public class SocialKeys {

     static String googleRedirect ="https://localhost:8282/member/social/google";
     static String googleClientId = "532305841689-dn86is98o1i734adhomgukcksp79n2j9.apps.googleusercontent.com";
     static String googleClientSecret = "pjELC2r3_WcMZ8tbj1YaZtgJ";

     static  String naverRedirect = "https://localhost:8282/member/social/naver";
     static String naverClientId = "mUtZ27Xzb4B0TFi2cOkn";
     static String naverClientSecret = "vENWTzSie_";

    public static String getGoogleRedirect() {
        return googleRedirect;
    }

    public static String getGoogleClientId() {
        return googleClientId;
    }

    public static String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public static String getNaverRedirect() {
        return naverRedirect;
    }

    public static String getNaverClientId() {
        return naverClientId;
    }

    public static String getNaverClientSecret() {
        return naverClientSecret;
    }
}
