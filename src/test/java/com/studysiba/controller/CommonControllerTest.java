package com.studysiba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studysiba.domain.member.MemberVO;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.ModelMap;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    ObjectMapper objectMapper;

    // MySQL 연동 테스트
    //@Test
    public void testConnection() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studysiba", "bytrustu", "dydwns89")) {
            log.info("mysql 연결 : " + conn);
        } catch (Exception e) {
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

    // 초대장 전송 테스트
    //@Test
    public void sendEmailTest() throws Exception {
        mockMvc.perform(get("/member/mail/invite/bytrustu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("INVITE_STATE_SUCCESS"));
    }

    // 초대장 인증 테스트
    @Test
    public void activateAuthentication() throws Exception {
        mockMvc.perform(get("/member/mail/invite/bytrustu/7cbcd0b2-c0be-4f47-8c2c-b87e6582dd38"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("AUTH_STATE_SUCCESS"));
    }

    // 회원가입 테스트
    //@Test
    public void registerTest1() throws Exception {
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId("bytrustu");
        memberVO.setMbrPass("test1234");
        memberVO.setMbrNick("닉네임1");
        memberVO.setMbrEmail("tiamo198712@naver.com");
        memberVO.setMbrProfile("kakao-1.png");
        String memberJson = objectMapper.writeValueAsString(memberVO);
        mockMvc.perform(post("/member/register").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(memberJson))
                .andExpect(status().isOk())
                .andExpect(content().string("MEMBER_STATE_SUCCESS"));

//        mockMvc.perform(post("/member/register")
//                .param("mbrId", "bytrustu")
//                .param("mbrPass", "abcd1234")
//                .param("mbrNick", "닉네임1")
//                .param("mbrEmail", "             ")
//                .param("mbrProfile", "kakao-1.png"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("EMAIL_STATE_EMPTY"));
//
//        mockMvc.perform(post("/member/register")
//                .param("mbrId", "bytrustu")
//                .param("mbrPass", "1234")
//                .param("mbrNick", "닉네임1")
//                .param("mbrEmail", "asd@gmail.com")
//                .param("mbrProfile", "kakao-1.png"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("PASS_STATE_ERROR"));
//
//        mockMvc.perform(post("/member/register")
//                .param("mbrId", "bytrustu")
//                .param("mbrPass", "test1234")
//                .param("mbrNick", "닉네임1")
//                .param("mbrEmail", "asd@gmail.com")
//                .param("mbrProfile", "kakao-1.png"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("MEMBER_STATS_SUCCESS"));
    }


}
