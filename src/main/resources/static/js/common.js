$(document).ready(function () {

    // 소셜로그인
    $('.social-login-icon').on('click', function () {
        let socialName = $(this).attr('data-name');
        switch (socialName) {
            case 'social-kakao' :
                $('.modal ').modal('hide');
                kakaoRequest();
                break;
            case 'social-facebook' :
                FB.login(function (response) {
                    checkFacebookLoginStatus(response);
                });
                break;
            default :
                location.href = $(this).attr('data-url');
        }
    });


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
                let moveUrl = `/member/logout/${logginedId}?currentUrl=${currentUrl}`;
                location.href = moveUrl;
                break;
        }
    });

// 모달 로그인 버튼
    $('.modal-loginbtn').on('click', function () {
        let memberInfo = new Map();
        memberInfo.set('mbrId', $('#input-loginid').val());
        memberInfo.set('mbrPass', $('#input-loginpass').val());
        let memberJson = mapToJson(memberInfo);
        for (let item of memberInfo) {
            if (item[1] === '') {
                errorAlert('정보를 모두 입력해주세요');
                return false;
            }
        }
        $('#modalLRForm').modal('hide');
        memberNormalLogin(memberJson)
            .then((data) => {
                if (data === 'LOGIN_STATE_SUCCESS') {
                    location.href = '/';
                } else if (data === 'ID_STATE_WAITAPPROVAL') {
                    timerAlert('로그인', '회원정보를 확인중입니다.', 2000);
                    setTimeout(() => {
                        $('#sendmailid').val($('#input-loginid').val());
                        $('#modalSendMail').modal('show');
                    }, 2300)
                }
            }).catch((error) => {
            error = error.responseText;
            setTimeout(() => {
                errorAlert(stateCode.get(error));
            }, 1400);
        });
    });


// 회원가입
    $('.modal-joinbtn').on('click', function () {
        const memberInfo = new Map();
        memberInfo.set('mbrId', $('#input-joinid').val());
        memberInfo.set('mbrPass', $('#input-joinpass').val());
        memberInfo.set('mbrNick', $('#input-joinnick').val());
        memberInfo.set('mbrEmail', $('#input-joinemail').val());
        memberInfo.set('mbrProfile', 'profile-1.png');

        for (let item of memberInfo) {
            if (item[1] === '') {
                errorAlert('정보를 모두 입력해주세요');
                return false;
            }
        }
        let memberJson = mapToJson(memberInfo);
        memberJoin(memberJson)
            .then((data) => {
                if (data == 'MEMBER_STATE_SUCCESS') {
                    $('#modalLRForm').modal('hide');
                    setTimeout(() => {
                        timerAlert('초대장전송', '메일로 초대장을 전송중입니다!', 100000);
                    }, 700);
                    sendMail(memberInfo).then((data) => {
                        Swal.closeModal();
                        setTimeout(() => {
                            successAlert(stateCode.get(data));
                        }, 700);
                    }).catch((error) => {
                        errorAlert(stateCode.get(error.responseText));
                    });
                }
            }).catch((error) => {
            errorAlert(stateCode.get(error.responseText));
        });
    });

});


// 미승인 회원정보 삭제
$('#modal-deleteinfo').on('click', () => {
    $('#modalSendMail').modal('hide');
    let memberJson = {
        'mbrId': $('#sendmailid').val()
    }
    memberJson = JSON.stringify(memberJson);
    setTimeout(() => {
        timerAlert('정보삭제', '회원님의 정보를 삭제중입니다.', 1500);
    }, 200);
    deleteInfomation(memberJson)
        .then((data) => {
            setTimeout(() => {
                successAlert(stateCode.get(data));
            }, 1900);
        }).catch((error) => {
        setTimeout(() => {
            errorAlert(stateCode.get(error.responseText));
        }, 1900);
    });
})



// 초대장 재발송
$('#modal-resendmail').on('click', () => {
    $('#modalSendMail').modal('hide');
    let memberInfo = new Map();
    memberInfo.set('mbrId', $('#sendmailid').val());
    setTimeout(() => {
        timerAlert('초대장 재발송', '초대장을 전송중입니다!', 100000);
    }, 300);
    sendMail(memberInfo)
        .then((data) => {
            Swal.closeModal();
            setTimeout(() => {
                successAlert(stateCode.get(data));
            }, 500);
        }).catch((error) => {
        liar(res => {
            setTimeout(() => {
                Swal.closeModal();
                res.next();
            }, 500);
        }).next(res => {
            setTimeout(() => {
                errorAlert(stateCode.get(error.responseText));
                res.next();
            }, 300);
        });
    });
});

// 카카오 연동
let kakaoRequest = () => {
    Kakao.Auth.login({
        success: (authObj) => {
            timerAlert("정보확인", "정보를 확인중입니다.", 2000);
            setTimeout(() => {
                Kakao.API.request({
                    url: '/v2/user/me',
                    success: (data) => {
                        let memberInfo = new Map();
                        memberInfo.set('mbrId', data.id);
                        memberInfo.set('mbrNick', data.properties.nickname);
                        let memberJson = mapToJson(memberInfo);
                        kakaoSigin(memberJson)
                            .finally(() => {
                                location.href = '/';
                            });
                    },
                    fail: (error) => {
                        errorAlert("로그인 실패했습니다.");
                    }
                });
            }, 2300);
        },
        fail: (error) => {
            errorAlert("로그인 실패했습니다.");
        }
    });
}


