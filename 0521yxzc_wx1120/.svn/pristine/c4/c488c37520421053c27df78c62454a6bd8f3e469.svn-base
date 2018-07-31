jQuery(document).ready(function ($) {
    var $document = $(document);

    // 首次进入界面，初始化接机起点数据
    comboBoxClickInit();
    setSelectOptions($('#qidian_input'),1);

    // 初始化登录弹窗界面
    initPage();
    function initPage() {

        // 初始化车型数据
        getCarTypeList();

        // 检查是否绑定微信用户
        checkedWechatUserIsBind();
    }

    // 获取车型数据
    function getCarTypeList() {
        $.ajax({
            url: "/api/base/getCarModels",
            data:{ areaUuid: getLocalStorage('OPENED_ARPORT_UUID')},
            type: "GET",
            success: function (result) {
                var data = result.data;
                var html = template('carTypeTemp', {list: data});
                $('#takemanBox').html(html);
            }
        });
    }

    // 页面回退监听
    pushHistory();
    window.addEventListener("popstate", function (e) {
        letShow();
    }, false);

    // 初始化--出发时间选择
    var area1 = new LArea();
    area1.init({
        'trigger': '#timeshow', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
        'valueTo': '#valueTo', //选择完毕后id属性输出到该位置
        'keys': {
            id: 'id',
            name: 'name'
        }, //绑定数据源相关字段id对应valueTo的value属性输出name对应trigger的value属性输出
        'type': 1, //数据源类型
        'data': LAreaData //数据源
    });

    // 点击呼叫专车
    var clickCount = 0;
    $document.on('click', '.btn-call-abled', function (event) {
        event.preventDefault();
        event.stopPropagation();

        var appointment = $("#valueTo").val();
        if (appointment === "") {
            layer.msg('请选择出发时间');
            return;
        }

        var openId = getLocalStorage('openId', 'string');

        //起始点距离
        var routeInfo = getLocalStorage('routeInfo', 'object');
        // 目的地
        var destLocation = null;

        var departTimeMap = getDepartTime();//预约单有的预约时间

        var city = getLocalStorage('city');

        var bizType = $('.airport-selected').eq(0).data('type');

        var prepayType = $('#pay_type').find('span.light').eq(0).data('prepaytype');

        if (bizType === "pickUp") {// 接机
            destLocation = getLocalStorage(END_LOCATION_INFO, 'object');
        } else if (bizType === "dropOff") { // 送机
            destLocation = getLocalStorage(START_LOCATION_INFO, 'object');
        }

        var vipCount = $('.vip-passageway .num-input').val();
        //处理地址过长
        if((destLocation.address).length>47){
            destLocation.address=((destLocation.address).substring(0,47)+'...')
        }
        var dataParam = {
            openId: openId, // 微信openid

            prepayType: prepayType,  // 预支付方式（1企业支付，2个人支付）

            originAddress: destLocation.name,//起点的详细地址,
            originDetailAddress: destLocation.district + destLocation.address,//起点的详细地址,
            originCity: destLocation.district,//起点城市,
            originLng: destLocation.location.lng,//起点精度,
            originLat: destLocation.location.lat,//起点纬度,

            destCity: destLocation.district,//终点城市,
            destLng: destLocation.location.lng,//终点精度,
            destLat: destLocation.location.lat,//终点纬度,
            destAddress: destLocation.name,//终点的详细地址,
            destDetailAddress: destLocation.district + destLocation.address,//终点的详细地址,

            planTrip: routeInfo.distance / 1000,//预估里程(公里)  (单位是米/m)  取时间 routeInfo.time (单位是秒/s),
            planFare: +$("#totalFee1").val(),//预估价格

            levelType: $('.cartype-selected').eq(0).data('modelnum'), // 车型级别

            deparTime: departTimeMap.departTime,  // 预约时间
            //orderType: departTimeMap.orderType,// 订单类型
            orderType: $('.airport-selected').attr('data-orderType'),// 订单类型
            areaUuid: $("#areaUuid").val(),
            remark: "",
            prepaidFee: $('#prePaymoney').text(),//预支付金额，默认为0
            actualPassengeMobile:$('.user-mobile').attr('data-mobile'),//乘车人电话号码,必填
            actualPassengeName:'',//乘车人姓名
            payToObject:'',
            flightNum:'',//航班号
            flightTime:'',//航班起飞时间
        };

        if (bizType === "pickUp") {// 接机
            var $select = $('#qidian_input option:selected');
            var address = $select.text();
            dataParam.originAddress = address;
            dataParam.originDetailAddress = address;
            dataParam.originCity = $select.data('city');
            dataParam.originLng = $select.data('lng');
            dataParam.originLat = $select.data('lat');
        } else if (bizType === "dropOff") { // 送机
            var $select = $('#zhongdian_input option:selected');
            var address = $select.text();
            dataParam.destCity = $select.data('city');
            dataParam.destLng = $select.data('lng');
            dataParam.destLat = $select.data('lat');
            dataParam.destAddress = address;
            dataParam.destDetailAddress = address;
        }
        var juli=distanceByLnglat(dataParam.originLng,dataParam.originLat,dataParam.destLng,dataParam.destLat)/1000;
        if(juli>800 ){
            layer.msg('仅提供800公里以内的服务');
            return;
        }else if(juli<2){
            layer.msg('仅提供大于2公里的服务');
            return;
        }
        if(prepayType==1){
            dataParam.preChoiceUserCouponUuid="-1";
        }else{
            dataParam.preChoiceUserCouponUuid= $("#userCouponUuid").val() || -1;
        }

        var flag = $('.vip-passageway .weixuanzhong').data('checked');
        if (flag) {
            dataParam.vipCount = vipCount;
        }
        if (clickCount == 0 && flag && bizType === "dropOff") { // 满足条件：1、送机业务；2、选中；
            vipPassgeWayTips(clickCount, openId, dataParam);
        } else if (clickCount == 0) {
            postData(clickCount, openId, dataParam);
        }
    });
    /*计算经纬度两点之间得直线距离*/
    function distanceByLnglat(lng1,lat1,lng2,lat2)
    {
        var radLat1 = Rad(lat1);
        var radLat2 = Rad(lat2);
        var a = radLat1 - radLat2;
        var b = Rad(lng1) - Rad(lng2);
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
        // //下面为两点间空间距离（非球面体）
        // var value= Math.pow(Math.pow(lng1-lng2,2)+Math.pow(lat1-lat2,2),1/2);
        // alert(value);
    }

    function Rad(d)
    {
        var d=parseFloat(d);
        return d * Math.PI / 180.0;
    };
    /*计算经纬度两点之间得直线距离 END*/


    /*function checkPlanFare() {
        var planFare = +$("#moneyText").text();
        if (isNaN(planFare) || planFare <= 0) {
            var carId = $('.cartype-selected').eq(0).data('modelnum');
            estimatedPrice(carId);
        }
    }*/

    var vipPassgeWayTips = function (clickCount, openId, dataParam) {
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            area: ['80%', ''],
            skin: 'layer-vip-passage-tip',
            content: '<div class="vip-passageway-tip">' +
            '<img style="width: 100%; margin-top: -1px;" src="/pub/images/wx/vip-tip.png" alt="">' +
            '<p class="tip-1">温馨提示</p>' +
            '<p class="tip-2">完成专车服务后，将赠送您<span id="vip_num"></span>位头等舱通道服务</p>' +
            /*'<a class="btn-ok" href="javascript:layer.closeAll();">好的，谢谢</a>' +*/
            '</div>',
            btn: ['好的，谢谢'],
            btn1: function (index) {
                layer.close(index);
                postData(clickCount, openId, dataParam);
            }
        });
        $('#vip_num').text($('.vip-passageway .num-input').val());
        $('.layui-layer .layui-layer-btn0').addClass('btn-ok');
    }
    var isAdvance=null;
    function postData(clickCount, openId, dataParam) {
        if(!!isAdvance){
            return;
        }
        clickCount++;
        isAdvance=$.ajax({
            type: "POST",
            url: "/wechat/order/advance",
            dataType: "json",
            data: dataParam,
            success: function (res) {
                if (res.success) {
                    //    呼叫专车成功
                    setLocalStorage('currentOrderId', res.data);
                    window.location.href = "/wechat/order/wreply/" + openId + "/" + res.data;

                } else {
                    isAdvance=null;
                    alert(res.msg);
                }
            },
            error: function (result) {
                console.log(result)
                isAdvance=null;
            }
        });
    }

    // 起点下拉点击
    $document.on('touchend', '#qidian_input', function (event) {
        event.stopPropagation();
//            event.preventDefault();
        var $this = $(this);
        comboBoxClick($this, "start");
    });
    // 起点下拉数据更改
    $document.on('change', '#qidian_input', function (event) {
        event.stopPropagation();
        var $this = $(this);
        var flag = $this.data('click');
        console.log(flag)
        if (!flag) {
            // 预估价格
            callFunctionEstimatedPrice();
            letShow();
        }
    });

    // 终点下拉点击
    $document.on('touchend', '#zhongdian_input', function (event) {
        event.stopPropagation();
        var $this = $(this);
        comboBoxClick($this, "end");
    });

    // 终点下拉数据更改
    $document.on('change', '#zhongdian_input', function (event) {
        event.stopPropagation();
        var $this = $(this);
        var flag = $this.data('click');
        if (!flag) {
            // 预估价格
            callFunctionEstimatedPrice();
            letShow();
        }
    });

    // 选择车型类型
    $document.on('touchend', '.cartype', function (event) {
        var $this = $(this);
        var status = $this.data('status'),
            modelnum = $this.data('modelnum');
        var num = $('.vip-passageway .num-input').val();
        if (status == -1) {
            layer.msg("暂未开通！");
            return;
        }
        if (modelnum == 1 && num > 4) {
            layer.msg("无法选择，赠送服务人数已超过该车型（4人）限制");
            return;
        }
        var type = $this.data('type');
        $this.addClass('cartype-selected').siblings().removeClass('cartype-selected');
        var $img = $this.find('img');
        $img.prop('src', '/pub/images/wx/order_car_checked_' + type + ".png");
        var $otherImgs = $this.siblings().find('img');
        for (var i = 0; i < $otherImgs.length; i++) {
            var $imgObj = $otherImgs.eq(i);
            var type = $imgObj.parent().data('type');
            $imgObj.prop('src', '/pub/images/wx/order_car_default_' + type + ".png");
        }

        // 预估价格
        var carId = $this.data('modelnum');
        estimatedPrice(carId);
    });

    // 取消
    $document.on('touchend', "#cancelBtn", function (event) {
        $('.amap-sug-result').remove();
        var $tabSelect = $('.airport-selected');
        var type = $tabSelect.data('type');
        if (type === "pickUp") { // 接机
            setName("你要去哪儿", "");
        } else if (type === "dropOff") { // 送机
            var currentL = getLocalStorage(START_LOCATION_INFO, 'object');
            if(!!currentL){
                setName(currentL.name, currentL.adcode );
            }else{
                setName('请您选择地址' );
            }
        }
        letShow();
        $('.kucity').hide();
    });

    // 起始点搜索input获取焦点监听
    $document.on('focus', "#tipinput", function (event) {
        $('.searchResultList').addClass('hide');
        $('.kucity').hide();
        appendHistory();
    });

    // 起始点搜索input输入监听
    $document.on('keyup input propertychange', "#tipinput", function (event) {

        var $this = $(this),
            keyword = $this.val();

        if (keyword.length > 0) {
            $('#input_delete').removeClass('hide');
            autoComplete(keyword);
        } else {
            $('#input_delete').addClass('hide');
        }
        $('#historyBox').addClass('hide').html('');
    });

    // 历史记录点击
    var toucheFlag = false;
    $document.on('touchstart touchmove touchend', '.history-item', function (event) {
        event.stopPropagation();
        switch (event.type) {
            case 'touchstart':
                toucheFlag = false;
                break;
            case 'touchmove':
                toucheFlag = true;
                break;
            case 'touchend':
                if (!toucheFlag) {
                    $('#qidian_input').prop('disabled', true);
                    var $this = $(this);
                    var $div = $this.clone();
                    $div.find(':nth-child(n)').remove();
                    var name = $div.html();
                    var data = $this.data('item');
                    validateHasLocationInfo(data);
                }
                toucheFlag = false;
                break;
        }
    });

    // 接送机tab切换
    $document.on('touchend', '.airport', function (event) {
        var $this = $(this);
        $this.addClass('airport-selected').parent('.col-xs-6')
            .siblings().find('.airport-selected').removeClass('airport-selected');

        $('#timeshow').text('请选择出发时间');
        $("#valueTo").val('');

        cancelInputOrBack();
    });

    // VIP值机通道-选中切换
    $document.on('touchend', '.vip-passageway .weixuanzhong', function (event) {
        var $this = $(this),
            flag = $this.data('checked');
        if (flag) {
            $this.prop('src', '/pub/images/wx/weixuanzhong_icon.png');
            $this.data('checked', false);
            $('.vip-passageway .opt-minus').removeClass('opt-minus-able');
            $('.vip-passageway .opt-plus').removeClass('opt-plus-able');
            $('.vip-passageway .num-input').val(1);
            $this.closest('.vip-passageway').removeClass('check-border');
        } else {
            $this.prop('src', '/pub/images/wx/xuanzhong_icon.png');
            $this.data('checked', true);
            $('.vip-passageway .opt-minus').addClass('opt-minus-able');
            $('.vip-passageway .opt-plus').addClass('opt-plus-able');
            $this.closest('.vip-passageway').addClass('check-border');
        }
    });

    // VIP值机通道-减人数
    $document.on('touchend', '.vip-passageway .opt-minus-able', function (event) {
        setPassagerNum("minus");
    });

    // VIP值机通道-加人数
    $document.on('touchend', '.vip-passageway .opt-plus-able', function (event) {
        setPassagerNum("plus");
    });

    // VIP值机通道-手动输入
    /*$document.on('blur', '.vip-passageway .num-input', function (event) {
     setPassagerNum("blur");
     });*/

    function setPassagerNum(type) {
        var $num = $('.vip-passageway .num-input').eq(0);
        var value = +$num.val();
        var $minus = $('.vip-passageway .opt-minus').eq(0);
        var $plus = $('.vip-passageway .opt-plus').eq(0);
        switch (type) {
            case 'minus':
                if (value < 2) {
                    value = 1;
                } else {
                    value = value - 1;
                    if (value == 1) {
                        $minus.addClass('opt-disable');
                    }
                    $plus.removeClass('opt-disable');
                }
                break;
            case 'plus':
                if (value > 5) {
                    value = 6;
                } else {
                    value = value + 1;
                    if (value == 6) {
                        $plus.addClass('opt-disable');
                    }
                    $minus.removeClass('opt-disable');
                }
                break;
            case 'blur':
                if (value < 2) {
                    value = 1;
                    $minus.addClass('opt-disable');
                    $plus.removeClass('opt-disable');
                } else if (value > 5) {
                    value = 6;
                    $plus.addClass('opt-disable');
                    $minus.removeClass('opt-disable');
                } else {
                    $plus.removeClass('opt-disable');
                    $minus.removeClass('opt-disable');
                }
                break;
        }
        $num.val(value);

        if (value > 4) {
            $('.cartype-economical').trigger('touchend');
        } else {
            $('.cartype').eq(0).trigger('touchend');
        }
    }

    // 支付方式切换
    $document.on('click', '.pay-types .ptype', function (event) {
        layer.open({
            type: 2,
            title: false,
            closeBtn: false,
            shadeClose: false,
            area: ['95%', '130px'], //宽高,
            shadeClose: true,
            anim: 'up',
            content: '/wechat/view/payType?v=' + new Date().getTime(),
        });
    });

    // 根据tab的类型设置对应select数据
    function setSelectDataByType(type) {
        var $qidian = $('#qidian_input'),
            $zhongdian = $('#zhongdian_input');
        var $optionDefault = $('<option selected disabled value=""></option>');
        if (type === "pickUp") { // 接机
            $zhongdian.data('click', true).html('');
            $optionDefault.text('你要去哪儿').appendTo($zhongdian);
            $qidian.data('click', false);
            setSelectOptions($qidian,1);
        } else if (type === "dropOff") { // 送机
            $qidian.data('click', true).html('');
            var currentLocation = getLocalStorage(START_LOCATION_INFO, "object");
            var optionHtml='';
            if(!!currentLocation){
                var location = currentLocation.location;
                optionHtml = '<option value="' + currentLocation.adcode + '" data-city="' + currentLocation.district + '" data-lng="' + location.lng + '" data-lat="' + location.lat + '" selected>' + currentLocation.name + '</option>';
                setArport_uuid(currentLocation.district);
            }else{
                optionHtml='<option value="" selected>请选择地点</option>';
            }

             $qidian.html(optionHtml);
            $zhongdian.data('click', false);
            setSelectOptions($zhongdian,2);
        }
    }

    // 设置input为hide的值
    function setInputValue(from, to, curLocation) {
        $('#containerBox').addClass('hide');
        $('#searchBox').removeClass('hide');
        $('#searchBox').load('/wechat/view/' + from);
        $('#curLocation').val(curLocation);
        $('#inputId').val(to);
    }

    // 取消输入或者返回时，重新设置select数据
    function cancelInputOrBack() {
        $('.amap-sug-result').remove();
        var $tabSelect = $('.airport-selected');
        var type = $tabSelect.data('type');
        setName("", "");
        setSelectDataByType(type);
        letShow();
        $('.kucity').hide();
    }

    // 打开历史记录列表
    function appendHistory() {
        var divHtml = "<div class='amap-sug-result'>";
        var searchHistory = getLocalStorage('searchHistory', 'object');
        if (searchHistory) {
            var len = searchHistory.length;
            if (len > 0) {
                for (var i = 0; i < len; i++) {
                    var item = searchHistory[i];
                    var dataStr = JSON.stringify(item);
                    divHtml += "<div data-item='" + dataStr + "' class='auto-item history-item'" +
                        " id='amap-sug" + i + "'>" + item.name + "<span class='auto-item-span'>" +
                        item.district + item.address + "</span></div>";
                }
                divHtml += "</div>";
                $('#historyBox').removeClass('hide').html(divHtml);
            } else {
                $('#historyBox').addClass('hide').html('');
            }
        }
    }

    // 下拉点击事件
    function comboBoxClick($this, type) {
        var flag = $this.data('click');
        if (flag) {
            $this.html("").prop("disabled", "disabled");
            // 禁止弹出软键盘
            document.activeElement.blur();
            if (type === "start") {
                setInputValue('start', 'qidian_input', START_LOCATION_INFO);
            } else if (type === "end") {
                setInputValue('end', 'zhongdian_input', END_LOCATION_INFO);
            }
        } else {
            $this.prop("disabled", false);
        }
    }

    /*
     * 禁止城市容器滚动
     * */
    $(".citybox").on('touchmove', function (e) {
        e.preventDefault();
    });

});