<script>
import TwinkleText from './TwinkleText'
import Logo from './Logo'
import Flop from './Flop/Flop'
import FlopItem from './Flop/Flop-item'
import Grid from './table/table'
import GridColumn from './table/table-column'
import FlexImageView from './FlexImageView'
import DoubleLayerText from './DoubleLayerText'
import axios from 'axios'
import EmptyDataConf from './../../mock/emptyData'
import Tools from './../common/js/template-tools'
import MarqueeText from './../components/MarqueeText'
import Clock from './../components/Clock'
import SYSCONFIG from './../common/config/sysconfig'
import { getTemplate, getSouceData, getDeviceInfo, getDeviceGroup } from './../api'
export default {
  data () {
    return {
      deviceInfo: {},
      deviceGroup: {},
      pagerHelper: {},
      templateConf: null,
      extraParam: {},
      loaded: false,
      apiData: {},
      timers: {},
      langSwitch: {
        langs: [ 'zh-cn', 'en-ww' ],
        current: 'zh-cn',
        interval: 15000,
        timer: null
      }
    }
  },
  components: {
    DoubleLayerText, MarqueeText, Clock, FlexImageView, Grid, GridColumn, Logo, TwinkleText, Flop, FlopItem
  },
  methods: {
    completeDeviceInfo () {
      // 10370 登机口
      // 10217 国内进港
      // 10262 出发航班
      // 3393 登机口候机引导
      // 10063 普通柜台
      // 10313 全开柜台
      // 10245 行李转盘
      // 4051 值机引导
      // 606 登机引导
      // 10221 专柜
      // 601 行李转盘提取
      let deviceId = this.$route.params.deviceId
      console.log('deviceId', deviceId)
      getDeviceInfo(deviceId).then(res => {
        let devices = res.attr.data.list
        console.log('devices', devices)
        // console.log(devices)
        if (res.ok && devices.length > 0) {
          this.deviceInfo = devices[0]
          console.log('this.deviceInfo', this.deviceInfo)
          console.log('device', this.deviceInfo)
          // 把设备信息加入apiData
          this.apiData[SYSCONFIG.CURRENT_DEVICE_INFO] = this.deviceInfo
          console.log('this.apiData', this.apiData)
          console.log('SYSCONFIG.CURRENT_DEVICE_INFO', SYSCONFIG.CURRENT_DEVICE_INFO)
          console.log('this.deviceInfo.id', this.deviceInfo.id)
          this.loadConf(this.deviceInfo.id)
        } else {
          this.$notify({
            type: 'error',
            title: '失败',
            message: '无法获取设备信息'
          })
        }
      }).then(() => {
        // 获取设备分组
        getDeviceGroup(this.deviceInfo.groupId).then(res => {
          if (res.ok && res.attr) {
            console.log('group', res.attr.data)
            this.deviceGroup = res.attr.data
          } else {
            console.warn('当前设备无分组信息或者为单屏设备')
            this.deviceGroup = {
              groupSize: 1
            }
          }
        }).catch(err => {
          console.error(err)
        })
      })
    },
    // 加载模板配置，传入参数设备id
    loadConf (deviceId) {
      console.log('deviceId', deviceId)
      getTemplate({deviceId: deviceId, airportCode: this.deviceInfo.airportCode}).then(res => {
        console.log('res', res)
        console.log(res)
        if (res.ok) {
          this.templateConf = JSON.parse(res.attr ? res.attr.data.content : '{}')

          // let templateConf = require('./../../mock/登机口变更')
          let templateConf = require('./../../mock/国内进港航班')
          this.templateConf = templateConf
          console.log('this.templateConf', this.templateConf)

          console.log('template:', this.templateConf)

          // 加载 extraParam
          this.templateConf && (this.extraParam = this.templateConf.extraParam || {})
          console.log('this.extraParam', this.extraParam)

          if (!this.templateConf) {
            this.$notify({
              type: 'error',
              title: '失败',
              message: '空白的模板配置'
            })
            return
          }
          this.queryAllData()
          this.buildLangSwitch()
        } else {
          this.$notify({
            type: 'error',
            title: '失败',
            message: '获取模板失败'
          })
        }
      })
    },
    // 查询模板内所有数据源id,并进行请求
    queryAllData () {
      // 获取配置内所有的数据源id
      let apiIds = Tools.recurDataSource(this.templateConf)
      console.log('apiIds', apiIds)
      if (apiIds && apiIds.length > 0) {
        let methods = []
        let _this = this
        for (let i = 0; i < apiIds.length; i++) {
          // 过滤假定数据源
          if (apiIds[i].key.includes('$')) {
            continue
          }
          methods.push(() => {
            let params = Object.assign({}, {id: apiIds[i].key}, _this.buildQueryParam(apiIds[i].mainPager))
            console.log('params', params)
            return getSouceData(params)
          })
        }
        axios
          .all(methods.map(method => method()))
          .then(axios.spread((...results) => {
            results.forEach(res => {
              // console.log(res)
              _this.apiData = Object.assign({}, _this.apiData, res.attr || {})
              console.log('_this.apiData', _this.apiData)
            })
            // let mockData = require('./../../mock/行李转盘数据')
            // _this.apiData = Object.assign({}, _this.apiData, mockData || {})
          })
        ).then(() => {
          // 无数据 加载无数据模板
          console.log('mockdata', _this.apiData)
          this.checkDataEmpty(this.apiData)
          setTimeout(() => {
            // _this.$set(_this.loaded, 'conf', true)
            _this.loaded = true
          }, 200)
        }).then(() => {
          // 判断是否需要动态滚动文本
          try {
            if (this.extraParam && this.deviceInfo.dynamicScrollTextSourceId === '0') {
              let dynamicTextConf = this.extraParam.dynaScrollText
              console.log('dynamicTextConf', dynamicTextConf)
              let dynamicText = Tools.getMatchData(this.apiData[dynamicTextConf.dataSource], dynamicTextConf.singleData)
              console.log('dynamicText', dynamicText)
              this.deviceInfo.dynamicScrollText = dynamicText[dynamicTextConf.columnName]
              // console.log(dynaText[dynaTextConf.columnName])
            }
          } catch (err) { console.warn('请检查 extraParam 配置！') }
        })
      } else {
        // this.$set(this.loaded, 'conf', true)
        this.loaded = true
      }
    },
    // 生成请求数据参数
    buildQueryParam (mainPager) {
      let params = {
        airportCode: this.deviceInfo.airportCode,
        deviceId: this.deviceInfo.id,
        langlist: this.templateConf ? this.templateConf.langs : ''
      }
      console.log('params', params)
      if (this.extraParam && this.extraParam.destination) {
        params = Object.assign({}, params, { destination: this.extraParam.destination })
        console.log('params', params)
      }
      if (mainPager) {
        return Object.assign({}, {
          pageNumber: this.deviceInfo.groupSort || 1,
          pageSize: this.deviceInfo.pageSizeC
        }, params)
      } else {
        return params
      }
    },
    // 定时刷新分页
    setPageInterval (api, interval) {
      console.log('api', api)
      console.log('interval', interval)
      let _this = this
      console.log('_this.timers', _this.timers)
      console.log('SYSCONFIG.MAIN_PAGER', SYSCONFIG.MAIN_PAGER)
      if (_this.timers[SYSCONFIG.MAIN_PAGER]) {
        return
      }
      _this.pagerHelper = _this.buildQueryParam(true)
      let timer = setInterval(() => {
        console.log('_this.apiData', _this.apiData)
        console.log('_this.apiData[SYSCONFIG.MAIN_PAGER]', _this.apiData[SYSCONFIG.MAIN_PAGER])
        let pageCount = _this.apiData[SYSCONFIG.MAIN_PAGER].pageCount
        _this.pagerHelper.pageNumber += 1
        if (_this.pagerHelper.pageNumber > pageCount) {
          _this.pagerHelper.pageNumber = _this.deviceInfo.groupSort
        }
        getSouceData(Object.assign({id: api}, _this.pagerHelper)).then(res => {
          _this.apiData = Object.assign({}, _this.apiData, res.attr || {})
          console.log('_this.apiData', _this.apiData)
          // console.log('changPage', 'current page:' + _this.pagerHelper.pageNumber)
        })
      }, interval)
      _this.timers[SYSCONFIG.MAIN_PAGER] = timer
      console.log('_this.timers', _this.timers)
      console.log('SYSCONFIG.MAIN_PAGER', SYSCONFIG.MAIN_PAGER)
    },
    // 定时刷新数据
    setDataInterval (api, interval, mainPager) {
      let _this = this
      if (_this.timers[api]) {
        // 语言切换小于刷新时间，切换会触发render函数重复执行，所以不能clear，否则定时时间永远不能到达
        // clearInterval(_this.timers[api])
        return
      }
      let timer = setInterval(() => {
        let params = Object.assign({}, {id: api}, Object.assign({}, _this.buildQueryParam(mainPager), mainPager && _this.apiData[SYSCONFIG.MAIN_PAGER] ? {pageNumber: _this.apiData[SYSCONFIG.MAIN_PAGER].pageNumber} : {}))
        console.log('params', params)
        getSouceData(params).then(res => {
          _this.apiData = Object.assign({}, _this.apiData, res.attr || {})
          console.log('_this.apiData', _this.apiData)
          // console.log('refresh', 'apiId:' + api, ',interval:' + interval)
        })
      }, interval)
      this.timers[api] = timer
    },
    // 提取语言切换的参数
    buildLangSwitch () {
      let { langTimer, defaultLang, langs } = this.templateConf
      this.langSwitch.interval = langTimer || this.langSwitch.interval
      this.langSwitch.current = defaultLang || this.langSwitch.current
      this.langSwitch.langs = langs || this.langSwitch.langs
      console.log('this.langSwitch', this.langSwitch)

      // 定时切换
      var changeLang = () => {
        let langs = this.langSwitch.langs
        let current = this.langSwitch.current
        let idx = Tools.indexOf(langs, current)
        if (idx >= langs.length - 1 && idx > -1) {
          idx = 0
        } else {
          idx += 1
        }
        this.$set(this.langSwitch, 'current', langs[idx])
      }
      if (this.langSwitch.timer) {
        clearInterval(this.langSwitch.timer)
      }
      this.langSwitch.timer = setInterval(changeLang, this.langSwitch.interval)
    },
    transLang (target) {
      if (target instanceof Object) {
        return target[this.langSwitch.current] || target['en-ww'] || target['zh-cn'] || ''
      } else {
        return target || ''
      }
    },
    // 随便写的判断空数据方法，后面要改！
    checkDataEmpty (data) {
      let empty = false
      let apiIds = Tools.recurDataSource(this.templateConf)
      let apiSource = []
      for (let i = 0; i < apiIds.length; i++) {
        if (apiIds[i].key.startsWith('$') || !apiIds[i].mainSource) {
          continue
        }
        apiSource.push(apiIds[i].key)
      }
      for (let j = 0; j < apiSource.length; j++) {
        if (!data[apiSource[j]] || !data[apiSource[j]].list || data[apiSource[j]].list.length === 0) {
          empty = true
          break
        }
      }
      if (empty) {
        this.templateConf = EmptyDataConf
      }
    },
    checkPagerInfo (conf) {
      // 分页信息加入main_pager
      if (conf.dataSource && conf.mainPager) {
        console.log('conf.dataSource', conf.dataSource)
        console.log('conf.mainPager', conf.mainPager)
        console.log('this.apiData', this.apiData)
        console.log('this.apiData[conf.dataSource].pager', this.apiData[conf.dataSource].pager)
        console.log('SYSCONFIG.MAIN_PAGER', SYSCONFIG.MAIN_PAGER)
        this.apiData[SYSCONFIG.MAIN_PAGER] = this.apiData[conf.dataSource] ? this.apiData[conf.dataSource].pager : {};
        // 定时，分页切换
        (this.deviceInfo.groupSort === 0 || this.deviceInfo.groupSort >= this.deviceGroup.groupSize) &&
        conf.pageInterval && this.setPageInterval(conf.dataSource, conf.pageInterval)
      }
    },
    // 标签渲染选择器
    confRender (h, conf, ajaxData) {
      this.checkPagerInfo(conf)
      if (conf.dataSource) {
        ajaxData = Tools.getMatchData(this.apiData[conf.dataSource], conf.singleData, conf.dataSource.startsWith('$'))
        // 造空行
        if (ajaxData instanceof Array && this.extraParam.virtualRowSize && ajaxData.length < Number(this.extraParam.virtualRowSize)) {
          for (let i = 0; i < this.extraParam.virtualRowSize - ajaxData.length; i++) {
            ajaxData.push({})
          }
        }

        // 定时, 有分页切换的页面不再加载数据

        ((conf.mainPager && this.deviceInfo.groupSort > 0 && this.deviceInfo.groupSort < this.deviceGroup.groupSize) || (!conf.mainPager)) &&
        conf.refreshInterval && this.setDataInterval(conf.dataSource, conf.refreshInterval, conf.mainPager)
      }
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
        // 以下为组合模板
        case 'DefaultMarqueeText':
          rs = this.defaultMarqueeTextRender(h, conf, ajaxData)
          break
        case 'FixedLeftMarquee':
          rs = this.fixedLeftMarqueeRender(h, conf, ajaxData)
          break
        default:
          rs = this.textRender(h, conf, ajaxData)
      }
      return rs
    },
    // 最外层渲染
    templateRender (h, conf, ajaxData) {
      return h(
        'div',
        { style: Tools.completeStyleSuffix(conf.bodyStyle), class: 'raiis-template-layout' },
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
            type: 'flex',
            align: conf.align,
            justify: conf.justify
          },
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
          attrs: { span: conf.span },
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
            label: typeof conf.label === 'number' ? String(conf.label) : (this.transLang(conf.label) || ''),
            width: style.width,
            minWidth: style.minWidth,
            align: conf.style.textAlign,
            columnStyle: style,
            headerStyle: Object.assign({}, defaultStyle ? defaultStyle.defaultHeaderStyle : {}, Tools.completeStyleSuffix(conf.headerStyle))
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
          conf.multiSource ? (
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
          ) : (
            this._l(ajaxData, data => {
              // console.log(data)
              return h(
                'flop-item',
                [
                  this.confRender(h, conf.item, data)
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
      let text = this.transLang(realText)
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
                return this.transLang(item)
              }).join('') : ''
            ]
          )
        ]
      )
    },
    // 固定左部滚动文本
    fixedLeftMarqueeRender (h, conf, ajaxData = {}) {
      // 固定部分配置
      let fixedPart = conf.fixedColumnNames.map(item => {
        return {
          type: 'Text',
          tag: 'span',
          style: Tools.completeStyleSuffix(conf.style),
          columnName: item
        }
      })
      // 连接符配置
      let joiner = {
        type: 'Text',
        tag: 'span',
        style: Tools.completeStyleSuffix(conf.style)
      }
      if (conf.joinerHideBy) {
        joiner.transExist = conf.joiner
        joiner.columnName = conf.joinerHideBy
      } else {
        joiner.value = conf.joiner
      }
      // 滚动文本配置
      let scrollPart = {
        type: 'MarqueeText',
        speed: conf.speed,
        style: {
          display: 'inline-block',
          width: 280
        },
        content: [
          {
            type: 'Text',
            style: Tools.completeStyleSuffix(conf.style),
            columnName: conf.scrollColumnName
          }
        ]
      }
      let genConf = [
        {
          type: 'MarqueeText',
          speed: conf.speed,
          style: {
            display: 'inline-block'
          },
          content: fixedPart.concat(joiner)
        },
        scrollPart
      ]
      return genConf.map(item => {
        return this.confRender(h, item, ajaxData)
      })
    },
    // 默认滚动文本，即marquee模板里面只有一个text子模板
    defaultMarqueeTextRender (h, conf, ajaxData = {}) {
      // marquee配置
      let genConf = {
        type: 'MarqueeText',
        speed: conf.speed,
        seamless: conf.seamless,
        always: conf.always,
        vertical: conf.vertical,
        backZero: conf.backZero,
        style: conf.style,
        content: []
      }
      if (conf.hiddenBy) genConf.hiddenBy = conf.hiddenBy
      // 文本配置
      let textConf = {
        type: 'Text',
        tag: conf.tag
      }
      if (conf.textStyle) textConf.style = conf.textStyle
      if (conf.columnName) textConf.columnName = conf.columnName
      if (conf.emptyReplace) textConf.emptyReplace = conf.emptyReplace
      if (conf.transExist) textConf.transExist = conf.transExist
      if (conf.value) textConf.value = conf.value
      if (conf.property) textConf.property = conf.property
      if (conf.date) textConf.date = conf.date
      if (conf.prefix) textConf.prefix = conf.prefix
      if (conf.suffix) textConf.suffix = conf.suffix
      if (conf.langLock) textConf.langLock = conf.langLock
      if (conf.langExcept) textConf.langExcept = conf.langExcept
      if (conf.transColor) textConf.transColor = conf.transColor
      if (conf.twinkle) textConf.twinkle = conf.twinkle
      if (conf.special) textConf.special = conf.special
      if (conf.replace) textConf.replace = conf.replace
      genConf.content.push(textConf)
      return this.confRender(h, genConf, ajaxData)
    }
  },
  created () {
    this.completeDeviceInfo()
  },
  destroyed () {
    for (let key in this.timers) {
      clearInterval(this.timers[key])
    }
  },
  render (h) {
    // console.log('--------------------  render ---------------------')
    if (!this.loaded || !this.templateConf) { return }
    return this.confRender(h, this.templateConf)
  }
}
</script>
<style>
body {
  /* background-color: #1E2D5A; */
  height: 100%;
  width: 100%;
  overflow: hidden;
}
.raiis-template-layout .el-table {
  border: 0 !important;
  background-color: transparent !important;
}

.raiis-template-layout .el-table table {
  border-collapse: collapse;
}
.raiis-template-layout .el-table__body-wrapper {
  overflow: hidden !important;
}
.raiis-template-layout .el-table::after, .el-table::before {
  height: 0 !important;
  content: none !important;
}
/* 标题默认样式 */
.raiis-template-layout .el-table th {
  font-weight: normal;
}

/* 标题文本区域默认样式*/
.raiis-template-layout .el-table th.is-leaf {
  background-color: #1E2D5A !important;
  line-height: 100px;
}

.raiis-template-layout .el-table tr {
  transition: all .2s;
  -moz-transition: all .2s;
  -webkit-transition: all .2s;
  -o-transition: all .2s;
  color: #FFFFFF;
}

/* td基本样式 */
.raiis-template-layout .el-table td {
  border-bottom: 0px !important;
  padding: 0px 5px 0px 5px;
}

/* td 文本框基本样式 不换行 */
.raiis-template-layout .el-table td .cell {
  /* padding-left: 0px !important;
  padding-right: 0px !important; */
  /* margin-left: 5px !important;
  margin-right: 5px !important; */
  white-space: nowrap !important;
}


.raiis-template-layout .radius-class {
  background: #fff;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>
