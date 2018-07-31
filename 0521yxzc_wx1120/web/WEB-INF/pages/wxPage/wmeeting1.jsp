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
    <title>等待接驾</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
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
            <p class="spanMsg font-16">${driverMap.driverDeparTime}出发，请提前做好准备</p>
        </div>
    </div>
<%--<div id="driverTempContainer" class="divBox driver-info">--%>
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
        <%--<p class="tip">--%>
            <%--${driverMap.driverDeparTime}出发，请提前做好准备--%>
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
    <a class="cancelBox" href="javascript:void(0);">
        <img src="/pub/images/wx/cancel.png">
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

        // 浏览器回退监听
        ListenBrowerBack();

        var driverMobile = "${driverMap.driverMobile}";

        $document.on('touchend', '.cancelBox', function(event) {
            layer.confirm('司机已经在赶来接您的的路上了，请您再耐心等等吧', {
                area: ['95%', 'auto'],
                title: "取消叫车",
                btn: ['联系师傅', '确定取消'] //按钮
            }, function(index) {
                $('.layui-layer-btn0').prop('href', 'tel:' + driverMobile);
                layer.close(index);
            }, function(index) {
                if (!getLocalStorage('currentOrderId')) {
                    setLocalStorage('currentOrderId', getQueryString('orderUuid'))
                }
                setTimeout(function() {
                    cancelOrder();
                }, 100)
            });
        });

        //定时请求OrderDetail，获取订单详情
        if(!getQueryString('orderlist')){
            setInterval(getOrderStatus, 5000);
        }


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
                    if (res.success) {
                        var uuid = res.data.uuid;
                        var status = res.data.subStatus;
                        if (status != 200) {
                            changeUrl(status, uuid);
                        }
                    }
                }
            });
        }

    });
</script>
</html>