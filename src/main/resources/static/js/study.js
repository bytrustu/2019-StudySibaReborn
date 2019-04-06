$(document).ready(function(){


    // 스터디,그룹 CkEditor 설정
    let detailContent;
    ClassicEditor
        .create(document.querySelector('#studyEditor'), {
            language: 'ko',
            toolbar: {
                viewportTopOffset: 30
            },
            container : {
                overflow : scroll
            },
            ckfinder: {
                uploadUrl: '/upload/community'
            }
        })
        .then(editor => {
            detailContent = editor;
        })
        .catch(error => {
        });


    // 스터디 등록 모달 버튼
    $('.content-studybtn').on('click', function(){
        $('#studyModal').modal('show');
        $('.stm-update').css('display','none');
        $('.stm-step4').css('display','inline-block');
    });



    /*
     *  Step1 일정
     */

    // 주제 버튼
    $('.ico').on('click', function(){
        let subjectValue = parseInt($('.sujectCnt').html());
        $(this).toggleClass('liked');
        if ( $(this).hasClass('liked') ) {
            if ( subjectValue >= 5 ) {
                autoClosingAlert('5개 초과 지정할수 없습니다.',2500);
                $(this).toggleClass('liked');
                return false;
            }
            subjectValue++;
            $(this).prev('.input-subject').val('true');
        } else {
            $(this).prev('.input-subject').val('false');
            subjectValue--;
        }
        $('.sujectCnt').html(subjectValue);
    });

    // 체크 된 주제값 가져오기
    let checkSubject = (className) => {
        let subject = [];
        let elements = document.getElementsByClassName(className);
        for (let element of elements) {
            if ( element.value == 'true' ) {
                subject.push(element.getAttribute('data-subject'));
            }
        }
        return subject;
    }

    // 스탭1 값 Map 저장
    function step1map(){
        let subjectInputs = checkSubject('input-subject');
        if ( subjectInputs == '' ) {
            let step1 = new Map();
            step1.set('stdSubject', '');
            return step1;
        }
        return subjectInputs;
    }




    /*
     *  Step2 일정
     */
    
    // 시작일자 조정
    $("#inputs_toPer").on("change", function() {
        let currDate = new Date();
        currDate = formatDate(currDate);
        var startDate = $(this).val();
        if ( startDate < currDate ) {
            autoClosingAlert('이전일자는 지정할수 없습니다.',2500);
            $(this).val('');
            return false;
        }
    });

    // 종료일자 조정
    $("#inputs_fromPer").on("change", function() {
        let startDate = $('#inputs_toPer').val();
        if ( startDate == '' ) {
            autoClosingAlert('시작일자가 지정되지 않았습니다.',2500);
            $(this).val('');
        } else if ( startDate >= $(this).val() ) {
            autoClosingAlert('시작일자보다 이후로 지정해주세요.',2500);
            $(this).val('');
        }
    });

    // 스탭2 값 Map 저장
    function step2map(){
        let lat = $('#stm-lat').val();
        let lng = $('#stm-lng').val();
        let address = $('#pac-input').val();
        let place = $('#stm-place').val();
        let startPer = $('.inputs_toPer').val();
        let endPer = $('.inputs_fromPer').val();
        let step2 = new Map();
        step2.set('stdLat',lat);
        step2.set('stdLng',lng);
        step2.set('stdPlace',place);
        step2.set('stdAddress',address);
        step2.set('stdStart',startPer);
        step2.set('stdEnd',endPer);
        return step2;
    }




    /*
     *  Step3 그룹
     */

    // 스탭3 값 Map 저장
    function step3map(){
        let group = $('#stm-title').val();
        let limit = $('#std-limit').val();
        let file = $('.file-upload-input').val();
        let updateFile = 'true';
        let step3 = new Map();
        if ( location.pathname.includes('view') ) {
            step3.set('stdNo', location.search.substring( location.search.lastIndexOf('=')+1 ));
            if ( $('.file-upload-image').attr('src').includes('/file') ) {
                updateFile = 'false';
            } else {
                step3.set('stdFile',file);
            }
        } else {
            step3.set('stdFile',file);
        }
        step3.set('updateFile',updateFile);
        step3.set('stdGroup',group);
        step3.set('stdLimit',limit);
        return step3;
    }



    /*
     *  Step4 상세
     */

    // 스탭4 값 Map 저장
    function step4map(){
        let detailTitle = $('#stm-detailtitle').val();
        let detailText = detailContent.getData();
        let step4 = new Map();
        step4.set('stdTitle', detailTitle);
        step4.set('stdContent', detailText);
        return step4;
    }

    // 스탭4 등록 버튼 클릭
    $('.stm-step4, .stm-update').on('click', function(){
        let step1 = step1map();
        let step2 = step2map();
        let step3 = step3map();
        let step4 = step4map();
        let idx = 0;
        if ( step1.constructor == Map ) {
            idx = checkStep(step1,'one');
        }

        if ( idx > 0 ) return false;
        idx = checkStep(step2,'two');
        if ( idx > 0 ) return false;
        idx = checkStep(step3,'three');
        if ( idx > 0 ) return false;
        idx = checkStep(step4,'four');
        if ( idx > 0 ) return false;



        let stepMap = new Map();
        stepMap.set('stdDivide',step1.join(','));
        stepMap = pushMap(stepMap,step2);
        stepMap = pushMap(stepMap,step3);
        stepMap = pushMap(stepMap,step4);
        let formData = mapToFormData(stepMap);
        if ( $(this).hasClass('stm-update') ) {
            if ( step3.get('updateFile') == 'false' ) {
                //formData.append('uldFilename', step3.get('stdFile') );
            } else {
                formData.append('stdFile', $('#studyFile')[0].files[0]);
            }
            updateStudy(formData)
                .then( (data) => {
                    $('#studyModal').modal('hide');
                    timerAlert("스터디수정","입력하신 정보로 수정중입니다.",2500);
                    setTimeout(()=>{location.href=`/study/view?no=${data.no}`},3000);
                }).catch( (error) => {
                    errorAlert(stateCode.get(error.stateCode));
            });
        } else {
            formData.append('stdFile', $('#studyFile')[0].files[0]);
            registerStudy(formData)
                .then( (data) => {
                    $('#studyModal').modal('hide');
                    timerAlert("스터디등록","입력하신 스터디를 등록중입니다.",2500);
                    setTimeout(()=>{location.href=`/study/view?no=${data.no}`},3000);
                }).catch( (error) => {
                    errorAlert(stateCode.get(error.stateCode));
            });
        }
    });

    let registerStudy = (formData) => {
        return new Promise( (resolve, reject) => {
            $.ajax({
                type : 'POST',
                url : `/study/register`,
                data : formData,
                dataType : 'json',
                processData : false,
                contentType : false,
                success : ( data ) => {
                    resolve(data);
                },
                error : (error) => {
                    reject(JSON.parse(error.responseText));
                }
            });
        });
    }

    let updateStudy = (formData) => {
        return new Promise( (resolve, reject) => {
            $.ajax({
                type : 'POST',
                url : `/study/update`,
                data : formData,
                dataType : 'json',
                processData : false,
                contentType : false,
                success : ( data ) => {
                    resolve(data);
                },
                error : (error) => {
                    reject(JSON.parse(error.responseText));
                }
            });
        });
    }

    
    
    /*
     *  Step 공통
     */

    // 스탭별 map 값을 확인해서 값이 없을시에 이동
    let checkStep = (stepMap, step) => {
        let idx=0;
        for ( let stepStr of stepMap ) {
            if ( stepStr[1] == '' ) {
                idx++;
                switch (step) {
                    case 'one':
                        setTimeout(()=>{$(`.res-step-${step}`).trigger('click');},500)
                        setTimeout(()=>{autoClosingAlert('주제를 하나이상 선택해주세요.',2500);},1500);
                        break;
                    case 'two':
                        setTimeout(()=>{$(`.res-step-${step}`).trigger('click');},500)
                        setTimeout(()=>{autoClosingAlert('일정항목을 모두 입력해주세요.',2500);},1300);
                        break;
                    case 'three':
                        setTimeout(()=>{$(`.res-step-${step}`).trigger('click');},500)
                        setTimeout(()=>{autoClosingAlert('그룹정보를 모두 입력해주세요..',2500);},1000);
                        break;
                    case 'four':
                        setTimeout(()=>{autoClosingAlert('상세내용을 모두 입력해주세요.',2500);},500);
                        break;
                }
            }
        }
        return idx;
    }

    // 에러 Alert 출력
    function autoClosingAlert(message ,delay) {
        var alert = $('#dangerMessage').alert();
        $('#dangerMessage').html('<strong>'+message+'</strong>');
        alert.show();
        window.setTimeout(function () {
            alert.hide()
        }, delay);
    }
    
    // Map 와 Map 합치기
    let pushMap = (originMap, addMap) => {
        for ( let map of addMap ) {
            originMap.set(map[0],map[1]);
        }
        return originMap;
    }

    // Map 을 FormData로
    let mapToFormData = (map) => {
        let formData = new FormData();
        for ( let data of map ) {
            formData.append(data[0],data[1]);
        }
        return formData;
    }


    /*
     *  스탭별 위쪽 버튼 및 아래쪽 이전 다음 버튼 클릭시 화면 보여지는 마진 처리
     */

    var steps = ['.res-step-one','.res-step-two','.res-step-three','.res-step-four'];
    var i = 1;
    $(".res-step-form .res-btn-orange").click(function(){
        var getClass = $(this).attr('data-class');
        $(".res-steps").removeClass('active');
        $(steps[i]).addClass('active');
        i++;
        if(getClass != ".res-form-four"){
            $(getClass).animate({
                left: '-150%'
            }, 500, function(){
                $(getClass).css('left', '150%');
            });
            $(getClass).next().animate({
                left: '50%'
            }, 500, function(){
                $(this).css('display','block');
            });
        }
    });

    $(".res-step-form .res-btn-gray").click(function(){
        var getClass = $(this).attr('data-class');
        $(".res-steps").removeClass('active');
        i--;
        $(steps[i-1]).addClass('active');
        $(getClass).prev().css('left','-150%')
        $(getClass).animate({
            left: '150%'
        }, 500);
        $(getClass).prev().animate({
            left: '50%'
        }, 500)
    });

    $('.res-step-one').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 0;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-one').css('left','-150%');
            $('.res-form-two, .res-form-three, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-one').animate({
                left: '50%'
            }, 500);
        }
    });
    
    $('.res-step-two').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 1;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-two').css('left','-150%');
            $('.res-form-one, .res-form-three, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-two').animate({
                left: '50%'
            }, 500);
        }
    });

    $('.res-step-three').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 2;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-three').css('left','-150%');
            $('.res-form-one, .res-form-two, .res-form-four').animate({
                left: '150%'
            }, 500);
            $('.res-form-three').animate({
                left: '50%'
            }, 500);
        }
    });

    $('.res-step-four').click(function(){
        if(!$(this).hasClass('active')){
            $(".res-steps").removeClass('active');
            i = 3;
            $(steps[i]).addClass('active');
            i++;
            $('.res-form-four').css('left','-150%');
            $('.res-form-one, .res-form-two, .res-form-three').animate({
                left: '150%'
            }, 500);
            $('.res-form-four').animate({
                left: '50%'
            }, 500);
        }
    });


    /*
     *  DatePicker Custom 설정
     */
    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });
    $('.datepickter').datepicker();




    // 스터디 클릭시
    $('.st-move').on('click', function(e){
        e.preventDefault();
        let currentPath = firstPath();
        let pathMap = new Map();
        let nextMove;
        let type;
        if ( $(this).attr('class').includes('content-listbtn') ) {
            type = $('#page-type').val();
            nextMove = 'list';
        } else {
            type = $('.std-keyword').val();
            let link = $(this).attr('data-no');
            pathMap.set('no',link);
            nextMove = 'view';
        }
        let pageNum = $('#page-num').val();
        let keyword = $('#page-keyword').val();
        if ( type == -1 || type == -2 || type == undefined ) type='';
        if ( pageNum == 1 ) pageNum='';
        pathMap.set('pageNum',pageNum);
        pathMap.set('type',type);
        pathMap.set('keyword',keyword);
        let path = `/${currentPath}/${nextMove}${makePath(pathMap)}`;
        location.href=path;
    });


    // 스터디 리스트 주제 선택 검색키워드 이동
    $('.st-subject span').on('click', function(){
        let keyword = $(this).html().trim();
        if ( keyword == '전체' ) {
            location.href='/study/list';
            return false;
        }
        location.href=`/study/list?keyword=${keyword}`;
    });


    $('.study-edit').on('click', function(){
        $('#studyModal').modal('show');

        $('.stm-update').css('display','inline-block');
        $('.stm-step4').css('display','none');

        let no = contentNo();
        
        initLiked();

        getStudy(no)
            .then( (data) => {
                setTimeout(()=>{
                    //Step1 주제
                    let subject = data.stdDivide.split(',');
                    activeSubject('input-subject',subject);
                    //Step2 일정
                    $('#pac-input').val(data.stdAddress);
                    $('#stm-lat').val(data.stdLat);
                    $('#stm-lng').val(data.stdLng);
                    $('#stm-place').val(data.stdPlace);
                    $('#inputs_toPer').val(data.stdStart);
                    $('#inputs_fromPer').val(data.stdEnd);
                    //Step3 그룹
                    $('#stm-title').val(data.stdGroup);
                    $('#std-limit').val(data.stdLimit);
                    $('.file-upload-image').attr('src',`/file/view/study/${data.uldFilename}`);
                    $('.file-upload-content').css('display','block');
                    $('.image-upload-wrap').css('display','none');
                    //Step4 상세
                    $('#stm-detailtitle').val(data.stdTitle);
                    detailContent.setData(data.stdContent);
                },500);
            }).catch( (error) => {
                errorAlert("잘못된 요청입니다.");
        });
    });

});

