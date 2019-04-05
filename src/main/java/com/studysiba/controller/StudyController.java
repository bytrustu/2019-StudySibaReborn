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
import org.springframework.http.MediaType;
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
     *  스터디 리스트 이동
     *  @Param criteria
     *  @Return 검색조건에 따른 페이징 및 리스트 반환
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


    /*
     *  스터디 뷰 이동
     *  @Param criteria, no
     *  @Return 스터디등록번호에 따른 정보 반환
     */
    @GetMapping("/view")
    public String moveView(Model model, @ModelAttribute Criteria criteria, @RequestParam("no") int no) throws Exception {
        String menu = "study";
        String location = "/"+menu+"/view";
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        StudyVO studyVO = studyService.getStudyOne(no);
        model.addAttribute("studyView",studyVO);
        log.info(studyVO);
        return location;
    }


    /*
     *  스터디 등록
     *  @Param studyVO
     *  @Return 스터디등록에 따른 상태코드 반환
     */
    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<StateVO> registerStudy(@ModelAttribute StudyVO studyVO) throws Exception {
        StateVO stateVO = studyService.registerStudy(studyVO);
        log.info(stateVO);
        return stateVO.getStateCode().equals("STUDY_REGISTER_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  스터디 정보 조회
     *  @Param no
     *  @Return 스터디등록번에 대한 정보 반환
     */
    @ResponseBody
    @GetMapping(value="/get/{no}",  consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StudyVO> getStudy(@PathVariable("no") int no) {
        StudyVO studyVO = studyService.getStudyOne(no);
        return studyVO != null ? new ResponseEntity<>(studyVO,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  스터디 수정
     *  @Param studyVO
     *  @Return 스터디수정에 따른 상태코드 반환
     */
    @ResponseBody
    @PostMapping(value="/update")
    public ResponseEntity<StateVO> updateStudy(@ModelAttribute StudyVO studyVO) throws Exception {
        StateVO stateVO = studyService.updateStudy(studyVO);
        log.info(stateVO);
        return stateVO.getStateCode().equals("STUDY_UPDATE_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
