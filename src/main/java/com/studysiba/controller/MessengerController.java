package com.studysiba.controller;

import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.messenger.MessageVO;
import com.studysiba.service.messenger.MessengerService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Log4j
@RestController
@RequestMapping("/messenger/*")
public class MessengerController {


    @Autowired
    MessengerService messengerService;

    /*
     *  그룹 메세지
     *  @Param id, params, messageHeaderAccessor
     *  @Return 그룹메세지 반환
     */
    @MessageMapping("/public")
    @SendTo("/topic/public")
    public ResponseEntity<MessageVO> sendPublicMessage(@RequestBody HashMap<String,String> params, SimpMessageHeaderAccessor messageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        MessageVO messageVO = messengerService.sendPublicMessage(params.get("message"), httpSession);
        return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  개인 메세지
     *  @Param params, messageHeaderAccessor
     *  @Return 그룹메세지 반환
     */
    @MessageMapping("/private/{id}")
    @SendTo("/topic/private/{id}")
    public ResponseEntity<MessageVO> sendPrivateMessage(@DestinationVariable("id") String id, @RequestBody HashMap<String, String> params, SimpMessageHeaderAccessor messageHeaderAccessor){
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        MessageVO messageVO = messengerService.sendPrivateMessage(id, params.get("message"), httpSession);
        return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  타입 별 조회 기능
     *  @Param type, id
     *  @Return 타입별 메신저 정보 반환
     */
    @GetMapping(value="/get/{type}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Object> getMessage(@PathVariable("type") String type, @RequestParam(value="id", required = false) String id){
        List<MessageVO> messageList = null;
        MessageVO messageVO = null;
        StateVO stateVO = null;
        switch (type) {
            case "public" :
                messageList = messengerService.getPublicMessageList(type);
                break;
            case "publiclast" :
                messageVO = messengerService.publicLastMessage();
                    break;
            case "private" :
                messageList = messengerService.getPrivateMessageList(id);
                break;
            case "sendinfo" :
                messageVO = messengerService.getSendMessageInfo(id);
                break;
            case "memberlist" :
                messageList = messengerService.getPrivateMemberList(id);
                break;
            case "ismember" :
                stateVO = messengerService.isMember(id);
                break;
            case "unread" :
                messageVO = messengerService.getPrivateUnReadCount(id);
        }
        if ( type.equals("public") || type.equals("private") || type.equals("memberlist") ) {
            return new ResponseEntity<>(messageList,HttpStatus.OK);
        } else if ( type.equals("sendinfo") || type.equals("publiclast") || type.equals("unread") ){
            return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return stateVO != null ? new ResponseEntity<>(stateVO,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     *  메세지 읽음 처리
     *  @Param messageVO
     */
    @PutMapping(value="/update/read", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Void> updateReadMessage(@RequestBody MessageVO messageVO) {
        messengerService.updateReadMessage(messageVO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *  개인 채팅 비활성화
     *  @Param id
     */
    @PutMapping(value="/disable/{id}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Void> disableMember(@PathVariable("id") String id){
        StateVO stateVO = messengerService.disableMember(id);
        return stateVO.getStateCode().contains("SUCCESS") ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  알람 설정 변경
     *  @Param id, state
     */
    @PutMapping(value="/alarm/{id}/{state}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Void> changeAlarmState(@PathVariable("id") String id, @PathVariable("state") String state){
        String stateCode = messengerService.changeAlarmState(id,state);
        return stateCode != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  닉네임으로 아이디 조회
     *  @Param nick
     *  @Return 닉네임으로 통한 아이디 반환
     */
    @GetMapping(value="/convert/{nick}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getMessage(@PathVariable("nick") String nick){
        String id = messengerService.convertNickId(nick);
        return id != null ? new ResponseEntity<>(id,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }





}


