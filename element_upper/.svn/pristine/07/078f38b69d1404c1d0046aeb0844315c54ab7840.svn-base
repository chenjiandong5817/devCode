/*
 * @Author: ylj
 * @Date: 2017-11-19 23:01:07
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-22 10:57:36
 */
<template>
<div class="loadAddMainClss">
  <div :class="addOrUpClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" :size="dialogSize" @open="initData">
    <el-card class="box-card fieldsetClss">
    <el-form ref="flightLoadMain">
      <el-row :gutter="10" v-if="operate === 'flightLoadAdd'">
        <el-col :span="12">
          <el-card class="box-card cardClss" ref="flightDetail">
            <flight-schedule ref="flightTable" v-on:onRowClick="setClickRow"></flight-schedule>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card cardClss" ref="flightLoadDetail">
            <add-or-update
              type="add"
              :to="to"
              :callback="callback"
              ref="addForm"></add-or-update>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10" v-if="operate !== 'flightLoadAdd'">
        <el-col :span="24">
          <el-card class="box-card cardClss" ref="flightLoadDetail">
            <add-or-update
              type="add"
              :to="to"
              :labelWidth="100"
              :callback="handleClose"
              ref="addForm"></add-or-update>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
    </el-card>
  </el-dialog>
  </div>
</div>
</template>

<script>
import API from '../../../api'
import flightSchedule from './FlightSchedule'
import addOrUpdate from './AddOrUpdate'

export default {
  props: {
    title: {
      type: String,
      default: '新增'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      operate: '',
      addOrUpClss: 'normalClass',
      dialogClss: 'smallDigCls',
      visible: false,
      loading: false,
      API: API
    }
  },
  components: {
    flightSchedule: flightSchedule,
    addOrUpdate: addOrUpdate
  },
  computed: {
    dialogSize () {
      return this.operate === 'flightLoadAdd' ? 'large' : 'small'
    }
  },
  methods: {
    initData () {
      // 1. 设置样式
      this.setClassName()
      // 2. 加载航班信息
      this.$nextTick(() => {
        if (this.operate === 'flightLoadAdd' && !this.$refs['flightTable'].firstLoading) {
          this.$refs['flightTable'].bindData()
        }
      })
    },
    setClassName () {
      // 设置dialog宽度、字段突出显示
      this.addOrUpClss = (this.operate !== 'Add' ? 'signClass' : 'normalClass') + (document.body.clientWidth <= 1366 ? ' largeDigCls' : ' smallDigCls')
    },
    bindData () {
    },
    show (operate, row, flight) {
      this.visible = true
      this.operate = operate
      this.$nextTick(() => {
        this.$refs['addForm'].show(this.operate, row, flight)
      })
      this.loading = false
      this.setClassName()
    },
    handleClose: function () {
      this.visible = false
      if (this.operate === 'flightLoadAdd') {
        this.$refs['flightTable'].clearData()
      }
      this.callback()
    },
    setClickRow (row) {
      this.show(this.operate, null, row)
    }
  },
  mounted () {
    this.bindData()
    // 自适应浏览器窗口大小
    window.addEventListener('resize', this.setClassName)
  }
}

</script>
<style lang="scss">
.loadAddMainClss {
  .el-form-item {
    margin-bottom: 8px;
  }
  .el-dialog__body {
    padding-top: 5px;
  }
  .el-table {
    margin-left: 0px!important;
    margin-right: 0px!important;
  }
  .el-table .segmetCnl-row {
    background: #e2f0e4;
  }
  .el-table .segmetNormal-row {
    background: #ffffff;
  }
  .smallDigCls {
    .el-dialog--small {
      width: 42%!important;
    }
  }
  .largeDigCls {
    .el-dialog--small {
      width: 62%!important;
    }
  }
  .el-dialog {
    margin-top: -4.5%!important;
  }
  .signClass [name=carrier],.signClass [name=flightNo],.signClass [name=direction] {
    background: #FFA500!important;
    color: black!important;
  }

  .cardClss {
    margin: 8px 5px 10px 8px;
    min-height: 700px;
    width: 98%;
    padding-left: 0px;
  }

  .el-card__body {
    padding: 5px;
  }

  .fieldsetClss {
    width: 100%;
    border: 1px solid #000;
  }
}
</style>

