<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <el-col :span="7">
        <selector v-model="conf.align" :options="Tools.alignOptions()" filterable label="对齐方式(align)" placeholder="选择对齐方式"></selector>
      </el-col>
      <el-col :span="7" :offset="1">
        <span class="editor-label">水平偏移(translateX)</span>
        <el-input type="number" :value="conf.translateX" @input="val => Tools.inputStr2Number(conf, 'translateX', val)"></el-input>
      </el-col>
      <el-col :span="7" :offset="1">
        <span class="editor-label">垂直偏移(translateY)</span>
        <el-input type="number" :value="conf.translateY" @input="val => Tools.inputStr2Number(conf, 'translateY', val)"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <span class="editor-label">
        <el-popover :ref="`popover-text-padding`" placement="top-start" width="250" trigger="hover">
          <div>
            children 子元素根据该参数分成上下两部分
          </div>
          <i
            class="el-icon-warning"
            slot="reference" />
        </el-popover>
        子元素分隔点(slicePoint)
      </span>
      <el-slider v-model="conf.slicePoint" :step="1" :min="0" :max="20" show-input></el-slider>
    </el-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>上层样式</legend>
        <div>
          <el-form-item>
            <el-col :span="8">
              <color-picker v-model="conf.topStyle.color" label="颜色(color)"></color-picker>
            </el-col>
            <el-col :span="7" :offset="1">
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
                  <i
                    class="el-icon-warning"
                    slot="reference" />
                </el-popover>
                内边距(padding)
              </span>
              <el-input :value="conf.topStyle.padding" @input="val => Tools.inputStrOrNumber(conf.topStyle, 'padding', val)"></el-input>
            </el-col>
            <el-col :span="7" :offset="1">
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
                  <i
                    class="el-icon-warning"
                    slot="reference" />
                </el-popover>
                外边距(margin)
              </span>
              <el-input :value="conf.topStyle.margin" @input="val => Tools.inputStrOrNumber(conf.topStyle, 'margin', val)"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">宽度(width)</span>
              <el-input type="number" :value="conf.topStyle.width" @input="val => Tools.inputStr2Number(conf.topStyle, 'width', val)"></el-input>
            </el-col>
            <el-col :span="11" :offset="1">
              <span class="editor-label">高度(height)</span>
              <el-input type="number" :value="conf.topStyle.height" @input="val => Tools.changeValAndLink2Number(conf.topStyle, ['height', 'lineHeight'], val)"></el-input>
            </el-col>
          </el-form-item>
          <selector v-model="conf.topStyle.textAlign" :options="Tools.alignOptions()" filterable clearable label="对齐方式(textAlign)" placeholder="对齐方式"></selector>
          <el-form-item>
            <el-col :span="12">
              <span class="editor-label">
                边框(border)
                <el-switch v-model="switchOptions.topBorder.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
            </el-col>
          </el-form-item>
          <el-form-item v-show="switchOptions.topBorder.switch" style="margin: 4px 0;" class="editor-inner-pannel editor-inner-pannel-left">
            <el-col :span="6">
              <selector v-model="switchOptions.topBorder.value.direction" :options="Tools.borderDirectionOptions()" filterable clearable :showLabel="false" placeholder="四周"></selector>
            </el-col>
            <el-col :span="4" style="margin-left: 4px;">
              <el-input type="number" :value="switchOptions.topBorder.value.width" @input="val => Tools.inputStr2Number(switchOptions.topBorder.value, 'width', val)" placeholder="宽度"></el-input>
            </el-col>
            <el-col :span="5" style="margin-left: 4px;">
              <selector v-model="switchOptions.topBorder.value.style" :options="Tools.borderStyleOptions()" filterable clearable :showLabel="false" placeholder="边框样式"></selector>
            </el-col>
            <el-col :span="8" style="margin-left: 4px;">
              <color-picker v-model="switchOptions.topBorder.value.color" placeholder="边框颜色"></color-picker>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>下层样式</legend>
        <div>
          <el-form-item>
            <el-col :span="8">
              <color-picker v-model="conf.bottomStyle.color" label="颜色(color)"></color-picker>
            </el-col>
            <el-col :span="7" :offset="1">
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
                  <i
                    class="el-icon-warning"
                    slot="reference" />
                </el-popover>
                内边距(padding)
              </span>
              <el-input :value="conf.bottomStyle.padding" @input="val => Tools.inputStrOrNumber(conf.bottomStyle, 'padding', val)"></el-input>
            </el-col>
            <el-col :span="7" :offset="1">
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
                  <i
                    class="el-icon-warning"
                    slot="reference" />
                </el-popover>
                外边距(margin)
              </span>
              <el-input :value="conf.bottomStyle.margin" @input="val => Tools.inputStrOrNumber(conf.bottomStyle, 'margin', val)"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">宽度(width)</span>
              <el-input type="number" :value="conf.bottomStyle.width" @input="val => Tools.inputStr2Number(conf.bottomStyle, 'width', val)"></el-input>
            </el-col>
            <el-col :span="11" :offset="1">
              <span class="editor-label">高度(height)</span>
              <el-input type="number" :value="conf.bottomStyle.height" @input="val => Tools.changeValAndLink2Number(conf.bottomStyle, ['height', 'lineHeight'], val)"></el-input>
            </el-col>
          </el-form-item>
          <selector v-model="conf.bottomStyle.textAlign" :options="Tools.alignOptions()" filterable clearable label="对齐方式(textAlign)" placeholder="对齐方式"></selector>
          <el-form-item>
            <el-col :span="12">
              <span class="editor-label">
                边框(border)
                <el-switch v-model="switchOptions.bottomBorder.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
            </el-col>
          </el-form-item>
          <el-form-item v-show="switchOptions.bottomBorder.switch" style="margin: 4px 0;" class="editor-inner-pannel editor-inner-pannel-left">
            <el-col :span="6">
              <selector v-model="switchOptions.bottomBorder.value.direction" :options="Tools.borderDirectionOptions()" filterable clearable :showLabel="false" placeholder="四周"></selector>
            </el-col>
            <el-col :span="4" style="margin-left: 4px;">
              <el-input type="number" :value="switchOptions.bottomBorder.value.width" @input="val => Tools.inputStr2Number(switchOptions.bottomBorder.value, 'width', val)" placeholder="宽度"></el-input>
            </el-col>
            <el-col :span="5" style="margin-left: 4px;">
              <selector v-model="switchOptions.bottomBorder.value.style" :options="Tools.borderStyleOptions()" filterable clearable :showLabel="false" placeholder="边框样式"></selector>
            </el-col>
            <el-col :span="8" style="margin-left: 4px;">
              <color-picker v-model="switchOptions.bottomBorder.value.color" placeholder="边框颜色"></color-picker>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
  </el-form>
