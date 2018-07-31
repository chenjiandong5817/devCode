<template>
  <el-date-picker
    :class="{'clearable': setting.clearable}"
    v-model="text"
    :type="type"
    :placeholder="'请输入' + placeholder"
    @change="handleChange"
    :size="setting.size"
    :clearable="setting.clearable"
    :disabled="disabled">
  </el-date-picker>
</template>

<script>
const defaultMeta = {
  size: 'small',
  clearable: true
}
export default {
  name: 'CrudDate',
  data () {
    return {
      text: '',
      setting: defaultMeta
    }
  },
  props: {
    value: {
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
      default: 'date'
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
