package com.studysiba.mapper.group;

import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.domain.group.GroupMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface GroupMapper {

    /*
     *  그룹 리스트 조회
     *  @Return 참여중인 그룹 리스트 반환
     */
    ArrayList<GroupMemberVO> getGroupList(String id);

    /*
     *  스터디 리더 확인 조회
     *  @Param groupBoardVO
     *  @Return 스터디리더인지 확인여부 반환
     */
    int checkGroupLeader(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 등록
     *  @Param groupBoardVO
     *  @Return 그룹 공지사항 등록여부 반환
     */
    int writeGroupPost(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 등록
     *  @Param groupBoardVO
     *  @Return 그룹 공지사항 등록여부 반환
     */
    int updateGroupPost(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 파일이름조회
     *  @Param groupBoardVO
     *  @Return 파일이름 반환
     */
    String getPrevFileName(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 파일 업데이트
     *  @Param groupBoardVO
     *  @Return 그룹 공지사항 파일 업데이트 여부 반환
     */
    int updateGroupFilename(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 가장 최근글번호 조회
     *  @Param groupBoardVO
     *  @Return 그룹 공지사항 가장 최근글번호 반환
     */
    int getMaxNoticeNum(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 리스트 조회
     *  @Param  no
     *  @Return  그룹 공지사항 리스트 반환
     */
    ArrayList<GroupBoardVO> getNoticeList(HashMap<String, Integer> pageMap);

    /*
     *  그룹 공지사항 글카운트 조회
     *  @Param  no
     *  @Return  그룹 공지사항 글카운트 반환
     */
    int getNoticeCount(int no);

    /*
     *  그룹 공지사항 게시글 조회
     *  @Param  no
     *  @Return  그룹 공지사항 게시글 정보 반환
     */
    GroupBoardVO getGroupPost(int no);


    /*
     *  그룹 탈퇴
     *  @Param  groupMemberVO
     *  @Return 그룹탈퇴 여부 반환
     */
    int outGroup(GroupMemberVO groupMemberVO);

    /*
     *  그룹 메세지 전송
     *  @Param  groupMessageVO
     *  @Return 그룹메세지 등록여부 반환
     */
    int sendGroupMessage(GroupMessageVO groupMessageVO);

    /*
     *  그룹 메세지 리스트 조회
     *  @Param  no
     *  @Return 그룹 메세지 리스트 반환
     */
    ArrayList<GroupMessageVO> getGroupMessageList(int no);
}
