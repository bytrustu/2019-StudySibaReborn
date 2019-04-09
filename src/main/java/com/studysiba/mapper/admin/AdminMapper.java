package com.studysiba.mapper.admin;

import com.studysiba.domain.member.PointVO;
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
}
