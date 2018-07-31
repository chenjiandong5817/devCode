<template>
  <transition name="fade" mode="out-in" @after-enter="handleAfterEnter">
    <div class="crud-container" v-if="isInited" key="main">
      <div class="toolbar crud-toolbar">
        <slot name="toolbar" v-if="tableOption.toolbar"></slot>
        <el-form :inline="true" :model="queryFilters" v-else>
          <!-- 按query 排序查询条件 -->
          <template v-for="queryItems in querys">
            <!-- 相同query序号 按照默认column的顺序排列 -->
            <template v-for="(column, index) of queryItems">
              <el-form-item :key="`query-${column.prop}-${index}`" v-if="!column.hide">
                <!-- 各类表单控件 -->
                <crud-auto-component :column="column" :form="queryFilters" :type="column.queryType || column.type">
                  <template slot-scope="{ target }" :slot="`${column.prop}Crud`">
                    <slot :name="`${column.prop}Crud`" v-bind="{ target }"></slot>
                  </template>
                </crud-auto-component>
              </el-form-item>
            </template>
          </template>
          <slot name="btn_opt"></slot>
        </el-form>
      </div>
      <div class="table-content">
        <el-table
          ref="crudTable"
          :data="tableData"
          :stripe="tableOption.stripe"
          highlight-current-row
          :width="setPx(tableOption.width, '100%')"
          v-bind="Object.assign({},
            isDefaultHeight
            ? {}
            : { height: (isAutoTableHeight ? height : tableOption.height) - validData(tableOption.calcHeight, 0) }
          )"
          :max-height="tableOption.maxHeight"
          size="small"
          :border="tableOption.border"
          @row-click="handleRowClick"
          @row-dblclick="handleRowDblclick"
          @row-contextmenu="handleRowContextmenu"
          @selection-change="handleSelectionChange"
          @sort-change="handleSortChange"
          v-loading="tableLoading"
        >
          <template v-if="tableOption.selection">
            <el-table-column type="selection" width="50" align="center" fixed="left">
            </el-table-column>
          </template>
          <template v-if="validData(tableOption.index, true)">
            <el-table-column type="index" width="50" fixed="left" align="center">
              <template slot-scope="scope">
                {{ (page.pageNumber - 1) * page.pageSize + scope.$index + 1 }}
              </template>
            </el-table-column>
          </template>
          <template v-for="(column, index) in tableOption.column">
            <crud-table-item :key="index" :column="column">
              <!-- slot无法跨级传递，先在本页获取，然后传递给子组件 -->
              <template slot-scope="{ row, $index }">
                <template v-for="(item) in leafSlotColumns">
                  <slot v-bind="{ row, $index }" :name="item.prop"></slot>
                </template>
              </template>
            </crud-table-item>
          </template>
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination
        v-if="tableOption.page"
        right
        class="crud-pagination"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.pageNumber"
        :page-sizes="page.pageSizes"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total">
      </pagination>
      <!-- 表单 -->
      <el-dialog :modal-append-to-body="false" :append-to-body="true" :title="dialogTitle" :visible.sync="dialogVisible" :width="setPx(tableOption.formWidth, '50%')" :before-close="handleDialogClose">
        <el-form ref="tableForm" :model="tableForm" label-width="80px" :rules="tableFormRules" v-if="elementFormInit">
          <el-row :gutter="20">
            <template v-for="(column, index) in leafColumns">
              <el-col :span="column.span || 12" v-if="dialogType === 0 ? validData(column.addVisible, true) : validData(column.editVisible, true)" :key="`form-item-${index}`">
                <el-form-item :label="column.label" :prop="column.prop" :label-width="setPx(column.labelWidth, tableOption.labelWidth || 80)">
                  <slot :form="tableForm" :column="column" :dic="findDic(column.dicData)" :name="`${column.prop}Form`" v-if="column.formSlot"></slot>
                  <crud-auto-component
                      v-else
                      :column="column"
                      :form="tableForm"
                      :disabled="dialogType === 0 ? validData(column.addDisabled, false) : validData(column.editDisabled, false)"
                    >
                    <template slot-scope="{ target }" :slot="`${column.prop}Crud`">
                      <slot :name="`${column.prop}Crud`" v-bind="{ target }"></slot>
                    </template>
                  </crud-auto-component>
                </el-form-item>
              </el-col>
            </template>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="rowUpdate" v-if="dialogType === 1" :loading="tableFormLoading" size="small">修 改</el-button>
          <el-button type="primary" @click="rowSave" v-else :loading="tableFormLoading" size="small">新 增</el-button>
        </span>
      </el-dialog>
    </div>
    <crud-loading v-else key="loading"></crud-loading>
  </transition>
