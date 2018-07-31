/*
 * @Author: chenyang
 * @Date: 2017-09-20 17:32:34
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-17 16:20:45
 */
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <city-name
            class="dropDown"
            ref="city"
            v-on:getAirportName = "getAirportCode">
        </city-name>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.imagetype" placeholder="图片类型" :filterable="true" @change="getImages">
            <el-option v-for="sub in imageTypeList" :key="sub.value" :label="sub.text" :value="sub.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.imagesize" placeholder="图片尺寸" :filterable="true" @change="getImages">
            <el-option v-for="sub in imageSizeList" :key="sub.value" :label="sub.text" :value="sub.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.filetype" placeholder="文件格式" :filterable="true" @change="getImages">
            <el-option v-for="sub in imageFormatList" :key="sub.value" :label="sub.text" :value="sub.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.imagename" placeholder="图片名称" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getImages">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="clearSerach">重置搜索框</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table v-bind:data="imageList" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="80">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="airportcode" label="机场名称" width="180" sortable>
      </el-table-column>
      </el-table-column>
      <el-table-column prop="imagetype" label="图片类型" width="180" sortable>
      </el-table-column>
      <el-table-column prop="imagename" label="图片名称" width="180" sortable>
      </el-table-column>
      <el-table-column prop="imagesize" label="图片尺寸" width="180" sortable show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="filetype" label="文件类型" width="180" sortable show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="180" sortable >
      </el-table-column>
      <el-table-column label="操作" min-width="150">
        <template scope="scope">
          <el-popover
            ref="popover4"
            placement="right"
            width="200"
            trigger="click">
            <img :src="imageUrl" height="200" width="200">
          </el-popover>
          <el-button v-popover:popover4 type="success" size="small" @click="preview(scope.$index, scope.row)">图片预览</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right" >
        <template scope="scope">
          <!--<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
          <el-dropdown @command="handleCommand">
            <el-button size="small" >更多操作<i class="el-icon-caret-bottom el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleEdit" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 编辑 </span></el-dropdown-item>
              <el-dropdown-item command="handleDel" :index="scope.$index" :row="scope.row"><span><i class="el-icon-delete"></i> 删除 </span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getImages" ref="page"></pagination>

    <!--删除窗口-->
    <common-delete
        :to="API.removeImage().go"
        :callback="getImages"
        :labelWidth="100"
        ref="delConfirm">
    </common-delete>
    <image-add-or-update
      title="新增"
      Func="addFunc"
      :DestinationData="bindDestination"
      :FlightLogo = "airLinesList"
      :FlightLogoServiceMode = "checkInModeList"
      :nonCheckin = "nonCheckInModeList"
      :flightLogoTypeList = "serviceType"
      :callback="getImages"
      :labelWidth="100"
      ref="imageAddForm">
    </image-add-or-update>
    <image-add-or-update
      title="编辑"
      Func="editFunc"
      :DestinationData="bindDestination"
      :FlightLogo = "airLinesList"
      :FlightLogoServiceMode = "checkInModeList"
      :nonCheckin = "nonCheckInModeList"
      :flightLogoTypeList = "serviceType"
      :callback="getImages"
      :labelWidth="100"
      ref="imageEditForm">
    </image-add-or-update>
  </section>
</template>

