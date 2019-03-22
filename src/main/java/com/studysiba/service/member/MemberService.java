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
     *  회원인증확인
     *  @Param 아이디, 코드
     *  @Return 인증확인여부
     */
    boolean emailAuthentication(String mbrId, String mbrCode);

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
}
