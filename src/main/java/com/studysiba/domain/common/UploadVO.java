package com.studysiba.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
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
