<template>
  <section>
    <!-- 点击新增时的弹出框，这里面有上传文件的框框与选择操作系统的下拉框 -->
    <el-dialog :title="addUpdateTitle" :visible.sync="showDialog" ref="formData" :before-close="handleClose">
      <!-- 依据要求重新修改界面 -->
      <el-form ref="addEditForm" :model="formData" :rules="addUpdateRules" label-width="10px">
         <el-form-item required>
           <el-row :gutter="0">
             <el-col :span="12">
               <el-col :span="5">
                 <span>操作系统</span>
               </el-col>
               <el-col :span="18">
                <el-form-item prop="system">
                 <el-select v-model="formData.system" placeholder="请选择" @change="selectForUpload">
                 <el-option
                    v-for="item in systems"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
                </el-form-item>
               </el-col>
             </el-col>
             <el-col :span="12">
                <el-col :span="5">
                  <span>强制更新</span>
                </el-col>
                <el-col :span="18">
                   <el-form-item prop="forceUpdate">
                    <el-select v-model="formData.forceUpdate" placeholder="请选择">
                   <el-option label="是" value="1" select></el-option>
                   <el-option label="否" value="0"></el-option>
                </el-select>
                </el-form-item>
                </el-col>
             </el-col>
           </el-row>
         </el-form-item>
         <el-form-item required>
           <el-row>
             <el-col :span="12">
               <el-col :span="5">
               <span>机场名称</span>
               </el-col>
               <el-col :span="18">
               <el-form-item prop="airport">
               <el-select v-model="formData.airport" filterable multiple placeholder="请选择">
                 <el-option-group
                  v-for="group in airportsTypesChoose"
                  :key="group.label"
                  :label="group.label">
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.labelShow"
                    :value="item.value">
                      <span style="float: left">{{ item.text }}</span>
                      <span style="float: right">{{ item.code }}</span>
                  </el-option>
                </el-option-group>
              </el-select>
              </el-form-item>
              </el-col>
             </el-col>
             <el-col :span="12">
               <el-col :span="5">
                 <span>版本类型</span>
               </el-col>
               <el-col :span="18">
               <el-form-item prop="versionType">
                 <el-select v-model="formData.versionType" placeholder="请选择">
                 <el-option label="增量包" value="1"></el-option>
                 <el-option label="完整包" value="0" select></el-option>
                 </el-select>
                 </el-form-item>
               </el-col>
             </el-col>
           </el-row>
         </el-form-item>
         <el-form-item required>
           <el-row>
             <el-col :span="12">
               <el-col :span="5">
                  <span>生效时间</span>
               </el-col>
               <el-col :span="18">
               <el-form-item prop="updateStart">
                   <el-date-picker type="datetime" placeholder="请选择" v-model="formData.updateStart">
                   </el-date-picker>
                   </el-form-item>
               </el-col>
             </el-col>
             <el-col :span="12">
               <el-col :span="5">
                 <span>
                   版本描述
                 </span>
               </el-col>
               <el-col :span="18">
                 <el-input type="textarea" v-model="formData.versionDes" :disabled="false" placeholder="请输入版本描述"></el-input>
               </el-col>
             </el-col>
           </el-row>
         </el-form-item>
         <el-form-item>
           <el-row>
             <el-col :span="12" v-show="false">
               <el-col :span="5">
                 <span>
                   版本号
                 </span>
               </el-col>
               <el-col :span="18">
                 <el-input v-model="formData.versionNo" :disabled="true" placeholder="选择操作系统后自动生成版本号"></el-input>
               </el-col>
             </el-col>
             <el-col :span="12">
               <el-col :span="5">
                 <span>文件上传</span>
               </el-col>
               <el-col :span="18">
                  <el-upload
                    class="upload-demo"
                    ref="uploadPackage"
                    :action="uploadUrl"
                    :data="uploadParams"
                    :headers="headersUpload"
                    :file-list="fileList"
                    :on-change="onChange"
                    :before-upload="beforeUpload"
                    :auto-upload="false"
                    :multiple="true">
                    <el-row :gutter="20">
                      <el-col :span="8">
                        <el-button size="small" type="primary">选择文件</el-button>
                      </el-col>
                      <el-col :span="16">
                        <div>请上传对应的安装包</div>
                      </el-col>
                    </el-row>
                  </el-upload>
               </el-col>
             </el-col>
           </el-row>
         </el-form-item>
         <el-form-item v-show="false">
           <el-row>
             <el-col :span="12">
               <el-col :span="5">
                 <span>发布用户</span>
               </el-col>
               <el-col :span="18">
                  <el-input v-model="formData.publishUser" :disabled="true" placeholder="请输入发布用户"></el-input>
               </el-col>
             </el-col>
             <el-col :span="12">
               <el-col :span="5">
                 <span>下载路径</span>
               </el-col>
               <el-col :span="18">
                 <el-input v-model="formData.path" :disabled="true" placeholder="请输入下载路径"></el-input>
               </el-col>
              </el-col>
            </el-row>
         </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="judgeSystemFile">提交</el-button>
        </div>
    </el-dialog>
    <!-- 下载记录表 -->
    <el-dialog :title="downloadRecordVersionNo" :visible.sync="showDownloadTable" size="small">
      <template>
        <el-table
          :data="downloadRecords"
          max-height="300"
          border
          style="width: 100%">
          <el-table-column
            prop="airport"
            label="机场"
            min-width="150">
          </el-table-column>
          <el-table-column
            prop="deviceId"
            label="设备ID"
            width="150">
          </el-table-column>
          <el-table-column
            prop="downloadtime"
            label="下载时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="ip"
            label="IP地址"
            width="150">
          </el-table-column>
        </el-table>
      </template>
    </el-dialog>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
        <el-select v-model="filters.selectedSystem" placeholder="请选择操作系统类型">
        <el-option
          v-for="item in systems"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
        </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.airport" filterable placeholder="请输入机场名称" style="width: 250px!important">
            <el-option-group
              v-for="group in airportsTypesChoose"
              :key="group.label"
              :label="group.label">
              <el-option
                v-for="item in group.options"
                :key="item.value"
                :label="item.labelShow"
                :value="item.value">
                  <span style="float: left">{{ item.text }}</span>
                  <span style="float: right">{{ item.code }}</span>
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getVersions" icon="search">搜索</el-button>
          <el-button type="primary" @click="handleAdd" icon="plus">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="versions" highlight-current-row :height="tableHeight" v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <!-- 代表每一行所展开的展开行 -->
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="versionNo" label="版本号" width="270" sortable>
      </el-table-column>
      <el-table-column prop="system" label="适用系统" min-width="150">
      </el-table-column>
      <el-table-column prop="airport" label="目标机场" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="versionType" label="版本类型" width="100">
      </el-table-column>
      <el-table-column prop="versionDes" label="版本描述"  width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="publishUser" :formatter="getUserName" label="发布用户"  width="100">
      </el-table-column>
      <el-table-column prop="forceUpdate" label="强制更新"  width="100">
      </el-table-column>
      <el-table-column prop="publishDate" label="发布日期"  width="180">
      </el-table-column>
      <el-table-column prop="updateStart" label="生效时间"  width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template scope="scope">
          <el-button size="small" @click="handleDownloadRecords(scope.$index, scope.row)">记录</el-button>
          <!-- <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
          <el-button size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar-bottom">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--删除窗口-->
    <common-delete
        :to="API.delVersionInfoRecord().go"
        :callback="getVersions"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import axiosHelper from './../../common/js/axios-helper'
  export default {
    data () {
      return {
        filters: {
          selectedSystem: '',
          airport: ''
        },
        systems: [
          {
            id: 'Windows',
            name: '微软操作系统'
          },
          {
            id: 'Ubuntu',
            name: '乌班图操作系统'
          },
          {
            id: 'Android_RK3288',
            name: '安卓操作系统_RK3288'
          },
          {
            id: 'Android_Philips',
            name: '安卓操作系统_Philips'
          }
        ],
        selectedSystem: '',
        uploadParams: {
          system: '',
          forceUpdate: '',
          airport: '',
          versionType: '',
          publishDate: '',
          updateStart: '',
          versionNo: '',
          versionDes: '',
          publishUser: '',
          path: ''
        },
        showDownloadTable: false,
        versions: [],
        total: 0,
        pageNumber: 1,
        pageSize: 10,
        listLoading: false,
        sels: [],
        airportsSelect: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        showDialog: false,
        uploadUrl: '',
        fileName: '',
        // 表格的高度的修改
        tableHeight: 495,
        fileList: [],
        // 存放下载记录的变量
        downloadRecords: [],
        // 将下载记录与版本信息整合后的变量，也许没用
        dataDownloadAndVersions: [],
        // expandinline的值
        expandInline: true,
        // 用于获取用户的名称
        allUsersData: [],
        // 新增编辑需要的字段
        addUpdateTitle: '',
        // 判断是新增还是编辑
        isAdd: Boolean,
        // 是否显示上传文件的item
        showUploadItem: Boolean,
        // 在下载记录表上面显示是哪个版本号的下载记录表
        downloadRecordVersionNo: '',
        // 用于保存请求的头部信息
        headersUpload: null,
        // 显示在目标机场上的文字
        showOverflowTooltip: true,
        // 版本号windows
        Windows: null,
        // Windows携带的文件
        resles: null,
        // 文件信息列表
        fileListJudge: [],
        // 文件的列表，用于操作系统与所选文件的判断
        fileListSystem: [],
        // 表单的数据
        formData: {
          system: '',
          versionNo: '',
          airport: '',
          versionType: '',
          versionDes: '',
          publishDate: '',
          publishUser: '',
          updateStart: '',
          path: '',
          forceUpdate: ''
        },
        // 表单验证
        addUpdateRules: {
          system: [
             { required: true, message: '请选择操作系统！' }
          ],
          forceUpdate: [
             { required: true, message: '请选择是否强制更新！' }
          ],
          airport: [
             { required: true, message: '请选择机场名称！' }
          ],
          versionType: [
             { required: true, message: '请选择版本类型' }
          ],
          updateStart: [
             { required: true, message: '请选择生效时间' }
          ]
        },
        API: API
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonDelete: commonDelete
      // uploadPlus: uploadPlus
    },
    methods: {
      // 点击分页刷新
      handleCurrentChange (val) {
        this.pageNumber = val
        this.getVersions()
      },
      // 获取版本信息列表
      getVersions () {
        let para = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          system: this.filters.selectedSystem,
          airport: this.filters.airport
        }
        this.listLoading = true
        API.getVersionInfoList().go(para).then((data) => {
          if (data.ok) {
            for (var s = 0; s < data.attr.data.list.length; s++) {
              data.attr.data.list[s].forceUpdate = this.getForceUpdateName(data.attr.data.list[s].forceUpdate)
              data.attr.data.list[s].versionType = this.getVersionTypeName(data.attr.data.list[s].versionType)
              data.attr.data.list[s].airport = this.getAirportName(data.attr.data.list[s].airport)
            }
            this.versions = data.attr.data.list
            this.total = data.attr.data.pager.recordCount
            this.getDownloadRecords()
            this.listLoading = false
            this.filters.selectedSystem = ''
          } else {
            this.$notify(util.notifyBody(false, data.msg))
            this.filters.selectedSystem = ''
            this.filters.airport = ''
            this.listLoading = false
          }
        })
      },
      // 点击复选框的时候发生的变化
      selsChange: function (sels) {
        this.sels = sels
      },
      // 更多操作
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
      // 显示新增界面，点击新增后出现的页面
      handleAdd: function () {
        this.reset()
        this.showUploadItem = true
        this.addUpdateTitle = '新增'
        this.formData.publishUser = localStorage.getItem('AdminId')
        for (var c = 0; c < this.allUsersData.length; c++) {
          if (this.formData.publishUser === this.allUsersData[c].id) {
            this.formData.publishUser = this.allUsersData[c].name
          }
        }
        // 这个字符串是微软操作系统
        var selectedSystem = 'Windows'
        this.selectForUpload(selectedSystem)
        this.formData.forceUpdate = '1'
        this.formData.versionType = '0'
        this.formData.system = 'Windows'
        this.formData.updateStart = new Date()
        this.isAdd = true
        this.showDialog = true
      },
      // 选择操作系统，并且进行赋值
      selectForUpload: function (val) {
        this.uploadUrl = '/raiis/appversions/uploadfile'
        this.formData.system = val
      },
      // 上传成功后，所进行的一些操作
      beforeUpload: function (file) {
        let fd = new FormData()
        this.fileListJudge.push(file)
        fd.append('airport', this.uploadParams.airport)
        fd.append('forceUpdate', this.uploadParams.forceUpdate)
        fd.append('path', this.uploadParams.path)
        fd.append('publishDate', this.uploadParams.publishDate)
        fd.append('publishUser', this.uploadParams.publishUser)
        fd.append('system', this.uploadParams.system)
        fd.append('updateStart', this.uploadParams.updateStart)
        fd.append('versionDes', this.uploadParams.versionDes)
        fd.append('versionNo', this.uploadParams.versionNo)
        fd.append('versionType', this.uploadParams.versionType)
        if (this.fileListJudge.length === 2) {
          for (var i = 0; i < this.fileListJudge.length; i++) {
            // 多个文件的key是files
            fd.append('files', this.fileListJudge[i])
          }
          API.submitPostALLFile(fd).then(res => {
            console.log('res', res)
            console.log('res.data.ok', res.data.ok)
          if (res.data.ok === true) {
            this.$notify(util.notifyBody(res.data.ok, res.data.msg))
            this.filters.selectedSystem = ''
            this.filters.airport = ''
            this.fileList = []
            this.getVersions()
            this.loading = false
          } else {
            this.filters.selectedSystem = ''
            this.filters.airport = ''
            this.$notify(util.notifyBody(res.data.ok, res.data.msg))
            this.fileList = []
            this.reset()
            this.loading = false
          }
        })
        this.fileListJudge = []
        }
        if (this.fileListJudge.length === 1 && this.fileListJudge[0].name.substring(0, 7) !== 'Windows' && this.fileListJudge[0].name.substring(0, 8) !== 'RELEASES') {
          // 单个文件的key是file还是files
          fd.append('files', file)
          API.submitPostALLFile(fd).then(res => {
            console.log('res', res)
            console.log('res.data.ok', res.data.ok)
          if (res.data.ok === true) {
            // this.loading = false
            this.$notify(util.notifyBody(res.data.ok, res.data.msg))
            // this.showDialog = false
            this.filters.selectedSystem = ''
            this.filters.airport = ''
            this.fileList = []
            this.getVersions()
            this.loading = false
          } else {
            // this.loading = false
            this.filters.selectedSystem = ''
            this.filters.airport = ''
            this.$notify(util.notifyBody(res.data.ok, res.data.msg))
            this.fileList = []
            this.reset()
            this.loading = false
          }
        })
        this.fileListJudge = []
        }
        return false
      },
      // 文件转台改变的时候的钩子
      onChange: function (file, fileList) {
        this.fileListSystem = fileList
        if (fileList.length === 2) {
          if (fileList[0].name.substring(0, 7) === 'Windows') {
            this.formData.versionNo = fileList[0].name.substring(0, 24)
            this.formData.path = fileList[0].name.substring(0, 24) + '.nupkg'
          }
          if (fileList[0].name.substring(0, 8) === 'RELEASES') {
            this.formData.versionNo = fileList[1].name.substring(0, 24)
            this.formData.path = fileList[1].name.substring(0, 24) + '.nupkg'
          }
        }
        if (fileList.length === 1) {
          if (fileList[0].name.substring(0, 6) === 'Ubuntu') {
          this.formData.versionNo = file.name.substring(0, 23)
          this.formData.path = this.formData.versionNo + '.zip'
          }
          if (fileList[0].name.substring(0, 15) === 'Android_Philips') {
            console.log('fileList[0].name.substring(0, 15)', fileList[0].name.substring(0, 15))
            this.formData.versionNo = file.name.substring(0, 32)
            console.log('file.name.substring(0, 32)', file.name.substring(0, 32))
            this.formData.path = this.formData.versionNo + '.apk'
          }
          if (fileList[0].name.substring(0, 14) === 'Android_RK3288') {
            console.log('fileList[0].name.substring(0, 14)', fileList[0].name.substring(0, 14))
            this.formData.versionNo = file.name.substring(0, 31)
            console.log('file.name.substring(0, 31)', file.name.substring(0, 31))
            this.formData.path = this.formData.versionNo + '.apk'
          }
        }
        // this.fileList = []
      },
      // 对新增的内容进行上传
      submit: function () {
        // 连同参数和安装包一起上传
        if (this.isAdd === true) {
          // 配置头部信息
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
          this.headersUpload = axiosHelper.generatAuthHeader()
          this.loading = true
          this.uploadParams.system = this.formData.system
          this.uploadParams.versionNo = this.formData.versionNo
          this.uploadParams.airport = this.formData.airport.join(',')
          this.uploadParams.versionType = this.formData.versionType
          this.uploadParams.versionDes = this.formData.versionDes
          var nowTime = new Date()
          this.uploadParams.publishDate = this.dateFormated(nowTime)
          this.uploadParams.publishUser = this.formData.publishUser
          this.uploadParams.updateStart = this.dateFormated(this.formData.updateStart)
          this.uploadParams.path = this.formData.path
          this.uploadParams.forceUpdate = this.formData.forceUpdate
          for (var h = 0; h < this.allUsersData.length; h++) {
            if (this.uploadParams.publishUser === this.allUsersData[h].name) {
              this.uploadParams.publishUser = this.allUsersData[h].id
            }
          }
          this.showDialog = false
          this.$refs.uploadPackage.submit()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
        if (this.isAdd === false) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            var oldValue = {
              system: '',
              versionNo: '',
              airport: '',
              versionType: '',
              versionDes: '',
              publishDate: '',
              publishUser: '',
              updateStart: '',
              path: '',
              forceUpdate: ''
            }
            let para = { newValue: this.formData, oldValue: oldValue }
            if (para.newValue.updateStart) {
              para.newValue.updateStart = this.dateFormated(para.newValue.updateStart)
            }
            if (para.newValue.publishDate) {
              para.newValue.publishDate = this.dateFormated(para.newValue.publishDate)
            }
            for (var l = 0; l < this.allUsersData.length; l++) {
              if (para.newValue.publishUser === this.allUsersData[l].name) {
                para.newValue.publishUser = this.allUsersData[l].id
              }
            }
            API.updateVersionInfoRecord().go(para).then((res) => {
              if (res.ok) {
                this.loading = false
                this.$notify(util.notifyBody(res.ok, res.msg))
                this.showDialog = false
                this.getVersions()
              } else {
                this.loading = false
                this.$notify(util.notifyBody(res.ok, res.msg))
                this.fileList = []
                this.reset()
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      },
      // 转换时间格式
      dateFormated: function (date) {
        var dataFormated = null
        var moment = null
        moment = require('moment')
        dataFormated = moment(date).format('YYYY-MM-DD HH:mm:ss')
        return dataFormated
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        var editRow = null
        editRow = util.deepCopy(row)
        editRow.airport = this.getAirportValue(editRow.airport)
        editRow.forceUpdate = this.getForceUpdateValue(editRow.forceUpdate)
        editRow.versionType = this.getVersionTypeValue(editRow.versionType)
        this.formData = editRow
        this.formData.publishUser = editRow.publishUser
        for (var c = 0; c < this.allUsersData.length; c++) {
          if (this.formData.publishUser === this.allUsersData[c].id) {
            this.formData.publishUser = this.allUsersData[c].name
          }
        }
        this.addUpdateTitle = '编辑'
        this.isAdd = false
        this.showUploadItem = false
        this.showDialog = true
      },
      // 显示下载记录表
      handleDownloadRecords: function (index, row) {
        this.downloadRecordVersionNo = '下载记录——' + row.versionNo
        this.showDownloadTable = true
        this.downloadRecords = row.downloadRecords
      },
      // 格式化用户名
      getUserName: function (row, column, cellValue) {
        for (var k = 0; k < this.allUsersData.length; k++) {
          if (cellValue === this.allUsersData[k].id) {
            return this.allUsersData[k].name
          }
        }
      },
      // 关闭这个弹出框的时候所触发的事件
      handleClose: function (done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {})
      },
      // 获取机场列表
      getAirportList: function () {
        this.airports = this.$cache.array.airports
        this.airportsSelect = util.typedAirport(this.airports)
        // 筛选出登录用户的订阅机场
        var resultCache = [
          {
            label: '国际',
            labelkey: 'I',
            options: []
          },
          {
            label: '国内',
            labelkey: 'D',
            options: []
          }
        ]
        // 对下拉框的数据进行分组
        for (var a = 0; a < this.airportsSelect.length; a++) {
          if (this.airportsSelect[a].nature === 'I') {
            resultCache[0].options.push(this.airportsSelect[a])
          }
          if (this.airportsSelect[a].nature === 'D') {
            resultCache[1].options.push(this.airportsSelect[a])
          }
        }
        this.airportsTypesChoose = resultCache
        this.getAllUserList()
      },
      // 获取用户列表
      getAllUserList: function () {
        let param = {
          pageNumber: 1,
          pageSize: 10000,
          name: '',
          locked: null
        }
        API.getUserListPage().go(param).then((data) => {
          if (data.ok) {
            this.allUsersData = data.attr.data.list
            this.getVersions()
          } else {
            this.$notify(util.notifyBody(false, data.msg))
          }
        })
      },
      // 为了在table里将机场代码，版本类型，强制转换改成中文
      // 转换目标机场的名称
      getAirportName: function (data) {
        if (data === null) {
          return
        }
        var result = ''
        data = data.split(',')
        for (var i = 0; i < data.length; i++) {
          for (var j = 0; j < this.airportsSelect.length; j++) {
            if (data[i] === this.airportsSelect[j].value) {
              result = this.airportsSelect[j].text + '；' + result
            }
          }
        }
        return result
      },
      // 格式化目标机场为了得到toottips的数据
      getAirportFormatteredName: function (data) {
        var result = data.airport.substring(0, 7) + '...'
        return result
      },
      // 转换版本类型
      getVersionTypeName: function (data) {
        if (data === '1') {
          return '增量包'
        }
        if (data === '0') {
          return '完整包'
        }
      },
      // 是否强制更新
      getForceUpdateName: function (data) {
        if (data === '1') {
          return '是'
        }
        if (data === '0') {
          return '否'
        }
      },
      // 为了在编辑的时候将机场代码，版本类型，强制转换改成value
      // 目标机场名称转换成value
      getAirportValue: function (data) {
        for (var n = 0; n < this.airportsSelect.length; n++) {
          if (data === this.airportsSelect[n].text) {
            return this.airportsSelect[n].value
          }
        }
      },
      // 将版本类型转换成value
      getVersionTypeValue: function (data) {
        if (data === '增量包') {
          return '1'
        }
        if (data === '完整包') {
          return '0'
        }
      },
      // 将是否强制更新转换成value
      getForceUpdateValue: function (data) {
        if (data === '是') {
          return '1'
        }
        if (data === '否') {
          return '0'
        }
      },
      // 获取下载记录
      getDownloadRecords: function () {
        API.getDownloadRecords().go().then((res) => {
          this.dataDownloadAndVersions = res.attr.data.list
          // 将下载记录的id与版本信息的id对应并且在versions里面创建downloadrecords属性
          this.versionsCreateDownloadRecords(this.versions, this.dataDownloadAndVersions)
        })
      },
      // 创建属性
      versionsCreateDownloadRecords: function (versions, records) {
        if (records === undefined) {
          return
        }
        for (var a = 0; a < versions.length; a++) {
          versions[a]['downloadRecords'] = []
          for (var j = 0; j < records.length; j++) {
            if (versions[a].id === records[j].versionId) {
              versions[a]['downloadRecords'].push(records[j])
            }
          }
        }
        this.versions = versions
      },
      cancel: function () {
        this.showDialog = false
      },
      reset: function () {
        this.formData = null
        this.formData = {
          system: '',
          versionNo: '',
          airport: [],
          versionType: '',
          versionDes: '',
          publishDate: '',
          publishUser: '',
          updateStart: '',
          path: '',
          forceUpdate: ''
        }
        this.fileList = []
      },
      // 这个函数用于判断操作系统和所选的文件是否相匹配
      judgeSystemFile: function () {
        // console.log(1)
        console.log('this.fileListSystem', this.fileListSystem)
        this.$refs['addEditForm'].validate((valid) => {
        if (valid) {
              // 操作系统是微软
            if (this.formData.system === 'Windows') {
              // 判断数量
              if (this.fileListSystem.length !== 2) {
                this.$notify({
                  title: '错误',
                  message: '所选文件应为两个',
                  type: 'error'
                })
                this.fileList = []
              }
              // Windows的两个上传文件的第一个上传文件不是windows或者resles
              if (this.fileListSystem.length === 2) {
                if (this.fileListSystem[0].name.substring(0, 7) !== 'Windows' && this.fileListSystem[0].name.substring(0, 8) !== 'RELEASES') {
                this.$notify({
                  title: '错误',
                  message: '所选操作系统与文件类型不匹配',
                  type: 'error'
                })
                this.fileList = []
                }
                // 如果上传文件第一个是windows而第二个不是resles
                if (this.fileListSystem[0].name.substring(0, 7) === 'Windows') {
                  if (this.fileListSystem[1].name.substring(0, 8) !== 'RELEASES') {
                    this.$notify({
                      title: '错误',
                      message: '所选操作系统与文件类型不匹配',
                      type: 'error'
                    })
                    this.fileList = []
                  }
                }
                // 如果上传文件第一个是resles而第二个不是windows
                if (this.fileListSystem[0].name.substring(0, 8) === 'RELEASES') {
                  if (this.fileListSystem[1].name.substring(0, 7) !== 'Windows') {
                    this.$notify({
                      title: '错误',
                      message: '所选操作系统与文件类型不匹配',
                      type: 'error'
                    })
                    this.fileList = []
                  }
                }
                // 只有正确的才能往下面走
                if ((this.fileListSystem[0].name.substring(0, 7) === 'Windows' && this.fileListSystem[1].name.substring(0, 8) === 'RELEASES') || (this.fileListSystem[0].name.substring(0, 8) === 'RELEASES' && this.fileListSystem[1].name.substring(0, 7) === 'Windows')) {
                  console.log(6666)
                  this.submit()
                }
              }
            }
            // 操作系统是乌班图
            if (this.formData.system === 'Ubuntu') {
              if (this.fileListSystem.length !== 1) {
                this.$notify({
                  title: '错误',
                  message: '所选文件应为一个',
                  type: 'error'
                })
                this.fileList = []
              }
              if (this.fileListSystem.length === 1) {
                if (this.fileListSystem[0].name.indexOf(this.formData.system) < 0) {
                  this.$notify({
                    title: '错误',
                    message: '所选操作系统与文件类型不匹配',
                    type: 'error'
                  })
                  this.fileList = []
                }
                if ((this.fileListSystem[0].name.indexOf(this.formData.system) > 0 || this.fileListSystem[0].name.indexOf(this.formData.system) === 0) && this.fileListSystem.length === 1) {
                  this.submit()
                }
              }
            }
            // 操作系统是安卓PK
            if (this.formData.system === 'Android_RK3288') {
              var systemRK3288 = this.formData.system.substring(8, 14)
              if (this.fileListSystem.length !== 1) {
                this.$notify({
                  title: '错误',
                  message: '所选文件应为一个',
                  type: 'error'
                })
                this.fileList = []
              }
              if (this.fileListSystem.length === 1) {
                if (this.fileListSystem[0].name.indexOf(systemRK3288) < 0) {
                  this.$notify({
                    title: '错误',
                    message: '所选操作系统与文件类型不匹配',
                    type: 'error'
                  })
                  this.fileList = []
                }
                if ((this.fileListSystem[0].name.indexOf(systemRK3288) > 0 || this.fileListSystem[0].name.indexOf(systemRK3288) === 0) && this.fileListSystem.length === 1) {
                  this.submit()
                }
              }
            }
            // 操作系统是安卓PHI
            if (this.formData.system === 'Android_Philips') {
              var systemPhilips = this.formData.system.substring(8, 15)
              if (this.fileListSystem.length !== 1) {
                this.$notify({
                  title: '错误',
                  message: '所选文件应为一个',
                  type: 'error'
                })
                this.fileList = []
              }
              if (this.fileListSystem.length === 1) {
                if (this.fileListSystem[0].name.indexOf(systemPhilips) < 0) {
                  this.$notify({
                    title: '错误',
                    message: '所选操作系统与文件类型不匹配',
                    type: 'error'
                  })
                  this.fileList = []
                }
                if ((this.fileListSystem[0].name.indexOf(systemPhilips) > 0 || this.fileListSystem[0].name.indexOf(systemPhilips) === 0) && this.fileListSystem.length === 1) {
                  this.submit()
                }
              }
            }
         }
       })
      }
    },
    mounted () {
      this.getAirportList()
    }
  }

</script>

<style scoped>
  .el-dialog {
    position: absolute;
    left: 50%;
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
    background: #fff;
    border-radius: 2px;
    box-shadow: 0 1px 3px rgba(0,0,0,.3);
    box-sizing: border-box;
    margin-bottom: 50px;
    width: 75%!important;
  }
    .el-date-editor--date {
      width: 100%!important
    }
    .el-select {
      width: 100%!important
    }
    .el-input {
      width: 100%!important
    }
    .el-textarea {
      width: 100%!important
    }
</style>
