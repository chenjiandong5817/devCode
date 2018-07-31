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
    <title>输入手机号</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/login.css?v=${editions}">
    <style type="text/css">
        .btn-div .btn-ok {
            display: inline-block;
            width: 100%;
            left: 0px;
            bottom: 0px;
            margin: 0;
            border-radius: 0;
            margin-top: 22px;
            text-align: center;
            color: #fff;
            height: 45px;
            line-height: 45px;
            font-size: 17px;

            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
        }

        .btn-div .btn-disable {
            background-color: #d0d5d9;
        }

        .btn-div .btn-able {
            background-color: #4f5a67;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <form style="padding: 10px 20px;" class="login-form">
        <ul>
            <li class="form-li">
                <input id="telphoneText" maxlength="11" style="width: 100%;" type="tel" class="form-controller"
                       placeholder="请输入手机号码">
            </li>
        </ul>
    </form>
    <div class="btn-div">
        <a class="btn-ok btn-disable" href="#">确定</a>
    </div>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">
    jQuery(document).ready(function ($) {

        var $document = $(document);

        function closeWin() {
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
            parent.$('.closeExtraDiv').remove();
        }

        $document.on('keyup', '#telphoneText', function (event) {
            var $this = $(this);
            var value = $this.val();
            if (value.length) {
                $('.btn-ok').addClass('btn-able').removeClass('btn-disable');
            } else {
                $('.btn-ok').removeClass('btn-able').addClass('btn-disable');
            }
        });

        $document.on('touchend', '.btn-able', function () {
            // 获取乘车人手机号码，调用后台方法存入session中  "actualMobile"
            var mobile = $("#telphoneText").val();
            if (!checkPhone(mobile)) {
                $("#telphoneText").focus().val('');
                //parent.$('#changeCustomerSpan').text("换乘车人");
                //closeWin();
                return;
            }
            $.post("${base}/wx/Order/saveActualMobile", {actualMobile: mobile}, function (result) {
                if (result.success) {
                    parent.$('#changeCustomerSpan').text("尾号" + mobile.substring(7));
                    closeWin();
                }
            });

        });


    });
</script>
</html>