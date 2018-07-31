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
      <el-col>
         <el-row class="data-row" >
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row>
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
                  <el-col :span="8">
                     <el-input v-model="aliPayParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="1">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="8">
                     <el-button size="large" @click="aliPayClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
          </el-row>
          <el-row class="data-row" >
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row>
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
                  <el-col :span="8">
                     <el-input v-model="weChatParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="1">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="8">
                     <el-button size="large" @click="weChatClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
          </el-row>
          <el-row class="data-row" >
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
            <el-col :span="22">
               <el-row>
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
                  <el-col :span="8">
                     <el-input v-model="unionPayParam" placeholder="请输入变更的广告语" size="large"></el-input>
                  </el-col>
                  <el-col :span="1">
                     <div style="width: 5px;height: 5px;">
                     </div>
                  </el-col>
                  <el-col :span="8">
                     <el-button size="large" @click="unionPayClick">提交更改</el-button>
                  </el-col>
               </el-row>
            </el-col>
            <el-col :span="1">
              <div style="width: 5px;height: 5px;">
              </div>
            </el-col>
          </el-row>
      </el-col>
    </el-row>
  </section>
</template>

<script>
  export default {
    data () {
      return {
        aliPay: null,
        weChat: null,
        unionPay: null,
        aliPayParam: '',
        weChatParam: '',
        unionPayParam: ''
      }
    },
    computed: {
    },
    components: {
    },
    methods: {
      getAdInfo () {
        this.aliPay = '推荐支付宝支付，满十元立减5元'
        this.weChat = '推荐微信支付，满十元立减5元'
        this.unionPay = '推荐银联支付，满十元立减5元'
      },
      aliPayClick () {
        console.log('aliPayParam', this.aliPayParam)
      },
      weChatClick () {
        console.log('weChatParam', this.weChatParam)
      },
      unionPayClick () {
        console.log('unionPayParam', this.unionPayParam)
      }
    },
    mounted () {
      this.getAdInfo()
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
