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
	<title>行程中</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style type="text/css">
        body{
            background-color: #fff;
        }
        a:focus, a:hover{
            text-decoration: none;
        }
        .divBox{
            width: 100%;
            border:none;
            box-shadow: none;
            margin-top: 0px;
        }
        .cancelPaybtn{
            color: #9ea7b1;
            text-decoration: none;
        }
        .rateBox{
            color: #4f5a67;
            margin-top: 30px;
        }
        
    </style>
</head>
<body>
    <div class="container-fluid">
    	<div class="divBox router-info">
    		<p class="text-center clearfix">
    			<a class="pull-left cancelPaybtn" href="#">取消</a>
    			<span>评价</span>
    		</p>
            <div class="text-center btn-rated" href="#">
                <img class="img" src="${base}/pub/images/wx/star_pre_big.png">
                <img class="img" src="${base}/pub/images/wx/star_pre_big.png">
                <img class="img" src="${base}/pub/images/wx/star_pre_big.png">
                <img class="img" src="${base}/pub/images/wx/star_pre_big.png">
                <img class="img" src="${base}/pub/images/wx/star_big.png">
            </div>

            <div class="rateBox text-center">
                “驾驶平稳，司机人很好”
            </div>
            
    	</div>
    </div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {
	
	var $document = $(document);
    // 取消
    $document.on('click','.cancelPaybtn',function(){

        // 关闭窗口
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    });


});
</script>
</html>