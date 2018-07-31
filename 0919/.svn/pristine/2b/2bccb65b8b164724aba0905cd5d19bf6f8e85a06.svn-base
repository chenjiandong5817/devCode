/*
 * @Author: ylj
 * @Date: 2017-12-14 17:47:42
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-14 17:50:15
 */

<template>
  <div class="searchFieldCss">
    <el-input :type="item.type" v-model="filters[item.name]" auto-complete="off" :placeholder="item.label" :disabled="item.disabled"
      v-if="item.type === 'text' || item.type === 'textarea' || item.type === 'password'" @change="setFilters(item)"></el-input>

    <date-time v-model="filters[item.name]" :visitDate="item.visitDate" :visitTime="item.visitTime" :dateStyle="item.dateStyle" :timeStyle="item.timeStyle" :datePlaceholder="item.datePlaceholder"
      :timePlaceholder="item.timePlaceholder" :formatter="item.formatter" v-else-if="item.type === 'dateTimeGroup'" :allDisable="item.allDisable" v-on:setOnChangeVal="setFilters(item)"></date-time>

    <el-select class="selectA" v-model="filters[item.name]" :disabled="item.disabled" :placeholder="item.label" :filterable="item.filterable" :clearable="item.clearable" :multiple="item.multiple" v-else-if="item.type === 'selectSimple'" @change="setFilters(item)">
      <el-option
        v-for="selectItem in item.choose"
        :key="selectItem[item.selValue]"
        :label="selectItem[item.selLabel] + '(' + (item.is2Label ? selectItem[item.selValue2] + '/' + selectItem[item.selValue] : selectItem[item.selValue]) + ')'"
        :value="selectItem[item.selValue]">
        <span style="float: left">{{ selectItem[item.selLabel] }}</span>
        <span style="float: right" v-if="!item.is2Label">{{ selectItem[item.selValue] }}</span>
        <span style="float: right" v-else-if="item.is2Label">{{ selectItem[item.selValue2] + '/' + selectItem[item.selValue] }}</span>
      </el-option>
    </el-select>
  </div>
</template>
<script>
import DateTime from '../../../components/DateTime'

export default {
  props: {
    item: {
      type: Object,
      default: {}
    },
    index: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      filters: []
    }
  },
  components: {
    dateTime: DateTime
  },
  methods: {
    setFilters (item) {
      let para = { index: this.index, field: item.name, value: this.filters[item.name] }
      this.$emit('setFiltersVal', para)
    }
  }
}
</script>
<style lang="scss">
.searchFieldCss {
  .selectA {
    width: 100%;
  }
}
</style>
