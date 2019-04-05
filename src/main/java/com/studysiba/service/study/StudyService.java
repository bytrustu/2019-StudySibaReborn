package com.studysiba.service.study;

import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.study.StudyVO;

import java.util.List;

public interface StudyService {


    /*
     *  스터디 등록
     *  @Param studyVO
     *  @Return 스터디 등록에 대한 상태코드 반환
     */
    StateVO registerStudy(StudyVO studyVO) throws Exception;

    /*
     *  스터디 조회
     *  @Param no
     *  @Return 스터디번호에 대한 스터디정보 조회
     */
    StudyVO getStudyOne(int no);

    /*
     *  스터디 리스트 조회
     *  @Param pageVO
     *  @Return page정보에 대한 스터디리스트 반환
     */
    List<StudyVO> getStudyList(PageVO pageVO);

    /*
     *  스터디 수정
     *  @Param studyVO
     *  @Return 스터디 수정에 대한 상태코드 반환
     */
    StateVO updateStudy(StudyVO studyVO) throws Exception;

    /*
     *  스터디 삭제
     *  @Param studyVO
     *  @Return 스터디 삭제에 대한 상태코드 반환
     */
    StateVO deleteStudy(int no);
}
