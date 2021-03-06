/*
* @Author: cdroid
* @Date:   2018-05-21 11:21:03
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-09 01:44:03
*/

/* 错误收集器  */
//onerror=handleErr
var txt=""

function handleErr(msg,url,l)
{
    txt="There was an error on this page.\n\n"
    txt+="Error: " + msg + "\n"
    txt+="URL: " + url + "\n"
    txt+="Line: " + l + "\n\n"
    txt+="Click OK to continue.\n\n"
    $.ajax({
        url: "/api/base/testConsole",
        type: "POST",
        data: {consoleText:txt}
    });
    return true
}
/* /错误收集器  */


// pushState 实现浏览器返回监听
function pushHistory() {
    var state = {
        title: "title",
        url: "#"
    };
    window.history.pushState(state, "title", "#");
}

function ListenBrowerBack() {
    pushHistory();
    var bool = false;
    setTimeout(function () {
        bool = true;
    });
    window.addEventListener("popstate", function (e) {
        if (bool) {
            layer.confirm('您点击了返回，是否需要取消该订单？', {
                area: ['70%', 'auto'],
                title: "取消叫车",
                shadeClose: true,
                btn: ['是', '否'] //按钮
            }, function (index) {
                cancelOrder();
                layer.close(index);
            }, function (index) {
                layer.close(index);
            });
        }
        pushHistory();
    }, false);
}

// 手机号码校验
function checkPhone(phone) {
    var flag = true;
    if (!(/^1(3|4|5|7|8)\d{9}$/.test(phone))) {
        flag = false;
    }
    return flag;
}

//去除数组中重复对象
function arrayUnique(array) {
    var unique = {};
    array.forEach(function (a) {
        unique[JSON.stringify(a)] = 1;
    });
    array = Object.keys(unique).map(function (u) {
        return JSON.parse(u);
    });
    return array;
}


function callFunctionEstimatedPrice() {
    var $tabSelect = $(".airport-selected");
    var type = $tabSelect.data('type');
    var destLocation = null;

    var isNone = function ($selected) {
        var lng = $selected.data('lng'),
            lat = $selected.data('lat');
        if (lng === "" || lat === "") {
            return null;
        } else {
            return [lng, lat];
        }
    }
    if (type === "dropOff") { // 送机
        var $selected = $("#zhongdian_input").find("option:selected");
        var ret = isNone($selected);
        if (ret != null) {
            var elng = ret[0];
            var elat = ret[1];
            setArport_uuid($selected.attr('data-city'));
            destLocation = getLocalStorage(START_LOCATION_INFO, "object");
            if (destLocation != undefined || destLocation != null) {
                var curLocation = destLocation.location;
                if (curLocation != "" && curLocation != undefined) {
                    var slng = curLocation.lng;
                    var slat = curLocation.lat;
                    calculatePricePromise(slng, slat, elng, elat);
                }
            }
        }
    } else if (type === "pickUp") { // 接机
        var $selected = $("#qidian_input").find("option:selected");
        var ret = isNone($selected);
        if (ret != null) {
            var slng = ret[0];
            var slat = ret[1];
            setArport_uuid($selected.attr('data-city'));
            destLocation = getLocalStorage(END_LOCATION_INFO, "object");
            if (destLocation != undefined || destLocation != null) {
                var curLocation = destLocation.location;
                if (curLocation != "" && curLocation != undefined) {
                    var elng = curLocation.lng;
                    var elat = curLocation.lat;
                    calculatePricePromise(slng, slat, elng, elat);
                }
            }
        }
    }
}

function calculatePricePromise(slng, slat, elng, elat) {
    var promise = getRouteInfo(slng, slat, elng, elat);
    promise.then(function (flag) {
        // 重新计算价格
        if (flag) {
            var carId = $('.cartype-selected').eq(0).data('modelnum');
            estimatedPrice(carId);
        }
    });
}

// 获取路线信息
function getRouteInfo(slng, slat, elng, elat) {
    var promise = new Promise(function (resolve) {
        if (slng == undefined || slat == undefined || elng == undefined || slng == elat) {
            resolve(false);
            return;
        }
        // 根据起终点经纬度调取后台统一返回路线行驶距离和时间
        $.post("/wechat/price/disAndDura", {
            oriLng: slng,
            oriLat: slat,
            destLng: elng,
            destLat: elat
        }, function (res) {
            resolve(res.success);
            if (res.success) {
                var data = res.data;
                setLocalStorage('routeInfo', data);
                resolve(res.success);
            }
        });
    });
    return promise;
}

