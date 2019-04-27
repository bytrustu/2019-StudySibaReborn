package com.studysiba.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.studysiba.common.DataConversion;
import com.studysiba.common.SessionListener;
import com.studysiba.interceptor.AdminAuthInterceptor;
import com.studysiba.interceptor.SocialUrlInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        Resource confiigLocation = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sqlSessionFactory.setConfigLocation(confiigLocation);
        return sqlSessionFactory.getObject();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public SocialUrlInterceptor socialUrlInterceptor(){
        return new SocialUrlInterceptor();
    }

    @Bean
    public AdminAuthInterceptor adminAuthInterceptor() {
        return new AdminAuthInterceptor();
    }

    @Bean
    public FilterRegistrationBean getXssEscapeServletFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/board/*","/study/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean getRequestBodyXSSFileterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RequestBodyXSSFilter());
        registrationBean.addUrlPatterns("/board/*","/study/*");
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(socialUrlInterceptor()).addPathPatterns("/");
        registry.addInterceptor(adminAuthInterceptor()).addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:static/");
//        registry.addResourceHandler("/file/view/**").addResourceLocations("file:C:/upload/studysiba/");
        registry.addResourceHandler("/file/view/**").addResourceLocations(DataConversion.filePath());
    }

}
