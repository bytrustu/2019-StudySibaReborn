package com.studysiba.service.board;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.board.CommentVO;
import com.studysiba.domain.board.LikeVO;
import com.studysiba.domain.common.PageVO;
import com.studysiba.mapper.board.BoardMapper;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.domain.common.StateVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

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
        if (boardVO.getBrdType().equals("notice") && !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN"))
            return postState;
        // 세션 아이디가 없을 경우 실패
        if (httpSession.getAttribute("id") == null) return postState;
        // 제목 내용이 없거나 공백일 경우
        if (!DataValidation.findEmptyValue(boardVO, new String[]{"mbrTitle", "mbrContent"}).equals("VALUES_STATE_GOOD"))
            return postState;

        boardVO.setBrdId((String) httpSession.getAttribute("id"));

        if (boardVO.getIsReply() == null || boardVO.getIsReply().equals("false")) {
            stateCode = boardMapper.writePost(boardVO) == 1 ? "BOARD_WRITE_SUCCESS" : "BOARD_WRITE_ERROR";
        } else {
            boardMapper.replyShape(boardVO);
            stateCode = boardMapper.replyPost(boardVO) == 1 ? "BOARD_WRITE_SUCCESS" : "BOARD_WRITE_ERROR";
        }

        // 글등록에 성공했을경우 글번호 함께 포함
        if (stateCode.equals("BOARD_WRITE_SUCCESS")) {
            int no = boardMapper.getPostMaxNum();
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
    public ArrayList<BoardVO> getPostList(PageVO pageVO) throws Exception {
        ArrayList<BoardVO> postList = boardMapper.getPostList(pageVO);
        // 지난기간 [ 몇분전, 몇일전 ] 변환
        for (BoardVO vo : postList) vo.setLastTime(DataConversion.DurationFromNow(vo.getBrdDate()));
        return postList;
    }

    /*
     *  게시판 게시글 조회
     *  @Param no
     *  @Return 해당 메뉴, 글번호에 해당하는 글 조회
     */
    @Override
    public BoardVO getPostOne(String menu, int no) throws Exception {
        BoardVO boardVO = new BoardVO();
        boardVO.setBrdType(menu);
        boardVO.setBrdNo(no);
        BoardVO postVO = boardMapper.getPostOne(boardVO);
        // 해당 글이 삭제 된 글인 경우
        try {
            if (postVO.getBrdAvailable() == 0) {
                // 로그인 되지 않은 사용자 접근거부
                if (httpSession.getAttribute("auth") == null) {
                    postVO = null;
                } else {
                    // 일반유저 접근 거부
                    if (!httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                        postVO = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpSession.setAttribute("stateCode", "BOARD_READ_ERROR");
        }
        // 조회수증가
        if (postVO != null) boardMapper.increaseReadCount(boardVO);
        else httpSession.setAttribute("stateCode", "BOARD_READ_ERROR");
        return postVO;
    }

    /*
     *  게시글 좋아요 추가
     *  @Param menu, no
     *  @Return 좋아요 추가상태 코드 반환
     */
    @Override
    public StateVO addLike(String menu, int no) throws Exception {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        stateVO.setStateCode("LIKE_STATE_ERROR");
        if (httpSession.getAttribute("id") != null) {
            LikeVO likeVO = new LikeVO();
            likeVO.setLikeFno(no);
            likeVO.setLikeId((String) httpSession.getAttribute("id"));
            // 이미 등록 추가한 회원인 경우
            if (boardMapper.alreadyRegisteredLike(likeVO) == 1) {
                stateVO.setStateCode("LIKE_STATE_ALREADY");
                return stateVO;
            }
            // 좋아요 추가
            int likeState = boardMapper.addLike(likeVO);
            if (likeState == 1) stateVO.setStateCode("LIKE_STATE_SUCCESS");
        }
        return stateVO;
    }

    /*
     *  게시판 댓글 리스트 조회
     *  @Param no
     *  @Return 게시판별 댓글 리스트 조회
     */
    @Override
    public ArrayList<CommentVO> getCommentList(int no) throws Exception {
        ArrayList<CommentVO> commentList = boardMapper.getCommentList(no);
        for ( int i=0; i<commentList.size(); i++ ) {
            String lastTime = DataConversion.DurationFromNow(commentList.get(i).getCmtDate());
            commentList.get(i).setLastTime(lastTime);
            System.out.println(commentList.get(i).getLastTime());
        }

        return commentList;
    }

    /*
     *  게시판 댓글 등록
     *  @Param commentVO
     *  @Return 댓글 등록 상태코드 반환
     */
    @Transactional
    @Override
    public StateVO writeComment(CommentVO commentVO) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(commentVO.getCmtBno());
        stateVO.setStateCode("COMMENT_WRITE_ERROR");
        if (httpSession.getAttribute("id") != null) {
            commentVO.setCmtId((String) httpSession.getAttribute("id"));
            int commentState = boardMapper.writeComment(commentVO);
            if (commentState == 1) {
                stateVO.setStateCode("COMMENT_WRITE_SUCCESS");
                stateVO.setNo(boardMapper.getCommentMaxNum(commentVO));
            }

        }
        return stateVO;
    }

    /*
     *  게시판 댓글 조회
     *  @Param  no
     *  @Return 댓글 정보 리턴
     */
    @Override
    public CommentVO getCommentOne(int no) {
        return boardMapper.getCommentOne(no);
    }

    /*
     *  게시판 댓글 삭제
     *  @Param  deleteVO
     *  @Return 게시판 댓글 삭제 상태코드 반환
     */
    @Override
    public StateVO deletePost(CommentVO deleteVO) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(deleteVO.getBrdNo());
        // 타입에 따른 에러상태코드 초기화
        if (deleteVO.getBrdType().equals("board")) stateVO.setStateCode("BOARD_DELETE_ERROR");
        else stateVO.setStateCode("COMMENT_DELETE_ERROR");
        // 등록한회원 또는 관리자만 변경 가능
        if (httpSession.getAttribute("id") != null || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
            int deleteState = 0;
            switch (deleteVO.getBrdType()) {
                case "comment":
                    if (httpSession.getAttribute("id").equals(boardMapper.getCommentOne(deleteVO.getCmtNo()).getCmtId()) || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                        deleteState = boardMapper.deleteComment(deleteVO);
                        if (deleteState == 1) {
                            stateVO.setStateCode("COMMENT_DELETE_SUCCESS");
                            stateVO.setNo(deleteVO.getCmtNo());
                        }
                    }
                    break;
                default:
                    if (httpSession.getAttribute("id").equals(boardMapper.getPostOne(deleteVO).getMbrId()) || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                        deleteState = boardMapper.deletePost(deleteVO);
                        if (deleteState == 1) {
                            stateVO.setStateCode("BOARD_DELETE_SUCCESS");
                            stateVO.setNo(deleteVO.getBrdNo());
                        }
                    }
                    break;
            }
            // 아닐경우 에러상태코드 반환
        }
        return stateVO;
    }

    /*
     *  게시글 수정
     *  @Param boardVO
     *  @Return 게시글수정 상태코드 반환
     */
    @Override
    public StateVO updatePost(BoardVO boardVO) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(boardVO.getBrdNo());
        stateVO.setStateCode("BOARD_UPDATE_ERROR");
        // 등록한회원 또는 관리자만 변경 가능
        if (httpSession.getAttribute("id") != null || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
            int updateState = 0;
            if (httpSession.getAttribute("id").equals(boardMapper.getPostOne(boardVO).getBrdId()) || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                updateState = boardMapper.updatePost(boardVO);
                stateVO.setStateCode("BOARD_UPDATE_SUCCESS");
                if (updateState == 1) {
                    stateVO.setStateCode("BOARD_UPDATE_SUCCESS");
                    stateVO.setObj(boardMapper.getPostOne(boardVO));
                }
            }
        }
        return stateVO;
    }
}
