package com.studysiba.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class UploadVO {
    // 업로드 번호
    private int uldNo;
    // 업로드 참조 게시물 번호
    private int  uldFno;
    // 업로드한 아이디
    private String uldId;
    // 업로드한 테이블 타입
    private String uldType;
    // 업로드 텍스트
    private String uldText;
    // 업로드 임시 생성 문자
    private String uldUuid;
    // 업로드 원본 이름
    private String uldFilename;
    // 업로드 파일 등록 일자
    private Timestamp uldDate;
}
