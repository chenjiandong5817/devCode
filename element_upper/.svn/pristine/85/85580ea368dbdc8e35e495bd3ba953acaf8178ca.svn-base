/*
 * @Author: chenyang
 * @Date: 2017-10-30 18:03:07
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:47:39
 */
// 目的地修改的模板
<template>
<el-form v-show="visible">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-tag type="warning">修改目的地图片相关信息</el-tag>
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
        <el-form-item label="目标地点">
          <el-select v-model="destinationData.destination" placeholder="选择目的地" :filterable="true" class="maxWidth">
            <el-option v-for="item in bindDestination" :key="item.value" :label="item.text" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择尺寸">
          <el-select v-model="destinationData.imageSize" placeholder="选择图片尺寸" :filterable="true" class="maxWidth">
            <el-option v-for="item in imageSize" :key="item.value" :label="item.text" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="destinationData.description" placeholder="添加描述,选填" type="textarea"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-upload
            class="avatar-uploader"
            :action="imageServerUrl"
            :show-file-list="false"
            :headers="headersUpload"
            :on-change="handleChange"
            :multiple="true"
            :before-upload="BeforeSubmit">
            <img v-if="destinationData.url" :src="destinationData.url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          </br>
          <el-button type="info" @click="clearImage()" icon="delete" class="buttonwidth">清空图片</el-button>
        </el-col>
      </el-row>
      </el-card>
    </el-form>
</template>

<script>
import LoginInfo from './../../../vuex/store'
import GlobalConfig from '../../../common/config/global'
import axiosHelper from '../../../common/js/axios-helper'
import API from './../../../api'
import CityName from '../../../components/CityName'
import ImageManage from '../../../api/device/image-manage'
export default {
  props: {
    ForDestination: {
      type: Array
    },
    getNewInfo: {
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
      EditFormData: {},
      subscribeAirports: [],
      subscribeAirportsLables: [],
      headersUpload: null,
      destinationData: {url: '', destination: '', imageFormat: '', imageSize: '', imageName: '', description: '', airportcode: '', fileType: ''},
      description: '',
      destinationCity: '',
      airportCode: '',
      imagesize: '',
      visible: false,
      loading: false,
      oldValue: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', url: '', id: ''},
      newValue: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', id: ''},
      bindDestination: [],
      imageSize: [],
      imageList: [], // 上传的图片信息
      imageServerUrl: ''
    }
  },
  components: {
    cityName: CityName
  },
  methods: {
    getUrl () {
      this.imageServerUrl = ImageManage.displayAction
    },
    getAirportCode (airportData) {
        // console.log(airportData)
      this.airportCode = airportData
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
    setNewValue () {
      this.newValue = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', id: ''}
      this.newValue.imagetype = this.EditFormData.imagetype
      this.newValue.imagesize = this.destinationData.imageSize
      this.newValue.imagename = this.destinationData.destination
      this.newValue.airportcode = this.destinationData.airportcode
      this.newValue.description = this.destinationData.description
      this.newValue.id = this.EditFormData.id
      if (typeof (this.destinationData.filetype) === 'undefined' || this.destinationData.filetype === '') {
        this.destinationData.filetype = this.EditFormData.filetype
      }
      this.newValue.filetype = this.destinationData.filetype
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
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
      this.$refs['city'].setValue(this.oldValue.airportcode)  // 新增这里传空,编辑这里传旧值
    },
    submit () {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.headersUpload = axiosHelper.generatAuthHeader()
        let fd = new FormData()
        this.setNewValue()
        // console.log(this.newValue)
        fd.append('newValue', JSON.stringify(this.newValue))
        fd.append('oldValue', JSON.stringify(this.oldValue))
        fd.append('file', this.imageList[0])
        // console.log(this.imageList[0])
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
    handleChange (file, fileList) {
      // this.imageList = []
      this.destinationData.url = URL.createObjectURL(file.raw)
      // this.newValue.filetype = this.getFileFormat(file.name)
      // this.imageList.push(file)
    },
    BeforeSubmit (file) {
      // console.log('准备提交')
      this.imageList = []
      this.destinationData.filetype = this.getFileFormat(file.name)
      this.imageList.push(file)
      return false
    },
    getFileFormat (fileName) {
      let name = []
      name = fileName.split('.')
      // console.log(name[1])
      return '.' + name[1]
    },
    initData () {
      this.imageList = []   // 图片数组清空
      this.imageSize = []
      // console.log('激活')
      this.bindDestination = this.ForDestination
      for (let i = 0; i < GlobalConfig.imageSize.length; i++) {
        let label = {text: GlobalConfig.imageSize[i], value: GlobalConfig.imageSize[i]}
        this.imageSize.push(label)
      }
      // console.log(this.airport)
    },
    setEditData () {   // 从服务器读取表单数据
      this.destinationData.airportcode = this.EditFormData.airportcode
      this.destinationData.destination = this.EditFormData.imagename
      this.destinationData.imageSize = this.EditFormData.imagesize
      this.destinationData.description = this.EditFormData.description
      this.destinationData.url = this.preview(this.EditFormData)
      this.setOldValue()
    },
    preview (row) {
      let urlPart = GlobalConfig.urlPart
      let queryPara = GlobalConfig.queryPara
      let finalUrl = urlPart + queryPara + row.id + '&url=' + row.url + '&updatetime=' + row.updateTime
      return finalUrl
    },
    show (filedsData) {
      this.getUrl()
      // console.log(filedsData)
      this.imageList = []
      this.EditFormData = {}
      this.EditFormData = filedsData
      this.SubscribeAirports()
      this.initData()
      this.setEditData()
      this.setAirportInfo()
      this.visible = true
    },
    clearImage () {
      this.destinationData.url = ''
      this.imageList = []
    },
    clearAllImage () {
      this.destinationData.url = ''
    },
    handleClose () {
      this.visible = false
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
