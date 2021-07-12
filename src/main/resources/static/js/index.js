var gid;
function drawAjax(){
    $("#open_winners_table_model")[0].style.visibility = 'hidden';
    let url = encodeURIComponent($('#input-url')[0].value);
    let start_floor = $('#input-start_floor')[0].value;
    let end_floor = $('#input-end_floor')[0].value;
    let start_date = $('#input-start_date')[0].value;
    let start_time = $('#input-start_time')[0].value;
    let end_date = $('#input-end_date')[0].value;
    let end_time = $('#input-end_time')[0].value;
    let keyword = $('#input-keyword')[0].value;
    let draw_nums = $('#input-draw_nums')[0].value;
    let use_regex = $('#input-use_regex')[0].checked;
    let black_list = $('#input-black_list')[0].value;
    let save_draw = $('#input-save_winners')[0].checked;
    let duplicate_save_draw = $('#input-duplicate_save_draw')[0].checked;

    let queryParam = 'url=' + url;

    if(start_floor)
        queryParam += '&start_floor=' + start_floor;
    if(end_floor)
        queryParam += '&end_floor=' + end_floor;
    if(keyword)
        queryParam += '&keyword=' + keyword;
    if(draw_nums)
        queryParam += '&draw_nums=' + draw_nums;
    if(use_regex)
        queryParam += '&use_regex=' + use_regex;
    if(start_date && !start_time)
        start_time= '00:00';
    if(end_date && !end_time)
        end_time = '23:59';
    if(start_date && start_time)
        queryParam += '&start_date=' + start_date + ' ' + start_time;
    if(end_date && end_time)
        queryParam += '&end_date=' + end_date + ' ' + end_time;
    if(black_list)
        queryParam += '&black_list=' + black_list;

    queryParam += '&save_draw=' + save_draw;
    queryParam += '&duplicate_save_draw=' + duplicate_save_draw;

    $('#draw_table').children().remove();

    if(!url || !(/http(s):\/\/(forum\.gamer\.com\.tw|m\.gamer\.com\.tw\/forum)\/C\.php\?.+/.test(decodeURIComponent(url)))){
        alert("網址未填寫或網址錯誤");
        return;
    }

    if(!draw_nums || draw_nums <= 0){
        alert("抽獎人數未填寫或抽獎人數不正確");
        return;
    }

    if(save_draw && draw_nums > 99){
        alert("儲存中獎名單時，中獎人數不得超過99人。");
        return;
    }

    $.ajax({
        url: "/api/draw/drawWinners",
        type: "GET",
        dataType: "text",
        data: queryParam,
        cache: false,
        success:function(response){

            if(response == '[]'){
                appendError('錯誤','找不到符合的抽獎人選。');
            }else{
                let winners = JSON.parse(response);

                $.each(winners,function(index,val){
                    appendWinner(index + 1,val);
                });

                gid = winners[0]['winnerGroup']['id'];
                if(save_draw)
                    $("#open_winners_table_model")[0].style.visibility = 'visible';
            }
        },
        error:function(response){
            let error = JSON.parse(response['responseText']);
            console.log(error['message']);
            appendError('錯誤：' + error['status'],error['message']);
        }
    });
}

function appendWinner(id,json){
    $('#draw_table').append($('<tr>')
        .append($('<th scope="row">')
            .text(id)
        )
        .append($('<td scope="row">')
            .text(json['floor'])
        )
        .append($('<td scope="row">')
            .append($('<a>')
                .attr('href','https://home.gamer.com.tw/homeindex.php?owner=' + json['userID'])
                .text(json['userID'])
            )
        )
        .append($('<td scope="row">')
            .append($('<a>')
                .attr('href','https://home.gamer.com.tw/homeindex.php?owner=' + json['userID'])
                .text(json['userName'])
            )
        )
        .append($('<td scope="row">')
            .append($('<a>')
                .attr('href','https://forum.gamer.com.tw/Co.php?bsn=' + json['bsn'] + '&sn=' + json['snb'])
                .text(json['article'].slice(0,50) + ' ……')
            )
        )
        .append($('<td scope="row">')
            .text(new Date(json['postDate']).toLocaleString())
        )
    );
}

function appendError(type,exceptionName,exceptionMsg){
    $('#draw_table').append($('<tr>')
        .append($('<th colspan="2" scope="row" class="text-danger">')
            .text(type)
        )
        .append($('<td colspan="2" scope="row" class="text-danger">')
            .text(exceptionName)
        )
        .append($('<td colspan="2" scope="row" class="text-danger">')
            .text(exceptionMsg)
        )
    );
}

function openWinnersTable(){
    window.open('/winners/' + btoa(gid), '_blank').focus();
}

 $(document).ajaxStart(function() {
   $("#loadingModal").modal("show");
 }).ajaxStop(function() {
   setTimeout(function () {
        $("#loadingModal").modal("hide");
   }, 500)
   $("#loadingModal").modal("hide");
 }).ajaxComplete(function(){
   setTimeout(function () {
        $("#loadingModal").modal("hide");
   }, 500)
   $("#loadingModal").modal("hide");
 }).ajaxError(function(){
   setTimeout(function () {
        $("#loadingModal").modal("hide");
   }, 500)
   $("#loadingModal").modal("hide");
 }).ajaxSuccess(function(){
   setTimeout(function () {
        $("#loadingModal").modal("hide");
   }, 500)
   $("#loadingModal").modal("hide");
 });