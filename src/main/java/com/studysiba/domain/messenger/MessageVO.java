package com.studysiba.domain.messenger;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class MessageVO extends RoomVO {
    // 메세지 순번
    private int msgNo;
    // 메세지 방번호
    private int msgRoom;
    // 메세지 타입
    private String msgType;
    // 메세지 보낸사람
    private String msgFrom;
    // 메세지 받는사람
    private String msgTo;
    // 메세지 내용
    private String msgText;
    // 메세지 수신여부
    private int msgReceive;
    // 메세지 삭제여부
    private int msgDelete;
    // 메세지 보낸시각
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp msgDate;
}
