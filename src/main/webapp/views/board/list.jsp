<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%--<%--%>
    <%--session.setAttribute("id","test1");--%>
<%--%>--%>


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
            <div class="board-total">총 ${page.count} 게시글</div>

            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/notice/list' }">
                    <button class="btn btn-warning content-writebtn" data-reply="false" data-write="<c:if test="${sessionScope.auth eq 'ADMIN'}">true</c:if>">글쓰기</button>
                </c:when>

                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/community/list' }">
                    <button class="btn btn-warning content-writebtn" data-reply="false" data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">글쓰기</button>
                </c:when>
            </c:choose>

        </div>

        <table class="board-table">
            <thead class="board-thead"></thead>
            <tbody class="board-tbody">

            <jsp:useBean id="now" class="java.util.Date"/>
            <fmt:formatDate value="${now}" var="now" pattern="yyyyMMdd" />

            <c:forEach items="${boardList}" var="boardList">

                <c:choose>
                    <c:when test="${boardList.brdAvailable ne 0}">
                        <tr>
                            <td class="board-divide">
                        <span>
                            <c:choose>
                                <c:when test="${boardList.brdDivide eq 1}">공지</c:when>
                                <c:when test="${boardList.brdDivide eq 2}">이벤</c:when>
                                <c:when test="${boardList.brdDivide eq 3}">잡담</c:when>
                                <c:when test="${boardList.brdDivide eq 4}">정보</c:when>
                                <c:when test="${boardList.brdDivide eq 5}">요청</c:when>
                            </c:choose>
                        </span>
                            </td>
                            <td class="board-subject">
                                <div class="board-thumb">
                                    <a class="board-imgbox">
                                        <img src="/static/image/profile/${boardList.mbrProfile}">
                                    </a>

                                    <div class="board-content">
                                        <div class="board-title">
                                            <p class="board-titletext">
                                                <a class="title-text board-postlink" href="${boardList.brdNo}">
                                                    <c:if test="${boardList.brdStep > 0}"><img class="board-replyicon" src="/static/image/common/re.png"></c:if>
                                                    ${boardList.brdTitle}</a>
                                                <c:if test="${boardList.brdCommentCount gt 0}">
                                                    <span class="board-commentcount">[${boardList.brdCommentCount}]</span>
                                                </c:if>


                                                <fmt:formatDate value="${boardList.brdDate}" var="brdDate" pattern="yyyyMMdd"/>
                                                <c:if test="${brdDate eq now}"><img class="board-containimg" src="/static/image/common/new.png"></c:if>
                                                <c:choose>
                                                    <c:when test="${fn:containsIgnoreCase(boardList.brdContent,'image' ) && fn:containsIgnoreCase(boardList.brdContent,'media'  )}">
                                                        <img class="board-containimg" src="/static/image/common/picture.png">
                                                    </c:when>
                                                    <c:when test="${fn:containsIgnoreCase(boardList.brdContent,'image' )}">
                                                        <img class="board-containimg" src="/static/image/common/landscape.png">
                                                    </c:when>
                                                    <c:when test="${fn:containsIgnoreCase(boardList.brdContent,'media' )}">
                                                        <img class="board-containimg" src="/static/image/common/youtube.png">
                                                    </c:when>
                                                </c:choose>
                                            </p>
                                            <img src="" class="icon-new">
                                        </div>
                                        <span class="board-writer"><a>${boardList.mbrNick}</a></span>
                                        <span class="board-bar">｜</span>
                                        <span class="board-time">${boardList.lastTime}</span>
                                    </div>

                                </div>
                            </td>
                            <td class="board-hit">
                                <img src="/static/image/common/board_search.png">
                                <span>${boardList.brdCount}</span>
                            </td>
                            <td class="board-like">
                                <img src="/static/image/common/board_like.png">
                                <span>${boardList.brdLikeCount}</span>
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td class="board-divide">
                        <span>
                            삭제
                        </span>
                            </td>
                            <td class="board-subject">
                                <div class="board-thumb">
                                    <a class="board-imgbox">
                                        <img src="/static/image/profile/lock2.png">
                                    </a>
                                    <div class="board-content">
                                        <div class="board-title">
                                            <p class="board-titletext">
                                                <a class="title-text board-postlink" href="${boardList.brdNo}">삭제 된 게시글 입니다.</a>
                                            </p>
                                            <img src="" class="icon-new">
                                        </div>
                                        <span class="board-writer board-delwriter"><a>알수없음</a></span>
                                        <span class="board-bar">｜</span>
                                        <span class="board-time">-</span>
                                    </div>

                                </div>
                            </td>
                            <td class="board-hit">
                                <img src="/static/image/common/board_search.png">
                                <span>-</span>
                            </td>
                            <td class="board-like">
                                <img src="/static/image/common/board_like.png">
                                <span>-</span>
                            </td>
                        </tr>

                    </c:otherwise>
                </c:choose>


            </c:forEach>

            </tbody>
        </table>


        <div class="board-searchbox">


            <select class="custom-select custom-select-sm board-select">

                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/notice/list' }">
                        <option value="-1" <c:if test="${cri.type eq null}">selected</c:if>>전체</option>
                        <option value="1" <c:if test="${cri.type eq '1'}">selected</c:if>>공지</option>
                        <option value="2" <c:if test="${cri.type eq '2'}">selected</c:if>>이벤</option>
                    </c:when>

                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/community/list' }">
                        <option value="-2" <c:if test="${cri.type eq null}">selected</c:if>>전체</option>
                        <option value="3" <c:if test="${cri.type eq '3'}">selected</c:if>>잡담</option>
                        <option value="4" <c:if test="${cri.type eq '4'}">selected</c:if>>정보</option>
                        <option value="5" <c:if test="${cri.type eq '5'}">selected</c:if>>요청</option>
                    </c:when>
                </c:choose>


            </select>



            <div class="board-search">
                <div class="md-form mt-0">
                    <input type="text" class="form-control board-searchinput" placeholder="검색" value="${cri.keyword}">
                </div>
            </div>

        </div>


        <nav>
            <ul class="pagination pg-amber board-pagination">
                <li class="page-item">
                    <c:if test="${page.startPage ne 1 }">
                        <a class="page-link"
                           href='/board/${boardList[0].brdType}/list?pageNum=${page.startPage-1}<c:if test="${cri.type ne null}">&type=${cri.type}</c:if><c:if test="${cri.keyword ne null}">&keyword=${cri.keyword}</c:if>'  aria-label="Previous">
                            <span aria-hidden="true"><i class="fas fa-angle-double-left"></i></span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </c:if>
                </li>
                    <c:forEach begin="${page.startPage }" end="${page.endPage }" step="1" var="i">
                        <li class="page-item board-pageactive <c:if test="${page.criteria.pageNum eq i}">active</c:if>">
                            <a class="page-link"
                               href="/board/${boardList[0].brdType}/list?pageNum=${i}<c:if test='${cri.type ne null}'>&type=${cri.type}</c:if><c:if test='${cri.keyword ne null}'>&keyword=${cri.keyword}</c:if>">${i}</a>
                        </li>
                    </c:forEach>
                <li class="page-item">
                    <c:if test="${page.endPage lt page.pageCount}">
                        <a class="page-link"
                           href='/board/${boardList[0].brdType}/list?pageNum=${page.endPage+1}<c:if test="${cri.type ne null}">&type=${cri.type}</c:if><c:if test="${cri.keyword ne null}">&keyword=${cri.keyword}</c:if>' aria-label="Next">
                            <span aria-hidden="true"><i class="fas fa-angle-double-right"></i></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </c:if>
                </li>
            </ul>
        </nav>


    </div>


</div>




