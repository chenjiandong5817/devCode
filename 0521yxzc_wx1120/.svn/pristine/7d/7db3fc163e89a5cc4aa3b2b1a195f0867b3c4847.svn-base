// 全局map
var map, geolocation;
//加载地图，调用浏览器定位服务
map = new AMap.Map('container', {
    resizeEnable: true
});
//初始化默认厦门市
if(!getLocalStorage('city')){
    setLocalStorage('city',"厦门市");
}
geolocation();

function geolocation() {
    map.plugin('AMap.Geolocation', function () {

        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true, //是否使用高精度定位，默认:true
            timeout: 10000, //超过10秒后停止定位，默认：无穷大
            // buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true, //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
            buttonPosition: 'RB'
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete); //返回定位信息
        AMap.event.addListener(geolocation, 'error', onError); //返回定位出错信息
    });
}
//解析定位结果
function onComplete(data) {
    var province = data.addressComponent.province; // 省
    var city = data.addressComponent.city; // 市
    var district = data.addressComponent.district; // 区
    var township = data.addressComponent.township; // 街道
    var name = data.formattedAddress//data.addressComponent.township + data.addressComponent.street + data.addressComponent.streetNumber,
    city = data.addressComponent.city
    name = name.replace(province, '').replace(city, '').replace(district, '');
    if (name.length > 9) {
        name = name.replace(township, '');
    }
    // var option = "<option value='' selected>"+name+"</option>";
    // $('#qidian_input').html(option);
    setLocalStorage('city', city == null ? "厦门市" : city);
    var startLocation = null;
    if (data != undefined && data.position != undefined) {// 用户通过定位已经获取了位置信息
        startLocation = {
            adcode: data.addressComponent.adcode,
            address: data.addressComponent.street + data.addressComponent.streetNumber,
            district: data.addressComponent.province + data.addressComponent.city + data.addressComponent.district,
            location: data.position,
            name: name,
            typecode: data.addressComponent.adcode
        }
    }
    setLocalStorage(START_LOCATION_INFO, startLocation);
}

//解析定位错误信息
function onError(data) {
    // document.getElementById('tip').innerHTML = '定位失败';
    console.log("定位失败");
    setLocalStorage('city',"厦门市");
}