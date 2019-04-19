$(document).ready(function () {

    // 모바일메뉴바
    $(document).on('click','#toggleBtn', function(){
        let mobileNav = $('#mobile');
        let target = $(this);
        if ( !mobileNav.hasClass('show') ) {
            let parentNavHeight = $('.navbar').css('height');
            mobileNav.css('top',parentNavHeight);
            target.find('i').removeClass('fa').removeClass('fa-bars');
            target.find('i').addClass('fas').addClass('fa-times-circle');
        } else {
            target.find('i').removeClass('fas').removeClass('fa-times-circle');
            target.find('i').addClass('fa').addClass('fa-bars');
        }
            mobileNav.toggleClass('show');
    });


    // 관리자페이지
    $(document).on('click','.admin-btn', ()=>{
        location.href='/admin/main';
    });

    if ( location.pathname == '/' ){
        $('.modal, .swal2-modal').css('left','-8px');
    } else {
        $('.modal, .swal2-modal').css('left','0');
    }

    // 모달버튼열기 [ 로그인,회원가입 / 닉네임변경 / 패스워드변경 / 프로필변경 ]
    $('.modal-user').on('click', function () {
        initElement('modal-input');
        let user = $(this).attr('data-user');
        switch (user) {
            case 'login' :
                $('#modalLRForm').modal('show');
                break;
            case 'nick' :
                $('#modalChangeNick').modal('show');
                break;
            case 'password' :
                $('#modalChangePass').modal('show');
                break;
            case 'profile' :
                $('#modalChangeProfile').modal('show');
                swing();
                break;
            case 'logout' :
                let logginedId = $(this).attr('data-id');
                let currentUrl = $(location).attr('pathname');
                let searchUrl = $(location).attr('search');
                let moveUrl = `/member/logout/${logginedId}?currentUrl=${currentUrl}${searchUrl}`;
                location.href = moveUrl;
                break;
        }
    });

    // 접속시간 갱신
    connectUpdate();
    setInterval(()=>{connectUpdate();},170000);


    // End ready
});




// 클래스 value 초기화
let initElement = (className) => {
    let elements = document.getElementsByClassName(className);
    for (let element of elements) {
        element.value = '';
    }
}

// 엔터키 적용
let enterPressAction = (inputName, targetName) => {
    $(`.${inputName}`).keyup( function(e) {
        if ( e.keyCode == 13 ) {
            $(`.${targetName}`).click();
        }
    });
}


// Map을 Json 으로 변환
const mapToJson = (map,isCheck) => {
    return JSON.stringify(mapToObject(map,isCheck));
}
const mapToObject = (map,isCheck) => {
    if ( !isCheck ) isCheck = false;
    let obj = Object.create(null);
    for (let [key, value] of map) {
        if ( isCheck ) {
            typeof value == 'string' ? obj[key] = value.toLowerCase() : obj[key] = value;
        } else {
            obj[key] = value;
        }
    }
    return obj;
}


// paremeter 주소줄 생성
let makePath = (pathMap) => {
    let path = '';
    let i =0;
    for ( let str of pathMap ) {
        if ( str[1] != '' ) {
            if ( i>0 ) path += '&';
            path += `${str[0]}=${str[1]}`;
            i++;
        }
    }
    if ( i > 0 ) path = `?${path}`;
    return path;
};


// 제목 자르기
let trimTitleLength = (className, length) => {
    let elements = document.getElementsByClassName(className);
    for ( let element of elements ) {
        if ( calByte.getByteLength(element.innerHTML) >= length ) {
            element.innerHTML = calByte.cuteByteLength(element.innerHTML, length-2) + '...';
        }
    }
}

// 해당 요소 html 태그 반환
let thisElement = (target) => {
    return target.clone().wrapAll("<div/>").parent().html();
}



// 첫번째 루트 경로 반환
let firstPath = () => {
    let currentPath = window.location.pathname;
    currentPath = currentPath.substring(1, currentPath.lastIndexOf('/'));
    return currentPath;
}

// 첫번째 루트 경로 반환
let secondPath = () => {
    let currentPath = window.location.pathname;
    currentPath = currentPath.substring(1, currentPath.lastIndexOf('/'));
    currentPath = currentPath.substring(currentPath.indexOf('/')+1);
    return currentPath;
}


