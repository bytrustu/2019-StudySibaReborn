package com.studysiba.domain.member;

import com.studysiba.domain.common.UploadVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class MemberVO extends UploadVO {
    // 유저정보 순번
    private int mbrNo;
    // 로그인 타입 ( normal, google, kakao, facebook, naver )
    private String mbrType;
    // 유저 권한 ( normal, admin )
    private String mbrAuth;
    // 인증 코드 ( BCryptPasswordEncdoe 변환 )
    private String mbrCode;
    // 아이디
    private String mbrId;
    // 비밀번호 ( BCryptPasswordEncdoe 로 변환 )
    private String mbrPass;
    // 닉네임
    private String mbrNick;
    // 이메일
    private String mbrEmail;
    // 프로필사진 이름
    private String mbrProfile;
    // 접속시간 로그
    private Timestamp mbrConnect;
    // 가입일자
    private Timestamp mbrJoin;
    // 포인트
    private int mbrPoint;
}
