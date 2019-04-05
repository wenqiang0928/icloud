var User={
    addUser:function () {
        var password=$("#password").val();
        var confirm_password=$("#confirm_password").val();
        if (password!=confirm_password){
            swal({
                title: "",
                text: "密码删除不一致",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            });
            return
        }
        var url = Config.baseUrl + "/userAdd";
        var params = {
            "name": $("#name").val(),
            "password": $("#password").val(),
            "alarm":$("#alarm").val()
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