<template>
  <el-checkbox-group v-model="text" @change="handleChange" :size="setting.size">
    <el-checkbox v-for="(item,index) in dic" :label="item.value" :key="index" :border="setting.border">{{item.label}}</el-checkbox>
  </el-checkbox-group>
</template>

<script>
const defaultMeta = {
  size: 'small',
  border: false
}
export default {
  name: 'CrudCheckbox',
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
