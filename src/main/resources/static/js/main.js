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

    // 슬라이더 클릭시 스터디 글로 이동
    $(document).on('click','.study-item', function(){
        let no = $(this).attr('data-no');
        location.href = `/study/view?no=${no}`;
    });



    // 슬라이드 조정
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
        let maxIndex = studyItem.length - 6;
        moveSlide = setInterval(() => {
            let left;
            slideFlag ? count++ : count--;
            if (count <= 0) {
                slideFlag = true;
            } else if (count >= maxIndex) {
                slideFlag = false;
            }
            left = count * -300;
            slider.css('left', left);
        }, 2500);
    }

    // 슬라이드 초기 랜덤 설정
    let randomSlideCount = $('.study-item').length - 7;
    let randomSlideWidth = rendomNumber(randomSlideCount * 300)+20;
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

    // 슬라이드 좌,우 버튼
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
            $('#changePass').val('');
        });
    });


    // 회원인증 비밀번호 변경
    let changePassword = (memberJson) => {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: 'PUT',
                url: '/member/mail/changepass/mailpassword',
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
                    errorAlert(stateCode.get(error.responseText));
            });
        initElement('modal-input');
    });

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




    // 소셜 로그인
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



// KAKAO API
Kakao.init('e81d959326670c5b78e4a63cbfc361f1');


