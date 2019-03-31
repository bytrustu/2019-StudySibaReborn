package com.studysiba.service.common;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.config.SocialKeys;
import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.mapper.board.BoardMapper;
import com.studysiba.mapper.common.CommonMapper;
import com.studysiba.mapper.member.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
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
                introComment.put("bottomFirst","공지사항 및 이벤트 안내가 올라오는 알림게시판 입니다.");
                introComment.put("bottomSecond","스터디시바 관련 공지사항, 이벤트를 확인하실 수 있습니다.");
                break;
            case "community" :
                introComment.put("top","커뮤니티");
                introComment.put("bottomFirst","회원간의 정보공유 관련, 기타 자유게시판 입니다.");
                introComment.put("bottomSecond","사이트 이용규칙 및 약관에 위배되지 않는 범위 내에서 자유롭게 이용하실 수 있습니다.");
                break;
            case "study" :
                introComment.put("top","스터디참여");
                introComment.put("bottomFirst","스터디 개설 및 참여 할수있는 공간 입니다.");
                introComment.put("bottomSecond","사이트 이용규칙 및 약관에 위배되지 않는 범위 내에서 자유롭게 이용하실 수 있습니다.");
                break;
            case "group" :
                introComment.put("top","스터디그룹");
                introComment.put("bottomFirst","참여하고 있는 스터디그룹을 확인 할수있는 공간 입니다.");
                introComment.put("bottomSecond","사이트 이용규칙 및 약관에 위배되지 않는 범위 내에서 자유롭게 이용하실 수 있습니다.");
                break;
        }
        return introComment;
    }


    /*
     *  공통 파일 업로드
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
     *  페이지 정보 조회
     *  @Param menu, criteria
     *  @Return 페이지정보 반환
     */
    @Override
    public PageVO getPageInfomation(String menu, Criteria criteria) {
        PageVO pageVO = null;
        switch (menu) {
            case "notice" :
                pageVO = new PageVO(criteria, boardMapper.getPostCount(menu), 10, 3);
                pageVO.setMenu(menu);
                break;
            case "community" :
                pageVO = new PageVO(criteria, boardMapper.getPostCount(menu), 10, 3);
                pageVO.setMenu(menu);
                break;
        }
        return pageVO;
    }

}
