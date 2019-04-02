<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
            <div class="board-total">총 ${page.count} 스터디</div>

            <button class="btn btn-warning content-writebtn" data-reply="false"
                    data-write="<c:if test="${sessionScope.auth eq 'ADMIN'}">true</c:if>">글쓰기
            </button>

            <button class="btn btn-warning content-writebtn" data-reply="false"
                    data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">글쓰기
            </button>

        </div>

        <div class="board-table">




        </div>


    </div>


</div>
