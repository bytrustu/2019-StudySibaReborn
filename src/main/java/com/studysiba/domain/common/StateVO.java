package com.studysiba.domain.common;

import lombok.Data;

@Data
public class StateVO {
    // 글번호
    int no;
    // 상태코드
    String stateCode;
    // 반환 Object
    Object obj;
}
