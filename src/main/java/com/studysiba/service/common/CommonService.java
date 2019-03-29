package com.studysiba.service.common;

import com.studysiba.domain.common.UploadVO;
import com.studysiba.domain.member.MemberVO;
import com.studysiba.domain.member.PointVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonService {

    /*
     *  구글 Social Login Url 반환
     */
    String getGoogleUrl();

    /*
     *  네이버 Social Login Url 반환
     */
    String getNaverUrl();

    /*
     *  유저 랭킹 1~3위 정보 조회
     */
    List<HashMap<String, Object>> viewUserTotalRanking() throws Exception;

    /*
     *  페이지 별 안내 게시글 반환
     *  @Param 메뉴
     *  @Return 안내글
     */
    HashMap<String,String> getIntroduceComment(String path);

    /*
     *  공통 파일 업로드
     *  @Param MultipartFile
     *  @Return 업로드 파일 이름
     */
    String uploadFile(MultipartFile multipartFile) throws Exception;

}
