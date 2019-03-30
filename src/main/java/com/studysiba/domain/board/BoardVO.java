package com.studysiba.domain.board;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardVO {

    private int brdNo;
    private String brdType;
    private int brdDivide;
    private String brdId;
    private String brdTitle;
    private String brdContent;
    private int brdGno;
    private int brdStep;
    private int brdIndent;
    private int brdCount;
    private int brdAvailable;
    private Timestamp brdDate;

}
