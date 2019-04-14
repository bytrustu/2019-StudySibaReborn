// 전체채팅 Client
let publicClient;
// 개인채팅 Client
let privateClient;
// 접속아이디
let connectId = $('#data-id').val();
// 채팅 연결 대상
let connectFromId='';

// 메신저 리스트창
let chatList = $('.chat-list');
// 메신저 리스트 전체/유저리스트 창
let messengerList = $('.messenger-chatlist');
// 메신저 리스트 채팅목록 제목
let messengerListTitle = $('.messenger-chattitle');


// 메신저 채팅창
let chatWindow = $('.chat-window');
// 채팅창 상단 제목
let chatTitle = $('.chat-window .top-title');
// 채팅 메세지 표시창
let messageContainer = $('.chat-message');
// 메세지 입력칸
let messageInput = $('.chat-input');
// 전체채팅 메세지 창
let messengerPublicBox = $('.messenger-public .messenger-comment');
// 메세지 전송 버튼
let sendButton = $('.chat-sendbox');

// 회원검색창
let searchBox = $('.search-member');
// 회원검색창 닫기버튼
let searchClose = $('.search-closebtn');
// 회원검색창 검색 입력칸
let searchInput = $('.search-input');
// 알림 ON/OFF 값
let isAlarm = $('#data-alarm');
// 알림버튼
let alarmButton = $('.top-alarm');
// 하단 메신저 버튼안 이모티콘(편지모양)
let msgOpenIcon = $('.msg-openicon');


