package com.studysiba.service.study;

import com.studysiba.common.DataValidation;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.StateVO;
import com.studysiba.domain.study.StudyVO;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.mapper.study.StudyMapper;
import com.studysiba.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    @Resource
    CommonService commonService;

    @Resource
    CommonMapper commonMapper;

    @Resource
    StudyMapper studyMapper;

    @Autowired
    HttpSession httpSession;


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
        if ( httpSession.getAttribute("id") == null ) return stateVO;
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        // valdation check
        String[] checkNames = {"stdId","stdGroup","stdDivide","stdTitle","stdContent","stdAddress","stdPlace","stdStart","stdEnd","stdLimit","stdLat","stdLng"};
        if ( !DataValidation.findEmptyValue(stateVO,checkNames).equals("VALUES_STATE_GOOD") ) return stateVO;
        if ( studyVO.getStdAddress().contains("대한민국 ") ) {
            studyVO.setStdAddress( studyVO.getStdAddress().replaceFirst("대한민국 ",""));
        }
        // 스터디 등록
        int registerState = studyMapper.registerStudy(studyVO);
        if ( registerState == 1 ) {
            int maxNum = studyMapper.getStudyMaxNum();
            stateVO.setNo(maxNum);
            studyVO.setStdNo(maxNum);
            // 스터디그룹 등록
            registerState = studyMapper.registerGroup(studyVO);
            // 그룹인원 등록
            if ( registerState == 1 ) registerState = studyMapper.registerGroupMember(studyVO);
            // 파일업로드
            if ( registerState == 1 ) stateVO.setStateCode( commonService.contentUploadFile(studyVO.getStdFile(), "study", studyVO.getStdNo()) );
            if (stateVO.getStateCode().equals("UPLOAD_STATE_SUCCESS")) {
                stateVO.setStateCode("STUDY_REGISTER_SUCCESS");
                httpSession.setAttribute("stateCode","STUDY_REGISTER_SUCCESS");
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
        return studyMapper.getStudyOne(no);
    }

    /*
     *  스터디 리스트 조회
     *  @Param pageVO
     *  @Return page정보에 대한 스터디리스트 반환
     */
    @Override
    public List<StudyVO> getStudyList(PageVO pageVO) {
        if ( pageVO.getCount() <= 0 ) return null;
        List<StudyVO> studyList = studyMapper.getStudyList(pageVO);
        for ( int i=0; i<studyList.size(); i++ ) {
            String[] address = studyList.get(i).getStdAddress().split(" ");
            studyList.get(i).setStdAddress( address[0] + " " + studyList.get(i).getStdPlace());
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
        StateVO stateVO = new StateVO();
        stateVO.setStateCode("STUDY_UPDATE_ERROR");
        // 접속중인 회원인지 확인
        if ( httpSession.getAttribute("id") == null ) return stateVO;
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        // valdation check
        String[] checkNames = {"stdId","stdGroup","stdDivide","stdTitle","stdContent","stdAddress","stdPlace","stdStart","stdEnd","stdLimit","stdLat","stdLng"};
        if ( !DataValidation.findEmptyValue(stateVO,checkNames).equals("VALUES_STATE_GOOD") ) return stateVO;
        if ( studyVO.getStdAddress().contains("대한민국 ") ) {
            studyVO.setStdAddress( studyVO.getStdAddress().replaceFirst("대한민국 ",""));
        }
        // 스터디 수정
        int registerState = studyMapper.updateStudy(studyVO);
        if ( registerState == 1 ) {
            stateVO.setNo(studyVO.getStdNo());
            if ( studyVO.getUpdateFile().equals("true") ) {
                // 파일업로드
                if ( registerState == 1 ) stateVO.setStateCode( commonService.contentUpdateFile(studyVO.getStdFile(), "study", studyVO.getStdNo()) );
                if (stateVO.getStateCode().equals("UPLOAD_STATE_SUCCESS")) {
                    stateVO.setStateCode("STUDY_UPDATE_SUCCESS");

                }
            } else {
                stateVO.setStateCode("STUDY_UPDATE_SUCCESS");
            }
            httpSession.setAttribute("stateCode","STUDY_UPDATE_SUCCESS");
        }
        return stateVO;
    }

    /*
     *  스터디 삭제
     *  @Param studyVO
     *  @Return 스터디 삭제에 대한 상태코드 반환
     */
    @Override
    public StateVO deleteStudy(int no) {
        StateVO stateVO = new StateVO();
        stateVO.setNo(no);
        stateVO.setStateCode("STUDY_DELETE_ERROR");
        if ( httpSession.getAttribute("id") == null ) return stateVO;
        StudyVO studyVO = new StudyVO();
        studyVO.setStdNo(no);
        studyVO.setStdId((String) httpSession.getAttribute("id"));
        int deleteState = studyMapper.deleteStudy(studyVO);
        if ( deleteState == 1 ) stateVO.setStateCode("STUDY_DELETE_SUCCESS");
        return stateVO;
    }


}
