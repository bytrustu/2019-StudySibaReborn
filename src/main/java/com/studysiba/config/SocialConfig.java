package com.studysiba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;


@Configuration
public class SocialConfig {

    @Bean
    public GoogleConnectionFactory googleConnectionFactory(){
        return new GoogleConnectionFactory("532305841689-dn86is98o1i734adhomgukcksp79n2j9.apps.googleusercontent.com", "pjELC2r3_WcMZ8tbj1YaZtgJ");
    }

    @Bean
    public OAuth2Parameters oAuth2Parameters(){
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setScope("https://www.googleapis.com/auth/plus.login");
        oAuth2Parameters.setRedirectUri("http://localhost:8282/member/social/google");
        return oAuth2Parameters;
    }


}