$(document).ready(function(){

    // 알림값 on/off 여부에 따라 class 변경
    if ( isAlarm.val() == 'on' || isAlarm.val() == '' ) {
        alarmButton.addClass('alarm-on');
    } else {
        alarmButton.addClass('alarm-off');
    }
    // popover 설정
    $('[data-toggle="popover"]').popover();
    $("[data-toggle='popover']").on('show.bs.popover', function(){

    });

    
    // 전체채팅
    let sockPublic = new SockJS("/public");
    publicClient = Stomp.over(sockPublic);
    publicClient.debug = null;
    publicClient.connect({}, function(){

        publicClient.subscribe('/topic/public', function(msg){
            let msgInfo = JSON.parse(msg.body);
            if ( msgInfo.statusCodeValue == '500' ){
                return false;
            }
            let isScroll = false;
            // 채팅창이 최하단에 있을경우, 메세지 보낸사람이 본인인 경우에만 채팅창 하단으로 스크롤 갱신
            if ( messageContainer.scrollTop() + messageContainer.innerHeight() >= messageContainer[0].scrollHeight || msgInfo.body.msgFrom == $('#data-id').val() ) {
                isScroll = true;
            }
            // 채팅창이 열려있으면서 전채치팅인 경우에만 메세지,스크롤여부 갱신
            if ( !chatWindow.hasClass('d-none') && chatWindow.find('.chat-message').hasClass('public') ) {
                appendMessage(msgInfo.body,isScroll);
                
                // 채팅리스트창이 열려있을경우 전체채팅 메세지칸 메세지 갱신
            } else if ( !chatList.hasClass('d-none') ) {
                messengerPublicBox.html(msgInfo.body.msgText);
            }
        });
    });
    

    // 개인채팅
    let sockPrivate = new SockJS("/private");
    // 접속중인 경우에만 해당
    if ( connectId != '' ) {
        privateClient = Stomp.over(sockPrivate);
        privateClient.debug = null;
        privateClient.connect({}, function(){

            privateClient.subscribe(`/topic/private/${connectId}`, function(msg){
                let msgInfo = JSON.parse(msg.body);
                if ( msgInfo.statusCodeValue == '500' ){
                    return false;
                }
                let isScroll = false;
                if ( messageContainer.scrollTop() + messageContainer.innerHeight() >= messageContainer[0].scrollHeight || msgInfo.body.msgFrom == $('#data-id').val() ) {
                    isScroll = true;
                }

                // 채팅창이 열려있으면서 개인채팅인 경우에만 그리고 나와 현재 연결중인 대상만 채팅창에 갱신
                if ( !chatWindow.hasClass('d-none') && chatWindow.find('.chat-message').hasClass('private') && msgInfo.body.msgFrom == connectFromId ) {
                    appendMessage(msgInfo.body,isScroll);
                    updateReadMessage(msgInfo.body);
                } else if ( !chatList.hasClass('d-none') ) {
                    // 연락온 대상의 아이디가 리스트에 있는지 확인
                    let isInclude = isIncludeTarget(msgInfo.body.msgFrom);
                    if ( isInclude ) {
                        // 있다면 해당 목록에 내용 갱신
                        updateIncludeTarget(msgInfo.body);
                    } else {
                        // 없다면 해당 목록 리스트에 추가
                        addIncludeTarget(msgInfo.body);
                    }
                }

                // 메신저 창에 내려가 있을때
                if ( chatWindow.hasClass('d-none') && chatList.hasClass('d-none') ) {
                    // 연락받을시 메신저 아이콘 swing 적용
                    msgOpenIcon.removeClass('swing').addClass('swing');
                    // 알림 메세지 추가
                    if ( isAlarm.val() == 'on' ) {
                        appendAlarm(msgInfo.body.mbrNick, msgInfo.body.mbrProfile, "님에게서 메세지가 도착 했습니다");
                    }
                }
            });
        });

        // 개인채팅 읽지 않은 메세지가 있을경우 하단 메세지창 이모티콘 동적 swing
        privateUnReadCount(connectId)
            .then((data) => {
                if ( data.msgCount > 0 ) {
                    msgOpenIcon.addClass('swing');
                }
            }).catch( (error) => {
        })
    }


    // 채팅 input EnterKey 적용
    enterPressAction('chat-input','chat-sendbox');

    
    // 메신저 공통 버튼 클릭시
    $(document).on('click','.messenger-common', function(e){
        e.stopPropagation();
        // 공통 클래스 초기화
        initClass();
        let element = $(this);
        let target = '';
        let lastText = '';
        
        // 공통버튼 클래스이름 지정
        let targetClass = new Set()
            .add('messenger-btn').add('messenger-list').add('top-alarm').add('top-invitebtn').add('search-closebtn')
            .add('top-closebtn').add('chat-prev').add('chat-sendbox').add('chat-input').add('search-btnbox');
        for ( const className of targetClass ) {
            if ( checkIncludes(element,className) ){
                target = className;
                break;
            }
        }

        switch (target) {
            // 우측아래 메신저 버튼 클릭시
            case 'messenger-btn' :
                chatWindow.addClass('d-none');
                chatList.addClass('d-none');
                chatList.toggleClass('d-none').addClass('flipInY');
                $('.messenger-alert').html('');
                appendMemberListModule();
                // 전채채팅 마지막 메세지
                publicLastMessage()
                    .then( (data) => {
                        messengerPublicBox.html(data.msgText);
                    }).catch( (error) => {
                });
                break;
                
                
                // 메신저 회원리스트 클릭시
            case 'messenger-list' :
                chatList.toggleClass('d-none');
                chatWindow.toggleClass('d-none').addClass('fadeIn');
                
                // 전체채팅 일 경우
                if ( $(this).attr('class').includes('messenger-public') ) {
                    messageContainer.addClass('public');
                    // 전체 메세지 리스트 조회
                    publicMessageList()
                        .then( (data) => {
                            messageContainer.html('');
                            let targetNick = $(this).find('.messenger-pnick').html();
                            chatTitle.html(targetNick);
                            $.each(data, (index,item) => {
                                appendMessage(item,true);
                            });
                        }).catch( (error) => {
                        return false;
                    })
                    
                    // 개인채팅 일 경우
                } else {
                    messageContainer.addClass('private');
                    let targetId = $(this).attr('data-id');
                    let targetNick = $(this).find('.messenger-nick').html();
                    connectFromId = targetId;
                    chatTitle.html(targetNick);
                    sendButton.attr('data-id',targetId);
                    // 개인 메세지 리스트 조회
                    privateMessageList(targetId)
                        .then( (data) => {
                            $.each(data, (index,item) => {
                                appendMessage(item,true);
                            });
                        }).catch( (error) =>{
                    });
                }

                if ( connectId != '' ) {
                    setTimeout(()=>{messageInput.focus();},500);
                }
                break;
                
                
                // 이전 버튼 클릭시
            case 'chat-prev' :
                // 가장 마지막 메세지를 남긴말로 설정
                if ( messageContainer.hasClass('public') ) {
                    lastText = $('.chat-window .chat-text').last().html();
                    $('.messenger-public .messenger-comment').html(lastText);
                }
                chatWindow.toggleClass('d-none');
                chatList.toggleClass('d-none').addClass('fadeIn');
                messageContainer.removeClass('public').removeClass('private');
                chatTitle.html('');
                messageContainer.html('');
                connectFromId = '';
                sendButton.attr('data-id','');
                appendMemberListModule();
                break;
                
                
                // 상단 알림 버튼 클릭시
            case 'top-alarm' :
                if ( connectId == '' ){
                    checkLogined();
                    return false;
                }
                let alarmState = isAlarm.val();
                if (alarmState == 'on' ){
                    alarmButton.removeClass('alarm-on').removeClass('alarm-off').addClass('alarm-off');
                    alarmState = 'off';
                    isAlarm.val(alarmState);
                } else {
                    alarmButton.removeClass('alarm-on').removeClass('alarm-off').addClass('alarm-on');
                    alarmState = 'on';
                    isAlarm.val(alarmState);
                }
                changeAlarmState(connectId,alarmState);
                break;
                
                
            // 상단 초대 버튼 클릭시
            case 'top-invitebtn' :
                if ( connectId == '' ){
                    checkLogined();
                    return false;
                }
                searchBox.toggleClass('d-none');
                searchBox.removeClass('fadeOutDown').addClass('fadeInUp');
                searchInput.focus();
                enterPressAction('search-input','search-btnbox');
                break;
                
                
                // 검색창 검색시
            case 'search-btnbox' :
                let nick = searchInput.val();
                if ( nick == '' ) {
                    return false;
                }
                searchInput.val('');
                convertNickId(nick)
                    .then( (data) => {
                        connectTarget(data,nick);
                        messageInput.focus();
                    }).catch( (error) => {
                    messengerAlert('존재하지 않는 회원 입니다.',1500);
                });
                break;
                
                
                // 회원 검색 닫기 클릭시
            case 'search-closebtn' :
                searchBox.removeClass('fadeInUp').addClass('fadeOutDown');
                setTimeout(()=>{
                    searchBox.toggleClass('d-none');
                    messageInput.focus();
                    },500);
                break;
                
                
                // 상단 닫기 버튼 클릭시
            case 'top-closebtn' :
                if ( connectId != '') {
                    privateUnReadCount(connectId)
                        .then((data) => {
                            if ( data.msgCount > 0 ) {
                                msgOpenIcon.addClass('swing');
                            } else {
                                msgOpenIcon.removeClass('swing');
                            }
                        }).catch( (error) => {
                    })
                }
                chatWindow.addClass('flipOutY');
                chatList.addClass('flipOutY');
                if ( !searchBox.hasClass('d-none') ){
                    searchBox.toggleClass('d-none').removeClass('fadeInUp').removeClass('fadeOutDown');
                }
                setTimeout(()=>{
                    chatWindow.addClass('d-none');
                    chatList.addClass('d-none');
                },500);
                break;
                
                
                // 보내기 전송 버튼 클릭시
            case 'chat-sendbox' :
                checkLogined();
                let message = messageInput.val();
                messageInput.val('');
                if ( message == '' ) return false;
                // 전체채팅 일 경우
                if ( messageContainer.attr('class').includes('public') ) {
                    publicClient.send(`/public`, {}, JSON.stringify({"message":message}));
                    
                    // 개인채팅 일 경우
                } else {
                    let targetId = $(this).attr('data-id');
                    privateClient.send(`/private/${targetId}`, {}, JSON.stringify({"message":message}));
                    setTimeout(()=>{
                        sendMessageInfo(targetId)
                            .then( (data) => {
                                data.msgText = message.replace(/</gi, "&lt;").replace(/>/,"&gt;");
                                appendMessage(data,true)
                            }).catch( (error) => {
                        });
                    },100);
                }
                break;
                
                // 채팅입력란 클릭시 로그인체크
            case 'chat-input' :
                checkLogined();
                break;
        }
    });


    // 메신저 멤버리스트 초기화 및 갱신
    let appendMemberListModule = () =>{
        messengerListTitle.nextAll().remove();
        if ( connectId != '' ) {
            privateMemberList(connectId)
                .then( (data) => {
                    $.each(data, (index,item) => {
                        appendMemberList(item);
                    }) ;
                }).catch( (error) => {
            });
        }
    }

    // ESC 버튼 메신저 닫기
    $(document).keyup(function(e) {
        // 회원검색이 열려있을경우 먼저 회원검색부터 닫기
        if (e.keyCode == 27) {
            if ( !searchBox.hasClass('d-none') ){
                searchClose.click();
                return false;
            }
            // 메신저가 열려 있는 경우에만 이벤트 실행
            if ( !checkIncludes($('.chat-list'),'d-none') || !checkIncludes($('.chat-window'),'d-none') ) {
                $('.top-closebtn').click();
            }
        }
    });

    // 메신저 회원리스트 삭제
    $(document).on('click', '.messenger-delete', function(){
        let memberId = $('.messenger-delete').parents('.messenger-list').attr('data-id');
        if ( memberId == '') return false;
        // 채팅룸 자신의 상태 비활성화
        disableMember(memberId)
            .then( (data) => {
                $(this).parents('.messenger-list').remove();
                $('.popover').remove();
            }).catch( (error) => {
        });
        return false;
    });


    // 프로필 선택시 상대와 연결
    $(document).on('click', '.messenger-connector', function(){
        if ( connectId == '' ) {
            checkLogined();
            return false;
        }
        if( $(this).parents('.chat-message').hasClass('private') ){
            return false;
        }

        let targetId = $(this).attr('data-id');
        let targetNick = $(this).attr('data-nick');
        connectTarget(targetId,targetNick);
        $("[data-toggle='popover']").popover('hide');
    });


    // 알림 메세지 닫기 버튼 클릭시
    $(document).on('click', '.alarm-close', function(){
        $(this).parents('.message-alarm').removeClass('fadeInUp').addClass('fadeOutDown');
        setTimeout(()=>{$(this).parents('.message-alarm').remove();},500);
    });

    
    // end ready
});


