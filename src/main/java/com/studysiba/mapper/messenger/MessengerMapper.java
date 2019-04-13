package com.studysiba.mapper.messenger;

import com.studysiba.domain.messenger.MessageVO;
import com.studysiba.domain.messenger.RoomVO;
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
     *  @Param  messageVO
     *  @Return  개인채팅방 방번호가 있는지 확인여부 조회
     */
    int checkPrivateRoomId(MessageVO messageVO);

    /*
     *  개인채팅 읽지 않은 메세지 수 조회
     *  @Param  messageVO
     *  @Return  읽지않은 메세지 반환
     */
    int getUnReadCount(MessageVO messageVO);

    /*
     *  개인채팅 메세지 리스트 조회
     *  @Param  messageVO
     *  @Return  개인채팅 메세지 리스트 반환
     */
    ArrayList<MessageVO> getPrivateMessageList(MessageVO messageVO);

    /*
     *  자신이 보낸 메세지 정보
     *  @Param messageVO
     *  @Return 자신이 보낸 메세지 정보 반환
     */
    MessageVO getMessageInfo(MessageVO messageVO);

    /*
     *  자신의 멤버 리스트 조회
     *  @Param id
     *  @Return 자신의 멤버 리스트 반환
     */
    ArrayList<MessageVO> getPrivateMemberList(String id);

    /*
     *  메세지 읽음처리
     *  @Param messageVO
     *  @Return 메세지 읽음처리 여부 반환
     */
    int updateReadMessage(MessageVO messageVO);

    /*
     *  전체채팅 마지막 메세지 조회
     *  @return 전체채팅 마지막 메세지 반환
     */
    MessageVO publicLastMessage();

    /*
     *  회원확인조회
     *  @Param id
     *  @Return 회원확인조회에 대한 상태코드 반환
     */
    int isMember(String id);

    /*
     *  닉네임으로 아이디 조회
     *  @Param nick
     *  @Return 닉네임으로 통한 아이디 반환
     */
    String convertNickId(String nick);

    /*
     *  개인채팅 비활성화
     *  @Param roomVO
     *  @Return 개인채팅 비활성화 여부 반환
     */
    int disableMember(RoomVO roomVO);

    /*
     *  개인채팅 활성화
     *  @Param roomVO
     *  @Return 개인채팅 활성화 여부 반환
     */
    int enableMember(RoomVO roomVO);

    /*
     *  채팅방 정보 조회
     *  @Param roomVO
     *  @Return 채팅방 정보 반환
     */
    RoomVO getRoomInfo(RoomVO roomVO);

    /*
     *  개인채팅 읽지 않은 메세지 카운트 조회
     *  @Param id
     *  @Return 개인채팅 읽지 않은 메세지 카운트 반환
     */
    MessageVO getPrivateUnReadCount(String id);
}
