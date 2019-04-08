package com.studysiba.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
@Log4j
public class MessengerController {


    @MessageMapping("info")
    @SendToUser("/queue/info")
    public String info(String message, SimpMessageHeaderAccessor messageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        log.info(httpSession.getAttribute("id")+ "가 입력함");
        return message;
    }

    @MessageMapping("/chat/{no}")
    @SendTo("/topic/message/{no}")
    public String chat(@DestinationVariable String no, String message, SimpMessageHeaderAccessor messageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) messageHeaderAccessor.getSessionAttributes().get("session");
        System.out.println(httpSession.getAttribute("id")+ " 가 입력함");
        System.out.println(no + "번방 : " + message +" 출력");
        return message;
    }


}
