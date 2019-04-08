$(document).ready(function(){

    $(document).on('click','.admin-divide',function(e){
        let menu = new Map();
        menu.set("전체","/main");
        menu.set("회원관리","/member");
        menu.set("게시판관리","/board");
        menu.set("스터디관리","/study");
        menu.set("그룹관리","/group");
        menu.set("메세지관리","/message");
        let key = $(this).html().trim();
        console.log(key);
        location.href = `/${firstPath()}${menu.get(key)}`;
    });


    viewDataChart();












});



let executeChart = () =>{
    dataChart()
        .then( (data) => {
            console.log(data);
        }).catch( (error) => {
        console.log(error.responseText);
    })
}


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
                reject(error);l
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
                console.log(response);
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
            console.log(error.responseText);
        }).finally(()=>{
            var options = {is3D: true};
            var chart = new google.visualization.PieChart(document.getElementById('dataChart'));
            chart.draw(data, options);
        })


    }
}







