<template>
  <component
    :is="getComponent(type || column.type)"
    v-model="form[column.prop]"
    :size="validData(column.meta ? column.meta.size : '', 'small')"
    :type="type || column.type"
    :meta="Object.assign({}, column.meta, isTemplateSlot(column) ? {template: column.prop} : {})"
    :placeholder="column.label"
    :dic="findDic(column.dicData)"
    :disabled="disabled"
  >
    <template slot-scope="{ target }" :slot="`${column.prop}Crud`" v-if="isTemplateSlot(column)">
      <slot :name="`${column.prop}Crud`" v-bind="{ target }"></slot>
    </template>
  </component>
</template>
<script>
import CrudInput from './crud-input'
import CrudRadio from './crud-radio'
import CrudInputNumber from './crud-input-number'
import CrudCascader from './crud-cascader'
import CrudCheckbox from './crud-checkbox'
import CrudDate from './crud-date'
import CrudSelect from './crud-select'
import { validData } from '@/util/util'
export default {
  components: {
    CrudInput, CrudRadio, CrudInputNumber, CrudCascader, CrudCheckbox, CrudDate, CrudSelect
  },
  inject: ['findDic'],
  props: {
    column: Object,
    form: Object,
    type: String,
    disabled: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    validData,
    isTemplateSlot (column) {
      return column.meta && column.meta.template && typeof column.meta.template === 'boolean'
    },
    // 动态获取组件
    getComponent (type) {
      const dateFormat = ['year', 'month', 'date', 'dates', 'week', 'datetime', 'datetimerange', 'daterange']
      if (type === 'select') {
        return 'CrudSelect'
      } else if (type === 'radio') {
        return 'CrudRadio'
      } else if (type === 'number') {
        return 'CrudInputNumber'
      } else if (dateFormat.includes(type)) {
        return 'CrudDate'
      } else if (type === 'checkbox') {
        return 'CrudCheckbox'
      } else if (type === 'cascader') {
        return 'CrudCascader'
      } else {
        return 'CrudInput'
      }
    }
  }
}
</script>
