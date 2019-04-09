<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="sub-page">


    <div class="admin-subject">
        <span class="admin-topcomment">${intro.top}</span>
        <span class="admin-bottomcomment">
            ${intro.bottomFirst}<br/>
            ${intro.bottomSecond}
        </span>
    </div>


    <div class="board-box group-box">
        <div class="admin-top">

            <div class="col-12 mt-2 mb-4 text-center">
                <button class="btn btn-warning btn-sm m-0 admin-divide">전체</button>
                <button class="btn btn-warning btn-sm m-0 admin-divide">회원관리</button>
                <button class="btn btn-warning btn-sm m-0 admin-divide">게시판관리</button>
                <button class="btn btn-warning btn-sm m-0 admin-divide">스터디관리</button>
                <button class="btn btn-warning btn-sm m-0 admin-divide">그룹관리</button>
                <button class="btn btn-warning btn-sm m-0 admin-divide">메세지관리</button>
            </div>


        </div>

        <div class="board-table st-table admin-table">


            <c:choose>
                <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/main' ) }">
                    <div class="row stv-row admin-row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="stv-line"></div>
                                    <h3 class="admin-title">데이터통계</h3>
                                    <h4 class="admin-subtitle">최근1주일</h4>
                                </div>
                                <div class="col-md-8">
                                    <div id="dataChart"></div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row stv-row admin-row">
                        <hr class="admin-hr">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="stv-line"></div>
                                    <h3 class="admin-title">회원통계</h3>
                                    <h4 class="admin-subtitle">최근1주일</h4>
                                </div>
                                <div class="col-md-8">
                                    <div id="infoChart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="md-form form-lg admin-searchform">
                        <input type="text" id="admin-search" class="form-control form-control-lg mt-5">
                        <label for="admin-search">검색...</label>
                    </div>

                    <table class="table table-responsive-md btn-table">

                        <c:choose>
                            <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/member' ) }">
                            <thead class="admin-thead">
                            <tr class="text-center">
                                <th class="font-weight-bold">순번</th>
                                <th class="font-weight-bold">아이디</th>
                                <th class="font-weight-bold">닉네임</th>
                                <th class="font-weight-bold">포인트</th>
                                <th class="font-weight-bold">기능</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${member}" var="member">
                                <tr class="text-center">
                                    <th scope="row">${member.mbrNo}</th>
                                    <td>${member.mbrId}</td>
                                    <td>${member.mbrNick}</td>
                                    <td>${member.pntScore}</td>
                                    <td><button type="button" class="btn btn-warning admin-custombtn btn-sm m-0 admin-memberbtn" data-id="${member.mbrId}">수정</button></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            </c:when>

                            <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/board' ) }">
                                <thead class="admin-thead">
                                    <tr class="text-center">
                                        <th class="font-weight-bold">순번</th>
                                        <th class="font-weight-bold">닉네임</th>
                                        <th class="font-weight-bold">제목</th>
                                        <th class="font-weight-bold">일자</th>
                                        <th class="font-weight-bold">기능</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${board}" var="board">
                                    <tr class="text-center">
                                        <th scope="row">${board.brdNo}</th>
                                        <td>${board.mbrNick}</td>
                                        <td>${board.brdTitle}</td>
                                        <td><fmt:formatDate value="${board.brdDate}" pattern="YY-MM-dd HH:mm"/></td>
                                        <td>
                                            <button type="button" class="btn btn-warning admin-custombtn btn-sm m-0 admin-movebtn" data-no="${board.brdNo}">이동</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:when>

                            <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/study' ) }">
                                <thead class="admin-thead">
                                <tr class="text-center">
                                    <th class="font-weight-bold">순번</th>
                                    <th class="font-weight-bold">스터디이름</th>
                                    <th class="font-weight-bold">리더</th>
                                    <th class="font-weight-bold">제목</th>
                                    <th class="font-weight-bold">기능</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${study}" var="study">
                                    <tr class="text-center">
                                        <th scope="row">${study.stdNo}</th>
                                        <td>${study.stdGroup}</td>
                                        <td>${study.mbrNick}</td>
                                        <td>${study.stdTitle}</td>
                                        <td>
                                            <button type="button" class="btn btn-warning admin-custombtn btn-sm m-0 admin-movebtn" data-no="${study.stdNo}">이동</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:when>
                            <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/group' ) }">
                                <thead class="admin-thead">
                                <tr class="text-center">
                                    <th class="font-weight-bold">순번</th>
                                    <th class="font-weight-bold">스터디이름</th>
                                    <th class="font-weight-bold">리더</th>
                                    <th class="font-weight-bold">참여인원</th>
                                    <th class="font-weight-bold">기능</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${group}" var="group">
                                    <tr class="text-center">
                                        <th scope="row">${group.grmGno}</th>
                                        <td>${group.stdGroup}</td>
                                        <td>${group.mbrNick}</td>
                                        <td>${group.stdPersonCount}</td>
                                        <td>
                                            <button type="button" class="btn btn-warning admin-custombtn btn-sm m-0 admin-movebtn" data-no="${group.grmGno}">이동</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:when>
                            <c:when test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/message' ) }">
                                <thead class="admin-thead">
                                <tr class="text-center">
                                    <th class="font-weight-bold">순번</th>
                                    <th class="font-weight-bold">보낸회원</th>
                                    <th class="font-weight-bold">받는회원</th>
                                    <th class="font-weight-bold">내용</th>
                                    <th class="font-weight-bold">수신여부</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${message}" var="message">
                                    <tr class="text-center">
                                        <th scope="row">${message.msgNo}</th>
                                        <td>${message.msgFrom}</td>
                                        <td>${message.msgTo}</td>
                                        <td>${message.msgText}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${message.msgReceive == 1}">
                                                    N
                                                </c:when>
                                                <c:otherwise>
                                                    Y
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:when>
                        </c:choose>




                    </table>

                </c:otherwise>
            </c:choose>



        </div>

    </div>


