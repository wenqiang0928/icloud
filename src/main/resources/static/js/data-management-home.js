var dirPathArr = [];
var pathNameArr = [];
//移动时，用户选中的节点id
var nowSelectedNodeId = 0;

// 列表属性 0-正常文件 1-回收站文件
var listType = 0;

$(document).ready(function () {
    $('#addFile').bind("click", addFile);
    $('#newFolder-modal .modal-footer .btn-info').bind("click", newFolder);
    $('.file-table-title input').bind("change", selectAndUnselectAll);
    $('body').bind("click", hidePopMenu);
    $('#delete-file-button').bind("click", deleteDocs);
    $('#move-file-button').bind("click", moveDocs);
    $('#download-file-button').bind("click", downloadDocs);
    $('#renameDocs-modal .modal-footer .btn-info').bind("click", renameDocs);
    $('#moveDocs-modal .modal-footer .btn-info').bind("click", moveDocsConfirm);

    $(document).ready(function () {
        $(document).bind("contextmenu", function (e) {
            return false;
        });
    });

    showUserDiv();
});

//获取treeview数据
function getTree(ids) {
    var tree = [];
    var url = Config.baseUrl + "/docs/dirTree?ids=" + ids;
    $.ajax({
        url: url,
        type: "get",
        contentType: "application/json",
        timeout: 30000, //超时时间：30秒
        async: false,//false-同步（当这个ajax执行完后才会继续执行其他代码）；异步-与其他代码互不影响，一起运行。
        dataType: "json",
        success: function (res) {
            // console.log(data);
            tree = res.data;
        }, error: function (data) {
            console.log(data);
        }
    });
    return tree;
}

//获取选中的文件id
function getSelectedDocsIds() {
    var selectedDocsArr = $("input[type='checkbox']:checked");
    var ids = "";
    if (selectedDocsArr.length > 0) {
        for (var i = 0; i < selectedDocsArr.length; i++) {
            var id = selectedDocsArr[i].value;
            if (id.length > 0) {
                ids += selectedDocsArr[i].value + ",";
            }
        }
    }
    ids = ids.substring(0, ids.length - 1);
    return ids;
}

/**
 * 监听全选按钮状态
 *
 */
function allFileSelect() {
    setTimeout(function () {
        if (listType == 0) {
            checkboxChanged();
        } else {
            deleteBtnChange();
        }
    }, 10);
}

//监听页面复选框
function checkboxChanged() {
    var count = $("input[type='checkbox']:checked").length;
    if (count === 0) {
        $("#delete-div").hide();
        $("#rename-div").hide();
        $("#move-div").hide();
        $("#download-div").hide();
    } else if (count === 1) {
        $("#delete-div").show();
        $("#rename-div").show();
        $("#move-div").show();
        var selectedDocsArr = $("input[type='checkbox']:checked");
        var path = selectedDocsArr.data("path");
        var type = selectedDocsArr.data("type");
        if (type != "1") {
            $("#download-div").show();
        }
    } else if (count > 1) {
        $("#delete-div").show();
        $("#move-div").show();
        $('#rename-div').hide();
        $("#download-div").hide();
    }
}

/**
 * 删除列表顶部按钮状态变化
 */
function deleteBtnChange() {
    var count = $(".file-list input[type='checkbox']:checked").length;
    // if (count > 0) {
    //     $("#restore-div").show();
    // } else {
    //     $("#restore-div").hide();
    // }
}


