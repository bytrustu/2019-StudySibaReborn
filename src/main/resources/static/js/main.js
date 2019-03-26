'use strict';

$(document).ready(function () {
    // 회원로그인/가입 모달
    $('.modal-login').on('click', () => {
        initElement('modal-input');
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
        setTimeout(() => {
            timerAlert('로그인', '회원정보를 확인중입니다.', 1000);
        }, 200);

        memberNormalLogin(memberJson)
            .then((data) => {
                setTimeout(() => {
                    if (data === 'LOGIN_STATE_SUCCESS') {
                        location.href = '/';
                    } else if (data === 'ID_STATE_WAITAPPROVAL') {
                        $('#sendmailid').val($('#input-loginid').val());
                        $('#modalSendMail').modal('show');
                    }
                }, 1400);
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
        memberInfo.set('mbrProfile', 'studysiba-default.png');

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

    // 구글 소셜로그인
    $('.social-google').on('click', function() {
        location.href=$(this).attr('data-url');
    });

    // 카카오 소셜로그인
    $('.social-kakao').on('click', () => {
        $('.modal ').modal('hide');
        kakaoRequest();
    });

    // Close Ready
});


let kakaoRequest = () => {
    Kakao.Auth.login({
        success: (authObj) => {
            console.log('success');
            Kakao.API.request({
                url: '/v2/user/me',
                success: (data) => {
                    let memberInfo = new Map();
                    memberInfo.set('mbrId', data.id);
                    if (data.properties.nickname == null || data.properties.nickname == undefined || data.properties.nickname == '') {
                        let studyNumber = rendomNumber(99999);
                        memberInfo.set('mbrNick', `스터디${studyNumber}`);
                    } else {
                        memberInfo.set('mbrNick', data.properties.nickname);
                    }
                    let memberJson = mapToJson(memberInfo);
                    kakaoSigin(memberJson)
                        .then((data) => {
                            timerAlert("로그인","회원정보를 확인중입니다.", 2000);
                            setTimeout( () => {
                                location.href = '/';
                            },2200);
                        }).catch((error) => {
                        timerAlert("로그인","회원정보를 확인중입니다.", 2000);
                        setTimeout( () => {
                            location.href = '/';
                        },2200);
                    });
                },
                fail: (error) => {
                    errorAlert("로그인 실패했습니다.");
                }
            });
        },
        fail: (error) => {
            errorAlert("로그인 실패했습니다.");
        }
    });
}

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
                console.log(data);
                resolve(data);
            },
            error: (error) => {
                console.log(error);
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
                console.log(`success : ${data}`);
                resolve(data);
            }, error: (error) => {
                console.log(`success : ${error.responseText}`);
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


// 회원인증 비밀번호 변경
let changePassword = (memberJson) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'PUT',
            url: '/member/auth/mailpassword',
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


// 클래스 value 초기화
let initElement = (className) => {
    let elements = document.getElementsByClassName(className);
    for (let element of elements) {
        element.value = '';
    }
}


// Map을 Json 으로 변환
const mapToJson = (map) => {
    return JSON.stringify(mapToObject(map));
}
const mapToObject = (map) => {
    let obj = Object.create(null);
    for (let [key, value] of map) {
        obj[key] = value;
    }
    return obj;
}


// SwertAlert Error
const errorAlert = (text) => {
    Swal.fire({
        position: 'top-end',
        type: 'error',
        title: text,
        showConfirmButton: false,
        allowOutsideClick: false,
        timer: 2500,
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
        timer: 2500
    });
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


// KAKAO API
Kakao.init('672b34ad5f77dd65240951209b6cbd32');