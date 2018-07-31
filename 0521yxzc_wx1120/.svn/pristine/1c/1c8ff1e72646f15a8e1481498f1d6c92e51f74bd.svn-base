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
    <title>选择支付方式</title>
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
    <style>
        .pay-types {

        }

        .pay-types div {
            padding: 10px 5px;
        }

        .pay-types .ptype {
            padding-left: 5px;
            padding-right: 0px;
            cursor: pointer;
        }

        .pay-types .ptype:first-child {
            border-bottom: 1px solid #eee;
        }

        .pay-types .light {
            color: #44c8ec;
            font-size: 16px;
        }

        .pay-types .pay-tips {
            color: #c5c7ca;
            font-size: 14px;
            margin-bottom: 0px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="divBox pay-types text-center">
        <div data-type="company" class="col-xs-12 ptype">
            <span data-prepaytype="1" class="light">企业支付(可用限额<span id="companyMoney"></span>元)</span>
            <p class="pay-tips">
                将自动使用企业余额进行支付
            </p>
        </div>
        <div data-type="person" class="col-xs-12 ptype">
            <span data-prepaytype="2" class="light">个人支付(可用余额<span id="personMoney"></span>元)</span>
            <p class="pay-tips">
                将自动使用余额，超出部分可用第三方支付
            </p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
<script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>

<script type="text/javascript">

    var cMoney = parent.$("#companyMoney").val(),
            pMoney = parent.$("#personMoney").val(),
            canbeused = parent.$("#isShowEnt").val();
    console.log(parent)
    $("#companyMoney").text(cMoney || '0');
    $("#personMoney").text(pMoney || '0');

    $(document).ready(function ($) {

        var $document = $(document);

        // 支付按钮点击
        $document.on('touchend', '.ptype', function (event) {
            var $this = $(this);
            var type = $this.data('type');

            if (type === "company") {
                if (canbeused === "0") {
                    layer.msg("企业支付当前不可使用，请使用个人支付！");
                    return;
                }
//                parent.$('#selectCou').hide();

            }else{
                if(!!parent.$('#selectCou').attr('data-parme')){
                    parent.$('#selectCou').show();
                }
            }
            window.top.userCouponUuid='-1';
            //企业支付是1；
            window.top.estimatedPrice(parent.$('.cartype-selected').data('modelnum'),$(this).find('.light').attr('data-prepaytype'));
            var exsitHtml = $this.html();
            parent.$('#pay_type').html(exsitHtml);

            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        })
    //        选择企业或者个人支付的时候，处理优惠券。
//        function handle(type){
//            if(!userCouponUuid){return;}
//            if(type=="company"){//选择企业支付
//                parent.$("#moneyText").html(totalFee1);
//                parent.$('#selectCou').hide();
//            }else{
//                if(parent.$('#selectCou').is(":hidden") && !userCouponUuid){
//                    parent.$('#selectCou').hide();
//                    return
//                };
//                parent.$("#moneyText").html(parseFloat(totalFee1)-parseFloat(couponMoney));
//                parent.$('#selectCou').hide().show()
//            }
//        }
    });


</script>
</html>