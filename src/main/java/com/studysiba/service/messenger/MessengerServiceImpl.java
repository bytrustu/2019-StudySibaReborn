package com.studysiba.service.messenger;

import com.studysiba.common.DataConversion;
import com.studysiba.domain.messenger.MessageVO;
import com.studysiba.mapper.messenger.MessengerMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Log4j
public class MessengerServiceImpl implements MessengerService {

    @Resource
    MessengerMapper messengerMapper;

    @Autowired
    HttpSession httpSession;

    @Override
    public MessageVO sendPublicMessage(String message, HttpSession session) {
        if ( session.getAttribute("id") == null ) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMsgTo("public");
        messageVO.setMsgFrom("public");
        messageVO.setMsgType("public");
        messageVO.setMsgText(message);
        int isRoomId = messengerMapper.checkPublicRoomId();
        messageVO.setMsgFrom((String) session.getAttribute("id"));
        messageVO = getRoomId(isRoomId, messageVO);

        int messageState = messengerMapper.sendMessage(messageVO);
        if ( messageState == 1 ) {
        messageVO.setMbrNick((String) session.getAttribute("nick"));
        messageVO.setMbrProfile((String) session.getAttribute("profile"));
        messageVO.setMsgDate(DataConversion.currentTimestamp());
        } else { return null; }
        return messageVO;
    }

    /*
     *  전체채팅 메세지 리스트 조회
     *  @Param type
     *  @Return 전체채팅 리스트 정보 반환
     */
    @Override
    public List<MessageVO> getPublicMessageList(String type) {
        return messengerMapper.getPublicMessageList(type);
    }

    /*
     *  개인채팅 메세지 메세지 전송
     *  @Param type
     *  @Return 개인채팅 메세지 정보 반환
     */
    @Override
    public MessageVO sendPrivateMessage(String id, String message, HttpSession session) {
        if ( session.getAttribute("id") == null ) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMsgType("private");
        messageVO.setMsgFrom((String) session.getAttribute("id"));
        messageVO.setMsgTo(id);
        messageVO.setMsgText(message);
        int isRoomId = messengerMapper.checkPrivateRoomId(messageVO);
        messageVO = getRoomId(isRoomId, messageVO);

        int messageState = messengerMapper.sendMessage(messageVO);
        if ( messageState == 1 ) {
            messageVO.setMsgCount(messengerMapper.getUnReadCount(messageVO));
            messageVO.setMbrNick((String) session.getAttribute("nick"));
            messageVO.setMbrProfile((String) session.getAttribute("profile"));
            messageVO.setMsgDate(DataConversion.currentTimestamp());
        }
        return messageVO;
    }

    /*
     *  방생성 여부에 따른 방번호 반환
     *  @Param isRoomId, messageVO
     *  @Return 방생성 여부에 따른 방번호 반환
     */
    private MessageVO getRoomId(int isRoomId, MessageVO messageVO){
        if ( isRoomId == 0 ) {
            messageVO.setMsgRoom(messengerMapper.getMaxRoomId());
            isRoomId = messengerMapper.makeMessageRoom(messageVO);
            if ( isRoomId == 0 ) new Exception();
        } else {
            messageVO.setMsgRoom(messengerMapper.getRoomId(messageVO));
        }
        return messageVO;
    }


    /*
     *  개인채팅 메세지 리스트 조회
     *  @Param id
     *  @Return 개인채팅 리스트 정보 반환
     */
    @Override
    public List<MessageVO> getPrivateMessageList(String id) {
        if ( httpSession.getAttribute("id") == null || id == null ) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMsgFrom(id);
        messageVO.setMsgTo((String) httpSession.getAttribute("id"));
        messageVO.setMsgType("private");
        if ( messengerMapper.checkPrivateRoomId(messageVO) == 0 ) return null;
        messageVO.setMsgRoom(messengerMapper.getRoomId(messageVO));
        List<MessageVO> messgaeList = messengerMapper.getPrivateMessageList(messageVO);
        messengerMapper.updateReadMessage(messageVO);
        return messgaeList;
    }

    /*
     *  자신이 보낸 메세지 정보
     *  @Param id
     *  @Return 자신이 보낸 메세지 정보 조회
     */
    @Override
    public MessageVO getSendMessageInfo(String id) {
        if ( httpSession.getAttribute("id") == null || id == null ) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMsgFrom((String) httpSession.getAttribute("id"));
        messageVO.setMsgTo(id);
        messageVO = messengerMapper.getMessageInfo(messageVO);
        return messageVO;
    }

    /*
     *  자신의 멤버 리스트 조회
     *  @Param id
     *  @Return 자신의 멤버 리스트 반환
     */
    @Override
    public List<MessageVO> getPrivateMemberList(String id) {
        if ( httpSession.getAttribute("id") == null ) return null;
        if ( !httpSession.getAttribute("id").toString().equals(id) ) return null;
        List<MessageVO> messageList = messengerMapper.getPrivateMemberList(id);
        return messageList;
    }
}
