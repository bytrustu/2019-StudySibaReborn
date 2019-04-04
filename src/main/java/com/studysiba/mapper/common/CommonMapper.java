package com.studysiba.mapper.common;

import com.studysiba.domain.common.UploadVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {

    /*
     *  공통 파일 업로드
     *  @Param UploadVO
     *  @Return 파일등록상태
     */
    int contentUploadFile(UploadVO uploadVO);

}