// 获取时间戳
//现在默认叫即时单，到时候通过选择的时间判断叫预约单或者即时单
function getDepartTime() {
    var map = {};
    var timeVal = $("#valueTo").val();
    var type = '0', hours = "", minutes;
    if (timeVal != "") {
        var timeList = timeVal.split(':');
        type = timeList[0];
        hours = +timeList[1];
        minutes = +timeList[2];
    }
    if (type == '0') // 如果type为0，则为即时单
        return map = {
            type: type,
            orderType: 5, // 立即用车
            departTime: formatDate(new Date())
        };
    var departTime = setDate(type, hours, minutes);
    return map = {
        type: type,
        orderType: 1, // 预约用车
        departTime: formatDate(departTime)
    }
}

function formatDate(departTime) {
    var month = departTime.getMonth() + 1 < 10 ? "0" + (departTime.getMonth() + 1) : departTime.getMonth() + 1;
    var date = departTime.getDate() < 10 ? "0" + departTime.getDate() : departTime.getDate();
    var hours = departTime.getHours() < 10 ? "0" + departTime.getHours() : departTime.getHours();
    var minutes = departTime.getMinutes() < 10 ? "0" + departTime.getMinutes() : departTime.getMinutes();

    return departTime.getFullYear() + "-" + month + "-" + date + " " + hours + ":" + minutes;
}

function setDate(type, hours, minutes) {
    var curDate = new Date();
    var date;
    switch (type) {
        case "2":
            date = curDate.getDate() + 1;
            break;
        case "3":
            date = curDate.getDate() + 2;
            break;
        default:
            date = curDate.getDate();
            break;
    }
    curDate.setHours(hours);
    curDate.setMinutes(minutes);
    curDate.setDate(date);
    return curDate;
}

//根据不同订单状态跳转页面
function changeUrl(subStstus, orderId) {
    var openId = getLocalStorage('openId');
    var timeZone = localStorage.getItem('TIME_ZONE');
    var h='';
    if (subStstus === 100) {
      h = '/wechat/order/wreply/' + openId + '/' + orderId + '?orderUuid=' + orderId + '/' + timeZone;
    } else if (subStstus === 200) { // 预约
      h = '/wechat/order/wmeeting1/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 201) { // 即时
      h = '/wechat/order/wmeeting/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 202) { // 待上车
      h = '/wechat/order/wtakecar/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 300) { // 行程中
      h = '/wechat/order/onway/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 301) { // 行程中
      h = '/wechat/order/onway/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 400 || subStstus === 402 || subStstus === 401) { // 400待支付; 402部分支付
      h = '/wechat/order/wpay/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else if (subStstus === 500) { // 待评价
      h = '/wechat/order/wrated/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId;
    } else { // 取消
        // 跳转到一个订单无人接收自动取消界面，目前貌似没有
      h = '/wechat/view/index';
    }
    if(window.location.href.indexOf(h)==-1){
        window.location.href=h;
    }
    /*else if (subStstus == 501) { // 评价完成
     //司机确认费用
     window.location.href = "/wechat/order/complete/" + openId + "/" + orderId;
     }*/

}


