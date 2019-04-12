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

            <!-- -->
            <div class="study-item">
                <div class="content">
                    1
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-1.png">
                    </div>
                    <h3>쟁비서1<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    2
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-2.png">
                    </div>
                    <h3>쟁비서2<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    3
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-3.png">
                    </div>
                    <h3>쟁비서3<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    4
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-4.png">
                    </div>
                    <h3>쟁비서4<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    5
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-5.png">
                    </div>
                    <h3>쟁비서5<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-6.png">
                    </div>
                    <h3>쟁비서6<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    7
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-7.png">
                    </div>
                    <h3>쟁비서7<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    8
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-8.png">
                    </div>
                    <h3>쟁비서8<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    9
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-9.png">
                    </div>
                    <h3>쟁비서9<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    10
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-10.png">
                    </div>
                    <h3>쟁비서10<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    1
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-11.png">
                    </div>
                    <h3>쟁비서11<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    2
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-12.png">
                    </div>
                    <h3>쟁비서12<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    3
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-13.png">
                    </div>
                    <h3>쟁비서13<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    4
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-14.png">
                    </div>
                    <h3>쟁비서14<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    5
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-15.png">
                    </div>
                    <h3>쟁비서15<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-16.png">
                    </div>
                    <h3>쟁비서16<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-17.png">
                    </div>
                    <h3>쟁비서17<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-18.png">
                    </div>
                    <h3>쟁비서18<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-1.png">
                    </div>
                    <h3>쟁비서19<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/profile/profile-1.png">
                    </div>
                    <h3>쟁비서20<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


        </div>
    </div>
</div>







<%@ include file="../layout/footer.jsp" %>