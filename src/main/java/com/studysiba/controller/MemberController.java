package com.studysiba.controller;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.service.member.MemberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.lang.reflect.Member;

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
    @GetMapping(value="/mail/invite/{mbrId}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> inviteUser(@PathVariable("mbrId") String mbrId) {
        boolean inviteState = false;
        try {
            inviteState = memberService.inviteUser(mbrId);
        } catch ( Exception e ) {
            log.error(e);
            return new ResponseEntity<>("INVITE_STATE_ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("초대장발송여부 : " + inviteState);
        return inviteState == true ? new ResponseEntity<>("INVITE_STATE_SUCCESS", HttpStatus.OK) : new ResponseEntity<>("INVITE_STATE_ERROR",HttpStatus.OK);
    }

    /*
     *  회원초대장인증
     *  @Param 아이디
     *  @Return 초대장인증여부
     */
    @GetMapping(value="/mail/invite/{mbrId}/{mbrCode}")
    public String emailAuthentication(@PathVariable("mbrId") String mbrId, @PathVariable("mbrCode") String mbrCode) {
        String authState = "";
        try {
            authState = memberService.emailAuthentication(mbrId, mbrCode);
        } catch ( Exception e ) {
            log.error(e);
        } finally {
            log.info("인증여부 : " + authState);
            return "redirect:/";
        }
    }

    /*
     *  회원가입
     *  @Param MemberVO
     *  @Return 회원가입절차에따른상태메세지
     */
    @ResponseBody
    @PostMapping( value="/register", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> register(@RequestBody MemberVO memberVO) throws Exception {
        log.info(memberVO);
        String stateCode = memberService.register(memberVO);
        log.info("회원가입상태메세지 : " + stateCode);
        return stateCode.equals("MEMBER_STATE_SUCCESS") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @PostMapping( value="/login", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> normalLoginAuthentication(@RequestBody MemberVO memberVO) throws Exception {
        String stateCode = memberService.normalLoginAuthentication(memberVO);
        log.info("회원로그인상태메세지 : " + stateCode);
        if ( stateCode.toUpperCase().equals("ID_STATE_WAITAPPROVAL") ) return new ResponseEntity<>(stateCode, HttpStatus.OK);
        return stateCode.equals("LOGIN_STATE_SUCCESS") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
