package com.studysiba.service.common;

import com.studysiba.mapper.common.CommonMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log4j
public class CommonServiceImpl implements CommonService {

    @Resource
    CommonMapper commonMapper;

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


}
