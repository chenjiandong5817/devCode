<template>
  <el-dialog :visible.sync="visible" :append-to-body="true" width="30%" top="25vh" :close-on-click-modal="false" title="消息文本"
    custom-class="control-message-text-container">
    <el-input type="textarea" :rows="2" placeholder="请输入消息文本" v-model="target.content"></el-input>
    <br />
    <el-radio-group v-model="target.ack">
      <el-radio :label="1">需要确认</el-radio>
      <el-radio :label="0">不需要确认</el-radio>
    </el-radio-group>
    <p class="help-text" v-html="renderHelpText"></p>
    <span slot="footer" class="dialog-footer">
       <el-button  @click="onclose" size="small" >关闭</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { MESSAGE_TEMPLATE_HELP } from '@/const/help'
export default {
  data () {
    return {
      target: {},
      key: null,
      visible: false
    }
  },
  computed: {
    renderHelpText () {
      let html = ''
      MESSAGE_TEMPLATE_HELP.forEach(line => {
        html += `<span>${line}</span><br />`
      })
      return html
    }
  },
  methods: {
    show (option) {
      this.target = option
      this.$nextTick(() => {
        this.visible = true
      })
    },
    onclose () {
      this.visible = false
    }
  }
}
</script>
<style lang="scss">
.control-message-text-container {
  min-width: 200px;
  .help-text {
    font-size: 12px;
    color: darkgray;
  }
}
</style>
