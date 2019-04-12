package com.studysiba.service.study;

import com.studysiba.common.DataValidation;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.mapper.study.StudyMapper;
import com.studysiba.service.common.CommonService;
import com.studysiba.domain.group.GroupMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    @Resource
    CommonService commonService;

    @Resource
    StudyMapper studyMapper;

    @Autowired
    HttpSession httpSession;

    @Autowired
    HttpServletRequest request;


    /*
     *  스터디 등록
     *  @Param studyVO
     *  @Return 스터디 등록에 대한 상태코드 반환
     */
    @Override
    @Transactional
    public StateVO registerStudy(StudyVO studyVO) throws Exception {

        StateVO stateVO = new StateVO();
        stateVO.setStateCode("STUDY_REGISTER_ERROR");
        // 접속중인 회원인지 확인
        if (httpSession.getAttribute("id") == null) return stateVO;
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        // valdation check
        String[] checkNames = {"stdId", "stdGroup", "stdDivide", "stdTitle", "stdContent", "stdAddress", "stdPlace", "stdStart", "stdEnd", "stdLimit", "stdLat", "stdLng"};
        if (!DataValidation.findEmptyValue(stateVO, checkNames).equals("VALUES_STATE_GOOD")) return stateVO;
        if (studyVO.getStdAddress().contains("대한민국 ")) {
            studyVO.setStdAddress(studyVO.getStdAddress().replaceFirst("대한민국 ", ""));
        }
        // 스터디 등록
        int registerState = studyMapper.registerStudy(studyVO);
        if (registerState == 1) {
            int maxNum = studyMapper.getStudyMaxNum();
            stateVO.setNo(maxNum);
            studyVO.setStdNo(maxNum);
            // 스터디그룹 등록
            registerState = studyMapper.registerGroup(studyVO);
            // 그룹인원 등록
            if (registerState == 1) registerState = studyMapper.registerGroupMember(studyVO);
            // 파일업로드
            if (registerState == 1)
                stateVO.setStateCode(commonService.contentUploadFile(studyVO.getStdFile(), "study", studyVO.getStdNo()));
            if (stateVO.getStateCode().equals("UPLOAD_STATE_SUCCESS")) {
                stateVO.setStateCode("STUDY_REGISTER_SUCCESS");
                httpSession.setAttribute("stateCode", "STUDY_REGISTER_SUCCESS");
            }
        }
        return stateVO;
    }

    /*
     *  스터디 조회
     *  @Param no
     *  @Return 스터디번호에 대한 스터디정보 조회
     */
    @Override
    public StudyVO getStudyOne(int no) {
        StudyVO studyVO = studyMapper.getStudyOne(no);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String currentUrl = urlPathHelper.getOriginatingRequestUri(request);
        if (currentUrl.equals("/group/view")) {
            ArrayList<GroupMemberVO> groupMemberList = studyMapper.getGroupMemberList(no);
            boolean checkMember = false;
            if (httpSession.getAttribute("auth") != null) {
                if (httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                    checkMember = true;
                } else {
                    for (GroupMemberVO group : groupMemberList) {
                        if (group.getGrmId().equals(httpSession.getAttribute("id"))) {
                            checkMember = true;
                            break;
                        }
                    }
                }
            } else {
                checkMember = false;
            }
            if (!checkMember) studyVO = null;
        }
        if (studyVO == null) httpSession.setAttribute("stateCode", "GROUP_LOCATION_ERROR");
        return studyVO;
    }

    /*
     *  스터디 리스트 조회
     *  @Param pageVO
     *  @Return page정보에 대한 스터디리스트 반환
     */
    @Override
    public List<StudyVO> getStudyList(PageVO pageVO) {
        if (pageVO.getCount() <= 0) return null;
        List<StudyVO> studyList = studyMapper.getStudyList(pageVO);
        for (int i = 0; i < studyList.size(); i++) {
            String[] address = studyList.get(i).getStdAddress().split(" ");
            studyList.get(i).setStdAddress(address[0] + " " + studyList.get(i).getStdPlace());
        }
        return studyList;
    }


    /*
     *  스터디 등록
     *  @Param studyVO
     *  @Return 스터디 등록에 대한 상태코드 반환
     */
    @Override
    @Transactional
    public StateVO updateStudy(StudyVO studyVO) throws Exception {
        System.out.println(studyVO);
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("STUDY_UPDATE_ERROR");
        // 접속중인 회원인지 확인
        if (httpSession.getAttribute("id") == null) return stateVO;
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        // valdation check
        String[] checkNames = {"stdId", "stdGroup", "stdDivide", "stdTitle", "stdContent", "stdAddress", "stdPlace", "stdStart", "stdEnd", "stdLimit", "stdLat", "stdLng"};
        if (!DataValidation.findEmptyValue(stateVO, checkNames).equals("VALUES_STATE_GOOD")) return stateVO;
        if (studyVO.getStdAddress().contains("대한민국 ")) {
            studyVO.setStdAddress(studyVO.getStdAddress().replaceFirst("대한민국 ", ""));
        }

        // 관리자 일 경우 리더 아이디로 변경
        if (httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
            studyVO.setStdId(studyMapper.getLeaderId(studyVO.getStdNo()));
        }
        // 스터디 수정
        int updateState = studyMapper.updateStudy(studyVO);
        System.out.println("스터디수정여부 " + updateState);
        if (updateState == 1) {
            stateVO.setNo(studyVO.getStdNo());
            if (studyVO.getUpdateFile().equals("true")) {
                // 파일업로드
                if (updateState == 1)
                    stateVO.setStateCode(commonService.contentUpdateFile(studyVO.getStdFile(), "study", studyVO.getStdNo()));
                if (stateVO.getStateCode().equals("UPLOAD_STATE_SUCCESS")) {
                    stateVO.setStateCode("STUDY_UPDATE_SUCCESS");

                }
            } else {
                stateVO.setStateCode("STUDY_UPDATE_SUCCESS");
            }
            httpSession.setAttribute("stateCode", "STUDY_UPDATE_SUCCESS");
        }
        return stateVO;
    }

    /*
     *  스터디 삭제
     *  @Param studyVO
     *  @Return 스터디 삭제에 대한 상태코드 반환
     */
    @Override
    @Transactional
    public StateVO deleteStudy(int no, String type) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        if (type.equals("delete")) {
            stateVO.setStateCode("STUDY_DELETE_ERROR");
        } else {
            stateVO.setStateCode("STUDY_ACTIVE_ERROR");
        }
        if (httpSession.getAttribute("id") == null) return stateVO;
        StudyVO studyVO = new StudyVO();
        studyVO.setStdNo(no);
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        if (type.equals("delete")) {
            int deleteState = studyMapper.deleteStudy(studyVO);
            if (deleteState == 1) stateVO.setStateCode("STUDY_DELETE_SUCCESS");
        } else {
            int deleteState = studyMapper.activeStudy(studyVO);
            if (deleteState == 1) stateVO.setStateCode("STUDY_ACTIVE_SUCCESS");
        }

        return stateVO;
    }

    /*
     *  스터디 참여
     *  @Param no
     *  @Return 스터디 참여에 대한 상태코드 반환
     */
    @Override
    public StateVO joinStudy(int no) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        stateVO.setStateCode("STUDY_JOIN_ERROR");
        if (httpSession.getAttribute("id") == null) return stateVO;
        StudyVO studyVO = new StudyVO();
        studyVO.setStdNo(stateVO.getNo());
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        int joinState = studyMapper.alreadyStudy(studyVO);
        if (joinState == 1) {
            stateVO.setStateCode("STUDY_STATE_ALREADY");
            return stateVO;
        }
        joinState = studyMapper.joinStudy(studyVO);
        if (joinState == 1) {
            stateVO.setStateCode("STUDY_JOIN_SUCCESS");
            httpSession.setAttribute("stateCode", stateVO.getStateCode());
        }
        return stateVO;
    }

    /*
     *  스터디 탈퇴
     *  @Param no
     *  @Return 스터디 탈퇴에 대한 상태코드 반환
     */
    @Override
    public StateVO outStudy(int no) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        stateVO.setStateCode("STUDY_OUT_ERROR");
        if (httpSession.getAttribute("id") == null) return stateVO;
        StudyVO studyVO = new StudyVO();
        studyVO.setStdNo(stateVO.getNo());
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        int outStudy = studyMapper.outStudy(studyVO);
        if (outStudy == 1) {
            stateVO.setStateCode("STUDY_OUT_SUCCESS");
            httpSession.setAttribute("stateCode", stateVO.getStateCode());
        }
        return stateVO;
    }

    /*
     *  스터디에 참여중인 멤버 리스트 조회
     *  @Param no
     *  @Return 스터디에 참여중인 멤버 리스트 반환
     */
    @Override
    public List<GroupMemberVO> getGroupMemberList(int no) {
        return studyMapper.getGroupMemberList(no);
    }

    /*
     *  스터디 탈퇴
     *  @Param no
     *  @Return 스터디 탈퇴 상태코드 반환
     */
    @Override
    public StateVO latestStudy(int no) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        stateVO.setStateCode("STUDY_LATEST_ERROR");
        if (httpSession.getAttribute("id") == null) return stateVO;
        StudyVO studyVO = new StudyVO();
        studyVO.setStdNo(stateVO.getNo());
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        // 관리자 일 경우 리더 아이디로 변경
        if (httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
            studyVO.setStdId(studyMapper.getLeaderId(no));
        }
        int latestStudy = studyMapper.latestStudy(studyVO);
        if (latestStudy == 1) {
            stateVO.setStateCode("STUDY_LATEST_SUCCESS");
            httpSession.setAttribute("stateCode", stateVO.getStateCode());
        }
        return stateVO;
    }


}