// 페이지 글번호 값
let contentNo = () => {
    let path = location.search;
    path = path.substring(path.indexOf('=')+1);
    if ( path.includes('&') ) {
        path = path.substring(0,path.indexOf('&'));
    }
    return path;
}

// url + parameter
let currentUrl = () =>{
    return `${location.pathname}${location.search}`;
}



// 글자 byte 계산, 자르기
let calByte = {
    getByteLength : function(str) {
        if ( str == null || str.length == 0 ) return 0;
        let size = 0;
        for ( let i = 0; i < str.length; i++ ) size += this.charByteSize(str.charAt(i));
        return size;
    },
    cuteByteLength : function(str, length) {
        if ( str == null || str.length == 0 ) return 0;
        let size = 0;
        let index = str.length;
        for ( let i=0; i<str.length; i++ ) {
            size += this.charByteSize(str.charAt(i));
            if ( size == length ) {
                index = i+1;
                break;
            } else if ( size > length ) {
                index = i;
                break;
            }
        }
        return str.substring(0, index);
    },
    charByteSize : function(ch){
        if ( ch == null || ch.length == 0 ) return 0;
        let charCode = ch.charCodeAt(0);
        return charCode > 128 ? 2 : 1;
    }
}


// 랜덤숫자 생성
let rendomNumber = (n) => {
    let result = Math.floor(Math.random() * n) + 1;
    return result;
}




// 날짜포맷 YYYY-MM-DD 변환
let formatDate = (date) => {
    let pad = (num) => {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate());
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
}




// 파일 업로드
let fileUpload = (formData, menu) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type: 'POST',
            url : `/upload/${menu}`,
            data : formData,
            dataType : 'json',
            processData : false,
            contentType : false,
            success : ( data ) => {
                resolve = data;
            },
            error : (error) => {
                    reject = error;
            }
        });
    });
}


// 접속로그 갱신
let connectUpdate = () =>{
    let connectId = $('#data-id').val();
    if ( connectId == '' ) return false;
    $.ajax({
        type : 'PUT',
        url : `/member/connect/${connectId}`,
        success : (data) => {
            },
        error : (error) => {
        }
    });
}




// SwertAlert Error
const errorAlert = (text) => {
    Swal.fire({
        type: 'error',
        title: text,
        showConfirmButton: false,
        allowOutsideClick: false,
        timer: 1500,
    });
}

// SweetAlert Success
const successAlert = (text) => {
    Swal.fire({
        type: 'success',
        title: text,
        showConfirmButton: false,
        allowOutsideClick: false,
        timer: 2000
    });
}

const confirmAlert = (title, text, path) => {
    Swal.fire({
        title: title,
        text: text,
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#fbc02d',
        cancelButtonColor: '#e4b67c ',
        confirmButtonText: '네'
    }).then((result) => {
        if (result.value) {
            if (path){
                location.href = path;
            }else {
                Swal.fire(
                    '성공',
                    '성공했습니다.',
                    'success'
                )
            }
        }
    })
}

// SweetAlert Timer
const timerAlert = (title, text, time) => {
    let timerInterval
    Swal.fire({
        title: title,
        html: text,
        timer: time,
        allowOutsideClick: false,
        onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
            }, 100)
        },
        onClose: () => {
            clearInterval(timerInterval)
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
        }
    })
}

// SweetAlert Ajax
const ajaxAlert = (title, url, successText, errorText) => {
    Swal.fire({
        title: title,
        input: 'text',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonText: '취소',
        confirmButtonText: '발송',
        showLoaderOnConfirm: true,
        preConfirm: (data) => {
            return fetch(`${url}${data}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            })
                .then(response => {
                    if (response.status == 500) {
                        throw new Error(response.statusText);
                    }
                    return response;
                })
                .catch(error => {
                    Swal.showValidationMessage(
                        `오류 발생: ${errorText}.`
                    )
                })
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((data) => {
        if (data.dismiss == 'cancel' || data.dismiss == 'backdrop') {
            return false;
        }
        Swal.closeModal();
        setTimeout(() => {
            successAlert(successText);
        }, 300);
    })
}





