<!-- 全国城市列表视图 -->
<template>
    <cube-index-list :data="cityList" :title="title" @select="selectCity" @title-click="clickTitle();sendMsgToParent()" ></cube-index-list>
</template>
<script>
import { mapState } from 'vuex'
export default {
  data () {
    return {
      // title: '当前定位：' + (this.Geolocation.city ? this.Geolocation.city : '...')
    }
  },
  components: {
  },
  computed: mapState({
    cityList (state) {
      return state.cityList
    },
    Geolocation (state) {
      return state.Geolocation
    },
    title (state) {
      return state.Geolocation ? ('当前定位：' + state.Geolocation.city) : '正在定位中...'
    }
  }),
  methods: {
    clickTitle () {
      this.$store.dispatch('selectCity', {
        'name': this.Geolocation.city,
        'tags': this.Geolocation.city,
        // 'cityid': 32,
        'lat': this.Geolocation.location.lat,
        'lng': this.Geolocation.location.lng
      })
    },
    selectCity (val) {
      this.sendMsgToParent()
      this.$store.dispatch('selectCity', val)
    },
    sendMsgToParent () {
      this.$emit('listenChildEvent')
    }
  },
  created () {
  }
}
</script>
<style lang="less">
</style>
