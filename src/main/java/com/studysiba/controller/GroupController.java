package com.studysiba.controller;

import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.service.common.CommonService;
import com.studysiba.service.group.GroupService;
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

@Controller
@RequestMapping("/group")
@Log4j
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    StudyService studyService;

    @Autowired
    CommonService commonService;




    /*
     *  그룹 리스트 이동
     *  @Return 참여중인 그룹 리스트 반환
     */
    @GetMapping("/list")
    public String moveList(Model model) throws Exception {

        String menu = "group";
        String location = "/"+menu+"/list";

        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        List<GroupMemberVO> groupList = groupService.getGroupList();
        model.addAttribute("group",groupList);
        log.info(groupList);
        return location;
    }


    /*
     *  그룹 뷰 이동
     *  @Param  no
     *  @Return 스터디그룹번호 따른 정보 반환
     */
    @GetMapping("/view")
    public String moveView(Model model, @RequestParam("no") int no, @ModelAttribute Criteria criteria) throws Exception {

        String menu = "group";
        String location = "/"+menu+"/view";

        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        StudyVO studyVO = studyService.getStudyOne(no);
        model.addAttribute("studyView",studyVO);

        PageVO pageVO = commonService.getGroupPageInfomation(criteria,no);
        model.addAttribute("page", pageVO);

        List<GroupBoardVO> noticeList = groupService.getNoticeList(pageVO,no);
        model.addAttribute("notice",noticeList);

        List<GroupMemberVO> groupMemberList = studyService.getGroupMemberList(no);
        model.addAttribute("groupMember", groupMemberList);

        return location;
    }



    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 작성에 대한 상태코드 반환
     */
    @ResponseBody
    @PostMapping(value = "/notice/write")
    public ResponseEntity<StateVO> writeGroupPost(@ModelAttribute GroupBoardVO groupBoardVO) throws Exception {
        StateVO stateVO = groupService.writeGroupPost(groupBoardVO);
        return stateVO.getStateCode().equals("NOTICE_WRITE_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) : new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  그룹 공지사항 업데이트
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 업데이트 대한 상태코드 반환
     */
    @ResponseBody
    @PostMapping(value = "/notice/update")
    public ResponseEntity<StateVO> updateGroupPost(@ModelAttribute GroupBoardVO groupBoardVO) throws Exception {
        StateVO stateVO = groupService.updateGroupPost(groupBoardVO);
        return stateVO.getStateCode().equals("NOTICE_UPDATE_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) : new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 삭제에 대한 상태코드 반환
     */
    @ResponseBody
    @DeleteMapping(value = "/notice/delete", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> deleteGroupPost(@RequestBody GroupBoardVO groupBoardVO) throws Exception {
        StateVO stateVO = groupService.deleteGroupPost(groupBoardVO);
        return stateVO.getStateCode().equals("NOTICE_DELETE_SUCCESS") ?
                new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  그룹 공지사항 조회
     *  @Param  no
     *  @Return 스터디 공지사항 게시글 조회
     */
    @ResponseBody
    @GetMapping(value = "/notice/view/{no}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GroupBoardVO> getGroupPost(@PathVariable("no") int no) throws Exception {
        GroupBoardVO groupBoardVO = groupService.getGroupPost(no);
        return groupBoardVO != null ? new ResponseEntity<>(groupBoardVO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  그룹 탈퇴
     *  @Param  studyGroupVO
     *  @Return 그룹탈퇴에 대한 상태코드 반환
     */
    @ResponseBody
    @DeleteMapping(value = "/out", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> outGroup(@RequestBody GroupMemberVO groupMemberVO) throws Exception {
        StateVO stateVO = groupService.outGroup(groupMemberVO);
        return stateVO.getStateCode().equals("GROUP_OUT_SUCCESS")? new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO,HttpStatus.INTERNAL_SERVER_ERROR);
    }







}
