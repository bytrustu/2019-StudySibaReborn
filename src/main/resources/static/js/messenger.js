$(document).ready(function(){
    $('.top-closebtn').on('click', function(){
        $('.chat-window, .chat-list').removeClass('flipInY').removeClass('fadeIn').addClass('flipOutY');
    });


    $('.messenger-btn').on('click', function(){
        $('.chat-window').removeClass('d-none').removeClass('flipOutY').addClass('flipInY');
    });

    $('.top-listbtn, .chat-prev').on('click', function(){
        $('.chat-window').addClass('d-none');
        $('.chat-list').removeClass('d-none').removeClass('flipOutY').addClass('fadeIn');
    });




});

