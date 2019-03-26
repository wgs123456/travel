//检验用户名
function checkUsername() {
    var val = $("#username").val();
    var reg_username = /^\w{6,12}$/;
    var boolean = reg_username.test(val);
    if (boolean){
        //格式正确
        $("#username").css("border","");
    }else {
        //格式错误:设置边框为红色
        $("#username").css("border","solid 1px red");
    }
    return boolean;
}

//校验密码
function checkPassword() {
    var val = $("#password").val();
    var reg_password = /^\w{6,12}$/;
    var boolean = reg_password.test(val);
    if (boolean){
        //格式正确
        $("#password").css("border","");
    }else {
        //格式错误:设置边框为红色
        $("#password").css("border","solid 1px red");
    }
    return boolean;
}
//校验邮箱
function checkEmail() {
    var val = $("#email").val();
    var reg_email = /^[0-9a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$/;
    var boolean = reg_email.test(val);
    if (boolean){
        //格式正确
        $("#email").css("border","");
    }else {
        //格式错误:设置边框为红色
        $("#email").css("border","solid 1px red");
    }
    return boolean;
}
//校验姓名  字母开头 + 数字/字母/下划线
function checkName() {
    var val = $("#name").val();
    var reg_name = /^[\u0391-\uFFE5]+$/;
    var boolean = reg_name.test(val);
    if (boolean){
        //格式正确
        $("#name").css("border","");
    }else {
        //格式错误:设置边框为红色
        $("#name").css("border","solid 1px red");
    }
    return boolean;
}
//校验手机号   11位
function checkTelephone() {
    var val = $("#telephone").val();
    var reg_telephone = /^1[3|4|5|8][0-9]\d{8}$/;
    var boolean = reg_telephone.test(val);
    if (boolean){
        //格式正确
        $("#telephone").css("border","");
    }else {
        //格式错误:设置边框为红色
        $("#telephone").css("border","solid 1px red");
    }
    return boolean;
}
$(function () {
    $("#registerForm").submit(function () {
        if (checkUsername() && checkPassword() &&checkEmail() && checkName() &&checkTelephone()){
            $.post("user/regist",$(this).serialize(),function (data) {
                //  alert(data.errorMsg);
                if (data.flag){
                    //保存成功
                    location.href="register_ok.html";
                }else {
                    $("#checkCode").attr("src","checkCode?"+new Date().getTime());
                    $("#err_msg").html(data.errorMsg);
                }
            },"json");


        }
        // //每次点击提交之后，异步请求新的验证码
        // $.get("checkCode",function (data) {
        //     $("#img_check").src="checkCode?"+new Date().getTime();
        // });
        return false;
        //return checkUsername() && checkPassword() &&checkEmail() && checkName() &&checkTelephone();
    });
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkTelephone);
});