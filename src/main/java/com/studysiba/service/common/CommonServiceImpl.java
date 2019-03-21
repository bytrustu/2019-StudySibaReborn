package com.studysiba.service.common;

import com.studysiba.mapper.common.CommonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    CommonMapper commonMapper;

    /*
     *  MyBatis 연동 TEST
     */
    @Override
    public int getTest() {
        return commonMapper.getTest();
    }


}
