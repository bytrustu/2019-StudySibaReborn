<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>

<div class="container-fluid">

    <!-- 상단 헤드 그림 -->
    <div class="col-12 headline">
        <img src="/static/image/main/back.png">
    </div>

    <!-- 정보 뷰 -->
    <div class="row main-content mt-5">
        <div class="col-lg-4 info-box">
            <div class="info-leftbox">
                <div class="info-header">
                    <img class="animated rubberBand infinite slow" src="/static/image/main/like.png">
                    <p>시바랭킹</p>
                </div>
                <div class="info-body">

                    <c:forEach var="rank" items="${rank}" varStatus="status">
                        <div class="row mt-3 member-rank">
                            <img src="/static/image/main/rank${rank.pntRank}.png">
                            <img src="/static/image/profile/${rank.mbrProfile}">
                            <p>
                                <span>${rank.pntRank}위 !!! ${rank.mbrNick}</span><br/>
                                <span class="point" id="point${rank.pntRank}"
                                      data-accept="false">${rank.pntScore}</span><span>점</span>
                            </p>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>
        <div class="col-lg-4 info-box">
            <div class="info-centerbox">
                <div class="info-header">
                    <img class="animated rubberBand infinite slow" src="/static/image/main/like.png">
                    <p>접속중</p>
                </div>
                <div class="info-body login-box scrollbar scrollbar-warning force-overflow">
                    <div class="member-loginstate mt-3">
                        <c:if test="${fn:length(connect) == 0}">
                            <p class="text-center mt-5 member-nologined">(｡◕‿◕｡)</p>
                            <p class="text-center member-nologined">로그인 된 회원이 없습니다.</p>
                        </c:if>
                        <c:forEach items="${connect}" var="connect">
                            <div class="member-output">
                                <img src="/static/image/profile/${connect.mbrProfile}">
                                <div class="member-infobox">
                                    <div class="row">
                                        ${connect.mbrNick}
                                    </div>
                                    <div class="row">
                                        <i class="far fa-comment-dots messenger-connector" data-id="${connect.mbrId}" data-nick="${connect.mbrNick}"></i>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${sessionScope.id eq null}">
                <div class="col-lg-4 info-box">
                    <div class="info-rightbox">

                        <div class="login-header">
                            <div class="header-text">
                                <p>스터디에 참가하려면 ?</p>
                            </div>
                            <div class="header-button">
                                <button class="header-loginbutton modal-login modal-user" data-user="login">
                                    스터디시바 로그인
                                </button>
                            </div>
                        </div>
                        <div class="login-footer">
                            <div class="footer-text">
                                <p id="recovery-password">비밀번호 재설정</p>
                            </div>
                            <div class="footer-button">
                                <img src="/static/image/main/google.png"
                                     class="social-login-icon waves-effect social-google" data-name="social-google"
                                     data-url="${sessionScope.googleUrl}">
                                <img src="/static/image/main/naver.png"
                                     class="social-login-icon waves-effect social-naver" data-name="social-naver"
                                     data-url="${sessionScope.naverUrl}">
                                <img src="/static/image/main/facebook.png"
                                     class="social-login-icon waves-effect social-facebook" data-name="social-facebook">
                                <img src="/static/image/main/kakao.png"
                                     class="social-login-icon waves-effect social-kakao" data-name="social-kakao">
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-lg-4 info-box">
                    <div class="info-rightbox user-boxline">
                        <div class="user-header">
                            <img class="user-image" src="/static/image/profile/${sessionScope.profile}">
                            <p class="user-connect">접속시간 : 2018.04.29. 23:01</p>
                        </div>
                        <div class="user-body">
                            <div>
                                <p id="loggined-nick"><span class="user-auth">
                                    <c:choose>
                                        <c:when test="${sessionScope.auth eq 'ADMIN'}">
                                            관리자
                                        </c:when>
                                        <c:when test="${sessionScope.auth eq 'NORMAL'}">
                                            일반회원
                                        </c:when>
                                    </c:choose>
                                </span>${sessionScope.nick}</p>
                                <p><span>${sessionScope.rank}위</span><span class="point">${sessionScope.score}</span><span>점</span></p>
                                <div id="loggined-id">${sessionScope.id}</div>
                            </div>
                            <div>
                                <div class="row">
                                    <div class="col-4">
                                        방문수
                                    </div>
                                    <div class="col-4">
                                        게시글
                                    </div>
                                    <div class="col-4">
                                        댓글
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-4 point">
                                        <span class="point">1111</span>
                                    </div>
                                    <div class="col-4 point">
                                        <span class="point">2222</span>
                                    </div>
                                    <div class="col-4 point">
                                        <span class="point">3333</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="user-footer">
                            <a class="user-changeinfo modal-user" data-user="nick">닉네임</a>
                            <span>｜</span>
                            <a class="user-changeinfo modal-user" data-user="password">비밀번호</a>
                            <span>｜</span>
                            <a class="user-changeinfo modal-user" data-user="profile">프로필</a>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>


    <div class="middleline">
        <div class="row middle-box">
            <img src="/static/image/main/middletop-text.png" class="middleline-logo">
            <span>는 </span> <span class="middle-text">333</span> <span>명의 회원분들이</span>
        </div>
        <div class="row">
            <span>총</span> <span class="middle-text">2019</span> <span>회 방문하셨습니다. <i
                class="fas fa-grin-tears"></i></span>
        </div>
    </div>
