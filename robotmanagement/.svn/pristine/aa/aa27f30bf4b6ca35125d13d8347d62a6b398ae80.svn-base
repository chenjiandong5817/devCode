<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section id="section">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <div class="operateTag">
         <el-form :inline="true" :model="filters">
            <el-form-item>
              <el-button type="primary" @click="handleAdd">新增</el-button>
            </el-form-item>
          </el-form>
      </div>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="robotInfos" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="category" label="类别" min-width="220" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="position" label="位置" min-width="220" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-button type="primary" @click="handleAdd">新增</el-button>
    <pagination :to="getRobotInfoCounts" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addRobotInfo().go"
        :callback="getRobotInfoCounts"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editRobotInfo(editId).go"
        :callback="getRobotInfoCounts"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeRobotInfo().go"
        :callback="getRobotInfoCounts"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          name: null
        },
        namesOptions: [],
        robotInfos: null,
        robotInfoCounts: null,
        tableHeight: null,
        listLoading: false,
        addOrUpdate: null,
        editId: null,
        allRobotInfos: null,
        sels: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'name', value: '', label: '名称', type: 'text', rules: [ { required: true, message: '请输入名称' } ], placeholder: '请输入名称' },
          { name: 'category', value: '', label: '类别', type: 'text', rules: [ { required: true, message: '请输入类别' } ], placeholder: '请输入类别' },
          { name: 'position', value: '', label: '位置', type: 'text', rules: [ { required: true, message: '请输入位置' } ], placeholder: '请输入位置' }
        ],
        API: API,
        baseUtil: baseUtil
      }
    },
    computed: {
      pageNumber () {
        return this.$refs['page'].get('pageNumber')
      },
      pageSize () {
        return this.$refs['page'].get('pageSize')
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 获取条数信息
      getRobotInfoCounts () {
        API.getRobotInfoCount().go().then((data) => {
          if (data.success) {
            this.robotInfoCounts = data.robotInfo
            this.getRobotInfoList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 按照分页获取list
      getRobotInfoList () {
        this.listLoading = true
        API.getRobotInfoListPage(this.pageNumber, this.pageSize).go().then((data) => {
          if (data.success) {
            this.$refs['page'].set('total', this.robotInfoCounts)
            this.robotInfos = data.robotInfoList
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.robotId)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addOrUpdate = 'add'
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields, this.addOrUpdate)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        this.addOrUpdate = 'edit'
        this.editId = row.robotId
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields, this.addOrUpdate)
      },
      getTableHeight () {
        this.$nextTick(() => {
          var sectionHeight = document.getElementById('section').offsetHeight
          this.tableHeight = sectionHeight - 52
        })
      }
    },
    mounted () {
      this.getRobotInfoCounts()
      this.getTableHeight()
    }
  }
</script>

<style scoped>
  .operateTag .el-form-item {
    float: right;
    margin-right: 30px;
  }
</style>
