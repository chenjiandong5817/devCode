<template>
  <div class="raiis-clock" :style="{
    alignItems: alignItems,
    justifyContent: justifyContent
  }">
    <template v-if="formatHourAndMinute">
      <div>{{time.substr(0, 2)}}</div>
      <div ref="clockColon">{{(colon ? ':' : ' ')}}</div>
      <div>{{time.substr(3, 2)}}</div>

    </template>
    <template v-else>
      {{time}}
    </template>
  </div>
</template>
<script>
import Moment from 'moment'
export default {
  props: {
    format: {
      type: String,
      default: 'HH:mm'
    },
    alignItems: {
      type: String,
      default: 'center'
    },
    justifyContent: {
      type: String,
      default: 'center'
    }
  },
  data () {
    return {
      time: '',
      colon: true
    }
  },
  computed: {
    formatHourAndMinute () {
      return this.format === 'HH:mm'
    }
  },
  methods: {
    updateTime () {
      this.time = Moment().format(this.format)
    }
  },
  mounted () {
    setInterval(this.updateTime, 30000)
    if (this.formatHourAndMinute) {
      if (this.$refs.clockColon.clientWidth) {
        this.$refs.clockColon.style.width = `${this.$refs.clockColon.clientWidth}px`
      }
      setInterval(() => {
        this.colon = !this.colon
      }, 500)
    }
    this.updateTime()
  }
}
</script>
<style>
.raiis-clock {
  display: flex;
}
.raiis-clock div {
  text-align: center;
}
</style>
