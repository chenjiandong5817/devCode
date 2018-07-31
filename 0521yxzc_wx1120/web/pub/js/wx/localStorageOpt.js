/*
* @Author: cdroid
* @Date:   2018-05-21 11:21:03
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-06-01 09:37:52
*/
// 先存入本地
function setLocalStorage(key, value) {
	if (window.localStorage) {
		var storage = window.localStorage;
		if (typeof(value) != "string")
			storage.setItem(key, JSON.stringify(value));
		else
			storage.setItem(key, value);
	} else {
		alert('该应用不支持本地存储！');
	}
}

// 获取本地缓存数据
function getLocalStorage(key, type) {
	if (window.localStorage) {
		var storage = window.localStorage;
		var value;
		if (type == "object")
			value = JSON.parse(storage.getItem(key));
		else
			value = storage.getItem(key);
		return value;
	} else {
		alert('该应用不支持本地存储！');
	}
}

// 移除本地缓存
function delLocalStorage(key) {
	if (window.localStorage) {
		var storage = window.localStorage;
		storage.removeItem(key);
	} else {
		alert('该应用不支持本地存储！');
	}
}

// 8位随机抽奖码
function randomString(len) {　　
	len = len || 8;　　
	var $chars = '123456789';　　
	var maxPos = $chars.length;　　
	var code = '';　　
	for (i = 0; i < len; i++) {　　　　
		code += $chars.charAt(Math.floor(Math.random() * maxPos));　　
	}　　
	return code;
}

function getSourceUrl() {
	var nowUrl = '';
	if (document.referrer.length > 0) {
		nowUrl = document.referrer;
	}
	try {
		if (nowUrl.length == 0 && opener.location.href.length > 0) {
			nowUrl = opener.location.href;
		}
	} catch (e) {

	}
	return nowUrl;
}