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
    <title>行程中</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/takecar.css?v=${editions}">
    <style type="text/css">
        body {
            background-color: #fff;
        }

        a:focus, a:hover {
            text-decoration: none;
        }

        .divBox {
            width: 100%;
            border: none;
            box-shadow: none;
            margin-top: 0px;
        }

        .ratesBox {
            text-align: center;
        }

        .rateBox {
            cursor: pointer;
            width: 40%;
            display: inline-block;
            text-align: center;
            border: solid 1px #e4e7ea;
            padding: 5px;
            font-size: 16px;
            color: #c5c7ca;
            border-radius: 4px;
            margin-top: 10px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .rateBox:nth-child(even) {
            margin-left: 15px;
        }

        .rateBox-select {
            border: 1px solid #00b5e6;
            color: #00b5e6;
        }

        .otherRate {
            width: 85%;
            margin: 10px auto;
            display: block;
            resize: none;
            height: 60px;
            border: 1px solid #e4e7ea;
            font-size: 14px;
            padding: 10px;
            /*color: #d0d5d9;*/
        }

        .btn-pay:focus, .btn-pay:hover {
            color: #fff;
        }

    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox router-info">
        <p class="text-center clearfix rate-header">
            <a class="pull-left cancelPaybtn" href="#">取消</a>
            <span style="font-size: 16px; color: #6b7886;">评价</span>
        </p>
        <div data-score="5" class="text-center btn-rated" href="#">
            <img data-count="1" class="img" src="${base}/pub/images/wx/star_pre_big.png">
            <img data-count="2" class="img" src="${base}/pub/images/wx/star_pre_big.png">
            <img data-count="3" class="img" src="${base}/pub/images/wx/star_pre_big.png">
            <img data-count="4" class="img" src="${base}/pub/images/wx/star_pre_big.png">
            <img data-count="5" class="img" src="${base}/pub/images/wx/star_pre_big.png">
        </div>

        <div class="ratesBox">
            <span class="rateBox" href="#">车内整洁</span>
            <span class="rateBox" href="#">活地图</span>
            <span class="rateBox" href="#">驾驶平稳</span>
            <span class="rateBox" href="#">态度好服务棒</span>
        </div>

        <textarea maxlength="30" id="other_rate" placeholder="其他想说的(将匿名并延迟告知司机)" class="otherRate"></textarea>

    </div>

    <a class="btn-pay">
        匿名评价
    </a>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
<script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
<script type="text/javascript">
    jQuery(document).ready(function ($) {

        var $document = $(document);

        initPageData();

        function initPageData() {
            // 初始化评价标签
            $.get("/wechat/orderEvaluate/tagList", function (res) {
                if (res.success) {
                    var data = res.data;
                    var len = data.length > 4 ? 4 : data.length;
                    var spanHtml = "";
                    for (var i = 0; i < len; i++) {
                        var obj = data[i];
                        spanHtml += '<span data-uuid="' + obj.uuid + '" class="rateBox" href="javascript:void(0);">' + obj.tagName + '</span>';
                    }
                    $('.ratesBox').html(spanHtml);
                }
            });
        }

        // 匿名评价
        $document.on('touchend', '.btn-pay', function () {
            var openId = getLocalStorage('openId'),
                orderUuid = parent.$('#orderUuid').val(),
                driverUuid = parent.$('#driverUuid').val(),
                score = $('.btn-rated').data('score'),
                evaluateContent = $("#other_rate").val(),
                evaluateTag = "";

            $.map($('.rateBox-select'), function (item, index) {
                var text = $(item).text();
                evaluateTag += text + ",";
            });
            evaluateTag = evaluateTag.substring(0, evaluateTag.length - 1);
            var data = {
                openId: openId,
                orderUuid: orderUuid,
                driverUuid: driverUuid,
                evaluateScore: score,
                evaluateContent: evaluateContent.trim(),
                evaluateTag: evaluateTag
            };
            $.post("/wechat/orderEvaluate/addEvaluate", data, function (res) {
                if (res.success) {
                    layer.msg('已提交');
                    parent.window.location.href = "/wechat/order/complete/" + openId + "/" + orderUuid;
                }
            });
        });

        // 取消
        $document.on('click', '.cancelPaybtn', function () {

            // 关闭窗口
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        });

        // 选择评价
        $document.on('click', '.rateBox', function () {

            var $this = $(this);
            var flag = $this.hasClass('rateBox-select');
            if (flag) {
                $this.removeClass('rateBox-select');
            } else {
                $this.addClass('rateBox-select');
            }
        });

        // 评星级
        $document.on('click', '.btn-rated .img', function () {

            var $this = $(this);
            var count = $this.data('count'),
                sub = 5 - count;

            $this.parent('.btn-rated').data('score', count);

            var start = '/pub/images/wx/star_big.png',
                start_pre = '/pub/images/wx/star_pre_big.png';
            var imghtml = "";
            for (var i = 0; i < count; i++) {
                imghtml += '<img data-count="' + (i + 1) + '" class="img" src="' + start_pre + '" />';
            }
            if (count != 0) {
                for (var i = 0; i < sub; i++) {
                    imghtml += '<img data-count="' + (i + 1 + count) + '" class="img" src="' + start + '" />';
                }
            }
            $('.btn-rated').html('').html(imghtml);
        });


    });
</script>
</html>