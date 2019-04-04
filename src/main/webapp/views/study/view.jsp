<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                <c:if test="${sessionScope.id eq board.brdId || sessionScope.auth eq 'ADMIN'}">
                    <img src="/static/image/common/edit.png" class="board-edit"><img src="/static/image/common/delete.png" class="board-delete">
                </c:if>
            </div>
        </div>

        <div class="board-body">

        </div>

    </div>


</div>