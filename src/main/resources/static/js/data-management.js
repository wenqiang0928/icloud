$(document).ready(function () {
    $("#sign-up").bind("click", signUp);
})

function signUp() {
    var url = Config.baseUrl + "/login";
    var params = {
        "name": $("#user-name").val(),
        "password": $("#password").val()
    };
    $.post(url, params, function () {
        window.location.href = Config.baseUrl + "/home";
    });
}