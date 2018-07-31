<template>
  <iframe :src='src' class="my-iframe" ref="iframe"></iframe>
</template>

<script>
export default {
  name: 'myiframe',
  data () {
    return {
      urlPath: this.getUrlPath() // iframe src 路径
    }
  },
  created () {
  },
  mounted () {
    this.load()
    // this.resize()
    console.log('iframe')
  },
  props: ['src'],
  methods: {
    // 显示等待框
    show () {
      this.$progress.start()
    },
    // 隐藏等待框
    hide () {
      this.$progress.done()
    },
    // 加载浏览器窗口变化自适应
    resize () {
      window.onresize = () => {
        this.iframeInit()
      }
    },
    // 加载组件
    load () {
      this.show()
      this.$route.query.src = this.$route.query.src
        ? this.$route.query.src.replace('$', '#')
        : ''
      // 超时3s自动隐藏等待框，加强用户体验
      let time = 3
      const timeFunc = setInterval(() => {
        time--
        if (time === 0) {
          this.hide()
          clearInterval(timeFunc)
        }
      }, 1000)
      this.iframeInit()
    },
    // iframe窗口初始化
    iframeInit () {
      const iframe = this.$refs.iframe
      // const clientHeight = document.documentElement.clientHeight - 110
      // iframe.style.height = `${clientHeight}px`
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {
          this.hide()
        })
      } else {
        iframe.onload = () => {
          this.hide()
        }
      }
    },
    getUrlPath () {
      // 获取 iframe src 路径
      let url = this.src || window.location.href
      // url = url.replace('/iframe', '')
      return url
    }
  }
}
</script>
