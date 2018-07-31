<template>
  <div :style="{
    position: 'relative',
    overflow: 'hidden',
    transform: `translate(${ translateX }px, ${ translateY }px)`
  }">
    <template v-if="showType === 'flexImage'">
      <div v-if="imgs.length < 7">
        <div v-for="i in 2" :key="i" >
          <el-row v-if="i == 1 || (i == 2 && imgs.length > 2)" type="flex" align="middle" justify="center" :style="{
            overflow: 'hidden',
            marginTop: `${spacing}px`,
            marginBottom: `${spacing}px`
          }">
            <div
              v-for="item in (i == 1 ? imgs.slice(0, layerPoint) : imgs.slice(layerPoint))"
              :key="item"
              :style="{
              position: 'relative',
              overflow: 'hidden',
              margin: '0 8px'
            }">
              <img
                :src="item"
                :style="Object.assign(
                  {},
                  imgStyle,
                  imgs.length === 1 && autoSize ? { height: `${ $parent.$el.offsetHeight }px`, width: null } : {},
                  imgs.length === 2 && autoSize ? { height: null, width: `${ imgDivWidth }px` } : {}
                )">
              </img>
            </div>
          </el-row>
        </div>
      </div>
      <div v-else>
        <flex-image :delay="0" :height="imgStyle.height" :imgs="imgs" :style="{marginBottom: `${spacing}px`, marginTop: `${spacing}px`}"></flex-image>
        <flex-image :start="3" :height="imgStyle.height" :imgs="imgs" :style="{marginBottom: `${spacing}px`}"></flex-image>
      </div>
    </template>
    <template v-else>
      <el-row v-if="imgs.length < 3" type="flex" align="middle" justify="center" :style="{
        overflow: 'hidden',
        marginTop: `${spacing}px`,
        marginBottom: `${spacing}px`
      }">
        <div
          v-for="item in imgs"
          :key="item"
          :style="{
          position: 'relative',
          overflow: 'hidden',
          margin: '0 8px'
        }">
          <img
            :src="item"
            :style="Object.assign(
              {},
              imgStyle,
              imgs.length === 1 && autoSize ? { height: `${ $parent.$el.offsetHeight - this.spacing * 2 }px`, width: null } : {},
              imgs.length === 2 && autoSize ? { height: null, width: `${ imgDivWidth }px` } : {}
            )">
          </img>
        </div>
      </el-row>
      <div v-else class="flex-image-carousel">
        <carousel :interval="interval" :autoplay="true" type="card" arrow="never" :height="imgStyle.height" :style="{
          textAlign: 'center',
          marginTop: `${spacing}px`,
          marginBottom: `${spacing}px`
        }">
          <carousel-item v-for="item in imgs" :key="item">
            <img :src="item" :style="imgStyle"></img>
          </carousel-item>
        </carousel>
      </div>

    </template>
  </div>
</template>
<script>
import FlexImage from './FlexImage/FlexImage'
import Carousel from './carousel/Carousel'
import CarouselItem from './carousel/Carousel-item'
import Tools from './../common/js/template-tools'
export default {
  props: {
    showType: {
      type: String,
      default: 'flexImage'
    },
    data: {},
    interval: {
      type: Number,
      default: 5000
    },
    imgStyle: {},
    spacing: {
      type: Number,
      default: 20
    },
    autoSize: {
      type: Boolean,
      default: false
    },
    translateX: {
      type: Number,
      default: 0
    },
    translateY: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      imgs: [],
      imgDivWidth: 0,

      padding: 30,
      clientHeight: 0,
      imgHeight: 0,
      layerPoint: 0,

      Tools
    }
  },
  components: {
    FlexImage,
    Carousel,
    CarouselItem
  },
  methods: {
    initData () {
      this.imgs = this.data
    },
    calcImgDivWidthAndHeight () {
      let length = this.imgs.length < 3 ? this.imgs.length : (this.imgs.length <= 4 ? 2 : 3)
      this.imgDivWidth = this.$el.offsetWidth / length - length * 8 * 2
    },
    calcLayerPoint () {
      this.layerPoint = this.imgs.length < 3 ? this.imgs.length : Math.floor((this.imgs.length + 1) / 2)
    }
  },
  created () {
    this.initData()
  },
  mounted () {
    this.calcImgDivWidthAndHeight()
    this.calcLayerPoint()
  }
}
</script>
<style>
  .flex-image-carousel .el-carousel__item img {
    border: 1px solid;
    border-color: #1E2D5A;
    margin: 0;
  }
  .flex-image-carousel .el-carousel__item:nth-child(2n) {
    background-color: transparent;
  }

  .flex-image-carousel .el-carousel__item:nth-child(2n+1) {
    background-color: transparent;
  }
  .flex-image-carousel .el-carousel__mask {
    background-color: transparent !important;
  }
  .flex-image-carousel .el-carousel__button {
    opacity: 1 !important;
    background-color: #CCCCCC !important;
  }
  .flex-image-carousel .el-carousel__indicators .is-active .el-carousel__button {
    background-color: #ff0000 !important;
  }
</style>
