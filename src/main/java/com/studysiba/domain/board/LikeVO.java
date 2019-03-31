package com.studysiba.domain.board;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LikeVO {
    // 좋아요 순번
    private int likeNo;
    // 좋아요 참조 게시글번호
    private int likeFno;
    // 좋아요 누른 회원 아이디
    private String likeId;
    // 좋아요 일자
    private Timestamp likeDate;
}
