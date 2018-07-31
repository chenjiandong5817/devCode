<template>
  <div :style="bodyStyle">
    <el-row v-if="imgs.length < 3" type="flex" align="top" justify="center" :style="{
      height:  `${ imgHeight }px`,
      overflow: 'hidden',
      marginTop: '80px',
      marginBottom: '80px'
    }">
      <div
        v-for="item in imgs"
        :key="item"
        :style="{
        position: 'relative',
        overflow: 'hidden'
      }">
        <img
          :src="item"
          :style="Object.assign(
            {},
            { padding: '0px 30px 0px 30px' },
            imgStyle,
            imgs.length === 1 && imgConf.oneTransLarge ? { height: `${ imgHeight }px` } : {}
          )">
        </img>
      </div>
    </el-row>
    <el-carousel v-else :interval="interval" :autoplay="false" type="card" arrow="never" :height="`${ imgHeight }px`" :style="{
      textAlign: 'center',
      paddingTop: '80px',
      paddingBottom: '50px'
    }">
      <el-carousel-item v-for="item in imgs" :key="item">
        <img :src="item" :style="imgStyle"></img>
      </el-carousel-item>
    </el-carousel>

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
      interval: 5000,
      imgScale: 0.7,
      textScale: 0.3,
      imgStyle: {},
      textStyle: {},

      padding: 30,
      clientHeight: 0,
      imgHeight: 0
    }
  },
  components: {
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
    calcHeight () {
      this.clientHeight = document.documentElement.clientHeight
      this.imgHeight = this.$el.offsetHeight * this.imgScale
    }
  },
  created () {
    this.initData()
  },
  mounted () {
    this.calcHeight()
  }

}
</script>
<style>
  .el-carousel__item img {
    border: 1px solid;
    border-color: #1E2D5A;
    margin: 0;
  }
  .el-carousel__item:nth-child(2n) {
    background-color: transparent;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: transparent;
  }
  .el-carousel__mask {
    background-color: transparent !important;
  }
  .el-carousel__button {
    opacity: 1 !important;
    background-color: #ff0000 !important;
  }
  .el-carousel__indicators .is-active .el-carousel__button {
    background-color: #00FF00 !important;
  }
</style>
