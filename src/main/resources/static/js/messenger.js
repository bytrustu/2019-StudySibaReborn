let publicClient;
let privateClient;
let messageContainer = $('.chat-message');
let messageInput = $('.chat-input');
let messengerPublicBox = $('.messenger-public .messenger-comment');
let chatWindow = $('.chat-window');
let chatList = $('.chat-list');
let connectId = $('#data-id').val();
let chatTitle = $('.chat-window .top-title');
let connectFromId='';
let sendButton = $('.chat-sendbox');
let messengerListTitle = $('.messenger-chattitle');
let messengerList = $('.messenger-chatlist');
let searchBox = $('.search-member');
let searchClose = $('.search-closebtn');
let searchInput = $('.search-input');


$(document).ready(function(){
    /*
            전체채팅
     */
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
            if ( messageContainer.scrollTop() + messageContainer.innerHeight() >= messageContainer[0].scrollHeight || msgInfo.body.msgFrom == $('#data-id').val() ) {
                isScroll = true;
            }

            if ( !chatWindow.hasClass('d-none') && chatWindow.find('.chat-message').hasClass('public') ) {
                appendMessage(msgInfo.body,isScroll);
            } else if ( !chatList.hasClass('d-none') ) {
                messengerPublicBox.html(msgInfo.body.msgText);
            }

        });
    });

    let sockPrivate = new SockJS("/private");
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
            });
        });
    }


    // 채팅 input EnterKey 적용
    enterPressAction('chat-input','chat-sendbox');

    // 메신저 공통 버튼 클릭시
    $(document).on('click','.messenger-common', function(){
        initClass();
        let element = $(this);
        let target = '';
        let lastText = '';
        let targetClass = new Set()
            .add('messenger-btn').add('messenger-list').add('top-listbtn').add('top-invitebtn').add('search-closebtn')
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
                appendMemberListModule();
                // 전채채팅 마지막 메세지
                publicLastMessage()
                    .then( (data) => {
                        messengerPublicBox.html(data.msgText);
                    }).catch( (error) => {
                });
                break;
                
                // 메신저 리스트 클릭시
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
                    setTimeout(()=>{messageInput.focus();},1000);
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
                // 상단 리스트 버튼 클릭시
            case 'top-listbtn' :
                let isChatList = $(this).parent().parent().parent().attr('class').includes('chat-list');
                if ( isChatList ) return false;
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
                break
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
                    messengerAlert('닉네임을 입력해주세요.',1500);
                    return false;
                }
                searchInput.val('');
                convertNickId(nick)
                    .then( (data) => {
                        console.log(`아이디 조회 : `+data);
                        connectTarget(data,nick);
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
                if ( messageContainer.attr('class').includes('public') ) {
                    publicClient.send(`/public`, {}, JSON.stringify({"message":message}));
                } else {
                    let targetId = $(this).attr('data-id');
                    privateClient.send(`/private/${targetId}`, {}, JSON.stringify({"message":message}));
                    setTimeout(()=>{
                        sendMessageInfo(targetId)
                            .then( (data) => {
                                data.msgText = message;
                                appendMessage(data,true)
                            }).catch( (error) => {
                        });
                    },100);
                }
                break;
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

    
    // end ready
});


// 닉네임으로 아이디 조회
let convertNickId = (nick) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/messenger/convert/${nick}`,
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                console.log(data);
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
                messengerAlert(text,1500);
                isError = true;
            }
        }).catch( (error) => {
            isError = true;
    }).finally(()=>{
        if ( isError ) return false;
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
            messageContainer.addClass('private').html('');
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
        },500);

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
                                            <div class="messenger-delete">
                                                <img src="/static/image/common/information.png">
                                            </div>
                                            <div class="messenger-count">
                                                ${messageInfo.msgCount}
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                `;
    messengerList.append(appendList);
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
                                <div class="chat-profile">
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
    $('#data-id').val() == messageInfo.msgFrom ? messageContainer.append(msgRight) : messageContainer.append(msgLeft);
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
        messengerAlert('로그인이 필요 합니다.', 1500);
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