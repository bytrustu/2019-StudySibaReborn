let publicClient;
let privateClient;
let messageContainer = $('.chat-message');
let messageInput = $('.chat-input');
let messengerPublicBox = $('.messenger-public .messenger-comment');



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
            viewPublicMessage(msgInfo.body,isScroll);
            messengerPublicBox.html(msgInfo.body.msgText);
        });
    });

    // 채팅 input EnterKey 적용
    enterPressAction('chat-input','chat-sendbox');

    // 메신저 공통 버튼 클릭시
    $(document).on('click','.messenger-common', function(){
        initClass();
        let element = $(this);
        let target = '';
        let targetClass = new Set()
            .add('messenger-btn').add('messenger-list').add('top-listbtn').add('top-invitebtn').add('top-closebtn').add('chat-prev').add('chat-sendbox').add('chat-input');
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
                break;
                
                // 메신저 리스트 클릭시
            case 'messenger-list' :
                chatList.toggleClass('d-none');
                chatWindow.toggleClass('d-none').addClass('fadeIn');
                // 전체채팅 일 경우
                if ( $(this).attr('class').includes('messenger-public') ) {
                    messageContainer.removeClass('private').removeClass('public').addClass('public');
                    publicMessageList()
                        .then( (data) => {
                            messageContainer.html('');
                            $.each(data, (index,item) => {
                                viewPublicMessage(item,true);
                            });
                        }).catch( (error) => {
                        return false;
                    })
                } else {
                    messageContainer.removeClass('private').removeClass('public').addClass('private');
                }
                chatMessage.scrollTop(chatMessage[0].scrollHeight);

                break;
                // 이전 버튼 클릭시
            case 'chat-prev' :
                chatWindow.toggleClass('d-none');
                chatList.toggleClass('d-none').addClass('fadeIn');
                break;
                // 상단 리스트 버튼 클릭시
            case 'top-listbtn' :
                let isChatList = $(this).parent().parent().parent().attr('class').includes('chat-list');
                if ( isChatList ) return false;
                chatList.toggleClass('d-none').addClass('fadeIn');
                chatWindow.toggleClass('d-none');
                break
            // 상단 초대 버튼 클릭시
            case 'top-invitebtn' :
                break;
                // 상단 닫기 버튼 클릭시
            case 'top-closebtn' :
                chatWindow.addClass('flipOutY');
                chatList.addClass('flipOutY');
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
                    publicClient.send(`/chat`, {}, JSON.stringify({"message":message}));
                } else {

                }
                break;
            case 'chat-input' :
                checkLogined();
                break;
        }

    });


    $(document).keyup(function(e) {
        // esc 버튼 클릭시
        if (e.keyCode == 27) {
            // 메신저가 열려 있는 경우에만 이벤트 실행
            if ( !checkIncludes($('.chat-list'),'d-none') || !checkIncludes($('.chat-window'),'d-none') ) {
                $('.top-closebtn').click();
            }
        }
    });


});

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
let viewPublicMessage = (messageInfo, isScroll) => {
    let msgLeft = `
                            <li class="chat-content chat-left animated bounce fast">
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
                            <li class="chat-content chat-right animated bounce fast">
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


let chatWindow = $('.chat-window');
let chatList = $('.chat-list');
let chatMessage = $('.chat-message');

// animate 효과 초기화
let initClass = () =>{
    chatWindow.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
    chatList.removeClass('flipInY').removeClass('flipOutY').removeClass('fadeIn');
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