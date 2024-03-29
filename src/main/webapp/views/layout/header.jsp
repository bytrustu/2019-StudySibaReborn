        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <!DOCTYPE html>
        <html lang="ko">

        <head>
                <meta charset="utf-8">
                <title>스터디시바</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <meta property="og:url" content="http://studysiba.com">
                <meta property="og:type" content="website">
                <meta property="og:title" content="스터디시바">
                <meta property="og:description" content="온라인 스터디 커뮤니티">
                <meta property="og:image" content="https://i.imgur.com/G614toq.png">
                <link rel="icon" type="image/x-icon" href="/static/image/main/sibacon.ico">

                <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
                <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                <link href="/static/css/bootstrap.min.css" rel="stylesheet">
                <link href="/static/css/mdb.min.css" rel="stylesheet">
                <link href="/static/css/sweetalert2.min.css" rel="stylesheet">
                <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
                <link type="text/css" rel="stylesheet" href="/static/css/animate.css">
                <link type="text/css" rel="stylesheet" href="/static/css/main.css">
                <link type="text/css" rel="stylesheet" href="/static/css/sub.css">

                <link type="text/css" rel="stylesheet" href="/static/css/messenger.css">
                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/study' ) || fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/group' )
                                   || fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin' )}">
                        <link type="text/css" rel="stylesheet" href="/static/css/study.css">
                </c:if>
                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin' )}">
                        <link type="text/css" rel="stylesheet" href="/static/css/admin.css">
                </c:if>
                <script type="text/javascript" src="/static/js/lib/jquery-3.3.1.min.js"></script>
                <script type="text/javascript" src="/static/js/lib/jquery-ui.min.js"></script>
        </head>


        <body class="sub-page">

                <!-- 메신저 버튼 -->
                <div class="messenger-common messenger-btn">
                        <i class="fas fa-envelope msg-openicon animated slow infinite"></i>
                </div>

                <!-- 관리자 버튼 -->
                <c:if test="${sessionScope.auth eq 'ADMIN'}">
                        <div class="admin-btn">
                                <i class="fas fa-unlock-alt"></i>
                        </div>
                </c:if>

                <!-- 알림창 -->
                <div class="messenger-alert">
                </div>

                <!-- 상단 메뉴바 -->
                <nav class="navbar navbar-expand-sm sticky-top shadow-sm p-2">
                        <a class="navbar-brand pl-3" href="#"><img src="/static/image/main/studysiba-logo.png"></a>
                        <a class="navbar-toggle" id="toggleBtn"><i class="fa fa-bars"></i></a>
                        <ul class="navbar-nav position-absolute">
                                <li class="nav-item text-right">
                                        <a class="nav-link" href="/">홈</a>
                                </li>
                                <li class="nav-item text-right">
                                        <a class="nav-link" href="/board/notice/list">공지사항</a>
                                </li>
                                <li class="nav-item">
                                        <a class="nav-link" href="/board/community/list">커뮤니티</a>
                                </li>
                                <li class="nav-item text-right">
                                        <a class="nav-link" href="/study/list">스터디참여</a>
                                </li>
                                <li class="nav-item text-right">
                                        <a class="nav-link" href="/group/list">스터디그룹</a>
                                </li>
                                <li class="login-button nav-item text-right">
                                        <div class="login-eff"></div>
                                        <c:choose>
                                                <c:when test="${sessionScope.id eq null}">
                                                        <c:choose>
                                                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/' }">
                                                                        <a href="#" class="modal-login modal-user" data-user="login">로그인/가입</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                        <a href="/?requireLogin=true" class="modal-login modal-user">로그인/가입</a>
                                                                </c:otherwise>
                                                        </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                        <a href="#" class="modal-login modal-user" data-user="logout" data-id="${sessionScope.id}">로그아웃</a>
                                                </c:otherwise>
                                        </c:choose>
                                </li>
                        </ul>
                </nav>
                <div class="mobile-menu" id="mobile">
                        <div class="mobile-item"><a class="mobile-link" id="mobileHome" href="/">홈</a></div>
                        <div class="mobile-item"><a class="mobile-link" id="mobileNotice" href="/board/notice/list">공지사항</a></div>
                        <div class="mobile-item"><a class="mobile-link" id="mobileCommunity" href="/board/community/list">커뮤니티</a></div>
                        <div class="mobile-item"><a class="mobile-link" id="mobileStudy" href="/study/list">스터디참여</a></div>
                        <div class="mobile-item"><a class="mobile-link" id="mobileGroup" href="/group/list">스터디그룹</a></div>
                        <c:if test="${sessionScope.id != null}">
                                <div class="mobile-item">
                                        <a class="mobile-link" id="mobileLogout"
                                        href="/member/logout/${sessionScope.id}?currentUrl=${requestScope['javax.servlet.forward.servlet_path']}">로그아웃</a>
                                </div>
                        </c:if>
                </div>