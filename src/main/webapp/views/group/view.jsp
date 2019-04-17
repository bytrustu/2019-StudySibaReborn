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
            <div class="board-total stg-groupname">
                <img class="st-icon" src="/static/image/study/startup.png">
                <span>${studyView.stdGroup}</span>
                <c:if test="${sessionScope.id eq studyView.stdId || sessionScope.auth eq 'ADMIN'}">
                    <img src="/static/image/common/edit.png" class="study-edit stg-edit" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="수정">
                    <c:choose>
                        <c:when test="${studyView.stdAvailable == 1}">
                            <img src="/static/image/common/delete.png" class="study-delete stg-delete" data-delete="delete" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="비활성">
                        </c:when>
                        <c:otherwise>
                            <img src="/static/image/common/open.png" class="study-delete stg-delete" data-delete="open" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="활성">
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>

            <c:choose>
                <c:when test="${sessionScope.id eq studyView.stdId}">
                    <button class="btn btn-warning group-noticebtn">공지작성</button>
                </c:when>
                <c:when test="${sessionScope.id ne studyView.stdId}">
                    <button class="btn btn-danger group-outbtn group-out" data-id="${sessionScope.id}">그룹탈퇴</button>
                </c:when>
            </c:choose>

        </div>

        <div class="board-body stv-body">

            <div class="row stv-row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Notice</h3>
                            <h4 class="stv-subtitle">공지사항</h4>
                        </div>
                        <div class="col-md-8">

                            <div class="stg-board">

                                <c:if test="${fn:length(notice) == 0}">
                                    <div class="group-notfound">
                                        <p>(⁎˃ᆺ˂)</p>
                                        <p>등록 된 공지사항이 없습니다.</p>
                                    </div>
                                </c:if>

                                <c:forEach items="${notice}" var="notice">
                                    <div class="row stg-notice">
                                        <span data-no="${notice.grbNo}">${notice.grbTitle}</span>
                                        <c:if test="${notice.grbFilename ne null}">
                                            <img src="/static/image/study/file.png" class="stg-icon" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="파일있음">
                                        </c:if>
                                        <span class="stg-lasttime">-${notice.lastTime}</span>
                                    </div>
                                </c:forEach>

                            </div>
                            <nav>
                                <ul class="pagination pg-amber board-pagination mb-0">
                                    <li class="page-item">
                                        <c:if test="${page.startPage ne 1 }">
                                            <a class="page-link"
                                               href='/group/view?no=${param.no}&pageNum=${page.startPage-1}'
                                               aria-label="Previous">
                                                <span aria-hidden="true"><i class="fas fa-angle-double-left"></i></span>
                                                <span class="sr-only">Previous</span>
                                            </a>
                                        </c:if>
                                    </li>
                                    <c:forEach begin="${page.startPage }" end="${page.endPage }" step="1" var="i">
                                        <li class="page-item board-pageactive <c:if test="${page.criteria.pageNum eq i}">active</c:if>">
                                            <a class="page-link" href="/group/view?no=${param.no}&pageNum=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item">
                                        <c:if test="${page.endPage lt page.pageCount}">
                                            <a class="page-link"
                                               href='/group/view?no=${param.no}&pageNum=${page.endPage+1}'
                                               aria-label="Next">
                                                <span aria-hidden="true"><i
                                                        class="fas fa-angle-double-right"></i></span>
                                                <span class="sr-only">Next</span>
                                            </a>
                                        </c:if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Chatting</h3>
                            <h4 class="stv-subtitle">그룹채팅</h4>
                        </div>

                        <div class="col-md-8">

                            <div class="container groupmsg-container scrollbar  scrollbar scrollbar-warning force-overflow">

                                <c:if test="${fn:length(groupMessage) == 0}">
                                    <div class="group-notfound">
                                        <p>(⁎˃ᆺ˂)</p>
                                        <p>등록 된 그룹채팅이 없습니다.</p>
                                    </div>
                                </c:if>
                                <c:forEach items="${groupMessage}" var="message">
                                    <div class="groupmsg-box center-block">
                                        <div class="row">
                                            <div class="col-xs-8 col-md-8">
                                                <img src="/static/image/profile/${message.mbrProfile}" class="groupmsg-photo messenger-connector"  data-id="${message.grmId}" data-nick="${message.mbrNick}"  data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="${message.mbrNick}와 채팅">
                                                <h4 class="groupmsg-name">${message.mbrNick}</h4>
                                            </div>
                                            <div class="col-xs-4 col-md-4 text-right groupmsg-date"><fmt:formatDate value="${message.grmDate}" pattern="MM-dd HH:mm"/></div>
                                        </div>
                                        <div class="row groupmsg-text">
                                            ${message.grmText}
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>

                            <div class="row groupmsg-bottom">
                                <div class="col-md-12">
                                    <div class="input-group">
                                        <input type="text" class="form-control groupmsg-input">
                                        <span class="input-group-btn">
                                                    <button class="btn btn-default groupmsg-send" type="button">전송</button>
                                                </span>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>


            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Member</h3>
                            <h4 class="stv-subtitle">스터디 함께하고 있는 멤버</h4>
                        </div>
                        <div class="col-md-8">

                            <svg class="crown" viewbox="0 0 140 140">
                                <path fill="gold">
                                    <animate
                                            attributeName="d"
                                            dur="1440ms"
                                            repeatCount="indefinite"
                                            values="M 10,110 L 10,10 L 40,50 L 70,10 L 100,50 L 130,10 L 130,110 z;M 30,110 L 0,0 L 50,50 L 70,0 L 90,50 L 140,0 L 110,110 z;M 10,110 L 10,10 L 40,50 L 70,10 L 100,50 L 130,10 L 130,110 z;"
                                    />
                                </path>
                            </svg>

                            <c:forEach items="${groupMember}" var="group" varStatus="status">
                                <c:set var="imgStep"
                                       value='${fn:substring(group.mbrProfile, fn:indexOf(group.mbrProfile,"-")+1, fn:indexOf(group.mbrProfile,".png"))}'/>
                                <div class="card stv-user">
                                    <div class="card-top stv-usertop
                                <c:choose>
                                    <c:when test="${imgStep == 1}">user-bgone</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">user-bgtwo</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">user-bgthree</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">user-bgfour</c:when>
                                </c:choose>
                                ">
                                        <c:if test="${sessionScope.id eq studyView.stdId || sessionScope.auth eq 'ADMIN'}">
                                            <c:if test="${status.count > 1}">
                                                <img src="/static/image/study/x.png" class="out-icon" data-id="${group.grmId}" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="내보내기">
                                            </c:if>
                                        </c:if>
                                        <img src="/static/image/profile/${group.mbrProfile}">
                                    </div>
                                    <div class="triangle
                                <c:choose>
                                    <c:when test="${imgStep == 1}">triangle-one</c:when>
                                    <c:when test="${imgStep > 1 && imgStep <= 8}">triangle-two</c:when>
                                    <c:when test="${imgStep >= 9 && imgStep <= 12}">triangle-three</c:when>
                                    <c:when test="${imgStep >= 13 && imgStep <= 18}">triangle-four</c:when>
                                </c:choose>
                                ">
                                        <div class="stv-msgbtn messenger-connector" data-id="${group.grmId}" data-nick="${group.mbrNick}">
                                            <img class="stv-msgimg" src="/static/image/main/like.png">
                                        </div>
                                    </div>
                                    <div class="card-bottom text-center stv-userbottom">
                                            ${group.mbrNick}
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row stv-row">
                <div class="col-md-12">
                    <hr class="stv-hr">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stv-line"></div>
                            <h3 class="stv-title">Schedule</h3>
                            <h4 class="stv-subtitle">스터디 일정</h4>
                        </div>
                        <div class="col-md-8">
                            <div class="stv-period">
                                <img src="/static/image/study/round-table.png" class="stv-icon">
                                <h4>${studyView.stdStart}</h4><span>부터</span> <h4>${studyView.stdEnd}</h4>
                                <span>까지 예정</span>
                            </div>
                            <div id="viewMap"></div>
                            <div class="stv-address">
                                <img src="/static/image/study/placeholder.png" class="stv-icon">
                                <span class="stv-locate">${studyView.stdAddress} ${studyView.stdPlace}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>


