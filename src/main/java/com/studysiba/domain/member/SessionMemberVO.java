package com.studysiba.domain.member;

import lombok.Data;

@Data
public class SessionMemberVO {
    private String auth;
    private String type;
    private String id;
    private String nick;
    private String email;
    private String profile;
    private String connect;
    private String visit;
    private String stateCode;
    private int score;
    private int rank;
}
