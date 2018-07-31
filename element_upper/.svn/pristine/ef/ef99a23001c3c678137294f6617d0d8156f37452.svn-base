/*
 * @Author: ylj
 * @Date: 2017-11-29 22:02:58
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 21:01:43
 * @Content Excel导入模板
 */

<template>
<section class="ExcelUpdateClss">
  <div id="uploadDiv">
    <el-col :span="24">
      <el-upload
        class="upload-demo"
        ref="excleImport"
        :action="action"
        :auto-upload="autoUpload"
        :multiple="multiple"
        :accept="fileType"
        :data="importPara"
        :file-list="fileList"
        :on-change="importChange"
        :before-upload="beforeUpload"
        :on-preview="handleExcPreview"
        :on-remove="handleExcRemove"
        style="width: 100%">
        <el-row :gutter="10">
          <el-col :span="24">
            <el-button size="small" type="primary">选择文件</el-button>
            <span style="font-size: 10px">请选择上传文件</span>
          </el-col>
        </el-row>
      </el-upload>
    </el-col>
  </div>
  <div id="previewDiv">
    <el-dialog :title="excelTitle" v-model="tableVisible" :close-on-click-modal="false" @close="excelTableClose" :modal="false" :size="previewSize" :class="previewClss">
      <common-table ref="excelTable"></common-table>
    </el-dialog>
  </div>
</section>
</template>

<script>
import Util from '../common/js/util'
import impExcel from '../common/js/importExcel'
import commonTable from './Table'

export default {
  props: {
    action: {
      type: String,
      default: '/raiis/src/views/fids/excelfile'
    },
    autoUpload: {
      type: Boolean,
      default: false
    },
    multiple: {
      type: Boolean,
      default: false
    },
    fileType: {
      type: String,
      default: '.xls,.xlsx'
    },
    handleChange: {
      type: Function,
      default: function (file, fileList) {
        this.importChange(file, fileList)
      }
    },
    beforeUpload: {
      type: Function,
      default: function () {}
    },
    handlePreview: {
      type: Function,
      default: function (file) {
        this.handleExcPreview(file)
      }
    },
    handleRemove: {
      type: Function,
      default: function (file, fileList) {
        this.handleExcRemove(file, fileList)
      }
    },
    previewSize: {
      type: String,
      default: 'large'
    },
    previewClss: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      tableVisible: false,
      excelTitle: '',
      importPara: {},
      fileList: [],
      titleMap: {},
      titleNum: 0
    }
  },
  components: {
    commonTable: commonTable
  },
  methods: {
    initData () {
      this.tableVisible = false
      this.excelTitle = ''
      this.importPara = {}
      this.fileList = []
      this.titleMap = {}
      this.titleNum = 0
    },
    setTitleInfo (titleMap, titleNum) {
      this.titleMap = Util.deepCopy(titleMap)
      this.titleNum = titleNum
    },
    setImportPara (ImportPara) {
      this.importPara = Util.deepCopy(ImportPara)
    },
    getImportPara () {
      return this.importPara
    },
    getFileList () {
      return Util.deepCopy(this.fileList)
    },
    excelTableClose (convertedData) {
      this.$refs['excelTable'].clearAllData()
      this.tableVisible = false
    },
    importChange (file, fileList) {
      impExcel.importFiles(file, this.titleMap, this.titleNum).then(res => {
        if (res !== undefined && res.ok) {
          this.fileList.push(file)
          this.importPara['excelData'] = res.resultJson
          this.$emit('getFileList', this.fileList)
        } else {
          // 文件上传类型出错，从上传列表中删除
          this.$message({
            type: 'info',
            message: res.msg
          })
        }
      })
    },
    handleExcPreview (file) {
      this.tableVisible = true
      let tableKey = []
      for (var key in this.titleMap) {
        let s = {}
        s['name'] = key
        s['value'] = this.titleMap[key]
        tableKey.push(s)
      }
      this.excelTitle = file.raw.name
      this.$nextTick(() => {
        this.$refs['excelTable'].tableData = Util.deepCopy(this.importPara['excelData'])
        this.$refs['excelTable'].tableKey = tableKey
      })
    },
    handleExcRemove (file, fileList) {
      let list = this.fileList.filter(item => {
        return JSON.stringify(item.raw) !== JSON.stringify(file.raw)
      })
      this.fileList = list
    }
  }
}
</script>

<style lang="scss">
.ExcelUpdateClss {
  .el-dialog__body {
    padding-top: 10px!important;
  }
  .el-dialog__header {
    padding: 10px 10px 0;
  }
}
</style>
