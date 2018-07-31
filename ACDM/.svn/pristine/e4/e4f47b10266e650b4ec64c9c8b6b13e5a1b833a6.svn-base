<template>
  <div>
    <el-dialog
      title="航班选择"
      :visible.sync="visible"
      @close="close"
      :show="show"
      :close-on-click-modal="false"
      width="300">
      <div class="toolbar">
        <el-form :inline="true" size="small" :model="filters">
          <el-form-item>
            <el-input  placeholder="请输入航班号，查询航班" v-model="filters.flight"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="getList">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table
        :data="tableData"
        @row-dblclick="handledbClick"
        max-height="300"
        size="small"
        title="请双击选择"
        highlight-current-row>
        <el-table-column type="index"></el-table-column>
        <template v-for="col in cols">
          <el-table-column v-bind="col" :key="col.label"></el-table-column>
        </template>
        <el-table-column   label="进出港">
          <template slot-scope="{row, $index}">
            <el-tag size="mini" :type="flightType[row.direction].type" >{{flightType[row.direction].name}}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import moment from 'moment'

export default {
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      flightType: {
        'A': { type: '', name: '进港' },
        'D': { type: 'danger', name: '出港' }
      },
      moment: moment,
      visible: this.show,
      filters: {
        flight: ''
      },
      cols: [
        { label: '执行日期',
          prop: 'opdate',
          formatter: (row, column) => { return row.opdate ? moment(row.opdate).format('YYYY-MM-DD') : '' }
        },
        { label: '候机楼', prop: 'terminal' },
        { label: '航班号', prop: 'flight' },
        { label: '承运人', prop: 'carrier' }
        // { slot: 'opt1' }
      ],
      tableData: []
    }
  },
  watch: {
    show () {
      this.visible = this.show
    }
  },
  methods: {
    handledbClick (row, event) {
      this.visible = false
      this.tableData = []
      this.filters.flight = ''
      this.$emit('dbClick', row)
    },
    close () {
      this.visible = false
      this.tableData = []
      this.filters.flight = ''
      this.$emit('update:show', false)
    },
    getList () {
      let ajax = this.$auth('post_flight_choose_list')
      if (ajax) {
        ajax(this.filters).then((res) => {
          this.tableData = res.data
        })
      }
    }
  }
}
</script>
