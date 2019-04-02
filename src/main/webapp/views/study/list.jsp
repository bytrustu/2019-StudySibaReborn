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
            <button class="btn btn-warning content-writebtn" data-reply="false"
                    data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">글쓰기
            </button>
        </div>

        <div class="board-table st-table">


        <ul class="st-ul">
            <div class="st-box">




                <li class="st-item">
                    <a class="st-link">
                        <div class="st-thumb">
                            <img src="/static/image/common/1.png">
                        </div>
                        <div class="st-body">
                            <h3 class="st-title">스프링 프로젝트 스터디원 구합니다.</h3>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/startup.png"><span class="st-group">캄캄컴퍼니</span>
                            </h6>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/circular-clock.png"><span class="st-time">17.04.01 - 17.04.31</span>
                            </h6>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">경남 창원시 마산회원구 구암1동 ㅎㅎㅎㅎ</span>
                            </h6>
                            <h6 class="st-taglist">
                                <span class="st-tag">자바</span><span class="st-tag">스프링</span><span class="st-tag">알고리즘</span>
                            </h6>
                        </div>
                        <div class="st-person">
                            <div class="st-now">
                                진행중
                            </div>
                            <h5 class="st-per">
                                <span class="st-pericon">
                                    <img src="/static/image/study/networking.png">
                                </span>
                                <span class="st-percount">
                                    30 / 30
                                </span>
                            </h5>
                        </div>
                    </a>
                </li>





            </div>


        </ul>










        </div>


    </div>


</div>
