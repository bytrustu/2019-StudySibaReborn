package com.studysiba.domain.board;

import com.studysiba.domain.member.MemberVO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardVO extends MemberVO {
    // 게시글 번호
    private int brdNo;
    // 게시글 메뉴 타입
    private String brdType;
    // 게시글 말머리 타입
    private int brdDivide;
    // 게시글 아이디
    private String brdId;
    // 게시글 제목
    private String brdTitle;
    // 게시글 내용
    private String brdContent;
    // 게시글 그룹번호
    private int brdGno;
    // 게시글 깊이번호
    private int brdStep;
    // 게시글 들여쓰기번호
    private int brdIndent;
    // 게시글 조회수
    private int brdCount;
    // 게시글 노출여부
    private int brdAvailable;
    // 게시글 등록일자
    private Timestamp brdDate;
    // 좋아요 갯수
    private int brdLikeCount;
    // 댓글 갯수
    private int brdCommentCount;
    // 게시글 지난시간 변환 문자
    private String lastTime;
    // 게시글이 답글인지 여부
    private String isReply;

}
