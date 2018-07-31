<%--
  Created by IntelliJ IDEA.
  User: SmmerSoft-lwr
  Date: 2017/9/28
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%!
    public long getTime() {
        long time = new Date().getTime();
        return time;
    }
%>
<%
    long times = getTime(); // 为某些静态文件添加时间戳，确保不会被缓存
%>
<html>
<head>
    <%@include file="../base.jsp"%>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <meta http-equiv="expires" content="0">
    <title>我的行程</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/dropload.css?v=${editions}">
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
<div class="l-myorder" id="l-myorder">
    <ul class="myorder-ul" id="orderold">
        <%--<li>--%>
            <%--<div class="myorder-time">--%>
                <%--<div><p>1月20日 12：32 <span class="myorder-class">微信立即</span></p></div>--%>
                <%--<div><p class="myorder-pay">待支付</p></div>--%>
            <%--</div>--%>
            <%--<div>--%>
                <%--<p class="myorder-icon qidian"> <span>厦门软件园二期</span></p>--%>
                <%--<p class="myorder-icon zhongdian"><span>厦门高崎机场</span></p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="myorder-time">--%>
                <%--<div><p>1月20日 12：32 <span class="myorder-class">微信立即</span></p></div>--%>
                <%--<div><p class="myorder-gray">已完成</p></div>--%>
            <%--</div>--%>
            <%--<div>--%>
                <%--<p class="myorder-icon qidian"> <span>厦门软件园二期</span></p>--%>
                <%--<p class="myorder-icon zhongdian"><span>厦门高崎机场</span></p>--%>
            <%--</div>--%>
        <%--</li>--%>
    </ul>
    <div class="loading" id="loading"></div>
</div>
<script type="text/html" id="orderhtml">
    <li data-orderUuid="{{uuid}}">
        <div class="myorder-time">
            <div><p>{{Times}}<span class="myorder-class">{{orderTypeName}}</span></p></div>
            <div><p class="myorder-{{statusClass}}">{{statusName}}</p></div>
        </div>
        <div>
            <p class="myorder-icon qidian"> <span>{{originAddress}}</span></p>
            <p class="myorder-icon zhongdian"><span>{{destAddress}}</span></p>
        </div>
    </li>
</script>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/dropload.js?v=${editions}"></script>

<link rel="stylesheet" href="${base}/pub/css/wx/dropload.css?v=${editions}">
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script>
    (function () {
        var page=1;
        var staue={
            100:{name:'进行中',class:'sing'},
            101:{name:'进行中',class:'sing'},
            200:{name:'进行中',class:'sing'},
            201:{name:'进行中',class:'sing'},
            202:{name:'进行中',class:'sing'},
            210:{name:'进行中',class:'sing'},
            220:{name:'进行中',class:'sing'},
            300:{name:'进行中',class:'sing'},
            301:{name:'进行中',class:'sing'},
            400:{name:'待支付',class:'pay'},
            401:{name:'待支付',class:'pay'},
            402:{name:'待支付',class:'pay'},
            500:{name:'待评价',class:'gray'},
            501:{name:'已完成',class:'gray'},
            600:{name:'已取消',class:'gray'},
            601:{name:'已取消',class:'gray'},
            602:{name:'已取消',class:'gray'},
            603:{name:'已取消',class:'gray'}
        }
        var orderType={
            1:"预约",
            2:'接机',
            3:'送机',
            4:'机场叫车',
            5:'立即用车',
            6:'半日租',
            7:'日租'
        }
        yxAjax.orderList={};//订单容器
//        获取订单数据
        function getListOrder(p,me){

            yxAjax.yxajax(
                {pageNum:p},
                '/wechat/order/myTrip',
                function(data){
                    var d=data.data;
                    if(d.length==0){
                        me.lock();
                        me.noData();
                        me.resetload();
                        layer.msg('暂无此行程')
                        return ;
                    }
                    var html='';
                    var s=$('#orderhtml').html();
                    for (var i =0;i<d.length;i++){
                        var di=d[i];
                        var sub=staue[di.subStatus];
                        if(di.subStatus==500){
                            if(!!di.cancelFee){
                                sub=staue['600']
                            }
                        }
                        di.statusClass=sub.class;
                        di.statusName=sub.name;
                        di.orderTypeName=orderType[di.orderType];
                        di.Times=(new Date((di.deparTime || di.createTime))).format('MM月DD日  hh:mm')
                        di.statusClass='gray';
                        yxAjax.orderList[di.uuid]=di;
                        html+=yxAjax.rendering(s,di);
                    }
                    if(d.length<10){
                        me.lock();
                        me.noData();
                    }
                    $('#orderold').append(html);
                    me.resetload();
                },
                function(data){
                    me.lock();
                    me.noData();
                    me.resetload();
                    layer.msg(data.msg)
                }
            )
        }
        function rendering(str,obj){
            if(!obj){return str;}
            var html='', str=str;
            for(var i in obj){
                var rex=new RegExp('{{'+i+'}}','g');
                str=str.replace(rex,obj[i]);
            }
            return str;
        }

        $('.l-myorder').dropload({
            scrollArea : window,
            distance:50,
            loadDownFn : function(me){
                getListOrder(page,me);
//                me.resetload();
                page++;
            },
        });
//        $('#loading').dropload({
//            scrollArea : window,
//            distance:50,
//            loadDownFn : function(me){
//                getListOrder(page,me)
//                page++;
//            },
//        });

        $('#orderold').on('click','li',function (event) {
            var t=$(this);
            var uuid=t.attr('data-orderuuid');
           console.log( yxAjax.orderList[uuid]);
           hrefSub(yxAjax.orderList[uuid].subStatus || '',uuid);
        })
//        订单状态跳转对应页面
        function hrefSub(subStatus,orderUuid){
            var openId = getLocalStorage('openId','string');
            var staueUrl={
                100:'/wechat/order/wreply/',
                101:'/wechat/order/wreply/',
                200:"/wechat/order/wmeeting1/",
                201:"/wechat/order/wmeeting/",
                202:"/wechat/order/wtakecar/",
                300:"/wechat/order/onway/",
                301:"/wechat/order/onway/",
                400:"/wechat/order/wpay/" ,
                401:"/wechat/order/wpay/" ,
                402:"/wechat/order/wpay/" ,
                500:"/wechat/order/wrated/",
                501:"/wechat/order/complete/",
                600:"/wechat/view/cancelPay",
                601:"/wechat/view/cancelPay",
                602:"/wechat/view/cancelPay",
                603:"/wechat/view/cancelPay"
            }
            var url=staueUrl[subStatus] || "/wechat/view/index";
            if(subStatus>=600){
                window.location.href=url+'?orderlist=true&orderUuid='+orderUuid;
            }else{
                window.location.href=url+openId+'/'+orderUuid+'?orderlist=true&orderUuid='+orderUuid;
            }


        }
    })()
</script>
</body>
</html>
