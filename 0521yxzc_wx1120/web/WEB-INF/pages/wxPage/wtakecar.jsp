<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>待上车</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style>
        .layui-layer-title {
            font-size: 18px !important;
            color: #4b4e51 !important;
            margin-top: 25px;
            margin-bottom: 10px;
            padding: 0px !important;
        }
        .layui-layer-dialog .layui-layer-content{
            height: 80px !important;
            padding:0 !important;
            margin: 0px 10px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox">
        <div class="driverMsg">
            <div class="driverImg">
                <c:choose>
                    <c:when test="${driverMap.driverFace != null}">
                        <img src="${driverMap.driverFace}">
                    </c:when>
                    <c:otherwise>
                        <img src="${base}/pub/images/wx/man.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="driverDetail">
                <div>
                    <span class="driver_name">${driverMap.driverName}</span>
                    <span class="driver_rate">${driverMap.driverScore}</span>
                    <!-- 司机订单数暂未开启 -->
                    <!-- <span class="driver_order">134单</span> -->
                </div>
                <div>
                    <span class="car_id">${driverMap.driverCarNo}</span>
                    <span class="car_msg">${driverMap.driverCarColor}${driverMap.driverBrandName}</span>
                </div>
            </div>
            <div class="driverTel">
                <a href="tel:${driverMap.driverMobile}">
                    <img src="${base}/pub/images/wx/phone.png">
                </a>
            </div>
        </div>
        <div class="addContent">
            <p class="spanMsg font-16">司机已到达，请尽快上车</p>
        </div>
    </div>
<%--<div class="divBox driver-info">--%>
        <%--<div id="driverTempContainer" class="row">--%>
            <%--<div class="col-xs-3 text-center imgBox">--%>
                <%--<c:choose>--%>
                    <%--<c:when test="${driverMap.driverFace != null}">--%>
                        <%--<img class="face" src="${driverMap.driverFace}">--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--<img class="face" src="${base}/pub/images/wx/man.png">--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>
            <%--</div>--%>
            <%--<div class="col-xs-7 infoBox">--%>
                <%--<p class="p">--%>
                    <%--<span class="dname">${driverMap.driverName}</span>--%>
                    <%--<span class="pingfen">--%>
							<%--<img src="${base}/pub/images/wx/star_s.png">--%>
							<%--${driverMap.driverScore}--%>
						<%--</span>--%>
                <%--</p>--%>
                <%--<p style="font-size: 16px;" class="p">--%>
                    <%--<span style="margin-right: 5px;">${driverMap.driverCarNo}</span>--%>
                    <%--<span>${driverMap.driverCarColor}${driverMap.driverBrandName}</span>--%>
                <%--</p>--%>
            <%--</div>--%>
            <%--<div class="col-xs-2 text-center imgBox">--%>
                <%--<a href="tel:${driverMap.driverMobile}">--%>
                    <%--<img class="phone-img" src="${base}/pub/images/wx/phone.png">--%>
                <%--</a>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<p class="tip">--%>
            <%--司机已到达，请尽快上车--%>
        <%--</p>--%>
    <%--</div>--%>
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
    <div class="divBox costBox row">
        <div class="text-center">
            <span style="color:#999;">已等待</span>
            <span id="timing" style="color: #00b5e6;"></span>
        </div>
    </div>

    <a class="cancelBox" href="javascript:void(0);">
        <img src="${base}/pub/images/wx/cancel.png">
        <p class="text">取消订单</p>
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/imgUtils.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">
    jQuery(document).ready(function ($) {

        var $img = $('.face').eq(0);
//        resolveImgsFixed($img);

        var $document = $(document);

        $document.on('touchend', '.cancelBox', function (event) {
            layer.confirm('<p style="color: #7a7c81; font-size: 14px;">司机已到达起点等待，现在取消可能产生费用，确定要取消订单吗？</p>', {
                title: "取消订单",
                closeBtn: false,
                area: ['80%','auto'],
                btn: ['算了', '确定取消'] //按钮
            }, function (index) {
                layer.close(index);
            }, function (index) {
                layer.close(index);
                <%--delLocalStorage('${orderMap.orderDeparTime}')--%>
                cancelOrderNeedToPay();
//                cancelOrder();
            });
        });

        //定时请求OrderDetail，获取订单详情
        setInterval(getOrderStatus, 10000);

        // 获取订单详情
        function getOrderStatus() {
            //获取刚才派出的订单ID
            var orderUuid = '${orderMap.orderUuid}';
            var openId = getLocalStorage('openId');
            $.ajax({
                type: "POST",
                url: "/wechat/order/orderResponse",
                dataType: "json",
                data: {uuid: orderUuid, openId: openId},
                success: function (res) {
                    var uuid = res.data.uuid;
                    if (res.success) {
                        var status = res.data.subStatus;
                        if (status != 202) {
                            delLocalStorage('${orderMap.orderUuid}')
                            changeUrl(status, uuid);
                        }
                    }
                }
            });
        }



        // 五分钟计时，等待接单
        var ele_timer = document.getElementById("timing");
        var n_sec = 0; //秒
        var n_min = 0; //分

        function ci(){
            var t=getLocalStorage('${orderMap.orderUuid}');
            if(!t){
                setLocalStorage('${orderMap.orderUuid}',new Date());
            }else{
                var n=new Date();
                t=new Date(t.replace(/\"/g,''));
                var cha=n-t;
                n_sec=parseInt(cha/1000%60);
                n_min=parseInt(cha/1000/60);
            }
        }
        ci();
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
                if (n_min > 30) {
                    n_sec = 0;
                    clearInterval(n_timer);
                }
            }, 1000);
        }

    });
</script>
</html>