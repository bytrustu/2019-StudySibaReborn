package com.studysiba.mapper.group;

import com.studysiba.domain.group.GroupMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GroupMapper {

    /*
     *  그룹 리스트 조회
     *  @Return 참여중인 그룹 리스트 반환
     */
    ArrayList<GroupMemberVO> getGroupList(String id);
}
