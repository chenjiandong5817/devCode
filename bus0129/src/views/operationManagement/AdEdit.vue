<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <el-row>
      <el-col :span="14">
         <el-row class="data-row" >
            <el-col :span="2">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row v-loading="aliListLoading">
                  <el-col :span="24">
                     <div class="text-show">
                       支付宝广告语：
                     </div>
                     <div class="text-show">
                       {{ aliPay }}
                     </div>
                  </el-col>
               </el-row>
               <el-row style="margin-top: 40px">
                  <el-col :span="16">
                     <el-input v-model="aliPayParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="2">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="6">
                     <el-button size="large" type="primary" @click="aliPayClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <!-- <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col> -->
          </el-row>
          <el-row class="data-row" >
            <el-col :span="2">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row v-loading="weListLoading">
                  <el-col :span="24">
                     <div class="text-show">
                       微信广告语：
                     </div>
                     <div class="text-show">
                       {{ weChat }}
                     </div>
                  </el-col>
               </el-row>
               <el-row style="margin-top: 40px">
                  <el-col :span="16">
                     <el-input v-model="weChatParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="2">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="6">
                     <el-button size="large" type="primary" @click="weChatClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <!-- <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col> -->
          </el-row>
          <el-row class="data-row" >
            <el-col :span="2">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row v-loading="unionListLoading">
                  <el-col :span="24">
                     <div class="text-show">
                       银联广告语：
                     </div>
                     <div class="text-show">
                       {{ unionPay }}
                     </div>
                  </el-col>
               </el-row>
               <el-row style="margin-top: 40px">
                  <el-col :span="16">
                     <el-input v-model="unionPayParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="2">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="6">
                     <el-button size="large" type="primary" @click="unionPayClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <!-- <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col> -->
          </el-row>
      </el-col>
      <!-- 图片上传 -->
      <el-col :span="10">
        <el-row style="margin-top: 40px;">
          <el-col :span="3">
              <div style="width: 5px;height: 5px;">
              </div>
           </el-col>
          <el-col :span="21">
            <div style="font-size: 25px;">
              请上传图片，该图片用于广告显示
            </div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 50px;">
           <el-col :span="3">
              <div style="width: 5px;height: 5px;">
              </div>
           </el-col>
           <el-col :span="21">
             <el-upload
              action="https://jsonplaceholder.typicode.com/posts/"
              list-type="picture-card"
              ref="uploadImgBlock"
              :before-upload="beforeUpload"
              :auto-upload="autoUpload"
              :file-list="fileList"
              :on-preview="handlePictureCardPreview"
              :on-remove="removeFile"
              :on-change="uploadChange">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
           </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="3">
            <div style="width: 5px;height: 5px;">
            </div>
          </el-col>
          <el-col :span="21">
            <el-button type="primary" @click="uploadImg">上传图片</el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </section>
</template>

