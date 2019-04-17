package com.studysiba.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CommentVO extends BoardVO {
    // 댓글 번호
    private int cmtNo;
    // 댓글이 참조하는 게시글 번호
    private int cmtBno;
    // 댓글 작성 회원 아이디
    private String cmtId;
    // 댓글 내용
    private String cmtContent;
    // 댓글 노출여부
    private int cmtAvailable;
    // 댓글 등록 날짜
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp cmtDate;
    // 댓글 지난시간 변환문자
    private String lastTime;
}
