package com.studysiba.service.admin;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.member.PointVO;

import java.util.HashMap;
import java.util.List;

public interface AdminService {

    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 게시판/스터디/그룹/메세지 통계 반환
     */
    HashMap<String,Object> getDataChart();

    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 방문자수/가입자수 시간별 통계 반환
     */
    List<HashMap<String,Object>> getInfoChart();

    /*
     *  회원 리스트 조회
     *  @Return 전체 회원 리스트 조회
     */
    List<PointVO> getMemberList();

    /*
     *  유저정보 변경
     *  @Param  memberMap
     *  @Return 유저정보 변경에 따른 상태코드 반환
     */
    StateVO updateMember(HashMap<String,Object> memberMap);

    /*
     *  유저정보 조회
     *  @Param  memberMap
     *  @Return 유저정보 반환
     */
    PointVO getMemberOne(String id);

    /*
     *  게시판 리스트 조회
     *  @Return 게시판 리스트 반환
     */
    List<BoardVO> getBoardList();
}