// 등록되어잇는 주제선정 입력
let activeSubject = (elements, array) =>{
    let inputs = document.getElementsByClassName(elements);
    let i =0;
    for ( let element of inputs ) {
        for ( let value of array ) {
            if ( element.getAttribute('data-subject') == value ) {
                    i++;
                    element.value = 'true';
                    liked(element, i);
            }
        }
    }
}

// 주제선정 초기화
function initLiked(){
    let inputs = document.getElementsByClassName('input-subject');
    for ( let element of inputs ) {
        element.value = 'false';
        element.nextSibling.nextSibling.classList.remove('liked');
    }
    $('.sujectCnt').html('0');
}

// 주제선정 순차적으로 클릭효과
function liked(element,i){
    setTimeout(()=>{
        element.nextSibling.nextSibling.classList.add('liked');
        $('.sujectCnt').html(i);
    },400*i);
}



// 댓글 게시글 조회
let getStudy = (no) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : `/study/get/${no}`,
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

// 스터디 비활성화 버튼
$('.study-delete').on('click', ()=>{
    let no = contentNo();
    Swal.fire({
        title: '스터디비활성화',
        text: '선택하신 스터디를 비활성화 하시겠습니까?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#fbc02d',
        cancelButtonColor: '#e4b67c ',
        confirmButtonText: '네'
    }).then((result) => {
        if (result.value) {
            deleteStudy(no)
                .then( (data) => {
                    successAlert(stateCode.get(data.stateCode));
                    setTimeout(()=>{
                        location.href=`/study/list`;
                    },1500);
                }).catch( (error) => {
                errorAlert(stateCode.get(error.responseText.stateCode));
            });
        }
    });
});

