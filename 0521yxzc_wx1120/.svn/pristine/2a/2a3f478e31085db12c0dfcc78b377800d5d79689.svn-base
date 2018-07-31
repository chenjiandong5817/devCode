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
    <title>待评价</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style type="text/css">
        .layui-layer {
            border-radius: 0px !important;
        }

        .layui-layer-other {
            border-radius: 10px !important;
        }

        .layui-layer .btn-ok {
            color: #00b5e6 !important;
            font-size: 16px;
            width: 100%;
            display: inline-block;
            text-align: center;
            line-height: 45px;
            height: 45px;
            border-bottom-right-radius: 10px;
        }

        .vip-passageway-tip p {
            line-height: 35px;
            margin-bottom: 0px;
        }

        .vip-passageway-tip .tip-1, .vip-passageway-tip .tip-3 {
            color: #223147;
            opacity: .5;
            font-size: 18px;
        }

        .vip-passageway-tip .tip-1 {
            margin-top: 20px;
        }

        .vip-passageway-tip .tip-3 {
            margin-bottom: 20px;
        }

        .vip-passageway-tip .tip-2 {
            color: #4b4d51;
            font-size: 20px;
        }
    </style>
</head>
<body>
<input type="hidden" value="${orderMap.orderUuid}" id="orderUuid">
<input type="hidden" value="${driverMap.driverUuid}" id="driverUuid">
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
            <p class="spanMsg font-16">感觉怎么样，评价一下司机师傅吧</p>
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
                        <%--<img class="face" src="/pub/images/wx/man.png">--%>
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
            <%--感觉怎么样，评价一下司机师傅吧--%>
        <%--</p>--%>
    <%--</div>--%>
    <div class="divBox router-info">
        <p class="driver-route-info">
            <img class="img img1" src="${base}/pub/images/wx/time.png">
            <span>${orderMap.orderDeparTime}</span>
        </p>
        <p class="driver-route-info">
            <img class="img img2" src="/pub/images/wx/qidian.png">
            <span id="originDetailAddressText">${orderMap.orderOriAddress}</span>
        </p>
        <p class="driver-route-info">
            <img class="img img3" src="${base}/pub/images/wx/zhongdian.png">
            <span id="destDetailAddressText">${orderMap.orderDestAddress}</span>
        </p>
        <div class="paymoney">
            <p style="margin-bottom: 0px;margin-top: 25px; font-size: 26px;" class="text-center">
                ￥<span id="moneyText"></span>
            </p>
            <p style="margin-bottom: 30px;" class="text-center">
                <span style="font-size: 14px;color: #00b5e6;" class="jiayi js_fz_hidden">一口价</span>
            </p>
            <a class="text-center btn-rated"  id="pingjiaxing" href="#">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
            </a>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/imgUtils.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript">
    //        5秒自动回退到首页
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    function backIndex(){
        if(!getQueryString('orderlist')){
            setTimeout(function(){
                window.location.href='/wechat/view/index'
            },5000)
        }
    }
    jQuery(document).ready(function ($) {

        var $img = $('.face').eq(0);
//        resolveImgsFixed($img);

        var openId = getLocalStorage("openId");
        var orderId = "${orderMap.orderUuid}";

        var $document = $(document);


        var cost = "${orderMap.orderTotalFare}";
        var canpd = "${orderMap.orderCancelFare}";
        var pfare = "${orderMap.orderPlanFare}";

        $("#moneyText").text(Math.floor(cost || canpd || pfare));
        if(canpd!=''){
            $('.jiayi').html('取消费')
            $('#pingjiaxing').hide()
            $('#tiptext').html('行程已取消')
            $('title').html('已取消')
            backIndex();
        }else{
            if(cost!=pfare){
//                总费用和总金额不同的时候。
//                $('.jiayi').html();
            }
        }
        $document.on('click', '.btn-rated', function () {
            layer.open({
                type: 2,
                title: false,
                closeBtn: false,
                shadeClose: false,
                area: ['100%', '400px'], //宽高,
                shadeClose: true,
                anim: 'up',
                content: '/wechat/view/ratedialog',
                offset: 'lb'
            });
            $('.layui-layer').css('border-radius', '0px !important');
        });

        $.get('/wechat/order/getVipStatusInfo?openId=' + openId + '&orderUuid=' + "${orderMap.orderUuid}", function (res) {
            if (res.success) {
                var data = res.data;
                if (data.vipEffTime && data.vipFlag) {
                    vipPassgeWayTips(data.vipCounts);
                }
            }
        });

        function vipPassgeWayTips(count) {
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                shadeClose: true,
                area: ['80%', '350px'],
                content: '<div class="vip-passageway-tip">' +
                '<img style="width: 100%;" src="/pub/images/wx/gongxi.png" alt="">' +
                '<p class="text-center tip-1">恭喜您已获得</p>' +
                '<p class="text-center tip-2">' + count + '位头等舱通道服务</p>' +
                '<p class="text-center tip-3">请查收短信</p>' +
                /*'<a class="btn-ok" href="javascript:layer.closeAll();">好的，谢谢</a>' +*/
                '</div>',
                btn: ['好的，谢谢'],
                btn1: function (index) {
                    layer.close(index);
                }
            });
            $('.layui-layer').addClass('layui-layer-other');
            $('.layui-layer .layui-layer-btn0').addClass('btn-ok');
        }

    });
    // --------福州区域隐藏‘一口价’描述----2018/4/3----
(function() {
    var areaUuid = getLocalStorage('areaUuid');
    if (areaUuid === 'cb33bbcc0e8c400f992c4d1acff274cb') {
        $('.js_fz_hidden').hide();
    }
})();
</script>
</html>