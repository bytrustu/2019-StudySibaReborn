package com.studysiba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

@Configuration
public class SocialConfig {

    @Bean
    public GoogleConnectionFactory googleConnectionFactory(){
        return new GoogleConnectionFactory(SocialKeys.getGoogleClientId(), SocialKeys.getGoogleClientSecret());
    }

    @Bean
    public OAuth2Parameters oAuth2Parameters(){
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setScope("https://www.googleapis.com/auth/plus.login");
        oAuth2Parameters.setRedirectUri(SocialKeys.getGoogleRedirect());
        return oAuth2Parameters;
    }


}