// 페이스북 연동
var checkFacebookLoginStatus = (response) => {
    if (response.status === 'connected') {
        timerAlert("정보확인", "정보를 확인중입니다.", 2000);
        setTimeout(() => {
            FB.api('/me', {
                fields: 'name,email'
            }, function (response) {
                let memberInfo = new Map();
                memberInfo.set('mbrId', response.id);
                memberInfo.set('mbrNick', response.name);
                let memberJson = mapToJson(memberInfo);
                facebookSignIn(memberJson)
                    .finally(() => {
                        location.href = '/';
                    })
            });
        });
    } else {
        errorAlert("로그인 실패 했습니다.")
    }

}


// 공통상태코드
const stateCode = new Map();
stateCode.set('MEMBER_STATE_SUCCESS', '회원정보가 등록되었습니다.');
stateCode.set('ID_STATE_EMPTY', '아이디를 입력해주세요..');
stateCode.set('PASS_STATE_EMPTY', '비밀번호를 입력해주세요.');
stateCode.set('NICK_STATE_EMPTY', '닉네임이 입력해주세요.');
stateCode.set('EMAIL_STATE_EMPTY', '이메일을 입력해주세요.');
stateCode.set('PROFILE_STATE_EMPTY', '프로필사진을 설정해주세요.');

stateCode.set('ID_STATE_USED', '이미 사용중인 아이디 입니다.');
stateCode.set('EMAIL_STATE_USED', '이미 사용중인 이메일 입니다.');
stateCode.set('NICK_STATE_USED', '이미 사용중인 닉네임 입니다.');

stateCode.set('ID_STATE_ERROR', '부적절한 아이디 입니다.');
stateCode.set('PASS_STATE_ERROR', '부적절한 비밀번호 입니다.');
stateCode.set('NICK_STATE_ERROR', '부적절한 닉네임 입니다.');
stateCode.set('EMAIL_STATE_ERROR', '부적절한 이메일 입니다.');
stateCode.set('PROFILE_STATE_ERROR', '부적절한 프로필사진 입니다.');

stateCode.set('INVITE_STATE_SUCCESS', '초대장이 발송되었습니다.');
stateCode.set('INVITE_STATE_ERROR', '초대장 발송을 실패했습니다.');
stateCode.set('AUTH_STATE_SUCCESS', '초대장 인증에 성공했습니다.');
stateCode.set('AUTH_STATE_ERROR', '초대장 인증에 실패했습니다.');
stateCode.set('ID_STATE_WAITAPPROVAL', '이메일 승인대기 아이디 입니다.');

stateCode.set('LOGIN_STATE_SUCCESS', '로그인 되었습니다.');
stateCode.set('LOGIN_STATE_ERROR', '아이디 혹은 패스워드가 다릅니다.');

stateCode.set('INFODEL_STATE_SUCCESS', '회원님의 정보를 삭제 했습니다.');
stateCode.set('INFODEL_STATE_ERROR', '회원님의 정보 삭제에 실패했습니다.');

stateCode.set('PASSMAIL_STATE_SUCCESS', '메일을 발송 했습니다.');
stateCode.set('PASSMAIL_STATE_ERROR', '이메일이 올바르지 않습니다.');
stateCode.set('PASSAUTH_STATE_SUCCESS', '메일 인증에 성공 했습니다.');
stateCode.set('PASSAUTH_STATE_ERROR', '메일 인증에 실패 했습니다.');

stateCode.set('PASS_CHANGE_SUCCESS', '비밀번호가 변경되었습니다..');
stateCode.set('PASS_CHANGE_ERROR', '비밀번호가 올바르지 않습니다.');

stateCode.set("SOCIAL_LOGIN_SUCCESS", "로그인 되었습니다.");
stateCode.set("SOCIAL_JOIN_SUCCESS", "회원가입 되었습니다.");
stateCode.set("SOCIAL_JOIN_ERROR", "회원가입 실패했습니다.");

stateCode.set("POINT_CREATE_SUCCESS", "포인트가 생성되었습니다.");
stateCode.set("POINT_CREATE_ERROR", "포인트 생성에 실패했습니다..");
stateCode.set("POINT_UPDATE_SUCCESS", "포인트가 수정되었습니다.");
stateCode.set("POINT_UPDATE_ERROR", "포인트 수정에 실패했습니다..");

stateCode.set("INFORMATION_CHANGE_SUCCESS", "회원정보를 변경 했습니다.");
stateCode.set("INFORMATION_CHANGE_ERROR", "정보변경에 실패 했습니다.");
stateCode.set("SOCIAL_PASSWORD_ERROR", "소셜회원은 변경 할수 없습니다.");

stateCode.set("LOGOUT_STATE_SUCCESS", "로그아웃 되었습니다.");
stateCode.set("LOGOUT_STATE_ERROR", "잘못된 접근입니다.");

