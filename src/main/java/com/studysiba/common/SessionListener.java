package com.studysiba.common;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.mapper.member.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Log4j
public class SessionListener implements HttpSessionListener {

    @Autowired
    MemberMapper memberMapper;

    /*
     *  홈페이지 방문시 접속기록
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(60*60);
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId("GUEST");
        memberMapper.visitRegistration(memberVO);
    }
}
