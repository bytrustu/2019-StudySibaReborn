$(document).ready(function(){




    $('.content-studybtn').on('click', function(){
        $('#studyModal').modal('show');
    });


    $('.content-studybtn').on('click', function(){
        $('#studyModal').modal('show');
    });


    // $('.file-upload-input').on('change', function(){
    //     readURL(this);
    // });










    $('.ico').on('click', function(){
        let subjectValue = parseInt($('.sujectCnt').html());
        $(this).toggleClass('liked');
        if ( $(this).hasClass('liked') ) {
            if ( subjectValue >= 5 ) {
                errorAlert('5개 이상 지정 할 수 없습니다.');
                $(this).toggleClass('liked');
                return false;
            }
            subjectValue++;
            $(this).prev('.input-subject').val('true');
        } else {
            $(this).prev('.input-subject').val('false');
            subjectValue--;
        }
        $('.sujectCnt').html(subjectValue);
    });





    $('.stm-step1').on('click', function(){
        let map = checkSubject('input-subject');
        console.log(map);
    });

    $('.stm-step2').on('click', function(){
        let lat = $('#stm-lat').val();
        let lng = $('#stm-lng').val();
        let address = $('#pac-input').val();
        let toPer = $('.inputs_toPer').val();
        let fromPer = $('.inputs_fromPer').val();
        console.log(`${lat} : ${lng} : ${address} : ${toPer} : ${fromPer}`);
    });



    // 클래스 value 초기화
    let checkSubject = (className) => {
        let subject = [];
        let elements = document.getElementsByClassName(className);
        for (let element of elements) {
            if ( element.value == 'true' ) {
                subject.push(element.getAttribute('data-subject'));
            }
        }
        console.log(subject);
        return subject;
    }








    var steps = ['.res-step-one','.res-step-two','.res-step-three','.res-step-four'];
    var i = 1;
    $(".res-step-form .res-btn-orange").click(function(){
        var getClass = $(this).attr('data-class');
        $(".res-steps").removeClass('active');
        $(steps[i]).addClass('active');
        i++;
        if(getClass != ".res-form-four"){
            $(getClass).animate({
                left: '-150%'
            }, 500, function(){
                $(getClass).css('left', '150%');
            });
            $(getClass).next().animate({
                left: '50%'
            }, 500, function(){
                $(this).css('display','block');
            });
        }
    });

    /* step back */
    $(".res-step-form .res-btn-gray").click(function(){
        var getClass = $(this).attr('data-class');
        $(".res-steps").removeClass('active');
        i--;
        $(steps[i-1]).addClass('active');
        $(getClass).prev().css('left','-150%')
        $(getClass).animate({
            left: '150%'
        }, 500);
        $(getClass).prev().animate({
            left: '50%'
        }, 500)
    });

    /* click from top bar steps */
    $('.res-step-one').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 0;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-one').css('left','-150%');
            $('.res-form-two, .res-form-three, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-one').animate({
                left: '50%'
            }, 500);
        }
    });

    $('.res-step-two').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 1;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-two').css('left','-150%');
            $('.res-form-one, .res-form-three, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-two').animate({
                left: '50%'
            }, 500);
        }
    });

    $('.res-step-three').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 2;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-three').css('left','-150%');
            $('.res-form-one, .res-form-two, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-three').animate({
                left: '50%'
            }, 500);
        }
    });

    $('.res-step-four').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 3;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-four').css('left','-150%');
            $('.res-form-one, .res-form-two, .res-form-three').animate({
                left: '150%'
            }, 500);
            $('.res-form-four').animate({
                left: '50%'
            }, 500);
        }
    });

    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });
    $('.datepickter').datepicker();










});



