// 메세지 알림 추가
let appendAlarm = (nick, profile, text) => {
    let bgClass = selectProfileBG(profile);
    $('.messenger-alert').append(
                                                    `
                                                        <div class="message-alarm animated fadeInUp fas ${bgClass}">
                                                            <img class="alarm-image" src="/static/image/profile/${profile}">
                                                            <span class="alarm-nick">${nick}</span><span class="alarm-text">${text}</span>
                                                            <div class="alarm-close"><img src="/static/image/study/x.png"></div>
                                                        </div>
                                                    `);
}


// 프로필에 해당하는 배경색 클래스 선택
let selectProfileBG = (profile) =>{
    let no = profile.substring( profile.indexOf('-')+1, profile.indexOf('.png') );
    let className = '';
    if ( no == 1 ) {
        className = 'bg-one';
    } else if ( no > 1 && no <= 8 ) {
        className = 'bg-two';
    } else if ( no >= 9 && no <= 12 ) {
        className = 'bg-three';
    } else if ( no >= 13 && no <= 18 ) {
        className = 'bg-four';
    }
    return className;
}


// 알림 세션 변경
let changeAlarmState = (id,state) => {
    $.ajax({
        type : 'PUT',
        url : `/messenger/alarm/${id}/${state}`,
        contentType : 'application/json; charset=utf-8',
        success : (data) => {
        },
        error : (error) => {
        }
    });
}


