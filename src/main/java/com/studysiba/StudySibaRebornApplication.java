package com.studysiba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("classpath:com.studysiba.mapper")
public class StudySibaRebornApplication extends ServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(StudySibaRebornApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StudySibaRebornApplication.class);
    }
}
