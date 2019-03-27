package com.studysiba.service.common;

import com.studysiba.config.SocialKeys;
import com.studysiba.mapper.common.CommonMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Service
@Log4j
public class CommonServiceImpl implements CommonService {

    @Resource
    CommonMapper commonMapper;

    @Autowired
    HttpSession httpSession;

    @Autowired
    GoogleConnectionFactory googleConnectionFactory;
    @Autowired
    OAuth2Parameters oAuth2Parameters;
    private OAuth2Operations oAuth2Operations;

    /*
     *  구글 Social Login Url
     */
    @Override
    public String getGoogleUrl() {
        oAuth2Operations = googleConnectionFactory.getOAuthOperations();
        String googleUrl = oAuth2Operations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        return googleUrl;
    }

    @Override
    public String getNaverUrl() {
        String clientId = SocialKeys.getNaverClientId();
        String redirectURI = null;
        try {
            redirectURI = URLEncoder.encode(SocialKeys.getNaverRedirect(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        apiURL += "&client_id=" + clientId;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&state=" + state;
        return apiURL;
    }


}
