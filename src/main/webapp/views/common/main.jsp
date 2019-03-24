<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <title>스터디시바</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta property="og:url" content="http://www.studysiba.com">
    <meta property="og:type" content="website">
    <meta property="og:title" content="스터디시바">
    <meta property="og:description" content="온라인 스터디 커뮤니티">
    <meta property="og:image" content="https://i.imgur.com/ltNAoPV.jpg">
    <link rel="icon" type="image/x-icon" href="/static/image/main/sibacon.ico">

    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/mdb.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/static/css/main.css">
</head>


<body>

<!-- 하단 메세지 버튼 -->
<div class="messenger-btn">
    <i class="fas fa-envelope"></i>
</div>

<!-- 상단 메뉴바 -->
<nav class="navbar navbar-expand-sm sticky-top shadow-sm p-2">
    <a class="navbar-brand pl-3" href="#"><img src="/static/image/main/studysiba-logo.png"></a>
    <a class="navbar-toggle" id="toggleBtn"><i class="fa fa-bars"></i></a>
    <ul class="navbar-nav position-absolute">
        <li class="nav-item text-right">
            <a class="nav-link" href="#">홈</a>
        </li>
        <li class="nav-item text-right">
            <a class="nav-link" href="#">공지사항</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">커뮤니티</a>
        </li>
        <li class="nav-item text-right">
            <a class="nav-link" href="#">스터디참여</a>
        </li>
        <li class="nav-item text-right">
            <a class="nav-link" href="#">스터디그룹</a>
        </li>
        <li class="login-button nav-item text-right">
            <div class="login-eff"></div>
            <a href="#" data-toggle="modal" data-target="#modalLRForm">로그인/가입</a>
        </li>
    </ul>
</nav>



