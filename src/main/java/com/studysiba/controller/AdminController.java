package com.studysiba.controller;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.service.admin.AdminService;
import com.studysiba.service.board.BoardService;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CommonService commonService;

    @Autowired
    AdminService adminService;

    @Autowired
    BoardService boardService;

    /*
     *  관리자 메뉴이동
     *  @Param  menu
     *  @Return 관리자 메뉴에 따른 정보 반환
     */
    @GetMapping("/{menu}")
    public String moveList(Model model, @PathVariable("menu") String menu) throws Exception {

        String page = "admin";
        String location = "/" + page +"/main";

        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(page);
        model.addAttribute("intro", introComment);

        switch (menu) {
            case "main" :
                break;
            case "member" :
                List<PointVO> memberList = adminService.getMemberList();
                model.addAttribute("member",memberList);
                break;
            case "board" :
                List<BoardVO> boardList = adminService.getBoardList();
                model.addAttribute("board",boardList);
                break;
            case "study" :
                break;
            case "group" :
                break;
            case "message" :
                break;
        }

        return location;
    }


    /*
     *  차트 데이터 조회
     *  @Param  type
     *  @Return 조건에 따른 차트 데이터 반환
     */
    @ResponseBody
    @PostMapping(value="/chart/{type}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Object> getChart(Model model, @PathVariable("type") String type) {
        switch (type) {
            case "data":
                HashMap<String, Object> chartData = adminService.getDataChart();
                return chartData != null ? new ResponseEntity<>(chartData, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            case "info":
                List<HashMap<String, Object>> infoChart = adminService.getInfoChart();
                return infoChart != null ? new ResponseEntity<>(infoChart, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return null;
        }
    }

    /*
     *  유저정보 조회
     *  @Param  id
     *  @Return 유저 정보 반환
     */
    @ResponseBody
    @GetMapping(value="/member/get/{id}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<PointVO> getMemberOne(@PathVariable("id") String id){
        PointVO pointVO = adminService.getMemberOne(id);
        return pointVO != null ? new ResponseEntity<>(pointVO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  유저정보 변경
     *  @Param  memberMap
     *  @Return 유저정보 변경에 따른 상태코드 반환
     */
    @ResponseBody
    @PutMapping(value="/member/update", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> updateMember(@RequestBody HashMap<String, Object> memberMap){
        StateVO stateVO = adminService.updateMember(memberMap);
        return stateVO.getStateCode().contains("SUCCESS")  ? new ResponseEntity<>(stateVO, HttpStatus.OK) :
                new ResponseEntity<>(stateVO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
