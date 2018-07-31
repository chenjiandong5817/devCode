<template>
  <div style="padding: 50px;">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="监听队列">
        <el-select v-model="value" placeholder="请选择" @change="handleSelectorChange">
          <el-option
            v-for="item in options"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="消息内容">
        <el-input type="textarea" v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item label-width="80px">
        <el-button type="primary" @click="handleSend">立即发送</el-button>
        <el-button @click="handleReset">取消</el-button>
      </el-form-item>
    </el-form>
    <fieldset>
      <legend>Receive</legend>
      <el-tag v-for="data in receiveData" :key="data" style="margin: 5px 10px;"> {{data}} </el-tag>
    </fieldset>
  </div>
</template>
<script>
import Stomp from 'stompjs'
export default {
  data () {
    return {
      options: ['hello', 'world'],
      value: null,
      form: {
        content: ''
      },
      receiveData: [],
      q: 'q.tmp.cxh',
      client: null
    }
  },
  methods: {
    handleSend () {
      // let q = this.q
      if (this.value) {
        let content = this.form.content
        this.client.send('/exchange/ex.hello/routingHello', {'content-type': 'application/json'}, content)
      } else {
        this.$notify({
          title: '错误',
          message: '请先先择queue',
          type: 'error'
        })
      }
    },
    handleReset () {
      this.$refs.form.resetFields()
    },
    handleSelectorChange () {
      console.log(123)
      // 订阅queue
      this.client.subscribe(`/queue/${this.value}`, (msg) => {
        this.receiveData.push(msg.body)
        msg.ack()
      }, {ack: 'client', id: this.value})
    },
    connect () {
      this.client = Stomp.over(new WebSocket('ws://10.1.16.117:9090/ws'))
      // heartbeat
      this.client.heartbeat.incoming = 300000
      this.client.heartbeat.outgoing = 300000
      // debug
      this.client.debug = e => {
        console.log(e)
      }
      // connected
      let onConnect = () => {
        console.log('connected')
      }
      // error
      let onError = () => {
        console.log('error')
      }
      this.client.connect('raiis.chenyang', 'raiis.chenyang', onConnect, onError, 'raiis.test')
    }
  },
  mounted () {
    this.connect()
    // console.log(Stomp)
  },
  destroyed () {
  }
}
</script>
