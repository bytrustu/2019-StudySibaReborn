$(document).ready(function () {



    // CKEDITOR5 설정
    ClassicEditor
        .create(document.querySelector('#editor'), {
            language: 'ko',
            toolbar: {
                viewportTopOffset: 30
            },
            container : {
                overflow : scroll
            },
            ckfinder: {
                uploadUrl: '/upload/community'
            }
        })
        .then(editor => {
            ckContent = editor;
        })
        .catch(error => {
            console.error(error);
        });

    document.querySelectorAll( 'oembed[url]' ).forEach( element => {
        const anchor = document.createElement( 'a' );
        anchor.setAttribute( 'href', element.getAttribute( 'url' ) );
        anchor.className = 'embedly-card';
        element.appendChild( anchor );
    });












    /*
            subpage Enterkey 적용
     */
    enterPressAction('comment-input', 'comment-btn');


    // 게시판 화면사이즈별 text크기 조절
    let contentWidthFix = (widthSize) => {
        switch (window.location.pathname) {
            case '/community/list' :
                if (widthSize <= 375) trimTitleLength('title-text', 14);
                else if (widthSize <= 440) trimTitleLength('title-text', 18);
                else if (widthSize <= 760) trimTitleLength('title-text', 14);
                break;
            case '/notice/list' :
                if (widthSize <= 375) trimTitleLength('title-text', 14);
                else if (widthSize <= 440) trimTitleLength('title-text', 18);
                else if (widthSize <= 760) trimTitleLength('title-text', 14);
                break;
        }
    }




    // 공지사항, 커뮤니티 게시판 반응형 제목 크기 변화
    let widthSize = window.innerWidth;
    contentWidthFix(widthSize);
    $(window).resize(function () {
        contentWidthFix(widthSize);
    });

    // 게시글 등록
    let ckContent = '';
    $('.write-btn').on('click', (e) => {
        e.preventDefault();
        let currentPath =firstPath();
        let boardInfo = new Map();

        if ( $('.board-input-title').val().trim() == '' || ckContent.getData().trim() == '' ){
            errorAlert('항목을 모두 입력해주세요');
            return false;
        }

        if ( typeof $('.post-no').val() != 'undefined' )
        boardInfo.set('brdNo', $('.post-no').val());
        boardInfo.set('brdType', currentPath);
        boardInfo.set('brdDivide', $('.basic-modal-select').val());
        boardInfo.set('brdTitle', $('.board-input-title').val());
        boardInfo.set('brdContent', ckContent.getData());
        boardInfo.set('isReply', $('.content-writebtn').attr('data-reply'));
        let boardJson = mapToJson(boardInfo, false);

        writeBoard(boardJson)
            .then((data) => {
                $('#basicModal').modal('hide');
                timerAlert("글쓰기","글의 정보를 확인중입니다.",1500);
                setTimeout(()=>{successAlert("게시글이 등록되었습니다.");},1500);
                setTimeout(()=>{location.href = `/${currentPath}/view?no=${data.no}`;},3000);
            }).catch((error) => {
                errorAlert('글등록에 실패했습니다');
            });
        });


    // 글쓰기 버튼
    $('.content-writebtn').on('click', function () {
        let currentPath = firstPath();
        if ( currentPath == 'notice' ) {
            if ($(this).attr('data-write') == 'true') {
                $('#basicModal').modal('show');

                $('.write-btn').css('display','inline-block');
                $('.update-btn').css('display','none');

                $('.navbar').css('z-index', 1050);
                $('body.modal-open').css('overflow', 'hidden');
                $('body').css('overflow', 'hidden');
            } else {
                errorAlert('관리자만 접근가능합니다.');
            }
        } else if ( 'community' ) {
            // 로그인 여부 확인
            if ($(this).attr('data-write') == 'true') {
                $('#basicModal').modal('show');

                $('.write-btn').css('display','inline-block');
                $('.update-btn').css('display','none');

                $('.navbar').css('z-index', 1050);
                $('body.modal-open').css('overflow', 'hidden');
                $('body').css('overflow', 'hidden');
            } else {
                confirmAlert('로그인이 필요합니다', '로그인화면으로 이동하시겠습니까?', '/?requireLogin=true')
            }
        }
    });

    // 모달 취소버튼
    $('.studysiba-cancel').on('click', () => {
        $('body').css('overflow', 'auto');
        let currentPath = firstPath();
        if ( currentPath == 'notice' ) {
            $('.basic-modal-select').val('1').prop('selected', true);
        } else if ( currentPath == 'community' ) {
            $('.basic-modal-select').val('3').prop('selected', true);
        }
        initElement('board-input-title');
        ckContent.setData('');
        setTimeout(()=>{

            $('.write-btn').css('display','inline-block');
            $('.update-btn').css('display','none');

        },500)
    });


    // 게시글 삭제
    $('.board-delete').on('click', ()=>{
        let no = $('.post-no').val();
        let currentPath = firstPath();
        let boardInfo = new Map();
        boardInfo.set('brdNo', no);
        boardInfo.set('brdType',currentPath);
        let boardJson = mapToJson(boardInfo);
        Swal.fire({
            title: '게시글삭제',
            text: '선택하신 게시글을 삭제하시겠습니까?',
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#fbc02d',
            cancelButtonColor: '#e4b67c ',
            confirmButtonText: '네'
        }).then((result) => {
            if (result.value) {
                deleteBoard(boardJson)
                    .then( (data) => {
                        successAlert(stateCode.get(data.stateCode));
                        setTimeout(()=>{
                            location.href=`/${currentPath}/list`;
                        },1500);
                    }).catch( (error) => {
                    errorAlert(stateCode.get(error.responseText.stateCode));
                });
            }
        });
    });



    // 댓글 등록 버튼
    $('.comment-btn').on('click', function() {
        let commentInfo = new Map();
        commentInfo.set('cmtBno', $('.post-no').val());
        commentInfo.set('cmtContent', $('.comment-input').val());
        let commentJson = mapToJson(commentInfo);

        if ($(this).attr('data-write') == 'true') {

            if ( commentInfo.get('cmtContent').trim() == '' ){
                errorAlert('댓글을 입력해주세요');
                return false;
            }
            writeComment(commentJson)
                .then( (data) => {
                    successAlert(stateCode.get(data.stateCode));
                    initElement('comment-input');
                    let cnt = $('.post-replycnt').html();
                    cnt = parseInt(cnt)+1;
                    $('.post-replycnt').html(cnt);
                    $('.comment-cnt').html(cnt);
                    getBoard('comment',data.no)
                        .then( (data) => {
                            $('.comment-bottom').append(addComment(data));
                        }).catch( (error) => {
                    });
                }).catch( (error) => {
                errorAlert(stateCode.get(error.responseText.stateCode));
            });

        } else {
            confirmAlert('로그인이 필요합니다', '로그인화면으로 이동하시겠습니까?', '/?requireLogin=true')
        }
    });


    // 댓글 템플릿 레이아웃
    let addComment = (commentJson) => {
        let commentLayout =
            `<div class="comment-list">
                    <input type="hidden" class="comment-no" value="${commentJson.cmtNo}">
                    <div class="comment-content">
                    <img src="/static/image/profile/${commentJson.mbrProfile}">
                    <div class="comment-info">
                    <p>[ ${commentJson.mbrNick} ]
                    <img class="comment-delete" src="/static/image/common/delete2.png">
                    </p>   
                    <p>${commentJson.cmtDate}</p>
                    </div>
                    </div>
                    <p class="comment-text">${commentJson.cmtContent}</p>
                    </div>`;
        return commentLayout;
    };


    // 댓글 삭제
    $(document).on('click','.comment-delete', function(){
        let currentList = $(this).parents('.comment-list');
        let no = currentList.children('.comment-no').val();
        console.log(no);
        let boardInfo = new Map();
        boardInfo.set('cmtNo', no);
        boardInfo.set('brdType','comment');
        let boardJson = mapToJson(boardInfo);
        Swal.fire({
            title: '댓글삭제',
            text: '선택하신 댓글을 삭제하시겠습니까?',
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#fbc02d',
            cancelButtonColor: '#e4b67c ',
            confirmButtonText: '네'
        }).then((result) => {
            if (result.value) {
                deleteBoard(boardJson)
                    .then( (data) => {
                        currentList.remove();
                        let replyCnt = $('.post-replycnt');
                        let commentCnt = $('.comment-cnt');
                        let cnt = parseInt($('.post-replycnt').html())-1;
                        replyCnt.html(cnt);
                        commentCnt.html(cnt);
                        successAlert(stateCode.get(data.stateCode));
                    }).catch( (error) => {
                    errorAlert(stateCode.get(error.responseText.stateCode));
                });
            }
        });
    });

    // 게시글 수정 모달버튼
    $('.board-edit').on('click', () => {
        let currentPath = firstPath();
        let no = parseInt($('.post-no').val());
        $('#basicModal').modal('show');

        getBoard(currentPath,no)
            .then( (data) => {
                $('.basic-modal-select').val(data.brdDivide).prop('selected', true);
                $('.board-input-title').val(data.brdTitle);
                $('#board-input-title').next().addClass('active');
                ckContent.setData(data.brdContent);
                $('.update-btn').css('display','inline-block');
                $('.write-btn').css('display','none');
                $('.update-btn').html('수정');
            }).catch( (error) => {
            $('#basicModal').modal('hide');
                errorAlert('잘못된 접근 입니다.');
        })
    });

    // 게시글 수정 버튼
    $('.update-btn').on('click', (e) => {
        e.preventDefault();
        let currentPath =firstPath();
        let boardInfo = new Map();

        if ( $('.board-input-title').val().trim() == '' || ckContent.getData().trim() == '' ){
            errorAlert('항목을 모두 입력해주세요');
            return false;
        }
        boardInfo.set('brdNo', $('.post-no').val());
        boardInfo.set('brdType', currentPath);
        boardInfo.set('brdDivide', $('.basic-modal-select').val());
        boardInfo.set('brdTitle', $('.board-input-title').val());
        boardInfo.set('brdContent', ckContent.getData());
        let boardJson = mapToJson(boardInfo, false);
        updateBoard(boardJson)
            .then((data) => {
                console.log(data);
                $('#basicModal').modal('hide');
                timerAlert("글수정","글의 정보를 확인중입니다.",1500);
                setTimeout(()=>{successAlert("게시글이 수정되었습니다.");},1500);
                setTimeout(()=>{
                    // 모달 수정내역에 동영상이 있을경우 리로드
                    if ( boardInfo.get('brdContent').includes('oembed') ) location.reload();
                    // 수정내역부분 변경
                    let divide = boardInfo.get('brdDivide').toString();
                    switch (divide){
                        case '1' : divide = '공지'; break;
                        case '2' : divide = '이벤'; break;
                        case '3' : divide = '잡담'; break;
                        case '4' : divide = '정보'; break;
                        case '5' : divide = '요청'; break;
                    }
                    $('.post-divide > span').html(divide);
                    $('.post-title').html(boardInfo.get('brdTitle'));
                    $('.post-body').html(boardInfo.get('brdContent'));
                },2500);
            }).catch((error) => {
            errorAlert('글수정에 실패했습니다');
        });
    });


    // 추천 버튼
    $('.post-like').on('click', function() {
        if ($(this).attr('data-write') == 'true') {
            let currentPath = firstPath();
            let no = $('.post-no').val();
            let path = `/${currentPath}/like/${no}`;
            likeRegister(path)
                .then( (data) => {
                    successAlert(stateCode.get(data.stateCode));
                    let cnt = $('.post-likecnt').html();
                    $('.post-likecnt').html(parseInt(cnt)+1);
                    console.log(cnt);
                }).catch( (error) => {
                errorAlert(stateCode.get(error.responseText));
            });
        } else {
            confirmAlert('로그인이 필요합니다', '로그인화면으로 이동하시겠습니까?', '/?requireLogin=true')
        }
    });



});




