package com.studysiba.controller;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.service.member.MemberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Log4j
public class MemberController {

    @Autowired
    MemberService memberService;

    /*
     *  회원초대장전송
     *  @Param 아이디
     *  @Return 초대장발송여부
     */
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

    /*
     *  회원초대장인증
     *  @Param 아이디
     *  @Return 초대장인증여부
     */
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

    /*
     *  회원가입
     *  @Param MemberVO
     *  @Return 회원가입절차에따른상태메세지
     */
    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute MemberVO memberVO) throws Exception {
        String stateCode = memberService.register(memberVO);
        log.info(stateCode);
        return stateCode;
    }


}
