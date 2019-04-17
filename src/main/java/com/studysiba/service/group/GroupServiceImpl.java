package com.studysiba.service.group;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.group.GroupMemberVO;
import com.studysiba.domain.group.GroupMessageVO;
import com.studysiba.mapper.group.GroupMapper;
import com.studysiba.mapper.study.StudyMapper;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j
public class GroupServiceImpl implements GroupService {

    @Resource
    GroupMapper groupMapper;

    @Resource
    StudyMapper studyMapper;

    @Resource
    CommonService commonService;

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
        groupBoardVO.setGrbId((String) httpSession.getAttribute("id"));
        // 데이터에 NULL 혹은 빈공간이 없는지 확인
        if ( !DataValidation.findEmptyValue(groupBoardVO,new String[]{"grdId","grdTitle","grdContent"})
                .equals("VALUES_STATE_GOOD") ) return stateVO;
        // 관리자가 아니라면
        if ( !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) {
            // 그룹의 리더가 맞는지 확인
            if ( groupMapper.checkGroupLeader(groupBoardVO) != 1 ) return stateVO;
        } else {
            // 관리자라면 아이디를 리더 아이디로 변경한다.
            groupBoardVO.setGrbId(studyMapper.getLeaderId(groupBoardVO.getGrbGno()));
        }
        // 공지사항 작성
        int writeGroup = groupMapper.writeGroupPost(groupBoardVO);
        if ( writeGroup == 1 ) {
            groupBoardVO.setGrbTitle(DataConversion.changeSpChar(groupBoardVO.getGrbTitle()));
            groupBoardVO.setGrbContent(DataConversion.changeSpChar(groupBoardVO.getGrbContent()));
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

            StateVO scoreState = commonService.setPoint((String) httpSession.getAttribute("id"), 1000);
            if ( scoreState.getStateCode().contains("ERROR") ) log.info("포인트 설정에 에러가 발생했습니다.");
        }
        return stateVO;
    }

    /*
     *  그룹 공지사항 업데이트
     *  @Param  groupBoardVO
     *  @Return 스터디 공지사항 업데이트 대한 상태코드 반환
     */
    @Transactional
    @Override
    public StateVO updateGroupPost(GroupBoardVO groupBoardVO) throws Exception {
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("NOTICE_UPDATE_ERROR");
        // 접속중인 회원인지 확인
        if ( httpSession.getAttribute("id") == null ) return stateVO;
        groupBoardVO.setGrbId((String) httpSession.getAttribute("id"));
        // 데이터에 NULL 혹은 빈공간이 없는지 확인
        if ( !DataValidation.findEmptyValue(groupBoardVO,new String[]{"grdId","grdTitle","grdContent"})
                .equals("VALUES_STATE_GOOD") ) return stateVO;

        // 관리자가 아니라면
        if ( !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) {
            // 그룹의 리더가 맞는지 확인
            if ( groupMapper.checkGroupLeader(groupBoardVO) != 1 ) return stateVO;
        } else {
            // 관리자라면 아이디를 리더 아이디로 변경한다.
            groupBoardVO.setGrbId(studyMapper.getLeaderId(groupBoardVO.getGrbGno()));
        }
        // 공지사항 업데이트
        int writeGroup = groupMapper.updateGroupPost(groupBoardVO);
        if ( writeGroup == 1 ) {
            groupBoardVO.setGrbTitle(DataConversion.changeSpChar(groupBoardVO.getGrbTitle()));
            groupBoardVO.setGrbContent(DataConversion.changeSpChar(groupBoardVO.getGrbContent()));
            stateVO.setStateCode("NOTICE_UPDATE_SUCCESS");
            stateVO.setNo(groupBoardVO.getGrbGno());
            // 첨부파일이 있다면 업로드
            if ( groupBoardVO.getGrbFile() != null ) {
                groupBoardVO.setGrbNo(groupMapper.getMaxNoticeNum(groupBoardVO));
                // 이전 파일 삭제 플래그
                groupBoardVO.setIsUpdateFile("true");
                String uploadState = groupFileUpload(groupBoardVO);
                if ( uploadState.equals("UPLOAD_STATE_GOOD") ) {
                    stateVO.setStateCode("NOTICE_UPDATE_SUCCESS");
                }
            }
        }
        return stateVO;
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
            noticeList.get(i).setGrbTitle(DataConversion.changeOriginTag(noticeList.get(i).getGrbTitle()));
            noticeList.get(i).setGrbContent(DataConversion.changeOriginTag(noticeList.get(i).getGrbContent()));
        }
        return noticeList;
    }

    /*
     *  그룹 공지사항 게시글 조회
     *  @Param  no
     *  @Return  그룹 공지사항 게시글 정보 반환
     */
    @Override
    public GroupBoardVO getGroupPost(int no) {
        GroupBoardVO groupBoardVO = groupMapper.getGroupPost(no);
        groupBoardVO.setGrbTitle(DataConversion.changeOriginTag(groupBoardVO.getGrbTitle()));
        groupBoardVO.setGrbContent(DataConversion.changeOriginTag(groupBoardVO.getGrbContent()));
        return groupBoardVO;
    }

    /*
     *  그룹 탈퇴
     *  @Param  groupMemberVO
     *  @Return 그룹탈퇴에 대한 상태코드 반환
     */
    @Override
    public StateVO outGroup(GroupMemberVO groupMemberVO) {
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("GROUP_OUT_ERROR");
        stateVO.setNo(groupMemberVO.getGrmGno());
        String leader = studyMapper.getLeaderId(groupMemberVO.getGrmGno());
        // 탈퇴 신청한 사람이 리더 일경우 오류 발생
        if ( leader.equals(groupMemberVO.getGrmId()) ) return stateVO;
        if ( groupMemberVO.getGrmId() == null ) return stateVO;
        // 탈퇴 신청한사람이 세션아이디와 동일하거나
        if ( httpSession.getAttribute("id").equals(groupMemberVO.getGrmId()) ||
                // 그룹의 리더 이거나
                httpSession.getAttribute("id").equals(leader) ||
                // 관리자 여야 가능합니다.
                httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ){
            int outGroup = groupMapper.outGroup(groupMemberVO);
            if ( outGroup == 1 ) {
                stateVO.setStateCode("GROUP_OUT_SUCCESS");
            }
        }
        return stateVO;
    }




    /*
     *  그룹 메세지 전송
     *  @Param  no, httpSession
     *  @Return 그룹메세지 전송 정보 반환
     */
    @Override
    public GroupMessageVO sendGroupMessage(int no, String message, HttpSession session) {

        GroupMessageVO groupMessageVO = null;
        if ( message.equals("") ) return null;
        message = DataConversion.changeSpChar(message);
        boolean isGroupMember = isGroupMember(no, (String) session.getAttribute("id"));
        if ( isGroupMember || session.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) {
            groupMessageVO = new GroupMessageVO();
            groupMessageVO.setGrmId((String) session.getAttribute("id"));
            groupMessageVO.setMbrProfile((String) session.getAttribute("profile"));
            groupMessageVO.setMbrNick((String) session.getAttribute("nick"));
            groupMessageVO.setGrmGno(no);
            groupMessageVO.setGrmText(message);
            groupMessageVO.setGrmDate(DataConversion.currentTimestamp());
            int groupMessageState = groupMapper.sendGroupMessage(groupMessageVO);
            if ( groupMessageState == 0 ) return null;
        }
        return groupMessageVO;
    }

    /*
     *  그룹 메세지 리스트 조회
     *  @Param  no
     *  @Return 그룹메세지 리스트 반환
     */
    @Override
    public List<GroupMessageVO> getGroupMessageList(int no) {
        List<GroupMessageVO> groupMessageList = null;
        boolean isGroupMember = isGroupMember(no, (String) httpSession.getAttribute("id"));
        if ( isGroupMember || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) {
            groupMessageList = groupMapper.getGroupMessageList(no);
        }
        return groupMessageList;
    }


    public String groupFileUpload(GroupBoardVO groupBoardVO){
        String stateCode = null;
        if ( groupBoardVO.getGrbFile().isEmpty() ) return null;
        if ( httpSession.getAttribute("id") == null ) return null;

        String path = DataConversion.filePath() + "group/";
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
            Runtime.getRuntime().exec("chmod 644 "+path + fileName);
            if ( groupBoardVO.getIsUpdateFile().equals("true") ) {
                // 이전 파일 삭제
                String prevFileName = groupMapper.getPrevFileName(groupBoardVO);
                if ( prevFileName != null ) {
                    File prevFile = new File(path, prevFileName);
                    if (prevFile.exists()) {
                        prevFile.delete();
                    }
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


    /*
     *  그룹 멤버 확인 여부
     *  @Param  no, id
     *  @Return 그룹 멤버 확인 결과 반환
     */
    public boolean isGroupMember(int no, String id){
        ArrayList<GroupMemberVO> groupMemberList = studyMapper.getGroupMemberList(no);
        boolean isGroupMember = false;
        for ( GroupMemberVO member : groupMemberList ) {
            if ( member.getGrmId().equals(id) ) {
                isGroupMember = true;
                break;
            }
        }
        return isGroupMember;
    }

}
