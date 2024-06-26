package com.studysiba.mapper.member;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

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

    /*
     *  이메일 업데이트
     *  @Param MemberVO
     *  @Return int
     */
    int updateEmail(MemberVO memberVO);

    /*
     *  미승인 회원 정보 삭제
     *  @Param MemberVO
     *  @Return int
     */
    int deleteInformation(MemberVO memberVO);

    /*
     *   회원 이메일 정보 확인
     *  @Param MemberVO
     *  @Return String
     */
    String checkMailState(MemberVO memberVO);

    /*
     *   이메일 인증을 통한 패스워드 변경
     *  @Param MemberVO
     *  @Return int
     */
    int changePasswordEmailAuth(MemberVO memberVO);

    /*
     *   소셜로그인 등록된 회원인지 상태확인
     *  @Param MemberVO
     *  @Return int
     */
    int socialSignInState(MemberVO memberVO);

    /*
     *   소셜로그인 회원정보 조회
     *  @Param MemberVO
     *  @Return MemberVO
     */
    MemberVO memberSocialInformation(MemberVO memberVO);

    /*
     *   소셜 회원가입
     *  @Param MemberVO
     *  @Return int
     */
    int socialSign(MemberVO memberVO);

    /*
     *  포인트 생성
     *  @Param MemberVO
     *  @Return int
     */
    int createPoint(MemberVO memberVO);

    /*
     *  포인트 증가/감소
     *  @Param MemberVO
     *  @Return int
     */
    int updatePoint(MemberVO memberVO);

    /*
     *  포인트 설정
     *  @Param MemberVO
     *  @Return int
     */
    int setPoint(MemberVO memberVO);

    /*
     *  오늘 접속기록이 있는지 확인조회
     *  @Param MemberVO
     *  @Return int
     */
    int isLoggedToday(MemberVO memberVO);

    /*
     *  접속기록갱신
     *  @Param MemberVO
     *  @Return int
     */
    int updateAccessTime(MemberVO memberVO);

    /*
     *  방문정보등록
     *  @Param MemberVO
     *  @Return int
     */
    int  visitRegistration(MemberVO memberVO);

    /*
     *  유저랭킹조회
     *  @Param MemberVO
     *  @Return PointVO
     */
    PointVO viewUserRanking(MemberVO memberVO);

    /*
     *  유저랭킹 1~3위 조회
     *  @Return List<PointVO>
     */
    List<PointVO> viewUserTotalRanking();

    /*
     *  유저 비밀번호 변경
     *  @Param MemberVO
     *  @Return 비밀번호변경결과값
     */
    int updatePassword(MemberVO memberVO);

    /*
     *  유저 닉네임 변경
     *  @Param MemberVO
     *  @Return 닉네임변경결과값
     */
    int updateNickname(MemberVO memberVO);

    /*
     *  유저 프로필사진 변경
     *  @Param MemberVO
     *  @Return 프로필사진변경결과값
     */
    int updateProfile(MemberVO memberVO);

    /*
     *  회원접속정보갱신
     *  @Param mbrId
     *  @Return 회원접속정보갱신여부반환
     */
    int isConnectUpdate(String mbrId);

    /*
     *  접속중인 멤버 리스트 조회
     *  @Return 접속중인 멤버 리스트 반환
     */
    List<MemberVO> connectedMemberList();

    /*
     *  로그아웃시 접속로그 변경
     *  @Return 로그아웃 접속로그 변경 여부 반환
     */
    int changeLogoutLog(String mbrId);
}
