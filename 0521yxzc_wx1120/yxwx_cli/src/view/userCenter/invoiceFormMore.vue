<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
          <div>
            <p class="title">更多信息</p>
            <div class="content">
                <div class="form-item">
                    <label class="form-label">备注说明</label>
                    <div class="input-item">
                        <input type="text" placeholder="填写备注说明" v-model="message" maxlength="50">
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">地址</label>
                    <div class="input-item">
                        <input type="text" placeholder="填写注册地址" v-model="address" maxlength="60">
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">电话</label>
                    <div class="input-item">
                        <input type="text" placeholder="填写注册电话" v-model="telephone" maxlength="60">
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">开户行及账号</label>
                    <div class="input-item">
                        <input type="text" placeholder="填写开户银行及银行账号" v-model="account" maxlength="30">
                    </div>
                </div>
            </div>
          </div>
            <p class="title absbottom">
                <cube-button @click="sure">确认</cube-button>
            </p>
        </div>
    </transition>
</template>
<script>
export default{
  data () {
    return {
      message: this.$store.state.message,
      telephone: this.$store.state.telephone,
      address: this.$store.state.address,
      account: this.$store.state.account
    }
  },
  methods: {
    // 提交更多信息
    sure () {
      this.$store.commit('SET_MESSAGE', this.message)
      this.$store.commit('SET_ADDRESS', this.address)
      this.$store.commit('SET_TELEPHONE', this.telephone)
      this.$store.commit('SET_ACCOUNT', this.account)
      this.$router.go(-1)
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  position: fixed;
  overflow-y:scroll; 
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  bottom: 0;
  left: 0;
  overflow: hidden;
  background: #f3f3f3;
  animation-duration: .3s;
  p.title {
    padding: 5px 15px;
    font-size: 12px;
    line-height: 1.5;
    color: #a9abb0;
  }
  .content {
    padding-left: 15px;
    color: #4f5a67;
    font-size: 14px;
    background: #ffffff;
    .form-item {
      display: flex;
      position: relative;
      padding-right: 15px;
      //height: 50px;
      line-height: 50px;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
        /* &:after {
          content: '';
          position: absolute;
          bottom: 0;
          width: 100%;
          height: .5px;
          background: #f3f3f3;
        } */
      }
      label.form-label {
        width: 100px;
      }
      .input-item {
        flex-grow: 1;
        input {
          width: 100%;
          outline: none;
          &::-webkit-input-placeholder {
            color: #c5c7ca;
          }
        }
        .checkRadio {
          display: flex;
          align-items: center;
          width: 100px;
        }
        .more {
          color: #c5c7ca;
          &:after {
            content: '';
            position: absolute;
            border-right: 1px solid #c5c7ca;
            border-bottom: 1px solid #c5c7ca;
            width: 8px;
            height: 8px;
            -webkit-transform: rotate(-45deg);
            transform: rotate(-45deg);
            top: 50%;
            margin-top: -2px;
            right: 15px;
          }
        }
      }
    }
  }
  .absbottom{
    margin-bottom: 15px;
    width: 100%;
    box-sizing: border-box;
  }
}

</style>>