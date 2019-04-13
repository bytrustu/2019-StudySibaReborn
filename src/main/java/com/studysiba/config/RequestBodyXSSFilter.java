package com.studysiba.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestBodyXSSFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        RequestWrapper requestWrapper = null;
        try{
            requestWrapper = new RequestWrapper(request);
        }catch(Exception e){
            e.printStackTrace();
        }
        chain.doFilter(requestWrapper, response);
    }
}
