/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:43:06
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-26 10:26:36
 * @Description: 分页界面
 */

<template>
  <!--工具条-->
    <el-col :span="24" :class="clas">
      <el-pagination :layout="layout"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="sizeArray"
          :page-size="pageSize"
          :total="total"
          style="float: right;">
      </el-pagination>
    </el-col>
</template>
<script>
export default {
  props: {
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    clas: {
      type: String,
      default: 'toolbar-bottom'
    },
    size: {
      type: Number,
      default: 50
    },
    sizeArray: {
      type: Array,
      default: () => {
        return [50, 100]
      }
    },
    to: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      pageNumber: 1,
      pageSize: this.size,
      total: 0
    }
  },
  methods: {
    handleSizeChange (size) {
      this.pageSize = size
      // console.log('this.pageSize', this.pageSize)
      this.to()
    },
    handleCurrentChange (val) {
      this.pageNumber = val
      // console.log('val', val)
      // console.log('this.pageNumber', this.pageNumber)
      this.to()
    },
    set (column, val) {
      this[column] = val
    },
    get (column) {
      return this[column]
    },
    queryParam () {
      return {
        pageSize: this.pageSize,
        pageNumber: this.pageNumber
      }
    }
  }
}
</script>

