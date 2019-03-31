package com.studysiba.controller;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.member.PointVO;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     *  게시판별이동 리스트 [ 공지사항, 커뮤니티, 스터디참여, 스터디그룹 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시판별 list 경로 이동
     */
    @GetMapping("/{menu}/list")
    public String moveList(Model model, @PathVariable("menu") String menu, @ModelAttribute Criteria criteria) {
        log.info("move community");
        log.info(criteria);
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        PageVO pageVO = commonService.getPageInfomation(menu, criteria);
        model.addAttribute("page", pageVO);

        List<BoardVO> boardList = null;

        // 이동 경로
        String location = "/";
        switch (menu) {
            case "notice":
                location += "board/list";
                boardList = boardService.getPostList(pageVO);
                model.addAttribute("boardList",boardList);
                break;
            case "community":
                location += "board/list";
                boardList = boardService.getPostList(pageVO);
                model.addAttribute("boardList",boardList);
                break;
            default:
                location += menu + "/list";
                break;
        }
        return location;
    }

    /*
     *  게시판별이동 뷰 [ 공지사항, 커뮤니티, 스터디참여, 스터디그룹 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시판별 list 경로 이동
     */
    @GetMapping("/{menu}/view")
    public String moveView(Model model, @PathVariable("menu") String menu, @RequestParam("no") int no, @ModelAttribute Criteria criteria) {
        log.info("move community view");
        log.info(criteria);
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        BoardVO boardVO = null;


        // 이동 경로
        String location = "/";
        switch (menu) {
            case "notice":
                location += "board/view";
                boardVO = boardService.getPostOne(menu, no);
                model.addAttribute("board", boardVO);
                break;
            case "community" :
                location += "board/view";
                boardVO = boardService.getPostOne(menu, no);
                model.addAttribute("board", boardVO);
                break;
            default:
                location += menu + "/view";
                break;
        }
        return location;
    }


    /*
     *  게시판별게시글작성 [ 공지사항, 커뮤니티 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시글 작성 상태코드 반환
     */
    @ResponseBody
    @PostMapping(value="/{menu}/write", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> writePost(Model model, @PathVariable("menu") String menu, @RequestBody BoardVO boardVO ) {
        log.info(menu + " : " +boardVO);
        String stateCode = boardService.writePost(boardVO);
        log.info("글등록 상태 : " + stateCode);
        return stateCode.equals("BOARD_WRITE_SUCCESS") ?
                new ResponseEntity<>(stateCode,HttpStatus.OK) : new ResponseEntity<>(stateCode,HttpStatus.INTERNAL_SERVER_ERROR);
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
        if ( fileName == null ) {
            stateCode = "FILE_STATE_ERROR";
            log.info(stateCode);
            return null;
        }

        switch (menu) {
            case "community" :
                HashMap<String, String> uploadInfo = new HashMap<>();
                uploadInfo.put("uploaded","true");
                uploadInfo.put("url","/file/view/" + menu+ "/" + fileName);
                return new ResponseEntity<>(uploadInfo,HttpStatus.OK);
            case "notice" :
                break;
        }
        return null;

    }



}