//删除文件/文件夹
function deleteDocs() {
    swal({
        title: "操作提示",      //弹出框的title
        text: "确定删除吗？",   //弹出框里面的提示文本
        type: "warning",        //弹出框类型
        showCancelButton: true, //是否显示取消按钮
        confirmButtonColor: "#DD6B55",//确定按钮颜色
        cancelButtonText: "取消",//取消按钮文本
        confirmButtonText: "是的，确定删除！",//确定按钮上面的文档
        closeOnConfirm: true
    }, function () {
        var selectedDocsArr = $("input[type='checkbox']:checked");
        var ids = "";
        if (selectedDocsArr.length > 0) {
            for (var i = 0; i < selectedDocsArr.length; i++) {
                var id = selectedDocsArr[i].value;
                if (id.length > 0) {
                    ids += selectedDocsArr[i].value + ",";
                }
            }
        }
        ids = ids.substring(0, ids.length - 1);

        $.ajax({
            url: Config.baseUrl + "/docs/deleteDocs",
            type: "POST",
            data: {
                _method: "DELETE",
                "nowDirId": dirPathArr[dirPathArr.length - 1],
                ids: ids
            },
            dataType: "json",
            success: function (result) {
                if (result.code === 200) {
                    freshFileList(dirPathArr[dirPathArr.length - 1]);
                    $("#delete-div").hide();
                    $("#rename-div").hide();
                    $("#move-div").hide();
                }
            }
        });
    });
}

/**
 * 预览文件
 *
 * @param path
 */
function doPreview(path) {
    $("#preview-modal").on("hidden.bs.modal", function () {
        $("#preview-view").html("");
    });
    var suffix = path.substring(path.lastIndexOf(".") + 1);
    if (["mp4", "avi"].indexOf(suffix.toLowerCase()) != -1) {
        var video = document.createElement("video");
        video.controls = true;
        video.preload = "auto";
        video.autoplay = true;
        var source = document.createElement("source");
        source.src = "/docs/getFileStream?path=" + path;
        source.type = "video/" + suffix;
        $(video).html(source).css({width: "100%"});
        $("#preview-view").html(video);
        $("#preview-modal").modal("show");
    }
    if (["jpg", "png"].indexOf(suffix.toLowerCase()) != -1) {
        var img = document.createElement("img");
        img.src = "/docs/getFileStream?path=" + path;
        $(img).css({maxWidth: '100%', maxHeight: '100%'});
        $("#preview-view").html(img).css({textAlign: 'center'});
        $("#preview-modal").modal("show");
    }
}

// 下载文件
function downloadDocs() {
    var selectedDocsArr = $("input[type='checkbox']:checked");
    var path = selectedDocsArr.data("path");
    var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //下面为在form表单中添加查询参数
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', "/docs/downLoad");
    var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'path');
    input1.attr('value', path);
    $('body').append(form);  //将表单放置在web中
    form.append(input1);   //将查询参数控件提交到表单上
    form.submit();   //表单提交
}

//重命名
function renameDocs() {
    var count = $("input[type='checkbox']:checked").length;
    if (count === 1) {
        var url = Config.baseUrl + "/docs/renameDocs";
        var params = {
            "docsId": $("input[type='checkbox']:checked")[0].value,
            "nowDirId": dirPathArr[dirPathArr.length - 1],
            "name": $("#new-name").val()
        };

        $.ajax({
            url: url,
            type: "POST",
            data: params,
            dataType: "json",
            success: function (result) {
                if (result.code === 200) {
                    freshFileList(dirPathArr[dirPathArr.length - 1]);
                    $("#new-name").val(null);
                    $("#delete-div").hide();
                    $("#rename-div").hide();
                    $("#move-div").hide();
                }
            }
        });
    }
}

//移动
function moveDocs() {
    //模态框加载数据
    var ids = getSelectedDocsIds();
    var data = getTree(ids);
    console.log(data);
    $('#dirTree').treeview({
        data: data,//节点数据
        expanded: false,//初始是否展开
        icon: "glyphicon glyphicon-file",
        levels: 2,//初始显示层数
        // icon: "glyphicon glyphicon-stop",
        // selectedIcon: "glyphicon glyphicon-stop",
        color: "#000000",
        backColor: "#FFFFFF",
        onNodeSelected: function (event, data) {
            nowSelectedNodeId = data.id;
            // console.log("you are choose me now :" + data.id);
            // openFile(data.id);
        }
    });
    //显示模态框
    $('#moveDocs-modal').modal();
}

