<template>
  <el-form :model="conf" label-width="80px" label-position="top">
    <el-form-item>
      <span class="editor-label">名称(name)</span>
      <el-input v-model="conf.name" placeholder="表格列名称"></el-input>
    </el-form-item>
    <el-form-item>
      <span class="editor-label">
        <el-popover :ref="`popover-text-label`" placement="top-start" width="250" trigger="hover">
          <div>
            打开标题，即使用多语言
          </div>
          <i 
            class="el-icon-warning" 
            slot="reference" />
        </el-popover>
        标题(label)
        <el-switch v-model="switchOptions.label.switch" active-color="#13ce66" inactive-color="#ff4949" on-text="多语言" off-text="单语言" :width="72"></el-switch>
      </span>
      <div class="editor-inner-pannel editor-inner-pannel-left">
        <el-form-item v-if="switchOptions.label.switch">
          <el-form-item>
            <el-button type="primary" size="mini" icon="plus" @click="handleAddLabelLang"></el-button>
          </el-form-item>
          <el-form-item v-for="(item, index) in switchOptions.label.value" :key="index">
            <el-col :span="7">
              <selector v-model="item.label" :options="Tools.langOptions()" :showValue="false" :showLabel="false"></selector>
            </el-col>
            <el-col :span="14" style="margin-left: 4px;">
              <el-input v-model="item.value"></el-input>
            </el-col>
            <el-col :span="2" style="margin-left: 4px;">
              <el-button type="default" icon="close" @click="handleRemoveLabelLang(index)"></el-button>
            </el-col>
          </el-form-item>
        </el-form-item>
        <el-form-item v-else>
          <el-input v-model="conf.label" placeholder="标题文字"></el-input>
        </el-form-item>
      </div>
    </el-form-item>
    <el-form-item>
      <span class="editor-label">
        <el-popover :ref="`popover-text-headerStyle`" placement="top-start" width="250" trigger="hover">
          <div>
            打开标题样式，会在【表格】模板的【默认标题样式】的基础上在修改样式
          </div>
          <i 
            class="el-icon-warning" 
            slot="reference" />
        </el-popover>
        标题样式(headerStyle)
        <el-switch v-model="switchOptions.headerStyle.switch" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
      </span>
      <div v-if="switchOptions.headerStyle.switch" class="editor-inner-pannel editor-inner-pannel-left">
        <div>
          <el-col :span="11">
            <color-picker v-model="conf.headerStyle.backgroundColor" label="背景色(backgroundColor)"></color-picker>
          </el-col>
          <el-col :span="11" :offset="1">
            <selector v-model="conf.headerStyle.textAlign" :options="Tools.alignOptions()" label="对齐方式(textAlign)" :showValue="false"></selector>
          </el-col>
        </div>
      </div>
    </el-form-item>
    <el-form-item>
      <fieldset class="editor-fieldset">
        <legend>列样式</legend>
        <div>
          <el-form-item>
            <el-col :span="6">
              <span class="editor-label">字体大小(fontSize)</span>
              <el-input type="number" :value="conf.style.fontSize" @input="val => Tools.inputStr2Number(conf.style, 'fontSize', val)"></el-input>
            </el-col>
            <el-col :span="7" :offset="1">
              <span class="editor-label">
                <el-popover :ref="`popover-text-width`" placement="top-start" width="250" trigger="hover">
                  <div>
                    打开标题，即使用最小宽度，当内容太长会自动延长
                  </div>
                  <i class="el-icon-warning" slot="reference" />
                </el-popover>
                宽度(width)
                <el-switch v-model="switchOptions.minWidth.switch" active-color="#13ce66" inactive-color="#ff4949" on-text="伸缩" off-text="固定"></el-switch>
              </span>
              <el-input type="number" :value="conf.style.minWidth" @input="val => Tools.inputStr2Number(conf.style, 'minWidth', val)" v-if="switchOptions.minWidth.switch"></el-input>
              <el-input type="number" :value="conf.style.width" @input="val => Tools.inputStr2Number(conf.style, 'width', val)" v-else></el-input>
            </el-col>
            <el-col :span="6" :offset="1">
              <selector v-model="conf.style.textAlign" :options="Tools.alignOptions()" :showValue="false" label="对齐方式(fontSize)"></selector>
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
        switchOptions: {
          label: {
            switch: false,
            value: [
              {label: 'zh-cn', value: ''},
              {label: 'en-ww', value: ''}
            ]
          },
          minWidth: {
            switch: false
          },
          headerStyle: {
            switch: false,
            // store ，以备切换之需
            value: {}
          }
        }
      }
    },
    watch: {
      conf: {
        handler (val) {
          this.$emit('input', val)
        },
        deep: true
      },
      'switchOptions.label': {
        handler (val) {
          if (val.switch) {
            this.conf.label = {}
            for (let item of val.value) {
              this.conf.label[item.label] = item.value
            }
          } else {
            this.conf.label = ''
          }
        },
        deep: true
      },
      'switchOptions.minWidth': {
        handler (val) {
          if (val.switch) {
            delete this.conf.style.width
          } else {
            delete this.conf.style.minWidth
          }
        },
        deep: true
      },
      'switchOptions.headerStyle': {
        handler (val) {
          if (val.switch) {
            this.conf.headerStyle = val.value
          } else {
            delete this.conf.headerStyle
          }
        },
        deep: true
      }
    },
    created () {
      if (Tools.isEmptyObject(this.value)) {
        this.conf = {
          type: 'GridColumn',
          typeLabel: '表格列',
          name: '',
          label: '',
          style: {},
          headerStyle: {},
          children: []
        }
      } else {
        this.conf = this.value
        this.initLabel()
        this.initStyle()
      }
    },
    methods: {
      initLabel () {
        let label = this.conf.label
        if (typeof label === 'string') {
          return
        }
        this.switchOptions.label.value.splice(0, this.switchOptions.label.value.length)
        for (let key in this.conf.label) {
          this.switchOptions.label.value.push({label: key, value: this.conf.label[key]})
        }
        this.$set(this.switchOptions.label, 'switch', true)
      },
      initStyle () {
        if (this.conf.style.minWidth) {
          this.$set(this.switchOptions.minWidth, 'switch', true)
        }
        if (!Tools.isEmptyObject(this.conf.headerStyle)) {
          this.$set(this.switchOptions.headerStyle, 'switch', true)
          this.switchOptions.headerStyle.value = this.conf.headerStyle
        }
      },
      handleAddLabelLang () {
        this.switchOptions.label.value.push({label: '', value: ''})
      },
      handleRemoveLabelLang (index) {
        this.switchOptions.label.value.splice(index, 1)
      }
    }
  }
</script>
