<template>
  <div :style="Object.assign({
    position: 'relative'
  }, bodyStyle)">
    <div v-if="imgs.length < 7">
      <div v-for="i in 2" :key="i" >
        <el-row v-if="i == 1 || (i == 2 && imgs.length > 2)" type="flex" align="middle" justify="center" :style="{
          height:  `${ imgs.length <= 2 ? imgAreaHeight : (getNumber(imgStyle.height) || imgAreaHeight) }px`,
          overflow: 'hidden',
          marginTop: '30px',
          marginBottom: '30px'
        }">
          <div
            v-for="item in (i == 1 ? imgs.slice(0, layerPoint) : imgs.slice(layerPoint))"
            :key="item"
            :style="{
            position: 'relative',
            overflow: 'hidden',
            margin: '8px'
          }">
            <img
              :src="item"
              :style="Object.assign(
                {},
                imgStyle,
                imgs.length === 1 && imgConf.oneTransLarge ? { height: `${ imgAreaHeight }px`, width: null } : {},
                imgs.length === 2 && imgConf.oneTransLarge ? { height: null, width: `${ imgDivWidth }px` } : {}
              )">
            </img>
          </div>
        </el-row>
      </div>
    </div>
    <div v-else>
      <flex-image :delay="200" :height="getNumber(imgStyle.height)" :imgs="imgs"></flex-image>
      <flex-image :start="3" :height="getNumber(imgStyle.height)" :imgs="imgs"></flex-image>
    </div>

    <el-row type="flex" align="middle" justify="center" :style="{
      flexWrap: 'wrap',
      justifyContent: 'center'
    }">
      <double-layer-text
        :align="textConf.align"
        :translateX="textConf.translateX"
        :translateY="textConf.translateY"
        :topStyle="textConf.topStyle || textConf.style"
        :bottomStyle="textConf.bottomStyle || textConf.style"
        >
          <div slot="topSlot">
            {{ data[textConf.topColumnName || textConf.columnName] }}
          </div>
          <div slot="bottomSlot" v-if="textConf.bottomColumnName && textConf.bottomColumnName !== ''">
            {{ data[textConf.bottomColumnName] }}
          </div>
      </double-layer-text>
    </el-row>
  </div>
</template>
<script>
import FlexImage from './FlexImage'
import DoubleLayerText from './../DoubleLayerText'
export default {
  props: {
    data: {},
    imgConf: {},
    textConf: {},
    bodyStyle: {}
  },
  data () {
    return {
      imgs: [],
      imgAreaHeight: 0,
      imgDivWidth: 0,
      interval: 5000,
      imgScale: 0.7,
      textScale: 0.3,
      imgStyle: {},
      textStyle: {},

      padding: 30,
      clientHeight: 0,
      imgHeight: 0,
      layerPoint: 0
    }
  },
  components: {
    FlexImage: FlexImage,
    DoubleLayerText: DoubleLayerText
  },
  methods: {
    initData () {
      this.imgs = this.data[this.imgConf.columnName] || this.imgs
      this.interval = this.imgConf.interval || this.interval
      this.imgScale = this.imgConf.scale || this.imgScale
      this.textScale = this.textConf.scale || this.textScale
      this.imgStyle = this.imgConf.style || this.imgStyle
      this.textStyle = this.textConf.style || this.textStyle
    },
    calcImgAreaHeight () {
      this.imgAreaHeight = this.$el.offsetHeight * this.imgScale * (this.imgs.length > 2 ? 0.5 : 1)
    },
    calcImgDivWidthAndHeight () {
      let length = this.imgs.length < 3 ? this.imgs.length : (this.imgs.length <= 4 ? 2 : 3)
      this.imgDivWidth = this.$el.offsetWidth / length - length * 8 * 2
    },
    calcLayerPoint () {
      this.layerPoint = this.imgs.length < 3 ? this.imgs.length : Math.floor((this.imgs.length + 1) / 2)
    },
    getNumber (str) {
      if (!str) {
        return 0
      }
      return Number(str.match(/\d+/g))
    }
  },
  created () {
    this.initData()
  },
  mounted () {
    this.calcImgAreaHeight()
    this.calcImgDivWidthAndHeight()
    this.calcLayerPoint()
  }
}
</script>
