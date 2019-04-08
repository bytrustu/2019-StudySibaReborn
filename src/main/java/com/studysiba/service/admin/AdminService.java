package com.studysiba.service.admin;

import java.util.HashMap;

public interface AdminService {

    /*
     *  차트 데이터 조회
     *  @Return 게시판/스터디/그룹/메세지 차트 데이터 반환
     */
    HashMap<String,Object> getDataChart();
}
