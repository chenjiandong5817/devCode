/*
 * @Author: chenyang
 * @Date: 2017-08-22 16:22:57
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-05 16:54:26
 */
<template>
  <section class="inPage">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <div>
          <el-form-item :span="4">
            <el-select v-model="filters.originaltype" placeholder="语言类型" @change="cli">
              <el-option label="AIRPORT 机场" value="AIRPORT"></el-option>
              <el-option label="AIRLINE 航线" value="AIRLINE"></el-option>
              <el-option label="FLIGHTSTATUS 航班状态" value="FLIGHTSTATUS"></el-option>
              <el-option label="LABEL 标签" value="LABEL"></el-option>
              <el-option label="LABELTEXT 标签文本" value="LABELTEXT"></el-option>
              <el-option label="AREA 区域" value="AREA"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :span="4">
            <el-select v-model="filters.langcode" placeholder="语言编码">
              <el-option label="zh-cn 中文" value="zh-cn"></el-option>
              <el-option label="en-ww 英文" value="en-ww"></el-option>
              <el-option label="ja-jp 日文" value="ja-jp"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :span="4">
            <el-input v-model="filters.original" placeholder="原文"></el-input>
          </el-form-item>
          <el-form-item :span="4">
            <el-input v-model="filters.translation" placeholder="译文"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getMultiLanguage" icon="search">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleAdd" icon="edit">新增</el-button>
          </el-form-item>
        </div>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table v-bind:data="multiLangList" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="originaltype" label="语言类型" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="langcode" label="语言编码" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="original" label="原文" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="translation" label="译文" min-width="200" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right" >
        <template scope="scope">
          <!--<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
          <el-button type="success" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getMultiLanguage" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addMultiLang().go"
        :callback="getMultiLanguage"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editMultiLang().go"
        :callback="getMultiLanguage"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>

    <!--删除窗口-->
    <common-delete
        :to="API.removeMultiLang().go"
        :callback="getMultiLanguage"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          originaltype: 'AIRPORT',
          original: '',
          langcode: '',
          translation: ''
        },
        multiLangList: [],
        tableHeight: 450,
        listLoading: false,
        sels: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'originaltype', value: '', label: '语言类型', type: 'select', choose: [{ text: 'AIRPORT 机场', value: 'AIRPORT' }, { text: 'AIRLINE 航线', value: 'AIRLINE' }, { text: 'FLIGHTSTATUS 航班状态', value: 'FLIGHTSTATUS' }, { text: 'LABEL 标签', value: 'LABEL' }, { text: 'LABELTEXT 标签文本', value: 'LABELTEXT' }, { text: 'AREA 区域', value: 'AREA' }], rules: [ { required: true, message: '请选择语言类型' } ] },
          { name: 'langcode', value: '', label: '语言编码', type: 'select', choose: [{ text: 'zh-cn 中文', value: 'zh-cn' }, { text: 'en-ww 英文', value: 'en-ww' }, { text: 'ja-jp 日文', value: 'ja-jp' }], rules: [ { required: true, message: '请选择语言编码' } ] },
          { name: 'original', value: '', label: '原文', type: 'text' },
          { name: 'translation', value: '', label: '译文', type: 'text' }
        ],
        API: API
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
      cli () {
        this.getMultiLanguage()
      },
      // 获取用户列表
      getMultiLanguage () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getMultiLang().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.multiLangList = data.attr.data.list
            // console.log(this.multiLangList)
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
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      }
    },
    mounted () {
      this.getMultiLanguage()
    }
  }

</script>

<style lang="scss">
.inPage {
  .el-form-item__label {
    text-align: right;
    vertical-align: middle;
    float: left;
    font-size: 14px;
    color: #48576a;
    line-height: 1;
    padding: 11px 33px 11px 0;
    box-sizing: border-box;
  }
  .el-table__fixed-right::before, .el-table__fixed::before {
    content: '';
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 1px;
    background-color: #dfe6ec;
    z-index: 4;
  }
}
</style>
