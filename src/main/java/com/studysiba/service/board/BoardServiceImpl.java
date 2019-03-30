package com.studysiba.service.board;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.mapper.board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    HttpSession httpSession;

    /*
     *  게시판 게시글 등록
     *  @Param BoardVO
     *  @Return 게시글 등록 상태코드 반환
     */
    @Transactional
    @Override
    public String writePost(BoardVO boardVO) {
        String stateCode = "";
        if ( httpSession.getAttribute("id") == null ) stateCode = "BOARD_WRITE_ERROR";
        boardVO.setBrdId((String) httpSession.getAttribute("id"));
        if ( boardVO.getBrdGno() == 0 ) {
            stateCode = boardMapper.writePost(boardVO) == 1 ? "BOARD_WRITE_ERROR" : "BOARD_WRITE_SUCCESS";
        } else {
            boardMapper.replyShape(boardVO);
            stateCode = boardMapper.replyPost(boardVO) == 1 ?  "BOARD_WRITE_ERROR" : "BOARD_WRITE_SUCCESS";
        }
        return stateCode;
    }
}
