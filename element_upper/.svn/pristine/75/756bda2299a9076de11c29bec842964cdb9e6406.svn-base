/*
 * @Author: ylj
 * @Date: 2017-11-19 16:50:47
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-19 18:05:06
 */
<template>
    <el-table :data="tableData" border @expand="handleExpand" @selection-change="selectChange" ref="raw_table">
        <el-table-column v-for="(item,key) in tableKey" align="center"
          v-if="!item.operate && !isExpand"
          :key="key"
          :prop="item.value"
          :label="item.name"
          :width="item.width">
        </el-table-column>

        <!-- 自定义列 -->
        <el-table-column v-else-if="item.operate && !isExpand">
            <template scope="scope">
                <slot :name="item.value" :$index="scope.$index" :row="scope.row"></slot>
            </template>
        </el-table-column>

        <el-table-column v-else type="expand">
          <template scope="scope">
              <slot name="expand" :$index="scope.$index" :row="scope.row"></slot>
          </template>
        </el-table-column>
    </el-table>
</template>

<script>
export default{
  name: 'table',
  props: {
    isExpand: {
      type: Boolean,
      default: false
    },
    isExpandOnly: {
      type: Boolean,
      default: true
    },
    loadData: {
      type: Function,
      default: function () {}
    },
    selectChange: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      tableData: [],
      tableKey: []
    }
  },
  methods: {
    handleExpand (row, isexpand) {
      if (this.isExpand && this.isExpandOnly) {
        this.$refs.raw_table.store.states.expandRows = isexpand ? [row] : []
      }
    },
    clearAllData () {
      this.tableData = []
      this.tableKey = []
    }
  }
}
</script>