//移动文件时，确认移动触发
function moveDocsConfirm() {
    console.log("ok");
    var ids = getSelectedDocsIds();
    var url = Config.baseUrl + "/docs/moveDocs";
    var params = {
        "ids": ids,
        "nowDirId": dirPathArr[dirPathArr.length - 1],
        "targetDirId": nowSelectedNodeId
    };

    $.ajax({
        url: url,
        type: "POST",
        data: params,
        dataType: "json",
        success: function (result) {
            if (result.code === 200) {
                freshFileList(dirPathArr[dirPathArr.length - 1]);
                $("#delete-div").hide();
                $("#rename-div").hide();
                $("#move-div").hide();
            }
        }
    });
}

//根据类型查询文件
function getDocsByType(num, e) {
    $("#upload-button").show();
    $("#new-file-button").show();
    $(".all-doc-button").removeClass("router-link-active");
    $(".sub-list a,.delete-doc-button").removeClass("router-link-active");
    $(e).addClass("router-link-active");
    var url = Config.baseUrl + "/docs/getDocsByType?type=" + num;
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code === 200) {
                var title;
                switch (num) {
                    case 0: {
                        title = "其他";
                        break;
                    }
                    case 1: {
                        title = "图片";
                        break;
                    }
                    case 2: {
                        title = "文档";
                        break;
                    }
                    case 3: {
                        title = "视频";
                        break;
                    }
                    case 4: {
                        title = "音频";
                        break;
                    }
                }
                console.log(title);
                $('.dir-path-info').html(title);
                fillUpTable(result.data);

            }
        }
    });
}

//搜索文件
function selectDocs() {
    var str = $("#search-input").val();
    if (str.length > 0) {
        var url = Config.baseUrl + "/docs/findDocs?name=" + str;
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result.code === 200) {
                    fillUpTable(result.data);
                }
            }
        });
    }
}

//右键重命名
function renameContext() {
    //判断选中的checkbox
    var count = $("input[type='checkbox']:checked").length;
    if (count === 1) {
        //显示模态框
        $('#renameDocs-modal').modal();
    }
}

//查询全部文件
function getAllDocs() {
    listType = 0;
    $("#upload-button").show();
    $("#new-file-button").show();
    $("#restore-div").hide();
    $(".file-table-title input").get(0).checked = false;
    $(".all-doc-button").addClass("router-link-active");
    $(".sub-list a,.delete-doc-button").removeClass("router-link-active");
    dirPathArr = [];
    pathNameArr = [];
    initTable();
    $('.dir-path-info').html("全部文件");
}

/**
 * 查看回收站
 */
function getDeleteFile() {
    listType = 1;
    $(".file-table-title input").get(0).checked = false;
    $("#upload-button").hide();
    $("#new-file-button").hide();
    $('.dir-path-info').html("");
    $("#restore-div").show();
    $(".delete-doc-button").addClass("router-link-active");
    $(".sub-list a,.all-doc-button").removeClass("router-link-active");
    dirPathArr = [];
    pathNameArr = [];
    getDeleteList();
}

/**
 * 获取已删除文件列表
 *
 */
function getDeleteList() {
    var url = Config.baseUrl + "/docs/getDeleteDocs";
    $.get(url, {}, function (result) {
        if (result.code === 200) {
            fillDeleteDocList(result.data.docsList);
            if (result.data.docsList.length && result.data.docsList.length > 0) {
                $(".empty-wrap").hide();
                $(".table-wrap").show();
            } else {
                $(".empty-wrap").show();
                $(".table-wrap").hide();
            }
        }
    });
}

/**
 * 执行还原文件操作
 *
 */
function restoreFile() {
    var selected = $(".file-list input[type='checkbox']:checked");
    var count = selected.length;
    if (count > 0) {
        var list = [];
        $.each(selected, function(index, item) {
            list.push(item.value);
        });
        $.ajax({
            url: Config.baseUrl + "/docs/restoreFile",
            type: "POST",
            data: {
                ids: list.join(",")
            },
            dataType: "json",
            success: function (result) {
                getDeleteList();
            }
        });
    }
}

