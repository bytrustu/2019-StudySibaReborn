'use strict';

$(document).ready(function () {


    /*
            mainpage Enterkey 적용
     */
    enterPressAction('login-input', 'modal-loginbtn');
    enterPressAction('join-input', 'modal-joinbtn');



    // 992px 이상일 경우 시바랭킹 카운팅
    if (window.innerWidth >= 992) {
        catchElement('.point', '.info-box', 18, true);
    }
    // 회원수 방문자 카운팅
    catchElement('.middle-text', '.middle-box', 3, true);

    // 슬라이더
    let randomSlideCount = $('.study-item').length - 7;
    let randomSlideWidth = rendomNumber(randomSlideCount * 300);
    studySlider(randomSlideWidth);

    // 슬라이드 시작/정지 버튼
    $('.button-slide').on('click', function () {
        if ($(this).attr('class').match('button-stop')) {
            $(this).removeClass('button-stop').addClass("button-start").html('시작');
            $('.button-lt1').removeClass('button-lt1').addClass('button-lt2');
            $('.button-gt1').removeClass('button-gt1').addClass('button-gt2');
            //            $('.button-lt, .button-gt').css('animation','animate2 2s linear infinite');
            clearInterval(moveSlide);
        } else {
            $(this).removeClass('button-start').addClass("button-stop").html('정지');
            let leftValue = $('.study-box').css('left');
            let leftPx = $('.study-box').css('left').indexOf('px');
            leftValue = leftValue.substring(1, leftPx);
            studySlider(leftValue);
            $('.button-lt2').removeClass('button-lt2').addClass('button-lt1');
            $('.button-gt2').removeClass('button-gt2').addClass('button-gt1');
            //            $('.button-lt, .button-gt').css('animation','animate1 2s linear infinite');
        }
    });

    // 슬라이드 < > 버튼
    $('.button-flag').on('click', function () {
        $('.button-slide').removeClass('button-start').addClass('button-stop').html('정지');
        slideFlag = $(this).data('flag').match('lt') ? false : true;
        let leftValue = $('.study-box').css('left');
        let leftPx = $('.study-box').css('left').indexOf('px');
        leftValue = leftValue.substring(1, leftPx);
        clearInterval(moveSlide);
        studySlider(leftValue);
        $('.button-lt2').removeClass('button-lt2').addClass('button-lt1');
        $('.button-gt2').removeClass('button-gt2').addClass('button-gt1');
    });


    // 비밀번호변경 모달창 호출
    $('#recovery-password').on('click', () => {
        ajaxAlert('가입하신 이메일을 입력하세요.', '/member/mail/changepass/', '이메일이 전송되었습니다.', '이메일이 올바르지 않습니다.');
    });

    // 이메일인증을통한비밀번호변경버튼
    $('#modal-changepass').on('click', () => {
        let memberInfo = new Map();
        memberInfo.set('mbrId', $('#authId').val());
        memberInfo.set('mbrPass', $('#changePass').val());
        let memberJson = mapToJson(memberInfo);
        changePassword(memberJson)
            .then((data) => {
                $('#modalChangePassword').modal('hide');
                setTimeout(() => {
                    successAlert(stateCode.get(data));
                }, 300);
            }).catch((error) => {
            errorAlert(stateCode.get(error.responseText));
        }).finally(() => {
            initElement('modal-input');
        });
    });


    // 프로필 사진 변경
    $('.image-target').on('click', () => {
        let changeProfile = `profile-${rendomNumber(18)}.png`;
        $('.logined-profile').attr('src',`/static/image/profile/${changeProfile}`);
        $('.modal-inputprofile').val(changeProfile);
    });


    // 회원정보변경 버튼
    $('.change-btn').on('click', function(){
        let changeType = $(this).attr('data-change');
        let logginedId = $('#loggined-id').html();
        let dataValue = $(this).parents('.modal-body').find('.modal-input').val().trim();
        if ( dataValue === '' ) { errorAlert("입력사항을 확인해주세요."); return false; };
        let url = `/member/${logginedId}/${changeType}/${dataValue}`;
            changeUserInformation(url)
                .then( (data) => {
                    if ( changeType === 'nick' ) $('#loggined-nick').html(dataValue);
                    if ( changeType === 'profile' ) $('.user-image').attr('src', `/static/image/profile/${dataValue}`);
                    $('.modal').modal('hide');
                    successAlert(stateCode.get(data));
                }).catch( (error) => {
                    //console.log(error);
                    errorAlert(stateCode.get(error.responseText));
            });
        initElement('modal-input');
    });



    // Close Ready
});


function swing(){
    let imageTarget = $('.image-target');
    imageTarget.animate({
        top: '-70px',
        left: '194px'
    }, 1000).animate({
        top: '-60px',
        left: '174px'
    }, 1000, swing);
}
