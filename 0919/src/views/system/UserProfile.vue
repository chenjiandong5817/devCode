/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:55:04
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-05-26 16:18:40
 * @Description:  用户信息界面
 */
<template>
  <el-dialog title="个人中心" v-model="visible" :close-on-click-modal="false"  @close="handleClose" size="tiny" custom-class="profile">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-form :model="userProfile" label-width="80px" :rules="rules" ref="userProfileForm">
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="userProfile.nickname"></el-input>
          </el-form-item>
          <el-form-item prop="gender" label="性别" >
            <el-radio-group v-model="userProfile.gender" size="small">
              <el-radio-button :label="1">男</el-radio-button>
              <el-radio-button :label="0">女</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="phone" label="手机" >
            <el-input type="phone" v-model.number="userProfile.phone" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="userProfile.email">
              <template slot="append">
                <el-button @click.native="sendValidMail" :loading="validMaidSending" :disabled="sendValidMailed" ref="sendValidMailBtn" v-if="!userProfile.emailChecked">
                  {{sendValidMailed ? '已发送' : '发送验证邮件'}}
                </el-button>
                <el-button :disabled="true" v-else>已验证</el-button>
              </template>
            </el-input>
          </el-form-item>
          <!--<el-form-item label="邮箱">
            <el-col :span="12" :gutter="0">
              <el-input v-model="userProfile.email"></el-input>
            </el-col>
            <el-col :span="12" :gutter="0">
              <el-button @click.native="sendValidMail" :loading="validMaidSending" :disabled="sendValidMailed" ref="sendValidMailBtn" v-if="!userProfile.emailChecked">
                  {{sendValidMailed ? '已发送' : '发送验证邮件'}}
              </el-button>
              <el-button :disabled="true" v-else>已验证</el-button>
            </el-col>
          </el-form-item>-->
          <el-form-item label="地址" prop="location">
            <el-input v-model="userProfile.location"></el-input>
          </el-form-item>
          <el-form-item label="自我介绍" prop="description">
            <el-input type="textarea" v-model="userProfile.description"></el-input>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="8">
        <el-upload
          class="avatar-uploader"
          :action="API.updateUserAvatar().url"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar img_height_width">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>


    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import Util from './../../common/js/util'
  import Rules from './../../common/js/rules'
  import API from './../../api'
  export default {
    data () {
      return {
        userProfile: {
          name: '',
          gender: 0,
          email: '',
          phone: '',
          location: '',
          description: ''
        },
        API: API,
        imageUrl: API.getUserAvatar().url,
        visible: false,
        loading: false,
        validMaidSending: false,
        sendValidMailed: false,
        rules: {
          email: [
            { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
          ],
          phone: [
            { type: 'number', message: '手机必须为数字值' },
            { validator: Rules.telephone, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleClose () {
      },
      handleSubmit () {
        this.$refs['userProfileForm'].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.loading = true
              let para = Object.assign({}, this.userProfile)
              API.updateUserProfile().go(para).then((res) => {
                this.loading = false
                this.$notify(Util.notifyBody(res.ok, res.msg))
                this.$refs['userProfileForm'].resetFields()
                this.$parent.$parent.$emit('updateUser')
                this.visible = false
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
      show () {
        this.getUserProfile()
        this.checkUserAvatar()
        this.visible = true
      },
      sendValidMail () {
        if (!this.userProfile.email) {
          return
        }
        this.$refs['userProfileForm'].validateField('email', (errorMsg) => {
          if (errorMsg === '') {
            this.validMaidSending = true
            API.activeMail().go().then(res => {
              if (res.ok) {
                this.sendValidMailed = true
              }
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.validMaidSending = false
            })
          }
        })
      },
      handleAvatarSuccess (res, file) {
        this.imageUrl = URL.createObjectURL(file.raw)
        this.$parent.$parent.$emit('updateAvatar')
      },
      beforeAvatarUpload (file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      getUserProfile () {
        API.getUserProfile().go({}).then(res => {
          res.phone = res.phone ? Number(res.phone) : null
          this.userProfile = res
        })
      },
      checkUserAvatar () {
        API.getUserAvatar().go().then(res => {
          if (!res) {
            this.imageUrl = ''
          }
        })
      }
    },
    components: {
      Rules: Rules
    },
    mounted () {
    }
  }
</script>
<style>
  .profile {
    min-width: 426px;
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
    width: 108px;
    height: 108px;
    line-height: 108px !important;
    text-align: center;
  }
  .avatar {
    width: 108px;
    height: 108px;
    display: block;
  }
  .img_height_width {
    width: 100%!important;
    height: 100%!important;
  }
</style>
