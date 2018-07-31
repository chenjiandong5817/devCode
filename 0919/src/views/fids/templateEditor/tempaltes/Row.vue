<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>对齐方式</legend>
        <div>
          <el-form-item>
            <el-col :span="11">
              <selector v-model="conf.align" :options="alignOptions" filterable label="垂直方向(align)" placeholder="选择垂直对齐方向"></selector>          
            </el-col>
            <el-col :span="11" :offset="1">
              <selector v-model="conf.justify" :options="justifyOptions" filterable label="水平方向(justify)" placeholder="选择水平对齐方向"></selector>
            </el-col>
          </el-form-item>
        </div>
      </fieldset>
    </el-form-item>
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
              <el-input type="number" :value="conf.bodyStyle.height" @input="cascadeHeight"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <color-picker v-model="conf.bodyStyle.backgroundColor" label="背景色(backgroundColor)" :inputScale="18" :pickerScale="6"></color-picker>
            </el-col>
            <el-col :span="11" :offset="1">
              <color-picker v-model="conf.bodyStyle.color" label="字体颜色(color)" :inputScale="18" :pickerScale="6"></color-picker>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                位置固定(position)
                <el-switch v-model="switchOptions.position.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
            </el-col>
            <el-col :span="11" :offset="1">
              <div v-show="switchOptions.position.switch">
                <selector v-model="switchOptions.position.value" :options="positionOptions" filterable :showLabel="false" placeholder="选择固定位置"></selector>
              </div>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="11">
              <span class="editor-label">
                边框(border)
                <el-switch v-model="switchOptions.border.switch" active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
              </span>
            </el-col>
            <el-col :span="11" :offset="1">
              <div v-show="switchOptions.border.switch">
                <selector v-model="switchOptions.border.value.direction" :options="positionOptions" filterable clearable :showLabel="false" placeholder="方向，默认四边"></selector>
              </div>
            </el-col>
          </el-form-item>
          <el-form-item v-show="switchOptions.border.switch" style="padding: 4px 0;">
            <el-col :span="7">
              <el-input type="number" :value="switchOptions.border.value.width" @input="val => Tools.inputStr2Number(switchOptions.border.value, 'width', val)" placeholder="边框宽度"></el-input>
            </el-col>
            <el-col :span="7" style="margin-left: 16px;">
              <selector v-model="switchOptions.border.value.style" :options="Tools.borderStyleOptions()" filterable clearable :showLabel="false" placeholder="选择边框样式"></selector>
            </el-col>
            <el-col :span="8" style="margin-left: 16px;">
              <color-picker v-model="switchOptions.border.value.color" placeholder="边框颜色"></color-picker>
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
    components: {
      ColorPicker, Selector
    },
    data () {
      return {
        conf: {},
        Tools,
        alignOptions: [
          {value: 'top', label: '顶部'},
          {value: 'middle', label: '中间'},
          {value: 'bottom', label: '底部'}
        ],
        justifyOptions: [
          {value: 'start', label: '左边'},
          {value: 'center', label: '中间'},
          {value: 'end', label: '右边'},
          {value: 'space-around', label: '均匀分布'},
          {value: 'space-between', label: '两端对齐'}
        ],
        switchOptions: {
          position: {
            switch: false,
            value: ''
          },
          border: {
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
    computed: {
      positionOptions () {
        return Tools.borderDirectionOptions()
      }
    },
    watch: {
      conf: {
        handler (val) {
          let bodyStyleParams = ['width']
          bodyStyleParams.forEach(item => {
            if (val.bodyStyle[item] === '' || val.bodyStyle[item] === 0) {
              delete val.bodyStyle[item]
            }
          })
          this.$emit('input', val)
        },
        deep: true
      },
      'switchOptions.position': {
        handler (val) {
          this.positionOptions.map(item => {
             delete this.conf.bodyStyle[item.value]
          })
          if (val.switch) {
            this.conf.bodyStyle.position = 'absolute'
            // 20180110 增加zindex，应对情况： 底部滚动栏被表格图标遮挡到
            this.conf.bodyStyle.zIndex = 999
            if (val.value) {
              this.conf.bodyStyle[val.value] = 0
            }
          } else {
            delete this.conf.bodyStyle.position
            delete this.conf.bodyStyle.zIndex
          }
        },
        deep: true
      },
      'switchOptions.border': {
        handler (val) {
          for (let key in this.conf.bodyStyle) {
            if (key.startsWith('border')) {
              delete this.conf.bodyStyle[key]
            }
          }
          if (val.switch) {
            let direction = Tools.firstToUpperCase(val.value.direction)
            val.value.width && (this.conf.bodyStyle['border' + direction + 'Width'] = val.value.width)
            val.value.style && (this.conf.bodyStyle['border' + direction + 'Style'] = val.value.style)
            val.value.color && (this.conf.bodyStyle['border' + direction + 'Color'] = val.value.color)
          }
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'Row',
          typeLabel: '行',
          align: 'top',
          justify: 'start',
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
        // position
        if (this.conf.bodyStyle && this.conf.bodyStyle.position) {
          this.$set(this.switchOptions.position, 'switch', true)
          this.$set(this.switchOptions.position, 'value', this.getDirection())
        }
        // border
        if (this.conf.bodyStyle) {
          let hasBorder = false
          let direction = ''
          for (let key in this.conf.bodyStyle) {
            if (key.startsWith('border')) {
              direction = Tools.getBorderDirection(key)
              hasBorder = true
              break
            }
          }
          this.$set(this.switchOptions.border.value, 'direction', direction)
          direction = Tools.firstToUpperCase(direction)
          this.$set(this.switchOptions.border.value, 'width', this.conf.bodyStyle['border' + direction + 'Width'])
          this.$set(this.switchOptions.border.value, 'style', this.conf.bodyStyle['border' + direction + 'Style'])
          this.$set(this.switchOptions.border.value, 'color', this.conf.bodyStyle['border' + direction + 'Color'])
          if (hasBorder) {
            this.$set(this.switchOptions.border, 'switch', true)
          }
        }
      },
      // 获取绝对定位固定方向
      getDirection () {
        for (let item of this.positionOptions) {
          if (this.conf.bodyStyle[item.value] !== null && this.conf.bodyStyle[item.value] !== undefined) {
            return item.value
          }
        }
        return ''
      },
      // 级联设置 lineheight
      cascadeHeight (val) {
        if (val > 0) {
          Tools.changeValAndLink2Number(this.conf.bodyStyle, ['height', 'lineHeight'], val)
        } else {
          delete this.conf.bodyStyle.lineHeight
          delete this.conf.bodyStyle.height
        }
      }
    }
  }
</script>
