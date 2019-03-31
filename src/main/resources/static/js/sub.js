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




    // 공지사항, 커뮤니티 게시판 반응형 제목 크기 변화
    let widthSize = window.innerWidth;
    contentWidthFix(widthSize);
    $(window).resize(function () {
        contentWidthFix(widthSize);
    });

    // 게시글 등록
    let ckContent = '';
    $('.write-btn').on('click', () => {
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
                        getComment(data.no)
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
                    <div class="comment-content">
                    <img src="/static/image/profile/${commentJson.mbrProfile}">
                    <div class="comment-info">
                    <p>[ ${commentJson.mbrNick} ]</p>   
                    <p>${commentJson.cmtDate}</p>
                    </div>
                    </div>
                    <p class="comment-text">${commentJson.cmtContent}</p>
                    </div>`;
            return commentLayout;
        };





    // 글쓰기 버튼
    $('.content-writebtn').on('click', function () {
        let currentPath = firstPath();
        if ( currentPath == 'notice' ) {
            if ($(this).attr('data-write') == 'true') {
                $('#basicModal').modal('show');
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


// 게시판 화면사이즈별 text크기 조절
let contentWidthFix = (widthSize) => {
    switch (window.location.pathname) {
        case '/community/list' :
            if (widthSize <= 375) trimTitleLength('title-text', 20);
            else if (widthSize <= 440) trimTitleLength('title-text', 28);
            else if (widthSize <= 760) trimTitleLength('title-text', 22);
            break;
        case '/notice/list' :
            if (widthSize <= 375) trimTitleLength('title-text', 20);
            else if (widthSize <= 440) trimTitleLength('title-text', 28);
            else if (widthSize <= 760) trimTitleLength('title-text', 22);
            break;
    }
}


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