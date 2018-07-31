<!-- 车型选择视图 -->
<template>
    <transition enter-active-class="animated bounceIn" leave-active-class="animated slideOutDown">
        <div class="wrap">
            <!-- 车型选择 -->
            <div class="carTypePick">
              <!-- 提示语与操作栏 -->
              <div class="picker-choose">
                <span class="cancel" @click="goback()">取消</span>
                <span class="confirm" @click="confirm()">确定</span>
                <h1 class="title">请选择车型</h1>
              </div>
              <ul class="pick-list">
                  <li class="item" v-for="(item,index) in carTypeList" @click="picksomeOne(item)" v-if="item.status>0">
                    <label>
                      <!-- 车的形状 -->
                      <input type="radio" name="carType" v-model="pickItem.value" :value="item.value">
                      <div class="item-content">
                      <div :class="item.class"></div>
                      <!-- 车的名字 -->
                      <p class="mar-t10">{{item.name}}</p>                          
                      </div>
                    </label>
                  </li>
              </ul>
            </div>
            <!-- 遮罩层 -->
            <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
                <com-mask :show="true" :event="default_event"></com-mask>
            </transition>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const comMask = () => import('@/components/Mask.vue')
export default{
  name: 'carType',
  data () {
    return {
      carTypeList: this.$store.state.carTypeList,
      pickItem: this.$store.state.carType
    }
  },
  components: {
    comMask
  },
  computed: {
    ...mapState([
      'VIPnum'
    ])
  },
  methods: {
    confirm () {
      if (this.pickItem.modelNum !== 2 && this.VIPnum > 4) {
        this.$store.commit('SET_TOPSERVICE', {name: 'num', val: 4})
      }
      this.$store.commit('SET_CARTYPE', this.pickItem)
      // this.$router.go(-1)
      window.history.go(-1)
    },
    picksomeOne (val) {
      this.pickItem = val
    },
    goback () {
      // this.$router.go(-1)
      window.history.go(-1)
    },
    default_event () {
      return false
    }
  }
}
</script>
<style lang='less' scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  z-index: 9;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  animation-duration: .3s;
}

.carTypePick {
  z-index: 99;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 210px;
  text-align: center;
  font-size: 12px;
  color: #6b7886;
  background: #ffffff;
  .picker-choose {
    position: relative;
    height: 50px;
    font-size: 16px;
    color: #6b7886;
    border-bottom: 0.5px solid #f3f3f3;
    h1{
      line-height: 50px;
    }
    .cancel{
      position: absolute;
      top: 3px;
      left: 0;
      padding: 15px;
      font-size: 14px;
      color: #999;
    }
    .confirm{
      position: absolute;
      top: 3px;
      right: 0;
      padding: 16px;
      font-size: 14px;
      color: #00b5e6;
    }
  }
  .pick-list {
    display: flex;
    padding: 50px 15px 20px 15px;
    li.item {
      flex-grow: 1;
      text-align: center;
      label{
        display: block;
        width: 100%;
        height: 100%;
      }
      input[type="radio"]{
        display:none;
      }
      input[type="radio"]:checked + div.item-content{
        color: #00b5e6;
        border-bottom: 1.5px solid #00b5e6;
        .type1{
          background: url('../../assets/images/car-type-1-active.png') no-repeat center;
          background-size: 100% 100%;
        }
        .type2{
          background: url('../../assets/images/car-type-2-active.png') no-repeat center;
          background-size: 100% 100%;
        }
        .type3{
          background: url('../../assets/images/car-type-3-active.png') no-repeat center;
          background-size: 100% 100%;
        }
      }
      .item-content{
        color: #b1b5ba;
        font-size: 12px;
        line-height: 2;
        border-bottom: 1.5px solid #dcdfe3;
        .type1{
          margin: 0 auto;
          width: 90px;
          height: 30px;
          background: url('../../assets/images/car-type-1.png') no-repeat center;
          background-size: 90% 90%;
        }
        .type2{
          margin: 0 auto;
          width: 90px;
          height: 30px;
          background: url('../../assets/images/car-type-2.png') no-repeat center;
          background-size: 90% 90%;
        }
        .type3{
          margin: 0 auto;
          width: 90px;
          height: 30px;
          background: url('../../assets/images/car-type-3.png') no-repeat center;
          background-size: 90% 90%;
        }
      }
    }
  }
}


.personPay {
  display: block;
  margin: 0 auto;
  width: 55px;
  height: 55px;
  background: url('../../assets/images/person-pay.png') no-repeat center;
  background-size: 100% 100%;
}

.enterprisePay {
  display: block;
  margin: 0 auto;
  width: 55px;
  height: 55px;
  background: url('../../assets/images/enterprise-pay.png') no-repeat center;
  background-size: 100% 100%;
}

</style>
