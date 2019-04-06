package com.studysiba.domain.group;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GroupMemberVO extends StudyGroupVO {
    private int grmNo;
    private int grmGno;
    private String grmId;
    private Timestamp grmDate;
}
