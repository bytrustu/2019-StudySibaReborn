package com.studysiba.controller;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.board.CommentVO;
import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
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

import java.util.List;
import java.util.Map;

@Log4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    CommonService commonService;


    /*
     *  게시판별이동 리스트 [ 공지사항, 커뮤니티]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시판별 list 경로 이동
     */
    @GetMapping("/{menu}/list")
    public String moveList(Model model, @PathVariable("menu") String menu, @ModelAttribute Criteria criteria) throws Exception {
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);

        PageVO pageVO = commonService.getPageInfomation(menu, criteria);
        model.addAttribute("page", pageVO);
        model.addAttribute("cri", criteria);

        // 이동 경로
        String location = "/board/list";
        List<BoardVO> commoonList = boardService.getPostList(pageVO);
        model.addAttribute("boardList", commoonList);
        return location;
    }

    /*
     *  게시판별이동 뷰 [ 공지사항, 커뮤니티 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시판별 list 경로 이동
     */
    @GetMapping("/{menu}/view")
    public String moveView(Model model, @PathVariable("menu") String menu, @RequestParam("no") int no, @ModelAttribute Criteria criteria) throws Exception {
        log.info("move community view");
        log.info(criteria);
        // 게시판 별 안내 글귀
        Map<String, String> introComment = commonService.getIntroduceComment(menu);
        model.addAttribute("intro", introComment);
        model.addAttribute("cri", criteria);

        BoardVO boardVO = null;


        // 이동 경로
        String location = "/";
        try {
            switch (menu) {
                case "notice":
                    location += "board/view";
                    boardVO = boardService.getPostOne(menu, no);
                    if (boardVO == null) return "redirect:/board/community/list";
                    model.addAttribute("board", boardVO);
                    List<CommentVO> noticeCommentList = boardService.getCommentList(no);
                    if (noticeCommentList != null) model.addAttribute("comment", noticeCommentList);
                    log.info(noticeCommentList);
                    break;
                case "community":
                    location += "board/view";
                    boardVO = boardService.getPostOne(menu, no);
                    if (boardVO == null) return "redirect:/community/list";
                    List<CommentVO> communityCommentList = boardService.getCommentList(no);
                    model.addAttribute("board", boardVO);
                    if (communityCommentList != null) model.addAttribute("comment", communityCommentList);
                    log.info(communityCommentList);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/" + menu + "/list";
        }
        return location;
    }


    /*
     *  댓글, 게시글 번호로 글정보 반환
     *  @Param type, no
     *  @Return 번호에 해당하는 정보 반환
     */
    @ResponseBody
    @GetMapping(value = "/{type}/get/{no}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Object> getPost(@PathVariable("type") String type, @PathVariable("no") int no) throws Exception {
        Object obj = null;
        switch (type) {
            case "community":
                obj = boardService.getPostOne(type, no);
                if (obj != null) log.info("커뮤니티글조회 : " + obj);
                break;
            case "notice":
                obj = boardService.getPostOne(type, no);
                if (obj != null) log.info("공지사항글조회 : " + obj);
                break;
            case "comment":
                obj = boardService.getCommentOne(no);
                if (obj != null) log.info("댓글조회 : " + obj);
                break;
        }
        return (obj != null) ?
                new ResponseEntity<>(obj, HttpStatus.OK) :
                new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  게시판별게시글작성 [ 공지사항, 커뮤니티 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 게시글 작성 상태코드 반환
     */
    @ResponseBody
    @PostMapping(value = "/{type}/write", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> writePost(Model model, @PathVariable("type") String type, @RequestBody CommentVO writeVO) throws Exception {
        log.info(type + " : " + writeVO);
        StateVO writeState = null;
        switch (type) {
            case "notice":
                writeState = boardService.writePost(writeVO);
                log.info("공지사항 글등록 상태 : " + writeState.getStateCode());
                break;
            case "community":
                writeState = boardService.writePost(writeVO);
                log.info("커뮤니티 글등록 상태 : " + writeState.getStateCode());
                break;
            case "comment":
                writeState = boardService.writeComment(writeVO);
                log.info("댓글등록 상태 : " + writeState.getStateCode());
                break;
        }
        return writeState.getStateCode().contains("SUCCESS") ?
                new ResponseEntity<>(writeState, HttpStatus.OK) : new ResponseEntity<>(writeState, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *  게시판별게시글삭제 [ 공지사항, 커뮤니티 ]
     *  @Param menu, type, deleteVO
     *  @Return 게시글 삭제 상태코드 반환
     */
    @ResponseBody
    @DeleteMapping(value = "/delete", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> deletePost(@RequestBody CommentVO deleteVO) throws Exception {
        log.info(deleteVO);
        StateVO deleteState = null;
        deleteState = boardService.deletePost(deleteVO);
        log.info("글삭제 상태 : " + deleteState.getStateCode());
        return deleteState.getStateCode().contains("SUCCESS") ?
                new ResponseEntity<>(deleteState, HttpStatus.OK) : new ResponseEntity<>(deleteState, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  게시판별게시글수정 [ 공지사항, 커뮤니티 ]
     *  @Param deleteVO
     *  @Return 게시글 수정 내용반환
     */
    @ResponseBody
    @PutMapping(value = "/update")
    public ResponseEntity<BoardVO> updatePost(@RequestBody BoardVO boardVO) throws Exception {
        log.info(boardVO);
        StateVO stateVO;
        stateVO = boardService.updatePost(boardVO);
        log.info(boardVO);
        return stateVO.getStateCode().equals("BOARD_UPDATE_SUCCESS") ? new ResponseEntity<>(boardVO, HttpStatus.OK) : new ResponseEntity<>(boardVO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     *  게시판별 좋아요 추가 [ 공지사항, 커뮤니티 ]
     *  @Param menu [ 메뉴이름 ]
     *  @Return 메뉴별 반환
     */
    @ResponseBody
    @PostMapping(value = "/{menu}/like/{no}", consumes = "application/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StateVO> addLike(@PathVariable("menu") String menu, @PathVariable("no") int no) throws Exception {
        log.info(menu + " : " + no);
        StateVO stateVO = boardService.addLike(menu, no);
        log.info("좋아요상태 : " + stateVO);
        return stateVO.getStateCode().equals("LIKE_STATE_ERROR") ?
                new ResponseEntity<>(stateVO, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<>(stateVO, HttpStatus.OK);
    }


}
