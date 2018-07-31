<!-- 车型选择视图 -->
<template>
  <router-link to="carTypePick" append>
	<div class="changeCarType">
		<i class="type1"></i>
		{{carType.name}}
	</div>
  </router-link>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'changeCarType',
  data () {
    return {
      carTypeList: this.$store.state.carTypeList
    }
  },
  computed: mapState({
    carType (state) {
      return state.carType
    }
  })
}
</script>
<style lang='less'>
.changeCarType{
	height: 50px;
	line-height: 50px;
	text-align: center;
  font-size: 12px;
  color: #6b7886;
  //background: #ffffff;
  .type1{
    display: inline-block;
    width: 24px;
    height: 9px;
    margin-right: 4px;
    background: url('../../assets/images/car-type1.png') no-repeat center;
    background-size: 100% 100%;
  }
}
</style>