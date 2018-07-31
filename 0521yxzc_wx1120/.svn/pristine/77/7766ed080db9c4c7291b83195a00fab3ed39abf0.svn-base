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
    <title>支付</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
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
            background-color: #ffffff;
        }

        .pay-ways .img {
            width: 20px;
            height: 20px;
            margin-top: 15px;
        }

        .pay-ways {
            height: 50px;
            line-height: 50px;
            margin-top: 10px;
            background-color: #ffffff;
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
            color: #00b5e6;
            font-size: 14px;
            margin-bottom: 25px;
        }

        .paymoney .tips1 {
            color: #efa95b;
            font-size: 14px;
            margin-bottom: 25px;
        }
        .pay-check{
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox router-info">
        <div class="paymoney">
            <p class="text-center">
                <span style="font-size: 28px;" id="costMoney">45</span>元
            </p>
        </div>
        <p data-type="2" class="pay-ways clearfix">
            <img class="img pull-left" src="${base}/pub/images/wx/pay_account.png">
            <span style="color: #4b4e51; font-size: 20px;">账户余额(<span id="personMoney"></span>元)</span>
            <img class="pay-check img pull-right" src="${base}/pub/images/wx/pay_default.png">
        </p>
        <p data-type="4" class="pay-ways clearfix">
            <img class="img pull-left" src="${base}/pub/images/wx/pay_wechat.png">
            <span style="color: #4b4e51; font-size: 20px;">微信支付</span>
            <img class="pay-check pay-check-select img pull-right" src="${base}/pub/images/wx/pay_checked.png">
        </p>
    </div>

    <a id="btn_wx_pay" class="btn-pay" href="javascript:void(0);">
        确认支付<span id="costMoney1"></span>元
    </a>

    <a id="btn_wx_cancel_pay" class="btn-pay" href="javascript:void(0);">
        取消支付
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.md5.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>


<script type="text/javascript">

    var personMoney =0;
    // 支付按钮点击
    var clickCount = 0;

    var paycost = getQueryString("money"),
            sourceFrom = getQueryString("type"),
            userCouponUuid = getQueryString("userCouponUuid"),
            payopenId = getLocalStorage('openId', 'string'),
            orderUuid = getQueryString("orderUuid");
//    $('#personMoney').text(getLocalStorage('personMoney','string') || 0);
    (function () {
        $.ajax({
            url:'${base}/wechat/passenger/getBalance',
            data:{
                openId:getLocalStorage('openId', 'string')
            },
            type:'POST',
            dataType: 'json',
            success:function(data){
                $('#personMoney').text(data.data || 0);
                if(data.data>=paycost){
                    $('.pay-check').eq(0).click()
                }
                personMoney=data.data || 0;
            },
            error:function(data){
                $('#personMoney').text(0);
            }
        })
    })()

    $('#costMoney').text(parseInt(paycost));
    $('#costMoney1').text(parseInt(paycost));
    var tipsHmtl = "";
    if (sourceFrom === "0") { // 0为取消生成费用
        tipsHmtl = '<p class="text-center tips1">取消费</p>';
    } else if (sourceFrom === "1") { //1为支付生成费用
        tipsHmtl = '<p class="text-center tips js_fz_hidden">一口价</p>';
    } else if(sourceFrom === "3"){//预支付费用
        tipsHmtl = '<p class="text-center tips">预支付费用</p>';
    }
    $('.paymoney').append(tipsHmtl);
    var areaUuid = getLocalStorage('areaUuid');
    if (areaUuid === 'cb33bbcc0e8c400f992c4d1acff274cb') {
        $('.js_fz_hidden').hide();
    }
    /*
     * 调取接口获取支付参数
     * 现在支付存在账户余额支付和微信支付
     * 1、当调用接口data数据返回为null或者空时，为账户余额支付
     * 2、data返回不为空时，为微信支付调取需要参数
     * */
    function readyToPay() {
        // 初始页面价格数据
        var paytype = $('.pay-check-select').eq(0).parent().data('type'),
            paysign = "money=" + paycost + "&openId=" + payopenId + "&orderUuid=" + orderUuid + "&type=" + paytype + "&key=" + WECHAT_PAY_SIGN_KEY;
        paysign = $.md5(paysign);
        var data = {
            openId: payopenId,
            money:  paycost,
            orderUuid: orderUuid,
            type: paytype,
            sign: paysign.toUpperCase(),
            userCouponUuid: userCouponUuid
        };
        var promise
        if (getQueryString("type") == "3") {
            promise = new Promise(function(resolve, reject) {
                $.post("/wechat/prepaid/pay", data, function(result) {
                    $('#btn_wx_pay').data('click', false)
                    if (typeof result == 'string') {
                        result = result.replace(/\n/g, '');
                        result = JSON.parse(result)
                    }
                    if (result.success) {
                        delLocalStorage('prePaymentTime'); // 移除本地当前订单预支付有效时间
                        var data = result.data;
                        if(!data.appid){
                            changeUrl(data.subStatus,data.uuid)
//                            layer.msg(result.msg,function(){
//                                changeUrl(data.subStatus,data.uuid)
//                            })
                        }else{
                            resolve(data);
                        }
                    } else {
                        layer.msg(result.msg,function(){
                            delLocalStorage('prePaymentTime');
                        })
                    }
                });
            })
        } else {
            promise = new Promise(function(resolve, reject) {
                $.post("/wechat/order/pay", data, function(result) {
                    $('#btn_wx_pay').data('click', false)
                    if (typeof result == 'string') {
                        result = result.replace(/\n/g, '');
                        result = JSON.parse(result)
                    }
                    if (result.success) {
                        delLocalStorage('prePaymentTime'); // 移除本地当前订单预支付有效时间
                        var data = result.data;
                        //                    if (data == null || data === "") // 账户余额支付
                        //                        window.location.href = "/wechat/view/index";
                        resolve(data);
                    } else {
                        layer.msg(result.msg)
                    }
                });
            })
        }
        return promise;
    }

    // 调取微信支付
    function onBridgeReady(data) {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": data.appid,     //公众号名称，由商户传入
                "timeStamp": data.timestamp,         //时间戳，自1970年以来的秒数
                "nonceStr": data.noncestr, //随机串
                "package": "prepay_id=" + data.prepayid,
                "signType": "MD5",  //微信签名方式：
                "paySign": data.sign//微信签名
            },
            function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    if(getQueryString("type") == "3"){
                        changeUrl('201',orderUuid)
                    }else{
                        window.location.href = "/wechat/order/wrated/" + payopenId + "/" + orderUuid;
                    }
                }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        );
    }

    // 添加微信监听
    function wechatPay(data) {
        if (data != null) {
            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady(data), false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady(data));
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(data));
                }
            } else {
                onBridgeReady(data);
            }
        } else {
            if(getQueryString("type") == "3"){
                window.location.href = "/wechat/order/wprepayment/" + payopenId + "/" + orderUuid;
            }else{
                window.location.href = "/wechat/order/wrated/" + payopenId + "/" + orderUuid;
            }

        }
    }

    $(document).ready(function ($) {

        var $document = $(document);


        $document.on('touchend', '#btn_wx_pay', function (event) {
            if (!$(this).data('click')) {
                $(this).data('click',true)
                readyToPay().then(function (data) {
                    wechatPay(data);
                });
            }
            clickCount++;
        });

        // 取消支付
        $document.on('touchend', '#btn_wx_cancel_pay', function (event) {
            if(getQueryString("type") == "3"){
                window.location.href = "/wechat/order/wprepayment/" + payopenId + "/" + orderUuid;
            }else{
                window.history.go(-1);
            }
        });

        // 选取支付方式
        $document.on('click', '.pay-check', function ()
        {
            var $this = $(this);
            var checkImgUrl = "/pub/images/wx/pay_checked.png",
                uncheckedImgUrl = "/pub/images/wx/pay_default.png";
            $this.prop('src', checkImgUrl);
            $this.addClass('pay-check-select').parent().siblings().find('.pay-check').removeClass('pay-check-select').prop('src', uncheckedImgUrl);
        });
    });


</script>
</html>