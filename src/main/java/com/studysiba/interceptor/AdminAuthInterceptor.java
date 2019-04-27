package com.studysiba.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean adminState = true;
        HttpSession httpSession = request.getSession();
        if ( httpSession.getAttribute("auth") == null ) {
            adminState = false;
        } else {
            if ( !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")){
                adminState = false;
            }
        }
        if (!adminState) {
            response.sendRedirect("/?requireAdmin=true");
        }
        return adminState;
    }
}
