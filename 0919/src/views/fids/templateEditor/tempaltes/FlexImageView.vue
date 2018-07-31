<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <el-col :span="11">
        <selector v-model="conf.showType" :options="showTypeOptions" label="展示类型(showType)" placeholder="选择展示类型"></selector>
      </el-col>
      <el-col :span="11" :offset="2">
        <span class="editor-label">
          <el-popover :ref="`popover-text-autoSize`" placement="top-start" width="250" trigger="hover">
            <div>
              该选项只在图片数量为0-2张的时候有效
            </div>
            <i class="el-icon-warning" slot="reference" />
          </el-popover>
          <span style="display: inline-block; width: 130px;">自适应(autoSize)</span>
        </span>
        <el-radio-group v-model="conf.autoSize">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span class="editor-label">水平偏移(translateX)</span>
        <el-input type="number" :value="conf.translateX" @input="val => Tools.inputStr2Number(conf, 'translateX', val)"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span class="editor-label">垂直偏移(translateY)</span>
        <el-input type="number" :value="conf.translateY" @input="val => Tools.inputStr2Number(conf, 'translateY', val)"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>总体样式</legend>
        <div>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                <i class="el-icon-star-on editor-label-require" title="此项必填"></i>高度(height)
              </span>
              <el-input type="number" :value="conf.style.height" @input="val => Tools.inputStr2Number(conf.style, 'height', val)"></el-input>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>图片样式</legend>
        <div>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                <i class="el-icon-star-on editor-label-require" title="此项必填"></i>高度(height)
              </span>
              <el-input type="number" :value="conf.imgStyle.height" @input="val => Tools.inputStr2Number(conf.imgStyle, 'height', val)"></el-input>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <selector v-model="conf.imgType" filterable :options="Tools.imgTypeOptions()" label="类型(imgType)" placeholder="选择图片尺寸"></selector>
      </el-col>
      <el-col :span="11" :offset="1">
        <selector v-model="conf.imgSize" :options="Tools.imgSizeOptions()" :showValue="false" label="尺寸(imgSize)" placeholder="选择图片尺寸"></selector>
      </el-col>
    </el-form-item>
    <el-form-item>
      <span class="editor-label">
        <el-popover :ref="`popover-text-columnName`" placement="top-start" width="250" trigger="hover">
          <div>
            如果没有选项可供选择，请检查父级节点是否正确配置数据源。<br />
            多选字段，将字段合并成图片参数，请注意字段的排列顺序
          </div>
          <i class="el-icon-warning" slot="reference" />
        </el-popover>
        字段名称(columnName)
      </span>
      <selector v-model="switchOptions.columnName.value" :options="columnNameOptions" filterable multiple :showValue="false" :showLabel="false" placeholder="选择字段名称"></selector>
    </el-form-item>
  </el-form>
</template>
<script>
  import Tools from '@/common/js/template-tools.js'
  import Selector from './form-items/Selector'
  export default {
    props: {
      value: Object
    },
    components: {
      Selector
    },
    data () {
      return {
        conf: {},
        Tools,
        showTypeOptions: [
          {label: '滚动', value: 'flexImage'},
          {label: '切换', value: 'carousel'}
        ],
        switchOptions: {
          columnName: {
            switch: true,
            value: []
          }
        }
      }
    },
    computed: {
      columnNameOptions () {
        // return Tools.getDataSourceColumns(this.$parent.currentDataSource)
        return this.$parent.dataSourceColumns
      }
    },
    watch: {
      conf: {
        handler (val) {
          this.$emit('input', val)
        },
        deep: true
      },
      'switchOptions.columnName': {
        handler (val) {
          this.$set(this.conf, 'columnName', val.value.join(','))
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'FlexImageView',
          typeLabel: '滑动图片',
          showType: 'flexImage',
          autoSize: true,
          translateX: 0,
          translateY: 0,
          style: {
            height: 0
          },
          imgStyle: {
            height: 0
          },
          columnName: ''
        }
      } else {
        this.conf = this.value
        this.initSwitch()
      }
    },
    methods: {
      initSwitch () {
        if (this.conf.columnName) {
          let array = this.conf.columnName.split(',')
          this.$set(this.switchOptions.columnName, 'value', array)
        }
      }
    }
  }
</script>
