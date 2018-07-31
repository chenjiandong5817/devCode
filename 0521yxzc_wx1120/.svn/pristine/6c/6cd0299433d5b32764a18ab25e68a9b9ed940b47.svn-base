<!-- 发票视图 -->
<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="aboutUs">
            <div class="top">
                <i class="yxzcLogo"></i>
                <label>元翔专车</label>
                <label class="version">V1.0.0</label>
            </div>
            <ul class="menuList">
                <a href="tel:059296363">
                    <li class="item">
                        服务热线<span>0592-96363转0</span>
                    </li>
                </a>
                <a href="javascript:void(0);">
                    <li class="item">
                        官方网站
                        <span>www.yxzc01.com</span>
                    </li>
                </a>
                <a href="javascript:void(0);">
                    <li class="item">
                        官方微信
                        <span>元翔快线</span>
                    </li>
                </a>
                <a href="/api/base/userAgreement">
                    <li class="item href">
                        服务条款
                    </li>
                </a>
            </ul>
        </div>
    </transition>
</template>
<script>
export default{
}
</script>
<style lang='less'>
.aboutUs {
  position: fixed;
  box-sizing: border-box;
  display: block;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  bottom: 0;
  background: #ffffff;
  animation-duration: .3s;
  .top {
    position: relative;
    box-sizing: border-box;
    padding: 30px 0;
    .yxzcLogo{
      display: block;
      margin:0 auto;
      width: 60px;
      height: 60px;
      background: url('../assets/images/yxzc_logo.png') no-repeat center;
      background-size: 100% 100%; 
      margin-bottom: 15px;
    }
    label {
      display: block;
      width: 100%;
      font-size: 14px;
      color: #333;
      line-height: 1.5;
      text-align: center;
      &.version{
        color: #b9b9b9;
      }
    }
    h1 {
      font-size: 32px;
      text-align: center;
      line-height: 1.5;
      font-weight: bold;
      color: #00b5e6;
      span{
        font-size: 14px;
      }
    }
  }
  .menuList{
    position: relative;
    display: block;
    padding-left: 15px;
    border-top: .5px solid #f3f3f3;
    border-bottom: .5px solid #f3f3f3;
    a {
      display: block;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
      }
    }
    li {
      position: relative;
      display: block;
      height: 50px;
      line-height: 50px;
      font-size: 14px;
      color: #333; 
      &.href:after{
        content: '';
        position: absolute;
        border-right: 1px solid #c5c7ca; 
        border-bottom: 1px solid #c5c7ca;  
        width: 8px;
        height: 8px;  
        transform: rotate(-45deg);  
        top: 50%;
        margin-top: -4px;
        right: 15px;
      }
    }
    .item{
      span{
        padding-left: 20px;
        color: #666;
      }
    }
  }
  .labelMsg{
    display: block;
    margin-top: 30px;
    padding: 0 15px;
    font-size: 12px;
    line-height: 1.6;
    color: #9ea7b1;
  }
}

</style>