<div class="modal fade basic-modal" id="groupModal" tabindex="-1" role="dialog" aria-labelledby="groupModalLabel"
     aria-hidden="true">
    <div class="modal-dialog basic-modal-dialog" role="document">
        <div class="modal-content basic-modal-content svg-modal-content">
            <div class="col-md-6 col-md-offset-2 m-auto">
                <div class="stm-title">
                    <img src="/static/image/study/writer.png">
                    <p>공지사항을 입력 해주세요</p>
                </div>

                <div class="form-group">
                    <label class="col-sm-12 control-label">제목</label>
                    <div class="col-sm-12">
                        <input type="text" class="form-control" id="stg-titleinput" maxlength="40"
                               placeholder="제목을 입력 해주세요.">
                    </div>
                </div>
                <div class="input-default-wrapper mt-3">
                    <span class="input-group-text mb-3" id="input1">업로드</span>
                    <input type="file" id="stg-fileinput" class="input-default-js">
                    <label class="label-for-default-js rounded-right mb-3" for="stg-fileinput"><span
                            class="span-choose-file">파일선택</span>
                        <div class="float-right span-browse">지정</div>
                    </label>
                </div>

                <div class="form-group">
                    <label class="col-sm-12 control-label">내용</label>
                    <div class="col-sm-12">
                        <textarea name="editor" id="groupEditor"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="text-center mt-5">
                        <button type="button" class="btn btn-warning col-xs-offset-1 stg-register studysiba-button">등록
                        </button>
                        <button type="button" class="btn btn-warning col-xs-offset-1 stg-update studysiba-button">수정
                        </button>
                        <button type="button" class="btn btn-danger studysiba-button" data-dismiss="modal">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade basic-modal" id="noticeModal" tabindex="-1" role="dialog" aria-labelledby="noticeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog basic-modal-dialog" role="document">
        <div class="modal-content basic-modal-content svg-modal-content">
            <div class="col-md-6 col-md-offset-2 m-auto">
                <div class="stm-title">
                    <img src="/static/image/study/writer.png">
                    <p>공지사항</p>
                </div>

                <div class="form-group stg-row">
                    <label class="col-sm-12 control-label">제목</label>
                    <div class="col-sm-12 svg-title">

                    </div>
                </div>
                <div class="form-group stg-row">
                    <label class="col-sm-12 control-label">첨부파일</label>
                    <div class="col-sm-12 svg-download">

                    </div>
                </div>
                <div class="form-group stg-row">
                    <label class="col-sm-12 control-label">내용</label>
                    <div class="col-sm-12 svg-content">

                    </div>
                </div>

                <div class="form-group">
                    <div class="text-center mt-5">
                        <c:if test="${sessionScope.id eq studyView.stdId || sessionScope.auth eq 'ADMIN'}">
                            <button type="button"
                                    class="btn btn-warning col-xs-offset-1 stg-noticeedit studysiba-button">수정
                            </button>
                        </c:if>
                        <button type="button" class="btn btn-danger studysiba-button" data-dismiss="modal">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

