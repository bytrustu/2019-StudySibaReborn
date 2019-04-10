$(document).ready(function(){

    let chatWindow = $('.chat-window');
    let chatList = $('.chat-list');

    $('.top-closebtn').on('click', function(){
        initClass();
        chatWindow.addClass('flipOutY');
        chatList.addClass('flipOutY');
        setTimeout(()=>{
            chatWindow.addClass('d-none');
            chatList.addClass('d-none');
            },500);
    });

    $('.messenger-btn').on('click', function(){
        initClass();
        chatWindow.addClass('d-none');
        chatList.addClass('d-none');
        chatList.toggleClass('d-none').addClass('flipInY');
    });

    $('.chat-prev').on('click', function(){
        initClass();
        chatWindow.toggleClass('d-none');
        chatList.toggleClass('d-none').addClass('fadeIn');
    });

    $('.top-listbtn').on('click', function(){
        initClass();
        let isChatList = $(this).parent().parent().parent().attr('class').includes('chat-list');
        if ( isChatList ) return false;
        chatList.toggleClass('d-none').addClass('fadeIn');
        chatWindow.toggleClass('d-none');
    });

    $('.messenger-list').on('click', function(){
        initClass();
        chatList.toggleClass('d-none');
        chatWindow.toggleClass('d-none').addClass('fadeIn');
    });


    let initClass = () =>{
        chatWindow.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
        chatList.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
    }




});

