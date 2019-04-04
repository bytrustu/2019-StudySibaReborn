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
        //console.log(subject);
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
        let startPer = $('.inputs_toPer').val();
        let endPer = $('.inputs_fromPer').val();
        let step2 = new Map();
        step2.set('stdLat',lat);
        step2.set('stdLng',lng);
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
        let person = $('select[name=person]').val();
        let file = $('.file-upload-input').val();
        let step3 = new Map();
        step3.set('stdGroup',group);
        step3.set('stdTitle',person);
        step3.set('stdFile',file);
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
    $('.stm-step4').on('click', ()=>{
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
        for (let str of stepMap) {
            console.log(`${str[0]} : ${str[1]}`);
        }
    });
    
    
    
    
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
    
    // Map 합치기
    let pushMap = (originMap, addMap) => {
        for ( let map of addMap ) {
            originMap.set(map[0],map[1]);
        }
        return originMap;
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










});



















