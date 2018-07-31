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
    <title>待支付</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style type="text/css">
        .layui-layer {
            border-radius: 0px !important;
        }

        .paymoney {
            cursor: pointer;
        }

        .paymoney .money {
            padding: 0px;
            padding-top: 20px;
            margin-bottom: 0px;
            color: #4b4e51;
            font-size: 28px;
        }

        .paymoney .cost {
            padding: 0px;
            color: #a8abaf;
        }

        .paymoney .cost:last-child {
            padding-bottom: 20px;
        }

        .paymoney .tips {
            font-size: 16px;
            color: #00b5e6;
            margin-bottom: 0px;
        }
        .f-fcf16565{
            color: #f16565;
        }
    </style>
</head>
<body>
<input type="hidden" value="${orderMap.orderUuid}" id="orderUuid">
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
            <p class="spanMsg font-16">已到达目的地，请支付车费</p>
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
            <%--已到达目的地，请支付车费--%>
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
            <p class="text-center cost tips js_fz_hidden">一口价</p>
            <%--<p class="text-center cost">(高速费、路桥费等由乘客支付实际产生的费用)${orderMap}</p>--%>
            <!-- <p class="text-center cost">（高速费、停车费等额外费用不计入一口价）</p> -->
            <p class="text-center cost" id="selectCou" style="display: none;"> <span >券已抵扣 <span class="f-fcf16565">0.0</span>元，更改 &rightarrow;</span></p>
        </div>
    </div>

    <%--//TODO 微信支付还没做--%>
    <a class="btn-pay" href="javascript:void(0);">
        去支付
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.md5.js"></script>

<script type="text/javascript" src="${base}/pub/js/wx/imgUtils.js?v=${editions}"></script>

<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">

    jQuery(document).ready(function ($) {

        var $img = $('.face').eq(0);
        var ctype=1;
//        resolveImgsFixed($img);
        var money="${orderMap.orderPlanFare}",
            orderFare={},
            $document = $(document);
        function getConpon(){
            //总金额
//            if(!uuid){
//                $("#moneyText").text(Math.floor(money));
//                $('#selectCou').hide();
//                return;
//            }
//            获取优惠券信息
            yxAjax.yxajax(
                {orderUuid:'${orderMap.orderUuid}'},
                '/wechat/price/orderFare',
                function(data){
                   var d=data.data;
                    d.totalFee=+(d.totalFee);
                    d.waitPayFee=+(d.waitPayFee);
                    d.couponMoney=+(d.couponMoney || 0);
                    orderFare={
                        money:d.totalFee,//订单总金额
                        couponMoney:d.couponMoney || 0,//优惠金额
                        userCouponUuid: d.userCouponUuid || -1,//优惠id
                        waitPayFee:(d.waitPayFee-d.couponMoney)>=0?d.waitPayFee-d.couponMoney:0//需要支付得金额
                    }
                    $('#selectCou').show();
                    if(d.isCancelPay){//是否是取消费
                        $('#selectCou').hide();
                        ctype=0;
                        $('.tips').html('取消费');
                        orderFare={
                            money:d.totalFee,//订单总金额
                            couponMoney:0,//优惠金额
                            userCouponUuid:'-1',//优惠id -1 无
                            waitPayFee:d.waitPayFee//需要支付得金额
                        }
                        $("#moneyText").text(parseInt(orderFare.waitPayFee));
                        return;
                    }
                    if(d.isDebited){//是否使用过优惠券
                        orderFare={
                            money:d.totalFee,//订单总金额
                            couponMoney:0,//优惠金额
                            userCouponUuid:'',//优惠id 无
                            waitPayFee:d.waitPayFee//需要支付得金额
                        }
                        $('#selectCou').hide();
                        $("#moneyText").text(parseInt(orderFare.waitPayFee));
                        return;
                    }
                    $("#moneyText").text(parseInt(orderFare.waitPayFee));
                    $('#selectCou').find('.f-fcf16565').html(parseInt(orderFare.couponMoney))
                },
                function(data){
                    layer.msg(data.msg)
                }
            )
        }
        //请求优惠券
        getConpon();
//        优惠券选择完后
        window.setCoupon=function(uuid,data){
            var couponMoney=0,
                userCouponUuid='',
                waitPayFee=0,
                money1=orderFare.money;
            if(uuid=='-1'){//-1不使用优惠券
                couponMoney=0;
                userCouponUuid='-1';
                waitPayFee=money1;
            }else{

                if(data.type==1){//折扣券/满减券
                    waitPayFee=money1*(data.discount/10);
                    couponMoney=money1-waitPayFee;
                    if(data.highestMoney!=0){
                        if(couponMoney>data.highestMoney){
                            couponMoney=data.highestMoney;
                            waitPayFee=money1-data.highestMoney
                        }
                    }else{
                        couponMoney=money1*(1-data.discount/10);
                    }
                }else{
                    couponMoney=data.money;
                    waitPayFee=money1-data.money;
                }
                userCouponUuid=data.uuid;
            }
            orderFare={
                money:money1,
                couponMoney:Math.floor(couponMoney),//优惠金额
                userCouponUuid:userCouponUuid,//优惠id
                waitPayFee:Math.floor(waitPayFee)//需要支付得金额
            }
            $("#moneyText").text(Math.floor(orderFare.waitPayFee));
            $('#selectCou').find('.f-fcf16565').html(orderFare.couponMoney);

        }
//        更改优惠券
        //        选择优惠券
        $('#selectCou>span').click(function(){
            layer.open({
                type: 2,
                title: false,
                closeBtn: false,
//            shadeClose: false,
                //skin: 'layui-layer-rim', //加上边框
                area: ['100%', '100%'], //宽高,
                shadeClose: true,
                anim: 'up',
                content: '/wechat/view/selectCoupon?orderUuid=${orderMap.orderUuid}&pay=true&couponUuid='+(orderFare.userCouponUuid || '-1'),
                offset: 'lb',
                success:function(index){
                    index.addClass('notradius')
                }
            });
        })
        //定时请求OrderDetail，获取订单详情
        setInterval(getOrderStatus, 5000);
        //获取刚才派出的订单ID

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
                        changeUrl(status, uuid);
                    }
                }
            });
        }

        $document.on('click', '.btn-pay', function () {
            window.location.href = "/wechat/view/paydialog?&money1=" + orderFare.waitPayFee + "&money=" + orderFare.waitPayFee + "&orderUuid=${orderMap.orderUuid}&userCouponUuid="+orderFare.userCouponUuid+'&type='+ctype;
        });

    });
</script>
</html>