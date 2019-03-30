package com.studysiba.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum;

    private String type;
    private String keyword;

    public Criteria() {
        this(1);
    }

    public Criteria(int pageNum) {
        this.pageNum = pageNum;
    }

}
