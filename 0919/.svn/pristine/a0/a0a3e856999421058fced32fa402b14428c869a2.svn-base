/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-01 18:02:29
 */

 <template>
  <el-dialog :title="title" v-model="visible" :before-close="handleClose" size="small" :close-on-click-modal="true">
    <!--<el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" class="demo-ruleForm" >-->
    <el-form ref="ImageAddOrUpdate" :model="fileds">
      <div class="box" v-if="isShowBox">
        <div><el-button type="primary" class="selectA button" @click="showItem('flight_logo')">航空公司LOGO(用于列表和值机柜台)</el-button></div>
        <div><el-button type="danger" class="selectA button" @click="showItem('static')">静态图片</el-button></div>
        <div><el-button type="success" class="selectA button" @click="showItem('destination')">目的地图片</el-button></div>
        <div><el-button type="warning" class="selectA button" @click="showItem('noncheckin')">非值机柜台业务</el-button></div>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer" v-if="submitShowDiv">
      <el-button @click.native="sendBack" icon="caret-left" v-show="canReturn">返回上一级</el-button>
      <el-button @click.native="sendBack">取消</el-button>
      <el-button @click="resetForm('ImageAddOrUpdate')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
    <no-check-in
      title="非值机柜台图片"
      ref='noncheckin'
      :callback = 'sendBack'
      :FlightServiceMode = "nonCheckin"
      :FlightType = "flightLogoTypeList"
      @getFlightLogoName = 'getCheckinFileName'
    >
    </no-check-in>
    <static-name
      title="静态图片"
      :callback = 'sendBack'
      :getNewInfo = 'getNew'
      ref='staticImage'
      @staticImage = 'getCheckinFileName'>
    </static-name>
    <flight-logo
      title="航班图片"
      ref='getFlightLogoImage'
      :callback = 'sendBack'
      :FlightAirline = 'FlightLogo'
      :FlightServiceMode = "FlightLogoServiceMode"
      :FlightType = "flightLogoTypeList"
      @getFlightLogoName = 'getCheckinFileName'>
    </flight-logo>
    <destination
      title="目的地图片"
      :callback = 'sendBack'
      :ForDestination = 'DestinationData'
      ref='getDestinationLogo'
      @getDestination = 'getCheckinFileName'>
    </destination>
    <!--删除窗口-->
    <common-delete
        :to="API.removeImage().go"
        :labelWidth="100"
        ref="delConfirm">
    </common-delete>
    <destination-edit
      ref="destinationEdit"
      :getNewInfo = 'getNew'
      :callback = 'sendBack'
      :ForDestination = 'DestinationData'>
    </destination-edit>
    <no-check-in-edit
      :FlightServiceMode = "nonCheckin"
      :FlightType = "flightLogoTypeList"
      ref="noCheckInEdit"
      :getNewInfo = 'getNew'
      :callback = 'sendBack'>
    </no-check-in-edit>
    <flight-logo-edit
      ref="flightLogoEdit"
      :callback = 'sendBack'
      :getNewInfo = 'getNew'
      :FlightAirline = 'FlightLogo'>
    </flight-logo-edit>
    <counters-edit
      ref="countersEdit"
      :callback = 'sendBack'
      :getNewInfo = 'getNew'
      :FlightAirline = 'FlightLogo'
      :FlightServiceMode = "FlightLogoServiceMode"
      :FlightType = "flightLogoTypeList">
    </counters-edit>
  </el-dialog>
</template>

 <script>
