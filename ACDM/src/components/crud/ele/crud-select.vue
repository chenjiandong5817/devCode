<template>
  <el-select
    v-model="text"
    :class="{'clearable': setting.clearable}"
    :placeholder="'请选择' + placeholder"
    @change="handleChange"
    :disabled="disabled"
    :clearable="setting.clearable"
    :filterable="setting.filterable"
    :multiple="setting.multiple"
    :size="setting.size">
    <el-option v-for="(item, index) in dic" :key="index" :label="item[setting.props.label]" :value="item[setting.props.value]">
      <slot :name="`${slot}Crud`" :target="item" v-if="slot"></slot>
      <template v-else-if="template">
        <span style="float: left">{{ template(item)[0] }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ template(item)[1] }}</span>
      </template>
    </el-option>
  </el-select>
</template>

<script>
const defaultMeta = {
  size: 'small',
  clearable: true,
  filterable: true,
  multiple: false,
  props: {
    label: 'label',
    value: 'value'
  },
  template: null
}
export default {
  name: 'CrudSelect',
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
      default: () => defaultMeta
    }
  },
  computed: {
    slot () {
      if (this.setting.template && typeof this.setting.template === 'string') {
        return this.setting.template
      }
      return null
    },
    template () {
      if (this.setting.template && this.setting.template instanceof Function) {
        return this.setting.template
      }
      return null
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
