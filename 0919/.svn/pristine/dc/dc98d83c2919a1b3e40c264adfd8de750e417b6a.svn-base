/*
 * @Author: chenyang
 * @Date: 2017-09-21 17:14:14
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:23:08
 */
// 1.输入目的地 2.对图片名称赋值 3.选择图片时在图片提交前获取格式 4.图片尺寸已定 5.数据获取完毕准备提交
 <template>
    <!--<el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" class="demo-ruleForm" >-->
    <el-form v-show="visible">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-tag type="warning">选择目的地并添加不同尺寸的图片</el-tag>
        </el-row>
        </br>
        <el-form-item label="选择机场">
          </br>
          <city-name
            class="selectAirportStyle"
            ref="city"
            v-on:getAirportName = "getAirportCode">
          </city-name>
        </el-form-item>
        <el-form-item label="目标地点">
          <el-select v-model="destinationCity" placeholder="选择目的地" @change="destinationClick" :filterable="true" class="maxWidth">
            <el-option v-for="item in bindDestination" :key="item.value" :label="item.text" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="description" placeholder="添加描述,选填" type="textarea"></el-input>
        </el-form-item>
        <el-row :gutter="20">
            <div v-for="(item, index) in sizeList" :key="item.imageSize">
              <el-col :span="6" @click.native.capture="uploadClick(index)">
                </br>
                <el-upload
                  class="avatar-uploader"
                  :action="imageServerUrl"
                  :show-file-list="false"
                  :headers="headersUpload"
                  :on-change="handleChange"
                  :multiple="true"
                  :before-upload="BeforeSubmit">
                  <img v-if="item.url" :src="item.url" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <el-tag type="success">尺寸: {{item.imageSize}}</el-tag>
                <el-button type="info" @click="clearImages(index)" icon="delete" class="buttonwidth" size="mini">清空图片</el-button>
              </el-col>
            </div>
        </el-row>
      </el-card>
    </el-form>
</template>