//删除数组最后一个元素
function truncate(arr) {
    return arr.slice(0, -1);
}

function hidePopMenu() {
    $("#context-menu").hide();
}

function selectAndUnselectAll() {
    var inputArr = $('.file-list input');
    for (var i = 0; i < inputArr.length; i++) {
        inputArr[i].checked = $('.file-table-title input').get(0).checked;
    }
}

function newFolder() {
    var url = Config.baseUrl + "/docs/addDir";
    var params = {
        "nowDirId": dirPathArr[dirPathArr.length - 1],
        "addDirName": $("#new-file-name").val()
    };
    $.ajax({
        url: url,
        type: "POST",
        data: params,
        dataType: "json",
        success: function (result) {
            if (result.code === 200) {
                freshFileList(dirPathArr[dirPathArr.length - 1]);
            }
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
    var $clone = $(this).clone().css('display', 'block').appendTo('body');
    var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
    top = top > 0 ? top : 0;
    $clone.remove();
    $(this).find('.modal-content').css("margin-top", top);
}

function freshFileList(dirId) {
    listType = 0;
    $(".file-table-title input").get(0).checked = false;
    if (event && event.srcElement) {
        var clicked = $(event.srcElement).parents().closest('li').find("input")[0];
        if (clicked && $(clicked).data("type") != "1") {
            var path = $(clicked).data("path");
            doPreview(path);
            return;
        }
    }
    var url = Config.baseUrl + "/docs/getAllDocsByPid";
    var params = {
        "dirId": dirId,
        "userId": user.id
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
            if (dirPathArr[dirPathArr.length - 1] !== result.data.nowDir.id) {
                dirPathArr[dirPathArr.length] = result.data.nowDir.id;// 存储当前路径
                pathNameArr[result.data.nowDir.id] = result.data.nowDir.name;// 存储当前路径名
                freshDirPath();
            }
        }
    });

}

function freshFileListForOtherUser(dirId, userId) {
    var url = Config.baseUrl + "/docs/getAllDocsByPid";
    var params = {
        "dirId": 0,
        "userId": userId
    };
    $.get(url, params, function (result) {
        if (result.code === 200) {
            var url = Config.baseUrl + "/docs/getAllDocsByPid";
            var params = {
                "dirId": result.data.nowDir.id,
                "userId": userId
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
                    if (dirPathArr[dirPathArr.length - 1] !== result.data.nowDir.id) {
                        dirPathArr[dirPathArr.length] = result.data.nowDir.id;// 存储当前路径
                        pathNameArr[result.data.nowDir.id] = result.data.nowDir.name;// 存储当前路径名
                        freshDirPath();
                    }
                }
            });
        }
    });

}


function freshDirPath() {
    if (dirPathArr.length === 1) {
        $('.dir-path-info').html("全部文件");
    } else if (dirPathArr.length > 1) {
        var path = "<a onclick='toPath(-1)'>返回上一级</a><span>|</span><a onclick='toPath(0)'>全部文件</a>";
        dirPathArr.forEach(function (value, index) {
            if (index > 0) {
                if (index < dirPathArr.length - 1) {
                    path += "<span>\></span><a onclick='toPath(" + index + ")'>" + pathNameArr[dirPathArr[index]] + "</a>";
                    console.log("if : " + dirPathArr[index]);
                } else {
                    path += "<span>></span>" + pathNameArr[dirPathArr[index]];
                    console.log("else : " + dirPathArr[index]);
                }
            }
        });
        $('.dir-path-info').html(path);
    }
}

function toPath(index) {
    if (index === -1) {
        dirPathArr.pop();
    } else {
        dirPathArr = dirPathArr.splice(0, index + 1);
    }
    freshDirPath();
    freshFileList(dirPathArr[dirPathArr.length - 1]);
}

