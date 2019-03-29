package com.studysiba.controller;

import com.studysiba.domain.common.UploadVO;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Log4j
public class CommonController {

    @Autowired
    CommonService commonService;

    @GetMapping("/")
    public String moveMain(Model model, @RequestParam(value = "requireLogin", required = false, defaultValue = "false") boolean requireLogin) throws Exception {
        List<HashMap<String, Object>> userRankingList = commonService.viewUserTotalRanking();
        model.addAttribute("rank", userRankingList);
        log.info(userRankingList);
        model.addAttribute("requireLogin", requireLogin);
        return "common/main";
    }


    // 공지사항 커뮤니티 스터디참여 스터디그룹 이동
    @GetMapping("/{menu}/list")
    public String moveMenu(Model model, @PathVariable("menu") String menu) {
        log.info("move community");
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        // 이동 경로
        String location = "/";
        switch (menu) {
            case "notice":
                location += "board/list";
                break;
            case "community":
                location += "board/list";
                break;
            default:
                location += menu + "/list";
                break;
        }

        return location;
    }

    @PostMapping("/community/write")
    public String writeContent(Model model) {
        log.info("zz");
        return "/";
    }


    @ResponseBody
    @PostMapping("/upload/{menu}")
    public ResponseEntity<HashMap<String, String>> uploadFile(@RequestPart MultipartFile upload, @PathVariable("menu") String menu) throws Exception {

        log.info(upload.getOriginalFilename());
        String stateCode = null;

        try {
            if (upload.isEmpty())  {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = commonService.uploadFile(upload);
        if ( fileName == null ) {
            stateCode = "FILE_STATE_ERROR";
            log.info(stateCode);
            return null;
        }

        switch (menu) {
            case "community" :
                HashMap<String, String> uploadInfo = new HashMap<>();
                uploadInfo.put("uploaded","true");
                uploadInfo.put("url","/file/view/"+fileName);
                return new ResponseEntity<>(uploadInfo,HttpStatus.OK);
            case "notice" :
                break;
        }
        return null;

    }


}