<script>
  import API from './../../api'
  import Util from './../../common/js/util'
  export default {
    data () {
      return {
        id: '',
        aliPay: null,
        weChat: null,
        unionPay: null,
        aliPayParam: '',
        weChatParam: '',
        unionPayParam: '',
        adContent: null,
        aliListLoading: false,
        weListLoading: false,
        unionListLoading: false,
        // 图片的链接
        dialogImageUrl: '',
        // 是否放大图片的显示
        dialogVisible: false,
        // 存储图片的信息用于显示的
        fileGetted: [{name: 'adImg', url: ''}],
        // 用于显示
        fileList: [],
        // 是否自动上传
        autoUpload: false,
        // 存储图片的信息用于上传的
        fileUploaded: [],
        // uid图片的
        uoloadImgUid: null,
        API: API
      }
    },
    computed: {
    },
    components: {
    },
    methods: {
      getAdInfo () {
        this.listLoading = true
        API.getAdContent().go(this.id).then((data) => {
          if (data.status === 1) {
            this.adContent = data.data
            this.aliPay = data.data[0].content
            this.weChat = data.data[1].content
            this.unionPay = data.data[2].content
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      aliPayClick () {
        var para = {
          id: this.adContent[0].id,
          title: this.adContent[0].title,
          content: this.aliPayParam
        }
        this.aliListLoading = true
        API.editAdContent().go(para).then((data) => {
          if (data.status === 1) {
            this.getAdInfo()
            this.$notify(Util.notifyBody(true, data.message))
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.aliListLoading = false
        })
      },
      weChatClick () {
        var para = {
          id: this.adContent[1].id,
          title: this.adContent[1].title,
          content: this.weChatParam
        }
        this.weListLoading = true
        API.editAdContent().go(para).then((data) => {
          if (data.status === 1) {
            this.getAdInfo()
            this.$notify(Util.notifyBody(true, data.message))
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.weListLoading = false
        })
      },
      unionPayClick () {
        var para = {
          id: this.adContent[2].id,
          title: this.adContent[2].title,
          content: this.unionPayParam
        }
        this.unionListLoading = true
        API.editAdContent().go(para).then((data) => {
          if (data.status === 1) {
            this.getAdInfo()
            this.$notify(Util.notifyBody(true, data.message))
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.unionListLoading = false
        })
      },
      // 点击已经上传的文件的函数
      handlePictureCardPreview (file) {
        this.dialogImageUrl = file.url
        this.dialogVisible = true
      },
      // 上传文件前的钩子函数
      beforeUpload (file) {
        let fd = new FormData()
        fd.append('id', 3)
        fd.append('fileName', '测试.jpg')
        fd.append('multipartFile', file)
        API.uploadPicture(fd).then((data) => {
          if (data.data.status === 1) {
            this.getPicture()
            this.$notify(Util.notifyBody(true, data.data.message))
          } else {
            this.$notify(Util.notifyBody(false, data.data.message))
          }
        })
        return false
      },
      // 文件状态改变时的函数
      uploadChange (file, fileList) {
        this.fileUploaded = fileList
      },
      removeFile (file, fileList) {
        this.fileUploaded = fileList
      },
      uploadImg () {
        var isJPG = null
        if (this.fileUploaded.length !== 1) {
          this.$notify(Util.notifyBody(false, '上传失败，只能上传一张图片！'))
          return
        }
        if (this.fileUploaded.length !== 0) {
          if (this.uoloadImgUid === this.fileUploaded[0].uid) {
            this.$notify(Util.notifyBody(false, '上传失败，上传的图片是相同的！'))
            return
          }
        }
        if (this.fileUploaded.length !== 0) {
          isJPG = this.fileUploaded[0].raw.type
          if (isJPG !== 'image/jpeg') {
            this.$notify(Util.notifyBody(false, '上传失败，只能上传JPG 格式的图片！'))
            return
          }
        }
        if (this.uoloadImgUid !== this.fileUploaded[0].uid && this.fileUploaded.length === 1 && isJPG === 'image/jpeg') {
          this.$refs.uploadImgBlock.submit()
        }
      },
      getPicture () {
        this.fileList = []
        API.getAdPicture().go(3).then((data) => {
          var headFlow = 'data:image/jpg;base64,'
          var afterFlow = data
          var imgUrl = headFlow + afterFlow
          this.fileGetted[0].url = imgUrl
          this.fileGetted[0]['raw'] = {}
          this.fileGetted[0]['raw']['type'] = 'image/jpeg'
          // 使用push赋值
          this.fileList.push(this.fileGetted[0])
          this.fileUploaded = this.fileGetted
          this.uoloadImgUid = this.fileUploaded[0].uid
        })
      }
    },
    mounted () {
      this.getAdInfo()
      this.getPicture()
    }
  }
</script>

<style scoped>
  .text-show {
    display: inline-block;
    font-family: 'PingFangSC-Regular', 'PingFang SC';
    font-weight: 400;
    font-style: normal;
    font-size: 20px;
  }
  @media screen and (min-height: 0px) and (max-height: 767px) {
    .data-row {
      height: 135px;
      margin-top: 46px;
    }
  }
  @media screen and (min-height: 768px) and (max-height: 1080px) {
    .data-row {
      height: 200px;
      margin-top: 65px;
    }
  }
</style>