// 추천 등록
let likeRegister = (path) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'POST',
            url : path,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(data);
            }
        });
    });
}




/*
     게시판 검색 모듈
 */


// select 선택시
$('.board-select').on('change', function(){
    let selected = $(this).val();
    let pageNum = $('#page-num').val();
    let keyword = $('#page-keyword').val();
    let type = selected;
    if ( pageNum == 1 ) pageNum='';
    if ( type == -1 || type == -2 ) type='';
    if ( keyword == undefined ) keyword='';
    let pathMap = new Map();
    pathMap.set('pageNum',pageNum);
    pathMap.set('type',type);
    pathMap.set('keyword',keyword);
    let path = `${window.location.pathname}${makePath(pathMap)}`;
    location.href=path;
});


// 검색 enter 시
$('.board-searchinput').on('keydown', function(e){
    if ( e.keyCode == 13 ) {
        let keyword = $(this).val();
        let type = $('.board-select').val();
        let pathMap = new Map();
        pathMap.set('keyword',keyword);
        pathMap.set('type',type);
        let path = `${window.location.pathname}${makePath(pathMap)}`;
        location.href=path;
    }
});

// 글제목, 목록 클릭시
$('.board-postlink, .board-listbtn').on('click', function(e){
    e.preventDefault();
    let currentPath = firstPath();
    let pathMap = new Map();
    let nextMove;
    let type;
    if ( $(this).attr('class').includes('board-listbtn') ) {
        type = $('#page-type').val();
        nextMove = 'list';
    } else {
        type = $('.board-select').val();
        let link = $(this).attr('href');
        pathMap.set('no',link);
        nextMove = 'view';
    }
    let pageNum = $('#page-num').val();
    let keyword = $('#page-keyword').val();
    if ( type == -1 || type == -2 || type == undefined ) type='';
    if ( pageNum == 1 ) pageNum='';
    pathMap.set('pageNum',pageNum);
    pathMap.set('type',type);
    pathMap.set('keyword',keyword);
    let path = `/${currentPath}/${nextMove}${makePath(pathMap)}`;
    console.log(path);
    location.href=path;
});

// paremeter 주소줄 생성
let makePath = (pathMap) => {
    let path = '';
    let i =0;
    for ( let str of pathMap ) {
        if ( str[1] != '' ) {
            if ( i>0 ) path += '&';
            path += `${str[0]}=${str[1]}`;
            i++;
        }
    }
    if ( i > 0 ) path = `?${path}`;
    return path;
};