<!-- 接机视图 -->
<template>
    <div class="content">
        <div class="taxi-content bg">
            <timepick class="border-bottom-1px"></timepick>
            <div class="taxi-location border-bottom-1px">
                <location-com type='from'></location-com>
                <location-com type='to'></location-com>
            </div>
            <!-- <flight></flight> -->
            <div v-if="show">
            <div class="flex-content border-top-1px">
                <changePassenger class="flex-item gap" type='from'></changePassenger>
                <changeCarType class="flex-item gap"></changeCarType>
            </div>
            <div class="flex-content border-top-1px">
                <changePayType class="flex-item"></changePayType>
            </div>
            <div class="flex-content border-top-1px">
                <estimate class="flex-item"></estimate>
            </div>
          </div>
        </div>
            <cube-button type="submit" :disabled="isAllow" @click="sendOrder">呼叫专车</cube-button>
    </div>
</template>
<script>
import { mapState } from 'vuex'
const locationCom = () => import('@/components/common/locationCom.vue')
const timepick = () => import('@/components/common/timePick.vue')
/* const flight = () => import('@/components/flight/flight.vue') */
const changePassenger = () => import('@/components/common/changePassenger.vue')
const changeCarType = () => import('@/components/common/changeCarType.vue')
const changePayType = () => import('@/components/common/changePayType.vue')
const estimate = () => import('@/components/common/estimate.vue')
export default{
  name: 'pickup',
  computed: mapState({
    show (state) {
      if (state.location.locationFrom && state.location.locationTo) {
        this.$store.dispatch('post_disAndDura').then((data) => {
          if (!data.success) {
            this.$createDialog({
              type: 'alert',
              title: '提示',
              content: data.msg
            }).show()
          }
        })
      }
      return state.location.locationFrom && state.location.locationTo
    },
    isAllow (state) {
      return !(state.location.locationFrom && state.location.locationTo)
    },
    LocationData (state) {
      return state.staticData.loca_data
    }
  }),
  methods: {
    sendOrder () {
      this.$store.dispatch('orderRequest').then((data) => {
        if (data.errCode === 2004) {
          this.$createDialog({
            type: 'confirm',
            title: '账号被封',
            content: '您的账号已被封，原因是：' + data.msg + '。如有疑问请联系客服。',
            confirmBtn: {
              text: '我知道了',
              active: true,
              disabled: false,
              href: 'javascript:;'
            },
            cancelBtn: {
              text: '联系客服',
              active: false,
              disabled: false,
              href: 'tel:059296363'
            },
            onConfirm: () => {},
            onCancel: () => {}
          }).show()
          return
        }
        if (data.noSubmit || !data.success) {
          this.$createDialog({
            type: 'alert',
            title: '提示',
            content: data.msg
          }).show()
        }
      })
    }
  },
  components: {
    locationCom,
    timepick,
    /* flight, */
    changePassenger,
    changeCarType,
    changePayType,
    estimate
  }
}
</script>
<style lang='less'>
@import "../../assets/css/less/variable.less";
.taxi-content {
  display: block;
  margin-bottom: 10px;
  color: #999;
  &.bg {
    background-color: #fff;
    .boxShadow()
  }
  &:after {
    .clear()
  }
}

</style>