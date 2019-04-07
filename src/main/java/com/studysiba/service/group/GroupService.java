package com.studysiba.service.group;

import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.domain.group.StudyGroupVO;

import java.util.List;

public interface GroupService {

    /*
     *  그룹 리스트 이동
     *  @Return 참여중인 그룹 리스트 반환
     */
    List<GroupMemberVO> getGroupList();


    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 작성에 대한 상태코드 반환
     */
    StateVO writeGroupPost(GroupBoardVO groupBoardVO) throws Exception;

    /*
     *  그룹 공지사항 업데이트
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 업데이트 대한 상태코드 반환
     */
    StateVO updateGroupPost(GroupBoardVO groupBoardVO) throws Exception;

    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 삭제에 대한 상태코드 반환
     */
    StateVO deleteGroupPost(GroupBoardVO groupBoardVO);

    /*
     *  그룹 공지사항 리스트 조회
     *  @Param  no
     *  @Return  그룹 공지사항 리스트 반환
     */
    List<GroupBoardVO> getNoticeList(PageVO pageVO, int no);

    /*
     *  그룹 공지사항 게시글 조회
     *  @Param  no
     *  @Return  그룹 공지사항 게시글 정보 반환
     */
    GroupBoardVO getGroupPost(int no);

    /*
     *  그룹 탈퇴
     *  @Param  groupMemberVO
     *  @Return 그룹탈퇴에 대한 상태코드 반환
     */
    StateVO outGroup(GroupMemberVO groupMemberVO);
}
