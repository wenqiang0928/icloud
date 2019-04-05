$(document).ready(function () {
    $("#sign-up").bind("click", signUp);
    $('#user-name').bind("keydown", toSignUp);
    $('#password').bind("keydown", toSignUp);
    $('#addFile').bind("click",addFile)
    showUserDiv();
})

function addFile() {
    
}

function toSignUp(event) {
    if(event.keyCode == "13") {
        if (!$("#user-name").val()) {
            return;
        }
        if (!$("#password").val()) {
            return;
        }
        signUp();
    }
}

function signUp() {
    var url = Config.baseUrl + "/login";
    var params = {
        "name": $("#user-name").val(),
        "password": $("#password").val()
    };
    $.post(url, params, function (res) {
        if (res.code==200){
            if (res.data==true){
                window.location.href=Config.baseUrl+"/home"
            } else {
                window.location.reload();
            }
        } else{
            window.location.reload();
        }
    });
}

function showUserDiv() {
    $("#guideTarget2").click(function () {
        $(".vip-info yp-header__avatarWrap__vip").show();
    });
}

function uploadModal() {

}

function centerModals() {
    var $clone = $(this).clone().css('display','block').appendTo('body');
    var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
    top = top > 0 ? top : 0;
    $clone.remove();
    $(this).find('.modal-content').css("margin-top", top);
}