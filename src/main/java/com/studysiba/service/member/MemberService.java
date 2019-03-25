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
}
