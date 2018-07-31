<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../base.jsp"%>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1"/>
    <title>元翔专车</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <style>
        .bg-img {
            width: 100%;
        }

        #container {
            position: relative;
        }

        .input-container {
            position: absolute;
            bottom: 10%;
            left: 50%;
            width: 250px;
            margin-left: -125px;
        }

        .common {
            font-size: 18px;
            display: block;
            width: 100%;
            margin: 10px auto;
            border-radius: 23px;
            text-align: center;
            line-height: 45px;
            height: 45px;
        }

        .input-tel-num {
            box-shadow: none;
            border: none;
        }

        .btn-get-gift {
            border: 5px solid #ffaab1;
            line-height: 36px;
            color: #fff;
            text-decoration: none;
            margin-top: 20px;
        }

        .btn-get-gift:link,
        .btn-get-gift:visited,
        .btn-get-gift:hover,
        .btn-get-gift:active {
            color: #fff;
            text-decoration: none;
        }

        .layer-gift {
            -webkit-border-radius: 10px !important;;
            -moz-border-radius: 10px !important;;
            border-radius: 10px !important;
        }

        .layer-gift .layui-layer-title {
            text-align: center;
            padding: 0px;
            color: #fff;
            background-color: #c91520;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            font-size: 16px;
        }

        .layer-gift .vcode-container {
            width: 90%;
            margin: 0 auto;
            margin-top: 10px;
        }

        .layer-gift .line-one {
            line-height: 40px;
            text-align: center;
            margin: 20px 0px;
        }

        .layer-gift .line-one input {
            border: 1px solid #d7d7d7;
            box-shadow: none;
            color: #9c9b9b;
            font-size: 16px;
            padding-left: 10px;
            width: 60%;
            -webkit-appearance: none;
            outline: none;
            border-radius: 0px;
            height: 40px;
            line-height: 40px;
        }

        .layer-gift .line-one a {
            padding: 0px 10px;
            border: 1px solid #d7d7d7;
            line-height: 38px;
            height: 40px;
            display: inline-block;
            border-left: none;
            color: #595757;
            font-size: 14px;
            width: 40%;
        }

        .layer-gift .line-two {
            text-align: center;
            margin-top: 10px;
            line-height: 35px;
        }

        .layer-gift .line-two a {
            display: inline-block;
            width: 80px;
            text-align: center;
            background-color: #c91520;
            color: #fff;
            font-size: 16px;
            border-radius: 5px;
            text-decoration: none;
        }

        .layer-gift .line-tip {
            margin-bottom: 0px;
            line-height: 30px;
            height: 30px;
            text-align: center;
            font-size: 16px;
            color: #696767;
        }

    </style>
</head>

<body>

<div id="container">
    <div class="">
        <img class="bg-img" src="${base}/pub/images/wx/activity.png" alt="">
    </div>
    <div class="input-container">
        <input type="tel" class="common input-tel-num" placeholder="输入手机号码" maxlength="11">
        <a class="common btn-get-gift" href="javascript:;">领取礼包</a>
    </div>
</div>

</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.md5.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript">
    //    var winHeight = $(window).height();
    //    $('.bg-img').css('height', winHeight);
    function verifyCode() {
        var activityUuid = 'f2530da788f14e36b2cdf40c87ac925f';
        var $code = $('#input_vcode');
        var identifyCode = $code.val();
        if (isNaN(identifyCode)) {
            layer.msg('验证码格式错误！');
            $code.val('');
            return;
        }
        if (identifyCode === "") {
            layer.msg('验证码不能为空！');
            return;
        }
        var data = {
            mobile: $('.input-tel-num').val(),
            identifyCode: identifyCode,
            activityUuid: activityUuid
        }
        $.post('/activity/hero/drawRedPack.arport', data, function (res) {
            if (res.success) {
                layer.closeAll();
                successCall(100);
            } else {
                layer.msg(res.msg);
            }
        });
    }

    // 获取短信签名
    function getLoginSign(mobile, noncestr) {
        var key = "ARPORT912012KEY33338475";
        var sign = "mobile=" + mobile + "&noncestr=" + noncestr + "&key=" + key;
        sign = $.md5(sign).toUpperCase();
        return sign;
    }

    function countTime($this) {
        var t = 60;
        var a = setInterval(daojishi, 1000);//1000毫秒
        function daojishi() {
            t--;
            //刷新时间显示
            if (t == 0) {
                //倒计时结束
                clearInterval(a);
                $this.text('重发');
                $this.data('click', true);
            } else {
                $this.data('click', false);
                $this.text(t + "秒");
            }
        }
    }

    function sendVcode(obj) {
        var $this = $(obj);
        var isClick = $this.data('click');
        var noncestr = new Date().getTime();
        var mobile = $('.input-tel-num').val();
        if (mobile === "") {
            layer.msg('手机号码不能为空！');
            return;
        }
        var sign = getLoginSign(mobile, noncestr);
        if (isClick) {
            countTime($this);
            $.get('/activity/hero/sendCode.arport?mobile=' + mobile + "&noncestr=" + noncestr + "&sign=" + sign, function (res) {
                layer.msg(res.msg);
            });
        } else {
            layer.msg('不要重复点击哦！');
        }
    }

    function successCall(money) {
        layer.open({
            type: 1,
            title: "请输入验证码",
            closeBtn: 0,
            shadeClose: true,
            skin: 'layer-gift',
            area: ['80%', '180px'],
            content: '<div class="vcode-container">' +
            '<p class="line-tip">' + money + '元优惠券已放入您的账户</p>' +
            '<p class="line-tip">送机可赠送头等舱通道服务</p>' +
            '<p class="line-two clearfix">' +
            '<a class="pull-left" onclick="verifyCode();" href="javascript:layer.closeAll();">确定</a>' +
            '<a class="pull-right" href="https://www.yxzc01.com/api/base/appDownload">APP下载</a>' +
            '</p>' +
            '</div>'
        });
    }

    // 获取验证码
    $('.btn-get-gift').on('click', function (event) {
        var mobile = $('.input-tel-num').val();
        if (mobile === "") {
            layer.msg('手机号码不能为空！');
            return;
        }
        layer.open({
            type: 1,
            title: "请输入验证码",
            //closeBtn: 0,
            shadeClose: true,
            skin: 'layer-gift',
            area: ['80%', '180px'],
            content: '<div class="vcode-container">' +
            '<p class="line-one">' +
            '<input type="text" maxlength="4" id="input_vcode" placeholder="请输入验证码">' +
            '<a data-click="true" onclick="sendVcode(this);" href="javascript:;">获取验证码</a>' +
            '</p>' +
            '<p class="line-two clearfix">' +
            '<a class="pull-left" onclick="verifyCode();" href="javascript:;">确定</a>' +
            '<a class="pull-right" href="javascript:layer.closeAll();">取消</a>' +
            '</p>' +
            '</div>'
        });
    });

    $(document).on('keyup', '#input_vcode', function (event) {
        var $this = $(this);
        var value = $this.val();
        if (value.length > 4) {
            layer.msg('验证码长度错误！');
            value = value.slice(0, 4);
            $this.val(value);
            return;
        }
    });

</script>

</html>