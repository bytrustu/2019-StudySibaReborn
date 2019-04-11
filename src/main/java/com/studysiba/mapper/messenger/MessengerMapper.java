package com.studysiba.mapper.messenger;

import com.studysiba.domain.messenger.MessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessengerMapper {

    /*
     *  메세지 방번호가 있는지 확인여부 조회
     *  @Param  messageVO
     *  @Return  메세지 방번호가 있는지 확인여부 조회
     */
    int checkRoomId(MessageVO messageVO);

    /*
     *  전체채팅방 방번호가 있는지 확인여부 조회
     *  @Return  전체채팅방 방번호가 있는지 확인여부 조회
     */
    int checkPublicRoomId();

    /*
     *  방번호로 사용할 가장 높은 방번호 반환
     *  @Return  방번호로 사용할 가장 높은 방번호 반환
     */
    int getMaxRoomId();

    /*
     *  방번호 조회
     *  @Param  messageVO
     *  @Return  방번호 반환
     */
    int getRoomId(MessageVO messageVO);

    /*
     *  채팅방 개설
     *  @Param  messageVO
     *  @Return  채팅방 개설되었는지 여부
     */
    int makeMessageRoom(MessageVO messageVO);

    /*
     *  메세지 전송
     *  @Param  messageVO
     *  @Return  메세지 전송 여부 반환
     */
    int sendMessage(MessageVO messageVO);

    /*
     *  전체채팅 메세지 리스트 조회
     *  @Param type
     *  @Return 전체채팅 리스트 정보 반환
     */
    ArrayList<MessageVO> getPublicMessageList(String type);

    /*
     *  개인채팅방 방번호가 있는지 확인여부 조회
     *  @Return  개인채팅방 방번호가 있는지 확인여부 조회
     */
    int checkPrivateRoomId(MessageVO messageVO);

    /*
     *  개인채팅 읽지 않은 메세지 수 조회
     *  @Return  읽지않은 메세지 반환
     */
    int getUnReadCount(MessageVO messageVO);
}