</template>
<script>
import { deepCopy, ignoreNull, validData, filterRepeatElement } from '@/util/util'
import { validatenull } from '@/util/validate'
import Pagination from '@/components/pagination/'
import CrudTableItem from './ele/crud-table-item'
import CrudLoading from './ele/crud-loading'
import CrudAutoComponent from './ele/crud-auto-component'
import RemoteData from '@/util/remote-data'
export default {
  name: 'Crud',
  components: {
    CrudAutoComponent, Pagination, CrudTableItem, CrudLoading
  },
  provide () {
    return {
      detail: this.detail,
      findDic: this.findDic
    }
  },
  props: {
    value: {
      type: Object,
      default: () => {
        return {}
      }
    },
    beforeClose: Function,
    beforeOpen: Function,
    tableLoading: {
      type: Boolean,
      default: false
    },
    filters: {
      type: Object,
      default: () => {}
    },
    tableData: {
      type: Array,
      required: true,
      default: () => []
    },
    tableOption: {
      type: Object,
      required: true,
      default: () => {}
    },
    page: {
      type: Object,
      default () {
        return {
          pageSize: 10,
          pageNumber: 1,
          total: 0,
          pageSizes: [10, 20, 50, 100]
        }
      }
    }
  },
  data () {
    return {
      height: 270,
      tableSelection: [],
      DIC: {},
      queryFilters: {},
      dialogType: 0, // 0 为新增，1 为编辑
      dialogVisible: false,
      tableFormLoading: false,
      tableForm: {},
      tableFormRules: {},
      // 强制重新熏染Form
      elementFormInit: false,
      // 加载完毕
      isInited: false
    }
  },
  computed: {
    // 从叶子节点取出带有slot的column
    leafSlotColumns () {
      if (validatenull(this.leafColumns)) {
        return []
      }
      return filterRepeatElement(this.leafColumns.filter(column => column.slot), 'prop')
    },
    // 过滤掉goup节点
    leafColumns () {
      if (validatenull(this.tableOption) || validatenull(this.tableOption.column)) {
        return []
      }
      return this.deepColumn(this.tableOption.column, 'column')
    },
    // 过滤字段列表
    querys () {
      if (this.tableOption.toolbar || validatenull(this.tableOption) || validatenull(this.tableOption.column)) {
        return {}
      }
      return this.groupColumn(this.tableOption.column, 'query')
    },
    // 字典键列表
    dicKeys () {
      if (validatenull(this.tableOption) || validatenull(this.tableOption.column)) {
        return []
      }
      return filterRepeatElement(this.deepColumn(this.tableOption.column, 'dic'), RemoteData.KEY)
    },
    // 窗口标题
    dialogTitle () {
      return this.dialogType === 0 ? '新增' : '编辑'
    },
    // 是否自动计算高度
    isAutoTableHeight () {
      return this.tableOption && this.tableOption.height === 'auto'
    },
    // 是否未设置高度，即没有配置了height参数
    isDefaultHeight () {
      return this.tableOption && (this.tableOption.height === undefined || !this.isAutoTableHeight)
    }
  },
  watch: {
    queryFilters: {
      deep: true,
      handler (val) {
        !this.tableOption.toolbar && this.$emit('update:filters', ignoreNull(deepCopy(val)))
      }
    },
    tableOption: {
      deep: true,
      handler (val) {
        this.afterRender()
      }
    },
    tableForm: {
      deep: true,
      handler (val) {
        this.setFormVal()
      }
    },
    dialogVisible (val) {
      let vm = this
      if (val) {
        vm.elementFormInit = val
      } else {
        setTimeout(() => {
          vm.elementFormInit = val
        }, 350)
      }
    }
  },
  beforeDestroy () {
    this.removeTableListener()
  },
  mounted () {
    this.afterRender(true)
  },
  methods: {
    // 获取数据，如不存在指定默认值
    validData,
    // 页面渲染完成后
    afterRender (init) {
      init && (this.isInited = false)
      this.initRules()
      // 可能包含异步加载字典
      this.initDic().then(() => {
        let vm = this
        setTimeout(() => {
          init && (vm.isInited = true)
          vm.$nextTick(() => {
            vm.removeTableListener()
            vm.addTableListener()
          })
        }, 100)
      })
    },
    addTableListener () {
      this.isAutoTableHeight && window.addEventListener('resize', this.calcTableAutoHeight)
    },
    removeTableListener () {
      this.isAutoTableHeight && window.removeEventListener('resize', this.calcTableAutoHeight)
    },
    handleAfterEnter (el) {
      this.isAutoTableHeight && this.calcTableAutoHeight()
    },
    // 补全px单位，如果为百分比则不操作
    setPx (val, defaultVal) {
      if (validatenull(val)) {
        val = defaultVal
      }
      val = val + ''
      if (val.indexOf('%') === -1) {
        val = val + 'px'
      }
      return val
    },
    // 遍历tableOption, 按字段的值分组
    groupColumn (columns = [], property = 'query') {
      let result = {}
      columns.forEach(column => {
        if (!validatenull(column.group)) {
          let subResult = this.groupColumn(column.group, property)
          for (let key in subResult) {
            if (result[key] === undefined) {
              result[key] = subResult[key]
            } else {
              result[key].push(...subResult[key])
            }
          }
        } else if (!validatenull(column[property])) {
          if (result[column[property]] === undefined) {
            result[column[property]] = []
          }
          result[column[property]].push(column)
        }
      })
      return result
    },
    // 遍历tableOption, 查询所有的dicData或者叶子节点
    deepColumn (columns = [], type) {
      let result = []
      columns.forEach(column => {
        if (!validatenull(column.group)) {
          result.push(...this.deepColumn(column.group, type))
        } else if (type === 'column') {
          result.push(column)
        } else if (type === 'dic' && column.dicData) {
          if (column.dicData instanceof RemoteData) {
            result.push(column.dicData)
          } else {
            result.unshift(column.dicData)
          }
        }
      })
      return result
    },
    // 计算当前页面需要高度
    calcTableAutoHeight () {
      let parent = this.$parent.$el || {}
      let el = this.$el
      if (!el) { return }
      // console.log(el)
      let toolbar = el.querySelector('.crud-toolbar') || {}
      let pagination = {} // el.querySelector('.crud-pagination') || {}
      let diff = this.validData(parent.clientHeight, 0) - this.validData(toolbar.clientHeight, 0) - this.validData(pagination.clientHeight, 0) - 10
      if (diff < 270) {
        diff = 270
      }
      // console.log(parent.clientHeight, toolbar.clientHeight, diff)
      this.height = diff
    },
    // 单击行回调
    handleRowClick (row, event, column) {
      this.$emit('row-click', row, event, column)
    },
    // 双击行回调事件
    handleRowDblclick (row, event) {
      this.$emit('row-dblclick', row, event)
    },
    // 右键行回调
    handleRowContextmenu (row, event) {
      this.$emit('row-contextmenu', row, event)
    },
    // 多选行回调事件
    handleSelectionChange (val) {
      this.tableSelection = val
      this.$emit('selection-change', val)
    },
    // 排序回调
    handleSortChange (val) {
      !this.tableOption.selection && this.$refs['crudTable'].setCurrentRow()
      this.$emit('sort-change', val)
    },
    // 处理数据
    detail (row, column) {
      let result = row[column.prop]
      if (!validatenull(column.dicData)) {
        result = this.findDicLabel(column.dicData, result)
      }
      return result
    },
    // 初始化规则
    initRules () {
      this.tableFormRules = {}
      this.leafColumns.forEach(column => {
        if (column.rules) {
          this.tableFormRules[column.prop] = column.rules
        }
      })
    },
    // 初始化字典
    initDic () {
      if (validatenull(this.dicKeys)) {
        return Promise.resolve()
      }
      let remoteIndex = this.dicKeys.findIndex(dic => dic instanceof RemoteData)
      if (remoteIndex < 0) {
        remoteIndex = this.dicKeys.length
      }
      let remoteDics = remoteIndex < 0 ? [] : [...this.dicKeys.slice(remoteIndex)]
      let localDics = [...this.dicKeys.slice(0, remoteIndex)]
      // console.log('dic_array', remoteDics, localDics)
      let getLocalDic = new Promise(resolve => {
        this.$store.dispatch('GetDic', localDics).then(data => {
          resolve(data)
        })
      })
      return new Promise(resolve => {
        Promise.all([getLocalDic, ...remoteDics.map(dic => dic.findData())]).then(results => {
          // console.log('dic_result', results)
          let result = {}
          results.forEach(item => {
            Object.assign(result, item)
          })
          this.DIC = result
          console.log('DIC', this.DIC)
          resolve()
        })
      })
    },
    // 从总字典里面找出单字典
    findDic (dic) {
      if (dic instanceof RemoteData) {
        if (this.DIC[dic.authKey] && !(this.DIC[dic.authKey] instanceof Array)) {
          throw new Error('Dictionary Data must be an Array: ' + dic[RemoteData.KEY])
        }
        return this.validData(this.DIC[dic[RemoteData.KEY]], [])
      } else if (typeof dic === 'string') {
        return this.validData(this.DIC[dic], [])
      } else if (dic instanceof Array) {
        return dic
      }
    },
    // 从字典里找出值
    findDicLabel (dic, value) {
      let label = ''
      let dicData = this.findDic(dic)
      for (let item of dicData) {
        if (item.value === value) {
          label = item.label
          break
        }
      }
      return label || value
    },
    // 回调设置绑定的v-model
    setFormVal () {
      this.$emit('input', this.tableForm)
    },
    // 初始化表单
    initForm () {
      const list = this.leafColumns
      let form = {}
      list.forEach(ele => {
        let defaultValue = ele.meta ? ele.meta.value : null
        if (ele.type === 'checkbox' || ele.type === 'cascader') {
          form[ele.prop] = this.validData(defaultValue, [])
        } else if (ele.type === 'number') {
          form[ele.prop] = this.validData(defaultValue, 0)
        } else {
          form[ele.prop] = this.validData(defaultValue, '')
        }
      })
      this.tableForm = deepCopy(form)
    },
    // 分页点击页码事件
    handleCurrentChange (val) {
      this.$emit('current-change', val)
    },
    // 分页修改分页大小事件
    handleSizeChange (val) {
      this.$emit('size-change', val)
    },
    // 表单关闭事件
    handleDialogClose (done) {
      // 释放form表单
      this.tableForm = {}
      if (typeof this.beforeClose === 'function') {
        this.beforeClose(this.hide)
      } else {
        this.hide()
      }
    },
    // 新增
    rowAdd () {
      // form表单初始化
      this.initForm()
      this.dialogType = 0
      if (typeof this.beforeOpen === 'function') {
        this.beforeOpen(this.show)
      } else {
        this.show()
      }
    },
    // 编辑
    rowEdit (row) {
      this.tableForm = deepCopy(row)
      this.dialogType = 1
      if (typeof this.beforeOpen === 'function') {
        this.beforeOpen(this.show)
      } else {
        this.show()
      }
    },
    // 删除
    rowDel (row) {
      this.$emit('row-del', row)
    },
    // 表单更新事件
    rowUpdate () {
      this.$refs['tableForm'].validate(valid => {
        if (valid) {
          this.tableFormLoading = true
          this.$emit('row-update', deepCopy(this.tableForm), this.hide)
        }
      })
    },
    // 表单新增事件
    rowSave () {
      this.$refs['tableForm'].validate(valid => {
        if (valid) {
          this.tableFormLoading = true
          this.$emit('row-save', deepCopy(this.tableForm), this.hide)
        }
      })
    },
    // 显示表单
    show () {
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs['tableForm'].resetFields()
      })
    },
    // 隐藏表单
    hide (cancel) {
      this.tableFormLoading = false
      if (cancel !== false) {
        this.dialogVisible = false
      }
    }
  }
}
</script>
