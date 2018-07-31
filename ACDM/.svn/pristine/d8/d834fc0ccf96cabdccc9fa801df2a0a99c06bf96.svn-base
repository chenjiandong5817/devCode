<template>
  <div class="fishbone-module-container">
    <div class="fishbone-toolbar">
      test
    </div>
    <div class="refresh-tag">
      <transition name="fade">
        <el-tag size="small" closable v-if="statusUpdateVisible">节点状态已更新...</el-tag>
      </transition>
    </div>
    <fish-bone :height="120" :width="140">
      <template v-for="(item, index) in steps">
        <bone :key="index" :label="item.value" :status="stepStatus[index]" :position="index < 2 ? 'top' : ''">
          <div class="fishbone-item-slot-demo">
            <div>
              <div class="">{{item.source}}</div>
              <div :class="{'is-empty': !item.scheduletime}">{{formatTime(item.scheduletime) || '-'}}</div>
            </div>
            <div>
              <div :class="{'is-empty': !item.estimatedtime}">{{formatTime(item.estimatedtime) || '-'}}</div>
              <div :class="{'is-empty': !item.actualtime}">{{formatTime(item.actualtime) || '-'}}</div>
            </div>
          </div>
        </bone>
      </template>
    </fish-bone>
    <el-button @click="test">click</el-button>
  </div>
</template>
<script>
import FishBone from '@/components/fishbone'
import Bone from '@/components/fishbone/bone'
import moment from 'moment'
export default {
  components: {
    FishBone, Bone
  },
  data () {
    return {
      steps: [
        { name: '落地', value: '落地', source: '指挥中心', scheduletime: 1526955587000, estimatedtime: 1526955647000, actualtime: 1526955647000 },
        { name: '上轮挡', value: '上轮挡', source: '指挥中心2号', scheduletime: 1526956523000, estimatedtime: 1526956523000, actualtime: 1526956523000 },
        { name: '靠桥及客梯车对接', value: '靠桥及客梯车对接', source: '指挥中心', scheduletime: 1526956615000, estimatedtime: null, actualtime: 1526956615000 },
        { name: '开货舱门', value: '开货舱门', source: '指挥中心', scheduletime: 1526956898000, estimatedtime: 1526956920000, actualtime: 1526956990000 },
        { name: '开客舱门', value: '开客舱门', source: '指挥中心', scheduletime: 1526957281000, estimatedtime: 1526957281000, actualtime: 1526957281000 },
        { name: '开始登机', value: '开始登机', source: '指挥中心', scheduletime: 1526957400000, estimatedtime: 1526958300000, actualtime: 1526958300000 },
        { name: '完成登机', value: '完成登机', source: '指挥中心', scheduletime: 1526959200000, estimatedtime: 1526959200000, actualtime: 1526959200000 },
        { name: '关客舱门', value: '关客舱门', source: '指挥中心', scheduletime: 1526959558000, estimatedtime: null, actualtime: 1526959738000 },
        { name: '撤桥及客梯车撤离', value: '撤桥及客梯车撤离', source: '指挥中心', scheduletime: 1526959800000, estimatedtime: 1526959800000, actualtime: 1526959800000 },
        { name: '关闭货舱门', value: '关闭货舱门', source: '指挥中心', scheduletime: 1526959860000, estimatedtime: 1526960100000, actualtime: null },
        { name: '撤轮挡', value: '撤轮挡', source: '指挥中心', scheduletime: 1526968834000, estimatedtime: 1526968834000, actualtime: null },
        { name: '起飞', value: '起飞', source: '指挥中心', scheduletime: 1526972074000, estimatedtime: 1526972074000, actualtime: null }
      ],
      allowShowStatus: true,
      refreshStatusTimer: null,
      refreshInterval: 30000,
      statusUpdateVisible: false
    }
  },
  computed: {
    stepStatus () {
      if (this.allowShowStatus) {
        return this.steps.map(item => this.getStatus(item))
      } else {
        return []
      }
    }
  },
  watch: {
    allowShowStatus (val) {
      let self = this
      if (!val) {
        self.statusUpdateVisible = true
      } else {
        setTimeout(() => {
          self.statusUpdateVisible = false
        }, 3000)
      }
    }
  },
  mounted () {
    this.startStatusTask()
  },
  methods: {
    formatTime (timestamp) {
      if (!timestamp) {
        return null
      }
      return moment(timestamp).format('HH:mm')
    },
    getStatus (item) {
      let finish = item.actualtime
      let time = item.estimatedtime || item.scheduletime
      if (finish) {
        return 'finish'
      } else if (!time) {
        return ''
      } else {
        let warningTime = 5 * 60 * 1000 // 警告时间
        let diff = moment(time).diff(moment())
        if (diff < 0) { // 超时
          return 'danger'
        } else if (diff < warningTime) {
          return 'warning'
        } else {
          return ''
        }
      }
    },
    startStatusTask () {
      let self = this
      this.refreshStatusTimer = setInterval(() => {
        console.log('开始刷新节点状态...')
        self.allowShowStatus = false
        self.$nextTick(() => {
          self.allowShowStatus = true
        })
      }, this.refreshInterval)
    },
    stopTask () {
      this.refreshStatusTimer && clearInterval(this.refreshStatusTimer)
    },
    test () {
      this.allowShowStatus = !this.allowShowStatus
    }
  },
  beforeDestroy () {
    this.stopTask()
  }
}
</script>
<style lang="scss">
.fishbone-module-container {
  .refresh-tag {
    position: absolute;
    right: 10px;
  }
  .fishbone-item-slot-demo {
    > div {
      display: inline-block;
      &:first-child {
        text-align: right;
        margin-left: 0px;
        padding-right: 5px;
      }
      &:not(:first-child) {
        padding-left: 5px;
      }
      > div {
        font-size: 14px;
        direction: rtl;
        width: 36px;
        &.is-empty {
          text-align: center;
        }
      }
    }
    transform: translateX(-49px);
  }
}
</style>
