package com.studysiba;

import com.studysiba.common.SessionListener;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("classpath:com.studysiba.mapper")
public class StudySibaRebornApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudySibaRebornApplication.class, args);
    }
}
