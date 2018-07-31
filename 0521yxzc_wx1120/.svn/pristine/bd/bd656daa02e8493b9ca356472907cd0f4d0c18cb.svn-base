<!-- 支付选择组件入口 -->
<template>
  <router-link to="payTypePick" append>
	<div class="payType">
		{{payType.name?payType.name:''}} (余额{{payType.money?payType.money:0}})
	</div>
  </router-link>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'payType',
  data () {
    return {
      payTypeList: this.$store.state.payTypeList
    }
  },
  computed: mapState({
    payType (state) {
      // return (JSON.stringify(state.payType) !== '{}') ? state.payType : state.payTypeList[0]
      return state.payType
    }
  })
}
</script>
<style lang='less'>
.payType {
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 12px;
  color: #6b7886;
  background: #ffffff;
}
</style>
