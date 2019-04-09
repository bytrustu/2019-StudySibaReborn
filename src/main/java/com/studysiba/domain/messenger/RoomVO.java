package com.studysiba.domain.messenger;

import com.studysiba.domain.member.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomVO extends MemberVO {
    // 메세지 방번호
    private int roomNo;
    // 방 등록 유저1 아이디
    private String roomFrUser;
    // 방 등록 유저1 상태
    private int roomFrState;
    // 방 등록 유저2 아이디
    private String roomSeUser;
    // 방 등록 유저2 상태
    private int roomSeState;
}
