/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-10-13 11:28:28
 */

 <template>
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :modal="false" :before-close="handleClose" size="small" :modal-append-to-body="false">
    <!--<el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" class="demo-ruleForm" >-->
    <el-form ref="deviceGroup" :model="checkinFiled">
      <el-col :gutter="20">
        <el-row :span="6">
          <el-form-item label="机场代码">
            </br>
            <el-select v-model="checkinFiled.airportCode" placeholder="订阅机场代码" :filterable="true" class="selectA">
              <el-option
                v-for="item in subscribeAirportList"
                :key="item.value"
                :label="item.text"
                :value="item.value"
                >
              </el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row :span="6">
          <el-form-item label="群组大小">
            <el-input v-model="checkinFiled.groupSize" placeholder="请输入群组大小"></el-input>
          </el-form-item>
        </el-row>
        <el-row :span="6">
          <el-form-item label="群组名称">
            <el-input v-model="checkinFiled.groupName" placeholder="请输入群组名称"></el-input>
          </el-form-item>
        </el-row>
        <el-row :span="6">
          <el-form-item label="群组位置">
            <el-input v-model="checkinFiled.groupLocation" placeholder="请输入群组位置"></el-input>
          </el-form-item>
        </el-row>
      </el-col>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="visible = false">取消</el-button>
      <el-button @click="resetForm('deviceGroup')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</template>

 <script>
import Util from '../../../common/js/util'
import API from '../../../api'
import LoginInfo from '../../../vuex/store'

export default {
  props: {
    subscrpitAirport: {
      type: String
    },
    title: {
      type: String,
      default: '新增'
    },
    to: {
      type: Function,
      default: function () { }
    },
    callback: {
      type: Function,
      default: function () { }
    }
  },
  data () {
    return {
      subscribeAirportList: [],
      visible: false,
      loading: false,
      oldValue: {},
      checkinFiled: { groupName: '', groupLocation: '', groupSize: 0, airportCode: '' }  // 只能增加其订阅的机场
      // deviceStatusList: [ { text: '删除', value: -1 }, { text: '在线', value: 0 }, { text: '离线', value: 1 }, { text: '异常', value: 2 } ],
      // 动态插入
    }
  },
  methods: {
    getSubscribeAirports () {
      let result = []
      let data = LoginInfo.state.userStorage.user.aiisAirports
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      this.subscribeAirportList = []
      for (let i = 0; i < result.length; i++) {
        let label = {text: result[i], value: result[i]}
        this.subscribeAirportList.push(label)
      }
      // console.log(this.subscribeAirportList)
    },
    show: function (row) {
      this.checkinFiled.airportCode = this.subscrpitAirport
      this.getSubscribeAirports()
      this.visible = true
      if (row != null) {
        this.checkinFiled = Util.deepCopy(row)
        // this.deviceInfo = row
        this.oldValue = Object.assign({}, row)
      }
    },
    handleClose: function (params) {
      this.visible = false
      this.$refs['deviceGroup'].resetFields()
      this.checkinFiled = { groupName: '', groupLocation: '', groupSize: 0, airportCode: '' }
    },
    handleSubmit: function (params) {
      this.$refs['deviceGroup'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let para = { newValue: this.checkinFiled, oldValue: this.oldValue }
            API.addDeviceGroups().go(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.$refs['deviceGroup'].resetFields()
              this.visible = false
              this.checkinFiled = { groupName: '', groupLocation: '', groupSize: 0, airportCode: '' }
              // this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    resetForm (formName) {
      // console.log(formName)
      this.$refs[formName].resetFields()
      this.checkinFiled = { groupName: '', groupLocation: '', groupSize: 0, airportCode: '' }
    }
  }
}
</script>
<style>
.selectA {
  width: 100%;
}
</style>

