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
                                <img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">캄캄네집안</span>
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
                                    10 / 5
                                </span>
                            </h5>
                        </div>
                    </a>
                </li>


                <li class="st-item">
                    <a class="st-link">
                        <div class="st-thumb">
                            <img src="/static/image/common/4.png">
                        </div>
                        <div class="st-body">
                            <h3 class="st-title">슺득이랑 공부할사람~</h3>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/startup.png"><span class="st-group">슺득이바보</span>
                            </h6>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/circular-clock.png"><span class="st-time">17.04.01 - 17.05.1</span>
                            </h6>
                            <h6 class="st-status">
                                <img class="st-icon" src="/static/image/study/global.png"><span class="st-locate">서울시 서울역 1번벤치</span>
                            </h6>
                            <h6 class="st-taglist">
                                <span class="st-tag">자바스크립트</span><span class="st-tag">ES6</span><span class="st-tag">리액트</span>
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
                                    10 / 10
                                </span>
                            </h5>
                        </div>
                    </a>
                </li>





            </div>


        </ul>

                <%--<input type="text" class="form-control" id="pac-input" name="address" placeholder="스터디 장소를 검색하세요.">--%>
                <%--<div id="map" style="width: 500px;height: 300px"></div>--%>












<span class="stm-divide">
    <span>프로그래밍</span>
    <input type="hidden" id="like1">
    <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
        <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
    </svg>
</span>

            <span class="stm-divide">
    <span>자격증</span>
    <input type="hidden" id="like1">
    <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
        <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
    </svg>
</span>

            <span class="stm-divide">
    <span>영어</span>
    <input type="hidden" id="like1">
    <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
        <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
    </svg>
</span>




        </div>


    </div>


</div>

