package com.studysiba.service.group;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.mapper.group.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 작성에 대한 상태코드 반환
     */
    @Override
    @Transactional
    public StateVO writeGroupPost(GroupBoardVO groupBoardVO) throws Exception {
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("NOTICE_WRITE_ERROR");
        // 접속중인 회원인지 확인
        if ( httpSession.getAttribute("id") == null ) return stateVO;
        System.out.println("1");
        groupBoardVO.setGrbId((String) httpSession.getAttribute("id"));
        // 데이터에 NULL 혹은 빈공간이 없는지 확인
        if ( !DataValidation.findEmptyValue(groupBoardVO,new String[]{"grdId","grdTitle","grdContent"})
                .equals("VALUES_STATE_GOOD") ) return stateVO;
        System.out.println("2");
        // 그룹의 리더가 맞는지 확인
        if ( groupMapper.checkGroupLeader(groupBoardVO) != 1 ) return stateVO;
        // 공지사항 작성
        System.out.println("3");
        int writeGroup = groupMapper.writeGroupPost(groupBoardVO);
        if ( writeGroup == 1 ) {
            System.out.println("4");
            stateVO.setStateCode("NOTICE_WRITE_SUCCESS");
            stateVO.setNo(groupBoardVO.getGrbGno());
            // 첨부파일이 있다면 업로드
            if ( groupBoardVO.getGrbFile() != null ) {
                groupBoardVO.setGrbNo(groupMapper.getMaxNoticeNum(groupBoardVO));
                groupBoardVO.setIsUpdateFile("false");
                String uploadState = groupFileUpload(groupBoardVO);
                if ( uploadState.equals("UPLOAD_STATE_GOOD") ) {
                    stateVO.setStateCode("NOTICE_WRITE_SUCCESS");
                }
            }
        }
        return stateVO;
    }

    /*
     *  그룹 공지사항 업데이트
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 업데이트 대한 상태코드 반환
     */
    @Override
    public StateVO updateGroupPost(GroupBoardVO groupBoardVO) {
//        StateVO stateVO = new StateVO();
//        stateVO.setStateCode("GROUPNOTICE_WRITE_ERROR");
//        if ( httpSession.getAttribute("id") == null ) return stateVO;
//        groupBoardVO.setGrbId((String) httpSession.getAttribute("id"));
//        if ( httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ){
//            groupBoardVO.setGrbId(groupMapper.getGroupLeaderId(groupBoardVO.getGrbNo()));
//        }
//        int updateGroup = groupMapper.updateGroupPost(groupBoardVO);
//        if ( updateGroup == 1 ) {
//            if ( groupBoardVO.getGrbFilename())
//        }

        return null;
    }

    /*
     *  그룹 공지사항 작성
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 삭제에 대한 상태코드 반환
     */
    @Override
    public StateVO deleteGroupPost(GroupBoardVO groupBoardVO) {
//        StateVO stateVO = new StateVO();
//        stateVO.setStateCode("GROUPNOTICE_WRITE_ERROR");
//        if ( httpSession.getAttribute("id") == null ) return stateVO;
//        groupBoardVO.setGrbId((String) httpSession.getAttribute("id"));
//        if ( httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ){
//            groupBoardVO.setGrbId(groupMapper.getGroupLeaderId(groupBoardVO.getGrbNo()));
//        }
//        int updateGroup = groupMapper.deleteGroupPost(groupBoardVO);
//        if ( updateGroup == 1 ) {
//            stateVO.setStateCode("GROUPNOTICE_DELETE_SUCCESS");
//        }

        return null;
    }

    /*
     *  그룹 공지사항 리스트 조회
     *  @Param  no
     *  @Return  그룹 공지사항 리스트 반환
     */
    @Override
    public List<GroupBoardVO> getNoticeList(PageVO pageVO, int no) {
        if ( pageVO.getCount() == 0 ) return null;
        HashMap<String, Integer> pageMap = new HashMap<>();
        pageMap.put("startRow", pageVO.getStartRow());
        pageMap.put("pageSize", pageVO.getPageSize());
        pageMap.put("no", no);
        List<GroupBoardVO> noticeList = groupMapper.getNoticeList(pageMap);
        // 지나간시각 문자열 처리
        for ( int i=0; i < noticeList.size(); i++ ) {
            noticeList.get(i).setLastTime(DataConversion.DurationFromNow(noticeList.get(i).getGrbDate()));
        }
        return noticeList;
    }



    public String groupFileUpload(GroupBoardVO groupBoardVO){
        String stateCode = null;
        if ( groupBoardVO.getGrbFile().isEmpty() ) return null;
        if ( httpSession.getAttribute("id") == null ) return null;

        String path = "C:\\upload\\studysiba\\group";
        File destdir = new File(path);
        String fileName = null;
        if ( !destdir.exists() ) destdir.mkdir();
//        //  JPG, JPEG, PNG, GIF, BMP 확장자 체크
//        if ( !DataValidation.checkImageFile(groupBoardVO.getGrbFile().getOriginalFilename()) ) return null;
        String uuid = DataConversion.returnUUID();
        String originFileName = groupBoardVO.getGrbFile().getOriginalFilename();
        groupBoardVO.setGrbUuid(uuid);
        groupBoardVO.setGrbFilename(originFileName);
        fileName = uuid+"_"+originFileName;
        File target = new File(path, fileName);
        try {
            FileCopyUtils.copy(groupBoardVO.getGrbFile().getBytes(), target);
            if ( groupBoardVO.getIsUpdateFile().equals("true") ) {
                // 이전 파일 삭제
                String prevFileName = groupMapper.getPrevFileName(groupBoardVO);
                File prevFile = new File(path, prevFileName);
                if (prevFile.exists()) {
                    prevFile.delete();
                }
            }
            int uploadState = groupMapper.updateGroupFilename(groupBoardVO);
            stateCode = uploadState == 1 ? "UPLOAD_STATE_SUCCESS" : "UPLOAD_STATE_ERROR";
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
        return stateCode;
    }
}