/**
 * 渲染回收站列表
 *
 * @param docsList
 */
function fillDeleteDocList(docsList) {
    var rows = "";
    docsList.forEach(function (doc) {
        var floderIcon = "<i class='glyphicon glyphicon-folder-close' style='color: rgb(255,214,89);margin-right: 8px;'></i>";
        var fileIcon = "<i class='glyphicon glyphicon-file' style='color: #eee;margin-right: 8px;'></i>";
        rows += "<li style='font-size: 13px;'><ul>" +
            "<li class='column-1'><input type='checkbox' value='" + doc.id + "' data-path='" + doc.path + "' data-type='" + doc.type + "' onchange='deleteBtnChange()'></li>" +
            "<li class='column-2'>" +
            "<span style='cursor: unset;'>" + (doc.type === 1 ? floderIcon : fileIcon) + doc.name + "</span>" +
            "</li>" +
            "<li class='column-3'>" + (doc.size ? doc.size : "-") + "</li>" +
            "<li class='column-4'>" + doc.createTime + "</li>" +
            "</ul></li>";
    });
    $(".file-table .file-list").html(rows);
    $(".file-list input").bind("click", function () {
        if (!$(this).get(0).checked) {
            $(".file-table-title input").get(0).checked = false;
        }
    });
}

function fillUpTable(docsList) {
    var rows = "";
    docsList.forEach(function (doc) {
        var floderIcon = "<i class='glyphicon glyphicon-folder-close' style='color: rgb(255,214,89);margin-right: 8px;'></i>";
        var fileIcon = "<i class='glyphicon glyphicon-file' style='color: #eee;margin-right: 8px;'></i>";
        rows += "<li style='font-size: 13px;'><ul>" +
            "<li class='column-1'><input type='checkbox' value='" + doc.id + "' data-path='" + doc.path + "' data-type='" + doc.type + "' onchange='checkboxChanged()'></li>" +
            "<li class='column-2 context'>" +
            "<span onclick='freshFileList(" + doc.id + ")'>" + (doc.type === 1 ? floderIcon : fileIcon) + doc.name + "</span>" +
            "</li>" +
            "<li class='column-3'>" + (doc.size ? doc.size : "-") + "</li>" +
            "<li class='column-4'>" + doc.createTime + "</li>" +
            "</ul></li>";
    });
    $(".file-table .file-list").html(rows);
    $(".context").contextmenu({
        target: '#context-menu',
        before: function () {
            console.log(event.srcElement.tagName);
            // execute code before context menu if shown
            var target = $(event.srcElement).parents().closest('li').find("input")[0];
            if (!target.checked) {
                var inputArr = $('.column-1 input');
                for (var i = 0; i < inputArr.length; i++) {
                    inputArr[i].checked = false;
                }
                target.checked = true;
            }
            var selectedDocsArr = $("input[type='checkbox']:checked");
            var count = selectedDocsArr.length;
            if (count > 1) {
                $("#renameContext").hide();
                $("#previewContext").hide();
                $("#downloadContext").hide();
            } else if (count === 1) {
                $("#renameContext").show();
                var path = selectedDocsArr.data("path");
                var type = selectedDocsArr.data("type");
                if (type != "1") {
                    $("#downloadContext").show();
                } else {
                    $("#downloadContext").hide();
                }
            }
        },
        onItem: function (context, e) {
            // execute on menu item selection
            // console.log(e.target);
        }
    });
    $(".file-list input").bind("click", function () {
        if (!$(this).get(0).checked) {
            $(".file-table-title input").get(0).checked = false;
        }
    });
}

function initTable() {
    var url = Config.baseUrl + "/docs/getAllDocsByPid";
    var params = {
        "dirId": 0,
        "userId": user.id
    };
    $.get(url, params, function (result) {
        if (result.code === 200) {
            freshFileList(result.data.nowDir.id);

        }
    });
}