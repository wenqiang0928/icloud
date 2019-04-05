$(document).ready(function () {
    $("#sign-up").bind("click", signUp);
    $('#user-name').bind("keydown", toSignUp);
    $('#password').bind("keydown", toSignUp);
    showUserDiv();
})

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
    $.post(url, params, function () {
        $("body").load(url, params);
    });
}

function showUserDiv() {
    $("#guideTarget2").click(function () {
        $(".vip-info yp-header__avatarWrap__vip").show();
    });
}