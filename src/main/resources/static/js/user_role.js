var User={
    addUser:function () {
        var password=$("#new_password").val();
        var confirm_password=$("#confirm_password").val();
        if (password!=confirm_password){
            alert("密码删除不一致")
            return
        }
        var url = Config.baseUrl + "/userAdd";
        var params = {
            "name": $("#new_name").val(),
            "password": $("#new_password").val(),
            "alarm":$("#new_alarm").val(),
            "roleId":$("#roleId").val()
        };
        $.post(url, params, function (data) {
            if (data.code==200){
                alert(data.data)
            }
        });
    },
    delUser:function(){
        var url = Config.baseUrl + "/delUser";
        var params = {
            "name": $("#del_name").val(),
            "alarm":$("#del_alarm").val()
        };
        $.post(url, params, function (data) {
            if (data.code==200){
                alert(data.data)
            }
        });
    },
    getUsers:function () {
        var url = Config.baseUrl + "/getUsers";
        var params = {
            "name": $("#name").val(),
            "password": $("#password").val(),
            "alarm":$("#alarm").val()
        };
        $.post(url, params, function (data) {

        });
    }
};