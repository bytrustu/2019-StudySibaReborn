package com.studysiba.service.board;

import com.studysiba.domain.board.BoardVO;

public interface BoardService {

    /*
     *  게시판 게시글 등록
     *  @Param BoardVO
     *  @Return 게시글 등록 상태코드 반환
     */
    String writePost(BoardVO boardVO);

}
