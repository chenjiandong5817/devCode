<template>
  <div>
    <raiis-table :data="apiGridData"
              style="width: 100%"
              :row-style="handleRowStyle"
              :tooltip="false"
              :hoverRow="false"
              :show-header="!hiddenHeader"
              >
      <raiis-table-column
        v-for="column in columns"
        :key="column.name"
        :prop="column.name"
        :label="multiLang(column.label || '')"
        :width="column.style.width"
        :min-width="column.style.minWidth || 0"
        :align="column.style.textAlign"
        :label-class-name="column.labelClassName"
        :columnStyle="column.style"
        :headerStyle="Object.assign({}, bodyStyle.defaultHeaderStyle, column.headerStyle)">
        <template scope="scope">
          <template v-if="column.displayMode.marquee">
            <marquee :class="{'marquee-inline': column.displayMode.subColumn && !column.displayMode.subColumn.wrap}"
              :style="Object.assign({}, bodyStyle.defaultRowStyle, {
                fontSize: column.style.fontSize || `42px`,
                color: column.displayMode.transColor
                  ? Tools.transTextProp(Tools.handleDisplayMode(column.displayMode, scope.row, column.name), column.displayMode.transColor)
                  : (column.style.color || ''),
                height: column.style.height
              })"
              :speed="column.displayMode.marquee.speed"
              :twinkled="column.displayMode.twinkle ? Boolean(Tools.transTextProp(Tools.handleDisplayMode(column.displayMode, scope.row, column.name), column.displayMode.twinkle)) : false"
              >
                {{multiLang(Tools.handleDisplayMode(column.displayMode, scope.row, column.name))}}
            </marquee><!--
          --><template v-if="column.displayMode.subColumn">
              <template v-for="subColumn in column.displayMode.subColumn.columns">
                <template v-if="subColumn.displayMode.marquee && Tools.handleDisplayMode(subColumn.displayMode, scope.row,subColumn.name)">
                  <marquee v-if="subColumn.displayMode.marquee.modifier && scope.row[subColumn.name]"
                    :class="{'marquee-inline': column.displayMode.subColumn && !column.displayMode.subColumn.wrap}" :key="subColumn.name"
                    :style="Object.assign({}, bodyStyle.defaultRowStyle, subColumn.displayMode.marquee.modifier.style)"
                  >
                    {{subColumn.displayMode.marquee.modifier.value}}
                  </marquee><!--
               --><marquee
                    :class="{'marquee-inline': column.displayMode.subColumn && !column.displayMode.subColumn.wrap}"
                    :key="subColumn.name"
                    :style="Object.assign({}, bodyStyle.defaultRowStyle, {
                      fontSize: subColumn.style.fontSize || `42px`,
                      color: subColumn.displayMode.transColor
                        ? Tools.transTextProp(Tools.handleDisplayMode(subColumn.displayMode, scope.row, subColumn.name), subColumn.displayMode.transColor)
                        : (subColumn.style.color || column.style.color || ''),
                      height: subColumn.style.height,
                      width: subColumn.style.width
                    })"
                    :speed="subColumn.displayMode.marquee.speed"
                    :twinkled="subColumn.displayMode.twinkle ? Boolean(Tools.transTextProp(Tools.handleDisplayMode(subColumn.displayMode, scope.row,subColumn.name), subColumn.displayMode.twinkle)) : false"
                    >
                      {{multiLang(Tools.handleDisplayMode(subColumn.displayMode, scope.row, subColumn.name))}}
                  </marquee>
                </template>
              </template>
            </template>
          </template>
          <carousel
            v-if="column.displayMode.logo"
            :height="column.style.height || bodyStyle.defaultRowStyle.height"
            :interval="column.displayMode.logo.interval || 7000"
            arrow="never"
            indicator-position="none"
            :mouseEvent="false">
            <carousel-item
              translateDirection="X"
              v-for="item in (Tools.handleMultiSourceText(column.displayMode.logo, scope.row, column.name))"
              :key="item"
              :style="{
                textAlign: column.style.textAlign
              }">
              <div :style="{
                   display: 'flex',
                   justifyContent: 'center',
                   alignItems: 'center',
                   height: '100%'
                   }">
                <img :src="`${SYSCONFIG.IMGPATH}/${column.displayMode.logo.type}/${column.displayMode.logo.size}/${item}`"
                 :class="{'radius-class': column.displayMode.logo.radius}"
                 :style="Object.assign({
                   height: '100%',
                   lineHeight: '100%'
                   }, column.displayMode.logo.style)"></img>
              </div>
            </carousel-item>
          </carousel>
          <flop
            v-else-if="column.displayMode.type === 'Flop'"
            :height="column.style.height"
            >
            <flop-item
              v-for="item in (typeof scope.row[column.name] === 'object' ? scope.row[column.name] : scope.row[column.name].split('/'))"
              :key="item">
              <template>
                <div v-if="column.flopType === 'text'">
                  {{ item }}
                </div>
                 <img v-else :src="item" :class="'radius-class'" :style="{
                    width: column.style.height,
                    height: column.style.height
                  }" ></img>
              </template>
            </flop-item>
          </flop>
        </template>
      </raiis-table-column>
    </raiis-table>
  </div>
