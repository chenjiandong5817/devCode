/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:44:47
 */

 <template>
    <!--<el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" class="demo-ruleForm" >-->
    <el-form ref="staticName" v-show="visible" v-model="checkinFiled">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-tag type="success">上传一张图片并完善信息</el-tag>
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
            <el-form-item label="文件命名">
              <el-input v-model="checkinFiled.namedFile" placeholder="请输入文件名"></el-input>
            </el-form-item>
            <el-form-item label="尺寸">
              <el-select v-model="checkinFiled.imageSize" placeholder="请选择尺寸" class="maxWidth" :filterable="true">
                <el-option v-for="item in imageSize" :key="item.value" :label="item.text" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="格式">
              <el-input v-model="checkinFiled.imageFormat" disabled placeholder="提交图片后系统生成"></el-input>
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="checkinFiled.imageDescribe" placeholder="添加描述,选填" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-upload
              ref="staticUpload"
              class="avatar-uploader"
              :action="imageServerUrl"
              :headers="headersUpload"
              :show-file-list="false"
              :multiple="true"
              :on-change = "changeItem"
              :before-upload="beforeUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            </br>
            <el-button type="info" @click="clearImage" icon="delete" class="buttonwidth">清空图片</el-button>
          </el-col>
        </el-row>
        <i class="el-icon-warning" v-show="repeat"><font size="3" color="red"> 提交的时候请注意:该条记录已存在于数据库，不过即便如此，您还是可以选择继续提交。</font></i>
      </el-card>
    </el-form>
</template>

 <script>
