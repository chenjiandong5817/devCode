<%--
  Created by IntelliJ IDEA.
  User: SmmerSoft-lwr
  Date: 2017/9/28
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="../base.jsp"%>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <meta http-equiv="expires" content="0">
    <title>我的优惠券</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <style>
        p{
            margin: 0;
            padding: 0;
        }
        body{
            font-family: PingFang-SC;
        }
        .l-coupon {
            -webkit-overflow-scrolling: touch;
            position: absolute;
            left: 0;
            right: 0;
            bottom: 110px;
            top: 0;
            /*background: #fff;*/
            overflow-y: auto;
            padding: 0 10px;
        }
    </style>
</head>
<body class="select-coupon">
<div class="l-coupon">
    <div class="not-coupon">
        <input type="radio" name="test" value="-1" id="cc" class="e-radio">
        <label for="cc">
            不使用电子券
        </label>
    </div>
    <p class="tishims">有0个可用电子券</p>
    <ul class="coupon-ul" id="coupon-ul">
        <%--<li>--%>
            <%--<input type="radio" name="test" value="" id="cc1" class="e-radio">--%>
            <%--<label for="cc1">--%>
                <%--<div class="coupon-img  zhe">--%>
                    <%--<div class="">--%>
                        <%--<p class="coupon-name">全场满减券</p>--%>
                        <%--<p class="coupon-time">有效期至2017-05-05</p>--%>
                    <%--</div>--%>
                    <%--<div class="coupon-money"><span>6</span>折</div>--%>
                <%--</div>--%>
                <%--<div class="coupon-text">--%>
                    <%--<span>用车类型不限，单笔满10元可用</span>--%>
                <%--</div>--%>
            <%--</label>--%>
        <%--</li>--%>

        <%--<li>--%>
            <%--<input type="radio" name="test" value="" id="cc2" class="e-radio">--%>
            <%--<label for="cc2">--%>
                <%--<div class="coupon-img  manjian">--%>
                    <%--<div class="">--%>
                        <%--<p class="coupon-name">全场满减券</p>--%>
                        <%--<p class="coupon-time">有效期至2017-05-05</p>--%>
                    <%--</div>--%>
                    <%--<div class="coupon-money">￥<span>5</span></div>--%>
                <%--</div>--%>
                <%--<div class="coupon-text">--%>
                    <%--<span>用车类型不限，单笔满10元可用</span>--%>
                <%--</div>--%>
            <%--</label>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<input type="radio" name="test" value="" id="cc3" class="e-radio">--%>
            <%--<label for="cc3">--%>
                <%--<div class="coupon-img  guoqi">--%>
                    <%--<div class="">--%>
                        <%--<p class="coupon-name">全场满减券</p>--%>
                        <%--<p class="coupon-time">有效期至2017-05-05</p>--%>
                    <%--</div>--%>
                    <%--<div class="coupon-money">￥<span>5</span></div>--%>
                <%--</div>--%>
                <%--<div class="coupon-text"><span>用车类型不限，单笔满10元可用</span></div>--%>
            <%--</label>--%>
        <%--</li>--%>
    </ul>
</div>
<div class="l-coupon-btn" >
    <div class="quxiao" id="quxiao">取消</div>
    <div class="queren" id="queren">确认</div>
</div>
<script type="text/html" id="coupon_html">
    <li>
        <input type="radio" name="test" value="{{uuid}}" id="{{uuid}}" class="e-radio"  {{checked}}>
        <label for="{{uuid}}">
            <div class="coupon-img  {{couponClass}}">
                <div class="">
                    <p class="coupon-name">{{couponName}}</p>
                    <p class="coupon-time">有效期至{{useEndTimeStr}}</p>
                </div>
                <div class="coupon-money">{{couponMoney}}</div>
            </div>
            <div class="coupon-text"><span>{{couponText}}</span></div>
        </label>
    </li>
</script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script>
    (function () {
        document.getElementById('coupon-ul').innerHTML="";
        var page=1;
//        console.log(getQueryString('orderType'),getQueryString('areaUuid'),getQueryString('couponUuid'));
        var orderType=getQueryString('orderType'),
            areaUuid=getQueryString('areaUuid'),
            couponUuid=getQueryString('couponUuid');
//        预估获取优惠券
        function getListMyCouponMap(){
            var pay=getQueryString('pay');
            var ad={},
                url='';
            if(pay=='true'){
                ad={
                    openId:yxAjax.openId,
                    orderUuid:getQueryString('orderUuid')
                }
                url='/wechat/coupon/findEnableCouponWithUseCarType';
            }else{
                ad={
                    openId:yxAjax.openId,
                    orderType:orderType,
                    areaUuid:areaUuid
                }
                url='/wechat/coupon/findEnableCouponForEstimate';
            }
            yxAjax.yxajax(
                ad,
                url,
                function (data) {
                    var d=data.data;
                    if(d.length==0){
                        layer.msg('当前没有可用优惠券供选择！');
                        return;
                    }
                    couponInfo(d);
                },
                function (data) {
                    layer.msg(data.msg);
                },
                'GET'
            )
        }
//        优惠券数据处理
        function couponInfo(data){
            var html="";
            var d=data;
            var couponType={//用车类型
                0:'用车类型不限',
                1:"仅限预约用车",
                2:'仅限接机用车',
                3:'仅限送机用车',
                4:'仅限机场叫车用车',
                5:'仅限立即用车用车',
                6:'仅限半日租用车',
                7:'仅限日租用车'
            }
            yxAjax.listData={};
            $('.tishims').html('有'+d.length+'个可用电子券')
            for(var i=0;i<d.length;i++){
                var di=d[i];
                var ht='';
//                di.couponText=couponType[di.useCarType]+','+di.useCondition;
                di.couponText=di.useCondition;
                if(!di.useEndTime){
                    di.useEndTimeStr='无限期'
                }else {
                    di.useEndTimeStr = (new Date(di.useEndTime)).format('YYYY-MM-DD');
                }
//                判断是否已选的优惠券

                di.checked=di.uuid==couponUuid?'checked':'';
                if(di.type==1){//折扣券
                    di.couponMoney='<span>'+di.discount+'</span>折';
                    di.couponClass='zhe'
                }else{//满减券
                    di.couponMoney='￥<span>'+di.money+'</span>'
                    di.couponClass='manjian'
                }
                yxAjax.listData[di.uuid]=di;
                html+=yxAjax.rendering($('#coupon_html').html(),di)
            }
            yxAjax.listData['-1']='-1';
            if(!couponUuid){$('#cc').attr('checked',true);}
            $('#coupon-ul').append(html);
        }

        getListMyCouponMap(page);
        //    确认
        $('#queren').click(function(){
            var wt=window.top;
           var ui= $('[name="test"]:checked').val();
            if(!yxAjax.listData){
                wt.setCoupon('-1', null);
            }else{
                wt.setCoupon(ui,yxAjax.listData[ui]);
            }

            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        })
        //    取消
        $('#quxiao').click(function(){
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        })
    })()

</script>
</body>
</html>
