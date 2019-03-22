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
     *  회원 정보 인증 상태
     *  @Param MemberVO
     *  @Return int
     */
    int informationCheckStatus(MemberVO memberVO);

    /*
     *  회원 활성화 및 코드갱신
     *  @Param MemberVO
     */
    void changeStatus(MemberVO memberVO);

    /*
     *  회원 아이디 중복 확인
     *  @Param MemberVO
     *  @Return int
     */
    int idReduplicationCheck(MemberVO memberVO);

    /*
     *  회원 상태코드 조회
     *  @Param MemberVO
     *  @Return int
     */
    String emailApprovalStatus(MemberVO memberVO);

    /*
     *  회원 정보 등록
     *  @Param MemberVO
     *  @Return int
     */
    int memberRegistration(MemberVO memberVO);

    /*
     *  회원 이메일 중복 확인
     *  @Param MemberVO
     *  @Return int
     */
    int emailReduplicationCheck(MemberVO memberVO);

    /*
     *  회원 닉네임 중복 확인
     *  @Param MemberVO
     *  @Return int
     */
    int nickReduplicationCheck(MemberVO memberVO);

    /*
     *  회원 방문수 조회
     *  @Param MemberVO
     *  @Return int
     */
    int totalVisitCountCheck(MemberVO memberVO);

    /*
     *  회원 정보 조회
     *  @Param MemberVO
     *  @Return MemberVO
     */
    MemberVO viewMemberInformation(MemberVO memberVO);


}