</template>
<script>
  import Tools from '@/common/js/template-tools.js'
  import ColorPicker from './form-items/ColorPicker'
  import Selector from './form-items/Selector'
  export default {
    props: {
      value: Object
    },
    data () {
      return {
        conf: {},
        Tools,
        switchOptions: {
          topBorder: {
            switch: false,
            value: {
              direction: '',
              width: '',
              style: '',
              color: ''
            }
          },
          bottomBorder: {
            switch: false,
            value: {
              direction: '',
              width: '',
              style: '',
              color: ''
            }
          }
        }
      }
    },
    components: {
      ColorPicker, Selector
    },
    watch: {
      conf: {
        handler (val) {
          let styleParams = ['width', 'height', 'lineHeight']
          styleParams.forEach(item => {
            if (val.topStyle[item] === '' || val.topStyle[item] === 0) {
              delete val.topStyle[item]
            }
            if (val.bottomStyle[item] === '' || val.bottomStyle[item] === 0) {
              delete val.bottomStyle[item]
            }
          })
          this.$emit('input', val)
        },
        deep: true
      },
      'switchOptions.bottomBorder': {
        handler (val) {
          for (let key in this.conf.bottomStyle) {
            if (key.startsWith('border')) {
              delete this.conf.bottomStyle[key]
            }
          }
          if (val.switch) {
            let direction = Tools.firstToUpperCase(val.value.direction)
            val.value.width && (this.conf.bottomStyle['border' + direction + 'Width'] = val.value.width)
            val.value.style && (this.conf.bottomStyle['border' + direction + 'Style'] = val.value.style)
            val.value.color && (this.conf.bottomStyle['border' + direction + 'Color'] = val.value.color)
          }
        },
        deep: true
      },
      'switchOptions.topBorder': {
        handler (val) {
          for (let key in this.conf.topStyle) {
            if (key.startsWith('border')) {
              delete this.conf.topStyle[key]
            }
          }
          if (val.switch) {
            let direction = Tools.firstToUpperCase(val.value.direction)
            val.value.width && (this.conf.topStyle['border' + direction + 'Width'] = val.value.width)
            val.value.style && (this.conf.topStyle['border' + direction + 'Style'] = val.value.style)
            val.value.color && (this.conf.topStyle['border' + direction + 'Color'] = val.value.color)
          }
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'DoubleLayerText',
          typeLabel: '双层文本',
          align: 'left',
          translateX: 0,
          translateY: 0,
          topStyle: {},
          bottomStyle: {},
          slicePoint: 0,
          children: []
        }
      } else {
        this.conf = this.value
        this.initSwitch()
      }
    },
    methods: {
      initSwitch () {
        // topStyle
        if (this.conf.topStyle) {
          this.checkBorder('topBorder', this.conf.topStyle)
        }
        // bottomStyle
        if (this.conf.bottomStyle) {
          this.checkBorder('bottomBorder', this.conf.bottomStyle)
        }
      },
      checkBorder (switchBorder, confStyle) {
        let hasBorder = false
        let direction = ''
        for (let key in confStyle) {
          if (key.startsWith('border')) {
            direction = Tools.getBorderDirection(key)
            hasBorder = true
            break
          }
        }
        this.$set(this.switchOptions[switchBorder].value, 'direction', direction)
        direction = Tools.firstToUpperCase(direction)
        this.$set(this.switchOptions[switchBorder].value, 'width', confStyle['border' + direction + 'Width'])
        this.$set(this.switchOptions[switchBorder].value, 'style', confStyle['border' + direction + 'Style'])
        this.$set(this.switchOptions[switchBorder].value, 'color', confStyle['border' + direction + 'Color'])
        if (hasBorder) {
          this.$set(this.switchOptions[switchBorder], 'switch', true)
        }
      }
    }
  }
</script>
