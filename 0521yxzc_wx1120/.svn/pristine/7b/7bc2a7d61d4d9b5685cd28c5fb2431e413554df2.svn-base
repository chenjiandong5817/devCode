<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="suggest">
            <suggest-head></suggest-head>
            <div class="page-list">
                <div class="citylist">
                    <autocomplete-list v-show="location.addressInputing"></autocomplete-list>
                    <searchHistory v-show="(!location.cityFocus&&!location.addressInputing&&location.addressFocus)"></searchHistory>
                    <!-- <suggest-list></suggest-list> -->
                    <!-- 是否开启本地静态数据 -->
                    <openCityData></openCityData>
                    <cityMatch-list v-show="location.cityInputing"></cityMatch-list>
                    <noResult v-if="noResultState"></noResult>
                </div>
            </div>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const suggestHead = () => import('@/components/suggest/suggestHead.vue')
const suggestList = () => import('@/components/suggest/suggestList.vue')
const openCityData = () => import('@/components/suggest/openCityData.vue')
const autocompleteList = () => import('@/components/suggest/autocompleteList.vue')
const searchHistory = () => import('@/components/suggest/searchHistory.vue')
const cityMatchList = () => import('@/components/suggest/cityMatchList.vue')
const noResult = () => import('@/components/suggest/noResult.vue')
export default {
  name: 'suggets',
  mounted () {
    // 定位功能-暂不开启
    // this.$store.dispatch('Geolocation')
  },
  computed: {
    noResultState () {
      if ((this.location.tips === undefined || this.location.tips.length === 0) && (this.location.cityInputing || this.location.addressInputing)) {
        return true
      } else return false
    },
    ...mapState([
      'location'
    ])
  },
  watch: {
  },
  components: {
    suggestHead,
    suggestList,
    autocompleteList,
    searchHistory,
    cityMatchList,
    noResult,
    openCityData
  }
}
</script>
<style lang="less">
@import "../../assets/css/less/variable.less"; 
.suggest{
  position: fixed;
  z-index: 9;
  top: 0;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  animation-duration: .3s;
  background: #f3f3f3;
}
.page-list {
  position: fixed;
  top: 43px;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.citylist {
  width: 100%;
  height: 100%;
  padding: 0 10px;
  overflow: hidden;
  margin-top: 10px;
  box-sizing: border-box;
}
</style>