//根据车型调用预估接口
function estimatedPrice(carId,isqi) {
    var routeInfo = getLocalStorage('routeInfo', 'object');
    var departTimeMap = getDepartTime();
    var openId = getLocalStorage('openId');
    var pd = routeInfo.duration / 60;
    setArport_uuid($('#qidian_input').find('option:selected').attr('data-city'));
    var data1 = {
        openId: openId,
        //orderType: departTimeMap.orderType,
        orderType: $('.airport-selected').attr('data-orderType'),
        levelType: $('#takemanBox').find('.cartype-selected').attr('data-modelnum'),
        planMileage: routeInfo.distance / 1000,//预估里程(公里),
        planDuration: pd + 1,//预估时长(分钟),
        deparTimeStr: departTimeMap.departTime,
        userCouponUuid:window.userCouponUuid,
        areaUuid:getLocalStorage(OPENED_ARPORT_UUID,'string')
    };
    if($('[data-prepaytype="1"]').length>0){
        data1.userCouponUuid='-1';
    }
    $.ajax({
        url: "/wechat/price/estimatedFare",
        type: "POST",
        dataType: 'json',
        data: data1,
        success: function (result) {
            window.userCouponUuid='';//清除优惠券ID，避免下次又发同样的优惠券。
            //赋值预估信息
            if (result.success) {
                var data = result.data;
                data.totalFee=parseFloat(data.totalFee);
                data.couponMoney=parseFloat(data.couponMoney || 0);
                $("#moneyText").html(parseInt(data.totalFee-data.couponMoney));
                $("#companyMoney").val(data.entAvailableBalance);
                $("#personMoney").val(data.balance);
                $("#isShowEnt").val(data.isShowEnt);
                $("#couponMoney").val(data.couponMoney || "0");
                $("#userCouponUuid").val(data.userCouponUuid || "");
                $("#totalFee1").val(data.totalFee);
                $("#areaUuid").val(data1.areaUuid);
                $("#personMoneyShow").html(data.balance);
                setLocalStorage('personMoney', data.balance);// 个人账户可用余额,供支付页面显示

                if($('[data-prepaytype="1"]').length>0){
                    $('#selectCou').hide();
                    $("#couponMoney").val("");
                    $("#userCouponUuid").val("");
                    $("#moneyText").html(parseInt(data.totalFee));
                }else{
                    $('#selectCou').show();
                    $('#selectCou').find('.f-fcf16565').html((data.couponMoney).toFixed(0));
                    //用于选择优惠券页面的查看
                    $('#selectCou').attr('data-parme','orderType='+data1.orderType+'&areaUuid='+data1.areaUuid+'&couponUuid='+data.userCouponUuid);
                }
                /*结合区域UUID下的sysAreaConfigDto.prepaidStatus ==1 处理预支付订单*/
                /*
                * prepaidStatus; //预支付状态
                  prepaidPercent; //预支付比例
                  prepaidTime; //预支付时长
                * */
                // ---------将预支付规则缓存本地
                var OPENED_ARPORT = getLocalStorage('OPENED_ARPORT', 'object'),
                    OPENED_ARPORT_UUID = getLocalStorage('OPENED_ARPORT_UUID');
                for (var i = OPENED_ARPORT.length - 1; i >= 0; i--) {
                    if (OPENED_ARPORT_UUID == OPENED_ARPORT[i].uuid) {
                        var sysAreaConfigDto = OPENED_ARPORT[i].sysAreaConfigDto;
                        setLocalStorage('sysAreaConfigDto', sysAreaConfigDto)
                    }
                }
                // 执行预支付判断
                prePaymentOrNo();
            } else {
                layer.confirm("" + result.msg + "。如有疑问请联系客服", {
                    title: "错误信息",
                    btn: ['联系客服', '我知道了']
                }, function (index) {
                    $('.layui-layer-btn0').prop('href',"tel:0592-96363");
                    // layer.close(index);
                }, function (index) {
                    layer.close(index);
                    initLoginPage();
                });

                $("#moneyText").html(0);
            }
        },
        error:function () {
            window.userCouponUuid='';//清除优惠券ID，避免下次又发同样的优惠券。
            layer.msg('请求异常，请联系管理员');
        }
    });
}

// 接送机起点或者终点的机场数据集合
function setSelectOptions($select,startingEnd) {
    var city = getLocalStorage('city');
    $.ajax({
        url: "/api/base/getLocations?areaUuid=" + city,
        type: "GET",
        success: function (result) {
            var cd=[],d=result.data;

            for(var i=0;i<d.length;i++){
                var di=d[i];
                if(di.startingEnd==startingEnd || di.startingEnd==3){
                    cd.push(di);
                }
            }

            var html = template('airportTemp', {list: cd});
            $select.html(html);
            var flag = callFunctionEstimatedPrice();
            // var $tabselect = $('.airport-selected').eq(0);
            letShow();
            /*var type = $tabselect.data('type');
             if (flag && type === "dropOff") {

             }*/
        }
    });
}

