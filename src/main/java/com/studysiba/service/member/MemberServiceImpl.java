package com.studysiba.service.member;

import com.studysiba.common.DataConversion;
import com.studysiba.common.DataValidation;
import com.studysiba.config.SocialKeys;
import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import com.studysiba.mapper.member.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    HttpSession httpSession;

    @Autowired
    GoogleConnectionFactory googleConnectionFactory;
    @Autowired
    OAuth2Parameters oAuth2Parameters;

    OAuth2Operations oAuth2Operations;

    /*
     *  회원초대장전송
     *  @Param 아이디
     *  @Return 초대장발송여부
     */
    @Transactional
    @Override
    public boolean inviteUser(String mbrId) throws Exception {

        // 초대 여부 반환값
        boolean inviteState = false;

        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(mbrId);

        int registrationStatus = memberMapper.registrationStatus(memberVO);
        log.info("가입상태확인 : " + registrationStatus);

        // 회원등록되어있을경우 ( 0:ERROR , 1:진행 )
        if (registrationStatus == 1) {
            String checkStatus = memberMapper.getType(memberVO);
            log.info("회원타입확인 : " + checkStatus);

            // 회원가입 대기상태일경우 ( NONE )
            if (checkStatus.toUpperCase().equals("NONE")) {
                sendEmail(memberVO, "invite");
                inviteState = true;
            }
        }
        return inviteState;
    }

    /*
     *  회원비밀번호변경메일발송
     *  @Param 이메일
     *  @Return 메일발송여부
     */
    @Override
    public boolean sendMailPasswordChanger(String mbrEmail) throws Exception {
        boolean sendState = false;
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrEmail(mbrEmail);
        String checkMailById = memberMapper.checkMailState(memberVO);
        if (checkMailById != null) {
            sendState = true;
            memberVO.setMbrId(checkMailById);
            sendEmail(memberVO, "password");
        }
        return sendState;
    }


    /*
     *  메일전송
     *  @Param MemberVO
     */
    public void sendEmail(MemberVO memberVO, String type) throws Exception {
        // 인증코드 갱신

        MimeMessage mail = javaMailSender.createMimeMessage();

        // 이메일 & 코드 조회 memberVO.setMbrCode(DataConversion.returnUUID());
        //        memberMapper.renewAuthenticationCode(memberVO);
        memberVO.setMbrEmail(memberMapper.getEmail(memberVO));
        memberVO.setMbrCode(memberMapper.getCode(memberVO));
        log.info("회원이메일확인 : " + memberVO.getMbrEmail());
        log.info("회원코드확인 : " + memberVO.getMbrCode());

        StringBuffer htmlStr = new StringBuffer();
//        String siteUrl = "localhost:8282";
        String siteUrl = "studysiba.com";

        // 초대장 보내질 양식
        switch (type) {
            case "invite":
                htmlStr.append("<div style='width:100%; text-align:center; margin-top:50px;'>");
                htmlStr.append("<a href='https://" + siteUrl + "/member/mail/invite");
                htmlStr.append("/" + memberVO.getMbrId());
                htmlStr.append("/" + memberVO.getMbrCode());
                htmlStr.append("'>");
                htmlStr.append("<img src='https://i.imgur.com/3Vt5UPz.png' style='width:60%;max-width=400px'></a>");
                htmlStr.append("</div>");
                log.info("초대장 양식 : " + htmlStr.toString());
                // 초대장 제목
                mail.setSubject("[스터디시바] 초대장발급 - " + memberVO.getMbrId() + "님에게");
                break;

            case "password":
                htmlStr.append("<div style='width:100%; text-align:center; margin-top:50px;'>");
                htmlStr.append("<a href='https://" + siteUrl + "/member/mail/changepass");
                htmlStr.append("/" + memberVO.getMbrId());
                htmlStr.append("/" + memberVO.getMbrCode());
                htmlStr.append("'>");
                htmlStr.append("<img src='https://i.imgur.com/aNQ7TX3.png' style='width:60%;max-width=400px'></a>");
                htmlStr.append("</div>");
                log.info("초대장 양식 : " + htmlStr.toString());
                // 초대장 제목
                mail.setSubject("[스터디시바] 비밀번호재설정 - " + memberVO.getMbrId() + "님에게");
                break;
        }

        // 초대장 내용 타입
        mail.setText(htmlStr.toString(), "utf-8", "html");
        // 초대장 보내어질 이메일 주소
        mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(memberVO.getMbrEmail()));
        javaMailSender.send(mail);
    }

    /*
     *  회원인증확인
     *  @Param 아이디, 코드
     *  @Return 인증확인여부
     */
    @Transactional
    @Override
    public String emailAuthentication(String mbrId, String mbrCode) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(mbrId);
        memberVO.setMbrCode(mbrCode);
        // 회원인증확인
        int informationCheckStatus = memberMapper.informationCheckStatus(memberVO);
        if (informationCheckStatus == 1) {
            // 회원 활성화 및 코드갱신
            memberVO.setMbrCode(DataConversion.returnUUID());
            try {
                memberMapper.changeStatus(memberVO);
                String createPoint = createPoint(memberVO);
                if (createPoint.equals("POINT_CREATE_ERROR")) new Exception();
                httpSession.setAttribute("stateCode", "AUTH_STATE_SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                httpSession.setAttribute("stateCode", "AUTH_STATE_ERROR");
                return "AUTH_STATE_ERROR";
            }
        } else {
            httpSession.setAttribute("stateCode", "AUTH_STATE_ERROR");
        }
        return informationCheckStatus == 1 ? "AUTH_STATE_SUCCESS" : "AUTH_STATE_ERROR";
    }

    /*
     *  패스워드변경메일인증확인
     *  @Param 아이디, 코드
     *  @Return 인증확인여부
     */
    @Override
    public String recoveryPassword(String mbrId, String mbrCode) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(mbrId);
        memberVO.setMbrCode(mbrCode);
        // 회원인증확인
        int passwordMailState = memberMapper.informationCheckStatus(memberVO);
        if (passwordMailState == 1) {
            // 코드갱신
            memberVO.setMbrCode(DataConversion.returnUUID());
            memberMapper.renewAuthenticationCode(memberVO);
            httpSession.setAttribute("stateCode", "PASSAUTH_STATE_SUCCESS");
            httpSession.setAttribute("authId", memberVO.getMbrId());
        } else {
            httpSession.setAttribute("stateCode", "PASSAUTH_STATE_ERROR");
        }
        return passwordMailState == 1 ? "PASSAUTH_STATE_SUCCESS" : "PASSAUTH_STATE_ERROR";
    }

    /*
     *  회원가입
     *  @Param MemberVO
     *  @Return 회원가입절차에 따른 상태메세지
     */
    @Transactional
    @Override
    public String register(MemberVO memberVO) throws Exception {
        String stateCode = "";
        String[] variableNames = {"mbrId", "mbrPass", "mbrNick", "mbrEmail", "mbrProfile"};

        // 입력 된 데이터 NULL 및 공백확인 [ 모든 데이터가 있을경우 VALUES_STATE_GOOD ]
        stateCode = DataValidation.findEmptyValue(memberVO, variableNames);
        if (stateCode.equals("VALUES_STATE_GOOD")) {
            // 아이디 검증 [ 영어 또는 숫자 포함 / 문자 길이 12까지 ]
            if (!DataValidation.checkEngAndNum(memberVO.getMbrId()) || !DataValidation.textLengthComparison(12, memberVO.getMbrId())) {
                stateCode = "ID_STATE_ERROR";
                // 비밀번호 검증 [ 영어, 숫자 포함 / 문자 길이 5-16까지 ]
            } else if (!DataValidation.checkPassword(memberVO.getMbrPass())) {
                stateCode = "PASS_STATE_ERROR";
                // 닉네임 검증 [ 한글 또는 영어 또는 숫자 포함 / 문자크기 10까지 ]
            } else if (!DataValidation.checkOnlyCharacters(memberVO.getMbrNick()) || !DataValidation.textLengthComparison(10, memberVO.getMbrNick())) {
                stateCode = "NICK_STATE_ERROR";
                // 이메일 검증
            } else if (!DataValidation.checkEmail(memberVO.getMbrEmail())) {
                stateCode = "EMAIL_STATE_ERROR";
                // 프로필사진 확장자 검증  [ JPG, JPEG, PNG, GIF, BMP ]
            } else if (!DataValidation.checkImageFile(memberVO.getMbrProfile())) {
                stateCode = "PROFILE_STATE_ERROR";

                // 회원가입 진행
            } else {
                // 아이디가 이미 등록되어져 있는 경우
                if (memberMapper.idReduplicationCheck(memberVO) == 1) {
                    stateCode = "ID_STATE_USED";
                    // 아이디가 이메일 승인 대기중인 경우
                    if (stateCode.equals("ID_STATE_USED")) {
                        stateCode = memberMapper.emailApprovalStatus(memberVO).toUpperCase().equals("NONE") ? "ID_STATE_WAITAPPROVAL" : "ID_STATE_USED";
                    }
                } else {
                    // 아이디 중복 이 없을경우 이메일 중복 확인
                    if (memberMapper.emailReduplicationCheck(memberVO) == 1) {
                        stateCode = "EMAIL_STATE_USED";
                    } else {
                        if (memberMapper.nickReduplicationCheck(memberVO) == 1) {
                            stateCode = "NICK_STATE_USED";
                        } else {
                            // 회원정보가 등록이 된 경우 => 패스워드 암호화 및 인증코드 발급
                            memberVO.setMbrPass(passwordEncoder.encode(memberVO.getMbrPass()));
                            memberVO.setMbrCode(DataConversion.returnUUID());
                            stateCode = memberMapper.memberRegistration(memberVO) == 1 ? "MEMBER_STATE_SUCCESS" : "SYSTEM_STATE_ERROR";
                        }
                    }
                }
            }
        } else {
            // 데이터가 NULL 및 공백이 있을경우 상태코드로 변환
            stateCode = stateCode.toUpperCase().replace("MBR", "").concat("_STATE_EMPTY");
        }
        return stateCode;
    }

    /*
     *  회원로그인
     *  @Param MemberVO
     *  @Return 회원로그인 따른 상태메세지
     */
    @Override
    public String normalLoginAuthentication(MemberVO memberVO) {
        String stateCode = "LOGIN_STATE_ERROR";
        // 회원정보데이터 조회
        MemberVO memberData = memberMapper.viewMemberInformation(memberVO);
        if (memberData != null) {
            // 입력된 비밀번호와 회원정보데이터와 매칭
            if (passwordEncoder.matches(memberVO.getMbrPass(), memberData.getMbrPass())) {
                // 회원상태정보가 승인검토중일경우 ID_STATE_WAITAPPROVAL 반환
                if (memberData.getMbrType().toUpperCase().equals("NONE")) return "ID_STATE_WAITAPPROVAL";
                stateCode = "LOGIN_STATE_SUCCESS";
                // 오늘접속하지 않은경우 포인트 +1000
                int isLoggedToday = memberMapper.isLoggedToday(memberVO);
                if (isLoggedToday == 0) {
                    memberVO.setMbrPoint(1000);
                    memberMapper.updatePoint(memberVO);
                }
                // 접속시간갱신
                memberMapper.updateAccessTime(memberVO);
                // 방문정보등록
                memberMapper.visitRegistration(memberVO);
                // 방문 조회수
                int visitCount = memberMapper.totalVisitCountCheck(memberVO);
                // 포인트 및 순위 조회
                PointVO pointVO = memberMapper.viewUserRanking(memberVO);


                // 세션등록
                httpSession.setAttribute("auth", memberData.getMbrAuth());
                httpSession.setAttribute("type", memberData.getMbrType());
                httpSession.setAttribute("id", memberData.getMbrId());
                httpSession.setAttribute("nick", memberData.getMbrNick());
                httpSession.setAttribute("email", memberData.getMbrEmail());
                httpSession.setAttribute("profile", memberData.getMbrProfile());
                httpSession.setAttribute("connect", memberData.getMbrCode());
                httpSession.setAttribute("visit", visitCount);
                httpSession.setAttribute("stateCode", stateCode);
                httpSession.setAttribute("score", pointVO.getPntScore());
                httpSession.setAttribute("rank", pointVO.getPntRank());
                httpSession.setAttribute("alarm", "on");
            }
        }
        return stateCode;
    }

    /*
     *  미승인회원정보삭제
     *  @Param MemberVO
     *  @Return 회원로그인 따른 상태메세지
     */
    @Override
    public String deleteInformation(MemberVO memberVO) {
        int deleteState = memberMapper.deleteInformation(memberVO);
        return deleteState == 1 ? "INFODEL_STATE_SUCCESS" : "INFODEL_STATE_ERROR";
    }

    /*
     *  이메일인증을통한비밀번호변경
     *  @Param MemberVO
     *  @Return 비밀번호변경여부에 따른 상태메세지
     */
    @Transactional
    @Override
    public String changePasswordEmailAuth(MemberVO memberVO) {
        String changePasswordState = "PASS_CHANGE_ERROR";
        // 비밀번호 유효성 검사 실패시
        if (!DataValidation.checkPassword(memberVO.getMbrPass())) return changePasswordState;
        String encodePassword = passwordEncoder.encode(memberVO.getMbrPass());
        memberVO.setMbrPass(encodePassword);
        int changeState = memberMapper.changePasswordEmailAuth(memberVO);
        if (changeState == 1) {
            changePasswordState = "PASS_CHANGE_SUCCESS";
            httpSession.removeAttribute("authId");
            // 인증코드 갱신
            memberVO.setMbrCode(DataConversion.returnUUID());
            memberMapper.renewAuthenticationCode(memberVO);
        }
        return changePasswordState;
    }

    /*
     *  구글 소셜로그인
     *  @Param code
     *  @Return 소셜로그인로직처리에따른 상태매세지 반환
     */
    @Override
    public String googleSignInCallback(String code, String mbrType) throws Exception {

        oAuth2Operations = googleConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuth2Operations.exchangeForAccess(code, oAuth2Parameters.getRedirectUri(), null);

        String accessToken = accessGrant.getAccessToken();
        Long expireTime = accessGrant.getExpireTime();

        if (expireTime != null && expireTime < System.currentTimeMillis()) {
            accessToken = accessGrant.getRefreshToken();
            log.info("토큰 만료. 새 토큰 발급 = " + accessToken);
        }

        // 소셜 회원정보 조회
        Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
        Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
        PlusOperations plusOperations = google.plusOperations();
        Person profile = plusOperations.getGoogleProfile();

        // 소셜 로직 처리
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(profile.getId());
        memberVO.setMbrNick(profile.getDisplayName());
        memberVO.setMbrType(mbrType);
        String stateCode = processingSocialLogic(memberVO);

        // Access Token 취소
        String revokeUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken + "";
        URL url = new URL(revokeUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return stateCode;
    }

    /*
     *  카카오, 페이스북 소셜로그인
     *  @Param MemberVO
     *  @Return 소셜로그인로직처리에따른 상태매세지 반환
     */
    @Override
    public String postSocialSignInCallback(MemberVO memberVO) {
        String stateCode = processingSocialLogic(memberVO);
        return stateCode;
    }


    /*
     *  네이버 소셜로그인 토큰
     *  @Param code, state
     *  @Return 토큰반환
     */
    @Override
    public String getNaverAccessToken(String code, String state) throws Exception {
        String redirectURI = URLEncoder.encode(SocialKeys.getNaverRedirect(), "UTF-8");
        String accessToken = "";
        String tokenUrl;
        tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        tokenUrl += "client_id=" + SocialKeys.getNaverClientId() + "&client_secret=" + SocialKeys.getNaverClientSecret();
        tokenUrl += "&redirect_uri=" + redirectURI + "&code=" + code + "&state=" + state;
        URL url = new URL(tokenUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader br;
        System.out.print("responseCode=" + responseCode);
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();
        if (responseCode == 200) {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res.toString());
            accessToken = obj.get("access_token").toString();
        }
        return accessToken;
    }

    /*
     *  네이버 소셜로그인
     *  @Param accessToken
     *  @Return 소셜로그인로직처리에따른 상태매세지 반환
     */
    @Override
    public String naverSignInCallback(String accessToken, String mbrType) throws Exception {
        String header = "Bearer " + accessToken;
        String apiURL = "https://openapi.naver.com/v1/nid/me";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", header);
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response.toString());
        obj = (JSONObject) parser.parse(obj.get("response").toString());
        MemberVO memberVO = new MemberVO();

        memberVO.setMbrId(obj.get("id").toString());
        memberVO.setMbrNick(obj.get("nickname").toString());
        memberVO.setMbrType(mbrType);
        String stateCode = processingSocialLogic(memberVO);
        return stateCode;
    }

    /*
     *  공통 소셜로그인 로그인/회원가입 처리 및 세션등록
     *  @Param MemberVO
     *  @Return 상태메세지 반환
     */
    @Transactional
    public String processingSocialLogic(MemberVO memberVO) {
        String stateCode = "";
        int socialSignInState = memberMapper.socialSignInState(memberVO);
        if (socialSignInState == 0) {
            try {
                if (DataValidation.findEmptyValue(memberVO, new String[]{"mbrNick"}).equals("VALUES_STATE_GOOD")) {
                    String mbrNick = memberVO.getMbrNick().replace(" ", "");
                    // 소셜 회원닉네임이 이미 중복일경우 임시 닉네임적용
                    if (memberMapper.nickReduplicationCheck(memberVO) == 1)
                        mbrNick = "스터디" + Integer.toString(DataConversion.returnRanNum(999999));
                    // 소셜 회원닉네임 정보의 데이터크기가 클때 보정
                    if (!DataValidation.textLengthComparison(12, mbrNick))
                        mbrNick = DataValidation.textLengthReturns(12, mbrNick);
                    memberVO.setMbrNick(mbrNick);
                } else {
                    // 닉네임정보가 없을경우 임시 닉네임 적용
                    memberVO.setMbrNick("스터디" + Integer.toString(DataConversion.returnRanNum(99999)));
                }
                memberVO.setMbrAuth("NORMAL");
                // 랜덤패스워드 발급
                String randomPassword = UUID.randomUUID()+memberVO.getMbrId();
                memberVO.setMbrPass(passwordEncoder.encode(randomPassword));
                memberVO.setMbrProfile("profile-1.png");
                memberVO.setMbrEmail(memberVO.getMbrId() + "@studysiba.com");
                memberVO.setMbrCode(DataConversion.returnUUID());
                // 소셜 회원가입
                int socialSign = memberMapper.socialSign(memberVO);
                String createPoint = createPoint(memberVO);
                // 소셜 가입 실패시 에러코드 반환
                if (socialSign == 0 || createPoint.equals("POINT_CREATE_ERROR")) {
                    new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
                stateCode = "SOCIAL_JOIN_ERROR";
                httpSession.setAttribute("stateCode", stateCode);
                return stateCode;
            }
        }
        // 소셜가입 , 소셜로그인 상태코드
        stateCode = socialSignInState == 0 ? "SOCIAL_JOIN_SUCCESS" : "SOCIAL_LOGIN_SUCCESS";
        int visitCount = memberMapper.totalVisitCountCheck(memberVO);
        memberVO = memberMapper.memberSocialInformation(memberVO);
        PointVO pointVO = memberMapper.viewUserRanking(memberVO);

        // 오늘접속하지 않은경우 포인트 +1000
        int isLoggedToday = memberMapper.isLoggedToday(memberVO);
        if (isLoggedToday == 0) {
            memberVO.setMbrPoint(1000);
            memberMapper.updatePoint(memberVO);
        }
        // 접속시간갱신
        memberMapper.updateAccessTime(memberVO);
        // 방문정보등록
        memberMapper.visitRegistration(memberVO);

        httpSession.setAttribute("auth", memberVO.getMbrAuth());
        httpSession.setAttribute("type", memberVO.getMbrType());
        httpSession.setAttribute("id", memberVO.getMbrId());
        httpSession.setAttribute("nick", memberVO.getMbrNick());
        httpSession.setAttribute("email", memberVO.getMbrEmail());
        httpSession.setAttribute("profile", memberVO.getMbrProfile());
        httpSession.setAttribute("connect", memberVO.getMbrCode());
        httpSession.setAttribute("visit", visitCount);
        httpSession.setAttribute("stateCode", stateCode);
        httpSession.setAttribute("score", pointVO.getPntScore());
        httpSession.setAttribute("rank", pointVO.getPntRank());
        httpSession.setAttribute("alarm", "on");
        return stateCode;
    }


    /*
     *  포인트 생성
     *  @Param MemberVO
     *  @Return 포인트처리코드
     */
    public String createPoint(MemberVO memberVO) {
        String pointState = "POINT_CREATE_ERROR";
        try {
            if (memberMapper.createPoint(memberVO) == 0) new Exception();
            pointState = "POINT_CREATE_SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            ;
        } finally {
            return pointState;
        }
    }

    /*
     *  포인트 증가/감소
     *  @Param MemberVO
     *  @Return 포인트처리코드
     */
    public String updatePoint(MemberVO memberVO) {
        String pointState = "POINT_UPDATE_ERROR";
        try {
            if (memberVO.getMbrId().equals((String) httpSession.getAttribute("id")) || httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                if (memberMapper.updatePoint(memberVO) == 0) new Exception();
                pointState = "POINT_UPDATE_SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return pointState;
        }
    }

    /*
     *  포인트 설정
     *  @Param MemberVO
     *  @Return 포인트처리코드
     */
    public String setPoint(MemberVO memberVO) {
        String pointState = "POINT_UPDATE_ERROR";
        try {
            if (httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN")) {
                if (memberMapper.setPoint(memberVO) == 0) new Exception();
                pointState = "POINT_UPDATE_SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return pointState;
        }
    }

    /*
     *  회원정보변경
     *  @Param  변경타입, 아이디, 변경값
     *  @Return 변경여부에 따른 상태코드반환
     */
    @Override
    public String changeUserInformation(String changeType, String mbrId, String changeValue) throws Exception {
        String stateCode = "INFORMATION_CHANGE_ERROR";
        int resultState = 0;
        // 동일한 아이디와 관리자만 변경 가능
        if ( !httpSession.getAttribute("id").equals(mbrId) ){
            if ( !httpSession.getAttribute("auth").toString().toUpperCase().equals("ADMIN") ) return stateCode;
        }
        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(mbrId);
        switch (changeType) {
            case "password" :
                memberVO.setMbrPass(changeValue);
                // 소셜회원 일경우 비밀번호 변경 불가능
                if ( !( httpSession.getAttribute("type").equals("NORMAL") || httpSession.getAttribute("auth").equals("ADMIN") ) ) return "SOCIAL_PASSWORD_ERROR";
                // 아이디 패스워드 공백 및 NULL 체크
                if ( !DataValidation.findEmptyValue(memberVO, new String[]{"mbrId","mbrPass"}).equals("VALUES_STATE_GOOD") ) break;
                // 패스워드 유효성 검사
                if ( !DataValidation.checkPassword(memberVO.getMbrPass()) ) break;
                // 패스워드 암호화
                memberVO.setMbrPass(passwordEncoder.encode(memberVO.getMbrPass()));
                // 패스워드 변경
                resultState = memberMapper.updatePassword(memberVO);
                break;
            case "nick":
                memberVO.setMbrNick(changeValue);
                // 아이디 닉네임 공백 및 NULL 체크
                if ( !DataValidation.findEmptyValue(memberVO, new String[]{"mbrId","mbrNick"}).equals("VALUES_STATE_GOOD") ) break;
                // 닉네임 중복 확인
                if ( memberMapper.nickReduplicationCheck(memberVO) == 1 ) return "NICK_STATE_USED";
                // 닉네임 유효성 검사
                if ( !DataValidation.checkOnlyCharacters(memberVO.getMbrNick()) ) break;
                // 닉네임 크기 검사
                if ( !DataValidation.textLengthComparison(12,memberVO.getMbrNick()) ) break;
                // 닉네임 변경
                resultState = memberMapper.updateNickname(memberVO);
                // 닉네임 변경시 세션 재등록
                if ( resultState == 1 ) httpSession.setAttribute("nick", memberVO.getMbrNick());
                break;
            case "profile":
                memberVO.setMbrProfile(changeValue);
                // 아이디 프로필사진 공백 및 NULL 체크
                if ( !DataValidation.findEmptyValue(memberVO, new String[]{"mbrId","mbrProfile"}).equals("VALUES_STATE_GOOD") ) break;
                // 프로필사진 변경
                resultState = memberMapper.updateProfile(memberVO);
                // 프로필사진 변경시 세션 재등록
                if ( resultState == 1 ) httpSession.setAttribute("profile", memberVO.getMbrProfile());
                break;
            case "auth":
                break;
        }
        stateCode = resultState == 1 ? "INFORMATION_CHANGE_SUCCESS" :  "INFORMATION_CHANGE_ERROR";
        return stateCode;
    }

    /*
     *  회원로그아웃
     *  @Param memberVO
     *  @Return 회원로그아웃 상태코드 반환
     */
    @Override
    public String userLogout(MemberVO memberVO) {
        if ( !httpSession.getAttribute("id").equals(memberVO.getMbrId()) ) return "LOGOUT_STATE_ERROR";
        memberMapper.changeLogoutLog((String) httpSession.getAttribute("id"));
        httpSession.invalidate();
        httpSession.setAttribute("stateCode", "LOGOUT_STATE_SUCCESS");
        return "LOGOUT_STATE_SUCCESS";
    }

    /*
     *  회원접속정보갱신
     *  @Param mbrId
     *  @Return 회원접속정보갱신여부반환
     */
    @Override
    public boolean isConnectUpdate(String mbrId) {
        if ( httpSession.getAttribute("id") == null ) return false;
        if ( !httpSession.getAttribute("id").toString().equals(mbrId) ) return false;
        int isConnectUpdate= memberMapper.isConnectUpdate(mbrId);
        if ( isConnectUpdate == 1 ) return true;
        else return false;
    }


}
