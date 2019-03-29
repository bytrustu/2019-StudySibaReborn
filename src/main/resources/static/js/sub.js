$(document).ready(function(){

    // 공지사항, 커뮤니티 게시판 반응형 제목 크기 변화
    $(window).resize(function (){
        let widthSize = window.innerWidth;
        switch (window.location.pathname) {
            case '/community/list' :
                if ( widthSize <=  375 ) trimTitleLength('title-text', 20);
                else if (widthSize <= 440 ) trimTitleLength('title-text', 28);
                else if (widthSize <= 760 ) trimTitleLength('title-text', 22);
                break;
            case '/notice/list' :
                if ( widthSize <=  375 ) trimTitleLength('title-text', 20);
                else if (widthSize <= 440 ) trimTitleLength('title-text', 28);
                else if (widthSize <= 760 ) trimTitleLength('title-text', 22);
                break;
        }
    });


    $('.content-writebtn').on('click', () => {
        $('#basicModal').modal('show');
        $('.navbar').css('z-index', 1050);
    });


});
