package com.studysiba.controller;
import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.service.common.CommonService;
import com.studysiba.service.study.StudyService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j
@Controller
@RequestMapping("/study")
public class StudyController {

    @Autowired
    CommonService commonService;

    @Autowired
    StudyService studyService;

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

        List<StudyVO> studyList = studyService.getStudyList(pageVO);
        model.addAttribute("study",studyList);
        return location;
    }

    @GetMapping("/view")
    public String moveView(Model model, @ModelAttribute Criteria criteria, @RequestParam("no") int no) throws Exception {
        String menu = "study";
        String location = "/"+menu+"/view";
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        StudyVO studyVO = studyService.getStudyOne(no);
        model.addAttribute("study",studyVO);
        log.info(studyVO);
        return location;
    }


    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<StateVO> registerStudy(Model model, @ModelAttribute Criteria criteria, @ModelAttribute StudyVO studyVO) throws Exception {
        StateVO stateVO = studyService.registerStudy(studyVO);
        log.info(stateVO);
        return stateVO.getStateCode().equals("STUDY_REGISTER_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
