package com.studysiba.mapper.admin;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface AdminMapper {


    /*
     *  차트 데이터 조회
     *  @Return 게시판/스터디/그룹 차트 데이터 반환
     */
    HashMap<String,Object> getDataChart();
}
