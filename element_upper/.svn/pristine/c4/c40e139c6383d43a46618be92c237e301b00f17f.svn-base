/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:15:34
 */

 <template>
    <el-form v-show="visible">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-tag type="danger">修改非值机柜台信息</el-tag>
        </el-row>
        </br>
        <el-row :gutter="20">
          <el-col :span="18">
            <el-form-item label="选择机场">
              </br>
              <city-name
                class="selectAirportStyle"
                ref="city"
                v-on:getAirportName = "getAirportCode">
              </city-name>
            </el-form-item>
            <el-form-item label="服务模式">
              <el-select v-model="fileds.serviceMode" placeholder="选择服务模式" :filterable="true" class="maxWidth" @change="getServiceType()">
                <el-option v-for="mode in flightServiceMode" :key="mode.value" :label="mode.text" :value="mode.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="服务类型">
              <el-select v-model="fileds.serviceType" placeholder="选择服务类型" :filterable="true" class="maxWidth">
                <el-option v-for="Servicetype in flightServiceType" :key="Servicetype.value" :label="Servicetype.text" :value="Servicetype.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="图片尺寸">
              <el-select v-model="fileds.imagesize" placeholder="选择图片尺寸" :filterable="true" class="maxWidth">
                <el-option v-for="size in imageSizeList" :key="size.value" :label="size.text" :value="size.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="描述">
                <el-input v-model="fileds.description" placeholder="添加描述,选填" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-upload
              ref="small"
              class="avatar-uploader"
              :action="imageServerUrl"
              :show-file-list="false"
              :multiple="true"
              :headers="headersUpload"
              :on-change="handleChange"
              :before-upload="handleBeforeUpload">
              <img v-if="fileds.url" :src="fileds.url" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            </br>
            <el-button type="info" @click="clearImage()" icon="delete" class="buttonwidth" :disabled="fileds.url === ''">清空图片</el-button>
          </el-col>
        </el-row>
    </el-card>
    </br>
    </el-form>
</template>

 <script>
