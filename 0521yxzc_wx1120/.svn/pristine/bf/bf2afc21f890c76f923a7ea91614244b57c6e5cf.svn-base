function autoComplete(keyword) {
    var city = $('#city_info').text();
    //输入提示
    var autoOptions = {
        city: city,
        citylimit: true
    };

    var auto = new AMap.Autocomplete(autoOptions);

    auto.search(city + keyword, function (status, result) {
        //TODO:开发者使用result自己进行下拉列表的显示与交互功能
        var divHtml = "<div class='amap-sug-result'>";
        if (status != "no_data") {
            var len = result.tips.length > 5 ? 5 : result.tips.length;
            for (var i = 0; i < len; i++) {
                var item = result.tips[i];
                var dataStr = JSON.stringify((item));
                divHtml += "<div onclick='select(this);' data-item='" + dataStr + "' class='auto-item search-item'" +
                    " id='amap-sug" + i + "'>" + item.name + "<span class='auto-item-span'>" +
                    item.district + item.address + "</span></div>";
            }
            divHtml += "</div>";
            $('.searchResultList').removeClass('hide').html(divHtml);
            $('.no-result').addClass('hide');
        } else {
            $('.searchResultList').addClass('hide').html("");
            $('.no-result').removeClass('hide');
        }
    });
    //构造地点查询类
    //AMap.event.addListener(auto, "select", select); //注册监听，当选中某条记录时会触发
}
function select(obj) {
    var poi = $(obj).data('item');
    validateHasLocationInfo(poi);
}

function validateHasLocationInfo(poi) {
    var curLocation = poi.location;
    if (curLocation === "" || curLocation == undefined) {
        layer.msg("请输入具体位置！");
    } else {
        search(poi);
        setName(poi, curLocation);
        callFunctionEstimatedPrice();
        letShow();
    }
}

function search(poi) {
    var curLocation = $('#curLocation').val();
    if (poi.location != undefined) {
        if (curLocation === START_LOCATION_INFO) {
            setLocalStorage(curLocation, poi);
        } else if (curLocation === END_LOCATION_INFO) {
            setLocalStorage(curLocation, poi);
        }
        // 加入搜索历史记录
        if (!searchHistory) {
            searchHistory = [];
        }
        var length = searchHistory.length;
        if (length > 9) {
            // 从数组尾部删除
            searchHistory.pop();
            // 从头部添加
            searchHistory.unshift(poi);
        } else {
            searchHistory.unshift(poi);
        }
        searchHistory = arrayUnique(searchHistory); // 去重
        setLocalStorage('searchHistory', searchHistory);
    }
}

// 给select框设值
function setName(poi, id) {
    $('#historyBox').addClass('hide').html('');

    $('#searchBox').html('');
    setTimeout(function () {
        $('#containerBox').removeClass('hide');
    }, 300);
    var inputId = $('#inputId').val();
    var option='';
    if(typeof poi =='string'){
        option = "<option value='" + 'error' + "' selected>" + poi + "</option>";
    }else{
        var l=poi.location
        option = '<option value="' + poi.adcode + '" data-city="' + poi.district + '" data-lng="' + l.lng + '" data-lat="' + l.lat + '" selected>' + poi.name + '</option>';
    }
    $('#' + inputId).html(option);
}
//  是否显示其他信息
function letShow() {
    var flag = validateNull();
    if (flag) {
        $('#qidian_input').prop('disabled', false);
        $('.takeman').removeClass('hide');
        var type = $('.header-tabs .airport-selected').data('type');
        if (type == "dropOff") { // 送机才会有值机业务
            $('.vip-passageway').removeClass('hide');
        }
        $('.costBox').removeClass('hide');
        $('.pay-types').removeClass('hide');
        $('.btn-call').removeClass('btn-call-disabled').addClass('btn-call-abled');
    } else {
        $('.takeman').addClass('hide');
        $('.vip-passageway').addClass('hide');
        $('.costBox').addClass('hide');
        $('.pay-types').addClass('hide');
        $('.btn-call').addClass('btn-call-disabled').removeClass('btn-call-abled');
    }
}
// 验证输入起始点
function validateNull() {
    var flag = true;
    var $inputs = $('.input'),
        length = $inputs.length;
    for (var i = 0; i < length; i++) {
        var $input = $inputs.eq(i),
            value = $input.val();
        if (value === "" || value === null) {
            flag = false;
            break;
        }
    }
    return flag;
}

