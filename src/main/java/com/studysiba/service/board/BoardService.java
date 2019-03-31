package com.studysiba.service.board;

import com.studysiba.domain.board.BoardVO;
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
    ArrayList<BoardVO> getPostList(PageVO pageVO);

    /*
     *  게시판 게시글 조회
     *  @Param menu, no
     *  @Return 해당 메뉴, 글번호에 해당하는 글 조회
     */
    BoardVO getPostOne(String menu, int no);
}
