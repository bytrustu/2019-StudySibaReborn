<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <div class="board-total">총 521 게시글</div>

            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/notice/list' }">
                    <button class="btn btn-warning content-writebtn" data-reply="false" data-write="<c:if test="${sessionScope.auth eq 'ADMIN'}">true</c:if>">글쓰기</button>
                </c:when>

                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/community/list' }">
                    <button class="btn btn-warning content-writebtn" data-reply="false" data-write="<c:if test="${sessionScope.id ne null}">true</c:if>">글쓰기</button>
                </c:when>
            </c:choose>

        </div>

        <table class="board-table">
            <thead class="board-thead"></thead>
            <tbody class="board-tbody">

            <c:forEach items="${boardList}" var="boardList">
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
                                        <a class="title-text" href="/${boardList.brdType}/view?no=${boardList.brdNo}">${boardList.brdTitle}</a>
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
                        <%--<i class="fab fa-sistrix"></i>--%>
                        <img src="/static/image/common/board_search.png">
                        <span>${boardList.brdCount}</span>
                    </td>
                    <td class="board-like">
                        <%--<i class="fas fa-heart"></i>--%>
                        <img src="/static/image/common/board_like.png">
                        <span>21</span>
                    </td>
                </tr>
            </c:forEach>

            <%--<tr>--%>
                <%--<td class="board-divide">--%>
                    <%--<span>정보</span>--%>
                <%--</td>--%>
                <%--<td class="board-subject">--%>
                    <%--<div class="board-thumb">--%>
                        <%--<a class="board-imgbox">--%>
                            <%--<img src="/static/image/profile/profile-14.png">--%>
                        <%--</a>--%>

                        <%--<div class="board-content">--%>
                            <%--<div class="board-title">--%>
                                <%--<p class="board-titletext">--%>
                                    <%--<a class="title-text" href="#">안녕하세욯ㅎㅎㅎㅎ123gggg456789</a>--%>
                                <%--</p>--%>
                                <%--<img src="" class="icon-new">--%>
                            <%--</div>--%>
                            <%--<span class="board-writer"><a>침착해내자신</a></span>--%>
                            <%--<span class="board-bar">｜</span>--%>
                            <%--<span class="board-time"><span>5</span><span>분전</span></span>--%>
                        <%--</div>--%>

                    <%--</div>--%>
                <%--</td>--%>
                <%--<td class="board-hit">--%>
                    <%--<i class="fab fa-sistrix"></i>--%>
                    <%--123--%>
                <%--</td>--%>
                <%--<td class="board-like">--%>
                    <%--<i class="fas fa-heart"></i>--%>
                    <%--21--%>
                <%--</td>--%>
            <%--</tr>--%>
            <!-- -->

            </tbody>
        </table>


        <div class="board-searchbox">


            <select class="custom-select custom-select-sm">

                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/notice/list' }">
                        <option selected value="0">전체</option>
                        <option value="1">공지</option>
                        <option value="2">이벤</option>
                    </c:when>

                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/community/list' }">
                        <option value="0" selected>전체</option>
                        <option value="3" selected>잡담</option>
                        <option value="4">정보</option>
                        <option value="5">요청</option>
                    </c:when>
                </c:choose>


            </select>



            <!-- Grid column -->
            <div class="board-search">
                <!-- Material input -->
                <div class="md-form mt-0">
                    <input type="text" class="form-control" placeholder="검색">
                </div>
            </div>

        </div>





        <nav>
            <ul class="pagination pg-amber board-pagination">
                <li class="page-item">
                    <c:if test="${page.startPage ne 1 }">
                        <a class="page-link" href="/community/list?pageNum=${page.startPage-1}"  aria-label="Previous">
                            <span aria-hidden="true"><i class="fas fa-angle-double-left"></i></span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </c:if>
                </li>
                    <c:forEach begin="${page.startPage }" end="${page.endPage }" step="1" var="i">
                        <li class="page-item board-pageactive <c:if test="${page.criteria.pageNum eq i}">active</c:if>">
                            <a class="page-link" href="/community/list?pageNum=${i}">${i}</a>
                        </li>
                    </c:forEach>
                <li class="page-item">
                    <c:if test="${page.endPage lt page.pageCount }">
                        <a class="page-link" href="/community/list?pageNum=${page.endPage+1}" aria-label="Next">
                            <span aria-hidden="true"><i class="fas fa-angle-double-right"></i></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </c:if>
                </li>
            </ul>
        </nav>


    </div>


</div>