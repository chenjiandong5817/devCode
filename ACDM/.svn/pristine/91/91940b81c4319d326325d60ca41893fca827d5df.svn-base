<template>
  <el-radio-group v-model="text" @change="handleChange" :disabled="disabled" :size="setting.size">
    <el-radio v-for="(item, index) in dic" :label="item.value" :key="index" :border="setting.border">{{item.label}}</el-radio>
  </el-radio-group>
</template>

<script>
const defaultMeta = {
  size: 'small',
  border: false
}
export default {
  name: 'CrudRadio',
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
