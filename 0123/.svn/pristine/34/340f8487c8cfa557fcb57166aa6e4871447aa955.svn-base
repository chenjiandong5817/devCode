<script>
export default {
  props: {
    twinkled: {
      type: Boolean,
      default: false
    },
    inline: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      timer: null,
      blinking: false
    }
  },
  computed: {
    doTwinkle () {
      if (!this.twinkled) {
        return false
      }
      return this.blinking
    }
  },
  methods: {
    setTwinkle () {
      this.timer && clearInterval(this.timer)
      if (this.twinkled) {
        this.timer = setInterval(this.toogle, 600)
      }
    },
    toogle () {
      this.blinking = !this.blinking
    }
  },
  updated () {
    this.setTwinkle()
  },
  render (h) {
    return h(
      'div',
      {
        style: {
          visibility: this.doTwinkle ? 'hidden' : 'visible',
          display: this.inline ? 'inline-block' : 'block'
        }
      },
      this.$slots.default
    )
  }
}
</script>