</div>


<div class="slide-buttonbox">
    <div class="button-flag button-lt1" data-flag="lt">
        &lt;
    </div>

    <div class="button-slide button-stop">
        정지
    </div>


    <div class="button-flag button-gt1" data-flag="gt">
        &gt;
    </div>
</div>
<div class="slider-box">
    <div class="study-slider">
        <div class="study-box row">



            <c:forEach items="${study}" var="study">
                <div class="study-item" data-no="${study.stdNo}">
                    <div class="content">
                        <img class="content-image" src="/file/view/study/${study.uldFilename}">
                        <div class="content-body">
                            <c:choose>
                                <c:when test="${study.stdAvailable == 1}">
                                    <div class="st-now d-block mt-3">진행중</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="st-ended d-block mt-3">마감</div>
                                </c:otherwise>
                            </c:choose>
                            <div class="st-per mt-3 font-small">
                                <img class="st-icon" src="/static/image/study/networking.png">
                                <span class="slide-text">${study.stdPersonCount}명 / ${study.stdLimit}명</span>
                            </div>
                            <div>
                                <img class="st-icon" src="/static/image/study/circular-clock.png">
                                <span class="st-time font-small slide-text">
                                    ${study.stdStart} ~ ${study.stdEnd}
                                </span>
                            </div>
                            <div><img class="st-icon" src="/static/image/study/global.png"><span class="font-small slide-text">${study.stdAddress}</span></div>
                            <div class="st-taglist mt-3">
                                <c:set var="stdSuject" value="${fn:split(study.stdDivide, ',')}"/>
                                <c:forEach items="${stdSuject}" var="subject">
                                    <span class="st-tag">${subject}</span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="details">
                        <div class="image">
                            <img src="/static/image/profile/${study.mbrProfile}">
                        </div>
                        <h3>${study.stdGroup}<br><span>${study.stdTitle}</span></h3>
                    </div>
                </div>
            </c:forEach>



            <!-- -->
            <%--<div class="study-item">--%>
                <%--<div class="content">--%>
                    <%--<img class="content-image" src="/file/view/study/32b59469-c6ed-4220-b714-013603340a83_moon.png">--%>
                    <%--<div class="st-now d-block mt-3">진행중</div>--%>
                    <%--<div class="st-per mt-3 font-small">--%>
                        <%--<img class="st-icon" src="/static/image/study/networking.png">--%>
                        <%--<span class="slide-text">2 / 6</span>--%>
                    <%--</div>--%>
                    <%--<div><img class="st-icon" src="/static/image/study/circular-clock.png"><span class="st-time font-small slide-text">04-06 ~ 04-14</span></div>--%>
                    <%--<div><img class="st-icon" src="/static/image/study/global.png"><span class="font-small slide-text">서울특별시 맥도날드 명동점</span></div>--%>
                    <%--<div class="st-taglist mt-3">--%>
                        <%--<span class="st-tag">프로그래밍</span>--%>
                        <%--<span class="st-tag">외국어</span>--%>
                        <%--<span class="st-tag">취업</span>--%>
                        <%--<span class="st-tag">창업</span>--%>
                        <%--<span class="st-tag">대학</span>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="details">--%>
                    <%--<div class="image">--%>
                        <%--<img src="/static/image/profile/profile-2.png">--%>
                    <%--</div>--%>
                    <%--<h3>스프링마스터<br><span>스프링 함께하실분 모집합니다. 서울이예요ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ</span></h3>--%>
                <%--</div>--%>
            <%--</div>--%>
            <!-- -->



        </div>
    </div>
</div>







<%@ include file="../layout/footer.jsp" %>