package com.studysiba.mapper.admin;

import com.studysiba.domain.board.BoardVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.domain.messenger.MessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper {


    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 게시판/스터디/그룹 통계 반환
     */
    HashMap<String,Object> getDataChart();

    /*
     *  차트 데이터 조회
     *  @Return 최근 1주일 방문자수/가입자수 시간별 통계 반환
     */
    ArrayList<HashMap<String,Object>> getInfoChart();

    /*
     *  회원 리스트 조회
     *  @Return 전체 회원 리스트 조회
     */
    ArrayList<PointVO> getMemberList();

    /*
     *  유저정보 변경
     *  @Param  memberMap
     *  @Return 유저정보 변경에 따른 결과 반환
     */
    int updateMember(HashMap<String,Object> memberMap);

    /*
     *  유저정보조회
     *  @Param  id
     *  @Return 유저정보 조회
     */
    PointVO getMemberOne(String id);

    /*
     *  게시판 리스트 조회
     *  @Return 게시판 리스트 반환
     */
    ArrayList<BoardVO> getBoardList();

    /*
     *  스터디 리스트 조회
     *  @Return 스터디 리스트 반환
     */
    List<StudyVO> getStudyList();

    /*
     *  그룹 리스트 조회
     *  @Return 그룹 리스트 반환
     */
    List<StudyVO> getGroupList();

    /*
     *  메세지 리스트 조회
     *  @Return 메세지 리스트 반환
     */
    List<MessageVO> getMessageList();
}
