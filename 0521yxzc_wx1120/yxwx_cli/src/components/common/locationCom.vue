<template>
  <router-link to="suggest" append>
	<div class="location-item" v-bind:class="{'location_to':(type =='to')}" @click="toSuggest()">
		<p class="location-field" v-bind:class="{'border-bottom-1px':(type!='to')}">
      {{location}}
		</p>
	</div>
</router-link>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'locationCom',
  props: ['type'],
  computed: mapState({
    location (state) {
      // 变换车型
      // 有值就显示值，没值就显示请选择上车地点
      if (this.type === 'from') {
        return state.locationFrom.name ? state.locationFrom.name : '请选择上车地点'
      }
      return state.locationTo.name ? state.locationTo.name : '请选择下车地点'
    }
  }),
  mounted () {
  },
  methods: {
    toSuggest () {
      let type = (this.type !== 'to')
      this.$store.dispatch('switch_location', {name: 'startOrEnd', val: type})
      // this.$router.push({path: '/suggest'})
    }
  }
}
</script>
<style lang="less">
@import "../../assets/css/less/variable.less";
.location-item {
  position: relative;
  padding-left: 40px;
  // background: #ffffff;
  &:before {
    position: absolute;
    z-index: 1;
    content: '';
    top: 50%;
    left: 23px;
    margin-top: -3px;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    background-color: #3cbca3;
  }
  &.location_to {
    &:before {
      background-color: #fc9153;
    }
  }
  .location-field {
    height: 50px;
    line-height: 50px;
    overflow: hidden;
    white-space: nowrap;
    font-size: 16px;
    font-weight: normal;
    font-style: normal;
    font-stretch: normal;
    letter-spacing: normal;
    text-overflow: ellipsis;
    text-align: left;
    color: @fc_base;
  }
}

</style>