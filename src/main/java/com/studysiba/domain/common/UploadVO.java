package com.studysiba.domain.common;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UploadVO {

    private int uldNo;
    private int  uldFno;
    private String uldId;
    private String uldType;
    private String uldText;
    private String uldUuid;
    private String uldFilename;
    private Timestamp uldDate;

}
