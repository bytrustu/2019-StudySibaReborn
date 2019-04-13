package com.studysiba.controller;

import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.service.board.BoardService;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Controller
@Log4j
public class CommonController implements ErrorController {

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
    public String moveMain(Model model, @RequestParam(value = "requireLogin", required = false, defaultValue = "false") boolean requireLogin,
                                                                @RequestParam(value = "requireAdmin", required = false, defaultValue = "false") boolean requireAdmin) throws Exception {
        // 랭킹 리스트
        List<PointVO> userRankingList = commonService.viewUserTotalRanking();
        commonService.isRequireAdmin(requireAdmin);
        model.addAttribute("rank", userRankingList);

        // 접속자 현황
        List<MemberVO> connectMemberList = commonService.connectedMemberList();
        model.addAttribute("connect",connectMemberList);

        // 슬라이드 스터디 리스트
        List<StudyVO> studyList = commonService.studyList();
        model.addAttribute("study",studyList);

        // 회원수, 방문수 카운트
        HashMap<String, Integer> memberCount = commonService.memberCount();
        model.addAttribute("count", memberCount);

        model.addAttribute("requireLogin", requireLogin);
        model.addAttribute("requireAdmin", requireAdmin);

        return "common/main";
    }


    /*
     *  에러상태코드에 따른 처리
     *  @Param request
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        commonService.handleError(request);
        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return "/error";
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

        HashMap<String, String> uploadInfo = new HashMap<>();
        uploadInfo.put("uploaded", "true");
        uploadInfo.put("url", "/file/view/" + menu + "/" + fileName);
        return new ResponseEntity<>(uploadInfo, HttpStatus.OK);
    }

    /*
     *  파일 다운로드
     *  @Param menu, no
     *  @Return resource
     */
    @ResponseBody
    @GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestParam String menu, @RequestParam int no){
        HashMap<String, Object> download = commonService.downloadFile(menu, no);
        Resource resource = (Resource) download.get("resource");
        HttpHeaders httpHeaders = (HttpHeaders) download.get("headers");
        return new ResponseEntity<Resource>(resource,httpHeaders, HttpStatus.OK);
    }


}
