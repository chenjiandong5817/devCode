<template>
  <el-form :model="templateConf" label-width="80px" label-position="top">
    <el-form-item label="编号" prop="timestamp" v-show="false">
      <el-input v-model="templateConf.timestamp" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="类型" prop="type">
      <el-input v-model="templateConf.type" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="" prop="hiddenHeader">
      <span>隐藏标题&nbsp;</span>
      <el-switch
        v-model="templateConf.hiddenHeader"
        active-color="#13ce66"
        inactive-color="#ff4949">
      </el-switch>
    </el-form-item>
    <el-form-item label="样式" prop="bodyStyle" label-width="80px">
      <el-input type="textarea" :rows="5" :value="JSON.stringify(templateConf.bodyStyle, null, 2)" @input="(value) => inputStr2Json(templateConf, 'bodyStyle', value)"></el-input>
    </el-form-item>
    <el-form-item>
      <fieldset>
        <legend>数据源</legend>
        <div>
          <el-form-item prop="type" v-if="templateConf.dataSource !== undefined">
            <el-select v-model="templateConf.dataSource" filterable placeholder="选择数据源">
              <template v-for="item in DataSources">
                <el-option :key="item.id" :label="item.description" :value="item.id"></el-option>
              </template>
            </el-select>
          </el-form-item>
          <el-form-item prop="type" v-if="templateConf.singleData !== undefined">
            <span class="form-item-label">单数据:</span>
            <el-radio-group v-model="templateConf.singleData">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item  prop="type" v-if="templateConf.mainPager !== undefined">
            <span class="form-item-label">主分页:</span>
            <el-radio-group v-model="templateConf.mainPager">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
    <el-form-item>
      <fieldset>
        <legend>列项</legend>
        <div>
          <div v-if="1 === 2 && gridColumns.length > 0"><span class="prompt-span-star">* </span><span class="prompt-span-text">选择模板后以模板中选择的字段名为准</span></div>
          <el-collapse accordion v-if="gridColumns && gridColumns.length > 0">
            <el-collapse-item v-for="(column, index) in gridColumns" :key="column.name">
              <template slot="title">
               {{ `${index + 1}` }}
              </template>
              <!-- <el-form-item label="字段名">
                <el-select v-model="column.name" filterable clearable placeholder="选择字段">
                  <template v-for="columnName in dataSourceColumnNames">
                    <el-option :key="columnName" :label="columnName" :value="columnName"></el-option>
                  </template>
                </el-select>
              </el-form-item> -->
              <el-form-item label-width="80px">
                <div slot="label">
                  <span>标题文本</span>
                  <el-popover
                    :ref="`popover-header-text-${index}`"
                    placement="top-start"
                    width="200"
                    trigger="hover">
                    <div>
                      多语言切换请可以配置以下格式：
                      <pre v-html="JSON.stringify({'zh-cn': '标题', 'en-ww': 'title', 'ja-jp': 'タイトル'}, null, 4)"></pre>
                      正常显示标题直接配置：<br>
                      字符串： "标题"<br>
                      数字： 12345
                    </div>
                    
                    <i class="el-icon-warning" slot="reference"></i>
                  </el-popover>
                </div>
                <el-input type="textarea" :rows="2" :value="JSON.stringify(column.label, null, 2)" @input="(value) => inputStr2Json(column, 'label', typeof value === 'number' ? String(value) : value)"></el-input>
              </el-form-item>
              <el-form-item label="标题样式" label-width="80px">
                <el-input type="textarea" :rows="3" :value="JSON.stringify(column.headerStyle, null, 2)" @input="(value) => inputStr2Json(column, 'headerStyle', value)"></el-input>
              </el-form-item>
              <el-form-item label="列样式" label-width="80px">
                <el-input type="textarea" :rows="3" :value="JSON.stringify(column.style, null, 2)" @input="(value) => inputStr2Json(column, 'style', value)"></el-input>
              </el-form-item>
              <el-form-item label="模板">
                <div style="display: inline-block;">
                  <el-select v-model="column.templateType" filterable clearable placeholder="选择模板" @change="(value) => handleChangeSubTemplate(column, value)">
                    <template v-for="item in componentTemplate">
                      <el-option :key="item.name" :label="item.dt" :value="item.name"></el-option>
                    </template>
                  </el-select>
                </div>
                <div style="display: inline-block;">
                  <el-button type="primary" icon="setting" plain :disabled="!Boolean(column.templateType)"  @click="handleClickSubTemplateSetting(column)"></el-button>
                </div>
              </el-form-item>
            </el-collapse-item>
          </el-collapse>
        </div>
      </fieldset>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleEdit">修改</el-button>
      <el-button type="danger" @click="$parent.handleDelete">删除</el-button>
      <el-button type="default" @click="addDataSource(templateConf)" plain v-if="!haveDataSource(templateConf)">添加数据源</el-button>
      <el-button type="danger" @click="deleteDataSource(templateConf)" plain v-else>删除数据源</el-button>
      <el-button type="primary" @click="handleAddColumn">添加列</el-button>
    </el-form-item>

    <!-- 编辑子模板选项 -->
    <el-dialog
      title="配置模板选项"
      :visible.sync="dialogVisible"
      size="small">
      <div>
        <default-marquee-text-editor ref="dialogSubTemplate" :conf="dialogTarget" :dataSourceKeys="dataSourceColumnNames" :submitCallback="handleCallbackSubTemplate"></default-marquee-text-editor>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="() => $refs.dialogSubTemplate.handleSubmit()">确 定</el-button>
      </span>
    </el-dialog>
  </el-form>
