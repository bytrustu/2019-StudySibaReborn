<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <div class="board-total">총 521 게시글</div>

            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/notice/view' }">

                </c:when>

                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/community/view' }">
                    <button class="btn btn-warning content-writebtn" data-reply="true" data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">답글쓰기</button>
                </c:when>
            </c:choose>

        </div>

        <div class="board-body">

            <div class="post-head">
                <div class="post-divide">
                    <span>
                        <c:choose>
                            <c:when test="${board.brdDivide eq 1}">공지</c:when>
                            <c:when test="${board.brdDivide eq 2}">이벤</c:when>
                            <c:when test="${board.brdDivide eq 3}">잡담</c:when>
                            <c:when test="${board.brdDivide eq 4}">정보</c:when>
                            <c:when test="${board.brdDivide eq 5}">요청</c:when>
                        </c:choose>
                    </span>
                </div>
                <div class="post-date"><span><fmt:formatDate value="${board.brdDate}" pattern="yy-MM-dd"/></span></div>
                <div class="post-info"><span>댓글수1</span></div>
                <div class="post-info"><span>추천수0</span></div>
                <div class="post-info"><span>조회수${board.brdCount}</span></div>
                <input type="hidden" class="post-no" value="${board.brdNo}">
            </div>

            <div class="post-top">
                <div class="post-title">${board.brdTitle}</div>
                <div class="post-nick">${board.mbrNick}</div>
            </div>
            <div class="post-body">
                ${board.brdContent}
            </div>
            <div class="post-member">
                <div class="member-left">
                    <img src="/static/image/profile/profile-1.png">
                    <span>침착해내자신</span>
                </div>
                <div class="member-right">
                    <div><img src="/static/image/common/friendship.png"><span>글보기</span></div>
                    <div><img src="/static/image/common/mail.png"><span>메세지</span></div>
                </div>
            </div>

            <div class="post-bottom">
                <button class="btn btn-primary">목록</button>
                <button class="btn btn-danger">추천</button>
            </div>
            <div class="comment-top">
                <div class="comment-subject">
                    <span>3</span><span>개의 댓글이 있습니다.</span>
                </div>
            </div>
            <div class="comment-bottom">
                <input type="text" class="comment-input">
                <button class="btn btn-warning comment-btn">작성</button>
            </div>


            <div class="comment-list">
                <div class="comment-content">
                    <img src="/static/image/profile/profile-1.png">
                    <div class="comment-info">
                        <p>[ 침착해내자신 ]</p>
                        <p>2019.03.03 20:47</p>
                    </div>
                </div>
                <p class="comment-text">안녕하세요 ㅎㅎㅎㅎㅎ</p>
            </div>

            <div class="comment-list">
                <div class="comment-content">
                    <img src="/static/image/profile/profile-1.png">
                    <div class="comment-info">
                        <p>[ 침착해내자신 ]</p>
                        <p>2019.03.03 20:47</p>
                    </div>
                </div>
                <p class="comment-text">안녕하세요 ㅎㅎㅎㅎㅎ</p>
            </div>

            <div class="comment-list">
                <div class="comment-content">
                    <img src="/static/image/profile/profile-1.png">
                    <div class="comment-info">
                        <p>[ 침착해내자신 ]</p>
                        <p>2019.03.03 20:47</p>
                    </div>
                </div>
                <p class="comment-text">안녕하세요 ㅎㅎㅎㅎㅎ</p>
            </div>



            <div class="comment-list">
                <div class="comment-content">
                    <img src="/static/image/profile/profile-1.png">
                    <div class="comment-info">
                        <p>[ 침착해내자신 ]</p>
                        <p>2019.03.03 20:47</p>
                    </div>
                </div>
                <p class="comment-text">안녕하세요 ㅎㅎㅎㅎㅎ</p>
            </div>


        </div>




    </div>


</div>