package com.studysiba.mapper.member;

import com.studysiba.domain.member.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    /*
     *  회원 가입 여부 조회
     *  @Param MemberVO
     *  @Return int
     */
    int registrationStatus(MemberVO memberVO);

    /*
     *  회원 타입 값 조회
     *  @Param MemberVO
     *  @Return String
     */
    String getType(MemberVO memberVO);

    /*
     *  회원 이메일 값 조회
     *  @Param MemberVO
     *  @Return String
     */
    String getEmail(MemberVO memberVO);

    /*
     *  회원 코드 값 조회
     *  @Param MemberVO
     *  @Return String
     */
    String getCode(MemberVO memberVO);

    /*
     *  회원 인증코드 갱신
     *  @Param MemberVO
     */
    void renewAuthenticationCode(MemberVO memberVO);

    /*
     *  회원 상태 변경 및 코드갱신
     *  @Param MemberVO
     *  @Return int
     */
    int changeStatus(MemberVO memberVO);
}