</div>






<div class="modal fade basic-modal" id="memberModal" tabindex="-1" role="dialog" aria-labelledby="memberModalLabel" aria-hidden="true">
    <div class="modal-dialog basic-modal-dialog" role="document">
        <div class="modal-content basic-modal-content svg-modal-content">
            <div class="col-md-6 col-md-offset-2 m-auto">
                <div class="stm-title">
                    <img src="/static/image/common/girl.png">
                    <p>회원정보</p>
                </div>

                <div class="form-group stg-row admin-memrow">
                    <label class="col-sm-12 control-label">아이디</label>
                    <div class="col-sm-12 admin-memId">test2</div>
                </div>

                <div class="form-group stg-row admin-memrow">
                    <label class="col-sm-12 control-label">비밀번호</label>
                    <input type="text" class="admin-meminput" data-type="MBR_PASS">
                    <div class="col-sm-12 svg-title admin-memPass"></div>
                </div>

                <div class="form-group stg-row admin-memrow">
                    <label class="col-sm-12 control-label">닉네임</label>
                    <input type="text" class="admin-meminput" data-type="MBR_NICK">
                    <div class="col-sm-12 svg-title admin-memNick">haha</div>
                </div>

                <div class="form-group stg-row admin-memrow">
                    <label class="col-sm-12 control-label">포인트</label>
                    <input type="text" class="admin-meminput" data-type="PNT_SCORE">
                    <div class="col-sm-12 svg-title admin-memScore">10000</div>
                </div>

                <div class="form-group admin-memrow">
                    <div class="row">
                        <div class="col-6">
                            <label class="col-sm-12 control-label">권한</label>
                            <select class="custom-select custom-select-sm admin-memselect admin-memAuth" data-type="MBR_AUTH">
                                <option value="ADMIN" >관리자</option>
                                <option value="NORMAL" >일반회원</option>
                                <option value="NONE" >미승인</option>
                            </select>
                        </div>
                        <div class="col-6">
                            <label class="col-sm-12 control-label">로그인</label>
                            <select class="custom-select custom-select-sm admin-memselect admin-memType" data-type="MBR_TYPE">
                                <option value="GOOGLE" >구글</option>
                                <option value="NAVER" >네이버</option>
                                <option value="FACEBOOK" >페이스북</option>
                                <option value="KAKAO" >카카오</option>
                                <option value="NORMAL" >일반</option>
                                <option value="NONE" >비활성화</option>
                            </select>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="text-center mt-5">
                        <button type="button" class="btn btn-danger studysiba-button" data-dismiss="modal">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>