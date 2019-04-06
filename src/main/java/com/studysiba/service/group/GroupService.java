package com.studysiba.service.group;

import com.studysiba.domain.group.GroupMemberVO;

import java.util.List;

public interface GroupService {

    /*
     *  그룹 리스트 이동
     *  @Return 참여중인 그룹 리스트 반환
     */
    List<GroupMemberVO> getGroupList();



}