stateCode.set("BOARD_WRITE_SUCCESS","게시글이 등록되었습니다.");
stateCode.set("BOARD_WRITE_ERROR","게시글 등록에 실패했습니다.");
stateCode.set("BOARD_UPDATE_SUCCESS","게시글이 수정되었습니다.");
stateCode.set("BOARD_UPDATE_ERROR","게시글 수정에 실패했습니다.");
stateCode.set("BOARD_DELETE_SUCCESS","게시글이 삭제되었습니다.");
stateCode.set("BOARD_DELETE_ERROR","게시글 삭제에 실패했습니다.");

stateCode.set("LIKE_STATE_SUCCESS", "추천 등록되었습니다.");
stateCode.set("LIKE_STATE_ERROR", "추천 실패했습니다.");
stateCode.set("LIKE_STATE_ALREADY", "이미 추천 하였습니다.");

stateCode.set("COMMENT_WRITE_SUCCESS","댓글이 등록되었습니다.");
stateCode.set("COMMENT_WRITE_ERROR","댓글 등록에 실패했습니다.");
stateCode.set("COMMENT_UPDATE_SUCCESS","댓글이 수정되었습니다.");
stateCode.set("COMMENT_UPDATE_ERROR","댓글 수정에 실패했습니다.");
stateCode.set("COMMENT_DELETE_SUCCESS","댓글이 삭제되었습니다.");
stateCode.set("COMMENT_DELETE_ERROR","댓글 삭제에 실패했습니다.");


// KAKAO API
Kakao.init('672b34ad5f77dd65240951209b6cbd32');


// 클래스 value 초기화
let initElement = (className) => {
    let elements = document.getElementsByClassName(className);
    for (let element of elements) {
        element.value = '';
    }
}

let enterPressAction = (inputName, targetName) => {
    $(`.${inputName}`).keyup( function(e) {
        if ( e.keyCode == 13 )
            $(`.${targetName}`).click();
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


let trimTitleLength = (className, length) => {
    let elements = document.getElementsByClassName(className);
    for ( let element of elements ) {
        if ( calByte.getByteLength(element.innerHTML) >= length ) {
            element.innerHTML = calByte.cuteByteLength(element.innerHTML, length-2) + '...';
        }
    }
}


// 첫번째 루트 경로 반환
let firstPath = () => {
    let currentPath = window.location.pathname;
    currentPath = currentPath.substring(1, currentPath.lastIndexOf('/'));
    return currentPath;
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
}


// 카카오 소셜 로그인
let kakaoSigin = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'POST',
            url: 'member/social/kakao',
            contentType: 'application/json; charset=utf-8',
            data: memberJson,
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}


// 페이스북 소셜로그인
let facebookSignIn = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'POST',
            url: '/member/social/facebook',
            contentType: 'application/json; charset=utf-8',
            data: memberJson,
            success: (data) => {
                resolve(data);
            }, error: (error) => {
                reject(error);
            }
        });
    });
}


// 회원로그인 정보 입력
let memberNormalLogin = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'POST',
            url: '/member/login',
            data: memberJson,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            }, error: (error) => {
                reject(error);
            }
        });
    });
}


// 미인증 회원정보 삭제
let deleteInfomation = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'POST',
            url: '/member/deleteinfo',
            data: memberJson,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}


// 초대장 전송
let sendMail = (memberInfo) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'GET',
            url: `/member/mail/invite/${memberInfo.get('mbrId')}`,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}


// 초대장 재전송
let reSendmail = (email) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'GET',
            url: `/member/mail/resend/${email}`,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            }, error: (error) => {
                reject(error);
            }
        });
    });
}


// 회원가입 정보 입력
let memberJoin = (memberJson) => {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'POST',
            url: '/member/register',
            data: memberJson,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}

// 유저정보 변경
let changeUserInformation = (url) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'PUT',
            url: url,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}

// 회원인증 비밀번호 변경
let changePassword = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'PUT',
            url: '/mail/changepass/mailpassword',
            data: memberJson,
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                resolve(data);
            },
            error: (error) => {
                reject(error);
            }
        });
    });
}

// 게시판 게시글 등록
let writeBoard = (boardJson) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'POST',
            url : `/board/write`,
            data : boardJson,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}

// 게시판 댓글 등록
let writeComment = (boardJson) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'POST',
            url : `/comment/write`,
            data : boardJson,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}

// 게시판 게시글 삭제
let deleteBoard = (boardJson) => {
    return new Promise ( (resolve, reject) => {
        $.ajax({
            type : 'DELETE',
            url : `/board/delete`,
            data : boardJson,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}

// 게시판 업데이트
let updateBoard = (boardJson) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'PUT',
            url : '/board/update',
            data : boardJson,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}



// 댓글 게시글 조회
let getBoard = (type,no) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/${type}/get/${no}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}


// SwertAlert Error
const errorAlert = (text) => {
    Swal.fire({
        position: 'top-end',
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
        position: 'top-end',
        type: 'success',
        title: text,
        showConfirmButton: false,
        allowOutsideClick: false,
        timer: 1500
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

