
<%--
  Created by IntelliJ IDEA.
  User: SummerSoft
  Date: 2016/10/18
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>元翔专车</title>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <style type="text/css">
        body {height: 100%; width: 100%; background: url(/pub/images/appH5Page/bg@2x.png) bottom no-repeat #00abf0;background-size: cover;color: #fff; text-align: center;margin: 0;}
        .logo {width: 35%;text-align: center;margin: 0 auto;padding: 36% 0 0px 0;}
        .logo img {width: 100%; display: inline-block;}
        .text{ word-spacing: 5px; margin-bottom: 15%; margin-top: 5px;}
        .down-btn .btn{ display: block;margin: 4% auto; width: 60%;text-align: center; border: 1px solid #fff; color: #fff; line-height: 2.6;border-radius: 4px;text-decoration: none;}
        .down-btn .btn .btn-icon{ display: inline-block; width: 17px;height: 20px; padding-right: 4%; vertical-align: middle; margin-top: -3px;}
        .down-btn .btn .btn-icon img{ width: 100%;height: 100%;display: block; vertical-align: middle;}
        .copyright{ width: 100%; position: fixed; bottom: 2%; margin: 0;font-size: 12px;}
        .copyright span{padding: 0 2px;  }
        .browser-tip{display:none;position: absolute;top:1%;left:1%;z-index: 11;text-align: center}
        .browser-tip img{width: 98%;}
    </style>
</head>
<body>
<div class="browser-tip"><img src="/pub/images/appH5Page/tip.png"/></div>
<div class="logo">
    <img src="/pub/images/appH5Page/logo@2x.png"/>
</div>
<p class="text">空港品质 尊享之旅</p>
<div class="down-btn">
    <a class="btn btn1"><i class="btn-icon"><img src="/pub/images/appH5Page/icon_apple@2x.png"/></i>iPhone版下载</a>
    <a class="btn btn2"><i class="btn-icon"><img src="/pub/images/appH5Page/icon_android@2x.png"/></i>Android版下载</a>
</div>
<p class="copyright"><span>版权所有</span><span>&copy;</span><span>元翔专车</span><span>2016</span></p>
<script src="/pub/enterprise/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('body').css('height', $(window).height());
        var browser={
            versions:function(){
                var u = navigator.userAgent, app = navigator.appVersion;
                return {
                    android: u.indexOf('Android') > -1, //android终端或uc浏览器
                    iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                };
            }(),
            language:(navigator.browserLanguage || navigator.language).toLowerCase()
        }
        if(browser.versions.ios){
            $(".browser-tip img").attr('src','/pub/images/appH5Page/tip.png');
        }else if(browser.versions.android){
            $(".browser-tip img").attr('src','/pub/images/appH5Page/tip2.png')
        }
        //iPhone版下载
        $(".btn1").on('click', function () {
            if (!isWeiXin()) {
                location.href = "https://itunes.apple.com/us/app/yuan-xiang-zhuan-che/id1169555641?mt=8";
            }
            function isWeiXin() {
                var ua = window.navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == 'micromessenger'||ua.match(/WeiBo/i) == "weibo"||ua.match(/QQ/i) == "qq") {
                    $(".browser-tip").show();
                    return true;
                } else {
                    location.href = "https://itunes.apple.com/us/app/yuan-xiang-zhuan-che/id1169555641?mt=8";
                    return false;
                }
            }
        });
        //Android版下载
        $(".btn2").on('click', function () {
            if (!isWeiXin()) {
                location.href = "https://www.yxzc01.com/pub/file/android/ad91c7a3807e4dd291308be329d2bd5x/passenger.apk";
            }
            function isWeiXin() {
                var ua = window.navigator.userAgent.toLowerCase();
                // alert(ua);  ||ua.match(/QQ/i) == "qq"
                if (ua.match(/MicroMessenger/i) == 'micromessenger'||ua.match(/WeiBo/i) == "weibo") {
                    $(".browser-tip").show();
                    return true;
                } else {
                    location.href = "https://www.yxzc01.com/pub/file/android/ad91c7a3807e4dd291308be329d2bd5x/passenger.apk";
                    return false;
                }
            }
        });
        $(".browser-tip").on('click', function () {
            $(".browser-tip").hide();
        })



    })
</script>
</body>
</html>