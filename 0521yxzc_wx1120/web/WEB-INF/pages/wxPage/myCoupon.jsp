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
    </style>
</head>
<body>
<div class="l-coupon">
    <%--<div class="not-coupon" >--%>
        <%--<input type="radio" name="test" value="" id="cc" class="e-radio">--%>
        <%--<label for="cc">--%>
            <%--不使用电子券--%>
        <%--</label>--%>
    <%--</div>--%>
    <%--<p class="tishims">有 2个可用电子券</p>--%>
    <ul class="coupon-ul" id="coupon-ul">
        <li>
            <%--<input type="radio" name="test" value="" id="cc1" class="e-radio">--%>
            <label>
                <div class="coupon-img  zhe">
                    <div class="">
                        <p class="coupon-name">全场满减券</p>
                        <p class="coupon-time">有效期至2017-05-05</p>
                    </div>
                    <div class="coupon-money"><span>6</span>折</div>
                </div>
                <div class="coupon-text">
                    <span>用车类型不限，单笔满10元可用</span>
                </div>
            </label>
        </li>

        <li>
            <%--<input type="radio" name="test" value="" id="cc2" class="e-radio">--%>
            <label>
                <div class="coupon-img  manjian">
                    <div class="">
                        <p class="coupon-name">全场满减券</p>
                        <p class="coupon-time">有效期至2017-05-05</p>
                    </div>
                    <div class="coupon-money">￥<span>5</span></div>
                </div>
                <div class="coupon-text">
                    <span>用车类型不限，单笔满10元可用</span>
                </div>
            </label>
        </li>
        <li>
            <%--<input type="radio" name="test" value="" id="cc3" class="e-radio">--%>
            <label>
                <div class="coupon-img  guoqi">
                    <div class="">
                        <p class="coupon-name">全场满减券</p>
                        <p class="coupon-time">有效期至2017-05-05</p>
                    </div>
                    <div class="coupon-money">￥<span>5</span></div>
                </div>
                <div class="coupon-text"><span>用车类型不限，单笔满10元可用</span></div>
            </label>
        </li>
    </ul>
</div>
<script type="text/html" id="coupon_html">
    <li>
    <label>
    <div class="coupon-img  {{couponClass}}">
        <div class="">
        <p class="coupon-name">{{couponName}}</p>
        <p class="coupon-time">有效期至{{useEndTimeStr}}</p>
        </div>
        <div class="coupon-money">{{couponMoney}}</div>
        <img src="${base}/pub/images/wx/profile-coupon-expired.png"  class="profile-guoqi" alt="">
    </div>
    <div class="coupon-text"><span>{{couponText}}</span></div>
    </label>
    </li>
</script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/dropload.js?v=${editions}"></script>
<link rel="stylesheet" href="${base}/pub/css/wx/dropload.css?v=${editions}">
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
<%--<script type="text/javascript" src="/pub/js/wx/position.js"></script>--%>


<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script>
    (function () {
        document.getElementById('coupon-ul').innerHTML="";
        var page=1;
        function getListMyCouponMap(page,me){
            yxAjax.yxajax(
                {
                    status:1,
                    pageNum:page
                },
                '/wechat/coupon/listMyCouponMap',
                function (data) {
                    var d=data.data;
                    if(d.length==0 || !d){
                        me.lock();
                        me.noData();
                        me.resetload();
                        layer.msg('已没有可展示的优惠券')
                        return;
                    }
                    couponInfo(d);
                    if(d.length<10){
                        me.lock();
                        me.noData();
                    }
                    me.resetload();
                },
                function (data) {
                    console.log(data);
                },
                'GET'
            )
        }
//        优惠券数据处理
        function couponInfo(data){
            var html="";
            var d=data,day=new Date();;
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
            for(var i=0;i<d.length;i++){
                var di=d[i];
                var ht='';
//                di.couponText=couponType[di.useCarType]+','+di.useCondition;
                di.couponText=di.useCondition;
                if(!di.useEndTime){
                    di.useEndTimeStr='无限期'
                }else{
                    di.useEndTimeStr=(new Date(di.useEndTime)).format('YYYY-MM-DD');
                }
                if(di.type==1){//折扣券
                    di.couponMoney='<span>'+di.discount+'</span>折';
                    di.couponClass='zhe'
                }else{//满减券
                    di.couponMoney='￥<span>'+di.money+'</span>'
                    di.couponClass='manjian'
                }
                if(new Date(di.useEndTime)<day){//判断是否过期
                    di.couponClass='guoqi'
                }
                // 获取id为coupon_html的html元素，然后再获取简单对象di。利用rendering来将它们的html与数据合在一起。（使用{{}}来标识。）
                html+=yxAjax.rendering($('#coupon_html').html(),di)
                // console.log('$('#coupon_html').html()', $('#coupon_html').html())
                // console.log('di', di)
            }
            // 在id为coupon-ul的尾巴插入内容html
            $('#coupon-ul').append(html);
        }

//        下拉刷新
        $('#loading').dropload({
            scrollArea : window,
            distance:50,
            loadDownFn : function(me){
                getListMyCouponMap(page,me);
                page++;
            },

        });

    })()
</script>

</body>
</html>
