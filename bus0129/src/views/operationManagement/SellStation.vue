<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section id="sellStationSection">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.sellStationName" placeholder="售票站点名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getSellStationList">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="sellStations" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%" :height="tableHeight">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" width="60">
          <template slot-scope="scope">
            {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="sellStationName" label="售票站点名称" min-width="250">
        </el-table-column>
        <el-table-column prop="longitude" label="经度" min-width="250">
        </el-table-column>
        <el-table-column prop="latitude" label="纬度" min-width="250">
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right" >
          <template slot-scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
            <el-button size="small" @click="arriveSite(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 到站 </span></el-button>
          </template>
        </el-table-column>
      </el-table>
    <!--分页-->
    <pagination :to="getSellStationList" ref="page"></pagination>
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
    <!-- 到站站点 -->
    <div id="arriveSite">
      <el-dialog title="编辑到站" :visible.sync="dialogTableVisible" :before-close="handleClose">
        <el-table ref="arriveSiteTable" :data="allArriveStations" @selection-change="selsChangeArrive" max-height="350">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column property="stationName" label="站点名称" min-width="150"></el-table-column>
          <el-table-column label="操作(到站时间)" width="180" fixed="right" >
          <template slot-scope="scope">
<!--             <el-button size="small" @click="timeUseEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 所用时间 </span></el-button> -->
            <el-input v-model="scope.row.time" @change="timeUseEdit(scope.row)" placeholder="请输入到站时间"></el-input>
          </template>
        </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelArrival">取 消</el-button>
          <el-button type="primary" @click="submitArrive">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--编辑界面-->
    <common-add-or-update
        @getClickEvent="showLocation"
        title="编辑"
        type="update"
        :to="API.editSellStation().go"
        :callback="getSellStationList"
        :labelWidth="120"
        buttonName="经纬度定位"
        ref="editForm"></common-add-or-update>
  </section>
</template>

<script>
  import Util from './../../common/js/util'
  import Pagination from './../../components/Pagination'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from './../../api'
  export default {
    data () {
      return {
        siteConfigs: null,
        listLoading: false,
        addUpdateTag: null,
        sellStations: [],
        // 过滤
        filters: {
          sellStationName: ''
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
        tableHeight: 495,
        searchLocationTag: null,
        // 弹出框的是否显示
        dialogFormVisible: false,
        // 可达站点的配置
        dialogTableVisible: false,
        // 所有的可达站点
        allArriveStations: null,
        // 需要上传的到站的数据
        arriveSiteParams: {},
        // 时间被修改后的所有可到到达站点的信息
        allArriveStationsTimed: [],
        // 弹出框label的长度
        formLabelWidth: '120px',
        // 编辑行的信息
        editRow: null,
        // 编辑到站的信息
        arrivalRow: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'sellStationId', value: '', hidden: true },
          { name: 'sellStationName', value: '', label: '售票站点名称', disabled: true, type: 'text', rules: [ { required: true, message: '请输入售票站点名称' } ], placeholder: '请输入售票站点名称' },
          { name: 'longitude', value: '', label: '经度', type: 'text', rules: [ { required: true, message: '请输入经度' } ], placeholder: '请输入经度' },
          { name: 'latitude', value: '', label: '纬度', type: 'text', rules: [ { required: true, message: '请输入纬度' } ], placeholder: '请输入纬度' },
          { name: 'isShow', value: true, type: 'button' }
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
      getSellStationList () {
        let para = {
          sellStationName: this.filters.sellStationName,
          pageNum: this.pageNumber,
          pageSize: this.pageSize
        }
        this.listLoading = true
        API.getSellStationListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.$refs['page'].set('total', data.data.total)
            this.sellStations = data.data.list
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
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      showLocation (data) {
        // 显示弹出框
        this.dialogFormVisible = true
        // 将站点名称显示在弹出框上
        this.location.locationName = this.editRow.sellStationName
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
      // 获取所有站点用于可达站点
      getAllStation () {
        let para = {
          pageNum: 1,
          pageSize: 10000
        }
        API.getAllStationListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.allArriveStations = data.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 可达站点的弹出框显示
      arriveSite (index, row) {
        console.time(1)
        this.dialogTableVisible = true
        this.arrivalRow = row
        // 对事件进行清理
        for (var l = 0; l < this.allArriveStations.length; l++) {
          this.allArriveStations[l]['time'] = ''
        }
        this.$nextTick(() => {
          this.$refs.arriveSiteTable.clearSelection()
          API.getArrivalStation().go(row.sellStationId).then((data) => {
          if (data.status === 1) {
              // 显示到站站点以及到那个站点的时间
              var result = []
              for (var j = 0; j < this.allArriveStations.length; j++) {
                for (var i = 0; i < data.data.length; i++) {
                  if (data.data[i].stationId === this.allArriveStations[j].stationId) {
                    this.allArriveStations[j].time = data.data[i].time
                    result.push(this.allArriveStations[j])
                  }
                }
              }
              if (result) {
                result.forEach(result => {
                  this.$refs.arriveSiteTable.toggleRowSelection(result)
                })
              }
              console.timeEnd(1)
            } else {
              this.$notify(Util.notifyBody(false, data.message))
            }
          })
        })
      },
      // 点击可达站点的复选框会触发的事件
      selsChangeArrive (sels) {
        // 每次打钩都要把数据放到arriveSiteParams里面（用于上传）。
        console.log('sels', sels)
        var result = []
        for (var i = 0; i < sels.length; i++) {
          var item = {}
          item['sellStationId'] = this.arrivalRow.sellStationId
          item['stationId'] = sels[i].stationId
          item['time'] = sels[i].time
          result.push(item)
        }
        this.arriveSiteParams['arrivalStationlst'] = result
        console.log('this.arriveSiteParams', this.arriveSiteParams)
      },
      // 到达这个站所用的时间输入框
      timeUseEdit (value) {
        console.log('value', value)
        // 每个INPUT框里的内容被修改后，只要去赋值已选的
        for (var i = 0; i < this.arriveSiteParams.arrivalStationlst.length; i++) {
          if (this.arriveSiteParams.arrivalStationlst[i].stationId === value.stationId) {
            this.arriveSiteParams.arrivalStationlst[i].time = value.time
          }
        }
        console.log(this.arriveSiteParams)
      },
      // 用于组织数据
      resolveArriveSiteParams (value) {
        // console.log('value', value)
      },
      // 点击编辑到站确定按钮所触发的事件
      submitArrive () {
        this.dialogTableVisible = false
        API.addArrivalStation().go(this.arriveSiteParams).then((data) => {
          if (data.status === 1) {
            this.$notify(Util.notifyBody(true, data.message))
            this.$refs.arriveSiteTable.clearSelection()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      cancelArrival () {
        this.dialogTableVisible = false
        this.$refs.arriveSiteTable.clearSelection()
      },
      handleClose () {
        this.dialogTableVisible = false
        this.$refs.arriveSiteTable.clearSelection()
      }
    },
    mounted () {
      this.getSellStationList()
      this.getAllStation()
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
  .arriveSite .el-dialog--small {
    width: 40%!important
  }
</style>
