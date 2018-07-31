<template>
    <div class="suggest-header">
        <div class="city-wrapper">
            <div class="city-content border-right-1px">
              <!-- 根据父级组件传参：type判断是否对子组件添加'.type_to'样式 -->
                <div class="city-select" v-bind:class="{'type_to':!location.starOrEnd}" v-show="!location.cityFocus" @click="switch_state({name:'cityFocus',val:true})">
                    <span>{{location.selectCity}}</span>
                </div>
                <div class="city-input-wrapper" v-show="location.cityFocus">
                    <input type="text"  ref="tipinput" placeholder="城市中文名或拼音" class="city-input" style="display: block;" v-on:keyup="switch_state({name:'cityInputing',val:true})" v-model="DistrictKeyWord">
                </div>
            </div>
        </div>
        <div class="address-wrapper">
            <input type="text" placeholder="请输入地点" class="address-input" @focus="switch_state({name:'cityFocus',val:false});switch_state({name:'addressFocus',val:true});switch_state({name:'cityInputing',val:false})" v-on:keyup="switch_state({name:'addressInputing',val:true})" v-model="addressKeyWord">
            <div class="clear" v-show="location.addressKeyWord">
                <i class="clearBtn" @click="switch_state({name:'addressKeyWord',val:''});switch_state({name:'tips',val:[]});switch_state({name:'addressInputing',val:false})"></i>
            </div>
        </div>
        <div class="cancel-wrapper">
            <span class="text" @click="resetLocation();back_home()">取消</span>
        </div>
    </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data () {
    return {
    }
  },
  computed: {
    ...mapState([
      'location',
      'DistrictKeyWord'
    ]),
    DistrictKeyWord: {
      get: function () {
        return this.$store.state.location.DistrictKeyWord
      },
      set: function (newVal) {
        this.$store.dispatch('switch_location', {name: 'DistrictKeyWord', val: newVal})
        this.$store.dispatch('CityListSearch', newVal)
      }
    },
    addressKeyWord: {
      get: function () {
        return this.$store.state.location.addressKeyWord
      },
      set: function (newVal) {
        this.$store.dispatch('switch_location', {name: 'addressKeyWord', val: newVal})
        this.$store.dispatch('autocomplete', newVal)
      }
    }
  },
  methods: {
    back_home () {
      this.$router.go(-1)
    },
    ...mapActions({
      'switch_state': 'switch_location',
      'DistrictSearch': 'DistrictSearch',
      'resetLocation': 'resetLocation'
    })
  },
  watch: {
    /* addressKeyWord (newVal, odlVal) {
      console.log(newVal)
      if (newVal !== odlVal) {
        this.$store.dispatch('switch_location', {name: 'addressKeyWord', val: newVal})
        this.$store.dispatch('autocomplete', newVal)
      }
    } */
  }
}
</script>
<style lang="less">
@import '../../assets/css/less/variable.less';
.suggest-header {
  position: relative;
  z-index: 9;
  display: flex;
  height: 44px;
  padding: 0 10px;
  white-space: nowrap;
  font-size: 14px;
  color: #7a7c81;
  background: #fff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.05);
  .city-wrapper {
    padding: 13px 0;
    .city-content {
      height: 18px;
      .city-select {
        line-height: 18px;
        padding: 0 29px 0 20px;
        &:before {
          content: '';
          position: absolute;
          width: 5px;
          height: 5px;
          border-radius: 50%;
          top: 50%;
          left: 7.5px;
          margin-top: -2.5px;
          background: #2fc68d;
        }
        &:after {
          content: '';
          position: absolute;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          top: 50%;
          right: 11.5px;
          margin-top: -2.5px;
          background: url('../../assets/images/arrow_down.png');
        }
        &.type_to{
          &:before{
            background: #ea7b83;
          }
        }
      }
      .city-input-wrapper {
        width: 130px;
        font-size: 14px;
        &:after {
          content: '';
          position: absolute;
          border-right: 1px solid #f5f5f5;
        }
      }
    }
  }
  .address-wrapper {
    position: relative;
    flex: 1;
    padding: 0 10px;
    .address-input {
      display: table-cell;
      width: 100%;
      padding: 13px 0;
      vertical-align: middle;
      font-size: 14px;
    }
    .clear{
      position: absolute;
      right: 0;
      top: 50%;
      width: 16px;
      height: 16px;
      margin-top: -8px;
      .clearBtn{
        display: block;
        width: 16px;
        height: 16px;
        background: url('../../assets/images/wrong.png');
      }
    }
  }
  .cancel-wrapper {
    display: inline-block;
    padding: 0 16px;
    margin-top: 15px;
    color: @fc_active;
    font-size: 14px;
  }
  input {
    width: 100%;
    outline: 0;
    border: 0;
    color: #7a7c81;
    overflow: hidden;
    &::-webkit-input-placeholder {
      color: #c5c7ca;
    }
  }
}
</style>
