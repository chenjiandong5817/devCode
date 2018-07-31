<template>
  <el-form-item v-if="conf.dataSource !== undefined">
    <fieldset class="editor-fieldset">
      <legend>数据源</legend>
      <div>
        <el-form-item>
          <span class="editor-label">数据源(dataSource)</span>
          <el-select v-model="conf.dataSource" filterable placeholder="选择数据源" class="editor-select-full">
            <template v-for="item in dataSourceList">
              <el-option :key="item.value" :label="item.label" :value="item.value"></el-option>
            </template>
          </el-select> 
        </el-form-item>
        <el-form-item>
          <el-col :span="11">
            <span class="editor-label">刷新时间(refreshInterval)</span> 
            <el-input type="number" :value="conf.refreshInterval" placeholder="选择刷新时间，单位毫秒" @input="val => Tools.inputStr2Number(conf, 'refreshInterval', val)"></el-input>
          </el-col>
          <!-- <el-col :span="11" :offset="1">
            <span class="editor-label">分页时间(pageInterval)</span> 
            <el-input type="number" :value="conf.pageInterval" placeholder="选择分页时间，单位毫秒" @input="val => Tools.inputStr2Number(conf, 'pageInterval', val)"></el-input>
          </el-col> -->
        </el-form-item>
        <el-form-item>
          <el-col :span="8">
            <span class="editor-label">
              <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
                <div>
                  是否为主要数据，是的话页面会根据空值判断是否显示模板
                </div>
                <i class="el-icon-warning" slot="reference"></i>
              </el-popover>
              主数据(mainSource)
            </span>
            <el-radio-group v-model="conf.mainSource">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-col>
          <el-col :span="8">
            <span class="editor-label">
              <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
                <div>
                  单行数据标志位，表示当前数据源内的数据只取第一条, 若【主分页】mainPager属性存在，则该属性失效
                </div>
                <i class="el-icon-warning" slot="reference"></i>
              </el-popover>
              单数据(singleData)
            </span>
            <el-radio-group v-model="conf.singleData">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-col>
          <el-col :span="8">
            <span class="editor-label">
              <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
                <div>
                  分页信息标志位，表示从当前数据源内获取pager为本设备显示的分页信息
                </div>
                <i class="el-icon-warning" slot="reference"></i>
              </el-popover>
              主分页(mainPager)
            </span>
            <el-radio-group v-model="conf.mainPager">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>
      </div>
    </fieldset>
  </el-form-item>
</template>
<script>
  import Tools from '@/common/js/template-tools.js'
  export default {
    props: {
      value: Object
    },
    data () {
      return {
        conf: {},
        Tools,
        dataSourceList: []
      }
    },
    watch: {
      conf: {
        handler (val) {
          this.$emit('input', val)
        },
        deep: true
      }
    },
    created () {
      this.conf = this.value
      this.getDataSourceList()
    },
    methods: {
      getDataSourceList () {
        // TODO 查询数据源列表
        this.dataSourceList = (this.$cache.fetch('dataSources') || []).map(item => {
          return {label: item.des, value: item.id}
        })
      }
    }
  }
</script>
