<template>
  <carousel :height="height" :interval="interval" arrow="never" indicator-position="none" :mouseEvent="false">
    <carousel-item translateDirection="X" v-for="item in data" :key="item" :style="{
                  textAlign: align
                }">
      <div :style="{
                     display: 'flex',
                     justifyContent: 'center',
                     alignItems: 'center',
                     height: '100%'
                     }">
        <img :src="item" :class="{'radius-class': radius}" :style="Object.assign({
                     height: '100%',
                     lineHeight: '100%'
                     }, imgStyle)"></img>
      </div>
    </carousel-item>
  </carousel>
</template>
<script>
import Carousel from './carousel/Carousel'
import CarouselItem from './carousel/Carousel-item'
export default {
  components: {
    Carousel, CarouselItem
  },
  props: {
    height: {
      type: String,
      required: true
    },
    align: {
      type: String,
      default: 'center'
    },
    interval: {
      type: Number,
      default: 7000
    },
    data: {
      type: Array,
      default: () => []
    },
    radius: {
      type: Boolean,
      default: true
    },
    imgStyle: {}
  }
}
</script>

