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

    @GetMapping(value="/mail/invite/{user}", produces = "application/json; charset=utf8")
    @ResponseBody
    public boolean emailAuthentication(@PathVariable("user") String user) {
        boolean inviteState = false;
        try {
            inviteState = memberService.inviteUser(user);
        } catch ( Exception e ) {
            log.error(e);
            return inviteState;
        }
        log.info("초대장발송여부 : " + inviteState);
        return inviteState;
    }


}
