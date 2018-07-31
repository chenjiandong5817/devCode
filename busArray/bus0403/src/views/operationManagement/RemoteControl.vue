<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section id="remoteControlSection">
    <!-- 大标题 -->
    <!-- <el-row>
      <el-col :span="24" class="bg-purple">
        根据section的高度获得远程控制的高度
        <div class="statistics" style="display:table-cell;vertical-align:middle;" id="remoteChinese">
          <span>远程控制</span>
        </div>
      </el-col>
    </el-row> -->
    <!-- 一体机运行情况 -->
    <el-row class="data-row" style="position: relative" id="operaRow">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20" style="margin-top: 30px;" class="data-panel">
        <div>
          <el-row class="title-panel">
            <el-col :span="6">
              <!-- 一体机运行情况的高度根据js来获取 -->
              <div class="title" style="display:table-cell;vertical-align:middle;padding-left:20px;" id="operaSituationChinese">
                <span>一体机运行情况</span>
              </div>
            </el-col>
            <el-col :span="18">
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="2">
              <div style="width: 5px;height: 5px;"></div>
            </el-col>
            <el-col :span="4" v-for="item in machineOperation" :key="item.id">
              <el-row style="margin-top: 10px;">
                <el-col>
                  <!-- 设置标题的高度 -->
                  <div class="title titleChinese" style="display:table-cell;vertical-align:middle;" id="titleChinese">
                    <span>{{ item.title }}</span>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <!-- 设置数据的高度 -->
                  <div class="title dataChinese" style="display:table-cell;vertical-align:middle;" id="dataChinese">
                      <span style="font-size: 28px;">{{ item.result }}</span>
                      <span>/</span>
                      <span>{{ sumNumber }}</span>
                  </div>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 一体机运行列表 -->
    <el-row>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="19">
        <div style="display:table-cell;vertical-align:middle;" id="runListChinese">
          <span style="font-size: 18px;">一体机运行列表</span>
        </div>
      </el-col>
      <el-col :span="1" style="position: static">
         <div style="position: absolute;top: 20px;">
          <el-button type="primary" size="small" @click="handleAdd">新增</el-button>
        </div>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 表格显示 -->
    <el-row style="margin-top: 30px;margin-bottom: 60px;">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20" class="table-border">
        <el-table :data="devices" highlight-current-row style="width: 100%" :height="remoteControlTableHeight">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column type="index" width="70">
            <template slot-scope="scope">
              {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
            </template>
          </el-table-column>
<!--           <el-table-column prop="id" label="编号" max-width="60">
          </el-table-column> -->
          <el-table-column prop="deviceid" label="设备编号" min-width="150">
          </el-table-column>
          <el-table-column prop="stationId" label="站点" min-width="200" :formatter="formatterStation">
          </el-table-column>
          <el-table-column prop="longitude" label="经度" min-width="100">
          </el-table-column>
          <el-table-column prop="latitude" label="纬度" min-width="100">
          </el-table-column>
          <el-table-column prop="address" label="地址" min-width="240">
          </el-table-column>
          <el-table-column label="是否在线" min-width="150">
             <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.isOnLine===1">在线</el-tag>
                <el-tag type="warning" v-if="scope.row.isOnLine===0">不在线</el-tag>
             </template>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right" >
            <!-- <template slot-scope="scope">
              <el-button size="small" @click="open(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 开启 </span></el-button>
              <el-button size="small" @click="maintain(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 维护 </span></el-button>
              <el-button size="small" @click="edit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 修改 </span></el-button>
              <el-button size="small" @click="stop(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 停机 </span></el-button>
            </template> -->
            <template slot-scope="scope">
              <el-dropdown @command="handleCommand">
                <el-button size="small" >更多操作<i class="el-icon-caret-bottom el-icon--right"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="open" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 开启 </span></el-dropdown-item>
                  <el-dropdown-item command="maintain" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 维护 </span></el-dropdown-item>
                  <el-dropdown-item command="stop" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 停机 </span></el-dropdown-item>
                  <el-dropdown-item command="edit" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 修改 </span></el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 用于分页 -->
    <el-row class="footerPage">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20">
         <el-row>
            <pagination :to="getDeviceList" ref="page"></pagination>
         </el-row>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
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
        :to="API.editDevice().go"
        :callback="getDeviceList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--新增界面-->
    <common-add-or-update
        @getClickEvent="showLocation"
        title="新增"
        type="add"
        :to="API.addDevice().go"
        :callback="getDeviceList"
        :callbackother="getRunDevices"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import Pagination from '../../components/Pagination'
  // import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonAddOrUpdate from './../base/baseForm/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from './../../api'
  export default {
    data () {
      return {
        sumNumber: null,
        machineOperation: [
           {
            id: 0,
            title: '正常运行台数',
            numberOfRunning: null
           },
           {
            id: 1,
            title: '暂停服务台数',
            pauseNumber: null
           }
        ],
        devices: null,
        // 站点list
        stationList: [],
        // 维护状态
        statusList: [
          { text: '维修状态', value: 0 },
          { text: '运行中', value: 1 }
        ],
        // 使用JS设置高度
        remoteControlTableHeight: null,
        remoteControlOpeHeight: null,
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
        // 编辑行的信息
        // editRow: null,
        // 正常运行的台数
        // runDevices: null,
        // 暂停服务的台数
        // suspendDevices: null,
        // 一体机运行情况请求成功的tag
        requestSuccessTag: null,
        addOrUpdateTag: null,
        // 新增编辑需要的字段
        fields: [
          {
            id: '0',
            hidden: true,
            item: [
              { name: 'id', value: '' }
            ]
          },
          {
            id: '1',
            item: [
               { name: 'deviceid', value: '', label: '设备编号', type: 'text', rules: [ { required: true, message: '请输入设备编号' } ], placeholder: '请输入设备编号', span: 12 },
               { name: 'stationId', value: '', label: '站点', type: 'select', choose: [], filterable: true, rules: [ { required: true, message: '请选择站点' } ], placeholder: '请选择站点', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
               { name: 'longitude', value: '', label: '经度', type: 'text', rules: [ { required: true, message: '请输入经度' } ], placeholder: '请输入经度', span: 12 },
               { name: 'latitude', value: '', label: '纬度', type: 'text', rules: [ { required: true, message: '请输入纬度' } ], placeholder: '请输入纬度', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
               { name: 'address', value: '', label: '地址', type: 'text', rules: [ { required: true, message: '请输入地址' } ], placeholder: '请输入地址', span: 12 },
               // { name: 'status', value: '', label: '维护状态', type: 'select', choose: [], rules: [ { required: true, message: '请输入维护状态' } ], placeholder: '请输入维护状态', span: 12 }
               { name: 'isShow', value: true, type: 'button', span: 12 }
            ]
          }
          // {
          //   id: '4',
          //   item: [
          //      { name: 'isShow', value: true, type: 'button', span: 12 }
          //   ]
          // }
        ],
        API: API
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
      // commonAddOrUpdate: commonAddOrUpdate,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 获取设备列表
      getDeviceList () {
        let para = {
          pageNum: this.pageNumber,
          pageSize: this.pageSize
        }
        this.listLoading = true
        API.getAllDevicesListPage().go(para).then((data) => {
            if (data.status === 1) {
              this.$refs['page'].set('total', data.data.total)
              this.devices = data.data.list
            } else {
              this.$notify(Util.notifyBody(false, data.msg))
            }
            this.listLoading = false
          })
      },
      // 订单概况的数据
      getMachineOperationData () {
        // 获取到经过处理的数据
        var result = null
        result = this.resolveData(this.machineOperation)
        this.machineOperation = result
        // 获取总的数据
        var sum = null
        sum = this.getSumNumber(this.machineOperation)
        this.sumNumber = sum
      },
      // 处理数据，为了在页面上进行显示
      resolveData (value) {
        var result = null
        result = Util.deepCopy(value)
        for (var i = 0; i < result.length; i++) {
          var key = Object.keys(result[i])[2]
          console.log('key', key)
          result[i]['result'] = result[i][key]
        }
        return result
      },
      // 获取总的台数
      getSumNumber (value) {
        var result = 0
        for (var i = 0; i < value.length; i++) {
          result = value[i].result + result
        }
        return result
      },
      // 正常运行台数
      getRunDevices () {
        this.listLoading = true
        API.devicesRun().go().then((data) => {
          if (data.status === 1) {
            this.machineOperation[0].numberOfRunning = data.data
            this.getSuspendDevices()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 暂停运行台数
      getSuspendDevices () {
        this.listLoading = true
        API.devicesSuspend().go().then((data) => {
          if (data.status === 1) {
            this.machineOperation[1].pauseNumber = data.data
            this.getMachineOperationData()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 开启
      open (index, row) {
        var para = {
          deviceid: row.deviceid
        }
        this.listLoading = true
        API.startDevice().go(para).then((data) => {
          if (data.status === 1) {
            this.$notify(Util.notifyBody(true, data.message))
            this.getDeviceList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      maintain (index, row) {
        var para = {
          deviceid: row.deviceid
        }
        this.listLoading = true
        API.maintainDevice().go(para).then((data) => {
          if (data.status === 1) {
            this.$notify(Util.notifyBody(true, data.message))
            this.getDeviceList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 停机
      stop (index, row) {
        var para = {
          deviceid: row.deviceid
        }
        console.log('row', row)
        this.listLoading = true
        API.stopDevice().go(para).then((data) => {
          if (data.status === 1) {
            this.$notify(Util.notifyBody(true, data.message))
            this.getDeviceList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 显示新增界面
      handleAdd: function () {
        this.addOrUpdateTag = 'add'
        this.fields[1].item[1].choose = this.stationList
        this.fields[3].item[1].choose = this.statusList
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      edit (index, row) {
        console.log('index', index)
        console.log('row', row)
        this.addOrUpdateTag = 'edit'
        // this.editRow = Util.deepCopy(row)
        this.fields[1].item[1].choose = this.stationList
        this.fields[3].item[1].choose = this.statusList
        // 赋值
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
      // 获取section的高度
      // 获取表格高度
      getTableHeight () {
        this.$nextTick(() => {
          // 获取section的高度
          var sectionHeight = document.getElementById('remoteControlSection').offsetHeight
          // 获取表格的高度用于设置表格的高度
          this.remoteControlTableHeight = sectionHeight * 0.47
          var operaRow = document.getElementById('operaRow')
          this.remoteControlOpeHeight = sectionHeight * 0.30
          operaRow.style.height = this.remoteControlOpeHeight + 'px'
          // 设置远程控制文字的高度
          // var remoteChineseHeight = sectionHeight * 0.08
          // var remoteChinese = document.getElementById('remoteChinese')
          // remoteChinese.style.height = remoteChineseHeight + 'px'
          // 设置一体机运行情况文字的高度
          var operaSituationChineseHeight = sectionHeight * 0.05
          var operaSituationChinese = document.getElementById('operaSituationChinese')
          operaSituationChinese.style.height = operaSituationChineseHeight + 'px'
          // 一体机运行情况标题的高度
          var titleChineseHeight = sectionHeight * 0.08
          var titleChinese = document.getElementsByClassName('titleChinese')
          for (var i = 0; i < titleChinese.length; i++) {
            titleChinese[i].style.height = titleChineseHeight + 'px'
          }
          // 一体机运行情况数据的高度
          var dataChineseHeight = sectionHeight * 0.08
          var dataChinese = document.getElementsByClassName('dataChinese')
          for (var j = 0; j < dataChinese.length; j++) {
            dataChinese[j].style.height = dataChineseHeight + 'px'
          }
          // 一体机运行列表文字的高度
          var runListChineseHeight = sectionHeight * 0.05
          var runListChinese = document.getElementById('runListChinese')
          runListChinese.style.height = runListChineseHeight + 'px'
        })
      },
      // 获取所有的站点并且组织数据结构
      getAllStationList () {
        let para = {
          pageNum: null,
          pageSize: null
        }
        para.pageNum = 1
        para.pageSize = 10000
        API.getAllStationListPage().go(para).then((data) => {
          if (data.status === 1) {
            // 组织数据结构
            this.stationList = []
            for (var i = 0; i < data.data.list.length; i++) {
              var result = {}
              result['text'] = data.data.list[i].cityName + data.data.list[i].stationName
              result['value'] = data.data.list[i].stationId
              this.stationList.push(result)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
        })
      },
      formatterStation (row, column, cellValue) {
        var result = null
        for (var i = 0; i < this.stationList.length; i++) {
          if (cellValue === this.stationList[i].value) {
            result = this.stationList[i].text
          }
        }
        return result
      },
      // 经纬度弹出框的设置
      showLocation (data) {
        console.log('data', data)
        this.dialogFormVisible = true
        this.location.locationName = this.formatterStation(0, 1, data.stationId)
        this.longAndLat.longitude = data.longitude
        this.longAndLat.latitude = data.latitude
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
          // this.editRow.longitude = this.longAndLat.longitude
          // this.editRow.latitude = this.longAndLat.latitude
          // 改变编辑框的经纬度
          if (this.addOrUpdateTag === 'add') {
            this.$refs['addForm'].changeInput('longitude', this.longAndLat.longitude)
            this.$refs['addForm'].changeInput('latitude', this.longAndLat.latitude)
          }
          if (this.addOrUpdateTag === 'edit') {
            this.$refs['editForm'].changeInput('longitude', this.longAndLat.longitude)
            this.$refs['editForm'].changeInput('latitude', this.longAndLat.latitude)
          }
          this.dialogFormVisible = false
        }
      }
    },
    mounted () {
      this.getTableHeight()
      this.getDeviceList()
      this.getAllStationList()
      this.getRunDevices()
    }
  }
</script>

<style scoped>
/*  .bg-purple {
    background: #e4e4e4;
    margin-top: 10px;
  }*/
/*  .statistics {
    font-family: 'PingFangSC-Regular', 'PingFang SC';
    font-weight: 400;
    font-style: normal;
    font-size: 20px;
    padding-left: 50px;
  }*/
  .data-row {
    /*height: 300px;*/
  }
  .data-panel {
    /*height: 210px;*/
    border-width: 1px;
    border-style: solid;
    border-color: rgba(121, 121, 121, 1);
    border-radius: 10px;
  }
  .title-panel {
    margin-top: 0;
    background: #e4e4e4;
    border-radius: 10px 10px 0 0;
  }
  .title {
    font-family: 'PingFangSC-Regular', 'PingFang SC';
    font-weight: 400;
    font-style: normal;
    font-size: 18px;
  }
  .table-border .el-table {
    border-right: 1px solid #dfe6ec;
  }
  .toolbar-bottom {
    border: 0;
    z-index: 90;
    background: #fff;
    padding: 10px 0 10px 10px;
    position: absolute;
    width: 100%!important;
  }
  .footerPage {
    width: 100%;
  }
</style>
