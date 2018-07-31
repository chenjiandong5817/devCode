<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../base.jsp"%>
	<meta charset="utf-8" />
	<meta name ="viewport" content ="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
	<meta http-equiv="expires" content="0">
	<title>取消行程</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css">
</head>
<body>
    <div class="container-fluid cancelCar">
    	<div class="imgBox">
            <img class="img" src="${base}/pub/images/wx/cancel-icon.png">
        </div>
        <p class="tellme">请告诉我们原因</p>
        <p class="tellme1">我们会努力为您提供更好的服务</p>
        <ul class="reason-list">
            <c:forEach items="${sessionScope.commonTagDtoList}" var="commonTagDto">
                <li class="clearfix li">
                    <span class="pull-left">${commonTagDto.tagName}</span>
                    <img data-checked="false" class="pull-right img" src="${base}/pub/images/wx/check_round.png">
                </li>
            </c:forEach>
        </ul>
        <a id="noCancelBtn" class="btn-d btn-no-cancel" href="javascript:void(0);">暂不取消</a>
        <a id="cancelBtn" class="btn-d btn-submit-disable" href="javascript:void(0);">提交</a>
    </div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?editions=${editions}"></script>
<script type="text/javascript" src="${base}/pub/js/wx/utils.js?editions=${editions}"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {

    var $document = $(document);
    $document.on('touchend','.reason-list .img',function(){
        var $this = $(this);
        var checked = $this.data('checked');
        var urlchecked = "${base}/images/check.png",
            urlunchecked = "${base}/images/check_round.png";
        if(checked){// 取消选中
            $this.prop('src',urlunchecked);
            $this.data('checked',false);
            $('#cancelBtn').addClass('btn-submit-disable').removeClass('btn-submit-able');
        }else{// 选中
            $this.prop('src',urlchecked).data('checked',true).addClass('reason-select');
            $this.parent().siblings('.li').find('.img').removeClass('reason-select').data('checked', false).prop('src', urlunchecked);
            $('#cancelBtn').addClass('btn-submit-able').removeClass('btn-submit-disable');
        }
    });

    $document.on('touchend','.btn-no-cancel',function(){
        $.ajax({
            type: "POST",
            url: "${base}/wx/Order/info",
            dataType: "json",
            data: {orderUuid: "${orderUuid}"},
            success: function (data) {
                if (data.success) {
                    changeUrl(data.data.subStatus);
                }
            }
        });
    });

    $document.on('touchend','.btn-submit-able',function(){
        var $checked = $('.reason-list .reason-select').eq(0);
        var text = $checked.prev().text();

        $.ajax({
            type: "POST",
            url: "${base}/wx/Order/canceConfirmlOrder",
            dataType: "json",
            data: {orderUuid: "${orderUuid}",cancelMsg:text},
            success: function (data) {
                if (data.success) {
                    window.location.href = "${base}/yue/cancelNoPay";
                }
            }
        });
    });

});
</script>
</html>