// 회원 비활성화
let disableMember = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'PUT',
            url : `/messenger/disable/${id}`,
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 닉네임으로 아이디 조회
let convertNickId = (nick) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/convert/${nick}`,
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 회원이 있는지 여부 조회
let existingMember = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/ismember?id=${id}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}

// 대상과 연결
let connectTarget = (id,nick) => {
    let isError = false;
    existingMember(id)
        .then( (data) => {
            let text = stateCode.get(data.stateCode);
            if ( data.stateCode == 'MESSENGER_FIND_SUCCESS') {
                if ( !searchBox.hasClass('d-none') ) {
                    searchBox.addClass('d-none');
                }
            } else {
                if ( chatList.hasClass('d-none') && chatWindow.hasClass('d-none') ) {
                    findUserAlert(text,1500);
                } else {
                    messengerAlert(text,1500);
                }
                isError = true;
            }
        }).catch( (error) => {
            isError = true;
    }).finally(()=>{
        if ( isError ) {
            searchInput.focus();
            return false;
        }
        initClass();
        if ( !chatWindow.hasClass('d-none') ) {
            chatWindow.addClass('fadeOut');
        }
        if ( !chatList.hasClass('d-none') ) {
            chatList.addClass('fadeOut');
        }
        setTimeout(()=>{
            chatList.removeClass('fadeOut').addClass('d-none');
            chatWindow.removeClass('fadeOut').removeClass('d-none').addClass('fadeIn');
            messageContainer.removeClass('private').removeClass('public').addClass('private').html('');
            connectFromId = id;
            chatTitle.html(nick);
            sendButton.attr('data-id',id);
            privateMessageList(id)
                .then( (data) => {
                    $.each(data, (index,item) => {
                        appendMessage(item,true);
                    });
                }).catch( (error) =>{
            });
        },250);
    });
}

// 메세지 읽음 처리
let updateReadMessage = (memberInfo) => {
    $.ajax({
        type : 'PUT',
        url : '/messenger/update/read',
        data : JSON.stringify(memberInfo),
        contentType : 'application/json; charset=utf-8',
        success : (data) => {
        },
        error : (error) => {
        }
    })
}


// 채팅목록에 해당 아이디가 포함 되어 있는지 확인
let isIncludeTarget = (id) =>{
    let isInclude = false;
    let elements = $('.messenger-list');
    for ( let element of elements ) {
        if (element.getAttribute('data-id') == id) {
            isInclude = true;
        }
    }
    return isInclude;
}


// 채팅목록에 해당 아이디 리스트에 내용 변경
let updateIncludeTarget = (messageInfo) => {
    let elements = $('.messenger-list');
    for ( let element of elements ) {
        if (element.getAttribute('data-id') == messageInfo.msgFrom) {
            $(element).find('.messenger-comment').html(messageInfo.msgText);
            $(element).find('.messenger-count').html(messageInfo.msgCount);
            let target = thisElement($(element));
            $(element).remove();
            $('.messenger-chattitle').after(target);
        }
    }
}


// 유저 목록 추가 상단 업데이트
let addIncludeTarget = (messageInfo) => {
    let appendList = `
                                <li class="messenger-common chat-content chat-left messenger-list animated pulse fast" data-id="${messageInfo.msgFrom}">
                                    <div class="chat-profile">
                                        <img src="/static/image/profile/${messageInfo.mbrProfile}">
                                    </div>
                                    <div class="messenger-infobox">
                                        <div class="messenger-nick">
                                            ${messageInfo.mbrNick}
                                        </div>
                                        <div class="messenger-commentbox">
                                            <div class="messenger-comment">
                                                ${messageInfo.msgText}
                                            </div>
                                            <div class="messenger-count">
                                                ${messageInfo.msgCount}
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                `;
    messengerListTitle.after(appendList);
}


// 유저 목록 추가 하단
let appendMemberList = (messageInfo) => {
    let appendList = `
                                <li class="messenger-common chat-content chat-left messenger-list animated pulse fast" data-id="${messageInfo.msgFrom}">
                                    <div class="chat-profile">
                                        <img src="/static/image/profile/${messageInfo.mbrProfile}">
                                    </div>
                                    <div class="messenger-infobox">
                                        <div class="messenger-nick">
                                            ${messageInfo.mbrNick}
                                        </div>
                                        <div class="messenger-commentbox">
                                            <div class="messenger-comment">
                                                ${messageInfo.msgText}
                                            </div>
                                            <div class="messenger-delete" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="삭제">
                                                <img src="/static/image/common/member-delete.png">
                                            </div>
                                            <div class="messenger-count">
                                                ${messageInfo.msgCount}
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                `;
    messengerList.append(appendList);
    chatList.find('.messenger-list').last().find('.messenger-delete').popover();
}


// 전체채팅 마지막 정보 조회
let publicLastMessage = () =>{
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/publiclast`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 자신의 채팅 멤버리스트 조회
let privateMemberList = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/memberlist?id=${id}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 자신의 읽지 않은 채팅 카운트 조회
let privateUnReadCount = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/unread?id=${id}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 자신이 보낸 메세지 정보 확인
let sendMessageInfo = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/sendinfo?id=${id}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 개인채팅 리스트 조회
let privateMessageList = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/get/private?id=${id}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 전체채팅 리스트 조회
let publicMessageList = () => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : '/messenger/get/public',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    })
}


