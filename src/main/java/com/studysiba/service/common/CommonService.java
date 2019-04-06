package com.studysiba.service.common;

import com.studysiba.domain.common.Criteria;
import com.studysiba.domain.common.PageVO;
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
    List<PointVO> viewUserTotalRanking() throws Exception;

    /*
     *  페이지 별 안내 게시글 반환
     *  @Param 메뉴
     *  @Return 안내글
     */
    HashMap<String,String> getIntroduceComment(String path);

    /*
     *  공통 파일 업로드
     *  @Param MultipartFile, menu
     *  @Return 업로드 파일 이름
     */
    String uploadFile(MultipartFile multipartFile, String menu) throws Exception;

    /*
     *  게시판 공통 파일 업로드
     *  @Param MultipartFile, menu, no
     *  @Return UploadVO
     */
    String contentUploadFile(MultipartFile multipartFile, String menu, int no) throws Exception;

    /*
     *  게시판 공통 파일 업데이트
     *  @Param MultipartFile, menu, no
     *  @Return UploadVO
     */
    String contentUpdateFile(MultipartFile multipartFile, String menu, int no) throws Exception;


    /*
     *  페이지 정보 조회
     *  @Param menu, criteria
     *  @Return 페이지정보 반환
     */
    PageVO getPageInfomation(String menu, Criteria criteria);

    /*
     *  그룹 공지사항 페이지 정보 조회
     *  @Param criteria, no
     *  @Return 페이지정보 반환
     */
    PageVO getGroupPageInfomation(Criteria criteria, int no);
}