import GlobalConfig from '../../../common/config/global'
import NoCheckIn from '../../device/ImageAddForm/NoCheckIn'
import StaticName from '../../device/ImageAddForm/StaticName'
import FlightLogo from '../../device/ImageAddForm/FlightLogo'
import Destination from '../../device/ImageAddForm/Destination'
import commonDelete from './../../../components/CommDelete'
import API from './../../../api'
import DestinationEdit from '../ImageAddForm/DestinationEdit'
import NoCheckInEdit from '../ImageAddForm/NocheckInEdit'
import FlightLogoEdit from '../ImageAddForm/FlightLogoEdit'
import CountersEdit from '../ImageAddForm/CountersEdit'
export default {
  props: {
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
    },
    Func: {
      type: String
    },
    DestinationData: {
      type: Array
    },
    FlightLogo: {
      type: Array
    },
    FlightLogoServiceMode: {
      type: Array
    },
    flightLogoTypeList: {   // 原始的服务List，主要是判断群组字段
      type: Array
    },
    nonCheckin: {
      type: Array
    }
  },
  data () {
    return {
      flightReuslt: -1,
      canReturn: true, // 是否可以返回上一级
      submitShowDiv: false,
      filters: {
          imagename: '',
          imagetype: '',
          imagesize: '',
          filetype: ''
      },
      isShowBox: false,
      lastSelect: '', // 最后一次选择类型
      fileInfo: {},
      imageList: [],
      editPara: {},
      fileList2: [],
      upLoadAction: '',
      // 用于保存请求的头部信息
      headersUpload: null,
      visible: false,
      loading: false,
      isFirst: true,
      oldValue: {},
      API: API,
      isShow: false,
      updateStatus: false,  // 更新是否成功
      fileds: {id: '', createBy: '', createTime: '', description: '', filetype: '', imagename: '', imagesize: '', imagetype: '', updateBy: '', updateTime: ''},
      imagetype: [ { text: GlobalConfig.imageTypeText[0], value: GlobalConfig.imageType[0] }, { text: GlobalConfig.imageTypeText[1], value: GlobalConfig.imageType[1] }, { text: GlobalConfig.imageTypeText[2], value: GlobalConfig.imageType[2] }, { text: GlobalConfig.imageTypeText[3], value: GlobalConfig.imageType[3] } ],
      filetype: [ { text: GlobalConfig.fileTypeText[0], value: GlobalConfig.fileType[0] }, { text: GlobalConfig.fileTypeText[1], value: GlobalConfig.fileType[1] }, { text: GlobalConfig.fileTypeText[2], value: GlobalConfig.fileType[2] }, { text: GlobalConfig.fileTypeText[3], value: GlobalConfig.fileType[3] } ]
    }
  },
  components: {
      noCheckIn: NoCheckIn,
      staticName: StaticName,
      flightLogo: FlightLogo,
      destination: Destination,
      commonDelete: commonDelete,
      destinationEdit: DestinationEdit,
      noCheckInEdit: NoCheckInEdit,
      flightLogoEdit: FlightLogoEdit,
      countersEdit: CountersEdit
  },
  methods: {
    getNew () {
      this.callback()
    },
    sendBack () {   // 发送返回上一级指令
      this.isShowBox = true
      this.hiddenAll()
    },
    getCheckinFileName (fileName) {
      // this.selectItemDetail.imagename = fileName
      this.fileds.imagename = fileName
      // console.log(fileName)
    },
    cutFlightLogoImageName () {  // 根据字段imagename将FlightLogo编辑界面分为两个
      let cnt = 0
      for (let i = 0; i < this.fileds.imagename.length; i++) {
        if (this.fileds.imagename[i] === '_') {
          cnt++
        }
      }
      if (cnt > 1) {
        return 1  // 用专柜的模板
      } else {   // 用航班图片、全开柜台、普通柜台的模板
        return 0
      }
    },
    showItem (clickItem) {
      // console.log(this.AirportData)
      this.selectItemDetail = clickItem
      // console.log(this.selectItemDetail)
      if (clickItem !== '') {
        this.isShowBox = false
        if (clickItem === 'flight_logo') {
          // console.log(this.Func)
          if (this.Func === 'editFunc') {   // 编辑界面,还要分两个页面
            this.flightReuslt = this.cutFlightLogoImageName()
            if (this.flightReuslt === 1) {  // 这是专柜的模板
              this.hiddenAll()
              this.submitShowDiv = true
              this.$refs['countersEdit'].show(this.fileds)
            } else {   // 这是航班LOGO的模板
              this.hiddenAll()
              this.submitShowDiv = true
              this.$refs['flightLogoEdit'].show(this.fileds)
            }
          } else {
            this.$refs['getFlightLogoImage'].setFunc(this.Func)
            this.hiddenAll()
            this.submitShowDiv = true
            this.$refs['getFlightLogoImage'].show()
          }
        } else if (clickItem === 'static') {   // 由于static页面为单个图片信息,不需要新增界面,所以新增和编辑用一套模板
          this.$refs['staticImage'].setFunc(this.Func)
          this.hiddenAll()
          this.submitShowDiv = true
          this.$refs['staticImage'].show(this.fileds)
        } else if (clickItem === 'destination') {
          if (this.Func === 'editFunc') {  // 如果是编辑,启用DestinationEdit页面
            this.hiddenAll()
            this.submitShowDiv = true
            this.$refs['destinationEdit'].show(this.fileds)
          } else {
            this.$refs['getDestinationLogo'].setFunc(this.Func)
            this.hiddenAll()
            this.submitShowDiv = true
            this.$refs['getDestinationLogo'].show()
          }
        } else if (clickItem === 'noncheckin') {
          if (this.Func === 'editFunc') {
            this.hiddenAll()
            this.submitShowDiv = true
            this.$refs['noCheckInEdit'].show(this.fileds)
          } else {
            this.$refs['noncheckin'].setFunc(this.Func)
            this.hiddenAll()
            this.submitShowDiv = true
            this.$refs['noncheckin'].show()
          }
        }
      }
        // console.log(this.selectItemDetail.imagetype)
    },
    show: function (row) {
      let imageTypeName = ''
      if (row != null) {
        this.canReturn = false
        // this.getImages()
        this.isShowBox = false  // 不要初始选择4项,直接进入该项
        this.fileds = row
        imageTypeName = row.imagetype
        this.oldValue = Object.assign({}, row)
      } else {
        this.canReturn = true
        this.isShowBox = true
        this.oldValue = {}
      }
      this.visible = true
      this.$nextTick(() => {
        this.showItem(imageTypeName)
      })

      // // console.log(this.isFirst)
      // if (this.fileds.imagetype === '' && this.isFirst === false) {
      //   this.hiddenAll()
      // }
      // this.isFirst = false
    },
    uploadSuccess () {
      this.$notify({
        title: '成功',
        message: '上传成功',
        type: 'success'
      })
    },
    uploadError () {
      this.$notify({
        title: '失败',
        message: '上传失败',
        type: 'error'
      })
    },
    hiddenAll () {
      this.$refs['getDestinationLogo'].clearDataForm() // 清除表单数据
      this.$refs['getDestinationLogo'].handleClose()
      this.$refs['staticImage'].handleClose()
      this.$refs['staticImage'].clearAllImage()
      this.$refs['getFlightLogoImage'].handleClose()
      this.$refs['getFlightLogoImage'].clearFormData()
      this.$refs['noncheckin'].handleClose()
      this.$refs['noncheckin'].clearFormData()
      this.$refs['destinationEdit'].handleClose()
      this.$refs['noCheckInEdit'].handleClose()
      this.$refs['flightLogoEdit'].handleClose()
      this.$refs['countersEdit'].handleClose()
      this.submitShowDiv = false
    },
    handleClose: function (params) {
      this.visible = false
      this.fileds = {id: '', createBy: '', createTime: '', description: '', filetype: '', imagename: '', imagesize: '', imagetype: '', updateBy: '', updateTime: ''}
      this.$refs['ImageAddOrUpdate'].resetFields()
      this.hiddenAll()
    },
    uploadFlightLogo () {
      this.$refs['getFlightLogoImage'].submit()
    },
    uploadStatic () {
      // console.log('准备提交')
      this.$refs['staticImage'].submit()
    },
    uploadDestination () {
      this.$refs['getDestinationLogo'].submit()
    },
    uploadNoncheckin () {
      this.$refs['noncheckin'].submit()
    },
    uploadDestinationEdit () {   // 目的地编辑
      this.$refs['destinationEdit'].submit()
    },
    uploadNoncheckinEdit () {   // 非值机柜台编辑
      this.$refs['noCheckInEdit'].submit()
    },
    uploadFlightLogoEdit () {   // 航班LOGO编辑之航空公司、全开柜台、普通柜台
      this.$refs['flightLogoEdit'].submit()
    },
    uploadCounter () {
      this.$refs['countersEdit'].submit()
    },
    handleSubmit: function (params) {
      if (this.Func === 'addFunc') {
        if (this.selectItemDetail === 'flight_logo') {   // 如果提交的选项是航班LOGO
          this.uploadFlightLogo()
        } else if (this.selectItemDetail === 'static') {  // 静态图片
          this.uploadStatic()
          // this.visible = false
        } else if (this.selectItemDetail === 'destination') { // 目的地图片
          this.uploadDestination()
        } else if (this.selectItemDetail === 'noncheckin') {  // 非值机柜台
          this.uploadNoncheckin()
        }
      } else if (this.Func === 'editFunc') {
        if (this.selectItemDetail === 'flight_logo') {   // 如果提交的选项是航班LOGO
          if (this.flightReuslt === 1) {  // 专柜
            this.uploadCounter()
          } else {   // 普通柜台、航班、全开柜
            this.uploadFlightLogoEdit()
          }
        } else if (this.selectItemDetail === 'static') {  // 静态图片
          this.uploadStatic()
          // this.visible = false
        } else if (this.selectItemDetail === 'destination') { // 目的地图片
          this.uploadDestinationEdit()
        } else if (this.selectItemDetail === 'noncheckin') {  // 非值机柜台
          this.uploadNoncheckinEdit()
        }
      }
    },
    resetForm (formName) {
      // console.log(formName)
      // this.$refs[formName].resetFields()
      this.showItem(this.selectItemDetail)
      // this.$refs['staticImage'].clearAllImage()
      // this.$refs['destinationEdit'].clearAllImage()
      this.$refs['getFlightLogoImage'].clearFormData()
      this.$refs['getDestinationLogo'].clearDataForm() // 清除表单数据
      // this.fileds = {imageType: '', imageSize: '', fileType: '', imageName: '', description: ''}
      // this.fileds = {id: '', createBy: '', createTime: '', description: '', filetype: '', imagename: '', imagesize: '', imagetype: '', updateBy: '', updateTime: ''}
    }
  }
}
</script>

<style scoped>
.selectA {
  width: 100%;
  height: 100%;
}
.box div {
    position: relative;
    width: 35%;
    height: 170px;
    margin-left: 70px;
    margin-bottom: 50px;
    float: left;
}
.button
{
    white-space:normal;
    word-break:break-all;
    word-wrap:break-word;
}
</style>
