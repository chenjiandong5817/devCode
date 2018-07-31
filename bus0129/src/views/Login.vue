/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:55:39
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-06-01 10:40:50
 * @Description:  登陆界面
 */
<template>
  <el-form :model="ruleForm" :rules="rules2" ref="ruleForm" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="enterConfirmationCode">
      <el-col :span="17">
         <!-- 验证码 -->
                <el-input type="text" v-model="ruleForm.enterConfirmationCode" auto-complete="off" placeholder="输入验证码"></el-input>
      </el-col>
      <el-col :span="2">
                 <div style="width: 5px;height: 5px;"></div>
      </el-col>
      <el-col :span="5">
          <!-- 输入验证码 -->
                <div style="width: 63px;height: 36px;float: right;">
                  <img :src="imgSrc" @click="imgRandom">
                </div>
      </el-col>
    </el-form-item>
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import API from './../api'
  import util from './../common/js/util.js'
  export default {
    data () {
      return {
        logining: false,
        ruleForm: {
          account: 'admin',
          checkPass: '',
          enterConfirmationCode: null
        },
        rules2: null,
        checked: true,
        // 验证码信息
        validateInfo: null,
        imgSrc: null,
        API: API
      }
    },
    methods: {
      // 输入框验证的函数
      inputValidate () {
        this.$nextTick(() => {
          this.rules2 = {
            account: [
              { required: true, message: '请输入账号', trigger: 'blur' }
            ],
            checkPass: [
              { required: true, message: '请输入密码', trigger: 'blur' }
            ],
            enterConfirmationCode: [
              { required: true, message: '请输入验证码', trigger: 'blur' }
            ]
          }
        })
      },
      // 登录时启动的函数
      handleSubmit (ev) {
        // var _this = this
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            // _this.$router.replace('/table');
            this.logining = true
            var loginParams = { username: this.ruleForm.account, password: this.ruleForm.checkPass, identifyCode: this.ruleForm.enterConfirmationCode }
            API.busRequestLogin().go(loginParams).then(data => {
              this.logining = false
              if (!data.status === 1) {
                this.$notify(util.notifyBody(data.status, data.message))
              } else {
                let userId = data.data.userId
                let token = data.data.token
                let userName = data.data.userName
                let user = {
                  id: userId,
                  name: userName,
                  profile: {
                    createTime: '2017-05-22 15:08:50',
                    description: '666',
                    email: '526327731@qq.com',
                    emailChecked: false,
                    gender: '0',
                    location: '666',
                    nickname: userName,
                    phone: '18111111111',
                    updateTime: '2018-02-28 15:08:49',
                    userId: '60c91e701df64692b46f7329331b4440'
                  }
                }
                localStorage.setItem('userName', data.data.userName)
                this.$store.dispatch('saveUserStorage', { user: user, token: token })
                this.$router.push({ path: '/home' })
              }
            }).catch(res => {
              this.logining = false
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      // 验证码信息图片的获取
      getValidateInfo () {
        API.busValidateImage().go().then(data => {
          var headFlow = 'data:image/jpg;base64,'
          var afterFlow = data
          this.imgSrc = headFlow + afterFlow
         })
      },
      // 每次点击图片，发生变化
      imgRandom () {
        this.getValidateInfo()
      }
    },
    mounted () {
      this.inputValidate()
      this.getValidateInfo()
    }
  }
</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin-bottom: 20px;
    background-color: #F9FAFC;
    margin: 180px auto;
    border: 2px solid #8492A6;
    width: 350px;
    padding: 35px 35px 15px 35px;
  }

  .login-container .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login-container .remember {
    margin: 0px 0px 35px 0px;
  }
</style>
