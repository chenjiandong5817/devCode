<template>
  <ul
    ref="contextmenu"
    :class="contextmenuCls"
    v-show="visible"
    :style="style">
    <slot></slot>
  </ul>
</template>

<script>
export default {
  name: 'VContextmenu',
  provide () {
    return {
      $$contextmenu: this
    }
  },
  props: {
    eventType: {
      type: String,
      default: 'contextmenu'
    },
    theme: {
      type: String,
      default: 'default'
    },
    autoPlacement: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      visible: false,
      style: {
        top: 0,
        left: 0
      }
    }
  },
  computed: {
    clickOutsideHandler () {
      return this.visible ? this.hide : () => {}
    },
    isClick () {
      return this.eventType === 'click'
    },
    contextmenuCls () {
      return [
        'v-contextmenu',
        `v-contextmenu--${this.theme}`
      ]
    }
  },
  watch: {
    visible (value) {
      if (value) {
        document.body.addEventListener('click', this.handleBodyClick)
      } else {
        document.body.removeEventListener('click', this.handleBodyClick)
      }
    }
  },
  mounted () {
    document.body.appendChild(this.$el)
    if (window.$$VContextmenu) {
      window.$$VContextmenu[this.$contextmenuId] = this
    } else {
      window.$$VContextmenu = { [this.$contextmenuId]: this }
    }
    this.$refs.reference && this.$refs.reference.forEach((ref) => {
      ref.addEventListener(this.eventType, this.handleReferenceContextmenu)
    })
  },
  beforeDestroy () {
    document.body.removeChild(this.$el)
    delete window.$$VContextmenu[this.$contextmenuId]
    this.$refs.reference && this.$refs.reference.forEach((ref) => {
      ref.removeEventListener(this.eventType, this.handleReferenceContextmenu)
    })
    document.body.removeEventListener('click', this.handleBodyClick)
  },
  methods: {
    handleReferenceContextmenu (event) {
      event.preventDefault()
      const eventX = event.pageX
      const eventY = event.pageY
      this.show()
      this.$nextTick(() => {
        const contextmenuPosition = {
          top: eventY,
          left: eventX
        }
        if (this.autoPlacement) {
          const contextmenuWidth = this.$refs.contextmenu.clientWidth
          const contextmenuHeight = this.$refs.contextmenu.clientHeight
          if (contextmenuHeight + eventY >= window.innerHeight) {
            contextmenuPosition.top -= contextmenuHeight
          }
          if (contextmenuWidth + eventX >= window.innerWidth) {
            contextmenuPosition.left -= contextmenuWidth
          }
        }
        this.style = {
          top: `${contextmenuPosition.top}px`,
          left: `${contextmenuPosition.left}px`
        }
        // 20180407 cdroid: add callback
        this.$emit('after', event.target)
      })
    },
    handleBodyClick (event) {
      const notOutside = this.$el.contains(event.target) || (
        this.isClick && this.$refs.reference.some(ref => ref.contains(event.target))
      )
      if (!notOutside) {
        this.visible = false
      }
    },
    show (position) {
      Object.keys(window.$$VContextmenu)
        .forEach((key) => {
          if (key !== this.$contextmenuId) {
            window.$$VContextmenu[key].hide()
          }
        })
      if (position) {
        this.style = {
          top: `${position.top}px`,
          left: `${position.left}px`
        }
      }
      this.visible = true
      this.$emit('show', this)
    },
    hide () {
      this.visible = false
      this.$emit('hide', this)
    },
    hideAll () {
      Object.keys(window.$$VContextmenu)
        .forEach((key) => {
          window.$$VContextmenu[key].hide()
        })
    }
  }
}
</script>
