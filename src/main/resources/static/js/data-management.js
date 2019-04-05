$(document).ready(function () {
    $("#sign-up").bind("click", signUp);
    $('#user-name').bind("keydown", toSignUp);
    $('#password').bind("keydown", toSignUp);
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
    var url = "/login";
    var params = {
        "name": $("#user-name").val(),
        "password": $("#password").val()
    };
    $("body").load(url, params);
}