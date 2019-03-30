package com.studysiba.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointVO extends MemberVO {
    private int pntNo;
    private String pntId;
    private int pntScore;
    private int pntRank;
}
