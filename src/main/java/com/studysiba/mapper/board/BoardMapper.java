package com.studysiba.mapper.board;

import com.studysiba.domain.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    /*
     *  게시판 게시글 등록
     *  @Param BoardVO
     *  @Return 게시글 등록 여부 반환
     */
    int writePost(BoardVO boardVO);

    /*
     *  답글로 먼저 등록된 게시글 STEP 증가
     *  @Param BoardVO
     *  @Return STEP 업데이트 변경 여부 반환
     */
    int replyShape(BoardVO boardVO);

    /*
     *  게시판 답글 등록
     *  @Param BoardVO
     *  @Return 답글 등록 여부 반환
     */
    int replyPost(BoardVO boardVO);
}
