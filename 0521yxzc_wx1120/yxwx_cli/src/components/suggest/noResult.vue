<template>
  <div class="noResult">
    <div class="noResultImg"></div>
    <p>暂无结果，换个词试试吧</p>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  data () {
    return {
    }
  },
  computed: {
    ...mapState([
      'cityList',
      'location'
    ])
  }
}
</script>
<style lang="less" scpoed>
.noResult{
  position: relative;
  text-align: center;
  color: #7a7c81;
  font-size: 14px;
  line-height: 2;
  .noResultImg{
    margin: 0 auto;
    width: 215px;
    height: 140px;
    background: url('../../assets/images/no-result.png') no-repeat center;
    background-size: 100% 100%;
  }
}
</style>
