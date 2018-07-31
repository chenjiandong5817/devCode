function resolveImgsFixed($img) {
    var src = $img.attr("src");
    // 图片容器的宽高,css样式中需要设置好容器的宽高
    var $con = $img.parent(),
        widthStr = $con.css('width'),
        heightStr = $con.css('height'),
        cwidth = +widthStr.substring(0, widthStr.indexOf('px')),
        cheight = +heightStr.substring(0, heightStr.indexOf('px'));
    // 图片宽高
    var imgProps = getImgOriginProps(src),
        oriWidth = imgProps.width,
        oriHeight = imgProps.height;
    // 新的宽高
    var newHeight = 0, newWidth = 0;
    if (oriHeight >= oriWidth) { // 图片竖向 按照高度百分比优先，宽度等比缩放
        newHeight = cheight - 10; // 设置图片高度为容器高度，10为修正值
        newWidth = oriWidth * newHeight / oriHeight; // 修正后的新宽度值
        //$img.css({
        //    'width': newWidth + 'px',
        //    'height': newHeight + 'px'
        //})
        $img.css({
            'width': 50 + 'px',
            'height': 50 + 'px'
        })
    } else {
        $img.css({
            'width':  '50px',
            'height':  '50px'
        })
    } // 图片横向 按照宽度百分比优先，高度等比缩放,这里可以用css样式实现，故无需js处理
}

// 获取图片原始宽高
function getImgOriginProps(src) {
    var image = new Image();
    image.src = src;
    return {
        height: image.height,
        width: image.width
    }
}