</template>

<script>
// import JSONDATA from '../../static/data/data.json'
import Marquee from './MarqueeText'
import Flop from './Flop/Flop'
import FlopItem from './Flop/Flop-item'
import RaiisTable from './table/table'
import RaiisTableColumn from './table/table-column'
// import debounce from 'throttle-debounce/debounce'
import carousel from './carousel/Carousel'
import carouselItem from './carousel/Carousel-item'
import TwinkleText from './TwinkleText'
import Tools from './../common/js/template-tools'
import SYSCONFIG from './../common/config/sysconfig'
export default {
  props: {
    apiGridData: {
      type: Array,
      default: () => []
    },
    apiPager: {
      type: Object,
      default: () => {}
    },
    columns: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 当前语言
    currentLang: {
      type: String,
      default: 'en-ww'
    },
    bodyStyle: {},
    hiddenHeader: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      // 列表数据
      gridData: {},
      Tools: Tools,
      SYSCONFIG: SYSCONFIG
    }
  },
  // 引入组件
  components: {
    Marquee: Marquee,
    Flop: Flop,
    FlopItem: FlopItem,
    RaiisTable: RaiisTable,
    RaiisTableColumn: RaiisTableColumn,
    carousel: carousel,
    carouselItem: carouselItem,
    TwinkleText
  },
  methods: {
    // 显示多语言
    multiLang (target) {
      if (target instanceof Object) {
        return target[this.currentLang] || target['en-ww'] || target['zh-cn']
      } else {
        return target || ''
      }
    },
    // 获取列表body
    getTableBody () {
      return this.$children[0].$children.filter(function (item) {
        return item.$el.className === 'el-table__body'
      })[0]
    },
    // 行样式回调函数
    handleRowStyle (row, index) {
      let rowStripe = this.bodyStyle.defaultRowStyle.rowStripe
      if (!rowStripe || rowStripe.length === 0) {
        rowStripe = ['']
      }
      return Object.assign({}, this.bodyStyle.defaultRowStyle, { backgroundColor: (index % 2 === 0) ? rowStripe[0] : (rowStripe[1] || rowStripe[0]) })
    }
  },
  mounted () {
  }
}
</script>

<style>
.el-table {
  border: 0 !important;
  background-color: transparent !important;
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
  font-weight: normal;
}

/* 标题文本区域默认样式*/
.el-table th.is-leaf {
  background-color: #1E2D5A !important;
  line-height: 100px;
}

.el-table tr {
  transition: all .2s;
  -moz-transition: all .2s;
  -webkit-transition: all .2s;
  -o-transition: all .2s;
  color: #FFFFFF;
}

/* td基本样式 */
td {
  border-bottom: 0px !important;
  padding: 0px 5px 0px 5px;
}

/* td 文本框基本样式 不换行 */
td .cell {
  /* padding-left: 0px !important;
  padding-right: 0px !important; */
  /* margin-left: 5px !important;
  margin-right: 5px !important; */
  white-space: nowrap !important;
}


.radius-class {
  background: #fff;
  -moz-border-radius: 60px;
  -webkit-border-radius: 60px;
  border-radius: 50px;
}

.marquee-inline {
  display: inline-block;
  margin: 0;
  padding: 0;
}

</style>
