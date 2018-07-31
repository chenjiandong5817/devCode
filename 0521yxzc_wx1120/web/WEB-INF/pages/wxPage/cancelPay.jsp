<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../base.jsp"%>
	<meta charset="utf-8" />
	<meta name ="viewport" content ="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
	<meta http-equiv="expires" content="0">
	<title>已取消</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
</head>
<body style="display: none;">
    <div class="container-fluid" id="container-fluid">
		<div class="divBox">
			<div class="driverMsg">
				<div class="driverImg">
					<img src="${base}/pub/images/wx/man.png">
				</div>
				<div class="driverDetail">
					<div>
						<span class="driver_name">${sessionScope.driverBean.name}</span>
						<span class="driver_rate">${sessionScope.driverBean.score}</span>
						<!-- 司机订单数暂未开启 -->
						<span class="driver_order">${sessionScope.driverBean.orderCount}单</span>
					</div>
					<div>
						<span class="car_id">${sessionScope.driverBean.plateNum}</span>
						<span class="car_msg">${sessionScope.driverBean.carColor}${sessionScope.driverBean.brandName}</span>
					</div>
				</div>
				<div class="driverTel">
					<a href="tel:${sessionScope.driverBean.mobile}">
						<img src="${base}/pub/images/wx/phone.png">
					</a>
				</div>
			</div>
			<div class="addContent">
				<p class="spanMsg font-16">行程已取消</p>
			</div>
		</div>
	<%--<div class="divBox driver-info">--%>
    		<%--<div class="row">--%>
    			<%--<div class="col-xs-3 text-center imgBox">--%>
					<%--<img class="" src="${base}/pub/images/wx/man.png">--%>
				<%--</div>--%>
				<%--<div class="col-xs-7 infoBox">--%>
					<%--<p class="p">--%>
						<%--<span class="dname">${sessionScope.driverBean.name}</span>--%>
						<%--<span class="pingfen">--%>
							<%--<img src="${base}/pub/images/wx/star_s.png">--%>
							<%--${sessionScope.driverBean.score}--%>
						<%--</span>--%>
						<%--${sessionScope.driverBean.orderCount}单--%>
					<%--</p>--%>
					<%--<p style="font-size: 16px;" class="p">--%>
						<%--<span style="margin-right: 5px;">${sessionScope.driverBean.plateNum}</span>--%>
						<%--<span>${sessionScope.driverBean.carColor}${sessionScope.driverBean.brandName}</span>--%>
					<%--</p>--%>
				<%--</div>--%>
				<%--<div class="col-xs-2 text-center imgBox">--%>
					<%--<a href="tel:${sessionScope.driverBean.mobile}">--%>
						<%--<img class="phone-img" src="${base}/pub/images/wx/phone.png">--%>
					<%--</a>--%>
				<%--</div>--%>
    		<%--</div>--%>
			<%--<p class="tip">--%>
				<%--行程已取消--%>
			<%--</p>--%>
    	<%--</div>--%>
		<div class="divBox router-info">
			<p>
				<img class="img img1" src="${base}/pub/images/wx/time.png">
				<span>${sessionScope.departTime}</span>
			</p>
			<p>
				<img class="img img2" src="${base}/pub/images/wx/qidian.png">
				<span>${sessionScope.originDetailAddress}</span>
			</p>
			<p>
				<img class="img img3" src="${base}/pub/images/wx/zhongdian.png">
				<span>${sessionScope.destDetailAddress}</span>
			</p>
			<div class="paymoney">
				<div class="imgcBox">
					<img class="imgc" src="${base}/pub/images/wx/cancel-icon.png">
				</div>
				<p class="tip text-center">
					${sessionScope.cancelReason}
				</p>
				<%--<a class="text-center cancel-rule" href="#">--%>
				<%--<img class="img" src="/images/wenhao.png">--%>
				<%--<span class="text">取消规则</span>--%>
				<%--</a>--%>
			</div>
		</div>
    </div>
	<script type="text/html" id="driver">
		<div class="divBox">
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
						<!-- <span class="car_id">${sessionScope.driverBean.plateNum}</span> -->
						<span class="car_id">{{carNo}}</span>
						<span class="car_msg">{{carColor}}{{brandName}}</span>
					</div>
				</div>
				<div class="driverTel">
					<!-- <a href="tel:${sessionScope.driverBean.mobile}"> -->
					<a href="tel:{{mobile}}">
						<img src="${base}/pub/images/wx/phone.png">
					</a>
				</div>
			</div>
			<div class="addContent">
				<p class="spanMsg font-16">行程已取消</p>
			</div>
		</div>
		<%--<div class="divBox driver-info">--%>
			<%--<div class="row">--%>
				<%--<div class="col-xs-3 text-center imgBox">--%>
					<%--<img class="" src="${base}/pub/images/wx/man.png">--%>
				<%--</div>--%>
				<%--<div class="col-xs-7 infoBox">--%>
					<%--<p class="p">--%>
						<%--<span class="dname">{{userName}}</span>--%>
						<%--<span class="pingfen">--%>
							<%--<img src="${base}/pub/images/wx/star_s.png">--%>
							<%--{{avgScore}}--%>
						<%--</span>--%>
						<%--{{currentAngle}}单--%>
					<%--</p>--%>
					<%--<p style="font-size: 16px;" class="p">--%>
						<%--<span style="margin-right: 5px;">${sessionScope.driverBean.plateNum}</span>--%>
						<%--<span>{{carColor}}{{brandName}}</span>--%>
					<%--</p>--%>
				<%--</div>--%>
				<%--<div class="col-xs-2 text-center imgBox">--%>
					<%--<a href="tel:{{mobile}}">--%>
						<%--<img class="phone-img" src="${base}/pub/images/wx/phone.png">--%>
					<%--</a>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<p class="tip">--%>
				<%--行程已取消--%>
			<%--</p>--%>
		<%--</div>--%>
	</script>
	<script type="text/html" id="order">
		<div class="divBox router-info">
			<p>
				<img class="img img1" src="${base}/pub/images/wx/time.png">
				<span>{{serviceTimeStart}}</span>
			</p>
			<p>
				<img class="img img2" src="${base}/pub/images/wx/qidian.png">
				<span>{{originDetailAddress}}</span>
			</p>
			<p>
				<img class="img img3" src="${base}/pub/images/wx/zhongdian.png">
				<span>{{destDetailAddress}}</span>
			</p>
			<div class="paymoney">
				<div class="imgcBox">
					<img class="imgc" src="${base}/pub/images/wx/cancel-icon.png">
				</div>
				<p class="tip text-center">
					{{cancelText}}
				</p>
				<%--<a class="text-center cancel-rule" href="#">--%>
				<%--<img class="img" src="/images/wenhao.png">--%>
				<%--<span class="text">取消规则</span>--%>
				<%--</a>--%>
			</div>
		</div>
	</script>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {
	if(!getQueryString('orderlist')){
	    $('body').show()
        setTimeout(goIndex,5000);
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
				var order=yxAjax.rendering($('#order').html(),o);
                $('#container-fluid').html(actualDriver+order);
			},function(data){
                console.log(data);
			},
			"POST"
		)
	}
	function goIndex() {
		window.location.href = "${base}/wechat/view/index";
	}

});
</script>
</html>