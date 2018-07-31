<template>
  <el-form-item>
    <span class="editor-label" v-show="showLabel">{{ label }}</span>
      <el-select v-model="conf" :filterable="filterable" :multiple="multiple" :clearable="clearable" :placeholder="placeholder" :class="{'editor-select-full': fullWidth}">
        <template v-for="(item, index) in options">
          <el-option :key="index" :label="item[optionLabel]" :value="item[optionValue]">
            <template v-if="showValue">
              <span class="editor-select-options--left">{{ item[optionLabel] }}</span>
              <span class="editor-select-options--right">{{ item[optionValue] }}</span>
            </template>
          </el-option>
        </template>
      </el-select> 
  </el-form-item>
</template>
<script>
  export default {
    props: {
      value: [String, Array],
      showLabel: {
        type: Boolean,
        default: true
      },
      label: {
        type: String,
        default: ''
      },
      placeholder: {
        type: String,
        default: ''
      },
      fullWidth: {
        type: Boolean,
        default: true
      },
      options: Array,
      optionLabel: {
        type: String,
        default: 'label'
      },
      optionValue: {
        type: String,
        default: 'value'
      },
      showValue: {
        type: Boolean,
        default: true
      },
      filterable: Boolean,
      multiple: Boolean,
      clearable: Boolean
    },
    data () {
      return {
        conf: ''
      }
    },
    watch: {
      conf (val) {
        this.$emit('input', val)
      }
    },
    created () {
      this.conf = this.value
    }
  }
</script>
<style>
.editor-select-full {
  width: 100%;
}
.editor-select-options--left {
  float: left;
}
.editor-select-options--right {
  float: right;
  color: #7c7a7a;
  font-size: 13px;
}
</style>
