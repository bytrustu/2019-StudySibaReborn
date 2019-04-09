package com.studysiba.service.admin;

import com.studysiba.domain.member.PointVO;
import com.studysiba.mapper.admin.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

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
}
