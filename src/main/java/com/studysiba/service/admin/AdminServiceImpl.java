package com.studysiba.service.admin;

import com.studysiba.common.DataValidation;
import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.mapper.admin.AdminMapper;
import com.studysiba.domain.messenger.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    HttpSession httpSession;


    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 게시판/스터디/그룹 통계 반환
     */
    @Override
    public HashMap<String, Object> getDataChart() {
        return adminMapper.getDataChart();
    }

    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 방문자수/가입자수 시간별 통계 반환
     */
    @Override
    public List<HashMap<String, Object>> getInfoChart() {
        return adminMapper.getInfoChart();
    }

    /*
     *  회원 리스트 조회
     *  @Return 전체 회원 리스트 조회
     */
    @Override
    public List<PointVO> getMemberList() {
        return adminMapper.getMemberList();
    }


    /*
     *  유저정보 조회
     *  @Param  memberMap
     *  @Return 유저정보 반환
     */
    @Override
    public PointVO getMemberOne(String id) {
        //if ( !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) return null;
        return adminMapper.getMemberOne(id);
    }

    /*
     *  게시판 리스트 조회
     *  @Return 게시판 리스트 반환
     */
    @Override
    public List<BoardVO> getBoardList() {

        return adminMapper.getBoardList();
    }

    /*
     *  스터디 리스트 조회
     *  @Return 스터디 리스트 반환
     */
    @Override
    public List<StudyVO> getStudyList() {

        return adminMapper.getStudyList();
    }

    /*
     *  그룹 리스트 조회
     *  @Return 그룹 리스트 반환
     */
    @Override
    public List<StudyVO> groupList() {

        return adminMapper.getGroupList();
    }

    /*
     *  메세지 리스트 조회
     *  @Return 메세지 리스트 반환
     */
    @Override
    public List<MessageVO> messageList() {

        return adminMapper.getMessageList();
    }


    /*
     *  유저정보 변경
     *  @Param  memberMap
     *  @Return 유저정보 변경에 따른 상태코드 반환
     */
    @Override
    public StateVO updateMember(HashMap<String, Object> memberMap) {
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("MEMBER_UPDATE_ERROR");
        if ( memberMap.get("type").equals("MBR_PASS") ) {
            boolean checkPass = DataValidation.checkPassword((String)memberMap.get("value"));
            if ( !checkPass ) {
                stateVO.setStateCode("PASS_STATE_ERROR");
                return stateVO;
            }
            memberMap.put("value",passwordEncoder.encode((String)memberMap.get("value")));
        } else if ( memberMap.get("type").equals("MBR_NICK") ) {
            if ( !DataValidation.checkOnlyCharacters((String)memberMap.get("value")) || !DataValidation.textLengthComparison(10, (String)memberMap.get("value")) ) {
                stateVO.setStateCode("NICK_STATE_ERROR");
                return stateVO;
            }
        } else if ( memberMap.get("type").equals("PNT_SCORE") ) {
            if ( Integer.parseInt((String) memberMap.get("value")) < 0 ) {
                stateVO.setStateCode("POINT_UPDATE_ERROR");
                return stateVO;
            }
        }
        int updateState = adminMapper.updateMember(memberMap);
        if ( updateState == 1 ) stateVO.setStateCode("MEMBER_UPDATE_SUCCESS");
        return stateVO;
    }
}
