<%@ page import="java.util.Date" %>
    <%@ page language="java" pageEncoding="utf-8" %>
        <!DOCTYPE html>
        <html>
        <%!
    public long getTime() {
        long time = new Date().getTime();
        return time;
    }
%>
            <%
    long times = getTime(); // 为某些静态文件添加时间戳，确保不会被缓存
%>

                <head>
                    <%@include file="../base.jsp"%>
                        <meta charset="utf-8" />
                        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1" />
                        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
                        <meta http-equiv="Pragma" content="no-cache" />
                        <meta http-equiv="Expires" content="0" />
                        <title>元翔专车</title>
                        <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/bootstrap.min.css">
                        <!-- 城市搜索插件css -->
                        <link rel="stylesheet" type="text/css" href="${base}/pub/js/plugins/citysearch/citysearch.css?v=${editions}">
                        <!--预约时间插件css-->
                        <link rel="stylesheet" href="${base}/pub/js/plugins/cityselect/css/LArea.min.css">
                        <!--单列选择-->
                        <link rel="stylesheet" href="${base}/pub/js/plugins/iosselect-master/src/iosSelect.css">
                        <!---->
                        <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/global.css?v=${editions}">
                        <link rel="stylesheet" type="text/css" href="${base}/pub/css/wx/index.css?v=${editions}">
                        <style type="text/css">
                        .input {
                            -webkit-appearance: none;
                            -moz-appearance: none;
                            -o-appearance: none;
                            appearance: none;
                            background-color: #ffffff;
                        }

                        #historyBox {
                            position: absolute;
                            top: 50px;
                            left: 0;
                            height: 1px;
                            width: 100%;
                        }

                        #historyBox .amap-sug-result {
                            position: absolute;
                        }
                        /*.layui-layer {
            -webkit-border-radius: 0px !important;
            -moz-border-radius: 0px !important;
            border-radius: 0px !important;
        }*/

                        .closeExtraDiv {
                            position: fixed;
                            text-align: center;
                        }

                        .f-fcf16565 {
                            color: #f16565;
                        }

                        #prePayment {
                            display: none;
                        }

                        .f-fc00b5e6 {
                            color: #00b5e6;
                        }
                        </style>
                        <style>
                        /* .m-headimg {
  position: relative;
  height: 40px;
  width: 90%;
  margin: 10px auto;
} */

                        .nowCity {
                            position: relative;
                            line-height: 30px;
                            font-size: 16px;
                            color: #4f5a67;
                            width: 100px;
                            margin: 0 auto;
                            text-align: center;
                        }

                        .nowCity span {
                            display: block;
                            width: 100%;
                        }

                        .nowCity:after {
                            content: '';
                            position: absolute;
                            border-right: 1px solid #333;
                            border-bottom: 1px solid #333;
                            width: 4px;
                            height: 4px;
                            -webkit-transform: rotate(45deg);
                            transform: rotate(45deg);
                            top: 50%;
                            margin-top: -2px;
                            right: 15px;
                        }

                        .citySelectMask {
                            z-index: 99;
                            display: none;
                            position: absolute;
                            width: 100%;
                            left: 0;
                            top: 50px;
                            line-height: 40px;
                            /* padding: 0 10px; */
                            -webkit-box-sizing: border-box;
                            box-sizing: border-box;
                            -webkit-animation-duration: .3s;
                            animation-duration: .3s;
                        }

                        .citySelectMask ul.ul {
                            z-index: 99;
                            display: block;
                            margin: 0;
                            padding: 0;
                            list-style: none;
                            background: #ffffff;
                            border-radius: 3px;
                        }

                        .citySelectMask ul.ul li.li {
                            z-index: 99;
                            text-align: center;
                        }

                        .citySelectMask ul.ul li.li:not(:first-child) {
                            border-top: 0.5px solid #f3f3f3;
                        }

                        .comMask {
                            z-index: 8;
                            display: none;
                            position: fixed;
                            width: 100%;
                            height: 100%;
                            top: 0;
                            left: 0;
                            background: rgba(0, 0, 0, 0.3);
                        }
                        </style>
                </head>
                <%--78787878--%>

                    <body>
                        <input type="hidden" value="" id="inputId">
                        <input type="hidden" value="" id="valueTo">
                        <input type="hidden" value="" id="curLocation">
                        <input type="hidden" value="" id="companyMoney">
                        <input type="hidden" value="" id="personMoney">
                        <input type="hidden" value="" id="couponMoney">
                        <input type="hidden" value="" id="userCouponUuid">
                        <input type="hidden" value="" id="areaUuid">
                        <input type="hidden" value="" id="totalFee1">
                        <input type="hidden" value="" id="isShowEnt">
                        <script type="text/html" id="carTypeTemp">
                            {{each list item index}} {{if index == 0}}
                            <div data-status="{{item.status}}" data-modelnum="{{item.modelNum}}" data-type="{{item.picNum}}" class="col-xs-4 cartype cartype-selected">
                                <img src="${base}/pub/images/wx/order_car_checked_{{item.picNum}}.png" alt="">
                                <p>{{item.modelName}}</p>
                            </div>
                            {{else if index == 1}}
                            <div data-status="{{item.status}}" data-modelnum="{{item.modelNum}}" data-type="{{item.picNum}}" class="col-xs-4 cartype cartype-economical">
                                <img src="${base}/pub/images/wx/order_car_default_{{item.picNum}}.png" alt="">
                                <p>{{item.modelName}}</p>
                            </div>
                            {{else}}
                            <div data-status="{{item.status}}" data-modelnum="{{item.modelNum}}" data-type="{{item.picNum}}" class="col-xs-4 cartype">
                                <img src="${base}/pub/images/wx/order_car_default_{{item.picNum}}.png" alt="">
                                <p>{{item.modelName}}</p>
                            </div>
                            {{/if}} {{/each}}
                        </script>
                        <script type="text/html" id="airportTemp">
                            <option value='' data-city="" data-lng="" data-lat="" selected>
                                请选择站点
                            </option>
                            {{each list item index}} {{if index == 0}}
                            <option value='{{item.uuid}}' data-city="{{item.city}}" data-lng="{{item.lng}}" data-lat="{{item.lat}}">
                                {{item.address}}
                            </option>
                            {{else}}
                            <option value='{{item.uuid}}' data-city="{{item.city}}" data-lng="{{item.lng}}" data-lat="{{item.lat}}">
                                {{item.address}}
                            </option>
                            {{/if}} {{/each}}
                        </script>
                        <div id="container" class="hide">
                        </div>
                        <div id="searchBox" class="container-fluid">
                        </div>
                        <div class="comMask"></div>
                        <div id="mask"></div>
                        <div id="historyBox" class=""></div>
                        <div id="containerBox" class="container-fluid">
                            <div class="m-headimg">
                                <div class="headimg-img" id="left-nav">
                                    <c:choose>
                                        <c:when test="${snsUserInfo.headImgUrl != null}">
                                            <img class="face" style="width: 100%;height: 100%;" src="${snsUserInfo.headImgUrl}">
                                        </c:when>
                                        <c:otherwise>
                                            <img class="face" src="${base}/pub/images/wx/man.png">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="nowCity">厦门市</div>
                                <div class="citySelectMask">
                                    <ul class="ul">
                                        <li class="li" data-uuid="f807671564b0409aa647b7b80af555b6">厦门市</li>
                                        <li class="li" data-uuid="cb33bbcc0e8c400f992c4d1acff274cb">福州市</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="header-tabs">
                                <div class="col-xs-6 text-center">
                                    <a data-type="pickUp" class="airport pickUp airport-selected" data-orderType="2" href="javascript:void(0)">站点</a>
                                </div>
                                <div style="position: relative;" class="col-xs-6">
                                    <img class="toudengcang" src="${base}/pub/images/wx/toudengcang_icon.png" alt="">
                                    <a data-type="dropOff" class="airport dropOff" data-orderType="3" href="javascript:void(0)">送机</a>
                                </div>
                            </div>
                            <div class="info-container">
                                <a id="setoutTime" href="#" class="text-center setoutTime">
            <img class="img" src="${base}/pub/images/wx/time.png">
            <span id="timeshow">请选择出发时间</span>
        </a>
                                <ul class="addr-inputs">
                                    <li class="li qidian">
                                        <img class="img" src="${base}/pub/images/wx/qidian.png">
                                        <select data-click="false" class="input qidian-input select-position" name="" id="qidian_input"></select>
                                    </li>
                                    <li class="li zhongdian">
                                        <img class="img" src="${base}/pub/images/wx/zhongdian.png">
                                        <select data-click="true" class="input select-position" name="" id="zhongdian_input">
                                            <option selected disabled value="">你要去哪儿</option>
                                        </select>
                                    </li>
                                </ul>
                            </div>
                            <div class="info-container vipAndCar">
                                <div class="vip-passageway check-border clearfix hide">
                                    <div class="pull-left">
                                        <img data-checked="true" class="weixuanzhong" src="${base}/pub/images/wx/xuanzhong_icon.png" alt="">
                                        <span class="tips">赠送头等舱通道服务</span>
                                        <a href="${base}/wechat/view/ruleDeclaration">
                    <img id="rule_declaration" class="help" src="${base}/pub/images/wx/xiaowenhao_icon@2x.png" alt="">
                </a>
                                    </div>
                                    <div class="pull-right">
                                        <span class="opt-disable opt-minus opt-minus-able">-</span>
                                        <input disabled="disabled" class="num-input" type="number" value="1" max="6" min="1">
                                        <span class="opt-plus opt-plus-able">+</span>
                                        <span class="unit">位</span>
                                    </div>
                                </div>
                                <div id="takemanBox" class="row takeman hide">
                                </div>
                            </div>
                            <div class="info-container pay-types hide">
                                <%--<input type="hidden" value="" id="companyMoney">--%>
                                    <%--<input type="hidden" value="" id="personMoney">--%>
                                        <%--<input type="hidden" value="" id="couponMoney">--%>
                                            <%--<input type="hidden" value="" id="userCouponUuid">--%>
                                                <%--<input type="hidden" value="" id="areaUuid">--%>
                                                    <%--<input type="hidden" value="" id="totalFee1">--%>
                                                        <%--<input type="hidden" value="" id="isShowEnt">--%>
                                                            <div style="margin:0; position:relative;" class="row">
                                                                <img class="pay-img icon-go" src="${base}/pub/images/wx/icon_go.png" alt="">
                                                                <div style="padding-left: 5px;padding-right: 0px;" class="col-xs-4">
                                                                    <img class="pay-img" src="${base}/pub/images/wx/paytype.png" alt="支付方式">
                                                                    <span class="dark">支付方式</span>
                                                                </div>
                                                                <div id="pay_type" class="col-xs-8 ptype">
                                                                    <span data-prepaytype="2" class="light">个人支付(可用余额<span id="personMoneyShow"></span>元)</span>
                                                                    <p class="pay-tips">
                                                                        将自动使用余额，超出部分可用第三方支付
                                                                    </p>
                                                                </div>
                                                            </div>
                            </div>
                            <div class="costBox hide">
                                <p class="money text-center">￥<span id="moneyText"></span></p>
                                <p class="text-center cost tips js_fz_hidden">一口价</p>
                                <%--<p class="text-center cost">(高速费、路桥费等由乘客支付实际产生的费用)</p>--%>
                                    <p class="text-center cost" id="selectCou"> <span>券已抵扣 <span class="f-fcf16565">10.0</span>元，更改 &rightarrow;</span>
                                    </p>
                                    <p class="text-center cost" id="prePayment"> <span>匹配司机后，需预支付<span id="prePaymoney" class="f-fc00b5e6">0</span>元，请及时确认</span>
                                    </p>
                            </div>
                            <a class="btn-call btn-call-disabled" href="javascript:void(0);" style="text-decoration:none;">呼叫专车</a>
                        </div>
                        <%--侧边导航--%>
                            <div class="nav_mask"></div>
                            <div class="l-left-nav">
                                <div class="user-info">
                                    <div class="user-face">
                                        <c:choose>
                                            <c:when test="${snsUserInfo.headImgUrl != null}">
                                                <img class="face" style="width: 100%;height: 100%;" src="${snsUserInfo.headImgUrl}">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="face" src="${base}/pub/images/wx/man.png">
                                            </c:otherwise>
                                        </c:choose>
                                        <%--<img src="${snsUserInfo.headImgUrl}" class="" alt="">--%>
                                            <span></span>
                                    </div>
                                    <div class="user-name"> ${snsUserInfo.nickname}</div>
                                    <div class="user-mobile"></div>
                                </div>
                                <div class="Rectangle-16-Copy-6"></div>
                                <div class="nav-list">
                                    <a href="${base}/wechat/view/my_order" class="trip">我的行程</a>
                                    <a href="${base}/wechat/view/myCoupon" class="exit" id="exit">我的优惠券</a>
                                </div>
                            </div>
                    </body>
                    <%--7788--%>
                        <script type="text/javascript" src="${base}/pub/js/wx/jquery.min.js"></script>
                        <script type="text/javascript" src="${base}/pub/js/wx/template-web.js"></script>
                        <script type="text/javascript" src="${base}/pub/js/plugins/layer-v3.0.3/layer.js"></script>
                        <!--引入高德地图JSAPI -->
                        <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=452056fb0b11a629c147b7c8c162f02b&plugin=AMap.Autocomplete,AMap.PlaceSearch,AMap.Driving"></script>
                        <!--引入UI组件库（1.0版本） -->
                        <script type="text/javascript">
                        var AMapUIProtocol = 'https:'; //注意结尾包括冒号
                        </script>
                        <!-- https 方式引入入口文件 -->
                        <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
                        <!-- 预约时间选择 -->
                        <script type="text/javascript" src="${base}/pub/js/plugins/cityselect/js/LAreaData1.js"></script>
                        <script type="text/javascript" src="${base}/pub/js/plugins/cityselect/js/LArea.js"></script>
                        <script type="text/javascript" src="${base}/pub/js/plugins/iosselect-master/src/iscroll.js"></script>
                        <!-- index需要文件 -->
                        <script type="text/javascript" src="${base}/pub/js/wx/localStorageOpt.js?v=${editions}"></script>
                        <script type="text/javascript" src="${base}/pub/js/wx/constant.js?v=${editions}"></script>
                        <script type="text/javascript" src="${base}/pub/js/wx/position.js?v=${editions}"></script>
                        <script type="text/javascript" src="${base}/pub/js/wx/utils.js?v=${editions}"></script>
                        <script type="text/javascript" src="${base}/pub/js/wx/search.js?v=${editions}"></script>
                        <script type="text/javascript" src="${base}/pub/js/plugins/citysearch/citysearch.js?v=${editions}"></script>
                        <script type="text/javascript">
                        // 设置本地存储微信用户openid
                        <%--window.openId="${snsUserInfo.openId}";--%>
                        setLocalStorage('openId', "${snsUserInfo.openId}");
                        setLocalStorage('nickName', "${snsUserInfo.nickname}");
                        setLocalStorage('wxUserSex', "${snsUserInfo.sex}");

                        //       setLocalStorage('openId', 'oUlRvwBioZUBvfGb7qC04KozBpH0');
                        //        setLocalStorage('nickName', "Ly");
                        //        setLocalStorage('wxUserSe x', "1");
                        //打开侧边导航栏
                        $('#left-nav').click(function() {
                            //                        判断是否已经登入
                            var openId = getLocalStorage('openId', 'string') || "";
                            logger.info(openId)
                            if (!openId) {
                                initLoginPage();
                            };
                            $('.nav_mask').show();
                            $('.l-left-nav').show()
                        });
                        $('.nav_mask').click(function() {

                            $('.nav_mask').hide();
                            $('.l-left-nav').hide();
                        });
                        //        选择优惠券
                        $('#selectCou>span').click(function() {
                            var p = $('#selectCou').attr('data-parme')
                            layer.open({
                                type: 2,
                                title: false,
                                closeBtn: false,
                                //            shadeClose: false,
                                //skin: 'layui-layer-rim', //加上边框
                                area: ['100%', '100%'], //宽高,
                                shadeClose: true,
                                anim: 'up',
                                content: '/wechat/view/selectCoupon?' + p,
                                offset: 'lb',
                                success: function(index) {
                                    index.addClass('notradius')
                                }
                            });
                        })
                        //处理选中的优惠券
                        window.userCouponUuid = '';
                        window.setCoupon = function(coupon) {
                            window.userCouponUuid = coupon;
                            callFunctionEstimatedPrice();
                        }
                        //系统区域获取接口
                        function opened_arport() {
                            $.ajax({
                                url: "/wechat/sysarea/opened.arport",
                                type: "GET",
                                data: "",
                                success: function(result) {
                                    setLocalStorage(OPENED_ARPORT, result.data)
                                },
                                error: function(data) {

                                }
                            });
                        }
                        opened_arport();
                        // -------------预支付功能-----对时间控件进行监听---当时间改变则出发预支付判断---
                        // 时间变动
                        $('#timeshow').bind('DOMNodeInserted', function(e) {
                            // --因为Dom操作有时间延迟，导致读取值时无法即时，故延后200ms
                            setTimeout(function() {
                                prePaymentOrNo();
                            }, 100)
                        })
                        // 支付类型变动
                        $('#pay_type').bind('DOMNodeInserted', function(e) {
                            // --因为Dom操作有时间延迟，导致读取值时无法即时，故延后200ms
                            setTimeout(function() {
                                prePaymentOrNo();
                            }, 100)
                        })
                        </script>
                        <script>
                        function get_currentCity(areaUuid) {
                            var url = '/wechat/change/session/' + areaUuid;
                            var promise = new Promise(function(resolve, reject) {
                                $.ajax({
                                        url: url,
                                        type: 'GET',
                                        dataType: 'JSON'
                                    })
                                    .done(function(res) {
                                        if (res.success) {
                                            window.location.href = '/wechat/view/index'
                                        }
                                    })
                                    .fail(function() {
                                        console.log("error");
                                    });
                            })
                            return promise
                        };
                        (function() {
                            $('.nowCity').on('click', function() {
                                $('.comMask').fadeIn(300)
                                $('.citySelectMask').fadeIn()
                            })
                            $('.comMask').on('click', function() {
                                $(this).fadeOut(300)
                                $('.citySelectMask').fadeOut(300)
                            })
                            $('.citySelectMask').on('click', '.li', function() {
                                var areaUuid = $(this).attr('data-uuid')
                                get_currentCity(areaUuid);
                            })

                        })();
                        </script>
                        <script type="text/javascript" src="/pub/js/wx/index.js?time=<%=times%>"></script>

        </html>