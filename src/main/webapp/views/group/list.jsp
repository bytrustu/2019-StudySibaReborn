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


    <div class="board-box group-box">
        <div class="board-top">
            <div class="board-total">총 ${fn:length(group)} 스터디</div>
        </div>

        <div class="board-table st-table">

            <ul class="st-ul">
                <div class="st-box">

                    <c:if test="${fn:length(group) == 0}">
                        <div class="group-notfound">
                            <p>(⁎˃ᆺ˂)</p>
                            <p>참여중인 스터디가 없습니다!</p>
                        </div>
                    </c:if>

                    <c:forEach items="${group}" var="group">
                        <li class="st-item st-move" data-no="${group.grmGno}">
                            <a class="st-link">
                                <div class="st-thumb">
                                    <img src="/file/view/study/${group.uldFilename}">
                                </div>
                                <div class="st-body">
                                    <h3 class="st-title">${group.stdTitle}</h3>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/startup.png"><span class="st-group">${group.stdGroup}</span>
                                    </h6>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/circular-clock.png"><span
                                            class="st-time">${group.stdStart} - ${group.stdEnd}</span>

                                    </h6>
                                    <h6 class="st-status">
                                        <img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">${group.stdAddress}</span>
                                    </h6>
                                    <h6 class="st-taglist">
                                        <c:set var="stdSuject" value="${fn:split(group.stdDivide, ',')}"/>
                                        <c:forEach items="${stdSuject}" var="subject">
                                            <span class="st-tag">${subject}</span>
                                        </c:forEach>
                                    </h6>
                                </div>
                                <div class="st-person">
                                    <c:choose>
                                        <c:when test="${now >= group.stdEnd || group.stdAvailable == '0'}">
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
                                        ${group.stdPersonCount} / ${group.stdLimit}
                                    </span>
                                    </h5>
                                </div>
                            </a>
                        </li>
                    </c:forEach>



                </div>


            </ul>



        </div>







    </div>


</div>

