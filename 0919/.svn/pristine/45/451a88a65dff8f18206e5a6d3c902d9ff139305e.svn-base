/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:17:57
 */

 <template>
    <el-form v-show="visible">
      <el-form-item label="选择机场">
        </br>
        <city-name
          class="selectAirportStyle"
          ref="city"
          v-on:getAirportName = "getAirportCode">
        </city-name>
      </el-form-item>
      <div v-for="(item, index) in shoppeList" :key="item.imageSize">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-tag type="danger">选择非值机柜台模式和服务</el-tag>
        </el-row>
        </br>
        <el-row :gutter="20" @click.native.capture="uploadClick(index)">
          <el-col :span="18">
            <el-form-item label="服务模式">
              <el-select v-model="item.serviceMode" placeholder="选择服务模式" :filterable="true" class="maxWidth" @change="getServiceType(index)">
                <el-option v-for="mode in flightServiceMode" :key="mode.value" :label="mode.text" :value="mode.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="服务类型">
              <el-select v-model="item.serviceType" placeholder="选择服务类型" :filterable="true" class="maxWidth">
                <el-option v-for="Servicetype in flightServiceType" :key="Servicetype.value" :label="Servicetype.text" :value="Servicetype.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="图片尺寸">
              <el-select v-model="item.imageSize" placeholder="选择图片尺寸" :filterable="true" class="maxWidth">
                <el-option v-for="size in imageSizeList" :key="size.value" :label="size.text" :value="size.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="描述">
                <el-input v-model="item.description" placeholder="添加描述,选填" type="textarea"></el-input>
            </el-form-item>
            <el-button type="success" @click="addCounters" v-if="index+1===shoppeList.length">新增非值机柜台信息</el-button>
            <el-button type="danger" @click="removeCounters(index)">删除非值机柜台信息</el-button>
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
              <img v-if="item.imageUrl" :src="item.imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            </br>
            <el-button type="info" @click="clearImages(index)" icon="delete" class="buttonwidth" :disabled="shoppeList[index].imageUrl === ''">清空图片</el-button>
          </el-col>
        </el-row>
    </el-card>
    </br>
    </div>
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
    callback: {
      type: Function,
      default: function () { }
    },
    FlightAirline: {
      type: Array
    },
    FlightServiceMode: {
      type: Array
    },
    FlightType: {
      type: Array
    }
  },
  data () {
    return {
      airportCode: '',
      headersUpload: null,
      subscribeAirportsLables: [],
      subscribeAirports: [],
      addOrUpdate: '',
      tabsValue: '',
      index: 0,
      uploadParamList: [], // 需要上传所有参数列表,如果选第一个tabs默认上传3条数据,第二个tabs一个专柜算一个 + 前三个
      imageSizeList: [],
      flightLogoAirline: [],
      flightServiceMode: [],
      flightServiceType: [],
      counterCount: 1,
      FlightLogoData: {url: '', airCompany: '', imageFormat: '', imageSize: '', imageName: '', description: '', airportCode: '', fileType: ''},   // 普通柜一些的数据，不用数组存
      shoppeData: {imageUrl: '', serviceMode: '', serviceType: '', imageSize: '', description: '', airportCode: '', fileType: ''},  // 专柜的一些基本数据
      shoppeList: [],  // 专柜的数组，有可能有多个
      visible: false,
      loading: false,
      oldValue: {},
      airLinesList: [],
      checkinFiled: { airCompany: '' },
      isInsert: true,
      fileds: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: ''},
      textList: [],   // 存放提交文本信息列表
      imageList: [],  // 存放图片信息列表
      imageServerUrl: ''
      // deviceStatusList: [ { text: '删除', value: -1 }, { text: '在线', value: 0 }, { text: '离线', value: 1 }, { text: '异常', value: 2 } ],
      // 动态插入
    }
  },
  components: {
    cityName: CityName
  },
  methods: {
    getUrl () {
      this.imageServerUrl = ImageManage.displayAction
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
    setFunc (func) {
      this.addOrUpdate = func
      // console.log(this.addOrUpdate)
    },
    getAirportCode (airportData) {
      this.airportCode = airportData
    },
    submit () {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
      this.headersUpload = axiosHelper.generatAuthHeader()
      let fd = new FormData()
      // console.log(this.addOrUpdate)
      if (this.addOrUpdate === 'addFunc') {
        // console.log(this.shoppeList)
        for (let i = 0; i < this.shoppeList.length; i++) {
          this.shoppeList[i].airportCode = this.airportCode
          this.fileds = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: ''}
          this.fileds.imagetype = 'noncheckin'
          this.fileds.imagesize = this.shoppeList[i].imageSize
          this.fileds.imagename = this.shoppeList[i].serviceMode + '_' + this.shoppeList[i].serviceType
          this.fileds.filetype = this.shoppeList[i].fileType
          this.fileds.airportcode = this.shoppeList[i].airportCode
          this.fileds.description = this.shoppeList[i].description
          this.textList.push(this.fileds)
        }
        // console.log(this.textList)
        // console.log(this.imageList)
        for (let i = 0; i < this.textList.length; i++) {
          fd.append('newValue', JSON.stringify(this.textList[i]))
            // console.log(JSON.stringify(this.textList[i]))
        }
        for (let i = 0; i < this.imageList.length; i++) {
          // console.log(this.imageList[i].raw)
          fd.append('file', this.imageList[i].raw)
        }
        API.submitPostFile(fd).then(res => {
          if (res.data.ok === true) {
            this.$notify({
              title: '成功',
              message: '上传成功',
              type: 'success'
            })
            this.callback()
          } else {
            this.$notify({
              title: '失败',
              message: '上传失败',
              type: 'error'
            })
            this.callback()
          }
        })
      } else {
          // fd.append('newValue', JSON.stringify(this.fileds))
          // fd.append('oldValue', JSON.stringify(this.oldValue))
          // fd.append('file', file)
          // console.log(this.fileds)
          // console.log(this.oldValue)
          // console.log(file)
          // API.submitPutFile(fd).then(res => {
          //   console.log(res)
          //   if (res.data.ok === true) {
          //     this.$notify({
          //       title: '成功',
          //       message: '修改成功',
          //       type: 'success'
          //     })
          //   } else {
          //     this.$notify({
          //       title: '失败',
          //       message: '修改失败',
          //       type: 'success'
          //     })
          //   }
          // })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    clearImages (index) {
      if (this.imageList.length < 1) {
        return
      }
      if (index < this.shoppeList.length - 1) {
        this.shoppeList[index].imageUrl = ''
        return
      }
      this.shoppeList[index].imageUrl = ''
      this.imageList.splice(this.imageList.length - 1, 1)  // 删除最后一个元素再插入
    },
    uploadClick (index) {
      this.index = index
      // console.log(index)
    },
    addCounters () {   // 新增专柜信息
      // this.counterCount += 1
      let length = this.shoppeList.length - 1
      if (this.airportCode !== '' && this.shoppeList[length].imageUrl !== '' && this.shoppeList[length].serviceMode !== '' && this.shoppeList[length].serviceMode !== '' && this.shoppeList[length].imageSize !== '') {
        this.flightServiceType = []
        this.shoppeData = {imageUrl: '', serviceMode: '', serviceType: '', imageSize: '', description: '', airportCode: '', fileType: ''}
        this.shoppeList.push(this.shoppeData)
      } else {
        this.$notify({
          title: '警告',
          message: '请先填充完所有的表单再进行新增!',
          type: 'warning'
        })
      }
    },
    clearFormData () {   // 清除表单数据
      this.airportCode = ''
      if (this.tabsValue === '0') { // 第一个tabs
        this.textList = []   // 存放提交文本信息列表
        this.imageList = []  // 存放图片信息列表
        this.FlightLogoData = {url: '', airCompany: '', imageFormat: '', imageSize: '', imageName: '', description: '', airportCode: '', fileType: ''}
        this.isInsert = false   // 不插入新的数据，维持
        this.shoppeList = []
        this.shoppeData = {imageUrl: '', serviceMode: '', serviceType: '', imageSize: '', description: '', airportCode: '', fileType: ''}
        this.shoppeList.push(this.shoppeData)
        this.tabsValue = '0'
      } else if (this.tabsValue === '1') {
        this.shoppeList = []
        this.shoppeData = {imageUrl: '', serviceMode: '', serviceType: '', imageSize: '', description: '', airportCode: '', fileType: ''}
        this.FlightLogoData = {url: '', airCompany: '', imageFormat: '', imageSize: '', imageName: '', description: '', airportCode: '', fileType: ''}
        this.shoppeList.push(this.shoppeData)
        this.isInsert = true
        this.tabsValue = '0'
      }
    },
    removeCounters (index) {  // 删除专柜x信息
      if (this.shoppeList.length <= 1) {
        this.$notify({
          title: '警告',
          message: '请至少保留一项',
          type: 'warning'
        })
      } else {
        this.shoppeList.splice(index, 1)
        this.imageList.splice(index, 1)  // 删除最后一个元素再插入
      }
    },
    getServiceType (index) {
      this.flightServiceType = []
      for (let i = 0; i < this.FlightType.length; i++) {
        if (this.FlightType[i].ckCounterTemplate === this.shoppeList[index].serviceMode) {
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
    handleChange (file, fileList) {
      if (this.index < this.shoppeList.length - 1) {  // 此处防止用户添加完图片，又去修改图片，比如已经添加两个信息，修改第一个信息的图片
        // console.log(this.index)
        // console.log(this.shoppeList.length - 1)
        // console.log(file.raw.name)
        this.shoppeList[this.index].imageUrl = URL.createObjectURL(file.raw)
        this.shoppeList[this.index].fileType = this.getFileFormat(file.raw.name)
        this.imageList[this.index] = file
        return
      }
      if (this.shoppeList[this.index].imageUrl !== '') {  // 当前位置上有图片,不用再push,赋值就行
        // console.log(this.imageList[this.imageList.length - 1])
        this.imageList.splice(this.imageList.length - 1, 1)  // 删除最后一个元素再插入
        this.imageList.push(file)
      } else {
        this.imageList.push(file)  // 如果为空直接插入
      }
      this.shoppeList[this.index].imageUrl = URL.createObjectURL(file.raw)
      this.shoppeList[this.index].fileType = this.getFileFormat(file.raw.name)
    },
    handleBeforeUpload (file) {
      return false
    },
    getFileFormat (fileName) {
      let name = []
      name = fileName.split('.')
      // console.log(name[1])
      return '.' + name[1]
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
      this.$refs['city'].setValue('')
    },
    getData () {
      this.flightLogoAirline = this.FlightAirline
      this.flightServiceMode = this.FlightServiceMode
    },
    show: function () {
      this.getUrl()
      this.airportCode = ''
      this.textList = []   // 存放提交文本信息列表
      this.imageList = []  // 存放图片信息列表
      this.SubscribeAirports()
      this.getImgSize()
      this.getData()
      if (this.isInsert === true) {   // 如果按的是第二个清空按钮
        this.shoppeList = []
        this.shoppeData = {imageUrl: '', serviceMode: '', serviceType: '', imageSize: '', description: '', airportCode: '', fileType: ''}
        this.shoppeList.push(this.shoppeData)
      }
      this.isInsert = true
        // this.setData()
      this.setAirportInfo()
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
