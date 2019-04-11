package com.studysiba.controller;

import com.studysiba.domain.group.GroupMessageVO;
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


//    @MessageMapping("info")
//    @SendToUser("/queue/info")
//    public String info(String message, SimpMessageHeaderAccessor messageHeaderAccessor) {
//        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
//        log.info(httpSession.getAttribute("id")+ "가 입력함");
//        return message;
//    }

    @Autowired
    MessengerService messengerService;

    /*
     *  그룹 메세지
     *  @Return 그룹메세지 반환
     */
    @MessageMapping("/public")
    @SendTo("/topic/public")
    @ResponseBody
    public ResponseEntity<MessageVO> sendPublicMessage(@RequestBody HashMap<String,String> params, SimpMessageHeaderAccessor messageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        MessageVO messageVO = messengerService.sendPublicMessage(params.get("message"), httpSession);
        return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @MessageMapping("/private/{id}")
    @SendTo("/topic/private/{id}")
    @ResponseBody
    public ResponseEntity<MessageVO> sendPrivateMessage(@DestinationVariable("id") String id, @RequestBody HashMap<String, String> params, SimpMessageHeaderAccessor messageHeaderAccessor){
        log.info(id);
        log.info(params);
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        log.info(httpSession.getAttribute("id"));
        MessageVO messageVO = messengerService.sendPrivateMessage(id, params.get("message"), httpSession);
        return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping(value="/get/{type}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Object> getMessage(@PathVariable("type") String type, @RequestParam(value="id", required = false) String id){
        List<MessageVO> messageList = null;
        MessageVO messageVO = null;
        log.info("대상 >>>>>>>" + id);
        switch (type) {
            case "public" :
                messageList = messengerService.getPublicMessageList(type);
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
        }

        if ( type.equals("public") || type.equals("private") || type.equals("memberlist") ) {
            return messageList != null ? new ResponseEntity<>(messageList,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return messageVO != null ? new ResponseEntity<>(messageVO,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}


