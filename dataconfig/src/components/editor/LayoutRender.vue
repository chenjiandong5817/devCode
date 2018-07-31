<script>
import TwinkleText from './../TwinkleText'
import Logo from './../Logo'
import Flop from './../Flop/Flop'
import FlopItem from './../Flop/Flop-item'
import Grid from './../table/table'
import GridColumn from './../table/table-column'
import FlexImageView from './../FlexImageView'
import DoubleLayerText from './../DoubleLayerText'
import Tools from './../../common/js/template-tools'
import MarqueeText from './../../components/MarqueeText'
import Clock from './../../components/Clock'
import SYSCONFIG from './../../common/config/sysconfig'
export default {
  data () {
    return {
      extraParam: {}
    }
  },
  props: {
    templateConf: Object
  },
  components: {
    DoubleLayerText, MarqueeText, Clock, FlexImageView, Grid, GridColumn, Logo, TwinkleText, Flop, FlopItem
  },
  methods: {
    onElementClick (event) {
      event.preventDefault()
      event.stopPropagation()
      console.log('click event')
      // return false
    },
    mockTransLang (target) {
      console.log(target)
      if (target instanceof Object) {
        return target['zh-cn'] || target['en-ww'] || ''
      } else {
        return target || ''
      }
    },
    // 标签渲染选择器
    confRender (h, conf, ajaxData) {
      let rs = {}
      switch (conf.type) {
        case 'Layout':
          rs = this.templateRender(h, conf, ajaxData)
          break
        case 'Row':
          rs = this.rowRender(h, conf, ajaxData)
          break
        case 'Column':
          rs = this.columnRender(h, conf, ajaxData)
          break
        case 'Grid':
          rs = this.gridRender(h, conf, ajaxData)
          break
        case 'DoubleLayerText':
          rs = this.doubleLayerTextRender(h, conf, ajaxData)
          break
        case 'Image':
          rs = this.imageRender(h, conf, ajaxData)
          break
        case 'MarqueeText':
          rs = this.marqueeTextRender(h, conf, ajaxData)
          break
        case 'Clock':
          rs = this.clockRender(h, conf, ajaxData)
          break
        case 'FlexImageView':
          rs = this.flexImageViewRender(h, conf, ajaxData)
          break
        case 'Logo':
          rs = this.logoRender(h, conf, ajaxData)
          break
        case 'Flop':
          rs = this.flopRender(h, conf, ajaxData)
          break
        case 'Text':
          rs = this.textRender(h, conf, ajaxData)
          break
        default:
          rs = h()
          console.log('do nothing')
      }
      return rs
    },
    // 最外层渲染
    templateRender (h, conf, ajaxData) {
      return h(
        'div',
        {
          style: Tools.completeStyleSuffix(conf.bodyStyle),
          class: ['template-layout', 'template-editor-over'],
          on: {
            click: this.onElementClick
          },
          attrs: {
            id: conf.timestamp
          }
        },
        [
          this._l(conf.rows, (row, index) => this.confRender(h, row, ajaxData))
        ]

      )
    },
    // 行渲染
    rowRender (h, conf, ajaxData) {
      return h(
        'el-row',
        {
          attrs: {
            id: conf.timestamp,
            type: 'flex',
            align: conf.align,
            justify: conf.justify
          },
          class: 'template-editor-over',
          style: Tools.completeStyleSuffix(conf.bodyStyle)
        },
        [
          this._l(conf.cols, (col, colIdx) => this.confRender(h, col, ajaxData))
        ]
      )
    },
    // 列渲染
    columnRender (h, conf, ajaxData) {
      return h(
        'el-col',
        {
          attrs: {
            id: conf.timestamp,
            span: conf.span
          },
          style: Tools.completeStyleSuffix(conf.bodyStyle)
        },
        [
          this._l(conf.children, (child, childIdx) => this.confRender(h, child, ajaxData))
        ]
      )
    },
    // 新版列表渲染
    gridRender (h, conf, ajaxData) {
      let list = ajaxData instanceof Array ? ajaxData : [ajaxData]
      let bodyStyle = Tools.completeStyleSuffix(conf.bodyStyle)
      let handleRowStyle = (row, index) => {
        let rowStripe = bodyStyle.defaultRowStyle.rowStripe
        if (!rowStripe || rowStripe.length === 0) {
          rowStripe = ['']
        }
        return Object.assign({}, bodyStyle.defaultRowStyle, { backgroundColor: (index % 2 === 0) ? rowStripe[0] : (rowStripe[1] || rowStripe[0]) })
      }
      return h(
        'grid',
        {
          props: {
            data: list,
            rowStyle: handleRowStyle,
            tooltip: false,
            hoverRow: false,
            showHeader: !conf.hiddenHeader
          },
          attrs: {
            id: conf.timestamp
          },
          style: Tools.completeStyleSuffix(Object.assign({}, {width: '100%'}, bodyStyle.style))
        },
        [
          this._l(conf.columns, (column, columnIdx) => this.gridColumnRender(h, column, list, bodyStyle))
        ]
      )
    },
    // 列表字段渲染
    gridColumnRender (h, conf, ajaxData, defaultStyle) {
      let style = Tools.completeStyleSuffix(conf.style)
      return h(
        'grid-column',
        {
          props: {
            prop: conf.name,
            label: this.mockTransLang(conf.label) || '',
            width: style.width,
            minWidth: style.minWidth,
            align: conf.style.textAlign,
            columnStyle: style,
            headerStyle: Object.assign({}, defaultStyle ? defaultStyle.defaultHeaderStyle : {}, Tools.completeStyleSuffix(conf.headerStyle))
          },
          attrs: {
            id: conf.timestamp
          },
          on: {
            click: this.onElementClick
          },
          scopedSlots: {
            default: scope => this._l(conf.children, (child, childIdx) => this.confRender(h, child, scope.row))
          }
        }
      )
    },
    // logo切换渲染
    logoRender (h, conf, ajaxData) {
      let list = Tools.handleMultiSource(conf, ajaxData)
      let data = list.map(item => {
        return SYSCONFIG.IMGPATH + Tools.genImgPath(conf, item, this.deviceInfo.airportCode)
      })
      return h(
        'logo',
        {
          props: {
            data: data,
            height: conf.height instanceof String ? conf.height : `${conf.height}px`,
            algin: conf.align,
            interval: conf.interval,
            type: conf.type,
            size: conf.size,
            radius: conf.radius,
            imgStyle: Tools.completeStyleSuffix(conf.style)
          }
        }
      )
    },
    // 翻转图片切换渲染
    flopRender (h, conf, ajaxData) {
      return h(
        'flop',
        {
          style: Tools.completeStyleSuffix(conf.style),
          props: {
            interval: conf.interval
          }
        },
        [
          ajaxData && ajaxData instanceof Array ? (
            this._l(ajaxData, data => {
              // console.log(data)
              return h(
                'flop-item',
                [
                  this.confRender(h, conf.item, data)
                ]
              )
            })
          ) : (
            this._l(Tools.handleMultiSource(conf, ajaxData), (item, index) => {
              let itemConf = Object.assign({}, conf.item)
              Tools.coverFlopValue(itemConf, 'columnName', 'flopItemName')
              return h(
                'flop-item',
                [
                  this.confRender(h, itemConf, {flopItemName: item})
                ]
              )
            })
          )
        ]
      )
    },
    // 滑动图片视图
    flexImageViewRender (h, conf, ajaxData) {
      let list = ajaxData
      let data = list.map(item => {
        return SYSCONFIG.IMGPATH + Tools.genImgPath(conf, item, this.deviceInfo.airportCode)
      })
      return h(
        'flex-image-view',
        {
          props: {
            showType: conf.showType,
            data: data,
            translateX: conf.translateX,
            translateY: conf.translateY,
            imgStyle: Tools.completeStyleSuffix(conf.imgStyle),
            spacing: conf.spacing,
            autoSize: conf.autoSize
          },
          style: Tools.completeStyleSuffix(conf.style)
        }
      )
    },
    // 双层文本渲染
    doubleLayerTextRender(h, conf, ajaxData) {
      return h(
        'double-layer-text',
        {
          attrs: {
            align: conf.content.align,
            translateX: conf.content.translateX,
            translateY: conf.content.translateY,
            topStyle: Tools.completeStyleSuffix(conf.content.topStyle),
            bottomStyle: Tools.completeStyleSuffix(conf.content.bottomStyle)
          }
        },
        [
          h(
            'div',
            { slot: 'topSlot' },
            [
              conf.content.topContent && conf.content.topContent.length > 0
              ? this._l(conf.content.topContent, (content, index) => this.confRender(h, content, ajaxData))
              : ''
            ]
          ),
          h(
            'div',
            { slot: 'bottomSlot' },
            [
              conf.content.bottomContent && conf.content.bottomContent.length > 0
              ? this._l(conf.content.bottomContent, (content, index) => this.confRender(h, content, ajaxData))
              : ''
            ]
          )
        ]
      )
    },
    // 滚动文本
    marqueeTextRender (h, conf, ajaxData) {
      let hidden = false
      if (conf.hiddenBy) {
        for (let i = 0; i < conf.hiddenBy.length; i++) {
          let item = conf.hiddenBy[i]
          if (typeof item !== 'object') {
            item = { [item]: true }
          }
          for (let key in item) {
            if (!ajaxData[key]) {
              hidden = item[key]
            } else {
              hidden = !item[key]
            }
          }
        }
      }
      return h(
        'marquee-text',
        {
          style: Tools.completeStyleSuffix(conf.style),
          props: {
            speed: conf.speed,
            always: conf.always,
            vertical: conf.vertical,
            backZero: conf.backZero,
            hidden: hidden
          }
        },
        [
          this._l(conf.content, (content, index) => this.confRender(h, content, ajaxData))
        ]
      )
    },
    // 图片渲染
    imageRender (h, conf, ajaxData = {}) {
      // 造假行的时候img标签会提示url错误，屏蔽掉
      if (conf.columnName && !conf.columnName.includes(',') && !ajaxData[conf.columnName]) {
        return
      }
      let imgPath = SYSCONFIG.IMGPATH + Tools.genImgPath(conf, ajaxData, this.deviceInfo.airportCode)
      if (conf.url) {
        imgPath = conf.url
      }
      return h(
        'img',
        {
          attrs: {
            src: imgPath
          },
          style: Tools.completeStyleSuffix(conf.style)
        }
      )
    },
    // 渲染时钟
    clockRender (h, conf, ajaxData = {}) {
      return h(
        'clock'
      )
    },
    // 文本渲染
    textRender (h, conf, ajaxData = {}) {
      let realText = Tools.renderText(conf, ajaxData)
      // let text = this.transLang(realText)
      let text = realText
      if (text) {
        text = `${(conf.prefix || '') + text + (conf.suffix || '')}`
      }
      return h(
        'twinkle-text',
        {
          props: {
            twinkled: Boolean(Tools.transTextProp(realText, conf.twinkle)),
            inline: conf.tag === 'span' || (conf.style && conf.style.display === 'inline-block')
          }
        },
        [
          h(
            conf.tag || 'div',
            {
              style: Object.assign(
                {},
                Tools.completeStyleSuffix(conf.style),
                conf.transColor ? {
                  color: Tools.transTextProp(realText, conf.transColor)
                } : {}
              )
            },
            [
              conf.replace ? Tools.replaceText(text, conf.replace, ajaxData) : text,
              conf.mergeText ? (Tools.addsMergeText(realText, conf.mergeText, ajaxData) || []).map(item => {
                return item // this.transLang(item)
              }).join('') : ''
            ]
          )
        ]
      )
    }
  },
  created () {
  },
  mounted () {
    console.log('mounted')
  },
  render (h) {
    console.log('go render')
    return this.confRender(h, Tools.deepCopy(this.templateConf))
  }
}
</script>
<style>

.template-layout .el-table table {
  border-collapse: collapse;
}
.template-layout .el-table__body-wrapper {
  overflow: hidden !important;
}
.template-layout .el-table::after, .el-table::before {
  height: 0 !important;
  content: none !important;
}
/* 标题默认样式 */
.template-layout .el-table th {
  font-weight: normal;
}

/* 标题文本区域默认样式*/
.template-layout .el-table th.is-leaf {
  background-color: #1E2D5A !important;
  line-height: 100px;
}

.template-layout .el-table tr {
  transition: all .2s;
  -moz-transition: all .2s;
  -webkit-transition: all .2s;
  -o-transition: all .2s;
  color: #FFFFFF;
}

/* td基本样式 */
.template-layout .el-table td {
  border-bottom: 0px !important;
  padding: 0px 5px 0px 5px;
}

/* td 文本框基本样式 不换行 */
.template-layout .el-table td .cell {
  /* padding-left: 0px !important;
  padding-right: 0px !important; */
  /* margin-left: 5px !important;
  margin-right: 5px !important; */
  white-space: nowrap !important;
}


.template-layout .radius-class {
  background: #fff;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>
