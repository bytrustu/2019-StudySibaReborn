package com.studysiba.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageVO {

    // 게시글수
    private int count;
    // 한페이지에보여질행수
    private int pageSize;
    // 시작행번호
    private int startRow;
    // 끝행번호
    private int endRow;
    // 총페이지수
    private int pageCount;
    // 한페이지에보여질페이지수
    private int pageBlock;
    // 시작페이지번호
    private int startPage;
    // 끝페이지번호
    private int endPage;
    private Criteria criteria;
    private String menu;

    public PageVO(Criteria criteria, int count, int pageSize, int pageBlock) {
        this.criteria = criteria;
        this.count = count;
        this.pageSize = pageSize;
        this.pageBlock = pageBlock;
        this.pageCount = count / pageSize + ( count % pageSize == 0 ? 0 : 1 );
        this.startPage = ( ( criteria.getPageNum() -1 ) / pageBlock ) * pageBlock + 1;
        int endPage = this.startPage + pageBlock - 1;
        if ( endPage > pageCount ) endPage = pageCount;
        this.endPage = endPage;
        this.startRow = ( criteria.getPageNum() - 1 ) * pageSize;
        this.endRow = this.startRow + pageSize - 1;
    }
}
