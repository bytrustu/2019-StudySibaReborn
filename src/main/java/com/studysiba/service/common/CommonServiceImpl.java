package com.studysiba.service.common;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.config.SocialKeys;
import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.common.UploadVO;
import com.studysiba.domain.group.GroupBoardVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.mapper.board.BoardMapper;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.mapper.group.GroupMapper;
import com.studysiba.mapper.member.MemberMapper;
import com.studysiba.mapper.study.StudyMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j
public class CommonServiceImpl implements CommonService {

    @Resource
    CommonMapper commonMapper;

    @Resource
    MemberMapper memberMapper;

    @Resource
    BoardMapper boardMapper;

    @Resource
    StudyMapper studyMapper;

    @Resource
    GroupMapper groupMapper;

    @Autowired
    HttpSession httpSession;

    @Autowired
    GoogleConnectionFactory googleConnectionFactory;
    @Autowired
    OAuth2Parameters oAuth2Parameters;
    private OAuth2Operations oAuth2Operations;

    /*
     *  구글 Social Login Url
     */
    @Override
    public String getGoogleUrl() {
        oAuth2Operations = googleConnectionFactory.getOAuthOperations();
        String googleUrl = oAuth2Operations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        return googleUrl;
    }

    /*
     *  네이버 Social Login Url
     */
    @Override
    public String getNaverUrl() {
        String clientId = SocialKeys.getNaverClientId();
        String redirectURI = null;
        try {
            redirectURI = URLEncoder.encode(SocialKeys.getNaverRedirect(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        apiURL += "&client_id=" + clientId;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&state=" + state;
        return apiURL;
    }

    /*
     *  1~3위 유저 랭킹 정보 조회
     */
    @Override
    public List<PointVO> viewUserTotalRanking() throws Exception {
        List<PointVO> userRankingList = new ArrayList<>();
        userRankingList = memberMapper.viewUserTotalRanking();
        return userRankingList;
    }

    /*
     *  페이지 별 안내 게시글 반환
     *  @Param 메뉴
     *  @Return 안내글
     */
    @Override
    public HashMap<String, String> getIntroduceComment(String path) {
        HashMap<String, String> introComment = new HashMap<>();
        switch ( path ) {
            case "notice" :
                introComment.put("top","공지사항");
                introComment.put("bottomFirst","공지사항 및 이벤트 안내가 올라오는 알림게시판 이예요!");
                introComment.put("bottomSecond","스터디시바 관련 공지사항, 이벤트를 확인할 수 있습니다.");
                break;
            case "community" :
                introComment.put("top","커뮤니티");
                introComment.put("bottomFirst","회원간의 정보공유 관련, 기타 자유게시판 이예요!");
                introComment.put("bottomSecond","사이트 이용규칙 및 약관에 위배되지 않는 범위 내에서 자유롭게 이용할 수 있습니다.");
                break;
            case "study" :
                introComment.put("top","스터디참여");
                introComment.put("bottomFirst","스터디 개설하고 참여 할수있는 공간 이예요!");
                introComment.put("bottomSecond","스터디참여에서 관심있는 스터디를 찾아보세요.");
                break;
            case "group" :
                introComment.put("top","스터디그룹");
                introComment.put("bottomFirst","참여하고 있는 스터디를 이용할 수 있어요!");
                introComment.put("bottomSecond","스터디그룹에서 참여중인 멤버들과 다양한 정보를 나누어 보세요.");
                break;
        }
        return introComment;
    }


    /*
     *  CK에디터 공통 파일 업로드
     *  @Param MultipartFile
     *  @Return UploadVO
     */
    @Transactional
    @Override
    public String uploadFile(MultipartFile multipartFile, String menu) throws Exception {

        if ( multipartFile.isEmpty() ) return null;
        if ( httpSession.getAttribute("id") == null ) return null;

        String path = "C:\\upload\\studysiba\\" + menu;
        File destdir = new File(path);
        String fileName = null;
        if ( !destdir.exists() ) destdir.mkdir();
            //  JPG, JPEG, PNG, GIF, BMP 확장자 체크
            if ( !DataValidation.checkImageFile(multipartFile.getOriginalFilename()) ) return null;
            fileName = DataConversion.returnUUID()+"_"+multipartFile.getOriginalFilename();
            File target = new File(path, fileName);
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), target);
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        return fileName;
    }


    /*
     *  게시판 공통 파일 업로드
     *  @Param MultipartFile, menu, no
     *  @Return 업로드 상태코드
     */
    @Transactional
    @Override
    public String contentUploadFile(MultipartFile multipartFile, String menu, int no) throws Exception {

        String stateCode = "UPLOAD_STATE_ERROR";
        if ( multipartFile.isEmpty() ) return stateCode;
        if ( httpSession.getAttribute("id") == null ) return stateCode;

        String path = "C:\\upload\\studysiba\\" + menu;
        File destdir = new File(path);
        String fileName = null;
        if ( !destdir.exists() ) destdir.mkdir();
        //  JPG, JPEG, PNG, GIF, BMP 확장자 체크
        if ( !DataValidation.checkImageFile(multipartFile.getOriginalFilename()) ) return stateCode;
        String uuid = DataConversion.returnUUID();
        String originFileName = multipartFile.getOriginalFilename();
        fileName = uuid+"_"+originFileName;
        File target = new File(path, fileName);
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), target);
            UploadVO uploadVO = new UploadVO();
            uploadVO.setUldId((String) httpSession.getAttribute("id"));
            uploadVO.setUldFno(no);
            uploadVO.setUldType(menu);
            uploadVO.setUldText(menu);
            uploadVO.setUldUuid(uuid);
            uploadVO.setUldFilename(originFileName);
            int uploadState = commonMapper.contentUploadFile(uploadVO);
            stateCode = uploadState == 1 ? "UPLOAD_STATE_SUCCESS" : "UPLOAD_STATE_ERROR";
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
        return stateCode;
    }

    /*
     *  게시판 공통 파일 업로드
     *  @Param MultipartFile, menu, no
     *  @Return 업로드 상태코드
     */
    @Transactional
    @Override
    public String contentUpdateFile(MultipartFile multipartFile, String menu, int no) throws Exception {

        String stateCode = null;
        if ( multipartFile.isEmpty() ) return null;
        if ( httpSession.getAttribute("id") == null ) return null;

        String path = "C:\\upload\\studysiba\\" + menu;
        File destdir = new File(path);
        String fileName = null;
        if ( !destdir.exists() ) destdir.mkdir();
        //  JPG, JPEG, PNG, GIF, BMP 확장자 체크
        if ( !DataValidation.checkImageFile(multipartFile.getOriginalFilename()) ) return null;
        String uuid = DataConversion.returnUUID();
        String originFileName = multipartFile.getOriginalFilename();
        fileName = uuid+"_"+originFileName;
        File target = new File(path, fileName);
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), target);
            UploadVO uploadVO = new UploadVO();
            uploadVO.setUldId((String) httpSession.getAttribute("id"));
            if ( httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) {
                uploadVO.setUldId( studyMapper.getLeaderId(no) );
            }
            uploadVO.setUldFno(no);
            uploadVO.setUldType(menu);
            uploadVO.setUldText(menu);
            uploadVO.setUldUuid(uuid);
            uploadVO.setUldFilename(originFileName);
            // 이전 파일 삭제
            String prevFileName = commonMapper.getPrevFileName(uploadVO.getUldFno());
            File prevFile = new File(path, prevFileName);
            if ( prevFile.exists() ) {
                prevFile.delete();
            }
            int uploadState = commonMapper.contentUpdateFile(uploadVO);
            stateCode = uploadState == 1 ? "UPLOAD_STATE_SUCCESS" : "UPLOAD_STATE_ERROR";
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
        return stateCode;
    }

    /*
     *  페이지 정보 조회
     *  @Param menu, criteria
     *  @Return 페이지정보 반환
     */
    @Override
    public PageVO getPageInfomation(String menu, Criteria criteria) {
        PageVO pageVO = null;
        HashMap<String, String> searchMap = new HashMap<>();
        searchMap.put("menu",menu);
        searchMap.put("keyword",criteria.getKeyword());
        searchMap.put("type", criteria.getType());
        switch (menu) {
            case "notice" :
                pageVO = new PageVO(criteria, boardMapper.getPostCount(searchMap), 10, 3);
                pageVO.setMenu(menu);
                break;
            case "community" :
                pageVO = new PageVO(criteria, boardMapper.getPostCount(searchMap), 10, 3);
                pageVO.setMenu(menu);
                break;
            case "study" :
                pageVO = new PageVO(criteria, studyMapper.getStudyCount(searchMap), 10, 3);
        }
        return pageVO;
    }

    @Override
    public PageVO getGroupPageInfomation(Criteria criteria, int no) {
        PageVO pageVO = new PageVO(criteria, groupMapper.getNoticeCount(no), 5,3);
        return pageVO;
    }

    @Override
    public HashMap<String, Object> downloadFile(String menu, int no) {
        String path = "C:\\upload\\studysiba\\" + menu;
        org.springframework.core.io.Resource resource = null;
        HashMap<String, Object> downloadMap = new HashMap<>();
        switch (menu) {
            case "group" :
                GroupBoardVO groupBoardVO = groupMapper.getGroupPost(no);
                String fileName = groupBoardVO.getGrbUuid()+"_"+groupBoardVO.getGrbFilename();
                resource = new FileSystemResource(path+"\\"+fileName);
                log.info("resource : " + resource );
                String resourceName = groupBoardVO.getGrbFilename();
                HttpHeaders headers = new HttpHeaders();
                try {
                    headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
                } catch ( UnsupportedEncodingException e ) {
                    e.printStackTrace();
                }
                downloadMap.put("resource", resource);
                downloadMap.put("headers", headers);
                break;
        }
        return downloadMap;
    }

}
