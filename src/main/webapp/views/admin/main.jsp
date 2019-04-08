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
        <div class="board-top">

            <div class="col-12 mt-2 mb-4 text-center">
                <span class="admin-divide">
                    전체
                </span>
                <span class="admin-divide">
                    회원관리
                </span>
                <span class="admin-divide">
                    게시판관리
                </span>
                <span class="admin-divide">
                    스터디관리
                </span>
                <span class="admin-divide">
                    그룹관리
                </span>
                <span class="admin-divide">
                    메세지관리
                </span>
            </div>


        </div>

        <div class="board-table st-table admin-table">

            <div id="dataChart"></div>



        </div>

    </div>


</div>

