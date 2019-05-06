$(document).ready(function(){

    $(document).on('click','.admin-divide',function(){
        let menu = new Map();
        menu.set("전체","/main");
        menu.set("회원관리","/member");
        menu.set("게시판관리","/board");
        menu.set("스터디관리","/study");
        menu.set("그룹관리","/group");
        menu.set("메세지관리","/message");
        let key = $(this).text().trim();
        location.href = `/${firstPath()}${menu.get(key)}`;
    });

    const menu = location.pathname;
    switch (menu){
        case '/admin/main':
            viewDataChart();
            viewInfoChart();
            break;
    }

    $('#admin-search').on('keyup', function(){
        let searchValue = $(this).val().toLowerCase();
        $('tbody tr').filter(function(){
            $(this).toggle( $(this).text().toLowerCase().indexOf(searchValue) > -1);
        });
    });

    let memberNo;
    $('.admin-memberbtn').on('click', function(){
        getMemberDate($(this).attr('data-id'))
            .then( (data) => {
                $('#memberModal').modal('show');
                memberNo = data.mbrNo;
                $('.sub-page').css('overflow-y','hidden');
                $('.admin-memId').html(data.mbrId);
                $('.admin-memPass').html('');
                $('.admin-memNick').html(data.mbrNick);
                $('.admin-memScore').html(data.pntScore);
                $(`.admin-memAuth option[value=${data.mbrAuth}]`).attr('selected','selected');
                $(`.admin-memType option[value=${data.mbrType}]`).attr('selected','selected');
            }).catch( (error) => {
                errorAlert("잘못된 접근 입니다.");
        });
    });


    $("#memberModal").on('hide.bs.modal', function () {
        $('.sub-page').css('overflow-y','auto');
    });

    $(document).on('click', '.svg-title', function(){
        let element = $(this);
        visibleInput(element);
        return false;
    });

    $(document).on('click', '.admin-meminput', function() {
        return false;
    });

    // input 에서 커서가 아웃될때 회원 데이터 수정
    $('.admin-meminput').blur(function(){
        let element = $(this);
        let type = element.attr('data-type');
        let data = element.val();
        let id = $('.admin-memId').html();
        let target = element.next('div');
        target.html(data).css('display','block');
        element.css('display','none');
        if ( data == prevData) return false;

        let memberMap = new Map();
        memberMap.set('type',type);
        memberMap.set('id',id);
        memberMap.set('value',data);
        if ( memberMap.get('type') == 'MBR_PASS' ){
            if ( memberMap.get('value').trim() == '' ) return false;
        }
        let memberJson = mapToJson(memberMap);
        executeMemberUpdate(memberJson,memberMap,memberNo);
    });

    // select가 변경될때 회원 데이터 수정
    $('.admin-memselect').on('change', function(){
        let id = $('.admin-memId').html();
        let data = $(this).val();
        let type = $(this).attr('data-type');
        let memberMap = new Map();
        memberMap.set('type',type);
        memberMap.set('id',id);
        memberMap.set('value',data);
        let memberJson = mapToJson(memberMap);
        executeMemberUpdate(memberJson);
    });



    /*
            게시판관리
     */

    // 이동 버튼
    $('.admin-movebtn').on('click',function(){
        let path = location.pathname.substring(location.pathname.lastIndexOf('/')+1);
        let no = $(this).attr('data-no');
        switch (path) {
            case 'board' :
                path = `/${path}/community/view?no=${no}`;
                break;
            case 'study' :
                path = `/${path}/view?no=${no}`;
                break;
            case 'group' :
                path = `/${path}/view?no=${no}`;
                break;
        }
        window.open(path,'_blank');
    });


});

