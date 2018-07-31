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
    <title>取消行程</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css">
    <style type="text/css">
        .divBox {
            width: 100%;
            border: none;
            box-shadow: none;
            margin-top: 0px;
            background-color: inherit;
        }

        .paymoney {
            border-bottom: 1px solid #f0f2f4;
            padding-top: 20px;
            margin-bottom: 100px;
        }

        .btn-pay:focus, .btn-pay:hover {
            color: #fff;
            text-decoration: none;
        }

        #btn_wx_cancel_pay {
            background-color: inherit;
            color: #7a7c81;
            border: 1px solid #7a7c81;
        }

        .paymoney .tips {
            color: #a8abaf;
            font-size: 14px;
            margin-bottom: 25px;
            margin-top: 50px;
        }

        .paymoney .tips1 {
            color: #a8abaf;
            font-size: 16px;
            margin-top: 50px;
            margin-bottom: 50px;
        }

    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox router-info">
        <div class="paymoney">
            <p class="text-center tips">取消费</p>
            <p class="text-center">
                <span style="font-size: 28px;" id="costMoney">0</span>元
            </p>
            <p class="text-center tips1">当前取消需支付<span id="costMoney1">0</span>元取消费</p>
        </div>
    </div>

    <a id="btn_wx_cancel_pay" class="btn-pay" href="javascript:void(0);">
        暂不取消
    </a>
    <a id="btn_wx_pay" class="btn-pay" href="javascript:void(0);">
        提交
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.md5.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>

<script type="text/javascript">

    var paycost = getQueryString("money"),
        orderUuid = getQueryString("orderUuid");
    $('#costMoney').text(paycost);
    $('#costMoney1').text(paycost);

    $(document).ready(function ($) {

        var $document = $(document);

        // 提交
        $document.on('touchend', '#btn_wx_pay', function (event) {

            cancelOrder();
        });

        // 暂不取消
        $document.on('touchend', '#btn_wx_cancel_pay', function (event) {

            window.history.go(-1);
        });
    });


</script>
</html>