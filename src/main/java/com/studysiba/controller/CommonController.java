package com.studysiba.controller;

import com.studysiba.domain.member.PointVO;
import com.studysiba.service.board.BoardService;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


@Controller
@Log4j
public class CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    BoardService boardService;

    /*
     *  메인이동
     *  @Param requiredLogin[ 로그인모달창 필요여부 ]
     *  @Return 메인경로이동
     */
    @GetMapping("/")
    public String moveMain(Model model, @RequestParam(value = "requireLogin", required = false, defaultValue = "false") boolean requireLogin) throws Exception {
        List<PointVO> userRankingList = commonService.viewUserTotalRanking();
        model.addAttribute("rank", userRankingList);
        log.info(userRankingList);
        model.addAttribute("requireLogin", requireLogin);
        return "common/main";
    }


    /*
     *  게시판별파일업로드 [ 공지사항, 커뮤니티, 스터디참여, 스터디그룹 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 메뉴별 반환
     */
    @ResponseBody
    @PostMapping("/upload/{menu}")
    public ResponseEntity<HashMap<String, String>> uploadFile(@RequestPart MultipartFile upload, @PathVariable("menu") String menu) throws Exception {

        log.info(upload.getOriginalFilename());
        String stateCode = null;

        String fileName = commonService.uploadFile(upload, menu);
        if (fileName == null) {
            stateCode = "FILE_STATE_ERROR";
            log.info(stateCode);
            return null;
        }

        switch (menu) {
            case "community":
                HashMap<String, String> uploadInfo = new HashMap<>();
                uploadInfo.put("uploaded", "true");
                uploadInfo.put("url", "/file/view/" + menu + "/" + fileName);
                return new ResponseEntity<>(uploadInfo, HttpStatus.OK);
            case "notice":
                break;
        }
        return null;
    }

}
