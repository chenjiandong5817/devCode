<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <el-col :span="11">
        <span class="editor-label">字体大小(fontSize)</span>
        <el-input type="number" :value="conf.style.fontSize" @input="val => Tools.inputStr2Number(conf.style, 'fontSize', val)"></el-input>
      </el-col>
    </el-form-item>
  </el-form>
</template>
<script>
  import Tools from '@/common/js/template-tools.js'
  export default {
    props: {
      value: Object
    },
    data () {
      return {
        conf: null,
        Tools
      }
    },
    watch: {
      conf: {
        handler (val) {
          if (!this.conf.style.fontSize) {
            delete this.conf.style.fontSize
          }
          this.$emit('input', val)
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'Clock',
          typeLabel: '时钟',
          style: {}
        }
      } else {
        this.conf = this.value
      }
    }
  }
</script>
