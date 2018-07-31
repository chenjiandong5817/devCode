<%@ page language="java" pageEncoding="utf-8" %>
<div class="searchBox row">
    <div class="col-xs-5 showCityBox">
        <img class="qidian" src="/pub/images/wx/zhongdian.png">
        <span id="city_info"></span>
        <img class="down" src="/pub/images/wx/down.png">
    </div>
    <div class="col-xs-5 inputCityBox hide">
        <input type="text" class="search-city" placeholder="城市中文或拼音">
    </div>
    <div class="col-xs-7 inputAddressBox">
        <img id="input_delete" class="hide" src="/pub/images/wx/input_delete.png" alt="">
        <input class="search-input" type="text" id="tipinput" placeholder="你要去哪儿">
        <span id="cancelBtn">取消</span>
    </div>
</div>
<div id="container"></div>
<div class="searchResultList hide"></div>
<div class="no-result text-center hide">
    <img src="/pub/images/wx/no.png">
    <p>暂无搜索结果，换个词试试吧</p>
</div>
<script type="text/javascript">

    (function ($) {
        var height = $(window).height();
        $('.kucity_body').css("height", height - 50)

        var city = getLocalStorage('city', 'string');
        document.getElementById('city_info').innerHTML = '' + city;

        // 触发城市搜索，显示输入框
        $(".showCityBox").on('touchend', function (event) {
            // 初始化城市搜索
            var $kucity = $('.search-city').kuCity();
            var $this = $(this);
            $this.addClass('hide').next().removeClass('hide');
            $('#historyBox').addClass('hide');
            $('.searchResultList').addClass('hide');
            $('.search-city').val('');
            $kucity.init();
            var hotCity = $kucity.hotCity['hot'];
            var activeContainer = $kucity.container.find('.active dd');
            activeContainer.html("");
            var str = '<p class="kucity-current">当前城市：' + getLocalStorage('city', 'string') + '</p>';
            for (var j = 0, jLen = hotCity.length; j < jLen; j++) {
                str += '<span>' + hotCity[j] + '</span>'
            }
            activeContainer.html(str);

            $kucity.container.find('.kucity_nav').show();
            $kucity.container.find('.result').show();
            $kucity.container.find('.kucity_item').show();

            $kucity.container.show().offset({
                'top': 50,
                'left': 0
            });
        });

        // 触发具体地点输入input
        $(".inputAddressBox").on('touchend', function (event) {

            $('.inputCityBox').addClass('hide');
            $('.showCityBox').removeClass('hide');
            $('#historyBox').removeClass('hide');
        });

        $('#input_delete').on('touchend', function (event) {

            $('#tipinput').val('');
        });

    })(jQuery);
</script>