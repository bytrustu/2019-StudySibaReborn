let groupClient;
$(document).ready(function(){
    $('.groupmsg-container').scrollTop($('.groupmsg-container')[0].scrollHeight);
    enterPressAction('groupmsg-input','groupmsg-send');

    let no = contentNo();
    let sockGroup = new SockJS("/group");
    groupClient = Stomp.over(sockGroup);
    groupClient.debug = null;
    groupClient.connect({}, function(){
        groupClient.subscribe(`/topic/group/${no}`, function(msg) {
            let msgInfo = JSON.parse(msg.body);
            if ( msgInfo.statusCodeValue == '500' ){
                errorAlert("잘못된 접근 입니다.");
                return false;
            }
            let groupContainer = $('.groupmsg-container');
            if ( groupContainer.html().includes('group-notfound') ){
                groupContainer.find('.group-notfound').remove();
            }
            sendGroupMessage(msgInfo.body);
        });
    });


    let sendGroupMessage = (messageInfo) => {
        let msg = `
                            <div class="groupmsg-box center-block animated bounce fast">
                                    <div class="row">
                                        <div class="col-xs-8 col-md-8">
                                            <img src="/static/image/profile/${messageInfo.mbrProfile}" class="groupmsg-photo">
                                            <h4 class="groupmsg-name">${messageInfo.mbrNick}</h4>
                                        </div>
                                        <div class="col-xs-4 col-md-4 text-right groupmsg-date">${messageInfo.grmDate}</div>
                                    </div>
                                    <div class="row groupmsg-text">
                                        ${messageInfo.grmText}
                                    </div>
                                </div>
                        `;
        $('.groupmsg-container').append(msg);
        $('.groupmsg-container').scrollTop($('.groupmsg-container')[0].scrollHeight);
    }

    $(document).on('click', '.groupmsg-send', function(){
        let message = $('.groupmsg-input').val();
        $('.groupmsg-input').val('');
        if ( message.trim() == '' ) { errorAlert("메세지를 입력해주세요."); return false;}
        groupClient.send(`/chat/${contentNo()}`, {}, JSON.stringify({"message":message}));
    });





});


