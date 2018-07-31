<template>
  <el-input
    :class="{'clearable': setting.clearable}"
    :size="setting.size"
    :clearable="setting.clearable"
    :value="text"
    :type="getType(type)"
    :autosize="{ minRows: setting.minRows, maxRows: setting.maxRows}"
    :placeholder="`请输入${placeholder}`"
    @input="handleInput"
    :disabled="disabled">
  </el-input>
</template>

<script>
let defaultMeta = {
  size: 'small',
  clearable: true,
  convert: '',
  minRows: 3,
  maxRows: 4
}
export default {
  name: 'CrudInput',
  data () {
    return {
      text: '',
      setting: Object.assign({}, defaultMeta),
      convertRange: ['toUpperCase', 'toLowerCase']
    }
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    },
    meta: {
      type: Object,
      default: () => defaultMeta
    }
  },
  watch: {
    value: function (n, o) {
      this.text = this.value
    }
  },
  created () {
    this.text = this.value
    this.setting = Object.assign({}, this.setting, this.meta)
  },
  mounted () {},
  methods: {
    getType (type) {
      let types = ['textarea', 'text', 'password']
      if (types.includes(type)) {
        return type
      }
      return 'text'
    },
    handleInput (value) {
      let val = value
      if (this.convertRange.includes(this.setting.convert)) {
        val[this.setting.convert] && (val = val[this.setting.convert]())
      }
      this.text = val
      this.$emit('input', this.text)
    },
    handleChange (value) {
      let val = value
      if (this.convertRange.includes(this.setting.convert)) {
        val[this.setting.convert] && (val = val[this.setting.convert]())
      }
      this.$emit('input', val)
    }
  }
}
</script>

<style>

</style>
