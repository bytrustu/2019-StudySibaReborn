package com.studysiba.controller;

import com.studysiba.service.admin.AdminService;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CommonService commonService;

    @Autowired
    AdminService adminService;


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
                break;
            case "board" :
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
    public ResponseEntity<HashMap<String,Object>> getChart(Model model, @PathVariable("type") String type){

        HashMap<String, Object> chartData = null;

        switch (type) {
            case "data" :
                chartData = adminService.getDataChart();
                break;
            case "join" :
                break;
            case "info" :
                break;
        }

        model.addAttribute("chart",chartData);

        System.out.println(chartData);
        System.out.println(Arrays.deepToString(new HashMap[]{chartData}));

        return chartData != null ? new ResponseEntity<>(chartData,HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
