<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<!-- 代码模块化预备 -->
<template>
  <section id="siteConfigSection">
        <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.stationName" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="synStation">同步erp系统站点信息</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
     <el-table :data="siteConfigs" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%" :height="tableHeight">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="stationName" label="站点名称" min-width="250">
      </el-table-column>
      <el-table-column prop="desClassify.classify" label="站点分类" width="120">
      </el-table-column>
      <el-table-column prop="longitude" label="经度" min-width="120">
      </el-table-column>
      <el-table-column prop="latitude" label="纬度" min-width="120">
      </el-table-column>
      <el-table-column prop="areaName" label="地区名称" width="120">
      </el-table-column>
      <el-table-column prop="provinceName" label="省份名称" min-width="120">
      </el-table-column>
      <el-table-column prop="cityName" label="城市名称" width="120">
      </el-table-column>
      <el-table-column prop="stationInputCode" label="站点编号" width="120">
      </el-table-column>
      <el-table-column label="操作" width="100" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getSiteConfigeList" ref="page"></pagination>
    <!-- 666666 -->

    <!-- 根据名称获取经纬度 -->
    <div class="locationDialog">
    <el-dialog title="坐标" :visible.sync="dialogFormVisible">
      <el-row style="margin-left: 20px">
        <el-col :span="3">
          <div class="text-show" style="margin-top: 7px">
              <span>地点名称</span>
          </div>
        </el-col>
        <el-col :span="17">
          <el-input v-model="location.locationName" auto-complete="off"></el-input>
        </el-col>
        <el-col :span="1">
          <div style="width: 5px;height: 5px;">
          </div>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="searchLocation">查询</el-button>
        </el-col>
      </el-row>
      <el-row style="margin-top: 20px;margin-left: 20px;">
         <el-col :span="3">
            <div class="text-show" style="margin-top: 7px">
              <span>经度</span>
            </div>
         </el-col>
         <el-col :span="6" style="text-align: center;">
            <el-input v-model="longAndLat.longitude" :disabled="true" auto-complete="off"></el-input>
         </el-col>
         <el-col :span="2">
          <div style="width: 5px;height: 5px;">
          </div>
        </el-col>
         <el-col :span="3">
            <div class="text-show" style="margin-top: 7px">
              <span>纬度</span>
            </div>
         </el-col>
         <el-col :span="6" style="text-align: center;">
             <el-input v-model="longAndLat.latitude" :disabled="true" auto-complete="off"></el-input>
         </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitLocation">确 定</el-button>
      </div>
    </el-dialog>
    </div>
    <!--编辑界面-->
    <common-add-or-update
        @getClickEvent="showLocation"
        title="编辑"
        type="update"
        :to="API.editStation().go"
        :callback="getSiteConfigeList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeStation().go"
        :callback="getSiteConfigeList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from './../../common/js/util'
  import Pagination from './../../components/Pagination'
  import commonAddOrUpdate from './../base/baseForm/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from './../../api'
  export default {
    data () {
      return {
        siteConfigs: null,
        listLoading: false,
        addUpdateTag: null,
        allDesClassifyOptions: [],
        // 过滤
        filters: {
          stationName: ''
        },
        // 地点名称
        location: {
          locationName: ''
        },
        // 精度与纬度
        longAndLat: {
          longitude: '',
          latitude: ''
        },
        searchLocationTag: null,
        // 弹出框的是否显示
        dialogFormVisible: false,
        // 弹出框label的长度
        formLabelWidth: '120px',
        // 编辑行的信息
        editRow: null,
        tableHeight: 495,
        // 新增编辑需要的字段
        fields: [
          {
            id: '0',
            hidden: true,
            item: [
              { name: 'stationId', value: '' }
            ]
          },
          {
            id: '1',
            item: [
               { name: 'stationName', value: '', label: '站点名称', type: 'text', disabled: true, rules: [ { required: true, message: '请输入站点名称' } ], placeholder: '请输入站点名称', span: 12 },
               { name: 'classifyid', value: '', label: '站点分类', type: 'select', choose: [], rules: [ { required: true, message: '请输入站点分类' } ], placeholder: '请输入站点分类', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
               { name: 'longitude', value: '', label: '经度', type: 'text', rules: [ { required: true, message: '请输入经度' } ], placeholder: '请输入经度', span: 12 },
               { name: 'latitude', value: '', label: '纬度', type: 'text', rules: [ { required: true, message: '请输入纬度' } ], placeholder: '请输入经度', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
               { name: 'areaName', value: '', label: '地区名称', type: 'text', disabled: true, rules: [ { required: true, message: '请输入地区名称' } ], placeholder: '请输入地区名称', span: 12 },
               { name: 'provinceName', value: '', label: '省份名称', type: 'text', disabled: true, rules: [ { required: true, message: '请输入省份名称' } ], placeholder: '请输入省份名称', span: 12 }
            ]
          },
          {
            id: '4',
            item: [
               { name: 'cityName', value: '', label: '城市名称', type: 'text', disabled: true, rules: [ { message: '请输入城市名称' } ], placeholder: '请输入城市名称', span: 12 },
               { name: 'stationInputCode', value: '', label: '站点编号', type: 'text', disabled: true, rules: [ { message: '请输入站点编号' } ], placeholder: '请输入站点编号', span: 12 }
            ]
          },
          {
            id: '5',
            item: [
               // { name: 'isShow', value: true, type: 'button', span: 12 }
            ]
          }
        ],
        API: API,
        sels: []
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
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      selsChange: function (sels) {
        this.sels = sels
      },
      // 获取所有站点列表
      getSiteConfigeList () {
        let para = {
          pageNum: null,
          pageSize: null
        }
        para.pageNum = this.pageNumber
        para.pageSize = this.pageSize
        this.listLoading = true
        API.getAllStationListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.$refs['page'].set('total', data.data.total)
            this.siteConfigs = data.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.editRow = Util.deepCopy(row)
        // 赋值
        this.fields[1].item[1].choose = this.allDesClassifyOptions
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            if (editFields[i].item[j].type === 'button') {
              continue
            }
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      // 获取所有的站点分类
      getAllDesClassifys () {
        let para = Object.assign({}, { pageNum: 1, pageSize: 10000 })
        API.getDesClassifyListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.allDesClassifyOptions = []
            for (var i = 0; i < data.data.list.length; i++) {
              var item = {}
              item['value'] = data.data.list[i].id
              item['text'] = data.data.list[i].classify
              this.allDesClassifyOptions.push(item)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 查询站点方法
      searchStation () {
        API.searchStation().go(this.filters.stationName).then((data) => {
          if (data.status === 1) {
            this.$refs['page'].set('total', data.data.length)
            this.siteConfigs = data.data
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 点击查询站点
      search () {
        if (this.filters.stationName === '') {
          this.getSiteConfigeList()
        } else {
          this.searchStation()
        }
      },
      showLocation (data) {
        // 显示弹出框
        this.dialogFormVisible = true
        // 将站点名称显示在弹出框上
        this.location.locationName = this.editRow.stationName
        // 显示已有的经纬度
        this.longAndLat.longitude = this.editRow.longitude
        this.longAndLat.latitude = this.editRow.latitude
      },
      searchLocation () {
        this.searchLocationTag = true
        API.getLatAndLon().go(this.location.locationName).then((data) => {
          if (data.status === 1) {
            if (!data.data.lng || !data.data.lat) {
              this.searchLocationTag = false
              this.longAndLat.longitude = null
              this.longAndLat.latitude = null
              this.$notify(Util.notifyBody(false, '地点在省外'))
            } else {
              this.longAndLat.longitude = data.data.lng
              this.longAndLat.latitude = data.data.lat
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      submitLocation () {
        if (!this.searchLocationTag) {
          this.dialogFormVisible = false
        } else {
          this.editRow.longitude = this.longAndLat.longitude
          this.editRow.latitude = this.longAndLat.latitude
          // 改变编辑框的经纬度
          this.$refs['editForm'].changeInput('longitude', this.longAndLat.longitude)
          this.$refs['editForm'].changeInput('latitude', this.longAndLat.latitude)
          this.dialogFormVisible = false
        }
      },
      // 同步ERP信息
      synStation () {
        this.listLoading = true
        API.synStation().go().then((data) => {
          this.listLoading = false
          this.$notify(Util.notifyBody(true, '同步erp系统站点信息'))
        })
      }
    },
    mounted () {
      this.getSiteConfigeList()
      this.getAllDesClassifys()
    }
  }
</script>

<style scoped>
  .locationDialog .el-dialog--small {
    width: 40%!important
  }
  .text-show {
    display: inline-block;
    font-family: 'PingFangSC-Regular', 'PingFang SC';
    font-weight: 400;
    font-style: normal;
    font-size: 15px;
  }
</style>
