<template>
  <router-link to="flightPage" append>
  <div class="flight">
    <i class="plane"></i>
    <span>{{airportNum}} {{airportDate | Dateformt}}</span>
  </div>
  </router-link>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'flight',
  filters: {
    Dateformt (val) {
      if (val) {
        let time = new Date(val)
        return time.getMonth() + 1 + '月' + time.getDate() + '日'
      } else {
        return ''
      }
    }
  },
  computed: mapState({
    airportNum (state) { return state.airport.num ? state.airport.num + ' / ' : '航班信息' },
    airportDate (state) { return state.airport.time }
  })
}
</script>
<style lang="less" scoped>
.flight{
  box-sizing: border-box;
  position: relative;
  padding: 0 15px;
  height: 50px;
  line-height: 50px;
  color: #6b7886;
  background: #fff;
  span{
    line-height: 100%;
    font-size: 12px;
  }
}
i.plane{
  position: relative;
  display: inline-block;
  width: 12px;
  height: 12px;
  margin:0 6px; 
  background: url('../../assets/images/plane.png') no-repeat center;
  background-size:100% 100%; 
}
</style>