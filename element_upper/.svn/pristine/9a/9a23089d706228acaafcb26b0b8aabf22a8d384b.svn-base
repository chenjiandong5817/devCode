/*
 * @Author: ylj
 * @Date: 2017-10-19 21:24:08
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-24 10:15:39
 */
<template>
<div class="shareFlightFormCls">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="clearData" :modal-append-to-body="false" size="small">
    <el-form v-for="(item, index) in shareFlightLs" :key="setFormName(index)" :model="shareFlightLs[index]" :ref="setFormName(index)"  >
      <!--<el-row :gutter="28">
        <el-col :span="24">
          <el-form-item label="航段" prop="segment">
            <el-select v-model="segment" placeholder="公司" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in curSegList"
                :key="item.segmentName"
                :label="item.segmentName"
                :value="item.segmentName">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>-->

      <el-row :gutter="10">
        <el-col :span="13">
          <el-form-item label="公司" prop="carrier" :rules="{required: true, message: '公司不能为空', trigger: 'blur'}">
            <el-select v-model="shareFlightLs[index].carrier" placeholder="公司" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in $cache.fetch('airlines')"
                :key="item.icaocode"
                :label="item.cnname+'(' + item.iatacode + '/' + item.icaocode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnname }}</span>
                <span style="float: right">{{ item.iatacode + '/' + item.icaocode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="7">
          <el-form-item label="航班号" prop="flightNo" :rules="{required: true, message: '航班号不能为空', trigger: 'blur'}">
            <el-input v-model="shareFlightLs[index].flightNo" style="width:100%;" placeholder="航班号"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item style="margin-left: 10px;">
            <br/>
            <el-button type="text" class="el-icon-plus" @click.native="addShareFlight(index)" size="small" v-if="index === shareFlightLs.length - 1"></el-button>
            <el-button type="text" class="el-icon-minus" @click.native="delShareFlight(index)" size="small"></el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import dateTime from '../../../components/DateTime'
export default {
  props: {
    title: {
      type: String,
      default: '共享航班配置'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      visible: false,
      loading: false,
      segment: '',
      curSegList: [],
      shareFlight: { id: null, carrier: '', flightNo: '' },
      shareFlightLs: [{ id: null, carrier: '', flightNo: '' }],
      deleteShareIdList: [],
      API: API
    }
  },
  components: {
    dateTime: dateTime
  },
  methods: {
    bindData () {
    },
    setFormName (index) {
      return 'shareFlightForm' + index
    },
    show (curSegList, shareFlightLs) {
      this.visible = true
      this.curSegList = curSegList
      this.shareFlightLs = Util.deepCopy(shareFlightLs)
      if (this.shareFlightLs.length < 1) {
        this.shareFlightLs.push(Util.deepCopy(this.shareFlight))
      }
    },
    handleClose () {
      this.visible = false
    },
    handleSubmit () {
      let validFlag = true
      for (var i = 0; i < this.shareFlightLs.length; i++) {
        let formid = 'shareFlightForm' + i
        this.$refs[formid][0].validate((valid) => {
          validFlag = valid
          if (!valid) {
            return false
          }
        })
        if (!validFlag) {
          return
        }
      }
      if (validFlag) {
        // 检验成功，提交信息
        let para = { shareFlightLs: this.shareFlightLs, deleteShareIdList: this.deleteShareIdList }
        // let para = Util.deepCopy(this.shareFlightLs)
        this.to(para)
        this.visible = false
      }
    },
    clearData () {
      for (var i = 0; i < this.shareFlightLs.length; i++) {
        let formid = 'shareFlightForm' + i
        this.$refs[formid][0].resetFields()
      }
      this.visible = false
    },
    addShareFlight (index) {
      // 新增共享航班
      this.shareFlightLs.push(Util.deepCopy(this.shareFlight))
    },
    delShareFlight (index) {
      // 删除共享航班
      if (this.shareFlightLs[index].id !== null) {
        this.deleteShareIdList.push(this.shareFlightLs[index].id)
      }
      this.shareFlightLs.splice(index, 1)
    }
  },
  mounted () {

  }
}
</script>
<style lang="scss">
.shareFlightFormCls {
  .el-form-item {
    margin-bottom: 10px!important;
  }
  .el-dialog__body {
    padding: 2px 20px!important;
  }
  .el-icon-plus:before, .el-icon-minus:before {
    font-size: 16px!important;
  }
  .el-dialog--small {
    width: 70%!important;
  }
}
.smallDigCls .el-dialog .shareFlightFormCls .el-dialog--small {
  width: 55%!important;
}
</style>
