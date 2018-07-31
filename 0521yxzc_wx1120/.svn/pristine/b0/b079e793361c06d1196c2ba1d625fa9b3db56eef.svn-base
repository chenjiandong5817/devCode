<%--
  Created by IntelliJ IDEA.
  User: SummerSoft
  Date: 2016/4/7
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Java API for WebSocket (JSR-356)</title>
</head>
<body>
<script type="text/javascript" src="jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="sockjs-0.3.min.js"></script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://192.168.1.70:8081/socket/connectWebSocketServer?websocket_token=456j733kffg544gc99b6hf7f7bf42fgh");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://192.168.1.70:8081/socket/connectWebSocketServer?websocket_token=456j733kffg544gc99b6hf7f7bf42fgh");
    }
    else {
        websocket = new SockJS("http://192.168.1.70:8081/socket/sockjs/connectWebSocketServer?websocket_token=456j733kffg544gc99b6hf7f7bf42fgh");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
//        alert(openEvt.Data);
    }

    function onMessage(evt) {
        alert(evt.data);
    }
    function onError() {}
    function onClose() {}

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var msg = document.getElementById("inputMsg").value;
            websocket.send(msg);//调用后台handleTextMessage方法
            alert("发送成功!");
        } else {
            alert("连接失败!");
        }
    }
</script>
请输入：<textarea rows="5" cols="10" id="inputMsg" name="inputMsg"></textarea>
<button onclick="doSend();">发送</button>
</body>
</html>
