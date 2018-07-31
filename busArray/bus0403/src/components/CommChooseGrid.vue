/*
 * @Author: cdroid
 * @Date: 2017-04-17 15:03:31
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-08-23 17:21:37
 * @Description: 通用选择列表界面
 */

<template>
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" size="tiny">
    <el-table :data="gridData" row-key="id" :height="gridHeight" border v-loading="listLoading" @selection-change="selectionChange" highlight-current-row v-if="tree===false" ref="chooseTable">
      <el-table-column type="selection" reserve-selection width="55"></el-table-column>
      <el-table-column v-for="item in columns" :key="item.name" :property="item.name" :label="item.label" :width="item.width" :show-overflow-tooltip="item.overflow" ></el-table-column>
    </el-table>
    <el-tree
      :data="gridData"
      show-checkbox
      node-key="id"
      :default-expanded-keys="treeExpandedKeys"
      :default-checked-keys="treeCheckedKeys"
      :props="treeProps"
      v-else
      ref="chooseTree">
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import util from './../common/js/util.js'
  import NProgress from 'nprogress'
  export default {
    props: {
      title: {
        type: String,
        default: '选择界面'
      },
      gridHeight: {
        type: Number,
        default: 0
      },
      tree: {
        type: Boolean,
        default: false
      },
      getListApi: {
        type: Function,
        default: function () {}
      },
      updateApi: {
        type: Function,
        default: function () {}
      },
      callback: {
        type: Function,
        default: function () {}
      }
    },
    data () {
      return {
        visible: false,
        target: {},
        gridData: [],
        listLoading: false,
        loading: false,
        sels: [],
        columns: [],

        // tree
        treeExpandedKeys: [],
        treeCheckedKeys: [],
        treeProps: {},

        // log
        oldValue: []

      }
    },
    methods: {
      show: function (target, columns, initData) {
        this.target = target
        this.visible = true
        if (this.tree) {
          this.treeProps = columns
          if (initData) {
            this.gridData = initData.data || []
            this.treeCheckedKeys = initData.treeCheckedKeys || []
            this.oldValue = this.treeCheckedKeys
          } else {
            // 后面修改远程加载数据，需配置api
          }
        } else {
          this.columns = columns
          if (initData) {
            this.gridData = initData.data || []
            this.sels = initData.sels || []
          } else {
            this.initTable()
          }
        }
      },
      initTable: function () {
        this.listLoading = true
        NProgress.start()
        let para = {id: this.target.id}
        this.getListApi(para).then((res) => {
          console.log(res)
          this.$refs['chooseTable'].clearSelection(this.sels)
          if (res.ok) {
            this.gridData = res.attr.dataGrid
            var checkedData = res.attr.checkedGrid
            this.oldValue = checkedData.map(item => item.id)
            var selecteds = []
            for (var i = 0; i < this.gridData.length; i++) {
              for (var j = 0; j < checkedData.length; j++) {
                if (checkedData[j].id === this.gridData[i].id) {
                  this.$refs['chooseTable'].toggleRowSelection(this.gridData[i])
                  selecteds.push(this.gridData[i])
                }
              }
            }
            this.sels = selecteds
          } else {
            this.$notify(util.notifyBody(false, res.msg))
          }
          this.listLoading = false
          NProgress.done()
        })
      },
      selectionChange: function (sels) {
        this.sels = sels
      },
      handleClose: function () {
        if (this.tree) {
          // 树形界面的回调函数
        } else {
          this.$refs['chooseTable'].clearSelection(this.sels)
        }
      },
      handleSubmit: function () {
        let ids = []
        if (this.tree) {
          ids = this.$refs['chooseTree'].getCheckedKeys(true)
        } else {
          ids = this.sels.map(item => item.id)
        }
        this.$confirm('确认提交吗？', '提示', {}).then(() => {
          // let ids = this.sels.map(item => item.id)
          this.loading = true
          NProgress.start()
          // let para = Object.assign(this.target, {checkedIds: ids})
          let para = Object.assign({}, {target: this.target, newValue: ids, oldValue: this.oldValue})
          this.updateApi(para).then((res) => {
            console.log(res)
            this.loading = false
            NProgress.done()
            this.$notify(util.notifyBody(res.ok, res.msg || ''))
            this.visible = false
            this.target = {}
            this.sels = []
            this.callback()
          })
        })
      }
    }
  }
</script>
<style>
</style>
