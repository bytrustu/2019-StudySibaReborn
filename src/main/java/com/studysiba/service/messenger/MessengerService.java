package com.studysiba.service.messenger;

import com.studysiba.domain.messenger.MessageVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MessengerService {

    /*
     *  전체채팅 메세지 전송
     *  @Param message, httpSession
     *  @Return 전체채팅 메세지 정보 반환
     */
    MessageVO sendPublicMessage(String message, HttpSession httpSession);

    /*
     *  전체채팅 메세지 리스트 조회
     *  @Param type
     *  @Return 전체채팅 리스트 정보 반환
     */
    List<MessageVO> getPublicMessageList(String type);
}
