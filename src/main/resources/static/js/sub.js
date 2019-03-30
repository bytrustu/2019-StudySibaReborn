$(document).ready(function () {

    // 공지사항, 커뮤니티 게시판 반응형 제목 크기 변화
    let widthSize = window.innerWidth;
    contentWidthFix(widthSize);
    $(window).resize(function () {
        contentWidthFix(widthSize);
    });

    // 게시글 등록
    let ckContent = '';
    $('.write-btn').on('click', () => {
        let currentPath = window.location.pathname;
        currentPath = currentPath.substring(1, currentPath.lastIndexOf('/'));
        let boardInfo = new Map();
        boardInfo.set('brdType', currentPath);
        boardInfo.set('brdDivide', $('.basic-modal-select').val());
        boardInfo.set('brdTitle', $('.board-input-title').val());
        boardInfo.set('brdContent', ckContent.getData());
        let boardJson = mapToJson(boardInfo, false);

        writeBoard(boardJson, currentPath)
            .then((data) => {
                console.log(`SUCCESS : ${data}`);
            }).catch((error) => {
            console.log(`ERROR : ${error}`);
        });
    });


    // CKEDITOR5 설정
    ClassicEditor
        .create(document.querySelector('#editor'), {
            language: 'ko',
            toolbar: {
                viewportTopOffset: 30
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


    // 글쓰기 버튼
    $('.content-writebtn').on('click', function () {
        // 로그인 여부 확인
        if ($(this).attr('data-write') == 'true') {
            $('#basicModal').modal('show');
            $('.navbar').css('z-index', 1050);
            $('body.modal-open').css('overflow', 'hidden');
            $('body').css('overflow', 'hidden');
        } else {
            confirmAlert('로그인이 필요합니다', '로그인화면으로 이동하시겠습니까?', '/?requireLogin=true')
        }
    });

    $('.studysiba-cancel').on('click', () => {
        $('body').css('overflow', 'auto');
    })


});


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
