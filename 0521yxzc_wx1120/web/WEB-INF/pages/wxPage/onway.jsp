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
    <title>行程中</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">


</head>
<body>
<div class="container-fluid">
    <div id="container" class="hide"></div>
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
            <p class="spanMsg font-16">行程已开始，请系好安全带</p>
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
            <%--行程已开始，请系好安全带--%>
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
            <span style="color:#999;">距离终点
                <span id="planMileage" style="color: #00b5e6;"></span>公里，预计行驶
                <span id="planDuration" style="color: #00b5e6;"></span>分钟
            </span>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/imgUtils.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/template-web.js"></script>

<script type="text/javascript"
        src="http://webapi.amap.com/maps?v=1.3&key=452056fb0b11a629c147b7c8c162f02b&plugin=AMap.Driving"></script>

<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>

<script type="text/javascript">

    var $img = $('.face').eq(0);
//    resolveImgsFixed($img);

    jQuery(document).ready(function ($) {
        var st=+ "${orderMap.subStatus}";
        if(st!=300 || st!=301){
            changeUrl(st,'${orderMap.orderUuid}')
        }

        //定时请求OrderDetail，获取订单详情
        setInterval(getOrderInfo, 15000);

        var routeInfo = getLocalStorage('routeInfo', 'object');
        $("#planMileage").text(routeInfo.distance / 1000);//预估里程(公里),
        $("#planDuration").text(routeInfo.duration / 60);//预估时长(分钟),

        function getOrderInfo() {

            //获取刚才派出的订单ID
            var orderUuid = '${orderMap.orderUuid}';

            var openId = getLocalStorage('openId');

            var routeInfo = getLocalStorage('routeInfo', 'object');

            $("#planMileage").text(+routeInfo.distance / 1000);//预估里程(公里),
            $("#planDuration").text(+routeInfo.duration / 60);//预估时长(分钟),

            // 根据后台返回的司机位置和终点位置获取距离和时间
            $.ajax({
                type: "POST",
                url: "/wechat/order/orderResponse",
                dataType: "json",
                data: {uuid: orderUuid, openId: openId},
                success: function (res) {
                    if (res.success) {

                        var slng = "${WX_DRIVER_MAP_SESSION.driverCurrentLng}",
                            slat = "${WX_DRIVER_MAP_SESSION.driverCurrentLat}",
                            elng = "${WX_ORDER_MAP_SESSION.orderDestLng}",
                            elat = "${WX_ORDER_MAP_SESSION.orderDestLat}";
                        getRouteInfo(slng, slat, elng, elat);

                        var status = res.data.subStatus;
                        var uuid = res.data.uuid;
//                        changeUrl(status,uuid)
                        if (status == 400 || status === 402) {
                            window.location.href = "/wechat/order/wpay/" + openId + "/" + uuid + "/" + localStorage.getItem('TIME_ZONE');
                        } else if (status == 500) {
                            window.location.href = "/wechat/order/wrated/" + openId + "/" + uuid + "/" + localStorage.getItem('TIME_ZONE');
                        } else {
                            window.location.reload(true);
                        }
                    }
                }
            });
        }

    });
</script>
</html>