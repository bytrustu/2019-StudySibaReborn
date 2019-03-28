package com.studysiba.domain.member;

import lombok.Data;

@Data
public class PointVO extends MemberVO {
    private int pntNo;
    private String pntId;
    private int pntScore;
    private int pntRank;
}
