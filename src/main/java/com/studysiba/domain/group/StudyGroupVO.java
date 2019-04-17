package com.studysiba.domain.group;

import com.studysiba.domain.study.StudyVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class StudyGroupVO extends StudyVO {
    private int stgNo;
    private String stgId;
    private String stgGroup;
    private Timestamp stgDate;
}
