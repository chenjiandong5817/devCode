<%@ page language="java" pageEncoding="utf-8" %> <!DOCTYPE html><html lang=zh-CN><head><meta charset=utf-8><meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1"><meta name=description content=元翔专车><meta name=keywords content=元翔专车><meta name=author content="tuisemo, tuisemo@sina.cn"><meta name=robots content=index,follow><meta name=viewport content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"><meta http-equiv=X-UA-Compatible content="IE=edge"><meta http-equiv=Pragma content=no-cache><meta http-equiv=Cache-Control content=no-cache><meta http-equiv=Expires content=0><title>个人中心</title><script>function setLocalStorage(key, value) {
    if (window.localStorage) {
      var storage = window.localStorage
      if (typeof(value) !== 'string') { storage.setItem(key, JSON.stringify(value)) } else { storage.setItem(key, value) }
    } else {
      console.log('该应用不支持本地存储！')
    }
  };
    // setLocalStorage('openId', "oQ5XFjl8zRm9IGzXR8igZG6dK_2c");
    // setLocalStorage('areaUuid', "f807671564b0409aa647b7b80af555b6");
    // setLocalStorage('nickName', "陈建东");
    // setLocalStorage('wxUserSex', "1");
    // setLocalStorage('headImgUrl', "http://thirdwx.qlogo.cn/mmopen/vi_32/l0umLia61h9J66UgGYrAvm4fBY4MTjrEfH2iaic9Nw1xzlnFCZUYic674nZv2xsAS0SbkoazJZxd5vHMss71TTr8Ww/132");
    setLocalStorage('openId', "${snsUserInfo.openId}");
    setLocalStorage('areaUuid', "${areaUuid}");
    setLocalStorage('nickName', "${snsUserInfo.nickname}");
    setLocalStorage('wxUserSex', "${snsUserInfo.sex}");
    setLocalStorage('headImgUrl', "${snsUserInfo.headImgUrl}");</script><link href=/static/css/vendor.910142a5185db9a73a5721411c9b37aa.css rel=stylesheet><link href=/static/css/userCenter.c4ce2fc79d2e8540149d98ac8a4c2b9a.css rel=stylesheet></head><body><div id=app></div><script src=/static/js/vendor.dll.js></script><script type=text/javascript src=/static/js/manifest.52f5391a30529144571f.js></script><script type=text/javascript src=/static/js/vendor.6afcdc2a922fc8dc89d3.js></script><script type=text/javascript src=/static/js/userCenter.0b05f2380c5d0adc3561.js></script></body></html>