<script>
  import LoginInfo from './../../vuex/store'
  import Util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'
  import GlobalConfig from '../../common/config/global'
  import ImageAddOrUpdate from '../device/ImageAddForm/ImageAddOrUpdate'
  import CityName from '../../components/CityName'
  export default {
    data () {
      return {
        imageFormatList: [],
        imageSizeList: [],
        imageTypeList: [],
        filters: {
          imagename: '',
          imagetype: '',
          imagesize: '',
          filetype: '',
          airportcode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        serviceMode: [],
        serviceType: [],
        airline: [],
        airport: [],
        airLinesList: [],
        checkInModeList: [],
        nonCheckInModeList: [],
        imageList: [],
        tableHeight: 560,
        listLoading: false,
        selectItemDetail: {},
        sels: [],
        filename: [],
        imageUrl: [],
        bindDestination: [],
        subscribeAirportsLables: [],
        subscribeAirports: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'imagetype', value: '', label: '图片类型', type: 'select', rules: null },
          { name: 'imagesize', value: '', label: '图片尺寸', type: 'select', rules: null },
          { name: 'filetype', value: '', label: '文件类型', type: 'select', rules: null },
          { name: 'imagename', value: '', label: '图片名称', type: 'text', rules: null },
          { name: 'description', value: '', label: '描述', type: 'text', rules: null }
          // { name: 'description', value: '', label: '描述', type: 'select', choose: [ { text: '国内', value: 'D' }, { text: '国际', value: 'I' }, { text: '地区', value: 'R' } ], rules: [ { required: true, message: '请选择地区代码' } ] },
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
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination,
      imageAddOrUpdate: ImageAddOrUpdate,
      cityName: CityName
    },
    watch: {
    '$store.state.cached' (val, oldVal) {
        this.getDestinationLabel()
        this.getAirLine()
        this.getServiceMode()
        this.getServiceType()
      }
    },
    methods: {
      getAirportCode (airportData) {
        // console.log(airportData)
        this.filters.airportcode = airportData
        this.getImages()
      },
      clearSerach () {
        this.filters.airportcode = JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        this.filters.imagetype = ''
        this.filters.imagesize = ''
        this.filters.filetype = ''
        this.filters.imagename = ''
      },
      getSubscribeAirports () {
        let result = []
        let data = LoginInfo.state.userStorage.user.aiisAirports
        for (let i = 0; i < data.length; i++) {
          result.push(data[i].airportCode)
        }
        return result
      },
      SubscribeAirports () {
        this.subscribeAirportsLables = []
        this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
        for (let i = 0; i < this.subscribeAirports.length; i++) {
          let label = {text: this.subscribeAirports[i], value: this.subscribeAirports[i]}
          this.subscribeAirportsLables.push(label)
        }
      },
      getImgSize () {
        this.imageSizeList = []
        let label = {}
        label = {text: '图片尺寸不限', value: ''}
        this.imageSizeList.push(label)
        for (let i = 0; i < GlobalConfig.imageSize.length; i++) {
          label = {text: GlobalConfig.imageSize[i], value: GlobalConfig.imageSize[i]}
          this.imageSizeList.push(label)
        }
      },
      getImageFormat () {
        this.imageFormatList = []
        let label = {}
        label = {text: '图片格式不限', value: ''}
        this.imageFormatList.push(label)
        for (let i = 0; i < GlobalConfig.fileType.length; i++) {
          label = {text: GlobalConfig.fileType[i], value: GlobalConfig.fileType[i]}
          this.imageFormatList.push(label)
        }
      },
      getAirLine () {  // 获取缓存经常无数据
        this.airline = this.$cache.fetch('airlines')
        this.airLinesList = []
        let label = {}
        for (let i = 0; i < this.airline.length; i++) {
          label = {text: this.airline[i].cnabbr + ' ' + this.airline[i].iatacode, value: this.airline[i].iatacode}
          this.airLinesList.push(label)
        }
        label = {text: '缺省', value: 'default'}
        this.airLinesList.push(label)
      },
      getDestinationLabel () {   // 在外面做费时操作
        this.airport = this.$cache.fetch('airports')
        this.bindDestination = []
        for (let i = 0; i < this.airport.length; i++) {
          let label = {text: this.airport[i].cnabbr2w + ' ' + this.airport[i].iatacode, value: this.airport[i].iatacode}
          this.bindDestination.push(label)
        }
      },
      getServiceMode () {   // 服务模式
        this.serviceMode = this.$cache.fetch('ckcounteropmodes')
        this.checkInModeList = []
        this.nonCheckInModeList = []
        for (let i = 0; i < this.serviceMode.length; i++) {
          if (this.serviceMode[i].opModeCode === 'D') {
            let label = {text: this.serviceMode[i].description + ' ' + this.serviceMode[i].opModeCode, value: this.serviceMode[i].opModeCode}
            this.checkInModeList.push(label)
          }
        }
        for (let i = 0; i < this.serviceMode.length; i++) {
          if (this.serviceMode[i].opModeCode === 'N') {
            let label = {text: this.serviceMode[i].description + ' ' + this.serviceMode[i].opModeCode, value: this.serviceMode[i].opModeCode}
            this.nonCheckInModeList.push(label)
          }
        }
      },
      getServiceType () { // 服务类型
        this.serviceType = this.$cache.fetch('ckcounterservicetypes')
        // this.checkInTypeList = []
        // for (let i = 0; i < this.serviceType.length; i++) {
        //   let label = {text: this.serviceType[i].description + ' ' + this.serviceType[i].serviceTypeCode, value: this.serviceType[i].serviceTypeCode}
        //   this.checkInTypeList.push(label)
        // }
      },
      getImages () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getImage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.imageList = data.attr.data.list
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
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        this.$refs['imageAddForm'].show()
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // console.log(row)
        this.$refs['imageEditForm'].show(row)
        // console.log(this.filters.imagetype)
      },
      setAirportInfo () {
        this.$refs['city'].setCitys(this.subscribeAirports)
      },
      preview (index, row) {
        let urlPart = GlobalConfig.urlPart
        let queryPara = GlobalConfig.queryPara
        let finalUrl = urlPart + queryPara + row.id + '&url=' + row.url + '&updatetime=' + row.updateTime
        this.imageUrl = finalUrl
      },
      addType () {
        this.imageTypeList = [{text: '图片类型不限', value: ''}, {text: '静态图片', value: 'static'}, {text: '航班及专柜图片', value: 'flight_logo'}, {text: '非值机柜台图片', value: 'noncheckin'}, {text: '目的地图片', value: 'destination'}]
      },
      init () {
        this.SubscribeAirports()
        this.addType()
        this.getImgSize()
        this.getImageFormat()
        this.getDestinationLabel()
        this.getAirLine()
        this.getImages()
        this.getServiceMode()
        this.getServiceType()
        this.setAirportInfo()
      }
    },
    mounted () {
      this.init()
    }
  }

</script>

<style scoped>
.dropDown {
  width: 250px;
}
</style>
