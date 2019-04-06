package com.studysiba.domain.group;

import com.studysiba.domain.study.StudyVO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class StudyGroupVO extends StudyVO {
    private int stgNo;
    private String stgId;
    private String stgGroup;
    private Timestamp stgDate;
}
