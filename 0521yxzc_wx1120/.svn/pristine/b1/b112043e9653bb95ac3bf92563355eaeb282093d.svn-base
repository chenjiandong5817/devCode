<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../base.jsp"%>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <meta http-equiv="expires" content="0">
    <title>元翔专车</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/login.css?v=${editions}">
    <style>
        .login-form .sixtycount {
            color: #d0d5d9 !important;
        }

        .login-form .completeCount {
            color: #00b5e6 !important;
        }

        .validate:active, .validate:visited {
            color: #d0d5d9;
            text-decoration: none;
        }
    </style>
</head>
<body style="background-color: #fff;">
<div class="container-fluid">
    <form class="login-form">
        <ul>
            <li class="form-li">
                <img class="img" src="${base}/pub/images/wx/login_phone.png">
                <input id="mobileText" type="tel" maxlength="11" class="form-controller" placeholder="请输入手机号">
                <a id="btn-validate" class="validate completeCount" href="javascript:void(0);">验证</a>
            </li>
            <li class="form-li">
                <img class="img" src="${base}/pub/images/wx/login_vertify.png">
                <input id="identifyCodeText" maxlength="4" type="number" class="form-controller" placeholder="请输入验证码">
            </li>
        </ul>
        <a class="btn-ok btn-ok-disable" href="#">确定</a>
        <p class="text-center">
            <a class="checkedBox" href="javascript:void(0);">
                <i data-check="true" class="check-icon login-checked"></i>
                <span>我接受元翔专车用户协议</span>
            </a>
        </p>
    </form>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.md5.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">
    jQuery(document).ready(function ($) {
        window.isLoginPage=true;
        var openId = getLocalStorage('openId');
        var nickName = getLocalStorage('nickName');
        var sex = getLocalStorage('wxUserSex');

        var $document = $(document);

        $document.on('touchend', '.checkedBox', function (event) {

            parent.window.location.href = "/api/base/userAgreement";
        });

        $document.on('keyup', '#identifyCodeText', function (event) {
            var $this = $(this);
            var code = $this.val();
            if (isNaN(code) || code.length > 4) {
                layer.msg('验证码输入错误！');
                $this.val('').focus();
                validateIsNull();
                return false;
            }
            validateIsNull();
        });

        $document.on('keyup', '#mobileText', function (event) {
            validateIsNull();
        });

        function validateIsNull() {
            var mobile = $("#mobileText").val();
            var code = $("#identifyCodeText").val();
            if (mobile === "" || code === "") {
                $('.btn-ok').addClass('btn-ok-disable').removeClass('btn-ok-able');
            } else {
                $('.btn-ok').removeClass('btn-ok-disable').addClass('btn-ok-able');
            }
        }

        $document.on('touchend', '.btn-ok-able', function () {
            var $agreePro = $('.check-icon'),
                flag = $agreePro.data('check');
            if (!flag) {
                layer.msg('请勾选用户协议！');
                return;
            }

            // 登录动作
            var data = {
                mobile: $("#mobileText").val(),
                nickName: nickName,
                sex: sex,
                identifyCode: $("#identifyCodeText").val(),
                openId: openId
            };
            $.ajax({
                type: "POST",
                url: "/wechat/passenger/login",
                dataType: "json",
                data: data,
                success: function (res) {
                    if (res.success) {
                        // 关闭窗口
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                        parent.$('.closeExtraDiv').remove();
                        //提示登陆成功并且已和该手机号绑定
                        layer.msg("登录成功");
                        window.location.href="/wechat/view/index"
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
        });

        // 获取短信签名
        function getLoginSign() {
            var key = "ARPORT912012KEY";
            var sign = "openId=" + openId + "&key=" + key;
            sign = $.md5(sign).toUpperCase();
            return sign;
        }

        //发送验证码动作
        var clickCount = 0;
        $document.on('touchend', '.completeCount', function (event) {
            event.stopPropagation();
            var $this = $(this);
            var mobile = $("#mobileText").val();
            if (mobile === "") {
                layer.msg("请先输入手机号");
                return;
            }
            if (!checkPhone(mobile)) {
                $("#mobileText").focus().val('');
                layer.msg("请输入正确的手机号！");
                return;
            }
            countTime($this);
            var sign = getLoginSign();
            if (clickCount == 0) {
                $.ajax({
                    type: "POST",
                    url: "/wechat/passenger/sendCode",
                    dataType: "json",
                    data: {
                        mobile: mobile,
                        sign: sign,
                        openId: openId
                    },
                    success: function (res) {
                        if (!res.success) {
                            clickCount=0
                            layer.msg(res.msg);
                        }
                    },
                    error:function(){
                        layer.msg('请求出错');
                        clickCount=0
                    }
                });
            }
            clickCount++;
        });

        function countTime($this) {
            var t = 60;
            var a = setInterval(daojishi, 1000);//1000毫秒
            function daojishi() {
                t--;
                //刷新时间显示
                if (t == 0) {
                    //倒计时结束
                    clearInterval(a);
                    $this.text('重发').removeClass('sixtycount').addClass('completeCount');
                } else {
                    $this.addClass('sixtycount').removeClass('completeCount');
                    $this.text(t + "秒");
                }
            }
        }

        // checkbox选中事件
        $document.on('touchend', '.check-icon', function (event) {
            event.stopPropagation();
            var $this = $(this);
            var checked = $this.data('check');
            $this.data('check', !checked);
            if (checked) {
                $this.addClass('login-unchecked').removeClass('login-checked');
            } else {
                $this.removeClass('login-unchecked').addClass('login-checked');
            }
        });

    });
</script>
</html>