<div class="container-fluid">

    <!-- 상단 헤드 그림 -->
    <div class="col-12 headline">
        <img src="/static/image/main/studysiba.png">
    </div>

    <!-- 정보 뷰 -->
    <div class="row main-content mt-5">
        <div class="col-lg-4 info-box">
            <div class="info-leftbox">
                <div class="info-header">
                    <img src="/static/image/main/like.png">
                    <p>시바랭킹</p>
                </div>
                <div class="info-body">
                    <div class="row mt-3 member-rank">
                        <img src="/static/image/main/rank1.png">
                        <img src="/static/image/main/1.jpg">
                        <p>
                            <span>1위 !!! 하하호호하하</span><br />
                            <span class="point" id="point1" data-accept="false">5000</span><span>점</span>
                        </p>
                    </div>
                    <div class="row mt-2 member-rank">
                        <img src="/static/image/main/rank2.png">
                        <img src="/static/image/main/1.jpg">
                        <p>
                            <span>2위 !!! 쟁</span><br />
                            <span class="point" data-accept="false">3000</span><span>점</span>
                        </p>
                    </div>
                    <div class="row mt-2 member-rank">
                        <img src="/static/image/main/rank3.png">
                        <img src="/static/image/main/1.jpg">
                        <p>
                            <span>3위 !!! 슺득</span><br />
                            <span class="point" data-accept="false">1000</span><span>점</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-4 info-box">
            <div class="info-centerbox">
                <div class="info-header">
                    <img src="/static/image/main/like.png">
                    <p>접속중 ~</p>
                </div>
                <div class="info-body login-box">
                    <div class="member-loginstate mt-3">
                        <!-- 유저 목록 시작 -->
                        <div class="member-output">
                            <img src="/static/image/main/1.jpg">
                            <div class="member-infobox">
                                <div class="row">
                                    하하호호후후
                                </div>
                                <div class="row">
                                    <i class="far fa-comment-dots"></i>
                                    <i class="far fa-grin-wink"></i>
                                </div>
                            </div>
                        </div>
                        <!-- 1명 끝 -->


                        <!-- 유저 목록 시작 -->
                        <div class="member-output">
                            <img src="/static/image/main/1.jpg">
                            <div>
                                <div class="row">
                                    abcdefasd231
                                </div>
                                <div class="row">
                                    <i class="far fa-comment-dots"></i>
                                    <i class="far fa-grin-wink"></i>
                                </div>
                            </div>
                        </div>
                        <!-- 1명 끝 -->

                        <!-- 유저 목록 시작 -->
                        <div class="member-output">
                            <img src="/static/image/main/1.jpg">
                            <div>
                                <div class="row">
                                    가나다라마바사아
                                </div>
                                <div class="row">
                                    <i class="far fa-comment-dots"></i>
                                    <i class="far fa-grin-wink"></i>
                                </div>
                            </div>
                        </div>
                        <!-- 1명 끝 -->

                        <!-- 유저 목록 시작 -->
                        <div class="member-output">
                            <img src="/static/image/main/1.jpg">
                            <div>
                                <div class="row">
                                    가나다라마바사
                                </div>
                                <div class="row">
                                    <i class="far fa-comment-dots"></i>
                                    <i class="far fa-grin-wink"></i>
                                </div>
                            </div>
                        </div>
                        <!-- 1명 끝 -->

                        <!-- 유저 목록 시작 -->
                        <div class="member-output">
                            <img src="/static/image/main/1.jpg">
                            <div>
                                <div class="row">
                                    가나다라마바사
                                </div>
                                <div class="row">
                                    <i class="far fa-comment-dots"></i>
                                    <i class="far fa-grin-wink"></i>
                                </div>
                            </div>
                        </div>
                        <!-- 1명 끝 -->
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-4 info-box">
            <div class="info-rightbox">

                <div class="login-header">
                    <div class="header-text">
                        <p>스터디에 참가하려면 ?</p>
                    </div>
                    <div class="header-button">
                        <button class="header-loginbutton">
                            스터디시바 로그인
                        </button>
                    </div>

                </div>
                <div class="login-footer">
                    <div class="footer-text">
                        <p>비밀번호 재설정</p>
                    </div>
                    <div class="footer-button">
                        <img src="/static/image/main/google.png" class="social-login-icon waves-effect" data-type="google">
                        <img src="/static/image/main/facebook.png" class="social-login-icon waves-effect" data-type="facebook">
                        <img src="/static/image/main/naver.png" class="social-login-icon waves-effect" data-type="naver">
                        <img src="/static/image/main/kakao.png" class="social-login-icon waves-effect" data-type="kakao">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="middleline">
        <div class="row middle-box">
            <img src="/static/image/main/middletop-text.png" class="middleline-logo">
            <span>는 </span> <span class="middle-text">333</span> <span>명의 회원분들이</span>
        </div>
        <div class="row">
            <span>총</span> <span class="middle-text">2019</span> <span>회 방문하셨습니다. <i class="fas fa-grin-tears"></i></span>
        </div>
    </div>
</div>


<div class="slide-buttonbox">
    <div class="button-flag button-lt1" data-flag="lt">
        &lt;
    </div>

    <div class="button-slide button-stop">
        정지
    </div>


    <div class="button-flag button-gt1" data-flag="gt">
        &gt;
    </div>
</div>
<div class="slider-box">
    <div class="study-slider">
        <div class="study-box row">

            <!-- -->
            <div class="study-item">
                <div class="content">
                    1
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서1<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    2
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서2<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    3
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서3<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    4
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서4<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    5
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서5<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서6<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    7
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서7<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    8
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서8<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    9
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서9<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    10
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서10<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    1
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서11<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    2
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서12<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    3
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서13<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    4
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서14<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    5
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서15<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->


            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서16<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서17<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서18<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서19<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->

            <!-- -->
            <div class="study-item">
                <div class="content">
                    6
                </div>
                <div class="details">
                    <div class="image">
                        <img src="/static/image/main/1.jpg">
                    </div>
                    <h3>쟁비서20<br><span>마라톤걸</span></h3>
                </div>
            </div>
            <!-- -->





        </div>
    </div>
