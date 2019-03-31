package com.studysiba.service.board;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.board.CommentVO;
import com.studysiba.domain.common.PageVO;
import com.studysiba.mapper.common.StateVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BoardService {

    /*
     *  게시판 게시글 등록
     *  @Param BoardVO
     *  @Return 게시글 등록 상태코드 반환
     */
    StateVO writePost(BoardVO boardVO) throws Exception;

    /*
     *  게시판 게시글 리스트 조회
     *  @Param pageVO
     *  @Return 게시판별 게시글 리스트 조회
     */
    ArrayList<BoardVO> getPostList(PageVO pageVO) throws Exception;

    /*
     *  게시판 게시글 조회
     *  @Param menu, no
     *  @Return 해당 메뉴, 글번호에 해당하는 글 조회
     */
    BoardVO getPostOne(String menu, int no) throws Exception;

    /*
     *  게시글 좋아요 추가
     *  @Param menu, no
     *  @Return 좋아요 추가상태 코드 반환
     */
    StateVO addLike(String menu, int no) throws Exception;

    /*
     *  게시판 댓글 리스트 조회
     *  @Param no
     *  @Return 게시판별 댓글 리스트 조회
     */
    ArrayList<CommentVO> getCommentList(int no) throws Exception;

    /*
     *  게시판 댓글 등록
     *  @Param commentVO
     *  @Return 댓글 등록 상태코드 반환
     */
    StateVO writeComment(CommentVO commentVO);

    /*
     *  게시판 댓글 조회
     *  @Param no
     *  @Return 댓글번호에 해당하는 댓글 정보 조회
     */
    CommentVO getCommentOne(int no);
}
