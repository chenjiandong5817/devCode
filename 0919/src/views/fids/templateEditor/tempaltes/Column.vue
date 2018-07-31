<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <el-button type="default" @click="Tools.addDataSource(conf)" plain v-if="!Tools.haveDataSource(conf)">添加数据源</el-button>
      <el-button type="danger" @click="Tools.removeDataSource(conf)" plain v-else>删除数据源</el-button>
    </el-form-item>
    <el-form-item>
      <span class="editor-label">列占比(span)</span>
      <el-input type="number" :value="conf.span" @input="val => Tools.inputStr2Number(conf, 'span', val)"></el-input>
    </el-form-item>
    <data-source-form-item v-model="conf"></data-source-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>样式</legend>
        <div>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">宽度(width)</span>
              <el-input type="number" :value="conf.bodyStyle.width" @input="val => Tools.inputStr2Number(conf.bodyStyle, 'width', val)"></el-input>
            </el-col>
            <el-col :span="11" :offset="1">
              <span class="editor-label">高度(height)</span>
              <el-input type="number" :value="conf.bodyStyle.height" @input="val => Tools.changeValAndLink2Number(conf.bodyStyle, ['height', 'lineHeight'], val)"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <selector v-model="conf.bodyStyle.textAlign" :options="Tools.alignOptions()" filterable label="对齐方式(textAlign)" placeholder="选择对齐方向"></selector>
            </el-col>
            <el-col :span="11" :offset="1">
              <color-picker v-model="conf.bodyStyle.backgroundColor" label="背景色(backgroundColor)" :inputScale="17" :pickerScale="7"></color-picker>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                <el-popover :ref="`popover-text-margin`" placement="top-start" width="250" trigger="hover">
                  <div>
                    默认填写数字，如 30， 即设置四边都为30px;<br />
                    分别设置四角圆角，并带上单位例如<font color="red">px</font> 或者<font color="red">em</font> <br />
                    输入格式1： 上  右  下  左 <br />
                    例如1： 10px  20px  30px  40px <br />
                    输入格式2： 上下  左右 <br />
                    例如2： 10px 20px <br />
                    单位不可省略，参数使用空格隔开
                  </div>
                  <i class="el-icon-warning" slot="reference" />
                </el-popover>
               外边距(margin)
                <el-switch v-model="switchOptions.margin.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
              <div v-show="switchOptions.margin.switch" class="editor-inner-pannel editor-inner-pannel-left">
                <el-input :value="switchOptions.margin.value" @input="val => Tools.inputStrOrNumber(switchOptions.margin, 'value', val)"></el-input>
              </div>
            </el-col>
            <el-col :span="11" :offset="1">
              <span class="editor-label">
                <el-popover :ref="`popover-text-padding`" placement="top-start" width="250" trigger="hover">
                  <div>
                    默认填写数字，如 30， 即设置四边都为30px;<br />
                    分别设置四角圆角，并带上单位例如<font color="red">px</font> 或者<font color="red">em</font> <br />
                    输入格式1： 上  右  下  左 <br />
                    例如1： 10px  20px  30px  40px <br />
                    输入格式2： 上下  左右 <br />
                    例如2： 10px 20px <br />
                    单位不可省略，参数使用空格隔开
                  </div>
                  <i class="el-icon-warning" slot="reference" />
                </el-popover>
               内边距(padding)
                <el-switch v-model="switchOptions.padding.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
              <div v-show="switchOptions.padding.switch" class="editor-inner-pannel editor-inner-pannel-left">
                <el-input :value="switchOptions.padding.value" @input="val => Tools.inputStrOrNumber(switchOptions.padding, 'value', val)"></el-input>
              </div>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                <el-popover :ref="`popover-text-radius`" placement="top-start" width="250" trigger="hover">
                  <div>
                    默认填写数字，如 500， 即设置四个角都为500px;<br />
                    分别设置四角圆角，并带上单位例如<font color="red">px</font> 或者<font color="red">em</font>, 顺序为上左、上右、下右、下左
                  </div>
                  <i class="el-icon-warning" slot="reference" />
                </el-popover>
               圆角大小(radius)
                <el-switch v-model="switchOptions.borderRadius.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
              <div v-show="switchOptions.borderRadius.switch" class="editor-inner-pannel editor-inner-pannel-left">
                <el-input :value="switchOptions.borderRadius.value" @input="val => Tools.inputStrOrNumber(switchOptions.borderRadius, 'value', val)"></el-input>
              </div>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
  </el-form>
</template>
<script>
  import Tools from '@/common/js/template-tools.js'
  import DataSourceFormItem from './form-items/DataSource'
  import ColorPicker from './form-items/ColorPicker'
  import Selector from './form-items/Selector'
  export default {
    props: {
      value: Object
    },
    components: {
      DataSourceFormItem, ColorPicker, Selector
    },
    data () {
      return {
        conf: {},
        Tools,
        switchOptions: {
          borderRadius: {
            switch: false,
            value: 0
          },
          margin: {
            switch: false,
            value: 0
          },
          padding: {
            switch: false,
            value: 0
          }
        }
      }
    },
    watch: {
      conf: {
        handler (val) {
          let bodyStyleParams = ['width', 'height', 'lineHeight']
          bodyStyleParams.forEach(item => {
            if (val.bodyStyle[item] === '' || val.bodyStyle[item] === 0) {
              delete val.bodyStyle[item]
            }
          })
          this.$emit('input', val)
        },
        deep: true
      },
      'switchOptions.borderRadius': {
        handler (val) {
          Tools.operateSwitch(val.switch, this.conf.bodyStyle, 'borderRadius', val.value)
        },
        deep: true
      },
      'switchOptions.margin': {
        handler (val) {
          Tools.operateSwitch(val.switch, this.conf.bodyStyle, 'margin', val.value)
        },
        deep: true
      },
      'switchOptions.padding': {
        handler (val) {
          Tools.operateSwitch(val.switch, this.conf.bodyStyle, 'padding', val.value)
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'Column',
          typeLabel: '列',
          span: 24,
          bodyStyle: {},
          children: []
        }
      } else {
        this.conf = this.value
        this.initSwitch()
      }
    },
    methods: {
      initSwitch () {
        // borderRadius
        if (this.conf.bodyStyle && (this.conf.bodyStyle.borderRadius || this.conf.bodyStyle.borderRadius > -1)) {
          this.setSwitchElements(this.switchOptions.borderRadius, this.conf.bodyStyle.borderRadius)
        }
        // margin
        if (this.conf.bodyStyle && (this.conf.bodyStyle.margin || this.conf.bodyStyle.margin > -1)) {
          this.setSwitchElements(this.switchOptions.margin, this.conf.bodyStyle.margin)
        }
        // padding
        if (this.conf.bodyStyle && (this.conf.bodyStyle.padding || this.conf.bodyStyle.padding > -1)) {
          this.setSwitchElements(this.switchOptions.padding, this.conf.bodyStyle.padding)
        }
      },
      setSwitchElements (target, value) {
        this.$set(target, 'value', value)
        this.$set(target, 'switch', true)
      }
    }
  }
</script>
