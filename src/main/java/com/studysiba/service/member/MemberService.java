package com.studysiba.service.member;

import com.studysiba.domain.member.MemberVO;

public interface MemberService {

    /*
     *  회원초대장전송
     *  @Param 아이디
     *  @Return 초대장발송여부
     */
    boolean inviteUser(String mbrId) throws Exception;

    /*
     *  회원비밀번호변경메일발송
     *  @Param 이메일
     *  @Return 메일발송여부
     */
    boolean sendMailPasswordChanger(String mbrEmail) throws Exception;

    /*
     *  회원초대장재전송
     *  @Param 이메일
     *  @Return 초대장발송여부
     */
//    boolean resendEmail(String mbrEmail) throws Exception;

    /*
     *  회원인증확인
     *  @Param 아이디, 코드
     *  @Return 인증상태코드
     */
    String emailAuthentication(String mbrId, String mbrCode);

    /*
     *  회원비밀번호변경인증
     *  @Param 아이디, 코드
     *  @Return 인증상태코드
     */
    String recoveryPassword(String mbrId, String mbrCode);

    /*
     *  회원가입
     *  @Param MemberVO
     *  @Return 절차에따른상태코드반환
     */
    String register(MemberVO memberVO) throws Exception;

    /*
     *  일반회원로그인
     *  @Param MemberVO
     *  @Return 절차에따른상태코드반환
     */
    String normalLoginAuthentication(MemberVO memberVO);

    /*
     *  미승인회원정보삭제
     *  @Param MemberVO
     *  @Return 절차에따른상태코드반환
     */
    String deleteInformation(MemberVO memberVO);

    /*
     *  이메일인증을통한패스워드변경
     *  @Param MemberVO
     *  @Return 절차에따른상태코드반환
     */
    String changePasswordEmailAuth(MemberVO memberVO);

    /*
     *  구글소셜로그인연동
     *  @Param  code
     *  @Return 소셜로그인 상태코드반환
     */
    String googleSignInCallback(String code, String mbrType) throws Exception;

    /*
     *  카카오,페이스북 소셜로그인연동
     *  @Param  MemberVO
     *  @Return 소셜로그인 상태코드반환
     */
    String postSocialSignInCallback(MemberVO memberVO);

    /*
     *  네이버소셜로그인토큰
     *  @Param  code, state
     *  @Return 토큰반환
     */
    String getNaverAccessToken(String code, String state) throws Exception;

    /*
     *  네이버소셜로그인연동
     *  @Param  accessToken
     *  @Return 소셜로그인 상태코드반환
     */
    String naverSignInCallback(String accessToken, String mbrType) throws Exception;

    /*
     *  회원정보변경
     *  @Param  변경타입, 아이디, 변경값
     *  @Return 변경여부에 따른 상태코드반환
     */
    String changeUserInformation(String changeType, String mbrId, String changeValue) throws Exception;

    /*
     *  회원로그아웃
     *  @Param  MemberVO
     *  @Return 로그아웃여부에 따른 상태코드반환
     */
    String userLogout(MemberVO memberVO);

    /*
     *  회원접속정보갱신
     *  @Param mbrId
     *  @Return 회원접속정보갱신여부반환
     */
    boolean isConnectUpdate(String mbrId);
}
