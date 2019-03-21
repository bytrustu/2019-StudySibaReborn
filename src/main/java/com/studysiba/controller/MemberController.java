package com.studysiba.controller;

import com.studysiba.service.member.MemberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
@Log4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping(value="/mail/invite/{mbrId}", produces = "application/json; charset=utf8")
    @ResponseBody
    public boolean inviteUser(@PathVariable("mbrId") String mbrId) {
        boolean inviteState = false;
        try {
            inviteState = memberService.inviteUser(mbrId);
        } catch ( Exception e ) {
            log.error(e);
            return inviteState;
        }
        log.info("초대장발송여부 : " + inviteState);
        return inviteState;
    }

    @GetMapping(value="/mail/invite/{mbrId}/{mbrCode}", produces = "application/json; charset=utf8")
    @ResponseBody
    public boolean emailAuthentication(@PathVariable("mbrId") String mbrId, @PathVariable("mbrCode") String mbrCode ) {
        boolean authState = false;
        try {
            authState = memberService.emailAuthentication(mbrId, mbrCode);
        } catch ( Exception e ) {
            log.error(e);
            return authState;
        }
        log.info("인증여부 : " + authState);
        return authState;
    }


}
