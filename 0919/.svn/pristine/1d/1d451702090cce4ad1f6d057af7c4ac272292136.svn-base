/*
 * @Author: ylj
 * @Date: 2017-10-17 14:56:16
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-03 19:52:41
 */
<template>
  <div style="dispaly:inline">
    <!--<el-input v-model="value" v-show="false"></el-input>-->
    <el-input
      type="text"
      class="el-input__inner"
      v-model="value"
      ref="input"
      v-show="false"
    ></el-input>
    <el-date-picker
      v-if="visitDate"
      v-model="dateVal"
      type="date"
      :placeholder="datePlaceholder"
      :format="formatter"
      :style="dateStyle"
      :class="dateClass"
      :picker-options="pickerOptions"
      @change="setDateTimeVal"
      :disabled="allDisable || dateDisable">
    </el-date-picker>
    <el-input
      v-if="visitTime"
      v-model="timeVal"
      :placeholder="timePlaceholder"
      :style="timeStyle"
      :class="timeClass"
      @change="setDateTimeVal"
      :disabled="allDisable || timeDisable"></el-input>
  </div>
</template>

<script>
import Util from '../common/js/util'
import API from '../api'
export default {
  props: {
    value: {
      type: String,
      default: ''
    },
    formatter: {
      type: String,
      default: ''
    },
    visitDate: {
      type: Boolean,
      default: true
    },
    visitTime: {
      type: Boolean,
      default: true
    },
    allDisable: {
      tyle: Boolean,
      default: false
    },
    dateDisable: {
      tyle: Boolean,
      default: false
    },
    timeDisable: {
      tyle: Boolean,
      default: false
    },
    datePlaceholder: {
      type: String,
      default: ''
    },
    timePlaceholder: {
      type: String,
      default: ''
    },
    dateClass: {
      type: String,
      default: 'datelayout'
    },
    dateStyle: {
      type: String,
      default: 'width:65%'
    },
    timeStyle: {
      type: String,
      default: 'width:30%'
    },
    timeClass: {
      type: String,
      default: 'timelayout'
    }
  },
  watch: {
    value (curVal, oldVal) {
      this.bindData()
    }
  },
  data () {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '明天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }]
      },
      dateVal: '',
      timeVal: '',
      modelVal: '',
      dateModel: '',
      timeModel: ''
    }
  },
  components: {
    Util: Util,
    API: API
  },
  methods: {
    bindData: function () {
      this.dateVal = this.toDate(this.value)
      this.timeVal = this.toTime(this.value)
    },
    toDateTime: function (date, time) {
      return this.isNotNull(date) ? ((typeof date === 'object' ? Util.formatDate.format(date, this.formatter) : date) + (this.isNotNull(time) && this.visitTime ? (' ' + time.substring(0, 2) + ':' + time.substring(2, 4) + ':00') : ' 00:00:00')) : null
    },
    toDate: function (val) {
      return this.isNotNull(val) ? (typeof val === 'object' ? Util.formatDate.format(val, this.formatter) : val.substring(0, this.formatter.length)) : ''
    },
    toTime: function (val) {
      let time = this.isNotNull(val) ? (typeof val === 'object' ? Util.formatDate.format(val, 'HHmm') : (val.length > 11 ? val.substring(this.formatter.length + 1, val.length - 3).replace(':', '') : '0000')) : ''
      return time === '0000' ? '' : time
    },
    initData: function () {

    },
    isNotNull: function (data) {
      return data !== undefined && data !== null && data !== ''
    },
    clearData: function () {

    },
    setDateTimeVal: function (value) {
      let val = this.toDateTime(this.dateVal, this.timeVal)
      this.$emit('input', val)
    }
  },
  mounted () {
    this.bindData()
  }
}
</script>
<style scoped lang="scss">
.datelayout {
  width:65%;
}

.timelayout {
  width:30%;
}
</style>
