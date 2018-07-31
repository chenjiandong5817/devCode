<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <title>计价规则</title>
    <link rel="stylesheet" href="/pub/wechat/css/reset.css" type="text/css" />
</head>
<body>
<div class="price-list">
    <p class="title">即时用车计价规则</p>
    <table>
        <tr><th>车型</th><th>时尚型</th><th>经济七座</th><th>豪华商务</th></tr>
        <tr><td class="name">起步价（元）</td><td>${data[12].startFee}</td><td>${data[13].startFee}</td><td>${data[14].startFee}</td></tr>
        <tr><td class="name">起步包含里程（公里）</td><td>${data[12].startTrip}</td><td>${data[13].startTrip}</td><td>${data[14].startTrip}</td></tr>
        <tr><td class="name">起步包含时长（分钟）</td><td>${data[12].startDuration}</td><td>${data[13].startDuration}</td><td>${data[14].startDuration}</td></tr>
        <tr><td class="name">超出里程（元/公里</td><td>${data[12].beyondTripFee}</td><td>${data[13].beyondTripFee}</td><td>${data[14].beyondTripFee}</td></tr>
        <tr><td class="name">超出时长（元/分钟）</td><td>${data[12].beyondTimeFee}</td><td>${data[13].beyondTimeFee}</td><td>${data[14].beyondTimeFee}</td></tr>
        <tr><td class="name">免费等待时长（分钟）</td><td>${data[12].freeWaitTime}</td><td>${data[13].freeWaitTime}</td><td>${data[14].freeWaitTime}</td></tr>
        <tr><td class="name">超时等待费（元/分钟）</td><td>${data[12].beyondWaitTime}</td><td>${data[13].beyondWaitTime}</td><td>${data[14].beyondWaitTime}</td></tr>
        <tr><td class="name">回空补贴级别（公里/级）</td><td>${data[12].haulBackTrip}</td><td>${data[13].haulBackTrip}</td><td>${data[14].haulBackTrip}</td></tr>
        <tr><td class="name">回空补贴差价（元/级）</td><td>${data[12].haulBackTripFee}</td><td>${data[13].haulBackTripFee}</td><td>${data[14].haulBackTripFee}</td></tr>
        <tr><td class="name">夜间补贴时段</td><td>${data[12].nightTimeStr}</td><td>${data[13].nightTimeStr}</td><td>${data[14].nightTimeStr}</td></tr>
        <tr><td class="name">夜间补贴（元/公里）</td><td>${data[12].nightTripFee}</td><td>${data[13].nightTripFee}</td><td>${data[14].nightTripFee}</td></tr>
    </table>
    <p class="title">预约用车计价规则</p>
    <table>
        <tr><th>车型</th><th>时尚型</th><th>经济七座</th></tr>
        <tr><td class="name">起步价（元）</td><td>${data[0].startFee}</td><td>${data[1].startFee}</td></tr>
        <tr><td class="name">起步包含里程（公里）</td><td>${data[0].startTrip}</td><td>${data[1].startTrip}</td></tr>
        <tr><td class="name">起步包含时长（分钟）</td><td>${data[0].startDuration}</td><td>${data[1].startDuration}</td></tr>
        <tr><td class="name">超出里程（元/公里）</td><td>${data[0].beyondTripFee}</td><td>${data[1].beyondTripFee}</td></tr>
        <tr><td class="name">超出时长（元/分钟）</td><td>${data[0].beyondTimeFee}</td><td>${data[1].beyondTimeFee}</td></tr>
        <tr><td class="name">免费等待时长（分钟）</td><td>${data[0].freeWaitTime}</td><td>${data[1].freeWaitTime}</td></tr>
        <tr><td class="name">超时等待费（元/分钟）</td><td>${data[0].beyondWaitTime}</td><td>${data[1].beyondWaitTime}</td></tr>
        <tr><td class="name">回空补贴级别（公里/级）</td><td>${data[0].haulBackTrip}</td><td>${data[1].haulBackTrip}</td></tr>
        <tr><td class="name">回空补贴差价（元/级）</td><td>${data[0].haulBackTripFee}</td><td>${data[1].haulBackTripFee}</td></tr>
        <tr><td class="name">夜间补贴时段</td><td>${data[0].nightTimeStr}</td><td>${data[1].nightTimeStr}</td></tr>
        <tr><td class="name">夜间补贴（元/公里）</td><td>${data[0].nightTripFee}</td><td>${data[1].nightTripFee}</td></tr>
    </table>
    <p class="title">接机计价规则</p>
    <table>
        <tr><th>接机</th><th>时尚型</th><th>经济七座</th></tr>
        <tr><td>基础价</td><td>${data[3].startFee}</td><td>${data[4].startFee}</td></tr>
        <tr><td>公里数（含）</td><td>${data[3].startTrip}</td><td>${data[4].startTrip}</td></tr>
        <tr><td>基础时长（含）</td><td>${data[3].startDuration}</td><td>${data[4].startDuration}</td></tr>
        <tr><td>超里程费</td><td>${data[3].beyondTripFee}</td><td>${data[4].beyondTripFee}</td></tr>
        <tr><td>超时长费</td><td>${data[3].beyondTimeFee}</td><td>${data[4].beyondTimeFee}</td></tr>
        <tr><td>免费等待时长</td><td>${data[3].freeWaitTime}</td><td>${data[4].freeWaitTime}</td></tr>
        <tr><td>超时等候附加费	</td><td>${data[3].beyondWaitTime}</td><td>${data[4].beyondWaitTime}</td></tr>
        <tr><td>回空里程	</td><td>${data[3].haulBackTrip}</td><td>${data[4].haulBackTrip}</td></tr>
        <tr><td>超回空里程附加费</td><td>${data[3].haulBackTripFee}</td><td>${data[4].haulBackTripFee}</td></tr>
        <tr><td>夜间附加费</td><td>${data[3].nightTripFee}</td><td>${data[4].nightTripFee}</td></tr>
        <tr><td>夜间服务时段</td><td>${data[3].nightTimeStr}</td><td>${data[4].nightTimeStr}</td></tr>
    </table>
    <p class="title">送机计价规则</p>
    <table>
        <tr><th>送机</th><th>时尚型</th><th>经济七座</th></tr>
        <tr><td>基础价</td><td>${data[6].startFee}</td><td>${data[7].startFee}</td></tr>
        <tr><td>公里数（含）</td><td>${data[6].startTrip}</td><td>${data[7].startTrip}</td></tr>
        <tr><td>基础时长（含）</td><td>${data[6].startDuration}</td><td>${data[7].startDuration}</td></tr>
        <tr><td>超里程费</td><td>${data[6].beyondTripFee}</td><td>${data[7].beyondTripFee}</td></tr>
        <tr><td>超时长费</td><td>${data[6].beyondTimeFee}</td><td>${data[7].beyondTimeFee}</td></tr>
        <tr><td>免费等待时长</td><td>${data[6].freeWaitTime}</td><td>${data[7].freeWaitTime}</td></tr>
        <tr><td>超时等候附加费	</td><td>${data[6].beyondWaitTime}</td><td>${data[7].beyondWaitTime}</td></tr>
        <tr><td>回空里程	</td><td>${data[6].haulBackTrip}</td><td>${data[7].haulBackTrip}</td></tr>
        <tr><td>超回空里程附加费</td><td>${data[6].haulBackTripFee}</td><td>${data[7].haulBackTripFee}</td></tr>
        <tr><td>夜间附加费</td><td>${data[6].nightTripFee}</td><td>${data[7].nightTripFee}</td></tr>
        <tr><td>夜间服务时段</td><td>${data[6].nightTimeStr}</td><td>${data[7].nightTimeStr}</td></tr>
    </table>
    <p class="title">半日租计价规则</p>
    <table>
        <tr><th>半日</th><th>时尚型</th><th>经济七座</th></tr>
        <tr><td>基础价</td><td>${data[15].startFee}</td><td>${data[16].startFee}</td></tr>
        <tr><td>公里数（含）</td><td>${data[15].startTrip}</td><td>${data[16].startTrip}</td></tr>
        <tr><td>基础时长（含）</td><td>${data[15].startDuration}</td><td>${data[16].startDuration}</td></tr>
        <tr><td>超里程费</td><td>${data[15].beyondTripFee}</td><td>${data[16].beyondTripFee}</td></tr>
        <tr><td>超时长费</td><td>${data[15].beyondTimeFee}</td><td>${data[16].beyondTimeFee}</td></tr>
        <tr><td>免费等待时长</td><td>${data[15].freeWaitTime}</td><td>${data[16].freeWaitTime}</td></tr>
        <tr><td>超时等候附加费	</td><td>${data[15].beyondWaitTime}</td><td>${data[16].beyondWaitTime}</td></tr>
        <tr><td>回空里程	</td><td>${data[15].haulBackTrip}</td><td>${data[16].haulBackTrip}</td></tr>
        <tr><td>超回空里程附加费</td><td>${data[15].haulBackTripFee}</td><td>${data[16].haulBackTripFee}</td></tr>
        <tr><td>夜间附加费</td><td>${data[15].nightTripFee}</td><td>${data[16].nightTripFee}</td></tr>
        <tr><td>夜间服务时段</td><td>${data[15].nightTimeStr}</td><td>${data[16].nightTimeStr}</td></tr>
    </table>

    <p class="title">日租计价规则</p>
    <table>
        <tr><th>日租</th><th>时尚型</th><th>经济七座</th></tr>
        <tr><td>基础价</td><td>${data[18].startFee}</td><td>${data[19].startFee}</td></tr>
        <tr><td>公里数（含）</td><td>${data[18].startTrip}</td><td>${data[19].startTrip}</td></tr>
        <tr><td>基础时长（含）</td><td>${data[18].startDuration}</td><td>${data[19].startDuration}</td></tr>
        <tr><td>超里程费</td><td>${data[18].beyondTripFee}</td><td>${data[19].beyondTripFee}</td></tr>
        <tr><td>超时长费</td><td>${data[18].beyondTimeFee}</td><td>${data[19].beyondTimeFee}</td></tr>
        <tr><td>免费等待时长</td><td>${data[18].freeWaitTime}</td><td>${data[19].freeWaitTime}</td></tr>
        <tr><td>超时等候附加费	</td><td>${data[18].beyondWaitTime}</td><td>${data[19].beyondWaitTime}</td></tr>
        <tr><td>回空里程	</td><td>${data[18].haulBackTrip}</td><td>${data[19].haulBackTrip}</td></tr>
        <tr><td>超回空里程附加费</td><td>${data[18].haulBackTripFee}</td><td>${data[19].haulBackTripFee}</td></tr>
        <tr><td>夜间附加费</td><td>${data[18].nightTripFee}</td><td>${data[19].nightTripFee}</td></tr>
        <tr><td>夜间服务时段</td><td>${data[18].nightTimeStr}</td><td>${data[19].nightTimeStr}</td></tr>
    </table>
    <p class="tips">免费等待时长的计算：若司机在预约时间前到达起点，从预约时间开始计算；若司机在预约时间之后到达起点，从司机到达时间开始计算。</p>

</div>

<script type="text/javascript">

</script>
</body>
</html>