// 전체채팅 메세지 추가
let appendMessage = (messageInfo, isScroll) => {
    let msgLeft = `
                            <li class="chat-content chat-left animated fadeIn fast">
                                <div class="chat-info">
                                    <div class="chat-nick">${messageInfo.mbrNick}</div>
                                    <div class="chat-date">${messageInfo.msgDate}</div>
                                </div>
                                <div class="chat-profile messenger-connector" data-nick="${messageInfo.mbrNick}" data-id="${messageInfo.msgFrom}" data-container="body" data-placement="top" data-toggle="popover" data-trigger="hover" data-content="${messageInfo.mbrNick}와 채팅">
                                    <img src="/static/image/profile/${messageInfo.mbrProfile}">
                                </div>
                                <div class="chat-textbox">
                                    <div class="chat-text">
                                        ${messageInfo.msgText}
                                    </div>
                                </div>
                            </li>
                        `;
    let msgRight = `
                            <li class="chat-content chat-right animated fadeIn fast">
                                <div class="chat-info">
                                    <div class="chat-nick">나</div>
                                    <div class="chat-date">${messageInfo.msgDate}</div>
                                </div>
                                <div class="chat-profile">
                                    <img src="/static/image/profile/${messageInfo.mbrProfile}">
                                </div>
                                <div class="chat-textbox">
                                    <div class="chat-text">
                                        ${messageInfo.msgText}
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </li>
                        `;
    if ( $('#data-id').val() == messageInfo.msgFrom ){
        messageContainer.append(msgRight)
    } else {
        messageContainer.append(msgLeft);
        // 전체채팅 경우에만 popover 적용
        if ( messageContainer.hasClass('public') ){
            messageContainer.find('.chat-left').last().find('.chat-profile').popover();
        }
    }
    if ( isScroll == true ) {
        messageContainer.scrollTop(messageContainer[0].scrollHeight);
    }
}


// 해당 element에 클래스 이름이 있는지 조회
let checkIncludes = (element,className) => {
    return element.attr('class').includes(className);
}



// animate 효과 초기화
let initClass = () =>{
    chatWindow.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
    chatList.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
    searchBox.removeClass('fadeInUp').removeClass('fadeOutDown');
}


// 로그인 체크
let checkLogined = () =>{
    if( $('#data-id').val() == '' ) {

        if ( chatList.hasClass('d-none') && chatWindow.hasClass('d-none') ) {
            findUserAlert("로그인이 필요합니다",1500);
        } else {
            messengerAlert('로그인이 필요 합니다.', 1500);
        }
        
        $(':focus').blur();
        return false;
    }
}


// 메세지 alert
let messengerAlert= (message, delay) => {
    var alert = $('#messengerMessage').alert();
    $('#messengerMessage').html('<strong>' + message + '</strong>');
    alert.show();
    window.setTimeout(function () {
        alert.hide()
    }, delay);
}


// 회원검색 alert
let findUserAlert= (message, delay) => {
    var alert = $('#findUserMessage').alert();
    $('#findUserMessage').html('<strong>' + message + '</strong>');
    alert.show();
    window.setTimeout(function () {
        alert.hide()
    }, delay);
}