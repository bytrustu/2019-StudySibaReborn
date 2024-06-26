package com.studysiba.service.study;

import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.domain.group.GroupMemberVO;

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
    StudyVO getStudyOne(int no) throws Exception;

    /*
     *  스터디 리스트 조회
     *  @Param pageVO
     *  @Return page정보에 대한 스터디리스트 반환
     */
    List<StudyVO> getStudyList(PageVO pageVO) throws Exception;

    /*
     *  스터디 수정
     *  @Param studyVO
     *  @Return 스터디 수정에 대한 상태코드 반환
     */
    StateVO updateStudy(StudyVO studyVO) throws Exception;

    /*
     *  스터디 삭제
     *  @Param no
     *  @Return 스터디 삭제에 대한 상태코드 반환
     */
    StateVO deleteStudy(int no, String type) throws Exception;

    /*
     *  스터디 참여
     *  @Param no
     *  @Return 스터디 참여에 대한 상태코드 반환
     */
    StateVO joinStudy(int no) throws Exception;

    /*
     *  스터디 탈퇴
     *  @Param no
     *  @Return 스터디 탈퇴에 대한 상태코드 반환
     */
    StateVO outStudy(int no) throws Exception;

    /*
     *  스터디에 참여중인 멤버 리스트 조회
     *  @Param no
     *  @Return 스터디에 참여중인 멤버 리스트 반환
     */
    List<GroupMemberVO> getGroupMemberList(int no) throws Exception;

    /*
     *  스터디 탈퇴
     *  @Param no
     *  @Return 스터디 탈퇴 상태코드 반환
     */
    StateVO latestStudy(int no) throws Exception;
}
