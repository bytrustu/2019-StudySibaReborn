package com.studysiba.mapper.common;

import com.studysiba.domain.common.UploadVO;
import com.studysiba.domain.member.PointVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

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

    /*
     *  스터디시바 회원수 및 방문수 조회
     *  @Return 회원수 방문수 반환
     */
    HashMap<String,Integer> memberCount();

    /*
     *  회원 포인트 정보 조회
     *  @Param id
     *  @Return 회원 포인트 정보 반환
     */
    PointVO getMemberPointInfo(String id);

    /*
     *  회원 포인트 업데이트
     *  @Param pointVO
     *  @Return 포인트 업데이트 여부 반환
     */
    int setMemberPoint(PointVO pointVO);
}
