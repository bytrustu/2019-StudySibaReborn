<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <footer class="page-footer font-small pt-4">
        <div class="container">
        <ul class="list-unstyled list-inline text-center">
        </ul>
        </div>

        <div class="footer-copyright text-center py-3">© 2019 Copyright:
        <a href="https://www.studysiba.com"> studysiba.com</a>
        </div>
        </footer>


            <!--Modal: Login / Register Form-->
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



        <script type="text/javascript" src="/static/js/lib/popper.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/mdb.js"></script>
        <script type="text/javascript" src="/static/js/lib/sweetalert2.all.min.js"></script>
        <script type="text/javascript" src="/static/js/lib/Liar.js"></script>
        <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
         <script type="text/javascript" src="/static/js/common.js"></script>
        <script type="text/javascript" src="/static/js/sub.js"></script>


        </body>
        </html>

