package com.studysiba.controller;

import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.service.common.CommonService;
import com.studysiba.service.group.GroupService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/group")
@Log4j
public class GroupController {

    @Autowired
    GroupService groupService;

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


}
