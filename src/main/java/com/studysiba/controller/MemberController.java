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
    @GetMapping(value = "/mail/invite/{mbrId}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> inviteUser(@PathVariable("mbrId") String mbrId) {
        boolean inviteState = false;
        try {
            inviteState = memberService.inviteUser(mbrId);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("INVITE_STATE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("초대장발송여부 : " + inviteState);
        return inviteState == true ? new ResponseEntity<>("INVITE_STATE_SUCCESS", HttpStatus.OK) : new ResponseEntity<>("INVITE_STATE_ERROR", HttpStatus.OK);
    }

    /*
     *  회원비밀번호변경메일전송
     *  @Param 이메일
     *  @Return 메일발송여부
     */
    @ResponseBody
    @GetMapping(value = "/mail/changepass/{mbrEmail:.+}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> sendMailPasswordChanger(@PathVariable("mbrEmail") String mbrEmail) throws Exception {
        boolean sendState = false;
        sendState = memberService.sendMailPasswordChanger(mbrEmail);
        log.info("패스워드메일발송여부 " + sendState);
        return sendState == true ? new ResponseEntity<>("PASSMAIL_STATE_SUCCESS", HttpStatus.OK) : new ResponseEntity<>("PASSMAIL_STATE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  이메일인증을통한비밀번호변경
     *  @Param MemberVO
     *  @Return 비밀번호변경여부상태코드
     */
    @ResponseBody
    @PutMapping(value = "/mail/changepass/mailpassword", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changePasswordEmailAuth(@RequestBody MemberVO memberVO) {
        String changePasswordState = memberService.changePasswordEmailAuth(memberVO);
        log.info("인증패스워드변경상태 : " + changePasswordState);
        return changePasswordState.equals("PASS_CHANGE_SUCCESS") ?
                new ResponseEntity<>(changePasswordState, HttpStatus.OK) : new ResponseEntity<>(changePasswordState, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  회원초대장인증
     *  @Param 아이디
     *  @Return 초대장인증여부
     */
    @GetMapping(value = "/mail/invite/{mbrId}/{mbrCode}")
    public String emailAuthentication(@PathVariable("mbrId") String mbrId, @PathVariable("mbrCode") String mbrCode) {
        String authState = "";
        try {
            authState = memberService.emailAuthentication(mbrId, mbrCode);
        } catch (Exception e) {
            log.error(e);
        } finally {
            log.info("인증여부 : " + authState);
            return "redirect:/";
        }
    }

    /*
     *  회원비밀번호변경인증
     *  @Param 아이디, 인증코드
     *  @Return 비밀번호메일인증여부
     */
    @GetMapping(value = "/mail/changepass/{mbrId}/{mbrCode}")
    public String recoveryPassword(@PathVariable("mbrId") String mbrId, @PathVariable("mbrCode") String mbrCode) {
        String authState = "";
        authState = memberService.recoveryPassword(mbrId, mbrCode);
        log.info("패스워드변경여부 : " + authState);
        return "redirect:/";
    }


    /*
     *  회원가입
     *  @Param MemberVO
     *  @Return 회원가입절차에따른상태메세지
     */
    @ResponseBody
    @PostMapping(value = "/register", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> register(@RequestBody MemberVO memberVO) throws Exception {
        log.info(memberVO);
        String stateCode = memberService.register(memberVO);
        log.info("회원가입상태메세지 : " + stateCode);
        return stateCode.equals("MEMBER_STATE_SUCCESS") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  회원로그인
     *  @Param MemberVO
     *  @Return 회원가입절차에따른상태메세지
     */
    @ResponseBody
    @PostMapping(value = "/login", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> normalLoginAuthentication(@RequestBody MemberVO memberVO) throws Exception {
        String stateCode = memberService.normalLoginAuthentication(memberVO);
        log.info("회원로그인상태메세지 : " + stateCode);
        if (stateCode.toUpperCase().equals("ID_STATE_WAITAPPROVAL"))
            return new ResponseEntity<>(stateCode, HttpStatus.OK);
        return stateCode.equals("LOGIN_STATE_SUCCESS") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  미승인 회원정보삭제
     *  @Param MemberVO
     *  @Return 회원삭제절차에따른상태메세지
     */
    @ResponseBody
    @PostMapping(value = "/deleteinfo", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> deleteInformation(@RequestBody MemberVO memberVO) throws Exception {
        String stateCode = memberService.deleteInformation(memberVO);
        log.info("미승인회원정보삭제 : " + stateCode);
        return stateCode.equals("INFODEL_STATE_SUCCESS") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  카카오, 네이버 소셜로그인 인증
     *  @Param mbrType, code, state
     *  @Return 소셜로그인절차에따른상태메세지
     */
    @GetMapping(value = "/social/{mbrType}")
    public String socialGetCallback(@PathVariable("mbrType") String mbrType, @RequestParam String code, @RequestParam(value="state", required=false) String state) throws Exception {
        String stateCode = "";
        mbrType = mbrType.toUpperCase();
        if ( mbrType.equals("GOOGLE") ) {
            stateCode = memberService.googleSignInCallback(code, mbrType);
        } else if ( mbrType.equals("NAVER") ) {
            String accessToken = memberService.getNaverAccessToken(code,state);
            stateCode = memberService.naverSignInCallback(accessToken, mbrType);
        }
        log.info(mbrType + "소셜로그인상태코드 : " + stateCode);
        return "redirect:/";
    }

    /*
     *  카카오, 페이스북 소셜로그인 인증
     *  @Param mbrType, MemberVO
     *  @Return 소셜로그인절차에따른상태메세지
     */
    @ResponseBody
    @PostMapping(value = "/social/{mbrType}")
    public ResponseEntity<String> socialPostCallback(@PathVariable("mbrType") String mbrType, @RequestBody MemberVO memberVO) throws Exception {
        memberVO.setMbrType(mbrType.toUpperCase());
        String stateCode = memberService.postSocialSignInCallback(memberVO);
        log.info(memberVO.getMbrType()+"소셜로그인상태코드 : " + stateCode);
        return !stateCode.equals("SOCIAL_JOIN_ERROR") ? new ResponseEntity<>(stateCode, HttpStatus.OK) : new ResponseEntity<>(stateCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  회원정보수정 [ 비밀번호, 닉네임, 프로필사진, 권한 ]
     *  @Param mbrId, changeType, changeValue
     *  @Return 회원정보수정에따른상태메세지
     */
    @ResponseBody
    @PutMapping(value = "/{mbrId}/{changeType}/{changeValue}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changeUserInformation(@PathVariable("mbrId") String mbrId, @PathVariable("changeType") String changeType, @PathVariable("changeValue") String changeValue) {
        String stateCode="";
        try {
            stateCode = memberService.changeUserInformation(changeType, mbrId, changeValue);
            log.info("회원정보변경상태 : " + stateCode);
        } catch ( Exception e ) {
            e.printStackTrace();
            stateCode = "INFORMATION_CHANGE_ERROR";
        } finally {
            return stateCode.equals("INFORMATION_CHANGE_SUCCESS") ? new ResponseEntity<>(stateCode,HttpStatus.OK) : new ResponseEntity<>(stateCode,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     *  회원로그아웃
     *  @Param mbrId, currentUrl
     *  @Return 현재 있었던 회원로그아웃 경로 반환
     */
    @GetMapping(value = "/logout/{mbrId}")
    public String userLogout(@PathVariable("mbrId") String mbrId, @RequestParam(required = false) String currentUrl) {
        log.info(currentUrl);
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(mbrId);
        String stateCode = memberService.userLogout(memberVO);
        log.info("로그아웃상태 : " + stateCode);
        return "redirect:"+currentUrl;
    }










}
