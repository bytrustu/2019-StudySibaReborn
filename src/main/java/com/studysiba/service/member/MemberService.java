package com.studysiba.service.member;

public interface MemberService {

    /*
     *  회원초대장전송
     *  @Param 아이디
     *  @Return 초대장발송여부
     */
    boolean inviteUser(String user) throws Exception;


}
