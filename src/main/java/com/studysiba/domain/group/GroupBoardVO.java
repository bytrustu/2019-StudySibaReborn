package com.studysiba.domain.group;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class GroupBoardVO extends StudyGroupVO {
    // 공지사항순번
    private int grbNo;
    // 그룹번호
    private int grbGno;
    // 공지사항 작성자
    private String grbId;
    // 공지사항 제목
    private String grbTitle;
    // 공지사항 내용
    private String grbContent;
    // 공지사항 UUID
    private String grbUuid;
    // 공지사항 파일이름
    private String grbFilename;
    // 공지사항 노출여부
    private int grbAvailable;
    // 공지사항 등록일자
    private Timestamp grbDate;
    // 공지사항 파일
    private MultipartFile grbFile;
    // 공지사항 파일업데이트 여부
    private String isUpdateFile;
    // 공지사항 파일 일시 문자 전환
    private String lastTime;
}