import GlobalConfig from '../../../common/config/global'
import API from './../../../api'
import LoginInfo from './../../../vuex/store'
import axiosHelper from '../../../common/js/axios-helper'
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
    getNewInfo: {
      type: Function,
      default: function () { }
    }
  },
  data () {
    return {
      repeat: false,
      headersUpload: null,
      uploadImageList: [], // 要上传的图片列表
      uploadTextList: [], // 新增的文本List
      uploadEditList: [],  // oldValue
      imageSize: [],
      imageUrl: '',
      visible: false,
      loading: false,
      oldValue: {},
      subscribeAirportsLables: [],
      subscribeAirports: [],
      checkinFiled: { namedFile: '', imageSize: '', imageFormat: '', imageDescribe: '', airportCode: '' },
      fileds: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: ''},
      addOrUpdate: '',
      filedText: {id: '', createBy: '', createTime: '', description: '', filetype: '', imagename: '', imagesize: '', imagetype: '', updateBy: '', updateTime: ''},
      filters: {imagename: '', imagesize: '', imagetype: '', filetype: '', airportcode: ''},
      imageServerUrl: ''
    }
  },
  watch: {
    checkinFiled: {
      handler: function (val) {
        if (val.airportCode !== '' && val.imageFormat !== '' && val.namedFile !== '' && val.imageSize !== '') {
          // console.log(val.namedFile)
          // 可以提交,并做验证
          this.filters.imagename = val.namedFile
          this.filters.imagesize = val.imageSize
          this.filters.imagetype = 'static'
          this.filters.filetype = val.imageFormat
          this.filters.airportcode = val.airportCode
          let para = Object.assign({}, this.filters)
          API.checkImageExist().go(para).then((data) => {
            if (data.ok) {
              this.repeat = false
              // console.log('验证成功没有重复!')
              // console.log(this.deviceinfos)
              // alert(JSON.stringify(this.deviceinfos))
            } else {
              this.repeat = true
              // console.log('重复了')
            }
          })
        } else {
          // 没填完不可以提交
        }
      },
      deep: true
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
      this.checkinFiled.airportCode = airportData
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
    changeItem (file, fileList) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    setData () { // 设置数据
      this.uploadTextList = []
      let data = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', id: ''}
      data.imagetype = 'static'
      data.imagename = this.checkinFiled.namedFile
      data.imagesize = this.checkinFiled.imageSize
      data.filetype = this.checkinFiled.imageFormat
      data.description = this.checkinFiled.imageDescribe
      data.airportcode = this.checkinFiled.airportCode
      data.id = this.filedText.id
      // console.log(data)
      this.uploadTextList.push(data)
    },
    setEditData () {
      this.uploadEditList = []
      let data = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: '', url: '', id: ''}
      data.imagetype = 'static'
      data.id = this.filedText.id
      data.imagename = this.checkinFiled.namedFile
      data.imagesize = this.checkinFiled.imageSize
      data.filetype = this.checkinFiled.imageFormat
      data.description = this.checkinFiled.imageDescribe
      data.airportcode = this.checkinFiled.airportCode
      data.url = this.filedText.url
      this.uploadEditList.push(data)
    },
    submit () {
      // console.log(this.headersUpload)
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
      this.headersUpload = axiosHelper.generatAuthHeader()
      this.setData()
      let fd = new FormData()
      if (this.addOrUpdate === 'addFunc') {
        for (let i = 0; i < this.uploadTextList.length; i++) {
          fd.append('newValue', JSON.stringify(this.uploadTextList[i]))
        }
        // fd.append('newValue', this.uploadTextList)
        // console.log(this.uploadTextList[0].airportcode)
        // console.log(this.uploadImageList[0])
        fd.append('file', this.uploadImageList[0])
        // console.log(this.uploadImageList[0])
        // for (let i = 0; i < this.uploadImageList.length; i++) {
        //   fd.append('file', this.uploadImageList[i])
        // }
        // fd.append('file', this.uploadImageList)
        // console.log(this.fileds)
        // console.log(file)
        API.submitPostFile(fd).then(res => {
          if (res.data.ok === true) {
            this.$notify({
              title: '成功',
              message: '上传成功',
              type: 'success'
            })
            this.getNewInfo()
            this.callback()
          } else {
            this.$notify({
              title: '失败',
              message: '上传失败',
              type: 'error'
            })
            this.getNewInfo()
            this.callback()
          }
        })
        } else {
          for (let i = 0; i < this.uploadTextList.length; i++) {
            fd.append('newValue', JSON.stringify(this.uploadTextList[i]))
            fd.append('oldValue', JSON.stringify(this.uploadEditList[i]))
          }
          // fd.append('newValue', this.uploadTextList)
          // console.log(this.uploadTextList[0].airportcode)
          // console.log(this.uploadImageList[0])
          fd.append('file', this.uploadImageList[0])
          // console.log(this.uploadImageList[0])
          // for (let i = 0; i < this.uploadImageList.length; i++) {
          //   fd.append('file', this.uploadImageList[i])
          // }
          // fd.append('file', this.uploadImageList)
          // console.log(this.fileds)
          // console.log(file)
          API.submitPutFile(fd).then(res => {
            if (res.data.ok === true) {
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success'
              })
              this.getNewInfo()
              this.callback()
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
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    beforeUpload (file) {
      this.uploadImageList = []
      this.checkinFiled.imageFormat = this.getFileFormat(file.name)
      this.uploadImageList.push(file)
      return false
    },
    preview (row) {
      let urlPart = GlobalConfig.urlPart
      let queryPara = GlobalConfig.queryPara
      let finalUrl = urlPart + queryPara + row.id + '&url=' + row.url + '&updatetime=' + row.updateTime
      this.imageUrl = finalUrl
      // console.log(this.imageUrl)
    },
    setEditValue () {
      // checkinFiled: { namedFile: '', imageSize: '', imageFormat: '', imageDescribe: '', airportCode: '' },
      this.checkinFiled.namedFile = this.filedText.imagename
      this.checkinFiled.imageSize = this.filedText.imagesize
      this.checkinFiled.imageFormat = this.filedText.filetype
      this.checkinFiled.imageDescribe = this.filedText.description
      this.checkinFiled.airportCode = this.filedText.airportcode
      this.preview(this.filedText)
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
      if (this.addOrUpdate === 'addFunc') {
        this.$refs['city'].setValue('')
      } else {
        this.$refs['city'].setValue(this.checkinFiled.airportCode)
      }
    },
    show: function (fileds) {
      this.getUrl()
      this.repeat = false
      this.filedText = fileds  // 编辑该行的数据
      this.checkinFiled = { namedFile: '', imageSize: '', imageFormat: '', imageDescribe: '', airportCode: '' }
      this.setEditValue()
      if (fileds.id === '') {
        this.imageUrl = ''
      }
      this.setEditData()  // 设置oldValue
      this.SubscribeAirports()
      this.initData()
      this.setAirportInfo()
      this.visible = true
    },
    initData () {   // 每次show的时候初始化object
      this.imageSize = []
      for (let i = 0; i < GlobalConfig.imageSize.length; i++) {
        let label = {text: GlobalConfig.imageSize[i], value: GlobalConfig.imageSize[i]}
        this.imageSize.push(label)
      }
    },
    resetForm (formName) {
      // console.log(formName)
      this.$refs[formName].resetFields()
      this.checkinFiled.namedFile = ''
    },
    handleClose () {
      this.repeat = false
      this.visible = false
    },
    getFileFormat (fileName) {
      let name = []
      name = fileName.split('.')
      return '.' + name[1]
    },
    clearAllImage () {
      this.imageUrl = ''
    },
    clearImage () {
      this.imageUrl = ''
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
    clear: both;
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
    height: 138px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 138px;
    display: block;
  }
  .selectAirportStyle {
    width: 100%;
  }
  .buttonwidth {
    width: 178px;
  }
</style>
