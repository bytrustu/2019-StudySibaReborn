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

    /*
     *  공통 파일 업로드
     *  @Param UploadVO
     *  @Return 파일등록상태
     */
    int contentUpdateFile(UploadVO uploadVO);

    /*
     *  파일 이름 조회
     *  @Param uldFno
     *  @Return 파일 이름 반환
     */
    String getPrevFileName(int uldFno);
}