<script>
import GlobalConfig from '../../../common/config/global'
import axiosHelper from '../../../common/js/axios-helper'
import LoginInfo from './../../../vuex/store'
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
    ForDestination: {
      type: Array
    }
  },
  components: {
    cityName: CityName
  },
  data () {
    return {
      order: [], // 记录元素下标，按添加顺序匹配数据
      subscribeAirports: [],
      subscribeAirportsLables: [],
      headersUpload: null,
      sizeList: [],  // 存放size
      destinationData: {url: '', destination: '', imageFormat: '', imageSize: '', imageName: ''},
      description: '',
      destinationCity: '',
      airportCode: '',
      currentClick: -1,
      visible: false,
      loading: false,
      oldValue: {},
      bindDestination: [],
      fileds: {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: ''},
      textList: [], // 上传的文本信息
      imageList: [], // 上传的图片信息
      imageServerUrl: ''
    }
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
    setFunc (func) {
      this.addOrUpdate = func
      // console.log(this.addOrUpdate)
    },
    submit () {
      this.textList = []
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.headersUpload = axiosHelper.generatAuthHeader()
        let fd = new FormData()
        if (this.addOrUpdate === 'addFunc') {
          for (let i = 0; i < this.order.length; i++) {
            let index = this.order[i]
            // console.log(index)
            this.fileds.imagetype = 'destination'
            this.fileds.imagesize = this.sizeList[index].imageSize
            this.fileds.imagename = this.destinationCity
            this.fileds.filetype = this.sizeList[index].imageFormat
            this.fileds.airportcode = this.airportCode
            this.fileds.description = this.description
            this.textList.push(this.fileds)
            // console.log(this.textList)
            this.fileds = {imagetype: '', imagesize: '', imagename: '', filetype: '', airportcode: '', description: ''}
          }
          for (let i = 0; i < this.textList.length; i++) {
            fd.append('newValue', JSON.stringify(this.textList[i]))
            // console.log(JSON.stringify(this.textList[i]))
          }
          // console.log(fd)
          for (let i = 0; i < this.imageList.length; i++) {
            // console.log(this.imageList[i].raw)
            fd.append('file', this.imageList[i].raw)
          }
          // console.log('order')
          // console.log(this.order)
          // console.log('textList')
          // console.log(this.textList)
          // console.log('imageList')
          // console.log(this.imageList)
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
    uploadClick (index) {
      this.currentClick = index
    },
    handleChange (file, fileList) {
      if (this.sizeList[this.currentClick].url === '') {
        this.imageList.push(file)
        this.order.push(this.currentClick)  // 记录元素下标
        this.sizeList[this.currentClick].url = URL.createObjectURL(file.raw)
        this.sizeList[this.currentClick].imageFormat = this.getFileFormat(file.name)
        this.sizeList[this.currentClick].imageName = this.destinationCity
        this.sizeList[this.currentClick].destination = this.destinationCity
      } else {
        let index = -1
        for (let i = 0; i < this.order.length; i++) {
          if (this.currentClick === this.order[i]) {
            index = i
            break
          }
        }
        console.log(index)
        if (index > -1) {
          this.imageList[index] = file
          this.sizeList[this.currentClick].url = URL.createObjectURL(file.raw)
          this.sizeList[this.currentClick].imageFormat = this.getFileFormat(file.name)
          this.sizeList[this.currentClick].imageName = this.destinationCity
          this.sizeList[this.currentClick].destination = this.destinationCity
        }
      }
      // console.log('这是第' + this.currentClick + '个')
      // console.log('尺寸为:' + this.sizeList[this.currentClick].imageSize)
      // console.log('格式为:' + this.sizeList[this.currentClick].imageFormat)
      // console.log('目的地为:' + this.sizeList[this.currentClick].destination)
      // console.log('图片名字为:' + this.sizeList[this.currentClick].imageName)
      // console.log('url为:' + this.sizeList[this.currentClick].url)
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
      this.$refs['city'].setValue('')  // 新增这里传空,编辑这里传旧值
    },
    clearDataForm () {
      this.textList = [] // 上传的文本信息
      this.imageList = [] // 上传的图片信息
      this.order = []
      this.airportCode = ''
      this.destinationCity = ''
      this.description = ''
      for (let i = 0; i < this.sizeList.length; i++) {
        this.clearImages(i)
      }
    },
    clearImage (index) {
      this.sizeList[index].destination = ''
      this.sizeList[index].imageFormat = ''
      this.sizeList[index].imageName = ''
      this.sizeList[index].url = ''
    },
    clearImages (index) {
      if (typeof (this.sizeList[this.currentClick]) === 'undefined') {
        return
      }
      let item = -1
      if (this.sizeList[this.currentClick].url !== '') {  // 不为空有数据要清空,order队列中元素要出队,imageList要出队,textList要出队
        for (let i = 0; i < this.order.length; i++) {
          if (this.currentClick === this.order[i]) {
            item = i
            break
          }
        }
      }
      if (item > -1) {
        this.order.splice(item, 1)
        this.imageList.splice(item, 1)
        this.textList.splice(item, 1)
        this.clearImage(this.currentClick)
      }
      // console.log(this.sizeList[this.currentClick])
    },
    clearDestination () {
      for (let i = 0; i < this.sizeList.length; i++) {
        this.sizeList[i].destination = ''
      }
    },
    BeforeSubmit (file) {
      return false
    },
    getSizeConfig () {  // 获取尺寸列表
      this.sizeList = []
      for (let i = 0; i < GlobalConfig.imageSize.length; i++) {
        this.destinationData = {url: '', destination: '', imageFormat: '', imageSize: '', imageName: ''}
        this.destinationData.imageSize = GlobalConfig.imageSize[i]
        this.destinationData.url = ''
        this.destinationData.destination = ''
        this.destinationData.imageFormat = ''
        this.destinationData.imageName = ''
        this.sizeList.push(this.destinationData)
      }
      // console.log(this.sizeList)
    },
    destinationClick () {
      for (let i = 0; i < this.sizeList.length; i++) {
        this.sizeList[i].imageName = this.destinationCity
        this.sizeList[i].destination = this.destinationCity
      }
    },
    getFileFormat (fileName) {
      let name = []
      name = fileName.split('.')
      // console.log(name[1])
      return '.' + name[1]
    },
    initData () {
      this.imageList = []   // 图片数组清空
      // console.log('激活')
      this.bindDestination = this.ForDestination
      // console.log(this.airport)
    },
    show: function () {
      this.getUrl()
      this.getSizeConfig()
      this.initData()
      this.SubscribeAirports()
      this.setAirportInfo()
      this.visible = true
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
    height: 138px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 138px;
    display: block;
  }
  .buttonwidth {
    width: 40%;
  }
  .selectAirportStyle {
    width: 100%;
  }
</style>
