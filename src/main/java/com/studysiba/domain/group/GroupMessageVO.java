package com.studysiba.domain.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class GroupMessageVO extends GroupMemberVO {
    private int grmNo;
    private int grmGno;
    private String grmId;
    private String grmText;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp grmDate;
}
