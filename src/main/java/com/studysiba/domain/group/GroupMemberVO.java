package com.studysiba.domain.group;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class GroupMemberVO extends StudyGroupVO {
    private int grmNo;
    private int grmGno;
    private String grmId;
    private Timestamp grmDate;
}
