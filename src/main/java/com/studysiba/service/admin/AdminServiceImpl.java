package com.studysiba.service.admin;

import com.studysiba.mapper.admin.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    /*
     *  차트 데이터 조회
     *  @Return 게시판/스터디/그룹 차트 데이터 반환
     */
    @Override
    public HashMap<String, Object> getDataChart() {
        return adminMapper.getDataChart();
    }
}
