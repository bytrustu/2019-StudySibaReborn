'use strict';


$(document).ready(function () {

    // 회원로그인/가입 모달
    $('.header-loginbutton').on('click', () => {
        $('#modalLRForm').modal('show');
    });

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


});

// 랜덤숫자 생성
let rendomNumber = (n) => {
    let result = Math.floor(Math.random() * n) + 1;
    return result;
}

let moveSlide;
let slideFlag = true;
let studySlider = (left) => {
    let count;
    left === undefined ? count = 1 : count = Math.floor(left / 300);
    let studyItem = $('.study-item');
    let slider = $('.study-box');
    let sliderWidth = studyItem.length * 300;
    slider.css('width', sliderWidth);
    slider.css('left', count * -300);
    let maxIndex = studyItem.length - 7;
    moveSlide = setInterval(() => {
        let left;
        slideFlag ? count++ : count--;
        if (count <= 1) {
            slideFlag = true;
        } else if (count >= maxIndex) {
            slideFlag = false;
        }
        left = count * -300;
        slider.css('left', left);
    }, 2500);

}

// scoll 값이 element 접근시 이벤트 발생 ( 증가대상, 뷰표현대상, 메뉴바없을시생략 )
let catchElement = (element, target, increaseNum, isNav) => {
    if (increaseNum === undefined) increaseNum = 33;
    // id , class 구분
    let elementType = element.substring(0, 1);
    // element 요소를 담는다
    element = $(`${element}`);
    target = $(target);
    // 브라우저에서 element 상단 위치 도달할 좌표 [ + 50 은 보정값 ]
    let rankTopSize = target.offset().top - window.outerHeight + 50;
    // 브라우저에서 element 하단 위치 도달할 좌표
    let rankBottomSize = 0;
    isNav === 'undefined' ?
        rankBottomSize = target.offset().top + target.outerHeight() :
        rankBottomSize = target.offset().top + target.outerHeight() - $('.navbar').outerHeight();
    // 현재 스크롤 좌표값
    let scrollValue = $(document).scrollTop();

    // 현재 브라우저 화면에 element가 있을때 바로 처리
    if (scrollValue >= rankTopSize && scrollValue <= rankBottomSize) {

        let index = 0;
        for (let names of element) {
            elementType == '#' ?
                rankPlay(`${elementType}${element[index].id}`, increaseNum) :
                rankPlay(`${elementType}${element[index].className}:nth(${index})`, increaseNum);
            index++;
        }
    } else {
        // 현재 브라우저 화면에 element 요소가 없을때 scroll 이벤트 처리
        $(window).scroll((e) => {
            let currentValue = $(document).scrollTop();
            if (currentValue >= rankTopSize && currentValue <= rankBottomSize) {
                let index = 0;
                for (let names of element) {
                    elementType == '#' ?
                        rankPlay(`${elementType}${element[index].id}`, increaseNum) :
                        rankPlay(`${elementType}${element[index].className}:nth(${index})`, increaseNum);
                    index++;
                }
                // element 요소가 보여졌을때 scroll 이벤트 종료
                $(window).off('scroll');
            }
        });
    }
};

// element 숫자 카운팅
let rankPlay = (element, plus) => {
    let point = $(element).html();
    element = $(element);
    // 초기 숫자 지정
    element.html('0');
    let count = 0;
    let play = setInterval(() => {
        count += plus;
        element.html(count);
        // 점수가 오버될시 보정
        if (point <= count) {
            if (point < count) count = point;
            element.html(count);
            clearInterval(play);
        }
    }, 10);
};
