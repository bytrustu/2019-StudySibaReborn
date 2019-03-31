package com.studysiba.service.board;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.PageVO;
import com.studysiba.mapper.board.BoardMapper;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.mapper.common.StateVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Log4j
@Service
public class BoardServiceImpl implements BoardService{

    @Resource
    BoardMapper boardMapper;

    @Resource
    CommonMapper commonMapper;

    @Autowired
    HttpSession httpSession;

    /*
     *  게시판 게시글 등록
     *  @Param BoardVO
     *  @Return 게시글 등록 상태코드 반환
     */
    @Transactional
    @Override
    public StateVO writePost(BoardVO boardVO) throws Exception {
        StateVO postState = new StateVO();
        String stateCode = "BOARD_WRITE_ERROR";
        postState.setStateCode(stateCode);
        // 공지사항 일경우 권한이 ADMIN이 아닌경우 실패
        if ( boardVO.getBrdType().equals("notice") && !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) return postState;
        // 세션 아이디가 없을 경우 실패
        if ( httpSession.getAttribute("id") == null ) return postState;
        // 제목 내용이 없거나 공백일 경우
        if ( !DataValidation.findEmptyValue(boardVO, new String[]{"mbrTitle","mbrContent"}).equals("VALUES_STATE_GOOD") ) return postState;

        boardVO.setBrdId((String) httpSession.getAttribute("id"));

        if ( boardVO.getIsReply() == null || boardVO.getIsReply().equals("false") ) {
            stateCode = boardMapper.writePost(boardVO) == 1 ? "BOARD_WRITE_SUCCESS" : "BOARD_WRITE_ERROR";
        } else {
            boardMapper.replyShape(boardVO);
            stateCode = boardMapper.replyPost(boardVO) == 1 ?  "BOARD_WRITE_SUCCESS" : "BOARD_WRITE_ERROR";
        }

        // 글등록에 성공했을경우 글번호 함께 포함
        if ( stateCode.equals("BOARD_WRITE_SUCCESS") ) {
            int no  = boardMapper.getPostMaxNum();
            postState.setNo(no);
            postState.setStateCode(stateCode);
        }
        return postState;
    }

    /*
     *  게시판 게시글 리스트 조회
     *  @Param pageVO
     *  @Return 게시판별 게시글 리스트 조회
 */
    @Override
    public ArrayList<BoardVO> getPostList(PageVO pageVO) {
        ArrayList<BoardVO> postList = boardMapper.getPostList(pageVO);
        // 지난기간 [ 몇분전, 몇일전 ] 변환
        for ( BoardVO vo : postList ) vo.setLastTime( DataConversion.DurationFromNow(vo.getBrdDate()) );
        return postList;
    }

    /*
     *  게시판 게시글 조회
     *  @Param no
     *  @Return 해당 메뉴, 글번호에 해당하는 글 조회
     */
    @Override
    public BoardVO getPostOne(String menu, int no) {
        BoardVO boardVO = new BoardVO();
        boardVO.setBrdType(menu);
        boardVO.setBrdNo(no);
        return boardMapper.getPostOne(boardVO);
    }
}