import GlobalConfig from '../../../common/config/global'
import LoginInfo from './../../../vuex/store'
import axiosHelper from '../../../common/js/axios-helper'
import API from './../../../api'
import CityName from '../../../components/CityName'
import ImageManage from '../../../api/device/image-manage'
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
    getNewInfo: {
      type: Function,
      default: function () { }
    },
    callback: {
      type: Function,
      default: function () { }
    },
    FlightServiceMode: {
      type: Array
    },
    FlightType: {
      type: Array
    }
  },
  components: {
    cityName: CityName
  },
  data () {
    return {
      flightServiceMode: [],
      flightServiceType: [],
      EditFormData: {},
      subscribeAirports: [],
      subscribeAirportsLables: [],
      headersUpload: null,
      destinationData: {url: '', destination: '', imageFormat: '', imageSize: '', imageName: '', description: '', airportcode: ''},
      description: '',
      destinationCity: '',
      airportCode: '',
      imagesize: '',
      visible: false,
      loading: false,
      oldValue: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', url: '', id: ''},
      newValue: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', id: ''},
      bindDestination: [],
      imageSizeList: [],
      imageList: [], // 上传的图片信息
      fileds: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', serviceMode: '', serviceType: '', url: ''},
      imageServerUrl: ''
    }
  },
  methods: {
    getUrl () {
      this.imageServerUrl = ImageManage.displayAction
    },
    getAirportCode (airportData) {
      this.fileds.airportcode = airportData
    },
    getSubscribeAirports () {
      let result = []
      let data = LoginInfo.state.userStorage.user.aiisAirports
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
    },
    SubscribeAirports () {
      this.subscribeAirportsLables = []
      this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
      for (let i = 0; i < this.subscribeAirports.length; i++) {
        let label = {text: this.subscribeAirports[i], value: this.subscribeAirports[i]}
        this.subscribeAirportsLables.push(label)
      }
    },
    submit () {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
      this.headersUpload = axiosHelper.generatAuthHeader()
      let fd = new FormData()
      this.setNewValue()
      fd.append('newValue', JSON.stringify(this.newValue))
      fd.append('oldValue', JSON.stringify(this.oldValue))
      fd.append('file', this.imageList[0])
      // console.log('newValue')
      // console.log(this.newValue)
      // console.log('oldValue')
      // console.log(this.oldValue)
      // console.log(this.imageList[0].raw)
      API.submitPutFile(fd).then(res => {
          if (res.data.ok === true) {
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success'
            })
            this.getNewInfo()   // 获取新的列表
            this.callback()   // 返回上一级
          } else {
            this.$notify({
              title: '失败',
              message: '修改失败',
              type: 'error'
            })
            this.getNewInfo()
            this.callback()
          }
        })
      }).catch(() => {
        // console.log('newValue')
        // console.log(this.newValue)
        // console.log('oldValue')
        // console.log(this.oldValue)
        // console.log(this.imageList[0].raw)
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    clearImage () {   // 清空图片
      this.imageList = []
      this.fileds.url = ''
    },
    getServiceType () {
      this.flightServiceType = []
      for (let i = 0; i < this.FlightType.length; i++) {
        if (this.FlightType[i].ckCounterTemplate === this.fileds.serviceMode) {
          // console.log(this.shoppeList[index].serviceMode)
          let label = {text: this.FlightType[i].description + ' ' + this.FlightType[i].serviceTypeCode, value: this.FlightType[i].serviceTypeCode}
          this.flightServiceType.push(label)
        }
      }
    },
    handleClose () {
      this.visible = false
      // console.log('关闭')
      // this.shoppeList = []
      // this.shoppeList.push(this.shoppeData)
    },
    getImgSize () {
      this.imageSizeList = []
      for (let i = 0; i < GlobalConfig.imageSize.length; i++) {
        let label = {text: GlobalConfig.imageSize[i], value: GlobalConfig.imageSize[i]}
        this.imageSizeList.push(label)
      }
    },
    setNewValue () {
      this.newValue = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', id: ''}
      this.newValue.imagetype = this.EditFormData.imagetype
      this.newValue.imagesize = this.fileds.imagesize
      this.newValue.imagename = this.fileds.serviceMode + '_' + this.fileds.serviceType
      this.newValue.airportcode = this.fileds.airportcode
      this.newValue.description = this.fileds.description
      this.newValue.id = this.EditFormData.id
      if (typeof (this.fileds.filetype) === 'undefined' || this.fileds.filetype === '') {
        this.fileds.filetype = this.EditFormData.filetype
      }
      // console.log(this.fileds.filetype)
      this.newValue.filetype = this.fileds.filetype
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
      this.$refs['city'].setValue(this.oldValue.airportcode)
    },
    setOldValue () {
      this.oldValue = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', url: '', id: ''}
      this.oldValue.imagetype = this.EditFormData.imagetype
      this.oldValue.imagesize = this.EditFormData.imagesize
      this.oldValue.imagename = this.EditFormData.imagename
      this.oldValue.filetype = this.EditFormData.filetype
      this.oldValue.airportcode = this.EditFormData.airportcode
      this.oldValue.description = this.EditFormData.description
      this.oldValue.id = this.EditFormData.id
      this.oldValue.url = this.EditFormData.url
    },
    getModeAndType (name) {
      this.fileds.serviceMode = name[0]
      this.fileds.serviceType = name[2]
    },
    setEditData () {   // 从服务器读取表单数据
      // console.log(this.EditFormData)
      this.getModeAndType(this.EditFormData.imagename)
      this.fileds.airportcode = this.EditFormData.airportcode
      this.fileds.imagesize = this.EditFormData.imagesize
      this.fileds.description = this.EditFormData.description
      this.fileds.url = this.preview(this.EditFormData)
      this.setOldValue()
    },
    preview (row) {
      let urlPart = GlobalConfig.urlPart
      let queryPara = GlobalConfig.queryPara
      let finalUrl = urlPart + queryPara + row.id + '&url=' + row.url + '&updatetime=' + row.updateTime
      return finalUrl
    },
    handleChange (file, fileList) {
      this.fileds.url = URL.createObjectURL(file.raw)
    },
    handleBeforeUpload (file) {
      this.imageList = []
      this.imageList.push(file)
      this.fileds.filetype = this.getFileFormat(file.name)
      return false
    },
    getFileFormat (fileName) {
      let name = []
      name = fileName.split('.')
      // console.log(name[1])
      return '.' + name[1]
    },
    getData () {
      this.flightServiceMode = this.FlightServiceMode
    },
    show: function (data) {
      this.getUrl()
      this.fileds = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', serviceMode: '', serviceType: '', url: ''}
      this.EditFormData = {}
      this.EditFormData = data
      this.imageList = []  // 存放图片信息列表
      this.SubscribeAirports()
      this.getImgSize()
      this.getData()
      this.setEditData()
      this.setAirportInfo()
        // this.setData()
      this.visible = true
    }
  }
}
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  .clearfix:after {
      clear: both
  }

  .box-card {
    width: 480px;
  }

  .maxWidth {
    width: 100%;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;

  }
  .buttonwidth {
    width: 178px;
  }
  .selectAirportStyle {
    width: 100%;
  }
</style>
