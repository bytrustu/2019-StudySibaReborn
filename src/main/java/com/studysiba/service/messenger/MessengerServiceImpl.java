package com.studysiba.service.messenger;

import com.studysiba.common.DataConversion;
import com.studysiba.domain.messenger.MessageVO;
import com.studysiba.mapper.messenger.MessengerMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Log4j
public class MessengerServiceImpl implements MessengerService {

    @Resource
    MessengerMapper messengerMapper;

    @Override
    public MessageVO sendPublicMessage(String message, HttpSession httpSession) {
        if ( httpSession.getAttribute("id") == null ) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMsgTo("public");
        messageVO.setMsgFrom("public");
        messageVO.setMsgType("public");
        messageVO.setMsgText(message);
        int isRoomId = messengerMapper.checkPublicRoomId();
        if ( isRoomId == 0 ) {
            messageVO.setMsgRoom(messengerMapper.getMaxRoomId());
        } else {
            messageVO.setMsgRoom(messengerMapper.getRoomId(messageVO));
        }
        messageVO.setMsgFrom((String) httpSession.getAttribute("id"));
        isRoomId = messengerMapper.makeMessageRoom(messageVO);
        if ( isRoomId == 1 ) {
            messengerMapper.sendMessage(messageVO);
            messageVO.setMbrNick((String) httpSession.getAttribute("nick"));
            messageVO.setMbrProfile((String) httpSession.getAttribute("profile"));
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
}
