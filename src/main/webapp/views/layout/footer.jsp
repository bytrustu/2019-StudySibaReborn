<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


    <%--<%--%>
        <%--session.setAttribute("id","test2");--%>
        <%--session.setAttribute("nick","test4");--%>
        <%--session.setAttribute("auth","ADMIN");--%>
        <%--session.setAttribute("profile","profile-1.png");--%>
    <%--%>--%>

    <div class="pageinfo-box">
        <input type="hidden" id="page-num" value="${cri.pageNum}">
        <input type="hidden" id="page-keyword" value="${cri.keyword}">
        <input type="hidden" id="page-type" value="${cri.type}">
    </div>



    <footer class="page-footer font-small pt-4">
        <div class="container">
            <ul class="list-unstyled list-inline text-center"></ul>
        </div>

        <div class="footer-copyright text-center py-3">© 2019 Copyright:<a href="https://www.studysiba.com"> studysiba.com</a></div>
    </footer>


        <!--회원가입 / 로그인 모달 -->
        <div class="modal fade" id="modalLRForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog cascading-modal" role="document">
                        <div class="modal-content">

                                <div class="modal-c-tabs">

                                <ul class="nav nav-tabs md-tabs tabs-2" role="tablist">
                                        <li class="nav-item waves-effect">
                                            <a class="nav-link active" data-toggle="tab" href="#panel7" role="tab"><i class="fas fa-user mr-1"></i>
                                                    로그인
                                            </a>
                                        </li>
                                        <li class="nav-item waves-effect">
                                            <a class="nav-link" data-toggle="tab" href="#panel8" role="tab"><i class="fas fa-user-plus mr-1"></i>
                                                    가입
                                            </a>
                                        </li>
                                </ul>

                                <div class="tab-content">
                                        <div class="tab-pane fade in show active login-modal-bottom" id="panel7" role="tabpanel">

                                                <div class="modal-body mb-1 modal-body-login">
                                                        <div class="md-form form-sm mb-">
                                                            <i class="far fa-laugh-wink prefix"></i>
                                                            <input type="email" id="input-loginid" class="form-control form-control-sm modal-input login-input">
                                                            <label data-error="wrong" data-success="right" for="input-loginid">아이디</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-4">
                                                                <i class="fas fa-lock prefix"></i>
                                                                <input type="password" id="input-loginpass" class="form-control form-control-sm modal-input login-input" >
                                                                <label for="input-loginpass">비밀번호</label>
                                                        </div>
                                                        <div class="text-center mt-2">
                                                            <button type="button" class="btn btn-warning modal-loginbtn">로그인</button>
                                                        </div>
                                                </div>
                                                <div class="modal-footer modal-footer-login">
                                                        <div class="options text-center text- mt-3 modal-social">
                                                                <img src="/static/image/main/google.png" class="social-login-icon waves-effect social-google" data-name="social-google" data-url="${sessionScope.googleUrl}">
                                                                <img src="/static/image/main/naver.png" class="social-login-icon waves-effect social-naver" data-name="social-naver" data-url="${sessionScope.naverUrl}">
                                                                <img src="/static/image/main/facebook.png" class="social-login-icon waves-effect social-facebook" data-name="social-facebook">
                                                                <img src="/static/image/main/kakao.png" class="social-login-icon waves-effect social-kakao" data-name="social-kakao">
                                                        </div>
                                                </div>

                                        </div>

                                        <div class="tab-pane fade" id="panel8" role="tabpanel">
                                                <div class="modal-body modal-body-join">
                                                        <div class="md-form form-sm mb-1">
                                                                <i class="far fa-laugh-wink prefix"></i>
                                                                <input type="text" id="input-joinid" class="form-control form-control-sm modal-input join-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinid">아이디</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="fas fa-lock prefix"></i>
                                                                <input type="password" id="input-joinpass" class="form-control form-control-sm modal-input join-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinpass">비밀번호</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="far fa-kiss-wink-heart prefix"></i>
                                                                <input type="text" id="input-joinnick" class="form-control form-control-sm modal-input join-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinnick">닉네임</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="far fa-envelope prefix"></i>
                                                                <input type="email" id="input-joinemail" class="form-control form-control-sm modal-input join-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinemail">이메일</label>
                                                        </div>

                                                        <div class="text-center mt-4 mb-2">
                                                            <button type="button" class="btn btn-warning modal-joinbtn">회원가입</button>
                                                        </div>

                                                </div>
                                        </div>
                                </div>
                                </div>
                        </div>
                </div>
        </div>


        <!-- / 경로만 적용 -->
        <c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/' }">
            <!-- 미인증회원 로그인시 모달 -->
            <div class="modal fade" id="modalSendMail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <img src="/static/image/main/siba-default.png" alt="avatar" class="rounded-circle img-responsive">
                        </div>
                        <div class="modal-body text-center mb-1">
                            <h6 class="mt-1 mb-2 modal-resendtext"><span class='modal-resendpoint'>인증</span>이 필요한 아이디 입니다.</h6>
                            <h6 class="mt-1 mb-2 modal-resendtext">필요한 사항의 버튼을 눌러주세요.</h6>
                            <h6 class="mt-1 mb-2 modal-resendtext">정보삭제시 <span class='modal-resendpoint'>재가입</span> 가능합니다.</h6>
                            <h5 class=""></h5>
                            <div class="md-form ml-0 mr-0">
                                <input type="hidden" id="sendmailid">
                            </div>
                            <div class="text-center mt-4 modal-sendbox">
                                <button class="btn btn-warning mt-1 modal-resendbtn" id="modal-deleteinfo">정보삭제</button>
                                <button class="btn btn-warning mt-1 modal-resendbtn" id="modal-resendmail">초대장재전송</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 메일 인증 비밀번호 변경 모달 -->
            <div class="modal fade" id="modalChangePassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                    <div class="modal-content">
                    <div class="modal-header">
                        <img src="/static/image/common/character-3.png" alt="avatar" class="rounded-circle img-responsive">
                    </div>
                    <div class="modal-body text-center mb-1">
                        <h6 class="mt-1 mb-2 modal-resendtext"><span class='modal-resendpoint'>비밀번호</span>를 입력해주세요.</h6>
                        <h6 class="mt-1 mb-2 modal-resendtext"><span class='modal-resendpoint'>영어숫자</span>포함 5~16자 설정!</h6>
                        <h5 class=""></h5>
                        <div class="md-form ml-0 mr-0">
                            <input type="hidden" id="authId" value="${sessionScope.authId}">
                            <input type="password" class="form-control form-control-sm modal-input" id="changePass">
                        </div>
                        <div class="text-center mt-4 modal-sendbox">
                            <button class="btn btn-warning mt-1 modal-resendbtn" id="modal-changepass">비밀번호변경</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>

            <c:if test="${sessionScope.id ne null }">
                <!-- 비밀번호 변경 모달 -->
                <div class="modal fade" id="modalChangePass" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <img src="/static/image/common/character-5.png" alt="avatar" class="rounded-circle img-responsive">
                            </div>
                            <div class="modal-body text-center mb-1">
                                <h6 class="mt-1 mb-2 modal-resendtext"><span class='modal-resendpoint'>비밀번호</span>를 입력해주세요.</h6>
                                <h6 class="mt-1 mb-2 modal-resendtext"><span class='modal-resendpoint'>영어숫자</span>포함 5~16자 설정!</h6>
                                <h5 class=""></h5>
                                <div class="md-form ml-0 mr-0">
                                    <input type="password" class="form-control form-control-sm modal-input">
                                </div>
                                <div class="text-center mt-4 modal-sendbox">
                                    <button class="btn btn-warning mt-1 modal-resendbtn change-btn" data-change="password">비밀번호변경</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 닉네임 변경 모달 -->
                <div class="modal fade" id="modalChangeNick" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <img src="/static/image/common/character-4.png" alt="avatar" class="rounded-circle img-responsive">
                            </div>
                            <div class="modal-body text-center mb-1">
                                <h6 class="mt-1 mb-2 modal-resendtext">원하는<span class='modal-resendpoint'> 닉네임</span>를 입력해주세요.</h6>
                                <h6 class="mt-1 mb-2 modal-resendtext">한글6자,영문숫자12자 <span class='modal-resendpoint'>제한!</span></h6>
                                <h5 class=""></h5>
                                <div class="md-form ml-0 mr-0">
                                    <input type="text" class="form-control form-control-sm modal-input">
                                </div>
                                <div class="text-center mt-4 modal-sendbox">
                                    <button class="btn btn-warning mt-1 modal-resendbtn change-btn" data-change="nick">닉네임변경</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 프로필 변경 모달 -->
                <div class="modal fade" id="modalChangeProfile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <img src="/static/image/common/rotate.png" class="image-target">
                                <img src="/static/image/profile/${profile}" alt="프로필사진" class="rounded-circle img-responsive logined-profile">
                            </div>
                            <div class="modal-body text-center mb-1">
                                <h6 class="mt-1 mb-2 modal-resendtext">원하는 <span class='modal-resendpoint'>프로필</span>을 설정 해보세요</h6>
                                <h6 class="mt-1 mb-2 modal-resendtext">상단 <span class='modal-resendpoint'>버튼</span>으로 바꿀수 있어요!</h6>
                                <div class="text-center mt-4 modal-sendbox">
                                    <input type="text" class="form-control form-control-sm modal-input modal-inputprofile" value="${sessionScope.profile}">
                                    <button class="btn btn-warning mt-1 modal-resendbtn change-btn" data-change="profile">프로필변경</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- 페이스북 sdk init -->
            <script type="text/javascript">
                (function (d, s, id) {
                    var js, fjs = d.getElementsByTagName(s)[0];
                    if (d.getElementById(id)) return;
                    js = d.createElement(s);
                    js.id = id;
                    js.src = "https://connect.facebook.net/en_US/sdk.js";
                    fjs.parentNode.insertBefore(js, fjs);
                }(document, 'script', 'facebook-jssdk'));
                    fbAsyncInit = () => {
                        FB.init({
                            appId: '260762411499713',
                            xfbml: true,
                            version: 'v3.2'
                        });
                    FB.AppEvents.logPageView();
                    };
            </script>
        </c:if>



    <!-- 공지사항/커뮤니티 글쓰기 모달 -->
    <div class="modal fade basic-modal" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModalLabel" aria-hidden="true">
        <div class="modal-dialog basic-modal-dialog" role="document">
            <div class="modal-content basic-modal-content">
                <div class="basic-modal-box">
                    <div class="modal-header basic-modal-header">

                        <select class="browser-default custom-select basic-modal-select">

                            <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/notice/list' || requestScope['javax.servlet.forward.servlet_path'] eq '/board/notice/view'  }">
                                    <option value="1" selected>공지</option>
                                    <option value="2">이벤</option>
                                </c:when>

                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/board/community/list' || requestScope['javax.servlet.forward.servlet_path'] eq '/board/community/view' }">
                                    <option value="3" selected>잡담</option>
                                    <option value="4">정보</option>
                                    <option value="5">요청</option>
                                </c:when>
                            </c:choose>


                        </select>

                        <div class="md-form md-outline margin-init basic-modal-title">
                            <input type="text" id="board-input-title" class="form-control board-input-title" maxlength="50">
                            <label for="board-input-title">제목</label>
                        </div>

                    </div>
                    <div class="modal-body basic-modal-body">
                                <textarea name="editor" id="editor"></textarea>
                    </div>
                    <div class="modal-footer basic-modal-footer">
                        <button type="button" class="btn btn-yellow studysiba-button write-btn">글쓰기</button>
                        <button type="button" class="btn btn-yellow studysiba-button update-btn">수정</button>
                        <button type="button" class="btn btn-yellow studysiba-button studysiba-cancel" data-dismiss="modal">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>




    <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/study' ) || fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/group' ) }">
        <!-- 스터디모달 -->
        <div class="modal fade basic-modal" id="studyModal" tabindex="-1" role="dialog" aria-labelledby="studyModalLabel" aria-hidden="true">
            <div class="modal-dialog basic-modal-dialog" role="document">
                <div class="modal-content basic-modal-content study-modal-content">
                    <div class="basic-modal-box">
                        <div class="modal-header basic-modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>

                            <section id="employer-post-new-job">
                                <div class="row st-stepper">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-xs-10 col-xs-offset-1 stm-top" id="container">
                                                <div class="res-steps-container">
                                                    <div class="res-steps res-step-one active" data-class=".res-form-one">
                                                        <div class="res-step-bar">1</div>
                                                        <div class="res-progress-bar"></div>
                                                        <div class="res-progress-title">주제</div>
                                                    </div>
                                                    <div class="res-steps res-step-two" data-class=".res-form-two">
                                                        <div class="res-step-bar">2</div>
                                                        <div class="res-progress-bar"></div>
                                                        <div class="res-progress-title">일정</div>
                                                    </div>
                                                    <div class="res-steps res-step-three" data-class=".res-form-three">
                                                        <div class="res-step-bar">3</div>
                                                        <div class="res-progress-bar"></div>
                                                        <div class="res-progress-title">그룹</div>
                                                    </div>
                                                    <div class="res-steps res-step-four" data-class=".res-form-four">
                                                        <div class="res-step-bar">4</div>
                                                        <div class="res-progress-bar"></div>
                                                        <div class="res-progress-title">상세</div>
                                                    </div>
                                                </div>
                                                <div class="clearfix">&nbsp;</div>
                                                <div class="clearfix">&nbsp;</div>


                                                <!-- Step 1 주제 -->
                                                <div class="res-step-form col-md-6 col-md-offset-2 res-form-one">
                                                    <div class="stm-title">
                                                        <img src="/static/image/study/reading.png">
                                                        <p>어떤 주제와 관련된 스터디 인가요?</p>
                                                    </div>
                                                    <div class="form-group">
                                                        <p class="col-sm-12 stm-subtext"><span>주제선정 :</span><span>현재 </span><span class="sujectCnt">0</span><span> 개 선택</span></p>
                                                        <div class="col-sm-12">
                                                            <span class="stm-divide">
                                                                <span>프로그래밍</span>
                                                                <input type="hidden" class="input-subject" data-subject="프로그래밍" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>외국어</span>
                                                                <input type="hidden" class="input-subject" data-subject="외국어" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>취업</span>
                                                                <input type="hidden" class="input-subject" data-subject="취업" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>자격증</span>
                                                                <input type="hidden" class="input-subject" data-subject="자격증" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>동기부여</span>
                                                                <input type="hidden" class="input-subject" data-subject="동기부여" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>맛집</span>
                                                                <input type="hidden" class="input-subject" data-subject="맛집" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>면접</span>
                                                                <input type="hidden" class="input-subject" data-subject="면접" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>IT</span>
                                                                <input type="hidden" class="input-subject" data-subject="IT" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>수능</span>
                                                                <input type="hidden" class="input-subject" data-subject="수능" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>여행</span>
                                                                <input type="hidden" class="input-subject" data-subject="여행" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>공부진도체크</span>
                                                                <input type="hidden" class="input-subject" data-subject="공부진도체크" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>출석체크</span>
                                                                <input type="hidden" class="input-subject" data-subject="출석체크" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>창업</span>
                                                                <input type="hidden" class="input-subject" data-subject="창업" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>대학</span>
                                                                <input type="hidden" class="input-subject" data-subject="대학" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>취미</span>
                                                                <input type="hidden" class="input-subject" data-subject="취미" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>운동</span>
                                                                <input type="hidden" class="input-subject" data-subject="운동" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>웹개발</span>
                                                                <input type="hidden" class="input-subject" data-subject="웹개발" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                            <span class="stm-divide">
                                                                <span>앱개발</span>
                                                                <input type="hidden" class="input-subject" data-subject="앱개발" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>


                                                            <span class="stm-divide">
                                                                <span>그외</span>
                                                                <input type="hidden" class="input-subject" data-subject="그외" value="false">
                                                                <svg class="ico" width="24" height="24" viewBox="0 0 24 24">
                                                                    <path d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z"></path>
                                                                </svg>
                                                            </span>

                                                         </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="text-center">
                                                            <button type="button" class="btn btn-warning col-xs-offset-1 btn res-btn-orange stm-step1" data-class=".res-form-one">다음</button>
                                                        </div>
                                                    </div>
                                                </div>




                                                <!-- Step 2 일정 -->
                                                <div class="res-step-form col-md-6 col-md-offset-2 res-form-two">
                                                    <div class="stm-title">
                                                        <img src="/static/image/study/round-table.png">
                                                        <p>팀원과 함께할 장소와 기간을 지정해주세요.</p>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">장소</label>
                                                        <div class="col-sm-12">
                                                            <input type="hidden" id="stm-lat"><input type="hidden" id="stm-lng"><input type="hidden" id="stm-place">
                                                            <input type="text" class="form-control" id="pac-input" name="address" placeholder="스터디 장소를 검색하세요.">
                                                            <div id="map" style="width: 100%;height: 300px"></div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">

                                                        <div class="row stm-date">
                                                            <div class="col-sm-6">
                                                                <label>시작일자</label>
                                                                <input type="text" class="form-control datepickter inputs_toPer" id="inputs_toPer" name="toPer" placeholder="일자가 선택되지 않았습니다.">
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <label>종료일자</label>
                                                                <input type="text" class="form-control datepickter inputs_fromPer" id="inputs_fromPer" name="fromPer" placeholder="일자가 선택 되지않았습니다">
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <div class="text-center">
                                                            <button type="button" class="btn btn-warning btn res-btn-gray" data-class=".res-form-two">이전</button>
                                                            <button type="button" class="btn btn-warning col-xs-offset-1 btn res-btn-orange stm-step2" data-class=".res-form-two">다음</button>
                                                        </div>
                                                    </div>
                                                </div>





                                                <!-- Step 3 그룹 -->
                                                <div class="res-step-form col-md-6 col-md-offset-2 res-form-three">
                                                    <div class="stm-title">
                                                        <img src="/static/image/study/teamwork.png">
                                                        <p>스터디그룹 정보를 입력 해주세요.</p>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">스터디명</label>
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="stm-title" maxlength="20" placeholder="스터디명을 입력 해주세요.">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">참여인원</label>
                                                        <div class="col-sm-12">
                                                            <select name="person" id="std-limit" class="form-control">
                                                                <option value="2">2명</option>
                                                                <option value="3">3명</option>
                                                                <option value="4">4명</option>
                                                                <option value="5">5명</option>
                                                                <option value="6">6명</option>
                                                                <option value="7">7명</option>
                                                                <option value="8">8명</option>
                                                                <option value="9">9명</option>
                                                                <option value="99">제한없음</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">대표이미지</label>
                                                        <div class="file-upload">
                                                            <div class="image-upload-wrap">
                                                                <input class="file-upload-input" id="studyFile" type='file'  onchange="readURL(this);"  accept="image/*" />
                                                                <div class="drag-text">
                                                                    <h3>이미지 드래그앤드롭 & 클릭</h3>
                                                                </div>
                                                            </div>
                                                            <div class="file-upload-content">
                                                                <img class="file-upload-image" src="#" alt="your image" />
                                                                <div class="image-title-wrap">
                                                                    <button type="button" class="remove-image">삭제 : <span class="image-title">Uploaded Image</span></button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="text-center">
                                                            <button type="button" class="btn btn-warning btn res-btn-gray" data-class=".res-form-three">이전</button>
                                                            <button type="button" class="btn btn-warning col-xs-offset-1 btn res-btn-orange stm-step3" data-class=".res-form-three">다음</button>
                                                        </div>
                                                    </div>
                                                </div>





                                                <!-- Step 4 상세 -->
                                                <div class="res-step-form col-md-6 col-md-offset-2 res-form-four">
                                                    <div class="stm-title">
                                                        <img src="/static/image/study/signature.png">
                                                        <p>스터디 상세내용을 입력 해주세요.</p>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">제목</label>
                                                        <div class="col-sm-12">
                                                            <input type="text" class="form-control" id="stm-detailtitle" maxlength="40" placeholder="제목을 입력 해주세요.">
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-sm-12 control-label">내용</label>
                                                        <div class="col-sm-12">
                                                            <textarea name="editor" id="studyEditor"></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="text-center">
                                                            <button type="button" class="btn btn-warning btn res-btn-gray" data-class=".res-form-four">이전</button>
                                                            <button type="button" class="btn btn-primary col-xs-offset-1 btn stm-step4">등록</button>
                                                            <button type="button" class="btn btn-primary col-xs-offset-1 btn stm-update">수정</button>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>

                        </div>
                    </div>
                </div>
            </div>
        </div>















    <!-- 스터디모달 이미지 -->
        <script type="text/javascript">
            $('.remove-image').on('click', function(){
                removeUpload();
            });
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function(e) {
                        $('.image-upload-wrap').hide();
                        $('.file-upload-image').attr('src', e.target.result);
                        $('.file-upload-content').show();
                        $('.image-title').html(input.files[0].name);
                    };
                    reader.readAsDataURL(input.files[0]);
                } else {
                    removeUpload();
                }
            }
            function removeUpload() {
                    $('.file-upload-input').replaceWith($('.file-upload-input').clone());
                    $('.file-upload-content').hide();
                    $('.image-upload-wrap').show();
                }
                $('.image-upload-wrap').bind('dragover', function () {
                    $('.image-upload-wrap').addClass('image-dropping');
                });
                $('.image-upload-wrap').bind('dragleave', function () {
                    $('.image-upload-wrap').removeClass('image-dropping');
                });


            function initAutocomplete() {
                 <c:choose>
                     <c:when test="${studyView eq null}">
                         var lat = 37.5640253;
                         var lng = 126.97377929999993;
                     </c:when>
                     <c:otherwise>
                         var lat = ${studyView.stdLat};
                         var lng = ${studyView.stdLng};
                     </c:otherwise>
                 </c:choose>
                var map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: lat, lng: lng},
                    zoom: 15
                });
                <c:if test="${studyView ne null}">
                    var origin = {lat: lat, lng: lng};
                    var marker = new google.maps.Marker({
                    position: origin,
                    map: map,
                    title: '${studyView.stdGroup}'
                    });
                </c:if>


        <c:if test="${studyView ne null}">
            var viewMap = new google.maps.Map(document.getElementById('viewMap'), {
            center: {lat: lat, lng: lng},
            zoom: 15
            });
            var origin = {lat: lat, lng: lng};
            var marker = new google.maps.Marker({
            position: origin,
            map: viewMap,
            title: '${studyView.stdGroup}'
            });
        </c:if>



                var input = document.getElementById('pac-input');
                var searchBox = new google.maps.places.SearchBox(input);
                map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
                map.addListener('bounds_changed', function() {
                searchBox.setBounds(map.getBounds());
                });
                var markers = [];

                searchBox.addListener('places_changed', function() {
                    var places = searchBox.getPlaces();

                    if (places.length == 0) {
                        return;
                    }
                    markers.forEach(function(marker) {
                        marker.setMap(null);
                    });
                    markers = [];
                    var bounds = new google.maps.LatLngBounds();
                    let lat = places[0].geometry.location.lat();
                    let lng = places[0].geometry.location.lng();
                    let placeName = places[0].name;
                    $('#stm-lat').val(lat);
                    $('#stm-lng').val(lng);
                    $('#stm-place').val(placeName);
                    $('#pac-input').val(places[0].formatted_address);
                    console.log(places[0].name);

                    places.forEach(function(place) {
                        if (!place.geometry) {
                            return;
                        }
                        markers.push(new google.maps.Marker({
                            map: map,
                            title: place.name,
                            position: place.geometry.location
                        }));

                        if (place.geometry.viewport) {
                            bounds.union(place.geometry.viewport);
                        } else {
                            bounds.extend(place.geometry.location);
                        }
                    });
                    map.fitBounds(bounds);
                });
            }
        </script>

    </c:if>


        <!-- / 경로가 아닌경우 로그인/회원가입 모달 클릭시 -->
        <c:if test="${requireLogin eq true }">
            <script type="text/javascript">
                setTimeout( () => {
                    $('#modalLRForm').modal('show');
                }, 300);
            </script>
        </c:if>


        <!-- 상태코드 메세지에 따른 결과 처리 -->
        <c:if test="${sessionScope.stateCode ne null}">
            <script type="text/javascript">
                $(document).ready(function () {
                    let stateComment = '${sessionScope.stateCode}';
                    let stateFlag = stateComment.includes('SUCCESS');
                    // 패스워드이메일인증
                    if (stateComment.includes('PASSAUTH')) {
                        timerAlert('비밀번호변경 인증', '이메일 정보를 인증중 입니다.', 2000);
                        setTimeout(() => {
                        stateFlag ? $('#modalChangePassword').modal('show') : errorAlert(stateCode.get(stateComment));
                        }, 2300);
                    // 초대장메일인증
                    } else {
                        stateComment.includes('SUCCESS') ? successAlert(stateCode.get(stateComment)) : errorAlert(stateCode.get(stateComment));
                    }
                });
                <c:remove var="stateCode" scope="session"/>
            </script>
        </c:if>




    <div class="alert alert-danger" id="dangerMessage" style="display: none; z-index: 9999;">
    </div>


        <script type="text/javascript" src="/static/js/lib/popper.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/mdb.js"></script>
        <script type="text/javascript" src="/static/js/lib/sweetalert2.all.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/Liar.js"></script>
        <script type="text/javascript" src="/static/js/lib/kakao.min.js"></script>
        <script async charset="utf-8" src="//cdn.embedly.com/widgets/platform.js"></script>
        <script type="text/javascript" src="/static/dist/ckeditor.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <script type="text/javascript" src="/static/js/common.js"></script>
        <script type="text/javascript" src="/static/js/messenger.js"></script>
        <c:choose>
            <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/' }">
                <script type="text/javascript" src="/static/js/main.js"></script>
            </c:when>
            <c:otherwise>
                <script type="text/javascript" src="/static/js/sub.js"></script>
                <script type="text/javascript" src="/static/js/study.js"></script>
                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/study' ) || fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/group' ) }">
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByjX-fIiEVgNTofLuWWpxgGqQADaoNSWk&libraries=places&callback=initAutocomplete" async defer></script>
                </c:if>
                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/admin' )}">
                    <script src="https://www.gstatic.com/charts/loader.js"></script>
                    <script src="/static/js/admin.js" ></script>
                </c:if>

                <c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'] , '/group/view' ) }">
                    <script src="/static/js/groupmessage.js" ></script>
                </c:if>
            </c:otherwise>
        </c:choose>

    </body>
    </html>

