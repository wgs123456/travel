/**
 * 校验用户名不为空
 */
function checkUsername() {
    var username = $("#username").val();
    if (username == ""){
        $("#username").css("border","solid 1px red");
        return false;
    }else {
        $("#username").css("border","");
        return true;
    }
}
/**
 * 校验密码不为空
 */
function checkPassword() {
    var password = $("#password").val();
    if (password == ""){
        $("#password").css("border","solid 1px red");
        return false;
    }else {
        $("#password").css("border","");
        return true;
    }
}

$(function () {
    $("#btn_login").click(function () {
        if (checkUsername() && checkPassword()){
            $.post("user/login",$("#loginForm").serialize(),function (data) {
                if (data.flag){
                    // 登陆成功
                    location.href="index.html";
                    // $("#errorMsg").html(data.errorMsg);
                }else {
                    //登陆失败
                    $("#checkCode").attr("src","checkCode?"+new Date().getTime());
                    $("#errorMsg").html(data.errorMsg);
                }
            },"json")
        }else {
            $("#errorMsg").html("请输入用户名和密码");
        }
    });

    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
});