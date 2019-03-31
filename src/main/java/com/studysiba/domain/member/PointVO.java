package com.studysiba.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointVO extends MemberVO {
    // 포인트 번호
    private int pntNo;
    // 포인트 아이디
    private String pntId;
    // 포인트 점수
    private int pntScore;
    // 포인트 순위
    private int pntRank;
}
