<template>
  <el-form :model="templateConf" class="default-marquee-container">
    <el-form-item prop="timestamp" v-show="false">
      <span>编号&nbsp;</span>
      <el-input v-model="templateConf.timestamp" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item prop="type">
      <el-col :span="22">
        <span>模板类型&nbsp;</span>
        <el-input v-model="templateConf.type" :disabled="true" class="default-input"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>字段名称&nbsp;</span>
        <el-select v-model="templateConf.columnName" filterable clearable placeholder="选择字段">
          <template v-for="columnName in dataSourceKeys">
            <el-option :key="columnName" :label="columnName" :value="columnName"></el-option>
          </template>
        </el-select>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>滚动速度&nbsp;</span>
        <el-input type="number" v-model="templateConf.speed" class="default-input"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>有值转化&nbsp;</span>
        <el-switch v-model="optionSwitch.transExist" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-input v-model="templateConf.transExist" class="default-input" v-if="optionSwitch.transExist"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>空值替换&nbsp;</span>
        <el-switch v-model="optionSwitch.emptyReplace" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-select v-model="templateConf.emptyReplace" filterable multiple placeholder="选择字段" v-if="optionSwitch.emptyReplace">
          <template v-for="columnName in dataSourceKeys">
            <el-option :key="columnName" :label="columnName" :value="columnName"></el-option>
          </template>
        </el-select>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>锁定键值&nbsp;</span>
        <el-switch v-model="optionSwitch.property" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
          <div>
            默认当前模板的值为一个对象，并从中固定对象的一个属性为当前模板的值，例如
            <pre v-html="JSON.stringify({'zh-cn': '标题', 'en-ww': 'title', 'ja-jp': 'タイトル'}, null, 4)"></pre>
            固定【zh-cn】，获取的值为‘标题’
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />

        <el-input v-model="templateConf.property" class="default-input" v-if="optionSwitch.property"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>静态值&nbsp;</span>
        <el-switch v-model="optionSwitch.value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
          <div>
            选中该选项，【字段名称】将失效
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input v-model="templateConf.value" class="default-input" v-if="optionSwitch.value"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>前缀字符&nbsp;</span>
        <el-switch v-model="optionSwitch.prefix" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-input v-model="templateConf.prefix" class="default-input" v-if="optionSwitch.prefix"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>后缀字符&nbsp;</span>
        <el-switch v-model="optionSwitch.suffix" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-input v-model="templateConf.suffix" class="default-input" v-if="optionSwitch.suffix"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>语言锁定&nbsp;</span>
        <el-switch v-model="optionSwitch.langLock" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-select v-model="templateConf.langLock" filterable clearable placeholder="选择语言" v-if="optionSwitch.langLock">
          <template v-for="lang in langs">
            <el-option :key="lang.value" :label="lang.label" :value="lang.value"></el-option>
          </template>
        </el-select>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>语言排除&nbsp;</span>
        <el-switch v-model="optionSwitch.langExcept" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-langExcept`" placement="top-start" width="250" trigger="hover">
          <div>
            当选择了【语言锁定】，该选项失效
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-select v-model="templateConf.langExcept" filterable multiple placeholder="选择语言" v-if="optionSwitch.langExcept">
          <template v-for="lang in langs">
            <el-option :key="lang.value" :label="lang.label" :value="lang.value"></el-option>
          </template>
        </el-select>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>转换颜色&nbsp;</span>
        <el-switch v-model="optionSwitch.transColor" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-transColor`" placement="top-start" width="250" trigger="hover">
          <div>
            请严格按照以下格式填写，否则不生效：
            <pre v-html="JSON.stringify({'matchType': 'equals', 'matchSet': {'ARR': '#ff0000', 'ENR': '#000'}}, null, 4)"></pre>
            matchType可选： begin | like | end | equals
            matchSet输入：{'ARR': '#ff000'} 表示当模板文本为'ARR'的时候字体转化为红色（#ff0000）
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input type="textarea" :value="JSON.stringify(templateConf.transColor, null, 2)" @input="(value) => inputStr2Json(templateConf, 'transColor', value)"  v-if="optionSwitch.transColor"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>配置闪烁&nbsp;</span>
        <el-switch v-model="optionSwitch.twinkle" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-twinkle`" placement="top-start" width="250" trigger="hover">
          <div>
            请严格按照以下格式填写，否则不生效：
            <pre v-html="JSON.stringify({'matchType': 'equals', 'matchSet': {'ARR': true, 'ENR': true}}, null, 4)"></pre>
            matchType可选： begin | like | end | equals
            matchSet输入：{'ARR': true} 表示当模板文本为'ARR'的时候可以闪烁
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input type="textarea" :value="JSON.stringify(templateConf.twinkle, null, 2)" @input="(value) => inputStr2Json(templateConf, 'twinkle', value)"  v-if="optionSwitch.twinkle"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>特殊函数&nbsp;</span>
        <el-switch v-model="optionSwitch.special" active-color="#13ce66" inactive-color="#ff4949"></el-switch><br />
        <el-select v-model="templateConf.special" filterable clearable placeholder="选择函数" v-if="optionSwitch.special">
          <template v-for="special in specialMethods">
            <el-option :key="special.value" :label="special.label" :value="special.value"></el-option>
          </template>
        </el-select>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>键值替换&nbsp;</span>
        <el-switch v-model="optionSwitch.replace" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-replace`" placement="top-start" width="250" trigger="hover">
          <div>
            请严格按照以下格式填写，否则不生效：
            <pre v-html="JSON.stringify([{'key': 'YOUR_KEY', 'columnName': 'DATASOURCE_COLUMN'}, {'key': 'YOU_KEY', 'value': 'YOUR_VALUE'}], null, 4)"></pre>
            可以设置多个替换键值对。<br />
            该选项会把模板文本中${YOUR_KEY}转化为对应的值。<br />
            columnName 是转化为当前数据源中其他字段的值 <br />
            value 是直接转成该值
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input type="textarea" :value="JSON.stringify(templateConf.replace, null, 2)" @input="(value) => inputStr2Json(templateConf, 'replace', value)"  v-if="optionSwitch.replace"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>合并文本&nbsp;</span>
        <el-switch v-model="optionSwitch.mergeText" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-mergeText`" placement="top-start" width="250" trigger="hover">
          <div>
            请严格按照以下格式填写，否则不生效：
            <pre v-html="JSON.stringify({separator: '/', matchArray: ['abc', null, '123'], addsName: 'carrier'}, null, 4)"></pre>
            separator： 合并文本的连接字符。<br />
            matchArray：当模板文本包含于该数组内满足匹配条件,可以为null空对象<br />
            columnName：合并的字段名称
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input type="textarea" :value="JSON.stringify(templateConf.mergeText, null, 2)" @input="(value) => inputStr2Json(templateConf, 'mergeText', value)"  v-if="optionSwitch.mergeText"></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>日期格式化&nbsp;</span>
        <el-switch v-model="optionSwitch.date" active-color="#13ce66" inactive-color="#ff4949"></el-switch>&nbsp;
        <el-popover :ref="`popover-text-date`" placement="top-start" width="250" trigger="hover">
          <div>
            请严格按照以下格式填写，否则不生效：
            <pre v-html="JSON.stringify({'formatFrom': 'yyyy-MM-dd HH:mm:ss', 'formatTo': 'HH:mm'}, null, 4)"></pre>
            日期格式表示：年yyyy | 月MM | 日dd | 小时HH | 分mm | 秒ss <br />
            例如： yyyy-MM-dd HH:mm:ss 为2018-01-01 12:00:00
          </div>
          <i class="el-icon-warning" slot="reference"></i>
        </el-popover><br />
        <el-input type="textarea" :value="JSON.stringify(templateConf.date, null, 2)" @input="(value) => inputStr2Json(templateConf, 'date', value)"  v-if="optionSwitch.date"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-col :span="11">
        <span>样式&nbsp;</span>
        <el-input type="textarea" :rows="2" :value="JSON.stringify(templateConf.style, null, 2)" @input="(value) => inputStr2Json(templateConf, 'style', value)" ></el-input>
      </el-col>
      <el-col :span="11" :offset="1">
        <span>文本样式&nbsp;</span>
        <el-input type="textarea" :rows="2" :value="JSON.stringify(templateConf.textStyle, null, 2)" @input="(value) => inputStr2Json(templateConf, 'textStyle', value)" ></el-input>
      </el-col>
    </el-form-item>
  </el-form>
</template>
<script>
  import Tools from './../../../common/js/template-tools'
  import { inputStr2Json } from './editor-helper'
  export default {
    props: {
      conf: Object,
      dataSourceKeys: Array,
      submitCallback: Function
    },
    data () {
      return {
        templateConf: {},
        optionSwitch: {},
        optionSwitchArray: ['emptyReplace', 'transExist', 'property', 'date', 'prefix', 'suffix', 'langLock', 'langExcept', 'transColor', 'twinkle', 'special', 'replace', 'mergeText', 'value'],
        langs: [
          {label: '中文', value: 'zh-cn'},
          {label: '英文', value: 'en-ww'}
        ],
        specialMethods: [
          {label: '归纳柜台号', value: 'induceCounter'}
        ]
      }
    },
    watch: {
      conf (val) {
        this.initTemplateConf(val)
      }
    },
    created () {
    },
    mounted () {
      this.initTemplateConf(this.conf)
    },
    methods: {
      inputStr2Json,
      initTemplateConf (conf) {
        this.conf && (this.templateConf = Tools.deepCopy(conf))
        for (let option of this.optionSwitchArray) {
          this.$set(this.optionSwitch, option, !Tools.emptyObject(this.templateConf[option]))
        }
      },
      handleSubmit () {
        for (let option of this.optionSwitchArray) {
          if (!this.optionSwitch[option]) {
            if (this.templateConf[option] instanceof Array) {
              console.log(option, 'set')
              this.$set(this.templateConf, option, [])
            } else {
              console.log(option, 'delete')
              this.$delete(this.templateConf, option)
            }
          }
        }
        this.submitCallback && this.submitCallback(this.templateConf)
      }
    }
  }
</script>
<style scoped>
.default-marquee-container .default-input {
  width: auto;
}
</style>
