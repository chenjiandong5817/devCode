<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<!-- 代码模块化预备 -->
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">创建二维码</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table v-bind:data="distributions" highlight-current-row v-loading="listLoadingDistribution" :height="tableHeight" style="width: 100%;text-align: center;">
      <el-table-column prop="distributionName" label="分销渠道名称" header-align="center" min-width="250">
      </el-table-column>
      <el-table-column prop="newFans" label="新粉丝（关注/取消）" header-align="center" min-width="250">
      </el-table-column>
      <el-table-column prop="oldFans" label="老粉丝（关注/取消）" header-align="center" min-width="250">
      </el-table-column>
      <el-table-column label="二维码生成" width="180" header-align="center" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="downloadTwoCode(scope.$index, scope.row)"><span><i class="el-icon-download"></i> 获取二维码 </span></el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" header-align="center" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination :to="getDistribution" ref="page"></pagination>
    <!-- 删除 -->
    <common-delete
        :to="API.removeDistributionCode().go"
        :callback="getDistribution"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
    <!-- 新增 -->
    <div class="twoCodeDialog">
      <el-dialog title="创建二维码" :visible.sync="addDialogVisiable">
        <el-form :model="form" :rules="rules" ref="createTwoCode">
          <el-form-item label="分销渠道名称" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.name" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <!-- 说明 -->
        <el-row>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
          <el-col :span="20">
            <div>
              说明：
            </div>
          </el-col>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
        </el-row>
        <!-- 内容一 -->
        <el-row>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
          <el-col :span="20">
            <div>
              1、分销渠道名称不超过20个字符（10个汉字），仅支持中英文及汉字输入，保存后不可修改
            </div>
          </el-col>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
        </el-row>
        <!-- 内容二 -->
        <el-row>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
          <el-col :span="20">
            <div>
              2、粉丝识别：打标签及分组，同时在公众号后台“用户管理”中自动打标签及分组以区分粉丝来源
            </div>
          </el-col>
          <el-col :span="2">
            <div style="width: 5px;height: 5px;"></div>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addConfirm">保存</el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 二维码图片显示 -->
    <div class="imgDownload">
      <el-dialog
        title="二维码展示"
        :visible.sync="twoCodeDialogVisible"
        width="30%">
        <div style="width: 100%;height: 100%;">
          <img style="width: 100%;height: 100%;" border="0" src="https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100" alt="W3School">
        </div>
      </el-dialog>
    </div>
  </section>
</template>

<script>
  import Pagination from './../../components/Pagination'
  import commonDelete from './../../components/CommDelete'
  import Util from './../../common/js/util'
  import API from './../../api'
  export default {
    data () {
      return {
        // 过滤数据用的
        filters: {},
        // 表格数据
        distributions: [],
        // 表格加载
        listLoadingDistribution: false,
        // 新增框显示
        addDialogVisiable: false,
        // 二维码框的显示
        twoCodeDialogVisible: false,
        // 新增宽标题的宽度
        formLabelWidth: '120px',
        // 新增表格需要的数据
        form: {
          name: ''
        },
        // 表单验证
        rules: {
          name: null
        },
        // 名称的验证值
        nameValidate: null,
        // 表格高度
        tableHeight: 495,
        // 标签中使用
        API: API
      }
    },
    computed: {
    },
    components: {
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 下载二维码
      downloadTwoCode (index, row) {
        console.log('index', index)
        console.log('row', row)
        // 展示二维码
        this.twoCodeDialogVisible = true
      },
      // 删除分销渠道
      handleDel (index, row) {
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增分销渠道名称
      handleAdd () {
        this.addDialogVisiable = true
        this.$nextTick(() => {
          this.form.name = ''
          this.rules.name = this.nameValidate
        })
      },
      // 新增分销渠道名称时的限制
      getNameValidate () {
        this.nameValidate = (rule, value, callback) => {
          // 所填写字符串长度
          var strLength = 0
          if (value.length === 0) {
            return callback(new Error('名称不能为空！'))
          }
          // 循环每一个字符串
          for (var i = 0; i < value.length; i++) {
            // 判断满足条件只能是英文或者汉字
            if ((value.charCodeAt(i) >= 65 && value.charCodeAt(i) <= 90) || (value.charCodeAt(i) >= 97 && value.charCodeAt(i) <= 122) || (value.charCodeAt(i) > 256)) {
              // 获得长度
              if (value.charCodeAt(i) > 256) {
                strLength = strLength + 2
              } else {
                strLength = strLength + 1
              }
              if (strLength > 20) {
                return callback(new Error('分销渠道名称不能超过20个字符（10个汉字）!'))
              }
            } else {
              // 不是英文或者汉字的情况
              return callback(new Error('仅支持英文或者汉字！'))
            }
          }
          callback()
        }
      },
      // 确定添加该分销渠道
      addConfirm () {
        this.$refs['createTwoCode'].validate((valid) => {
          if (valid) {
            this.addDialogVisiable = false
            API.addDistributionCode().go(this.form.name).then((data) => {
              this.addDialogVisiable = false
              this.$notify(Util.notifyBody(false, data.message))
            })
          } else {
            return false
          }
        })
      },
      // 获取表格数据
      getDistribution () {
        this.distributions = [
          {
            id: 1,
            distributionName: '陈建东',
            newFans: 5 + '/' + 2,
            oldFans: 2 + '/' + 3
          },
          {
            id: 2,
            distributionName: '陈建南',
            newFans: 8 + '/' + 5,
            oldFans: 2 + '/' + 7
          }
        ]
        this.$refs['page'].set('total', 2)
        this.getNameValidate()
      }
    },
    mounted () {
      this.getDistribution()
    }
  }
</script>

<style>
    .twoCodeDialog .el-input__inner {
      width: 70%!important;
    }
    .twoCodeDialog .el-dialog__footer {
      text-align: center;
    }
    /*768以内的*/
    @media screen and (min-height: 0px) and (max-height: 767px) {
      .twoCodeDialog .el-dialog {
        width: 40%!important;
      }
      .imgDownload .el-dialog {
        width: 25%!important;
      }
    }
    /*768到1080之间的*/
    @media screen and (min-height: 768px) and (max-height: 1080px) {
      .twoCodeDialog .el-dialog {
        width: 35%!important;
      }
      .imgDownload .el-dialog {
        width: 20%!important;
      }
    }
    /*做1080以上分辨率的适配*/
    @media screen and (min-height: 1080px) {
      .twoCodeDialog .el-dialog {
        width: 30%!important;
      }
      .imgDownload .el-dialog {
        width: 15%!important;
      }
    }
</style>
