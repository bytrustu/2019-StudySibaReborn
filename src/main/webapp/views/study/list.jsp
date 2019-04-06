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
            <div class="board-total">총 ${page.count} 스터디</div>
            <button class="btn btn-warning content-studybtn" data-reply="false"
                    data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">스터디등록
            </button>
        </div>

        <div class="board-table st-table">


            <ul class="st-ul">
                <div class="st-box">


                    <div class="col-12 mt-2 mb-4 st-subject">
                        <span class="stm-divide">
                            전체
                        </span>
                        <span class="stm-divide">
                            프로그래밍
                        </span>
                        <span class="stm-divide">
                            외국어
                        </span>
                        <span class="stm-divide">
                            취업
                        </span>
                        <span class="stm-divide">
                            자격증
                        </span>
                        <span class="stm-divide">
                            동기부여
                        </span>
                        <span class="stm-divide">
                            면접
                        </span>
                        <span class="stm-divide">
                            IT
                        </span>
                        <span class="stm-divide">
                            수능
                        </span>
                        <span class="stm-divide">
                            여행
                        </span>
                        <span class="stm-divide">
                            공부진도체크
                        </span>
                        <span class="stm-divide">
                            대학
                        </span>
                        <span class="stm-divide">
                            취미
                        </span>
                        <span class="stm-divide">
                            운동
                        </span>
                        <span class="stm-divide">
                            그외
                        </span>
                    </div>

                    <c:forEach items="${study}" var="study">
                        <li class="st-item st-move" data-no="${study.stdNo}">
                            <a class="st-link">
                                <div class="st-thumb">
                                    <img src="/file/view/study/${study.uldFilename}">
                                </div>
                                <div class="st-body">
                                    <h3 class="st-title">${study.stdTitle}</h3>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/startup.png"><span class="st-group">${study.stdGroup}</span>
                                    </h6>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/circular-clock.png"><span
                                            class="st-time">${study.stdStart} - ${study.stdEnd}</span>

                                    </h6>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">${study.stdAddress}</span>
                                    </h6>
                                    <h6 class="st-taglist">
                                        <c:set var="stdSuject" value="${fn:split(study.stdDivide, ',')}"/>
                                        <c:forEach items="${stdSuject}" var="subject">
                                            <span class="st-tag">${subject}</span>
                                        </c:forEach>
                                    </h6>
                                </div>
                                <div class="st-person">
                                    <c:choose>
                                        <c:when test="${now >= study.stdEnd || study.stdAvailable == '0'}">
                                            <div class="st-ended">마감</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="st-now">진행중</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <h5 class="st-per">
                                    <span class="st-pericon">
                                        <img src="/static/image/study/networking.png">
                                    </span>
                                        <span class="st-percount">
                                        ${study.stdPersonCount} / ${study.stdLimit}
                                    </span>
                                    </h5>
                                </div>
                            </a>
                        </li>
                    </c:forEach>


                    <%--<li class="st-item">--%>
                        <%--<a class="st-link">--%>
                            <%--<div class="st-thumb">--%>
                                <%--<img src="/static/image/common/4.png">--%>
                            <%--</div>--%>
                            <%--<div class="st-body">--%>
                                <%--<h3 class="st-title">슺득이랑 공부할사람~</h3>--%>
                                <%--<h6 class="st-status">--%>
                                    <%--<img class="st-icon" src="/static/image/study/startup.png"><span class="st-group">슺득이바보</span>--%>
                                <%--</h6>--%>
                                <%--<h6 class="st-status">--%>
                                    <%--<img class="st-icon" src="/static/image/study/circular-clock.png"><span--%>
                                        <%--class="st-time">17.04.01 - 17.05.1</span>--%>
                                <%--</h6>--%>
                                <%--<h6 class="st-status">--%>
                                    <%--<img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">서울시 서울역 1번벤치</span>--%>
                                <%--</h6>--%>
                                <%--<h6 class="st-taglist">--%>
                                    <%--<span class="st-tag">자바스크립트</span><span class="st-tag">ES6</span><span--%>
                                        <%--class="st-tag">리액트</span>--%>
                                <%--</h6>--%>
                            <%--</div>--%>
                            <%--<div class="st-person">--%>
                                <%--<div class="st-now">--%>
                                    <%--진행중--%>
                                <%--</div>--%>
                                <%--<h5 class="st-per">--%>
                                <%--<span class="st-pericon">--%>
                                    <%--<img src="/static/image/study/networking.png">--%>
                                <%--</span>--%>
                                    <%--<span class="st-percount">--%>
                                    <%--10 / 10--%>
                                <%--</span>--%>
                                <%--</h5>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                    <%--</li>--%>


                </div>


            </ul>

            <%--<input type="text" class="form-control" id="pac-input" name="address" placeholder="스터디 장소를 검색하세요.">--%>
            <%--<div id="map" style="width: 500px;height: 300px"></div>--%>


        </div>




    <div class="mt-4">
        <input type="text" class="form-control st-searchinput" placeholder="검색" value="${cri.keyword}">
    </div>



    <nav>
        <ul class="pagination pg-amber board-pagination">
            <li class="page-item">
                <c:if test="${page.startPage ne 1 }">
                    <a class="page-link"
                       href='/study/list?pageNum=${page.startPage-1}<c:if test="${cri.type ne null}">&type=${cri.type}</c:if><c:if test="${cri.keyword ne null}">&keyword=${cri.keyword}</c:if>'  aria-label="Previous">
                        <span aria-hidden="true"><i class="fas fa-angle-double-left"></i></span>
                        <span class="sr-only">Previous</span>
                    </a>
                </c:if>
            </li>
            <c:forEach begin="${page.startPage }" end="${page.endPage }" step="1" var="i">
                <li class="page-item board-pageactive <c:if test="${page.criteria.pageNum eq i}">active</c:if>">
                    <a class="page-link"
                       href="/study/list?pageNum=${i}<c:if test='${cri.type ne null}'>&type=${cri.type}</c:if><c:if test='${cri.keyword ne null}'>&keyword=${cri.keyword}</c:if>">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item">
                <c:if test="${page.endPage lt page.pageCount}">
                    <a class="page-link"
                       href='/study/list?pageNum=${page.endPage+1}<c:if test="${cri.type ne null}">&type=${cri.type}</c:if><c:if test="${cri.keyword ne null}">&keyword=${cri.keyword}</c:if>' aria-label="Next">
                        <span aria-hidden="true"><i class="fas fa-angle-double-right"></i></span>
                        <span class="sr-only">Next</span>
                    </a>
                </c:if>
            </li>
        </ul>
    </nav>










    </div>


</div>

