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
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
  import { requestLogin } from '../api/login'
  // import authorityHelper from './../common/js/authority-helper'
  export default {
    data () {
      return {
        logining: false,
        ruleForm: {
          account: 'admin',
          checkPass: '123456'
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' }
            // { validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' }
            // { validator: validaePass2 }
          ]
        },
        checked: true
      }
    },
    methods: {
      handleReset () {
        this.$refs.ruleForm.resetFields()
      },
      handleSubmit (ev) {
        // var _this = this
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            // _this.$router.replace('/table');
            this.logining = true
            var loginParams = { username: this.ruleForm.account, password: this.ruleForm.checkPass }
            requestLogin().go(loginParams).then(data => {
              this.logining = false
              if (!data.ok) {
                this.$notify({
                  title: '错误',
                  message: data.msg || '',
                  type: 'error'
                })
              } else {
                let { user, at } = data.attr
                localStorage.setItem('AdminId', data.attr.user.id)
                // 装换成json
                var AdminSubscribeAirportJson = JSON.stringify(data.attr.user.aiisAirports)
                localStorage.setItem('AdminSubscribeAirportJson', AdminSubscribeAirportJson)
                this.$store.dispatch('saveUserStorage', { user: user, token: at })
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
      }
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
