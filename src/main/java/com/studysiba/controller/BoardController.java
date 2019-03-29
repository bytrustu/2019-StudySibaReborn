package com.studysiba.controller;

import com.studysiba.service.board.BoardService;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Log4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    CommonService commonService;

//    @GetMapping("/{path}")
//    public String moveBoardCommunity(Model model, @PathVariable("path") String path){
//        log.info("move community");
//
//        // 게시판 별 안내 글귀
//        Map<String, String> introComment = commonService.getIntroduceComment(path);
//        model.addAttribute("intro",introComment);
//
//
//        return "/board/list";
//    }

}
