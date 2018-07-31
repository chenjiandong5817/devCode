<!--
  这个组件只是为了用来嵌套el-table-item
-->
<template>
  <el-table-column :label="column.label" :header-align="validData(column.headerAlign, 'center')" v-if="column.group">
    <template v-for="(item, index) of column.group">
      <crud-table-item :key="index" :column="item" multiHeader>
        <template slot-scope="{ row, $index }">
          <slot v-bind="{row, $index}"></slot>
        </template>
      </crud-table-item>
    </template>
  </el-table-column>
  <el-table-column
    v-else-if="!column.hide"
    show-overflow-tooltip
    :prop="column.prop"
    :width="column.width"
    :min-width="column.minWidth"
    :label="column.label"
    :fixed="column.fixed"
    :sortable="column.sortable"
    :header-align="validData(column.headerAlign, multiHeader ? 'center' : 'left')"
    :align="validData(column.align, 'left')"
    >
    <template slot-scope="{ row, $index }">
      <slot v-bind="{row, $index}" v-if="column.slot"></slot>
      <template v-else>
        <span class="my-custom">{{detail(row, column)}}</span>
      </template>
    </template>
  </el-table-column>
</template>
<script>
import { validData } from '@/util/util'
export default {
  name: 'CrudTableItem',
  inject: ['detail'],
  props: {
    column: Object,
    multiHeader: Boolean
  },
  mounted () {
    // console.log(this.column.prop, this.column.label, this.$scopedSlots)
  },
  methods: {
    validData
  }
}
</script>
