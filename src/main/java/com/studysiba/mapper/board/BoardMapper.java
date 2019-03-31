package com.studysiba.mapper.board;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.PageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

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

    /*
     *  게시글수 조회
     *  @Param menu
     *  @Return 게시글 글수 반환
     */
    int getPostCount(String menu);

    /*
     *  게시글 리스트조회
     *  @Param pageVO
     *  @Return 조건에 맞는 게시글 리스트 반환
     */
    ArrayList<BoardVO> getPostList(PageVO pageVO);

    /*
     *  가장 마지막에 작성된 게시글 번호
     *  @Return MAX 게시글 번호 반환
     */
    int getPostMaxNum();

    /*
     *  게시판 게시글 조회
     *  @Param boardVO
     *  @Return 해당 메뉴, 글번호에 해당하는 글 조회
     */
    BoardVO getPostOne(BoardVO boardVO);
}
