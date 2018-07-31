/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:53:54
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-05 13:13:38
 * @Description:  API展示列表
 */
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="全字段匹配" @keydown.enter.native="query" icon="search" :on-icon-click="query"></el-input>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <div class="table-content">
      <el-table v-bind:data="apiGrid" highlight-current-row stripe v-loading="listLoading" style="width: 100%;">
        <!--<el-table-column type="selection" width="55">
        </el-table-column>-->
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column prop="name" label="名称" width="180" sortable>
        </el-table-column>
        <el-table-column prop="type" label="分类" width="150" sortable>
        </el-table-column>
        <el-table-column prop="url" label="地址" min-width="180" sortable>
        </el-table-column>
        <el-table-column prop="requetType" label="请求类型" width="150" sortable>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="180" sortable>
        </el-table-column>
      </el-table>
    </div>

    <!--工具条-->
    <!--<el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>-->
  </section>
</template>

<script>
import API from './../../api'
export default {
  data () {
    return {
      filters: {
        name: ''
      },
      apiGrid: [],
      storeGrid: [],
      listLoading: false
    }
  },
  methods: {
    getApiGrid () {
      let grid = []
      this.listLoading = true
      for (var key in API) {
        grid.push(API[key].apply(this))
      }
      this.apiGrid = grid
      this.listLoading = false
    },
    query () {
      let grid = []
      for (var key in this.storeGrid) {
        for (var k in this.storeGrid[key]) {
          if (!(this.storeGrid[key][k] instanceof Function) && this.storeGrid[key][k].indexOf(this.filters.name) > -1) {
            grid.push(this.storeGrid[key])
            break
          }
        }
      }
      this.apiGrid = grid
    }
  },
  mounted () {
    this.getApiGrid()
    this.storeGrid = this.apiGrid
  }
}
</script>
