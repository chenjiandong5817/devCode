<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <meta http-equiv="expires" content="0">
    <title>支付失败</title>
    <%@include file="../base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
</head>

<body>
    <div class="container-fluid">
        <div class="addContent">
            <div class="width80">
                <p class="title mar-t20">支付失败</p>
                <p>
                    <img src="${base}/pub/images/wx/pay_icon_fail.png" alt="" class="payErrImg">
                </p>
                <p class="spanMsg font-16 mar-t20">
                    非常抱歉，您的微信支付失败，请尝试其他支付方式
                </p>
                <a id="PayBtn" class="styleBtn mar-t50" href="javascript:void(0);">重新支付</a>
                <a id="btn_wx_cancel_pay" class="styleBtn normal mar-t20" href="javascript:void(0);">取消支付</a>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/imgUtils.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">
(function() {
    // 取消支付
    $('#btn_wx_cancel_pay').on('click', function() {
        var payTokenId = getLocalStorage('openId', 'string'),
            orderUuid = getQueryString("orderUuid");
        $.ajax({
            type: "POST",
            url: "${base}/wechat/order/orderResponse",
            dataType: "json",
            data: { uuid: orderUuid, tokenId: payTokenId },
            success: function(res) {
                if (res.success) {
                    var uuid = res.data.uuid;
                    var status = res.data.subStatus;
                    changeUrl(status, uuid)
                }
            }
        });
    });

    $('#PayBtn').on('click', function() {
        var tokenId = getLocalStorage('openId'),
            orderUuid = getQueryString("orderUuid"),
                money = getQueryString("money"),
        type = getQueryString("type");
//        window.location.href = "/wechat/order/paydialog/" + openId + "/" + orderUuid;

        window.location.href="/wechat/view/paydialog?money=" +money+ '&orderUuid='+orderUuid+'&type='+type;
    })
})();
</script>

</html>