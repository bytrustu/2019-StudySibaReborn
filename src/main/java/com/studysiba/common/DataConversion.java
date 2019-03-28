package com.studysiba.common;

import java.util.UUID;

public class DataConversion {

    // UUID 생성
    public static String returnUUID(){
        return UUID.randomUUID().toString();
    }

    // 범위 번호 생성
    public static int returnRanNum(int range){
        return (int)(Math.random()*range)+1;
    }


}
