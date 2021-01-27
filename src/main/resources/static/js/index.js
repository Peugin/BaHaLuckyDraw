function drawAjax(){
    let url = encodeURIComponent($('#input-url')[0].value);
    let start_floor = $('#input-start_floor')[0].value;
    let end_floor = $('#input-end_floor')[0].value;
    let start_date = $('#input-start_date')[0].value;
    let start_time = $('#input-start_time')[0].value;
    let end_date = $('#input-end_date')[0].value;
    let end_time = $('#input-end_time')[0].value;
    let keyword = $('#input-keyword')[0].value;
    let draw_nums = $('#input-draw_nums')[0].value;
    let use_regex = $('#input-keyword-use-regex')[0].checked;
    let white_list = $('#input-white_list')[0].value;
    let black_list = $('#input-black_list')[0].value;

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
    if(white_list)
        queryParam += '&white_list=' + white_list;
    if(black_list)
        queryParam += '&black_list=' + black_list;

    $('#draw_table').children().remove();

    if(!url || !(/https:\/\/forum\.gamer\.com\.tw\/C\.php\?.+/.test(decodeURIComponent(url)))){
        alert("網址未填寫或網址錯誤");
        return;
    }

    if(!keyword){
        alert("關鍵字未填寫");
        return;
    }

    if(!draw_nums || draw_nums <= 0){
        alert("抽獎人數未填寫或抽獎人數不正確");
        return;
    }

    $.ajax({
        url: "/api/draw",
        type: "GET",
        dataType: "text",
        data: queryParam,
        success:function(response){

            if(response == '[]'){
                appendError('錯誤','找不到符合的抽獎人選。');
            }else{
                let winners = JSON.parse(response);

                $.each(winners,function(index,val){
                    appendWinner(index + 1,val);
                });
            }
        },
        error:function(response){
            appendError('錯誤','可能為輸入資料有誤，或搜尋的樓層過多，請再試一次。');
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

function appendError(error,msg){
    $('#draw_table').append($('<tr>')
        .append($('<th scope="row">')
            .text(error)
        )
        .append($('<td colspan="5" scope="row">')
            .text(msg)
        )
    );
}

$body = $("body");

$(document).on({
    ajaxStart: function() {
      $("#btn-draw").prop('disabled', true);
      $("#loadingModal").modal("show");
    },
     ajaxStop: function() {
      $("#btn-draw").prop('disabled', false);
      $("#loadingModal").modal("hide");
     }
});