package com.studysiba.mapper.study;

import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.domain.group.GroupMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface StudyMapper {


    /*
     *  스터디 등록
     *  @Param studyVO
     *  @Return 스터디 등록 여부
     */
    int registerStudy(StudyVO studyVO);

    /*
     *  가장 마지막에 등록된 스터디번호
     *  @Return MAX 스터디번호 반환
     */
    int getStudyMaxNum();

    /*
     *  스터디그룹 등록
     *  @Param studyVO
     *  @Return 스터디그룹 등록 여부
     */
    int registerGroup(StudyVO studyVO);

    /*
     *  스터디그룹 참여 등록
     *  @Param studyVO
     *  @Return 스터디그룹 참여 여부 반환
     */
    int registerGroupMember(StudyVO studyVO);

    /*
     *  스터디 총 개설수 조회
     *  @Param searchMap
     *  @Return 스터디 총 개설수 반환
     */
    int getStudyCount(HashMap<String,String> searchMap);

    /*
     *  스터디 조회
     *  @Param no
     *  @Return 스터디번호에 해당하는 스터디정보 조회
     */
    StudyVO getStudyOne(int no);

    /*
     *  스터디 리스트
     *  @Param pageVO
     *  @Return 페이지정보에 대한 스터디리스트 반환
     */
    List<StudyVO> getStudyList(PageVO pageVO);

    /*
     *  스터디그룹 업데이트
     *  @Param studyVO
     *  @Return 스터디그룹 업데이트 여부
     */
    int updateStudy(StudyVO studyVO);

    /*
     *  스터디삭제
     *  @Param studyVO
     *  @Return 스터디 삭제 여부 반환
     */
    int deleteStudy(StudyVO studyVO);

    /*
     *  스터디활성화
     *  @Param studyVO
     *  @Return 스터디 활성화 여부 반환
     */
    int activeStudy(StudyVO studyVO);

    /*
     *  스터디 참여
     *  @Param studyVO
     *  @Return 스터디 참여에 대한 결과 반환
     */
    int joinStudy(StudyVO studyVO);

    /*
     *  스터디 탈퇴
     *  @Param studyVO
     *  @Return 스터디 탈퇴에 대한 결과 반환
     */
    int outStudy(StudyVO studyVO);

    /*
     *  스터디 참여 확인
     *  @Param studyVO
     *  @Return 스터디에 참여중인지 확인 결과 반환
     */
    int alreadyStudy(StudyVO studyVO);

    /*
     *  스터디에 참여중인 멤버 리스트 조회
     *  @Param no
     *  @Return 스터디에 참여중인 멤버 리스트 반환
     */
    ArrayList<GroupMemberVO> getGroupMemberList(int no);

    /*
     *  스터디를 개설한 리더 아이디 조회
     *  @Param no
     *  @Return 스터디 리더 아이디 반환
     */
    String getLeaderId(int no);

    /*
     *  스터디 최신화
     *  @Param studyVO
     *  @Return 스터디 최신화 업데이트 여부 반환
     */
    int latestStudy(StudyVO studyVO);

    ArrayList<StudyVO> getAllStudyList();
}
