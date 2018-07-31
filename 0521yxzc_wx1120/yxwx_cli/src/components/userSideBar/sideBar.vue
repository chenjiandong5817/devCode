<template>
    <div>
        <!-- 估计是动画效果 -->
        <transition enter-active-class="animated slideInLeft" leave-active-class="animated slideOutLeft">
            <div class="sidebar-container" v-if="showUserBar">
                <div class="user">
                  <!-- 当图片不存在时，则不使用img -->
                    <i class="userPhoto"><img :src="headImgUrl" v-if="headImgUrl"></i>
                    <p>{{userMsg.realName?userMsg.realName:''}}</p>
                    <!-- 数据处理貌似管道 -->
                    <span>{{userMsg.mobile?userMsg.mobile:'' | encrypt}}</span>
                </div>
                <menu-list></menu-list>
            </div>
        </transition>
        <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
            <com-mask :show="showUserBar" :event="closeUserBar"></com-mask>
        </transition>
    </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import api from '@/store/index/api'
const menuList = () => import('@/components/userSideBar/menuList.vue')
const comMask = () => import('@/components/Mask.vue')
export default {
  filters: {
    // 貌似管道记着
    encrypt (val) {
      return val ? (val.slice(0, 3) + '****' + val.slice(7, 11)) : ''
    }
  },
  data () {
    return {
      headImgUrl: api.getLocalStorage('headImgUrl') ? api.getLocalStorage('headImgUrl') : ''
    }
  },
  computed: {
    ...mapState([
      'userMsg',
      'showUserBar'
    ])
  },
  methods: {
    ...mapActions({
      'closeUserBar': 'switch_UserBar'
    }),
    default_event () {
      return false
    }
  },
  components: {
    menuList,
    comMask
  }
}
</script>
<style lang="less">
.sidebar-container {
  position: fixed;
  z-index: 99;
  left: 0;
  top: 0;
  bottom: 0;
  width: 60%;
  padding: 0 15px;
  height: 100%;
  box-shadow: 4px 0 18px 6px rgba(0, 0, 0, .12);
  background-color: #fff;
  animation-duration: .3s;
  .user {
    position: relative;
    color: #666;
    text-align: center;
    padding: 30px 0;
    color: #4f5a67;
    font-size: 16px;
    line-height: 1.5;
    text-align: center;
    border-bottom: .5px solid #e4e7ea;
    .userPhoto {
      margin: 0 auto;
      display: block;
      width: 65px;
      height: 65px;
      margin-bottom: 15px;
      background: url("../../assets/images/userPhoto.png") no-repeat center;
      background-size: 40% 40%;
      background-color: #e4e7ea;
      border-radius: 50%;
      img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
    span{
      font-size: 12px;
      color: #9ea7b1
    }
  }
}

</style>
