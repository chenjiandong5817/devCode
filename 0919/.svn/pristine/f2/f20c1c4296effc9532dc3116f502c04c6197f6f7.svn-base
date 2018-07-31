<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.directionCode" placeholder="编码" @change="directionCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getFlightDirectionsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="flightdirections" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="directionCode" label="编码" width="150" sortable>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getFlightDirectionsList" ref="page"></pagination>

    <!--新增界面-->
    <div class="flightdirectionTag">
      <common-add-or-update
        @getInputItem="directionCodeChange"
        type="add"
        :to="API.addFlightDirections().go"
        :callback="getFlightDirectionsList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="flightdirectionTag">
      <common-add-or-update
        @getInputItem="directionCodeChange"
        title="编辑"
        type="update"
        :to="API.editFlightDirections().go"
        :callback="getFlightDirectionsList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeFlightDirections().go"
        :callback="getFlightDirectionsList"
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
          directionCode: '',
          description: ''
        },
        flightdirections: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // airportsChoose: [],
        directionCodeValidate: null,
        directionCodeTag: null,
        addUpdateTag: null,
        directionCodeCharacter: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'directionCode', value: '', label: '编码', type: 'text', rules: null, placeholder: '请输入编码' },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 2, min: 1, required: true, message: '请输入一位到两位字符串的描述' } ], placeholder: '请输入描述' },
          { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '请输入一位到一百位字符串的描述' } ], placeholder: '请输入备注' }
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
      // 获取用户列表
      getFlightDirectionsList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getFlightDirectionsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.flightdirections = data.attr.data.list
            this.filters.directionCode = ''
            this.filters.description = ''
            this.getUsedDirectionCode()
            this.getDirectionCodeValidate()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
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
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.fields[1].rules = [ { validator: this.directionCodeValidate } ]
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        var result = []
        for (var i = 0; i < this.directionCodeCharacter.length; i++) {
          if (this.directionCodeCharacter[i].directionCode !== row.directionCode) {
            result.push(this.directionCodeCharacter[i])
          }
        }
        this.directionCodeCharacter = result
        this.addUpdateTag = 'edit'
        this.directionCodeTag = row.directionCode
        this.fields[1].rules = [ { validator: this.directionCodeValidate } ]
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      directionCodeChange: function (data) {
        if (data.directionCode !== this.directionCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.directionCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var directionCodeTagStrAdd = 'directionCode'
            this.$refs['addForm'].changeInput(directionCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var directionCodeStrEdit = 'directionCode'
            this.$refs['editForm'].changeInput(directionCodeStrEdit, singleUpperCode)
          }
        }
        this.directionCodeTag = data.directionCode
      },
      getDirectionCodeValidate: function () {
        this.directionCodeValidate = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('编码不能为空！'))
          }
          if (value.length !== 1) {
            return callback(new Error('请输入一位字母！'))
          }
          if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          for (var i = 0; i < this.directionCodeCharacter.length; i++) {
            if (value.toUpperCase() === this.directionCodeCharacter[i].directionCode) {
              return callback(new Error('编码已存在！'))
            }
          }
          callback()
       }
      },
      // 获取已经使用过的大写字母
      getUsedDirectionCode: function () {
        this.directionCodeCharacter = []
        for (var i = 0; i < this.flightdirections.length; i++) {
          var item = {}
          item['directionCode'] = this.flightdirections[i].directionCode
          item['description'] = this.flightdirections[i].description
          this.directionCodeCharacter.push(item)
        }
      },
      directionCodeUpper: function (data) {
        this.filters.directionCode = data.toUpperCase()
      }
    },
    mounted () {
      this.getFlightDirectionsList()
    }
  }

</script>

<style>
  .flightdirectionTag .el-dialog--small {
    width: 40%!important
  }
</style>