// 스터디 비활성화 처리
let deleteStudy = (no) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'DELETE',
            url : `/study/delete/${no}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(JSON.parse(error.responseText));
            }
        })
    });
}


$('.study-joinbtn').on('click', function(){
    let btnState = $(this).attr('data-join');
    console.log(btnState);
    switch (btnState) {
        case 'already' :
            errorAlert('이미 참여중인 스터디 입니다.');
            break;
        case 'unable' :
            errorAlert('스터디 모집이 마감되었습니다.');
            break;
        case 'join' :
            let no = contentNo();
            joinStudy(no)
                .then( (data) => {
                    timerAlert('스터디참여', '스터디 참여정보를 확인중입니다.', 2000);
                    setTimeout(()=>{
                        location.href=`/study/view?no=${data.no}`;
                    },2200);
                }).catch( (error) => {
                    errorAlert(stateCode.get(error.stateCode));
            });
            break;
    }
});

$('.study-outbtn').on('click', function(){
    let btnState = $(this).attr('data-out');
    let no = contentNo();
    if ( btnState == 'member' ) {
        outStudy(no)
            .then( (data) => {
                timerAlert('스터디탈퇴', '스터디 탈퇴정보를 확인중입니다.', 2000);
                setTimeout(()=>{
                    location.href=`/study/view?no=${data.no}`;
                },2200);
            }).catch( (error) => {
                errorAlert(stateCode.get(error.stateCode));
        });
    }
});

let joinStudy = (no) =>{
    return new Promise( (resolve, reject ) => {
        $.ajax({
            type : 'POST',
            url : `/study/join/${no}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(JSON.parse(error.responseText));
            }
        });
    });
}

let outStudy = (no) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'DELETE',
            url : `/study/out/${no}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(JSON.parse(error.responseText));
            }
        });
    });
}

let contentNo = () =>{
    return location.search.substring( location.search.lastIndexOf('=')+1 );
}



$('.study-latest').on('click', ()=>{
    let no = contentNo();
    Swal.fire({
        title: '스터디갱신',
        text: '스터디 게시글을 최상단으로 갱신 하시겠습니까?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#fbc02d',
        cancelButtonColor: '#e4b67c ',
        confirmButtonText: '네'
    }).then((result) => {
        if (result.value) {
            latestStudy(no)
                .then( (data) => {
                    timerAlert("스터디갱신","스터디 갱신 정보를 확인중입니다.",1500);
                    setTimeout(()=>{
                        location.href=`/study/list`;
                    },1500);
                }).catch( (error) => {
                errorAlert(stateCode.get(error.responseText.stateCode));
            });
        }
    });
});

let latestStudy = (no) => {
    return new Promise( (resolve, reject) => {
        $.ajax({
            type : 'PUT',
            url : `/study/latest/${no}`,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            success : (data) => {
                resolve(data);
            },
            error : (error) => {
                reject(JSON.parse(error.responseText));
            }
        });
    });
}

















