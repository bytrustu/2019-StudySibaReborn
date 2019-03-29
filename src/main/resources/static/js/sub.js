$(document).ready(function () {

    // 공지사항, 커뮤니티 게시판 반응형 제목 크기 변화
    let widthSize = window.innerWidth;
    contentWidthFix(widthSize);
    $(window).resize(function () {
        contentWidthFix(widthSize);
    });

    $('.write-btn').on('click', () => {
        $('#editorForm').submit();
    });


    ClassicEditor
        .create( document.querySelector( '#editor' ),{
            language: 'ko',
            toolbar : {
                viewportTopOffset: 30
            },
            ckfinder: {
                uploadUrl: '/upload/community'
            }
        } )
        .catch( error => {
            console.error( error );
        } );


    $('.content-writebtn').on('click', () => {
        $('#basicModal').modal('show');
        $('.navbar').css('z-index', 1050);
        $('body.modal-open').css('overflow','hidden');
        $('body').css('overflow','hidden');
    });

    $('.studysiba-cancel').on('click', () => {
        $('body').css('overflow','auto');
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