// 회원정보 업데이트 처리
const executeMemberUpdate = (memberJson,memberMap,memberNo) => {
    updateMember(memberJson)
        .then( (data) => {
            successAlert('회원정보가 변경 되었습니다.');
            let elements = $('th');
            for ( let element of elements ) {
                if ( element.innerHTML == memberNo ) {
                    if (memberMap.get('type') == 'MBR_NICK' ) {
                        element.nextElementSibling.nextElementSibling.innerHTML = memberMap.get('value');
                    } else if ( memberMap.get('type') == 'PNT_SCORE' ) {
                        element.nextElementSibling.nextElementSibling.nextElementSibling.innerHTML = memberMap.get('value');
                    }
                }
            }
        }).catch( (error) => {
            errorAlert(stateCode.get(error.stateCode));
    });
}

const updateMember = (memberJson) =>{
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'PUT',
            url : '/admin/member/update',
            data : memberJson,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) =>{
                resolve(data);
            },
            error : (error) =>{
                reject(JSON.parse(error.responseText));
            }
        })
    });
}

const getMemberDate = (id) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/admin/member/get/${id}`,
            data : id,
            dataType:'json',
            contentType: 'application/json; charset=utf-8',
            success : (data) =>{
                resolve(data);
            },
            error : (error) =>{
                reject(error);
            }
        });
    });
}

let prevData;
// div => input 으로
const visibleInput = (element) => {
    let data = element.html();
    prevData = data;
    let target = element.prev('input');
    target.val(data).css('display','block');
    target.focus();
    element.html('').css('display','none');
    return false;
}

// 커뮤니티/스터디/그룹/메세지 데이터 통계 조회
const dataChart = () =>{
    return new Promise( (resolve, reject ) => {
        $.ajax({
            type : 'POST',
            url : '/admin/chart/data',
            dataType : 'json',
            success : (data) =>{
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}

// 데이터 통계 파이차트
const viewDataChart = () => {
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', '분류');
        data.addColumn('number', '작성률%');
        data.addRows(6);
        data.setCell(0,0,'커뮤니티 게시글');
        data.setCell(1,0,'커뮤니티 댓글');
        data.setCell(2,0,'스터디 게시글');
        data.setCell(3,0,'그룹 공지사항');
        data.setCell(4,0,'그룹 단체채팅');
        data.setCell(5,0,'개인 채팅');
        dataChart()
            .then( (response) => {
                $.each(response,function(key,value){
                    switch(key){
                        case 'BCNT' :
                            data.setCell(0,1,value);
                            break;
                        case 'CCNT' :
                            data.setCell(1,1,value);
                            break;
                        case 'SCNT' :
                            data.setCell(2,1,value);
                            break;
                        case 'NCNT' :
                            data.setCell(3,1,value);
                            break;
                        case 'GCNT' :
                            data.setCell(4,1,value);
                            break;
                        case 'MCNT' :
                            data.setCell(5,1,value);
                            break;
                    }
                });
            }).catch( (error) => {
            errorAlert('데이터 차트 조회에 실패 했습니다.');
        }).finally(()=>{
            let options = {is3D: true};
            let chart = new google.visualization.PieChart(document.getElementById('dataChart'));
            chart.draw(data, options);
        });
    }
}


// 방문자수/가입수 데이터 통계 조회
const infoChart = () => {
    return new Promise( (resolve, reject ) => {
        $.ajax({
            type : 'POST',
            url : '/admin/chart/info',
            dataType : 'json',
            success : (data) =>{
                resolve(data);
            },
            error : (error) => {
                reject(error);
            }
        });
    });
}


// 정보통계 바차트
const viewInfoChart = () => {
    google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);
    function  drawChart(){
        let data = new google.visualization.DataTable();
        data.addColumn('string', '시간대별');
        data.addColumn('number', '가입수');
        data.addColumn('number', '방문수');

        infoChart().then( (response) => {
            data.addRows(response.length);
            $.each(response,function(index,value){
                data.setCell(index,0,`${value.VDATE}시`);
                data.setCell(index,1,value.MCNT);
                data.setCell(index,2,value.VCNT);
            });
        }).catch( (error) => {
            errorAlert("정보차트 조회에 실패했습니다.");
        }).finally( ()=> {
            let options = {is3D:true};
            let chart = new google.charts.Bar(document.getElementById('infoChart'));
            chart.draw(data, google.charts.Bar.convertOptions(options));
        });
    }
}











