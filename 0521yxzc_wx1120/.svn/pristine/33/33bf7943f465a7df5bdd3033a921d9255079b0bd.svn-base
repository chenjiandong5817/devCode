<!-- 城市关键词检索结果视图 -->
<template>
  <div class="autocompleteList">
    <scroll class="wrapper" v-bind:data="location.tips">
        <ul class="matchlist">
            <li v-for="(item ,index) in location.tips" class="list-item" @click="switch_state({name:'selectCity',val:item.name});sendMsgToParent(item)">{{item.name}}</li>
        </ul>
    </scroll>
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
const scroll = () => import('@/components/scroll')
export default {
  data () {
    return {
    }
  },
  computed: mapState({
    ...mapState([
      'location'
    ]),
    state (state) {
      return state
    }
  }),
  methods: {
    ...mapActions({
      'switch_state': 'switch_location'
    }),
    sendMsgToParent (value) {
      console.log('this.state确定上下车城市后的', this.state)
      this.$emit('listenChildEvent', value)
    }
  },
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
.autocompleteList {
  .wrapper {
    max-height: 500px;
    width: 100%;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, .1);
    ul.matchlist {
      position: relative;
      display: block;
      background-color: #fff;
      overflow: hidden;
      li.list-item {
        position: relative;
        height: 50px;
        line-height: 50px;
        padding: 0 16px;
        font-size: 14px;
        color: #666;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
        &:active {
          background: rgba(0, 0, 0, 0.04)
        }
        &:after {
          content: '';
          position: absolute;
          width: 100%;
          left: 0;
          border-bottom: 1px solid #eee;
          transform: scaleY(.5)
        }
      }
    }
  }
}
</style>
