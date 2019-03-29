package com.studysiba.controller;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    // 공지사항 커뮤니티 스터디참여 스터디그룹 이동
    @GetMapping("/{menu}/{role}")
    public String moveTest(Model model, @PathVariable("menu") String menu, @PathVariable("role") String role){
        log.info("move community");
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro",introComment);

        // 이동 경로
        String location = "/";
        switch (menu) {
            case "notice" :
                location += "board/" + role;
                break;
            case "community" :
                location += "board/" + role;
                break;
            default :
                location += menu + "/" + role;
                break;
        }

        return location;
    }





}
