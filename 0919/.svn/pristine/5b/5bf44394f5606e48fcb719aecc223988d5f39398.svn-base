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
          <el-select v-model="filters.thirdLang" filterable placeholder="请选择第三语言">
            <el-option
              v-for="item in thirdLangChoose"
              :key="item.value"
              :label="item.text"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDestLangList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="destlangs" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="airportcode" label="机场代码" width="150">
      </el-table-column>
      <el-table-column prop="airportcnname" label="机场中文名称" min-width="250" sortable>
      </el-table-column>
      <el-table-column prop="thirdLang" label="第三语言" width="150">
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
    <pagination :to="getDestLangList" ref="page"></pagination>

    <!--新增界面-->
    <!-- 这边的destLangsTag样式是用于这个vue文件的样式去除掉scoped的时候，会影响到其他的页面 -->
    <div class="destLangsTag">
    <common-add-or-update
        type="add"
        :to="API.addDestLang().go"
        :callback="getDestLangList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="destLangsTag">
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editDestLang().go"
        :callback="getDestLangList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeDestLang().go"
        :callback="getDestLangList"
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
  import airportSelect from './baseForm/SelectAirport'

  export default {
    data () {
      return {
        filters: {
          airportcode: '',
          thirdLang: ''
        },
        destlangs: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airportsChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        thirdLangChoose: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportcode', value: '', label: '机场代码', filterable: true, type: 'selectExplain', choose: [], rules: null, placeholder: '请选择机场代码' },
          { name: 'thirdLang', value: '', label: '第三语言', type: 'select', choose: [], rules: null, placeholder: '请选择第三语言' }
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
      pagination: Pagination,
      airportSelect: airportSelect
    },
    methods: {
      // 获取用户列表
      getDestLangList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getDestLangListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.destlangs = data.attr.data.list
            // this.filters.airportcode = ''
            this.filters.thirdLang = ''
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
        this.fields[2].choose = this.thirdLangChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
      	this.fields[1].choose = this.airportsTypesChoose
      	this.fields[2].choose = this.thirdLangChoose
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      // 获取第三语言语言下拉框的值
      getThirdLangChoose: function () {
      	let para = {
      	  originaltype: 'AIRPORT',
          original: '',
          langcode: '',
          translation: '',
          pageSize: 100000,
          pageNumber: 1
      	}
      	API.getMultiLang().go(para).then((data) => {
          if (data.ok) {
          	var result = []
            this.thirdLangChoose = []
	      	for (var i = 0; i < data.attr.data.list.length; i++) {
	      		if (this.thirdLangChoose.indexOf(data.attr.data.list[i].langcode) === -1) {
	      			this.thirdLangChoose.push(data.attr.data.list[i].langcode)
	      		}
	      	}
	      	for (var j = 0; j < this.thirdLangChoose.length; j++) {
	      		var item = {}
	      		item['text'] = this.thirdLangChoose[j]
	      		item['value'] = this.thirdLangChoose[j]
	      		if (this.thirdLangChoose[j] !== null) {
	      			result.push(item)
	      		}
	      	}
	      	this.thirdLangChoose = result
	      	console.log('this.thirdLangChoose', this.thirdLangChoose)
          }
        })
      },
      // 机场下拉框的通用模块
      getBaseAirports: function () {
        // 筛选出登录用户的订阅机场
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
          this.airportsChoose = Util.typedAirport(this.$cache.array.airports)
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
      }
    },
    mounted () {
      this.getBaseAirports()
      this.getDestLangList()
      this.getThirdLangChoose()
    }
  }

</script>

<style>
   .destLangsTag .el-dialog--small  {
    width: 40%!important
   }
</style>
