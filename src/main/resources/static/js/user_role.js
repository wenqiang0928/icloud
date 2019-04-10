var User = {
    addUser: function () {
        var password = $("#new_password").val();
        var confirm_password = $("#confirm_password").val();
        if (password != confirm_password) {
            alert("密码删除不一致")
            return
        }
        var url = Config.baseUrl + "/userAdd";
        var params = {
            "name": $("#new_name").val(),
            "password": $("#new_password").val(),
            "alarm": $("#new_alarm").val(),
            "roleId": $("#roleId").val()
        };
        $.post(url, params, function (data) {
            if (data.code == 200) {
                alert(data.data)
            }
        });
    },
    delUser: function () {
        var url = Config.baseUrl + "/delUser";
        var params = {
            "name": $("#del_name").val(),
            "alarm": $("#del_alarm").val()
        };
        $.post(url, params, function (data) {
            if (data.code == 200) {
                alert(data.data)
            }
        });
    },
    getUsers: function () {
        var url = Config.baseUrl + "/getUsers";
        var params = {
            "name": $("#name").val(),
            "password": $("#password").val(),
            "alarm": $("#alarm").val()
        };
        $.post(url, params, function (data) {

        });
    },
    modifyPassword: function () {
        if(checkPassNotNull()) {
            var url = Config.baseUrl + "/modifyPassword";
            var params = {
                "oldPassWord": $("#old_password").val(),
                "modifyPassword": $("#modify_password").val()
            };
            $.ajax({
                url: url,
                type: "POST",
                data: params,
                dataType: "json",
                success: function (result) {
                    if (result.code === 200) {
                        alert("密码修改成功，请重新登录");
                        window.location.href = "/";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    }
};

function checkPassNotNull() {
    var old_password = $("#old_password").val();
    var modify_password = $("#modify_password").val();
    var confirm_modify_password = $("#confirm_modify_password").val();
    if (old_password.replace(/(^s*)|(s*$)/g, "").length === 0) {
        alert("请输入原密码");
        name.focus();
        return false;
    } else if (modify_password.replace(/(^s*)|(s*$)/g, "").length === 0) {
        alert("请输入新密码");
        return false;
    } else if (confirm_modify_password.replace(/(^s*)|(s*$)/g, "").length === 0) {
        alert("请确认新密码");
        return false;
    }
    if (modify_password !== confirm_modify_password) {
        alert("两次输入密码不一致");
        return false;
    } else {
        return true;
    }
}