/*
 * @Author: ylj
 * @Date: 2017-12-11 17:35:56
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-11 17:38:18
 */

<template>
  <div class="seaFieldConfigClss">
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false" @close="handleClose" :size="size" >
    <el-form :model.sync="form" ref="seaFieldConfigForm">
      <el-row :gutter="1">
        <el-col :span="24">
          <el-form-item prop="checkList">
            <el-checkbox :indeterminate="isIndeterminate" v-model="isCheckAll" @change="handleCkallChange">全选</el-checkbox>
            <el-form-item>
              <el-checkbox-group v-model="form.checkList" @change="setCkeckList">
                <el-checkbox v-for="item in fields" :label="item.name" :key="item.name">
                  <span class="el-checkbox__label"> {{ item.label }} </span>
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click.native="visible = false">取消</el-button>
        <el-button @click="resetForm('seaFieldConfigForm')">重置</el-button>
        <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
  </div>
</template>
<script>
// import Util from '../../../common/js/util'
export default {
  props: {
    size: {
      type: String,
      default: 'small'
    },
    title: {
      type: String,
      default: '新增'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      fields: [],
      visible: false,
      loading: false,
      isIndeterminate: false,
      isCheckAll: false,
      form: {
        checkList: []
      }
    }
  },
  computed: {
    checkboxLength () {
      let num = 0
      for (let key in this.fields) {
        if (key !== undefined) {
          num++
        }
      }
      return num
    }
  },
  methods: {
    show: function (fields, checkList) {
      this.loading = false
      this.visible = true
      this.fields = fields
      this.form.checkList = (checkList !== undefined ? checkList : [])
    },
    setCkeckList (val) {
      let ckCount = val.length
      this.isCheckAll = ckCount === this.checkboxLength
      this.isIndeterminate = ckCount > 0 && ckCount < this.checkboxLength
    },
    handleSubmit: function () {
      this.$refs['seaFieldConfigForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let result = []
            for (let i = 0; i < this.form.checkList.length; i++) {
              let key = this.form.checkList[i]
              result.push(this.fields[key])
            }
            this.callback(result)
            this.visible = false
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    resetForm (formName) {
      this.$refs['seaFieldConfigForm'].resetFields()
      // this.target = {}
      // this.target = Object.assign({}, this.oldValue)
    },
    handleClose () {
      this.$refs['seaFieldConfigForm'].resetFields()
      // this.target = {}
    },
    handleCkallChange (val) {
      this.form.checkList = this.isCheckAll ? this.setAllFields() : []
      this.isIndeterminate = false
    },
    setAllFields () {
      let array = []
      for (let key in this.fields) {
        array.push(key)
      }
      return array
    }
  }
}
</script>
<style lang="scss">
.seaFieldConfigClss {
  .ckboxGroupCls {
    float: left;
    text-align: right;
    padding-left: 0px;
    padding-right: 8px;
    width: 80px;
  }
  .el-dialog__body {
    padding-top: 5px;
  }
}
</style>
