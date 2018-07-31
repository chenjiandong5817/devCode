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
    <title>已完成</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style type="text/css">
        .layui-layer {
            border-radius: 0px !important;
        }

        .paymoney .money {
            padding: 0px;
            padding-top: 20px;
            padding-bottom: 5px;
            color: #4b4e51;
            font-size: 30px;
        }

        .paymoney .cost {
            padding: 0px;
            color: #a8abaf;
        }
        .paymoney .cost:last-child {
            padding-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox" id="driverMsgCon">
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
            <p class="spanMsg font-16">到达目的地，订单已完成</p>
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
            <%--到达目的地，订单已完成--%>
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
        <div class="paymoney">
            <p class="money text-center">￥<span id="moneyText"></span></p>
            <p class="text-center cost js_fz_hidden">一口价</p>
            <p class="text-center cost js_fz_hidden">(高速费、停车费等额外费用不计入一口价)</p>
        </div>
    </div>
</div>
</body>
<script type="text/html" id="driver">
            <div class="driverMsg">
                <div class="driverImg">
                    <!-- <img src="${base}/pub/images/wx/man.png"> -->
                    <img src="{{face}}">
                </div>
                <div class="driverDetail">
                    <div>
                        <span class="driver_name">{{userName}}</span>
                        <span class="driver_rate">{{avgScore}}</span>
                        <!-- 司机订单数暂未开启 -->
                        <!-- <span class="driver_order">{{currentAngle}}单</span> -->
                    </div>
                    <div>
                        <span class="car_id">{{carNo}}</span>
                        <span class="car_msg">{{carColor}}{{brandName}}</span>
                    </div>
                </div>
                <div class="driverTel">
                    <a href="tel:{{mobile}}">
                        <img src="${base}/pub/images/wx/phone.png">
                    </a>
                </div>
            </div>
            <div class="addContent">
                <p class="spanMsg font-16">到达目的地，订单已完成</p>
            </div>
</script>
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
        var cost = "${orderMap.orderTotalFare}";
        var canpd = "${orderMap.orderCancelFare}";
        var pfare = "${orderMap.orderPlanFare}";

        $("#moneyText").text(Math.floor(canpd || cost || pfare));
        if(canpd!=''){
            $('.jiayi').html('取消费')
            $('#pingjiaxing').hide()
            $('#tiptext').html('行程已取消')
        }else{
            if(cost!=pfare){
//                总费用和总金额不同的时候。
//                $('.jiayi1').html();
            }
        }
        $document.on('click', '.btn-rated', function () {

            layer.open({
                type: 2,
                title: false,
                closeBtn: false,
                shadeClose: false,
                //skin: 'layui-layer-rim', //加上边框
                area: ['100%', '200px'], //宽高,
                shadeClose: true,
                anim: 'up',
                content: '/wechat/ratedetail',
                offset: 'lb'
            });
        });
        if(!getQueryString('orderlist')){
            setInterval(getOrderStatus, 5000);
        }else{
        $('#container-fluid').html('');
        yxAjax.ajax(
            {
                orderUuid:getQueryString('orderUuid'),
                openId:yxAjax.openId
            },
            '${base}/wechat/order/tripDetail',
            function(data){
                $('body').show();
                var d=data.data;
                var actualDriver=yxAjax.rendering($('#driver').html(),d.actualDriver);
                var o=d.order;
                o.serviceTimeStart=(new Date(o.deparTime || o.serviceTimeStart)).format('YYYY-MM-DD  hh:mm');
                o.cancelText=o.cancelReason || o.closeReason || '';
                //var order=yxAjax.rendering($('#order').html(),o);
                //$('#cdriverMsgCon').html(actualDriver+order);
                $('#driverMsgCon').html(actualDriver);
            },function(data){
                console.log(data);
            },
            "POST"
        )
    }

        // 获取订单详情
        function getOrderStatus() {
            //获取刚才派出的订单ID
            var orderUuid = getLocalStorage('currentOrderId');
            var openId = getLocalStorage('openId');
            $.ajax({
                type: "POST",
                url: "/wechat/order/orderResponse",
                dataType: "json",
                data: {uuid: orderUuid, openId: openId},
                success: function (res) {
                    if (res.success) {
                        var status = res.data.subStatus;
                        if (status == 501){ // 已完成
                            window.location.href = "/wechat/view/index";
                        }
                    }
                }
            });
        }
    });
</script>
</html>