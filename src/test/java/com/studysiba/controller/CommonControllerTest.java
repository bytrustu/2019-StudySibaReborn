package com.studysiba.controller;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Log4j
public class CommonControllerTest {

    @Autowired
    MockMvc mockMvc;

    // MySQL 연동 테스트
    //@Test
    public void testConnection(){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studysiba","bytrustu","dydwns89") ) {
            log.info("mysql 연결 : " + conn);
        } catch ( Exception e ) {
            log.info(e.getMessage());
        }
    }

    // 기본 경로 테스트
    //@Test
    public void getTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
