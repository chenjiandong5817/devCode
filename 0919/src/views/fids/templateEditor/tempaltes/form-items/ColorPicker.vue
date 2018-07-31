<template>
  <el-form-item>
    <span class="editor-label" v-show="showLabel">{{ label }}</span>
    <div>
      <el-col :span="inputScale">
        <el-input v-model="conf" :placeholder="placeholder"></el-input>
      </el-col>
      <el-col :span="pickerScale" style="margin-left: 4px; max-width: 59px;">
        <el-color-picker v-model="conf"></el-color-picker>
      </el-col>
    </div>
  </el-form-item>
</template>
<script>
  export default {
    props: {
      placeholder: String,
      showLabel: {
        type: Boolean,
        default: true
      },
      label: {
        type: String,
        default: ''
      },
      inputScale: {
        type: Number,
        validator (val) {
          return val > 0 && val < 24
        },
        default: 15
      },
      pickerScale: {
        type: Number,
        validator (val) {
          return val > 0 && val < 24
        },
        default: 8
      },
      value: String
    },
    data () {
      return {
        conf: {}
      }
    },
    watch: {
      conf (val) {
        this.$emit('input', val)
      },
      value (val) {
        this.conf = val
      }
    },
    created () {
      this.conf = this.value
    }
  }
</script>
