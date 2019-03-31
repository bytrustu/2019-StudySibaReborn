package com.studysiba.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
    // 페이지번호
    private int pageNum;
    // 글검색 말머리 타입
    private String type;
    // 글검색 키워드
    private String keyword;
    // 페이지번호 초기 1
    public Criteria() {
        this(1);
    }
    public Criteria(int pageNum) {
        this.pageNum = pageNum;
    }

}
