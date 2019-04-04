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
                <meta property="og:url" content="http://www.studysiba.com">
                <meta property="og:type" content="website">
                <meta property="og:title" content="스터디시바">
                <meta property="og:description" content="온라인 스터디 커뮤니티">
                <meta property="og:image" content="https://i.imgur.com/ltNAoPV.jpg">
                <link rel="icon" type="image/x-icon" href="/static/image/main/sibacon.ico">

                <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                <link href="/static/css/bootstrap.min.css" rel="stylesheet">
                <link href="/static/css/mdb.min.css" rel="stylesheet">
                <link href="/static/css/sweetalert2.min.css" rel="stylesheet">
                <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
                <link type="text/css" rel="stylesheet" href="/static/css/main.css">
                <link type="text/css" rel="stylesheet" href="/static/css/sub.css">
                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/study' ) || fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/group' ) }">
                        <link type="text/css" rel="stylesheet" href="/static/css/study.css">
                </c:if>
                <script type="text/javascript" src="/static/js/lib/jquery-3.3.1.min.js"></script>
                <script type="text/javascript" src="/static/js/lib/jquery-ui.min.js"></script>
        </head>


        <body class="sub-page">

                <!-- 하단 메세지 버튼 -->
                <div class="messenger-btn">
                <i class="fas fa-envelope"></i>
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