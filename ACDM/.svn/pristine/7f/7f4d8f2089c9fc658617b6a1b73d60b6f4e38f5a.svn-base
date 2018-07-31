<template>
  <el-input-number
    v-model="text"
    @change="handleChange"
    :min="setting.min"
    :max="setting.max"
    :size="setting.size"
    :label="'请输入' + placeholder"
    :disabled="disabled">
  </el-input-number>
</template>

<script>
const defaultMeta = {
  size: 'small',
  min: -Infinity,
  max: Infinity
}
export default {
  name: 'CrudInputNumber',
  data () {
    return {
      text: '',
      setting: defaultMeta
    }
  },
  props: {
    value: {
      type: Number
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
    handleChange (value) {
      this.$emit('input', value)
    }
  }
}
</script>

<style>

</style>
