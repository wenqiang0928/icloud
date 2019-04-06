var dirPathArr = [];
var pathNameArr = [];

$(document).ready(function () {
    $('#addFile').bind("click",addFile);
    $('#newFolder-modal .modal-footer .btn-info').bind("click", newFolder);
    showUserDiv();
})

function newFolder() {
    var url = Config.baseUrl + "/docs/addDir";
    var params = {
        "nowDirId": dirPathArr[dirPathArr.length-1],
        "addDirName": $("#new-file-name").val()
    };
    $.post(url, params, function (result) {
        if (result.code === 200) {
            freshFileList(dirPathArr[dirPathArr.length-1]);
        }
    });
}

function addFile() {
    
}

function showUserDiv() {
    $("#guideTarget2").click(function () {
        $(".vip-info yp-header__avatarWrap__vip").show();
    });
}

function centerModals() {
    var $clone = $(this).clone().css('display','block').appendTo('body');
    var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
    top = top > 0 ? top : 0;
    $clone.remove();
    $(this).find('.modal-content').css("margin-top", top);
}

function freshFileList(dirId) {
    var url = Config.baseUrl + "/docs/getAllDocsByPid";
    var params = {
        "dirId": dirId
    };
    $.get(url, params, function (result) {
        if (result.code === 200) {
            if (result.data.docsList.length) {
                fillUpTable(result.data.docsList);
                $(".empty-wrap").hide();
                $(".table-wrap").show();
            } else {
                $(".table-wrap").hide();
                $(".empty-wrap").show();
            }
            if (dirPathArr[dirPathArr.length-1] !== result.data.nowDir.id) {
                dirPathArr[dirPathArr.length] = result.data.nowDir.id;// 存储当前路径
                pathNameArr[result.data.nowDir.id] = result.data.nowDir.name;// 存储当前路径名
            }
        }
    });
}

function fillUpTable(docsList) {
    var rows = "";
    docsList.forEach(function (doc) {
        rows += "<li><ul>" +
            "<li class='column-1'><input type='checkbox'></li>" +
            "<li class='column-2'>"+doc.name+"</li>" +
            "<li class='column-3'>"+(doc.size ? doc.size : "-")+"</li>" +
            "<li class='column-4'>"+doc.createTime+"</li>" +
            "</ul></li>";
    });
    $(".file-table .file-list").html(rows);
}