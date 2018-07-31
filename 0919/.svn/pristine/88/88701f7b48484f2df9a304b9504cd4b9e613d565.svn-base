/*
 * @Author: ylj
 * @Date: 2018-02-23 22:09:03
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-06 10:15:12
 */
<template>
<div>
  <el-row :gutter="20">
    <el-col :span="24">
      <!--<el-form-item label="" prop="registerModel">-->
      <label>维修记录登记</label>
      <el-button type="primary" @click="showAddDevRepaidRecord" :loading="loading" icon="edit" size="small" >新增</el-button>
      <el-button type="success" @click="showEditDevRepaidRecord" :loading="loading" icon="edit" size="small" >编辑</el-button>
      <el-button type="danger" @click="delDevRepaidRecord" :loading="loading" icon="edit" size="small" >删除</el-button>
      <!--</el-form-item>-->
    </el-col>
  </el-row>
  <el-tabs type="border-card" style="margin-top: 10px;">
    <el-tab-pane label="维修记录">
      <el-row :gutter="20">
        <el-table v-bind:data="form.devRepairRecords" highlight-current-row v-loading="tableLoading"  @current-change="selsChange" border style="width: 98%" height="200" ref="devRepairTable" append >
          <el-table-column prop="indexNo" label="序号" width="60" sortable>
            <template slot-scope="scope" >
              {{scope.row.indexNo + 1}}
            </template>
          </el-table-column>
          <el-table-column prop="repairStatus" label="维修状态" width="90" align="center" sortable>
            <template slot-scope="scope" >
              <el-tag v-for="item in devRepairStatusList" :key="item.id" v-if="scope.row.repairStatus === parseInt(item.enumNo)" :type="item.remark">
              {{item.displayValue}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="repairNo" label="维修单编码" width="120" sortable></el-table-column>
          <el-table-column prop="repairMatter" label="维修问题" width="120" sortable show-overflow-tooltip></el-table-column>
          <el-table-column prop="repairUnit" label="维修单位" width="90" sortable></el-table-column>
          <el-table-column prop="repairUser" label="维修人员" width="90" sortable></el-table-column>
          <el-table-column prop="warrantyUnit" label="保修单位" width="90" sortable></el-table-column>
          <el-table-column prop="warrantyMan" label="保修人员" width="90" sortable></el-table-column>
          <el-table-column prop="beginTime" label="维修开始时间" :formatter="dataFormat" width="150" sortable></el-table-column>
          <el-table-column prop="endTime" label="维修结束时间" :formatter="dataFormat" width="150" sortable></el-table-column>
          <el-table-column prop="repairRecord" label="维修记录" min-width="150" sortable show-overflow-tooltip></el-table-column>
        </el-table>
      </el-row>
    </el-tab-pane>
  </el-tabs>

  <!--新增维修单-->
  <add-or-update
    :to="params => { return this.addDevRepairRecord(params) }"
    ref="devRepairAddForm">
  </add-or-update>

  <!--编辑维修单-->
  <add-or-update
    :to="params => { return this.editDevRepairRecord(params) }"
    ref="devRepairEditForm">
  </add-or-update>
</div>
</template>

<script>
 import Util from '../../../common/js/util'
 import API from '../../../api'
 import AddOrUpdate from './AddOrUpdate'
 import NProgress from 'nprogress'

 export default {
   data () {
     return {
       API: API,
       form: {
         devRepairRecord: {
           id: '',
           indexNo: 0,
           repairUser: '',
           repairStatus: '',
           repairRecord: '',
           beginTime: '',
           endTime: '',
           warrantyUnit: '',
           warrantyMan: '',
           repairNo: '',
           repairUnit: '',
           repairMatter: ''
         },
         devRepairRecords: [],
         oldDevRepairRecords: []
       },
       tableLoading: false,
       loading: false,
       tableSel: [],
       devRepairStatusList: []
      }
   },
    components: {
      API: API,
      addOrUpdate: AddOrUpdate
    },
    methods: {
     getDevRepairStatusList () {
        // 获取设备维修类型列表
        let para = {enumType: 'DEVICEREPAIRSTATUS'}
        API.getEnumInfo().go(para).then((data) => {
          if (data.ok) {
            this.devRepairStatusList = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
     },
     show (devRepairRecords) {
       let list = []
       for (var i = 0; i < devRepairRecords.length; i++) {
         let obj = Object.assign({}, this.form.devRepairRecord, devRepairRecords[i])
         obj.indexNo = i
         list.push(obj)
       }
       this.form.devRepairRecords = Util.deepCopy(list)
       this.form.oldDevRepairRecords = Util.deepCopy(list)
     },
     initData () {
      this.tableLoading = false
      this.tableSel = []
     },
     getDevRepairPara () {
        let obj = { devRepairNewValue: this.form.devRepairRecords, devRepairOldValue: this.form.oldDevRepairRecords }
        return obj
     },
     dataFormat (row, column, cellValue) {
       let date = row[column.property]
       if (date === undefined) {
         return ''
        }
        return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
     },
     selsChange (row) {
       this.tableSel = row
     },
     showAddDevRepaidRecord (deviceIrrInfo) {
       this.$nextTick(() => {
         let obj = Object.assign({}, this.form.devRepairRecord)
         obj.indexNo = this.form.devRepairRecords.length
         this.$refs['devRepairAddForm'].show(obj, deviceIrrInfo.airportCode, false)
       })
     },
     showEditDevRepaidRecord (deviceIrrInfo) {
      if (this.tableSel['indexNo'] !== undefined) {
          this.$nextTick(() => {
            this.$refs['devRepairEditForm'].show(this.tableSel, deviceIrrInfo.airportCode, false)
          })
      } else {
        this.$message({
          message: '请先选择一条记录再编辑',
          center: true
        })
      }
     },
     addDevRepairRecord (repairRecordPara) {
       this.form.devRepairRecords.push(Object.assign({}, repairRecordPara.newRepairRecord))
     },
     editDevRepairRecord (repairRecordPara) {
       let list = Util.deepCopy(this.form.devRepairRecords)
       let index = repairRecordPara.newRepairRecord.indexNo
       list[index] = Object.assign({}, this.form.devRepairRecord, repairRecordPara.newRepairRecord)
       this.form.devRepairRecords = list
     },
     delDevRepaidRecord () {
       if (this.tableSel['indexNo'] !== undefined) {
         this.$confirm('确认删除吗？', '提示', {}).then(() => {
           if (this.tableSel.id !== null && this.tableSel.id !== '') {
             NProgress.start()
             API.removeDeviceRepairRecord(this.tableSel).then((res) => {
               NProgress.done()
               if (res.ok) {
                 // 更新维修单list
                 this.delDevRepaidList()
               }
               this.$notify(Util.notifyBody(res.ok, res.msg))
             })
           } else {
             this.delDevRepaidList()
             this.$notify(Util.notifyBody(true, '删除成功'))
           }
         }).catch(() => {
           this.$message({
             type: 'info',
             message: '已取消提交'
           })
         })
       } else {
          this.$message({
            message: '请先选择一条记录再删除',
            center: true
          })
        }
     },
     delDevRepaidList () {
       // 更新维修单list
       let list = []
       let delIndex = this.tableSel.indexNo
       for (var i = 0; i < this.form.devRepairRecords.length; i++) {
         if (i < delIndex) {
           list.push(this.form.devRepairRecords[i])
          } else if (i > delIndex) {
            list.push(this.form.devRepairRecords[i])
            list[i].indexNo = list[i].indexNo - 1
          }
       }
       this.form.devRepairRecords = list
     }
   },
   mounted () {
     this.getDevRepairStatusList()
   }
 }
</script>

<style lang="scss">

</style>


