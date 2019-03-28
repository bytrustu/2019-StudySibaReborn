package com.studysiba.common;

import com.studysiba.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SocialUrlInterceptor implements HandlerInterceptor {

    @Autowired
    CommonService commonService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        if ( httpSession.getAttribute("id") == null ) {
            // 구글 소셜로그인 URL
            String googleUrl = commonService.getGoogleUrl();
            httpSession.setAttribute("googleUrl", googleUrl);

            // 네이버 소셜로그인 URL
            String naverUrl = commonService.getNaverUrl();
            httpSession.setAttribute("naverUrl", naverUrl);
        }
        return true;
    }
}
