<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="toDay" class="java.util.Date" />
<fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd" var="now"/>

<div class="sub-page">

    <div class="sub-subject">
        <span class="sub-topcomment">${intro.top}</span>
        <span class="sub-bottomcomment">
            ${intro.bottomFirst}<br/>
            ${intro.bottomSecond}
        </span>
    </div>




    <div class="board-box">
        <div class="board-top">
            <div class="board-total">
                <c:if test="${sessionScope.id eq studyView.stdId || sessionScope.auth eq 'ADMIN'}">
                    <img src="/static/image/common/edit.png" class="study-edit"><img src="/static/image/common/rollback.png" class="study-latest"><img src="/static/image/common/delete.png" class="study-delete">
                </c:if>
            </div>

            <c:choose>
                <c:when test="${fn:contains(groupMember,sessionScope.id )}">
                    <button class="btn btn-danger study-joinbtn" data-join="already">참여중</button>
                </c:when>
                <c:when test="${fn:length(groupMember) >= studyView.stdLimit || studyView.stdAvailable == 0 || now >= studyView.stdEnd}">
                    <button class="btn btn-danger study-joinbtn" data-join="unable">참여불가</button>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-warning study-joinbtn" data-join="join">참여하기</button>
                </c:otherwise>
            </c:choose>

            <%--<button class="btn btn-danger study-outbtn" data-out="member">탈퇴</button>--%>
        </div>

        <div class="board-body stv-body">


            <div class="row stv-row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Introduce</h3>
                            <h4 class="stv-subtitle">스터디 소개</h4>
                        </div>
                        <div class="col-md-8">


                            <c:choose>
                                <c:when test="${now >= studyView.stdEnd || studyView.stdAvailable == '0'}">
                                    <div class="st-ended">마감</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="st-now">진행중</div>
                                </c:otherwise>
                            </c:choose>
                            <span class="stv-group">${studyView.stdGroup}</span>

                            <img src="/file/view/study/${studyView.uldFilename}" class="stv-logo">

                        </div>
                    </div>
                </div>
            </div>

            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Subject</h3>
                            <h4 class="stv-subtitle">스터디 주제</h4>
                        </div>
                        <div class="col-md-8">

                            <h6 class="st-taglist stv-taglist">
                                <span class="st-tag stv-tag">수능</span>
                                <span class="st-tag stv-tag">여행</span>
                                <span class="st-tag stv-tag">공부진도체크</span>
                                <span class="st-tag stv-tag">출석체크</span>
                                <span class="st-tag stv-tag">창업</span>
                            </h6>

                        </div>
                    </div>
                </div>
            </div>


            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Details</h3>
                            <h4 class="stv-subtitle">스터디 상세내용</h4>
                        </div>
                        <div class="col-md-8">
                            <h3 class="stv-studytitle">${studyView.stdTitle}</h3>
                            <div class="stv-studycontent">
                                ${studyView.stdContent}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Schedule</h3>
                            <h4 class="stv-subtitle">스터디 일정</h4>
                        </div>
                        <div class="col-md-8">
                            <div class="stv-period">
                                <img src="/static/image/study/round-table.png" class="stv-icon">
                                <h4>${studyView.stdStart}</h4><span>부터</span> <h4>${studyView.stdEnd}</h4><span>까지 예정</span>
                            </div>
                            <div id="viewMap"></div>
                            <div class="stv-address">
                                <img src="/static/image/study/placeholder.png" class="stv-icon">
                                <span class="stv-locate">${studyView.stdAddress} ${studyView.stdPlace}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Leader</h3>
                            <h4 class="stv-subtitle">스터디를 이끌 리더</h4>
                        </div>
                        <div class="col-md-8">

                            <c:set var="imgStep" value='${fn:substring(studyView.mbrProfile, fn:indexOf(studyView.mbrProfile,"-")+1, fn:indexOf(studyView.mbrProfile,".png"))}'/>

                            <div class="card stv-user">
                                <div class="card-top stv-usertop
                                <c:choose>
                                    <c:when test="${imgStep == 1}">user-bgone</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">user-bgtwo</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">user-bgthree</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">user-bgfour</c:when>
                                </c:choose>
                                ">
                                    <img src="/static/image/profile/${studyView.mbrProfile}">
                                </div>
                                <div class="triangle
                                <c:choose>
                                    <c:when test="${imgStep == 1}">triangle-one</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">triangle-two</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">triangle-three</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">triangle-four</c:when>
                                </c:choose>
                                ">
                                    <div class="stv-msgbtn">
                                        <img class="stv-msgimg" src="/static/image/main/like.png">
                                    </div>
                                </div>
                                <div class="card-bottom text-center stv-userbottom">
                                    용준
                                </div>
                            </div>


                        </div>
                    </div>
                </div>
            </div>

            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Member</h3>
                            <h4 class="stv-subtitle">스터디를 함께할 멤버</h4>
                        </div>
                        <div class="col-md-8">


                            <c:forEach items="${groupMember}" var="group" begin="1">

                                <c:set var="imgStep" value='${fn:substring(group.mbrProfile, fn:indexOf(group.mbrProfile,"-")+1, fn:indexOf(group.mbrProfile,".png"))}'/>

                                <div class="card stv-user">
                                    <div class="card-top stv-usertop
                                <c:choose>
                                    <c:when test="${imgStep == 1}">user-bgone</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">user-bgtwo</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">user-bgthree</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">user-bgfour</c:when>
                                </c:choose>
                                ">
                                        <img src="/static/image/profile/${group.mbrProfile}">
                                    </div>
                                    <div class="triangle
                                <c:choose>
                                    <c:when test="${imgStep == 1}">triangle-one</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">triangle-two</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">triangle-three</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">triangle-four</c:when>
                                </c:choose>
                                ">
                                        <div class="stv-msgbtn">
                                            <img class="stv-msgimg" src="/static/image/main/like.png">
                                        </div>
                                    </div>
                                    <div class="card-bottom text-center stv-userbottom">
                                        ${group.mbrNick}
                                    </div>
                                </div>



                            </c:forEach>


                        </div>
                    </div>
                </div>
            </div>


        </div>

    </div>


</div>