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
	<title>待评价</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css">
</head>
<body>
    <div class="container-fluid">
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
						<!-- <span class="driver_order">${sessionScope.driverBean.orderCount}单</span> -->
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
    			<img class="img img3" src="/pub/images/wx/zhongdian.png">
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
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?editions=${editions}"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {

	setInterval(goIndex,5000);
	function goIndex() {
		window.location.href = "${base}/wechat/index";
	}

});
</script>
</html>