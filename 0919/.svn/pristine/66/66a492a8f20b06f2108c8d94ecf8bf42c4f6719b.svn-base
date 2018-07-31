<template>
  <el-dialog
    title="预览参数"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    size="small"
    :before-close="handleClose">
    <el-form>
      <el-form-item>
        <span class="editor-label">
          <el-popover :ref="`popover-text-device`" placement="top-start" width="250" trigger="hover">
            <div>
              请尽量选择已经在播放列表中设置好的设备，才能确保有数据用于预览
            </div>
            <i 
              class="el-icon-warning" 
              slot="reference" />
          </el-popover>
          终端设备
        </span>
        <selector v-model="deviceId" :options="deviceOptions" filterable optionLabel="deviceIp" optionValue="id" :showLabel="false"></selector>
      </el-form-item>
      <el-form-item>
        <span class="editor-label">
          <el-popover :ref="`popover-text-device`" placement="top-start" width="250" trigger="hover">
            <div>
              选择在模板中有选过的数据源，并设置用于测试的数据。<br />
              预览窗口仅显示静态页面效果，多语言转换、定时刷新等功能请到真实环境下查看。
            </div>
            <i 
              class="el-icon-warning" 
              slot="reference" />
          </el-popover>
          虚拟数据
        </span>
        <el-form-item v-for="(dataSource, dsIdx) in dataSources" :key="dsIdx">
          <el-form-item>
            <el-col :span="20">
              <selector v-model="dataSource.id" :options="dataSourceOptions" filterable :showValue="false" :showLabel="false"></selector>
            </el-col>
            <el-col :span="4" style="padding-left: 4px;">
              <el-button icon="plus" @click="handleAddSource" v-if="dsIdx === 0"></el-button>
              <el-button icon="minus" @click="handleRemoveSource(dsIdx)" v-else></el-button>
            </el-col>
          </el-form-item>
          <div v-show="dataSource.id" class="editor-inner-pannel editor-inner-pannel-left preview-panel">
            <div style="overflow: auto">
              <table border="1" cellspacing="0">
                <thead>
                  <th v-for="item in dataSourceFields[dataSource.id]" :key="item">{{ item }}</th>
                </thead>
                <tbody>
                  <tr v-for="(tr, index) in dataSource.list" :key="index">
                    <td v-for="(td, tdIdx) in dataSourceFields[dataSource.id]" :key="tdIdx" style="min-width: 80px;">
                      <el-input v-model="tr[td]" class="input-not-padding"/>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="preview-panel-button">
              <el-button icon="plus" @click="handleAddSourceData(dsIdx)"></el-button>
              <el-button icon="close" @click="handleRemoveSourceData(dsIdx)"></el-button>
            </div>
          </div>
        </el-form-item>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" :disabled="!submitable" @click="handleSubmit">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  import Selector from './tempaltes/form-items/Selector'
  import Tools from '@/common/js/template-tools.js'
  export default {
    props: {
      conf: Object,
      previewDevice: [String, Number],
      previewData: Object
    },
    data () {
      return {
        dialogVisible: false,
        deviceId: '',
        dataSources: [
          {id: '', list: []}
        ],
        tableData: []
      }
    },
    components: {
      Selector
    },
    computed: {
      deviceOptions () {
        return this.$cache.fetch('deviceInfo')
      },
      dataSourceOptions () {
        return (this.$cache.fetch('dataSources') || []).map(item => {
          return {label: item.des, value: item.id}
        })
      },
      dataSourceFields () {
        return this.$cache.fetch('dataSourceFields') || {}
      },
      submitable () {
        return this.deviceId && this.dataSources && this.dataSources.length > 0 && this.dataSources[0].id
      }
    },
    watch: {
    },
    mounted () {
      // console.log(this.dataSourceFields)
      if (this.previewDevice) {
        this.deviceId = this.previewDevice
      }
      if (!Tools.isEmptyObject(this.previewData)) {
        this.dataSources = []
        for (let key in this.previewData) {
          this.dataSources.push({
            id: key,
            list: this.previewData[key]
          })
        }
      }
    },
    methods: {
      show () {
        this.dialogVisible = true
      },
      hide () {
        this.dialogVisible = false
      },
      pacakgeData () {
        let data = {}
        for (let item of this.dataSources) {
          data[item.id] = {
            list: item.list,
            pager: {
              pageSize: 10,
              pageNumber: 1,
              pageCount: item.list.length + ((item.list.length % 10) > 0 ? 1 : 0),
              recordCount: item.list.length
            }
          }
        }
        return {
          conf: this.conf,
          data
        }
      },
      handleClose (done) {
        done()
      },
      handleSubmit () {
        this.$emit('setPreviewParams', this.deviceId, this.pacakgeData())
        this.dialogVisible = false
      },
      handleAddSource () {
        this.dataSources.push({id: '', list: []})
      },
      handleRemoveSource (index) {
        this.dataSources.splice(index, 1)
      },
      handleAddSourceData (index) {
        this.dataSources[index].list.push({})
      },
      handleRemoveSourceData (index) {
        let data = this.dataSources[index].list
        if (data && data.length > 0) {
          data.splice(data.length - 1, 1)
        }
      }
    }
  }
</script>
<style lang="scss">
.input-not-padding .el-input__inner{
  padding: 0 !important;
}
.float-button {
  position: absolute;
  top: -100%;
}

.preview-panel {
  position: relative;
  margin: 5px 0 46px;
  padding: 5px 5px 0px 5px;
  .preview-panel-button {
    position: absolute;
    right: 0;
  }
}
</style>
