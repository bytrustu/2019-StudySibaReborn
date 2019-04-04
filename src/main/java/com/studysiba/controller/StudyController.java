package com.studysiba.controller;

import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/study")
public class StudyController {

    @Autowired
    CommonService commonService;

    /*
     *  게시판별이동 리스트 [ 공지사항, 커뮤니티, 스터디참여, 스터디그룹 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시판별 list 경로 이동
     */
    @GetMapping("/list")
    public String moveList(Model model, @ModelAttribute Criteria criteria) throws Exception {

        String menu = "study";
        String location = "/"+menu+"/list";

        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        PageVO pageVO = commonService.getPageInfomation(menu, criteria);
        model.addAttribute("page", pageVO);
        model.addAttribute("cri", criteria);

        List<?> commoonList = null;

        return location;
    }

}
