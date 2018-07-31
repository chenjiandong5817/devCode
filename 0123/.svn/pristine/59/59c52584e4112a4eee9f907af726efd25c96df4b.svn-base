<script>
// import debounce from 'throttle-debounce/debounce'
import Velocity from 'velocity-animate'
export default {
  name: 'MarqueeText',
  props: {
    // 每秒移动的距离px
    speed: {
      type: Number,
      default: 50
    },
    always: {
      type: Boolean,
      default: false
    },
    // 是否无缝滚动
    seamless: {
      type: Boolean,
      default: false
    },
    spacing: {
      type: Number,
      default: 50
    },
    vertical: {
      type: Boolean,
      default: false
    },
    backZero: {
      type: Boolean,
      default: false
    },
    hidden: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      translate: 0,
      // content 内的内容临时存储
      tempContent: null,
      moveTime: 0,
      timer: null,
      parent: null,
      content: null,
      copyContent: null,
      isMove: false,
      defaultTranslate: 9999
    }
  },
  computed: {
    targetDirection () {
      return this.vertical ? 'offsetHeight' : 'offsetWidth'
    }
  },
  methods: {
    initContent: function () {
      this.emptyCopyContent()
      // 隐藏直接stop
      if (this.hidden) {
        this.stop()
        return
      }
      if (this.always) {
        // console.log('always')
        this.seamMove()
      } else if (this.parent[this.targetDirection] < this.content[this.targetDirection]) { // 滚动
        if (this.seamless) {
          this.seamlessMove()
        } else {
          this.seamMove()
        }
      } else {
        this.translate = 0
        this.stop()
        this.$nextTick(() => {
          this.defaultTranslate = 0
          this.onComplete()
          this.finish()
        })
      }
    },
    seamlessMove: function () {
      let div = this.content.childNodes
      if (div) {
        for (let i = 0; i < div.length; i++) {
          if (div[i]) {
            this.copyContent.appendChild(div[i].cloneNode(true))
          }
        }
      }
      let tempContent = this.tempContent
      this.tempContent = this.content.innerHTML
      this.translate = this.content[this.targetDirection]
      if (tempContent === null || tempContent !== this.tempContent) {
        this.stop()
        this.$nextTick(() => {
          this.finish()
          this.onComplete()
        })
      }
      if (!this.isMove) {
        this.onComplete()
        this.move()
      }
    },
    seamMove: function () {
      // 有缝滚动
      let tempContent = this.tempContent ? this.tempContent : null
      this.tempContent = this.content.innerHTML
      if (tempContent === null || tempContent !== this.tempContent) {
        this.stop()
        this.$nextTick(() => {
          this.finish()
        })
      }
      if (!this.isMove) {
        this.alwaysComplete()
        this.alwaysMove(this.content[this.targetDirection], this.parent[this.targetDirection])
      }
    },
    move: function () {
      this.isMove = true
      Velocity(this.content, {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: -1 * this.content[this.targetDirection]}, { duration: this.calcMoveTime(this.content[this.targetDirection]) * 1000, easing: 'linear' })
      Velocity(
        this.copyContent,
        {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: -1 * this.copyContent[this.targetDirection]},
        {
          duration: this.calcMoveTime(this.copyContent[this.targetDirection]) * 1000,
          easing: 'linear',
          complete: () => {
            if (!this.content) { return }
            this.finish()
            this.onComplete()
            this.$nextTick(() => {
              this.move()
            })
          }
        }
      )
    },
    alwaysMove (contentWidth, parentWidth) {
      this.isMove = true
      Velocity(
        this.content,
        {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: -1 * contentWidth},
        {
          duration: this.calcMoveTime(contentWidth + parentWidth) * 1000,
          easing: 'linear',
          complete: () => {
            if (!this.content) { return }
            this.finish()
            this.alwaysComplete()
            this.$nextTick(() => {
              this.alwaysMove(contentWidth, parentWidth)
            })
          }
        }
      )
    },
    alwaysComplete () {
      Velocity(this.content, {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: this.backZero ? 0 : this.parent[this.targetDirection]}, {duration: 0, easing: [0]})
    },
    onComplete () {
      Velocity(this.content, {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: 0}, {duration: 0, easing: [0]})
      Velocity(this.copyContent, {translateZ: 0, [`translate${this.vertical ? 'Y' : 'X'}`]: 0}, {duration: 0, easing: [0]})
    },
    finish () {
      Velocity(this.content, 'finish')
      Velocity(this.copyContent, 'finish')
    },
    stop () {
      this.isMove = false
      Velocity(this.content, 'stop', true)
      Velocity(this.copyContent, 'stop', true)
    },
    calcMoveTime (len) {
      // let len = this.$refs['marquee-text-content'].clientWidth
      // 假设speed = 10
      return len * 1.00 / this.speed
    },
    emptyCopyContent () {
      if (this.copyContent) {
        this.copyContent.innerHTML = ''
      }
    }
  },
  created () {
  },
  beforeDestroy () {
    // console.log('before destory')
    this.stop()
  },
  render (h) {
    return h(
      'div',
      { class: ['marquee-text', {'marquee-text-hide': this.hidden}] },
      [
        h(
          'div',
          {
            class: 'marquee-text-container',
            style: {
              whiteSpace: this.vertical ? 'wrap' : 'nowrap'
            }
          },
          [
            h(
              'div',
              {
                class: 'marquee-text-content',
                style: {
                  transform: `translate${this.vertical ? 'Y' : 'X'}(${this.defaultTranslate}px)`
                },
                ref: 'marquee-text-content'
              },
              this.$slots.default
            ),
            h(
              'div',
              { class: 'marquee-text-content--copy',
                ref: 'marquee-text-copy',
                style: {
                  [`padding${this.vertical ? 'Top' : 'Left'}`]: `${this.translate === 0 ? 0 : this.spacing}px`,
                  transform: `translate${this.vertical ? 'Y' : 'X'}(${this.defaultTranslate}px)`
                }
              }
            )
          ]
        )
      ]
    )
  },
  mounted () {
    this.content = this.$refs['marquee-text-content']
    this.copyContent = this.$refs['marquee-text-copy']
    this.parent = this.$el
  },
  updated () {
    this.initContent()
  }
}
</script>
<style scoped>
.marquee-text {
  /* font-weight:normal; */
  overflow: hidden;
}
.marquee-text-hide {
  display: none;
}
.marquee-text-container {
  position: relative;
  /* white-space: nowrap; */
}
[class^="marquee-text-content"] {
  display: inline-block;
  /* transform: translateZ(0); */
}
</style>