</template>
<script>
  import Tools from './../../../common/js/template-tools'
  import DataSources from './../datasource'
  import Templates from './../templates'
  // import debounce from 'throttle-debounce/debounce'
  import { haveDataSource, addDataSource, deleteDataSource, getDataSourceOptionById, inputStr2Json } from './editor-helper'
  import DefaultMarqueeTextEditor from './DefaultMarqueeTextEditor'
  export default {
    props: {
      conf: Object,
      dataSource: Object
    },
    components: {
      DefaultMarqueeTextEditor
    },
    data () {
      return {
        // 工具方法
        haveDataSource,
        addDataSource,
        deleteDataSource,
        DataSources,
        // 变量
        dialogVisible: false,
        dialogTarget: null,
        alertTag: false,
        templateConf: {},
        gridColumns: [],
        componentTemplate: [
          { name: 'Text', dt: '文本' },
          { name: 'DefaultMarqueeText', dt: '滚动文本' },
          { name: 'FixedLeftMarquee', dt: '固定左部滚动文本' }
        ]
      }
    },
    computed: {
      currentDataSource () {
        if (haveDataSource(this.templateConf) && this.templateConf.dataSource !== '') {
          return getDataSourceOptionById(this.templateConf.dataSource)
        } else {
          return this.dataSource
        }
      },
      dataSourceColumnNames () {
        if (this.currentDataSource) {
          return Object.keys(this.currentDataSource.exampleData[0])
        } else {
          return []
        }
      }
    },
    methods: {
      // input事件转化数据格式为json
      inputStr2Json: inputStr2Json,
      // 修改按钮点击事件, 单独
      handleEdit () {
        this.coverGridColumn2Template()
        this.$parent.$emit('editTemplate', Tools.deepCopy(this.templateConf))
      },
      coverGridColumn2Template () {
        let gridColumns = Tools.deepCopy(this.gridColumns)
        for (let column of gridColumns) {
          column.children = column.template ? [Tools.deepCopy(column.template)] : []
          delete column.template
          delete column.templateType
        }
        this.templateConf.columns = gridColumns
        console.log(this.templateConf)
      },
      // 修改选中子模板
      handleChangeSubTemplate (column, value) {
        if (!value) {
          delete column.template
        } else {
          column.template = Templates(value)
        }
        console.log(column)
      },
      // 点击子模板配置
      handleClickSubTemplateSetting (column) {
        console.log(column)
        this.dialogTarget = column.template
        this.$nextTick(() => {
          this.dialogVisible = true
        })
      },
      handleCallbackSubTemplate (conf) {
        this.dialogVisible = false
        for (let column of this.gridColumns) {
          if (column.template && column.template.timestamp === conf.timestamp) {
            column.template = Tools.deepCopy(conf)
            break
          }
        }
        console.log(this.gridColumns)
      },
      // 添加列
      handleAddColumn () {
        if (!haveDataSource(this.templateConf) || this.templateConf.dataSource === '') {
          this.$message({
            message: '请先选择数据源',
            type: 'error'
          })
          return
        }
        // 增加列项
        this.gridColumns.push({
          timestamp: 'GridColumn_' + new Date().getTime(),
          type: 'GridColumn',
          name: '',
          label: {},
          style: {},
          headerStyle: {},
          templateType: ''
        })
      }
    },
    mounted () {
      this.templateConf = Tools.deepCopy(this.conf)
      // 事先遍历以下列项模板类型
      if (this.templateConf.columns) {
        this.templateConf.columns.forEach(item => {
          if (item.children) {
            let column = {
              timestamp: 'GridColumn_' + new Date().getTime(),
              name: item.name,
              label: Tools.deepCopy(item.label),
              style: Tools.deepCopy(item.style),
              headerStyle: Tools.deepCopy(item.headerStyle),
              templateType: (item.children && item.children[0]) ? item.children[0].type : null,
              template: Tools.deepCopy((item.children && item.children[0]) ? item.children[0] : null)
            }
            this.gridColumns.push(column)
          }
        })
      }
      console.log(this.templateConf, this.gridColumns)
    }
  }
</script>
<style>
  .prompt-span-star {
    color: #ff0000;
    font-size: 12px;
  }
  .prompt-span-text {
    color: #828282;
    font-size: 12px;
  }
  .form-item-label {
    width: 55px;
    text-align: right;
    display: inline-block;
  }
  .form-item-bottomline {
    clear: both;
    background-color: #c1bfbf;
    margin: 8px 16px;
    line-height: 1px;
    height: 1px;
  }
  .form-item-bottomline::after {
    content: " ";
  }
</style>
