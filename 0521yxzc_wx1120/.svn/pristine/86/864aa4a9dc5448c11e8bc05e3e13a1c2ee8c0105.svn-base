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
    <title>等待应答</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
</head>
<body>
<div class="container-fluid">
    <div class="waiting-reply">
        <img class="img" src="${base}/pub/images/wx/reply.gif">
        <div class="text">
            <p id="timing">00:00</p>
            <p>订单推送中</p>
            <p>请稍候...</p>
        </div>
    </div>
    <div id="tempContainer" class="divBox router-info">
        <p class="driver-route-info">
            <img class="img img1" src="${base}/pub/images/wx/time.png">
            <span>${orderMap.orderDeparTime}</span>
        </p>
        <p class="driver-route-info">
            <img class="img img2" src="${base}/pub/images/wx/qidian.png">
            <span id="originDetailAddressText">${orderMap.orderOriAddress}</span>
        </p>
        <p class="driver-route-info">
            <img class="img img3" src="${base}/pub/images/wx/zhongdian.png">
            <span id="destDetailAddressText">${orderMap.orderDestAddress}</span>
        </p>
    </div>
    <a class="cancelBox" href="javascript:void(0);">
        <img src="${base}/pub/images/wx/cancel.png">
        <p class="text">取消订单</p>
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<%--<script type="text/javascript" src="/pub/js/wx/template-web.js"></script>--%>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>

<script type="text/javascript">
    jQuery(document).ready(function ($) {
        var isAjax=false;
        var $document = $(document);

        // 浏览器回退监听
        ListenBrowerBack();

        $document.on('touchend', '.cancelBox', function (event) {

            layer.confirm('正在为您寻找车辆，再耐心等待一会儿吧', {
                title: "取消叫车",
                btn: ['不等了', '再等一会'] //按钮
            }, function (index) {
                cancelOrder();
                layer.close(index);
            }, function (index) {
                layer.close(index);
            });
        });

        //定时请求OrderDetail，获取订单详情，当有司机接单的时候，自动跳转到、yue/wmeeting
        setInterval(getOrderStatus, 5000);

        // 获取订单详情
        function getOrderStatus() {
            //获取刚才派出的订单ID
//            var orderUuid = getLocalStorage('currentOrderId');
//            var openId = getLocalStorage('openId');
            var orderUuid = "${orderMap.orderUuid}";
            var openId = getLocalStorage('openId');
            setLocalStorage('currentOrderId',orderUuid);
            $.ajax({
                type: "POST",
                url: "/wechat/order/orderResponse",
                dataType: "json",
                data: {uuid: orderUuid, openId: openId},
                success: function (res) {
//                    alert(JSON.stringify(res))
                    if (res.success) {
                        var uuid = res.data.uuid;
                        var status = res.data.subStatus;
                        var prepaidFee= + '${orderMap.prepaidFee}';//预估费用
                        var prepaidStatus=+ '${orderMap.prepaidStatus}';//【0：初始化 1：待支付 2：支付中 3：已支付 4:支付异常 】
                        if (status != 100 ) {
                            //处理预支付订单跳转预支付页面
                            if(status==201 || status ==200){
                                if ((0 < parseInt(prepaidFee)) &&  prepaidStatus<3) {
                                    window.location.href="/wechat/order/wprepayment/" + openId + "/" + "${orderMap.orderUuid}";
                                    return;
                                }
                            }
                            changeUrl(status, uuid);
                        }
                    }
                }
            });
        }

        // 三分钟计时，等待接单
        var ele_timer = document.getElementById("timing");
        var n_sec = 0; //秒
        var n_min = 0; //分
        var n_timer = timer();

        function timer() {
            return setInterval(function () {
                var str_sec = n_sec;
                var str_min = n_min;
                if (n_sec < 10) {
                    str_sec = "0" + n_sec;
                }
                if (n_min < 10) {
                    str_min = "0" + n_min;
                }
                var time = str_min + ":" + str_sec;
                ele_timer.innerHTML = time;
                n_sec++;
                if (n_sec > 59) {
                    n_sec = 0;
                    n_min++;
                }
                if (n_min > 2) {
                    n_sec = 0;
                    clearInterval(n_timer);
                    confirmDialog();
                }
            }, 1000);
        }

        function countDown() {
            var t = 6;
            var a = setInterval(daojishi, 1000);//1000毫秒
            function daojishi() {
                t--;
                //刷新时间显示
                if (t == 0) {
                    //倒计时结束
                    clearInterval(a);
                    window.location.href = "/wechat/view/index";
                } else {
                    $("#countDown").text(t);
                }
            }
        }

        // 三分钟无人应答是弹出询问是否继续等待
        var confirmDialog = function () {
            isAjax=true;
            layer.confirm('暂时没有司机应答，您要取消本次叫车吗？', {
                title: "取消叫车",
                btn: ['重新叫车', '取消叫车(<span id="countDown"></span>)'] //按钮
            }, function (index) {
                window.location.reload(true);
                layer.close(index);
            }, function (index) {
                window.location.href = "/wechat/view/index";
                cancelOrder();
                layer.close(index);
            });
            countDown();
        }

    });
</script>
</html>