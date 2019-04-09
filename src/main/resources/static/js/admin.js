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
        console.log(key);
        location.href = `/${firstPath()}${menu.get(key)}`;
    });

    let menu = location.pathname;
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



});

let dataChart = () =>{
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


function viewDataChart(){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = new google.visualization.DataTable();
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
            var options = {is3D: true};
            var chart = new google.visualization.PieChart(document.getElementById('dataChart'));
            chart.draw(data, options);
        });
    }
}


let infoChart = () =>{
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



function viewInfoChart(){
    google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = new google.visualization.DataTable();
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
            var options = {is3D:true};
            var chart = new google.charts.Bar(document.getElementById('infoChart'));
            chart.draw(data, google.charts.Bar.convertOptions(options));
        });
    }
}











