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


        <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin/main' ) }">
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
        </c:if>


            <div class="md-form form-lg admin-searchform">
                <input type="text" id="admin-search" class="form-control form-control-lg">
                <label for="admin-search">검색...</label>
            </div>

            <table class="table table-striped table-responsive-md btn-table">

                <thead>
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
                        <td><button type="button" class="btn btn-primary btn-sm m-0" data-id="${member.mbrId}">수정</button></td>
                    </tr>
                </c:forEach>

                </tbody>

            </table>



























        </div>

    </div>


</div>

