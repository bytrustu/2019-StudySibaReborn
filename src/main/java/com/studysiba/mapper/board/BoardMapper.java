package com.studysiba.mapper.board;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.board.CommentVO;
import com.studysiba.domain.board.LikeVO;
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

    /*
     *  좋아요 추가여부 확인
     *  @Param likeVO
     *  @Return 좋아요 추가여부 조회 반환
     */
    int alreadyRegisteredLike(LikeVO likeVO);

    /*
     *  좋아요 추가
     *  @Param likeVO
     *  @Return 좋아요 추가여부 반환
     */
    int addLike(LikeVO likeVO);

    /*
     *  게시글 댓글 리스트조회
     *  @Param no
     *  @Return 댓글 리스트 반환
     */
    ArrayList<CommentVO> getCommentList(int no);

    /*
     *  게시판 댓글 등록
     *  @Param commentVO
     *  @Return 댓글 등록
     */
    int writeComment(CommentVO commentVO);

    /*
     *  게시판 댓글 작성한 댓글 번호 조회
     *  @Param commentVO
     *  @Return 자기가 작성한 마지막 댓글 조회
     */
    int getCommentMaxNum(CommentVO commentVO);

    /*
     *  댓글번호에 해당하는 글 정보 조회
     *  @Param no
     *  @Return 글번호에 해당하는 글정보 회원정보 조회
     */
    CommentVO getCommentOne(int no);

    /*
     *  글 조회수 증가
     *  @Param boardVO
     *  @Return 글 조회수 증가 여부 반환
     */
    int increaseReadCount(BoardVO boardVO);

    /*
     *  게시글 삭제
     *  @Param deleteVO
     *  @Return 게시글 삭제 업데이트 여부 반환
     */
    int deletePost(CommentVO deleteVO);

    /*
     *  댓글 삭제
     *  @Param deleteVO
     *  @Return 댓글 삭제 업데이트 여부 반환
     */
    int deleteComment(CommentVO deleteVO);
}