</div>










<footer class="page-footer font-small pt-4">
    <div class="container">
        <ul class="list-unstyled list-inline text-center">
        </ul>
    </div>

    <div class="footer-copyright text-center py-3">© 2019 Copyright:
        <a href="https://mdbootstrap.com/education/bootstrap/"> studysiba.com</a>
    </div>
</footer>









<!--Modal: Login / Register Form-->
<div class="modal fade" id="modalLRForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">

            <!--Modal cascading tabs-->
            <div class="modal-c-tabs">

                <!-- Nav tabs -->
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

                <!-- Tab panels -->
                <div class="tab-content">
                    <!--Panel 7-->
                    <div class="tab-pane fade in show active" id="panel7" role="tabpanel">

                        <!--Body-->
                        <div class="modal-body mb-1 modal-body-login">
                            <div class="md-form form-sm mb-">
                                <i class="far fa-laugh-wink prefix"></i>
                                <input type="email" id="modalLRInput10" class="form-control form-control-sm validate">
                                <label data-error="wrong" data-success="right" for="modalLRInput10">아이디</label>
                            </div>

                            <div class="md-form form-sm mb-4">
                                <i class="fas fa-lock prefix"></i>
                                <input type="password" id="modalLRInput11" class="form-control form-control-sm validate">
                                <label data-error="wrong" data-success="right" for="modalLRInput11">비밀번호</label>
                            </div>
                            <div class="text-center mt-2">
                                <button type="button" class="btn btn-warning modal-loginbtn">로그인</button>
                            </div>
                        </div>
                        <!--Footer-->
                        <div class="modal-footer modal-footer-login">
                            <div class="options text-center text- mt-1 modal-social">
                                <img src="/static/image/main/google.png" class="social-login-icon waves-effect" data-type="google">
                                <img src="/static/image/main/facebook.png" class="social-login-icon waves-effect" data-type="facebook">
                                <img src="/static/image/main/naver.png" class="social-login-icon waves-effect" data-type="naver">
                                <img src="/static/image/main/kakao.png" class="social-login-icon waves-effect" data-type="kakao">
                            </div>
                        </div>

                    </div>
                    <div class="tab-pane fade" id="panel8" role="tabpanel">
                        <div class="modal-body modal-body-join">
                            <div class="md-form form-sm mb-1">
                                <i class="far fa-laugh-wink prefix"></i>
                                <input type="text" id="modalLRInput12" class="form-control form-control-sm " required>
                                <label data-error="wrong" data-success="right" for="modalLRInput12">아이디</label>
                            </div>
                            <div class="md-form form-sm mb-1">
                                <i class="fas fa-lock prefix"></i>
                                <input type="password" id="modalLRInput13" class="form-control form-control-sm" required>
                                <label data-error="wrong" data-success="right" for="modalLRInput13">비밀번호</label>
                            </div>
                            <div class="md-form form-sm mb-1">
                                <i class="far fa-kiss-wink-heart prefix"></i>
                                <input type="text" id="modalLRInput14" class="form-control form-control-sm required ">
                                <label data-error="wrong" data-success="right" for="modalLRInput14">닉네임</label>
                            </div>
                            <div class="md-form form-sm mb-1">
                                <i class="far fa-envelope prefix"></i>
                                <input type="email" id="modalLRInput14" class="form-control form-control-sm" required>
                                <label data-error="wrong" data-success="right" for="modalLRInput14">이메일</label>
                            </div>
                            <div class="text-center mt-4 mb-2">
                                <button type="button" class="btn btn-warning modal-loginbtn">회원가입</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>










<script type="text/javascript" src="/static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/static/js/popper.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/js/mdb.js"></script>

<script type="text/javascript" src="/static/js/main.js"></script>

</body>

</html>
