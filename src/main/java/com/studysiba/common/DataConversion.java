package com.studysiba.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class DataConversion {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    // UUID 생성
    public static String returnUUID(){
        return UUID.randomUUID().toString();
    }

    // 범위 번호 생성
    public static int returnRanNum(int range){
        return (int)(Math.random()*range)+1;
    }


}
