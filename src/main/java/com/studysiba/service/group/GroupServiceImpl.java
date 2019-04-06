package com.studysiba.service.group;

import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.mapper.group.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    GroupMapper groupMapper;

    @Autowired
    HttpSession httpSession;

    /*
     *  그룹 리스트 이동
     *  @Return 참여중인 그룹 리스트 반환
     */
    @Override
    public List<GroupMemberVO> getGroupList() {
        if ( httpSession.getAttribute("id" ) == null ) return null;
        String id = (String) httpSession.getAttribute("id");
        List<GroupMemberVO> groupList = groupMapper.getGroupList(id);
        return groupList;
    }
}
