<template>
	<div class="location-item location-to" v-bind:class="{'location_to':(type =='to')}"  @click="loca_Picker">
		<p class="location-field" v-bind:class="{'border-bottom-1px':(type!='to')}">
      {{location}}
		</p>
	</div>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'locationPick',
  props: ['type', 'data', 'limit'],
  computed: mapState({
    location (state) {
      // 变换车型
      // 有值就显示值，没值就显示请选择上车地点
      if (this.type === 'from') {
        return state.locationFrom.name ? state.locationFrom.name : '请选择上车地点'
      }
      return state.locationTo.name ? state.locationTo.name : '请选择下车地点'
    },
    staticLocation (state) {
      let self = this
      // 因为高德地图数据结构与cube-ui的picker要求不一致，此处重新构建
      let dataArr = []
      this.data.forEach(function (item, index, arr) {
        let data = {
          text: item.name,
          value: item
        }
        if (self.limit && self.limit !== item.startingEnd) {
          return
        }
        dataArr.push(data)
      })
      return dataArr
    }
  }),
  methods: {
    loca_Picker () {
      let type = (this.type !== 'to')
      this.$store.dispatch('switch_location', {name: 'startOrEnd', val: type})
      this.$createPicker({
        title: (this.type === 'from') ? '选择上车地点' : '选择下车地点',
        data: [this.staticLocation],
        onSelect: (selectedVal, selectedIndex, selectedText) => {
          this.$store.dispatch('switch_location', {name: 'location', val: selectedVal[0]})
        },
        onCancel: () => {
          this.$createToast({
            type: 'correct',
            txt: '操作已取消',
            time: 1000
          }).show()
        }
      }).show()
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