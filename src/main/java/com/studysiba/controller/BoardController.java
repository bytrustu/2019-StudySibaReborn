package com.studysiba.controller;

import com.studysiba.service.board.BoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;


    @GetMapping("/community")
    public String moveBoardCommunity(){
        log.info("move community");

        return "/board/list";
    }
}
