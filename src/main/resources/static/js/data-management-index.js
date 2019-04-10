$(document).ready(function () {
    $('#sign-up').bind("click", signUp);
    $('#user-name').bind("keydown", toSignUp);
    $('#password').bind("keydown", toSignUp);
    // showUserDiv();
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