// 校验用户是否注册后台
function checkedWechatUserIsBind() {
    var openId = getLocalStorage('openId');
    $.ajax({
        url: "/wechat/passenger/login/validateOpenId",
        type: "POST",
        data: {openId: openId},
        success: function (result) {
            var isBind = result.success;
            if (isBind) {
                // setLocalStorage('isBind', isBind); // 已经成功绑定微信用户
                var passengerUuid = result.data.uuid;
                checkUserOrderStatus(passengerUuid);
                $('.user-mobile').attr('data-mobile',(result.data.mobile || ''))
                $('.user-mobile').html((result.data.mobile || '').replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'))
            } else {
                initLoginPage();
                /*var promise = initLoginPage().then(function (flag) {
                 if (flag)
                 addExtraCloseBtn();
                 });*/
            }
        },
        error: function (data) {
            alert(data)
        }
    });
}

// 查看是否有正在进行的订单
function checkUserOrderStatus(passengerUuid) {
    $.post("/wechat/order/jugStatus", {passengerUuid: passengerUuid}, function (result) {
        if (result.success) {
            var order = result.data;

            if (order.defaultOrders) { // 默认订单状态
                changeUrl(order.defaultOrders.subStatus, order.defaultOrders.uuid);
            } else
            if (order.ongoingOrders) { // 存在进行中的订单
                if (order.ongoingOrders.subStatus != 200 && order.ongoingOrders.subStatus != 201 ){
                    changeUrl(order.ongoingOrders.subStatus, order.ongoingOrders.uuid);
                }
                // 判断是否存在预支付及其状态
                switch (order.ongoingOrders.prepaidStatus) {
                    case 1:
                    case 2:
                    case 4:
                    { // 预支付订单 待支付 支付中 支付异常
                        var openId = getLocalStorage('openId');
                        window.location.href = "/wechat/order/wprepayment/" + openId + "/" + order.ongoingOrders.uuid;
                    }
                        return;
                    case 0:
                    case 3:
                    { // 无需预支付----预支付订单已支付
                        var now = Date.now();
                        if ((order.ongoingOrders.deparTime >= now + 1800000)) {
                            // 当出发时间超过30分钟后，默认停留本页面
                            return
                        }
                        changeUrl(order.ongoingOrders.subStatus, order.ongoingOrders.uuid)
                    }
                        break;
                    default:
                        return;
                }
            } else if (order.toPayOrders) { // 存在待支付订单
                changeUrl(order.toPayOrders.subStatus, order.toPayOrders.uuid)
            }

        }
        //var order = result.data.defaultOrders; // 等待应答的订单
        //if (order === null) {
        //    order = result.data.ongoingOrders; // 进行中的订单
        //        var now = Date.now()
        //    if (order === null) {
        //        if(order.prepaidStatus == '2'){
        //            //跳转到预支付待支付页面
        //            var openId = getLocalStorage('openId');
        //            window.location.href = "/wechat/order/wprepayment/" + openId + "/" +  passengerUuid;
        //        }else{
        //            order = result.data.toPayOrders; // 待支付订单
        //        }
        //    }else if((order.deparTime >= now + 1800000)){
        //    // 若订单已完成预支付且出发时间在30分钟后，该状态下不执行页面跳转
        //        return
        //    }
        //}
        //if (result.success && order != null) {
        //    changeUrl(order.subStatus, order.uuid);
        //}
    });
}
// 待上车取消订单，获取取消费
function cancelOrderNeedToPay() {
    var orderUuid = getLocalStorage('currentOrderId', 'string');
    var openId = getLocalStorage('openId', 'string');
    $.post("/wechat/order/preCancel", {orderUuid: orderUuid, openId: openId}, function (result) {
        delLocalStorage(orderUuid)
        if (result.success) {
            var data = result.data;
            window.location.href = "/wechat/view/cancelOrderNeedToPay?money=" + data.preCancelFee + "&orderUuid=" + orderUuid;
        }
    });
}
// 获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
//调用后台取消接口
function cancelOrder() {
    var orderUuid = getLocalStorage('currentOrderId', 'string');
    var openId = getLocalStorage('openId', 'string');
    $.post("/wechat/order/cancel", {orderUuid: orderUuid, openId: openId}, function (result) {
        if (result.success) {
            // TODO 取消订单，如果订单状态为401时，为取消订单收取费用，需要调取支付
            var data = result.data;
            if (data.needToPay) { // 需要支付取消费用
                if(!!data.waitPayFee){
                    window.location.href = "/wechat/view/paydialog?type=0&money=" + data.waitPayFee + "&orderUuid=" + orderUuid;
                }else{
                    window.location.href = "/wechat/view/index";
                }

            } else if (data.needToEvaluation) {
                window.location.href = "/wechat/order/wrated/" + openId + "/" + orderUuid;
            } else {
                window.location.href = "/wechat/view/index";
                delLocalStorage('currentOrderId'); // 移除本地当前订单id存储
                delLocalStorage('prePaymentTime'); // 移除本地当前订单预支付有效时间
            }
        } else {
            alert('订单取消失败！');
        }
    });
}

// 避免还未加载完页面点击下拉，导致触发select默认样式
function comboBoxClickInit() {
    var $this = $("#zhongdian_input");
    var flag = $this.data('click');
    if (flag) {
        $this.prop("disabled", "disabled");
        // 禁止弹出软键盘
        document.activeElement.blur();
    }
}

// 登录页弹窗初始化
// 登录页弹窗初始化
var isLoginPage=false;//当前页面只能显示一次
function initLoginPage() {
    if(isLoginPage){
        return;
    }
    if(window.location.href.indexOf('/wechat/view/login')>-1){
        return;
    }
    isLoginPage=true;
    layer.open({
        type: 2,
        title: "验证手机号，方便司机联系您",
        area: ['80%', '310px'], //宽高,
        shadeClose: false,
        skin: 'layer-user-login',
        closeBtn: false,
        content: '/wechat/view/login',
        success:function (index) {
            index.addClass('radius')
        }
    });

    /*var flag = false;
     var promise = new Promise(function (resovle) {

     flag = true;
     resovle(flag);
     });
     return promise;*/
}
//扩展Date输出格式
Date.prototype.format = function(format) {
    var date = {
        "M+": this.getMonth() + 1,
        "D+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}
//设置区域UUID
function setArport_uuid(arport){
    var arport=arport || '厦门市';
    var arportlist=getLocalStorage(OPENED_ARPORT,'object');
    var uuid='';
    if(!arportlist){
        setTimeout(setArport_uuid(arport),60)
        return;
    }
    for(var i=0;i<arportlist.length;i++){
        var ai=arportlist[i];
        //对城市区域解析
        var ci=ai.cityNames.split(',');
        for(var j=0;j<ci.length;j++){
            var cij=ci[j];
            if(!!cij){
                if(arport.indexOf(cij)>-1){
                    uuid=ai.uuid;
                    break;
                }
            }
        }
    }
    // console.log(uuid);
    setLocalStorage(OPENED_ARPORT_UUID,uuid);
    return uuid;
}
//ajax 封装
var yx={};
yx.ajax=function(){
    this.init.apply(this, arguments);
}
yx.ajax.prototype={
    init:function(){
        this.openId=getLocalStorage('openId','string') || window.openId || '';
        if(!this.openId){
            //initLoginPage();
        };
    },
    yxajax:function(params, url, successCallBack, errorCallBack){
        params.openId=this.openId;
        this.ajax(params,url, successCallBack, errorCallBack,"GET");
    },
//ajax封装
    ajax: function (params, url, successCallBack, errorCallBack, type) {
        var t = this;
        var ajaxObject = {
            type: !type ? 'POST' : type,
            url: url,
            data: params,
            dataType: 'json',
            cache: false,
            success: function (result) {
                if (result.success) {
                    successCallBack(result);
                } else {
                    errorCallBack(result);
                }
            },
            error: function () {
                layer.msg('请求异常，请联系管理员');
            }
        };
        $.ajax(ajaxObject);
    },
    //    时间计算：明后天和上下午计算
    time_count:function (time) {
        var time=time;
        var now=new Date();
        var hour = time.getHours(),hourName=""
        if(hour < 6){hourName="凌晨"}
        else if (hour < 9){hourName="早上"}
        else if (hour < 12){hourName="上午"}
        else if (hour < 14){hourName="中午"}
        else if (hour < 17){hourName="下午"}
        else if (hour < 19){hourName="傍晚"}
        else if (hour < 22){hourName="晚上"}
        else {hourName="夜晚"}
        var dayName={
            '-2':'前天',
            '-1':'昨天',
            '0':'今天',
            '1':'明天',
            '2':'后天',
        }
        function zeroPoint(dc) {//传入时间戳，返回凌晨时间，用来两个时间相减后计算dayName
            var d=new Date(dc);
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
            //时间戳是毫秒级的，所以也需要把时间秒后面的多出来的毫秒给归零
            d=d-d%1000;
            return d;
        }

        // var dates = Math.abs(Math.floor(((now - time))/(1000*60*60*24)));
        //当前时间减去需要计算的时间。向下四舍五入
        var dates = Math.floor((zeroPoint(time) - zeroPoint(now))/(1000*60*60*24));

        var dN;
        dN=dayName[dates] || time.format('MM月DD日');
        return {
            day:dN,
            hour:hourName
        }
    },
    // 千夏编写的rendering方法
    rendering:function(str,obj){
        if(!obj){return str;}
        var html='', str=str;
        for(var i in obj){
            var rex=new RegExp('{{'+i+'}}','g');
            str=str.replace(rex,obj[i] || "");
        }
        return str;
    }
}
window.yxAjax=new yx.ajax();

// 添加关闭弹窗按钮
// function addExtraCloseBtn() {
//     var $layer = $('.layui-layer'),
//         height = $layer.css('height'),
//         top = $layer.css('top'),
//         width = $layer.css('width'),
//         left = $layer.css('left'),
//         zIndex = $layer.css('z-index');
//
//     var $close = $('<div class="closeExtraDiv"><a id="closeExtra" href="javascript:void(0);"><img src="/pub/images/wx/closeImg.png" /></a></div>');
//
//     top = +(top.substring(0, top.indexOf('px')));
//     height = +(height.substring(0, height.indexOf('px')));
//
//     $close.css({
//         'z-index': '19910927'
//     }).animate({
//         'width': width,
//         'height': '35px',
//         'top': (top + height) + 'px',
//         'left': left
//     }).find('img').css({
//         'height': '80px'
//     });
//
//     $close.insertAfter($layer);
// }

// ---------根据预支付规则计算预支付费用情况
function prePaymentOrNo() {

    // 获取出发时间时间戳
    var departTime = Date.parse(new Date(getDepartTime().departTime.replace(/-/g, '/')));
    // 获取当前时间时间戳
    var nowDate = Date.parse(new Date());
    // 从系统获取时间
    var systemTime = new Date();
    // 将从系统获取的时间转换为字符串
    systemTime = systemTime.toString();
    // 获取所在时区的字符串
    var timaArea = systemTime.slice(systemTime.length - 12, systemTime.length - 11);
    // 将所在时区的字符串转换为数字
    timaArea = parseInt(timaArea);
    if (timaArea === 8) {
        departTime = departTime * 1;
        nowDate = nowDate * 1;
    }
    if (timaArea === 0) {
        departTime = departTime + 8 * 60 * 60 * 1000;
        nowDate = nowDate + 8 * 60 * 60 * 1000;
    }
    var sysAreaConfigDto = getLocalStorage('sysAreaConfigDto', 'object');
    var totalFee =+ $('#totalFee1').val(),
        pay_type = $('#pay_type').children("span:first-child").attr('data-prepaytype'),
        couponMoney =+ $('#couponMoney').val();
    if ((departTime >= (nowDate + sysAreaConfigDto.prepaidTime * 60000) || sysAreaConfigDto.prepaidTime == 0 ) && sysAreaConfigDto.prepaidStatus && pay_type == 2) {
        // 满足预支付规则，但预支付金额为0
        if(Math.floor(parseInt((totalFee - (couponMoney).toFixed(0)) * sysAreaConfigDto.prepaidPercent / 100))<1){
            $('#prePayment').hide();
            $('#prePaymoney').html('0');
            return;
        }else {
            // 判断是否需要预支付且满足预支付时长限制
            $('#prePayment').show();
            $('#prePaymoney').html(Math.floor(parseInt((totalFee - (couponMoney).toFixed(0)) * sysAreaConfigDto.prepaidPercent / 100)));
        }
    } else {
        $('#prePayment').hide();
        $('#prePaymoney').html('0');
    }
}

// --------福州区域隐藏‘一口价’描述----2018/4/3----
(function() {
    var areaUuid = getLocalStorage('areaUuid');
    if (areaUuid === 'cb33bbcc0e8c400f992c4d1acff274cb') {
        $('.js_fz_hidden').hide();
    }
})();