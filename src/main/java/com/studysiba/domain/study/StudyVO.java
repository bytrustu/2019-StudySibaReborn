package com.studysiba.domain.study;

import com.studysiba.domain.member.MemberVO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class StudyVO extends MemberVO {
    // 스터디 순번
    private int stdNo;
    // 스터디 리더 아이디
    private String stdId;
    // 스터디 그룹이름
    private String stdGroup;
    // 스터디 주제
    private String stdDivide;
    // 스터디 제목
    private String stdTitle;
    // 스터디 내용
    private String stdContent;
    // 스터디 주소
    private String stdAddress;
    // 스터디 시작일자
    private String stdStart;
    // 스터디 종료일자
    private String stdEnd;
    // 스터디 인원수
    private int stdLimit;
    // 스터디 위도
    private String stdLat;
    // 스터디 경도
    private String stdLng;
    // 스터디 노출여부
    private int stdAvailable;
    // 스터디 갱신일자
    private Timestamp stdUpdate;
    // 스터디 등록일자
    private Timestamp stdReg;
    // 스터디 파일
    private MultipartFile stdFile;

    // 스터디 참여 인원 카운트
    private int stdPersonCount;
}
