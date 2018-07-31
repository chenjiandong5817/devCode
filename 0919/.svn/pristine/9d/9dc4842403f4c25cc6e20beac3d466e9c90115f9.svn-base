/*
 * @Author: chenyang
 * @Date: 2017-10-19 14:40:35
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-21 09:38:01
 * 播放列表点击树形列表Item显示界面和编辑界面页面
 */
<template>
  <div>
  <el-table :data="deviceConfig" highlight-current-row v-loading="deivceLoading" border :height="tableHeight" style="width: 73%;">
      <!-- <el-table-column type="selection" width="55">
      </el-table-column> -->
      <!-- <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column> -->
      <el-table-column prop="tempConfig.description" label="模板" show-overflow-tooltip min-width="280">
      </el-table-column>
      <el-table-column prop="timeSpan" label="时间间隔" show-overflow-tooltip width="180">
      </el-table-column>
      <el-table-column prop="begin" label="开始时间" show-overflow-tooltip width="180">
      </el-table-column>
      <el-table-column prop="end" label="结束时间" show-overflow-tooltip width="180">
      </el-table-column>
      <el-table-column label="操作" width="160" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
  </el-table>
  </common-add-or-update>
  <!--编辑界面-->
  <common-add-or-update
    size="tiny"
    title="编辑"
    :to="API.editDeviceConfig().go"
    :callback="SendMessage"
    ref="editForm">
  </common-add-or-update>
  <!--删除窗口-->
  <common-delete
    :to="API.removeDeviceConfig().go"
    title="删除"
    :callback="SendMessage"
    ref="delConfirm">
  </common-delete>
    <!-- 分页 -->
   <pagination :to="getDeviceConfigList" ref="page"></pagination>
  </div>
</template>

<script>
import Util from '../../common/js/util'
import API from '../../api'
import Pagination from '../../components/Pagination'
import CommonAddOrUpdate from './../../components/CommAddOrUpdate'
import CommonDelete from './../../components/CommDelete'
export default {
  data () {
    return {
      id: '',
      API: API,
      deviceConfig: [],
      deivceLoading: false,
      tableHeight: 900,
      device: [],  // 存放设备数组
      templates: [], // 存放模板介绍
      filters: {
        deviceId: null
      }
    }
  },
  components: {
    pagination: Pagination,
    commonAddOrUpdate: CommonAddOrUpdate,
    commonDelete: CommonDelete
  },
  methods: {
    handleEdit (index, row) {
      let editFields = Util.deepCopy(this.fields)
      for (let i = 0; i < editFields.length; i++) {
        editFields[i].value = row[editFields[i].name]
      }
      this.$refs['editForm'].show(editFields, row)
    },
    handleDel (index, row) {
      this.$refs['delConfirm'].del(row.id)
    },
    getInfoList () {
      this.device = this.$cache.fetch('deviceInfo') || []
      this.templates = this.$cache.fetch('templates') || []
    },
    getDeviceId (ip) {
      for (let i = 0; i < this.device.length; i++) {
        if (this.device[i].deviceIp === ip) {
          this.id = this.device[i].id
          break
        }
      }
      this.getDeviceConfigList()
    },
    SendMessage () {
      this.deivceLoading = true
      this.filters.deviceId = this.id
      this.$emit('delAndEdit', this.id)
      let params = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      API.getDeviceConfig().go(params).then(res => {
        console.log(res)
        this.deivceLoading = false
        this.deviceConfig = res.attr.data.list
        this.$refs['page'].set('total', res.attr.data.pager.recordCount)
      })
    },
    getDeviceConfigList () {
      // console.log(id)
      this.deivceLoading = true
      this.filters.deviceId = this.id
      let params = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      API.getDeviceConfig().go(params).then(res => {
        console.log(res)
        this.deivceLoading = false
        this.deviceConfig = res.attr.data.list
        this.$refs['page'].set('total', res.attr.data.pager.recordCount)
      })
    },
    getParentSendIp (ip) {
      this.getDeviceId(ip)
    }
  },
  computed: {
    pageNumber () {
      return this.$refs['page'].get('pageNumber')
    },
    pageSize () {
      return this.$refs['page'].get('pageSize')
    },
    fields () {
      return [
        { name: 'id', value: '', hidden: true },
        { name: 'deviceId', value: '', disabled: false, label: '设备', filterable: true, type: 'select', choose: this.device.map(item => { return { text: item.deviceIp, value: item.id } }), rules: [ { required: true, message: '请选择设备', trigger: 'blur' } ] },
        { name: 'temId', value: '', label: '模板', type: 'select', choose: this.templates.map(item => { return { text: item.description, value: item.id } }), rules: [ { required: true, message: '请选择模板', trigger: 'blur' } ] },
        { name: 'timeSpan', value: 0, label: '时间间隔', type: 'number', rules: [ { type: 'number', required: true, message: '请填写时间间隔', trigger: 'blur' } ] },
        { name: 'begin', value: '', label: '开始时间', type: 'text', rules: [ { required: true, message: '请填写开始时间', trigger: 'blur' } ] },
        { name: 'end', value: '', label: '结束时间', type: 'text', rules: null }
      ]
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      this.device = this.$cache.fetch('deviceInfo') || []
      this.templates = this.$cache.fetch('templates') || []
    }
  },
  mounted () {
    this.getInfoList()
  }
}
</script>

<style scoped>

</style>
