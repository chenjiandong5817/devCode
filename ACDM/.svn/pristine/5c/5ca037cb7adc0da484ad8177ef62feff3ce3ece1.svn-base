<template>
  <el-cascader
    :options="dic"
    :props="setting.props"
    v-model="text"
    :placeholder="'请选择' + placeholder"
    :disabled="disabled"
    :clearable="setting.clearable"
    :size="setting.size"
    @change="handleChange">
  </el-cascader>
</template>

<script>
const defaultMeta = {
  size: 'small',
  clearable: true,
  props: {
    value: 'value',
    label: 'label',
    children: 'children'
  }
}
export default {
  name: 'CrudCascader',
  data () {
    return {
      text: [],
      setting: defaultMeta
    }
  },
  props: {
    value: {
      default: () => {
        return []
      }
    },
    placeholder: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    dic: {
      default: () => {
        return []
      }
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
