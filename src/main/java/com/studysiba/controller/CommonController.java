package com.studysiba.controller;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


@Controller
@Log4j
public class CommonController {

    @Autowired
    CommonService commonService;

    @GetMapping("/")
    public String moveMain(Model model, @RequestParam(value="requireLogin", required = false, defaultValue = "false") boolean requireLogin) throws Exception {
        List<HashMap<String, Object>> userRankingList = commonService.viewUserTotalRanking();
        model.addAttribute("rank", userRankingList);
        log.info(userRankingList);
        model.addAttribute("requireLogin", requireLogin);
        return "common/main";
    }




}
