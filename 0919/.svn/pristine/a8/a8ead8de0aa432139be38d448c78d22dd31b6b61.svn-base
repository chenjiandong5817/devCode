<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-select v-model="filters.airportId" filterable placeholder="请选择机场" style="width: 250px!important">
            <el-option-group
              v-for="group in airportsTypesChoose"
              :key="group.label"
              :label="group.label">
              <el-option
                v-for="item in group.options"
                :key="item.value"
                :label="item.labelShow"
                :value="item.value">
                  <span style="float: left">{{ item.text }}</span>
                  <span style="float: right">{{ item.code }}</span>
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.title" placeholder="职务" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAirportContactsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="airportcontacts" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="职务" min-width="200" sortable>
      </el-table-column>
      <el-table-column prop="content" label="联系方式" min-width="200">
      </el-table-column>
      <el-table-column prop="airportId" label="机场" min-width="200" :formatter="formatterAirport">
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" min-width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getAirportContactsList" ref="page"></pagination>

    <!--新增界面-->
    <div class="addAirportContactTag">
      <common-add-or-update
        type="add"
        :to="API.addAirportContacts().go"
        :callback="getAirportContactsList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="addAirportContactTag">
      <common-add-or-update
          title="编辑"
          type="update"
          :to="API.editAirportContacts().go"
          :callback="getAirportContactsList"
          :labelWidth="100"
          ref="editForm"></common-add-or-update>
      </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeAirportContacts().go"
        :callback="getAirportContactsList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          title: '',
          airportId: ''
        },
        airportcontacts: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airportsChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportId', value: '', label: '机场', filterable: true, type: 'selectExplain', choose: [], rules: null, placeholder: '' },
          { name: 'title', value: '', label: '职务', type: 'text', rules: [ { max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的职务' } ], placeholder: '请输入职务' },
          { name: 'content', value: '', label: '联系方式', type: 'text', rules: [ { max: 50, min: 1, message: '请输入一位到五十位字符串的联系方式' } ], placeholder: '请输入联系方式' }
        ],
        API: API,
        baseUtil: baseUtil
      }
    },
    computed: {
      pageNumber () {
        return this.$refs['page'].get('pageNumber')
      },
      pageSize () {
        return this.$refs['page'].get('pageSize')
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getAirportContactsList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getAirportContactsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.airportcontacts = data.attr.data.list
            this.filters.title = ''
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
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
        this.fields[1].choose = this.airportsTypesChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
      	this.fields[1].choose = this.airportsTypesChoose
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      // 获取所有机场的详细信息,为了获得下拉框的value与text
      getBaseAirports: function () {
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
          this.airportsChoose = []
          var userSubscribeAirports = JSON.parse(localStorage.getItem('AdminSubscribeAirportJson'))
          for (var i = 0; i < userSubscribeAirports.length; i++) {
            var item = {}
            item['text'] = ''
            item['value'] = ''
            item['code'] = ''
            item['text1'] = ''
            item['nature'] = ''
            item['labelShow'] = ''
            for (var j = 0; j < this.$cache.array.airports.length; j++) {
              if (userSubscribeAirports[i].airportCode === this.$cache.array.airports[j].iatacode) {
                item['text'] = this.$cache.array.airports[j].displaycnname
                item['value'] = this.$cache.array.airports[j].id
                item['code'] = this.$cache.array.airports[j].iatacode + '/' + this.$cache.array.airports[j].icaocode
                item['text1'] = this.$cache.array.airports[j].displaycnname
                item['nature'] = this.$cache.array.airports[j].airportnature
                item['labelShow'] = this.$cache.array.airports[j].displaycnname + '(' + this.$cache.array.airports[j].iatacode + ')'
              }
            }
            this.airportsChoose.push(item)
          }
          // this.filters.airportId = this.airportsChoose[0].value
          // console.log('this.airportsChoose', this.airportsChoose)
          // 对下拉框的数据进行分组
          for (var a = 0; a < this.airportsChoose.length; a++) {
            if (this.airportsChoose[a].nature === 'I') {
              resultCache[0].options.push(this.airportsChoose[a])
            }
            if (this.airportsChoose[a].nature === 'D') {
              resultCache[1].options.push(this.airportsChoose[a])
            }
          }
          this.airportsTypesChoose = resultCache
          // console.log('this.airportsTypesChoose', this.airportsTypesChoose)
      },
      // 格式化运营机场
      formatterAirport: function (row, column, cellValue) {
        for (var i = 0; i < this.airportsChoose.length; i++) {
          if (cellValue === this.airportsChoose[i].value) {
            return this.airportsChoose[i].text
          }
        }
      }
    },
    mounted () {
      this.getBaseAirports()
      this.getAirportContactsList()
    }
  }

</script>

<style>
  .addAirportContactTag .el-dialog--small {
    width: 40%!important
  }
</style>
