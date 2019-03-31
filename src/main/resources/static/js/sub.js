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
        console.log(boardInfo);
        let boardJson = mapToJson(boardInfo, false);
        console.log(boardJson);

        writeBoard(boardJson, currentPath)
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

        let currentPath = window.location.pathname;
        currentPath = currentPath.substring(1, currentPath.lastIndexOf('/')).toLowerCase();
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
