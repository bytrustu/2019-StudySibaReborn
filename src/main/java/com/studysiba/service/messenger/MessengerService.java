package com.studysiba.service.messenger;

import com.studysiba.domain.common.StateVO;
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
     *  전체채팅 마지막 메세지 조회
     *  @return 전체채팅 마지막 메세지 반환
     */
    MessageVO publicLastMessage();

    /*
     *  전체채팅 메세지 리스트 조회
     *  @Param type
     *  @Return 전체채팅 리스트 정보 반환
     */
    List<MessageVO> getPublicMessageList(String type);

    /*
     *  개인채팅 메세지 메세지 전송
     *  @Param type
     *  @Return 개인채팅 메세지 정보 반환
     */
    MessageVO sendPrivateMessage(String id, String message, HttpSession httpSession);

    /*
     *  개인채팅 메세지 리스트 조회
     *  @Param id
     *  @Return 개인채팅 리스트 정보 반환
     */
    List<MessageVO> getPrivateMessageList(String id);

    /*
     *  자신이 보낸 메세지 정보
     *  @Param id
     *  @Return 자신이 보낸 메세지 정보 조회
     */
    MessageVO getSendMessageInfo(String id);

    /*
     *  자신의 멤버 리스트 조회
     *  @Param id
     *  @Return 자신의 멤버 리스트 반환
     */
    List<MessageVO> getPrivateMemberList(String id);

    /*
     *  메세지 읽음 처리
     *  @Param messageVO
     */
    void updateReadMessage(MessageVO messageVO);

    /*
     *  회원확인조회
     *  @Param id
     *  @Return 회원확인조회에 대한 상태코드 반환
     */
    StateVO isMember(String id);

    /*
     *  닉네임으로 아이디 조회
     *  @Param nick
     *  @Return 닉네임으로 통한 아이디 반환
     */
    String convertNickId(String nick);

    /*
     *  개인채팅 비활성화
     *  @Param id
     *  @Return 개인채팅 비활성화 여부 반환
     */
    StateVO disableMember(String id);
}
