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
      <el-form :inline="true" :model="filters">
        <el-form-item label="状态">
        <el-select v-model="filters.isUse" placeholder="请选择是否启用">
          <el-option
            v-for="item in isUseOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="类别编码">
          <el-select v-model="filters.category" placeholder="请选择类别">
          <el-option
            v-for="item in knowledgeCategoryOptions"
            :key="item.value"
            :label="item.text"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <!-- 根据机器人机型过滤 -->
        <el-form-item label="机器人名称">
        <el-select v-model="filters.knowledgeRobotId" placeholder="请选择机器人">
          <el-option
            v-for="item in robotNameOptions"
            :key="item.value"
            :label="item.text"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" v-on:click="searchKnowledge">过滤查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" v-on:click="searchKnowledgeAll">查询全部</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
        <!-- 模糊查询 -->
        <el-form-item>
          <div style="width: 20px;height: 5px;"></div>
        </el-form-item>
        <el-form-item label="问题搜索">
          <el-input v-model="fuzzyFilter.params" placeholder="查询条件"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-on:click="fuzzySearch">问题模糊查询</el-button>
        </el-form-item>
        <!-- 答案查询 -->
        <el-form-item>
          <div style="width: 20px;height: 5px;"></div>
        </el-form-item>
        <el-form-item label="答案搜索">
          <el-input v-model="fuzzyAnswerFilter.params" placeholder="查询答案"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-on:click="fuzzySearchAnswer">答案模糊查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="knowledges" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="question" label="问题" min-width="220" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="answer" label="答案" min-width="220" show-overflow-tooltip>
      </el-table-column>
      <!-- <el-table-column prop="isUse" label="状态" width="100" :formatter="baseUtil.formatterIsNo">
      </el-table-column> -->
      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
           <el-tag type="success" v-if="scope.row.isUse===1">启用</el-tag>
           <el-tag type="danger" v-if="scope.row.isUse===0">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="knowledgeRobotId" label="机器人名称" width="150" :formatter="formatterRobot">
      </el-table-column>
      <el-table-column prop="categoryId" label="类别" width="150" :formatter="formatterCategory">
      </el-table-column>
      <el-table-column prop="createTime" label="生效时间" width="180" :formatter="baseUtil.formatterDated">
      </el-table-column>
      <el-table-column prop="lastUpdateTime" label="最后更新时间" width="180" :formatter="baseUtil.formatterDated">
      </el-table-column>
      <el-table-column label="操作" width="160" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" v-if="scope.row.isUse===1" type="danger" @click="startIsUse(scope.$index, scope.row)">禁用</el-button>
          <el-button size="small" v-if="scope.row.isUse===0" type="success" @click="startIsUse(scope.$index, scope.row)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="searchKnowledge" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addKnowledge().go"
        :callback="getKnowledgeCounts"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editKnowledge(editId).go"
        :callback="getKnowledgeCounts"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeKnowledge().go"
        :callback="getKnowledgeCounts"
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
          category: '',
          isUse: -1,
          knowledgeRobotId: ''
        },
        isUseOptions: [
          { value: -1, label: '全部' },
          { value: 1, label: '启用' },
          { value: 0, label: '禁用' }
        ],
        knowledges: null,
        knowledgesCounts: null,
        tableHeight: null,
        listLoading: false,
        addOrUpdate: null,
        editId: null,
        // CategoryValidate: null,
        robotNameOptions: [],
        knowledgeCategoryOptions: [],
        sels: [],
        // 模糊查询
        fuzzyFilter: {
          params: ''
        },
        // 模糊搜索答案
        fuzzyAnswerFilter: {
          params: ''
        },
        fuzzySearchCount: null,
        fuzzySearchAnswerCount: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'question', value: '', label: '问题', type: 'text', rules: [ { required: true, message: '请输入问题' } ], placeholder: '请输入问题' },
          { name: 'answer', value: '', label: '答案', type: 'textarea', rules: [ { required: true, message: '请输入答案' } ], placeholder: '请输入答案' },
          { name: 'isUse', value: '', label: '请选择状态', type: 'select', choose: [ { text: '启用', value: 1 }, { text: '禁用', value: 0 } ], rules: null, placeholder: '' },
          { name: 'categoryId', value: '', label: '请选择类别', type: 'select', choose: [], rules: null, placeholder: '请输入类别' },
          { name: 'knowledgeRobotId', value: '', label: '请选择机器人', type: 'select', choose: [], rules: null, placeholder: '请选择机器人' }
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
      getKnowledgeCounts () {
        API.getKnowledgeCount().go().then((data) => {
          if (data.success) {
            this.knowledgesCounts = data.knowledgeCount
            this.getKnowledgeList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 获取list
      getKnowledgeList () {
        this.listLoading = true
        API.getKnowledgeListPage(this.pageNumber, this.pageSize).go().then((data) => {
          if (data.success) {
            this.$refs['page'].set('total', this.knowledgesCounts)
            this.knowledges = data.knowledgeList
            // this.getCategoryValidate()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      searchKnowledge () {
        this.fuzzyFilter.params = ''
        this.fuzzyAnswerFilter.params = ''
        // 每次查询先获取总条数
        API.searchKnowledgeCount(this.filters.isUse, this.filters.category, this.filters.knowledgeRobotId).go().then((data) => {
          if (data.success) {
              this.knowledgesCounts = data.knowledgeCount
              API.searchKnowledge(this.filters.isUse, this.filters.category, this.filters.knowledgeRobotId, this.pageNumber, this.pageSize).go().then((data) => {
                  if (data.success) {
                    this.knowledges = data.knowledgeList
                    this.$refs['page'].set('total', this.knowledgesCounts)
                  } else {
                    this.$notify(Util.notifyBody(false, data.message))
                  }
                  this.listLoading = false
                })
            } else {
              this.$notify(Util.notifyBody(false, data.message))
            }
            this.listLoading = false
        })
      },
      searchKnowledgeAll () {
        this.filters = {
          category: '',
          isUse: -1,
          knowledgeRobotId: ''
        }
        this.fuzzyFilter.params = ''
        this.fuzzyAnswerFilter.params = ''
        this.searchKnowledge()
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
        this.addOrUpdate = 'add'
        this.fields[3].value = 1
        // this.fields[4].rules = this.CategoryValidate
        var knowledgeResult = []
        var robotNameResult = []
        knowledgeResult = this.knowledgeCategoryOptions.slice(1, this.knowledgeCategoryOptions.length)
        robotNameResult = this.robotNameOptions.slice(1, this.robotNameOptions.length)
        this.fields[4].choose = knowledgeResult
        this.fields[5].choose = robotNameResult
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields, this.addOrUpdate)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        this.addOrUpdate = 'edit'
        var knowledgeResult = []
        var robotNameResult = []
        knowledgeResult = this.knowledgeCategoryOptions.slice(1, this.knowledgeCategoryOptions.length)
        robotNameResult = this.robotNameOptions.slice(1, this.robotNameOptions.length)
        this.fields[4].choose = knowledgeResult
        this.fields[5].choose = robotNameResult
        this.editId = row.id
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields, this.addOrUpdate)
      },
      startIsUse: function (index, row) {
        if (row.isUse === 1) {
          row.isUse = 0
        } else {
          row.isUse = 1
        }
        let para = {
          question: row.question,
          answer: row.answer,
          categoryId: row.categoryId,
          isUse: row.isUse
        }
        API.editKnowledge(row.id).go(para).then((res) => {
          this.$notify(Util.notifyBody(res.success, res.message))
          this.searchKnowledge()
        })
      },
      // 获取所有list
      getAllRobotInfoList () {
        API.getRobotInfoListPage(1, 10000).go().then((data) => {
          if (data.success) {
            var robotArray = data.robotInfoList
            this.robotNameOptions = []
            this.robotNameOptions = [ {
              text: '全部',
              value: ''
            } ]
            for (var i = 0; i < robotArray.length; i++) {
              var result = {}
              result['text'] = robotArray[i].name
              result['value'] = robotArray[i].robotId
              this.robotNameOptions.push(result)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      formatterRobot (row, column, cellValue) {
        var result = null
        for (var i = 0; i < this.robotNameOptions.length; i++) {
          if (cellValue === this.robotNameOptions[i].value) {
            result = this.robotNameOptions[i].text
          }
        }
        return result
      },
      getKnowledgeCategoryList () {
        API.getKnowledgeCategoryListPage(1, 10000).go().then((data) => {
          if (data.success) {
            var knowledgeCategoryArray = data.knowledgeCategoryList
            this.knowledgeCategoryOptions = []
            this.knowledgeCategoryOptions = [ {
              text: '全部',
              value: ''
            } ]
            for (var i = 0; i < knowledgeCategoryArray.length; i++) {
              var result = {}
              result['text'] = knowledgeCategoryArray[i].knowledgeCategoryCNName
              result['value'] = knowledgeCategoryArray[i].knowledgeCategoryId
              this.knowledgeCategoryOptions.push(result)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      formatterCategory (row, column, cellValue) {
        var result = null
        for (var i = 0; i < this.knowledgeCategoryOptions.length; i++) {
          if (this.knowledgeCategoryOptions[i].value === cellValue) {
            result = this.knowledgeCategoryOptions[i].text
          }
        }
        return result
      },
      getTableHeight () {
        this.$nextTick(() => {
          var sectionHeight = document.getElementById('section').offsetHeight
          this.tableHeight = sectionHeight - 52
        })
      },
      fuzzySearch () {
        // 重置查询搜索框
        this.filters = {
          category: '',
          isUse: -1,
          knowledgeRobotId: ''
        }
        this.fuzzyAnswerFilter.params = ''
        API.fuzzySearchKnowledgeCount(this.fuzzyFilter.params).go().then((data) => {
          if (data.success) {
              this.fuzzySearchCount = data.knowledgeCount
              API.fuzzySearchKnowledge(this.fuzzyFilter.params, this.pageNumber, this.pageSize).go().then((data) => {
                  if (data.success) {
                    this.knowledges = data.knowledgeList
                    this.$refs['page'].set('total', this.fuzzySearchCount)
                  } else {
                    this.$notify(Util.notifyBody(false, data.message))
                  }
                  this.listLoading = false
                })
            } else {
              this.$notify(Util.notifyBody(false, data.message))
            }
            this.listLoading = false
        })
      },
      fuzzySearchAnswer () {
        // 重置查询搜索框
        this.filters = {
          category: '',
          isUse: -1,
          knowledgeRobotId: ''
        }
        this.fuzzyFilter.params = ''
        API.fuzzySearchKnowledgeAnswerCount(this.fuzzyAnswerFilter.params).go().then((data) => {
          if (data.success) {
              this.fuzzySearchAnswerCount = data.knowledgeCount
              API.fuzzySearchKnowledgeAnswer(this.fuzzyAnswerFilter.params, this.pageNumber, this.pageSize).go().then((data) => {
                  if (data.success) {
                    this.knowledges = data.knowledgeList
                    this.$refs['page'].set('total', this.fuzzySearchAnswerCount)
                  } else {
                    this.$notify(Util.notifyBody(false, data.message))
                  }
                  this.listLoading = false
                })
            } else {
              this.$notify(Util.notifyBody(false, data.message))
            }
            this.listLoading = false
        })
      }
    },
    mounted () {
      this.getAllRobotInfoList()
      this.getKnowledgeCategoryList()
      this.getKnowledgeCounts()
      this.getTableHeight()
    }
  }
</script>

<style scoped>

</style>
