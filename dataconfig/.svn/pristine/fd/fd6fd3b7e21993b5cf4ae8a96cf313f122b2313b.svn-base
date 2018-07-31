<template>
  <div>
    <el-table :data="gridData"
              style="width: 100%"
              stripe>
      <el-table-column prop="logo"
                       label=""
                       width="120"
                       min-width="120"
                       class-name="f_logo"
                       label-class-name="labelClass">
        <template scope="scope">
          <el-carousel height="80px" :interval="7000" arrow="never" indicator-position="none" :mouseEvent="false">
            <el-carousel-item v-for="item in scope.row.logo.split('/')" :key="item">
              <img :src="'./../../static/logo/220x220/' + item + '.jpg'"
                 class="airline-img" />
            </el-carousel-item>
          </el-carousel>
        </template>
      </el-table-column>
      <el-table-column prop="flight"
                       :label="multiLang(titleData.flight)"
                       width="196"
                       min-width="156"
                       class-name="marquee"
                       label-class-name="labelClass"
                       :show-overflow-tooltip="false">
        <template scope="scope">
          <Marquee :marqueeKey="scope.$index"
                   name="flight"
                   :content="scope.row.flight.toUpperCase()"
                   :width="196"></Marquee>
        </template>
      </el-table-column>
      <el-table-column prop="passby_destination"
                       :label="multiLang(titleData.dest)"
                       width="260"
                       min-width="260"
                       class-name="marquee"
                       label-class-name="labelClass"
                       >
        <template scope="scope">
          <Marquee :marqueeKey="scope.$index"
                   name="passby_destination"
                   :speed="7"
                   :fontSize="44"
                   :height="55"
                   :content="multiLang(scope.row.passby_destination)"
                   :width="260"></Marquee>
        </template>
      </el-table-column>
      <el-table-column prop="actualcheckinclose"
                       :label="multiLang(titleData.time)"
                       width="280"
                       min-width="280"
                       class-name="marquee"
                       label-class-name="labelClass">
        <template scope="scope">
          <Marquee :marqueeKey="scope.$index"
                   name="actualcheckinclose"
                   :content="scope.row.actualcheckinclose.toUpperCase()"
                   :width="260"></Marquee>
        </template>
      </el-table-column>
      <el-table-column prop="checkincounter"
                       :label="multiLang(titleData.counter)"
                       min-width="245"
                       class-name="marquee"
                       label-class-name="labelClass">
        <template scope="scope">
          <Marquee :marqueeKey="scope.$index"
                   name="checkincounter"
                   :content="induceCounter(scope.row.checkincounter.toUpperCase())"></Marquee>
        </template>
      </el-table-column>
      <el-table-column prop="flightstatus"
                       :label="multiLang(titleData.status)"
                       width="192"
                       min-width="192"
                       class-name="marquee"
                       label-class-name="labelClass">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import JSONDATA from '../../static/data/data.json'
