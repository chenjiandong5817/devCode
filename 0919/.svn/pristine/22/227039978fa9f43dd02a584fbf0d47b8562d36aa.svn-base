<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="请输入机场代码" @change="airportCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAirports" icon="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd" icon="plus">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="airports" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="airportCode" label="机场代码" width="150" sortable>
      </el-table-column>
      <el-table-column prop="airport" label="机场全称" min-width="180" sortable>
      </el-table-column>
      <el-table-column prop="disabled" label="是否禁用" :formatter="getResolveNum" width="120" sortable>
      </el-table-column>
      <el-table-column prop="remark" label="备注"  width="120" sortable show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template scope="scope">
          <el-button size="small" v-if="scope.row.disabled===1" type="success" @click="startDisableAirport(scope.$index, scope.row)">启用</el-button>
          <el-button size="small" v-if="scope.row.disabled===2" type="danger" @click="startDisableAirport(scope.$index, scope.row)">禁用</el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar-bottom">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addAiisAirport().go"
        :callback="getAirports"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editAiisAirport().go"
        :callback="getAirports"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>

    <!--删除窗口-->
    <common-delete
        :to="API.removeAiisAirport().go"
        :callback="getAirports"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'

  export default {
    data () {
      return {
        filters: {
          name: ''
        },
        airports: [],
        total: 0,
        pageNumber: 1,
        pageSize: 10,
        listLoading: false,
        sels: [],
        // 所有的通用机场
        allBaseAirport: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 新增编辑需要的字段
        fields: [
          {name: 'id', value: '', hidden: true},
          {name: 'airportCode', value: '', disabled: false, label: '机场名称', choose: [], type: 'selectExplain', rules: [{max: 3, min: 3, required: true, message: '请选择机场'}], filterable: true},
          {name: 'disabled', value: 2, label: '是否禁用', choose: [ { text: '禁用', value: 1 }, { text: '启用', value: 2 } ], type: 'select', rules: null},
          {name: 'remark', value: '', label: '备注', type: 'text', rules: null}
        ],
        API: API
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete
    },
    methods: {
      handleCurrentChange (val) {
        this.pageNumber = val
        this.getAirports()
      },
      // 获取用户列表
      getAirports () {
        let para = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          airportcode: this.filters.name
        }
        // 加载开始
        this.listLoading = true
        API.getAiisAirportListPage().go(para).then((data) => {
          if (data.ok) {
            this.total = data.attr.data.pager.recordCount
            this.airports = data.attr.data.list
            this.filters.name = ''
            this.getBaseAirports()
          } else {
            this.$notify(util.notifyBody(false, data.msg))
          }
        // 加载完成
          this.listLoading = false
        })
      },
      // 获取通用机场列表,用于在aiis里面将三字码转换成文字
      getBaseAirports () {
        let para = {
          iatacode: '',
          icaocode: '',
          pageNumber: 1,
          pageSize: 10000
        }
        API.getAirportListPage().go(para).then((data) => {
          this.allBaseAirport = data.attr.data.list
          for (var i = 0; i < this.airports.length; i++) {
            this.airports[i]['airport'] = ''
            // 这个循环用于赋值airport，根据三字码来获得机场全称
            for (var j = 0; j < this.allBaseAirport.length; j++) {
              if (this.airports[i].airportCode === this.allBaseAirport[j].iatacode) {
                this.airports[i]['airport'] = this.allBaseAirport[j].airportcnname
              }
            }
          }
          var resultCache = [
            {
              label: '国际',
              labelkey: 'I',
              options: []
            },
            {
              label: '国内',
              labelkey: 'D',
              options: []
            }
          ]
          var typedAirportsArray = util.typedAirport(this.allBaseAirport)
          // 对下拉框的数据进行分组
          for (var a = 0; a < typedAirportsArray.length; a++) {
            if (typedAirportsArray[a].nature === 'I') {
              resultCache[0].options.push(typedAirportsArray[a])
            }
            if (typedAirportsArray[a].nature === 'D') {
              resultCache[1].options.push(typedAirportsArray[a])
            }
          }
          this.airportsTypesChoose = resultCache
        })
      },
      // 是否禁用返回的是数字，解释成英文
      getResolveNum (row, column, cellValue) {
        if (cellValue === 1) {
          return '禁用'
        }
        if (cellValue === 2) {
          return '启用'
        }
      },
      // 启用或者禁用的函数
      startDisableAirport (index, row) {
        if (row.disabled === 1) {
          row.disabled = 2
        } else {
          row.disabled = 1
        }
        let para = {
          id: row.id,
          airportCode: row.airportCode,
          disabled: row.disabled,
          groupId: row.groupId,
          remark: row.remark
        }
        let params = {}
        params = {
          newValue: para
        }
        API.editAiisAirport().go(params).then((res) => {
          this.$notify(util.notifyBody(res.ok, res.msg))
          this.getAirports()
        })
      },
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        console.log(this.fields)
        this.fields[1].choose = this.airportsTypesChoose
        let addFields = util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        let editFields = util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields, row)
      },
      airportCodeUpper: function (data) {
        this.filters.name = data.toUpperCase()
      }
    },
    mounted () {
      this.getAirports()
    }
  }

</script>

<style scoped>
 .el-dialog {
    position: absolute;
    left: 50%;
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
    background: #fff;
    border-radius: 2px;
    box-shadow: 0 1px 3px rgba(0,0,0,.3);
    box-sizing: border-box;
    margin-bottom: 50px;
    width: 40%!important;
 }
 .el-dialog--small {
  width: 40%!important;
 }
</style>
