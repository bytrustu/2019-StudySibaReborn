<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
                                                            <input type="email" id="input-loginid" class="form-control form-control-sm modal-input">
                                                            <label data-error="wrong" data-success="right" for="input-loginid">아이디</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-4">
                                                                <i class="fas fa-lock prefix"></i>
                                                                <input type="password" id="input-loginpass" class="form-control form-control-sm modal-input">
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
                                                                <input type="text" id="input-joinid" class="form-control form-control-sm modal-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinid">아이디</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="fas fa-lock prefix"></i>
                                                                <input type="password" id="input-joinpass" class="form-control form-control-sm modal-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinpass">비밀번호</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="far fa-kiss-wink-heart prefix"></i>
                                                                <input type="text" id="input-joinnick" class="form-control form-control-sm modal-input">
                                                                <label data-error="wrong" data-success="right" for="input-joinnick">닉네임</label>
                                                        </div>

                                                        <div class="md-form form-sm mb-1">
                                                                <i class="far fa-envelope prefix"></i>
                                                                <input type="email" id="input-joinemail" class="form-control form-control-sm modal-input">
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



    <!-- Modal -->
    <div class="modal fade basic-modal" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModalLabel" aria-hidden="true">
        <div class="modal-dialog basic-modal-dialog" role="document">
            <div class="modal-content basic-modal-content">
                <div class="basic-modal-box">
                    <div class="modal-header basic-modal-header">

                        <select class="browser-default custom-select basic-modal-select">

                            <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/notice/list' || requestScope['javax.servlet.forward.servlet_path'] eq '/notice/view'  }">
                                    <option value="1" selected>공지</option>
                                    <option value="2">이벤</option>
                                </c:when>

                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/community/list' || requestScope['javax.servlet.forward.servlet_path'] eq '/community/view' }">
                                    <option value="3" selected>잡담</option>
                                    <option value="4">정보</option>
                                    <option value="5">요청</option>
                                </c:when>
                            </c:choose>


                        </select>

                        <div class="md-form md-outline margin-init basic-modal-title">
                            <input type="text" id="board-input-title" class="form-control board-input-title">
                            <label for="board-input-title">제목</label>
                        </div>

                    </div>
                    <div class="modal-body basic-modal-body">
                                <textarea name="editor" id="editor"></textarea>
                    </div>
                    <div class="modal-footer basic-modal-footer">
                        <button type="button" class="btn btn-yellow studysiba-button studysiba-button write-btn">글쓰기</button>
                        <button type="button" class="btn btn-yellow studysiba-button studysiba-cancel" data-dismiss="modal">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


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

        <script type="text/javascript" src="/static/js/lib/popper.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/mdb.js"></script>
        <script type="text/javascript" src="/static/js/lib/sweetalert2.all.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/Liar.js"></script>
        <script type="text/javascript" src="/static/js/lib/kakao.min.js"></script>
        <script async charset="utf-8" src="//cdn.embedly.com/widgets/platform.js"></script>
        <script type="text/javascript" src="/static/dist/ckeditor.js"></script>
        <script type="text/javascript" src="/static/js/common.js"></script>
        <c:choose>
            <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/' }">
                <script type="text/javascript" src="/static/js/main.js"></script>
            </c:when>
            <c:otherwise>
                <script type="text/javascript" src="/static/js/sub.js"></script>
            </c:otherwise>
        </c:choose>

        </body>
        </html>