import Marquee from './Marquee'
export default {
  data() {
    return {
      // 列表数据
      gridData: {},
      // 标题数据， 多语言等
      titleData: {},
      // 当前语言
      lang: 'en',
      // 语言列表
      langs: ['en', 'cn', 'jp']
    }
  },
  computed: {
  },
  // 引入组件
  components: {
    Marquee: Marquee
  },
  methods: {
    // 初始化列表数据
    initGridData: function () {
      var result = []
      for (var i = 0; i < JSONDATA.length; i++) {
        var dataItem = JSONDATA[i]
        var tmp = {}
        tmp.logo = dataItem.carrier
        tmp.flight = dataItem.flight
        // tmp.passby_destination = this.initData[i].passby + ' / ' + this.initData[i].destination
        tmp.passby_destination = {}
        tmp.passby_destination.en = dataItem.lang2 || ''
        tmp.passby_destination.cn = dataItem.lang1 || ''
        tmp.passby_destination.jp = dataItem.lang3 || ''
        tmp.actualcheckinclose = dataItem.actualcheckinclose
        tmp.checkincounter = dataItem.checkincounter
        result.push(tmp)
      }
      // console.log(result)
      this.gridData = result
    },
    // 初始化标题数据
    initTitleData () {
      this.titleData = {
        flight: { cn: '航班号', en: 'FLIGHT' },
        dest: { cn: '经停/目的地', en: 'DESTINATION' },
        time: { cn: '办理时间', en: 'TIME' },
        counter: { cn: '办理柜台', en: 'COUNTER' },
        status: { cn: '航班状态', en: 'STATUS' }
      }
    },
    // 匹配数字，用于登机口
    matchNumber: function (str) {
      if (!str) {
        return
      }
      return str.match(/[1-9][0-9]*/g).join()
    },
    // 归纳登机口连号，比如【11，12，13】转成 11-13
    induceCounter: function (val) {
      var result = []
      var tmpArray = val.split(',')
      if (tmpArray) {
        tmpArray.sort()
        var begin
        var end
        var isCon = false
        for (var i = 0; i <= tmpArray.length - 1; i++) {
          var current = Number(this.matchNumber(tmpArray[i])) + 1
          var next = Number(this.matchNumber(tmpArray[i + 1]))
          if (current === next) {
            if (!isCon) {
              begin = tmpArray[i]
            }
            isCon = true
          } else {
            end = tmpArray[i]
            isCon = false
            if (begin && end) {
              result.push(begin + '-' + end)
            } else if (begin) {
              result.push(begin)
            } else {
              result.push(end)
            }
            begin = null
            end = null
          }
        }
      }
      return result.join(',')
    },
    // 切换语言定时器
    changeLangTimer () {
      var changeLang = () => {
        // this.lang = this.lang === 'cn' ? 'en' : 'cn'
        let idx = this.indexOf(this.langs, this.lang)
        if (idx >= this.langs.length - 1 && idx > -1) {
          idx = 0
        } else {
          idx += 1
        }
        this.lang = this.langs[idx]
      }
      setInterval(changeLang, 15000)
    },
    // 显示多语言
    multiLang (target) {
      return target[this.lang] || target['en']
    },
    // 自定义数组indexof方法
    indexOf: function (arr, str) {
      // 如果可以的话，调用原生方法
      if (arr && arr.indexOf) {
        return arr.indexOf(str)
      }
      var len = arr.length
      for (var i = 0; i < len; i++) {
        // 定位该元素位置
        if (arr[i] === str) {
          return i
        }
      }
      // 数组中不存在该元素
      return -1
    },
    // 获取列表body
    getTableBody () {
      return this.$children[0].$children.filter(function (item) {
        return item.$el.className === 'el-table__body'
      })[0]
    },
    // 均分高度，用于自适应
    averageHeight () {
      let self = this
      setTimeout(() => {
        let trs = self.$children[0].$el.children[2].getElementsByTagName('tr')
        let autoHeight = (document.documentElement.clientHeight - self.$children[0].$el.children[1].offsetHeight) / trs.length
        let fillPX = Math.floor((autoHeight - Math.floor(autoHeight)) * trs.length)
        autoHeight = Math.floor(autoHeight)
        // console.log(autoHeight)
        for (let i = 0; i < trs.length; i++) {
          let fillHeight = autoHeight
          if (i < fillPX) {
            fillHeight += 1
          }
          trs[i].style.height = fillHeight + 'px'
        }
      }, 200)
    },
    // 清除element-ui自带tooltip
    clearTooltip () {
      this.getTableBody().$refs.tooltip.handleShowPopper = function () {}
    }
  },
  mounted () {
    this.changeLangTimer()
    window.onresize = () => {
      this.averageHeight()
    }
    window.onresize()
    this.clearTooltip()
  },
  created () {
    this.initTitleData()
    this.initGridData()
  }
}
</script>

<style>
body {
  background-color: #1E2D5A;
  overflow: hidden;
}
.el-table {
  border: 0 !important;
}
.el-table__body-wrapper {
  overflow: hidden !important;
}
.el-table::after, .el-table::before {
  height: 0 !important;
  content: none !important;
}
/* 标题默认样式 */
.el-table th {
  font-size: 34px ;
  text-align: left;
  color: #FAC35A !important;
  border-bottom: 3px solid !important;
  border-bottom-color: #FAC35A !important;
  font-weight: normal;
  border-right: 0 !important;
}
/* 标题文本区域默认样式*/
.el-table th.is-leaf {
  background-color: #1E2D5A !important;
  line-height: 100px;
}
.labelClass{
  /* 背景颜色 */
  background-color: #1E2D5A !important;
  /* 底线颜色 */
  border-bottom-color: #FAC35A !important;
  /* 字体大小 */
  font-size: 34px ;
  /* 字体颜色 */
  color: #FAC35A !important;
  /* 格局 */
  text-align: left;
  /* 行高 */
  line-height: 100px !important;
  /* 高度  默认和行高一致*/
  height: 100px;
}

.el-table tr {
  transition: all .2s;
  -moz-transition: all .2s;
  -webkit-transition: all .2s;
  -o-transition: all .2s;
}

/* td基本样式 */
td {
  background-color: #284682;
  color: #FFFFFF;
  /*line-height: 87px;*/
  border-bottom: 0px !important;
  padding: 0px 5px 0px 5px;
}


.el-table--striped .el-table__body tr:nth-child(2n) td {
  background-color: #1E2D5A !important;
}

.el-table--striped .el-table__body tr:nth-child(2n-1) td {
  background-color: #284682 !important;
}

/* td 文本框基本样式 不换行 */
td .cell {
  padding-left: 0px !important;
  padding-right: 0px !important;
  margin-left: 5px !important;
  margin-right: 5px !important;
  white-space: nowrap !important;
  /*line-height: 55px !important;*/
}

.f_logo {
  text-align: center;
}

.airline-img {
  width: 75px;
  height: 75px;
  background: red;
  -moz-border-radius: 60px;
  -webkit-border-radius: 60px;
  border-radius: 50px;
  margin-left: 5px;
  vertical-align: middle;
}

.marquee {
  /* 背景颜色 */
  background-color: #284682;
  /* 字体大小 */
  font-size: 42px;
  /* 字体颜色 */
  color: #fff;
  /* 格局 */
  text-align: left;
}
</style>
