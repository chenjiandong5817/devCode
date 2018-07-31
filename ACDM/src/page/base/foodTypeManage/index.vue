<template>
<div>
  <div class="toolbar">
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="filters" placeholder="请输入配餐类型" size="small"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  size="small"   @click="handleSearchfoodType">查询</el-button>
        <el-button type="primary"  size="small"   @click="handleAddfoodType" v-auth ="`post_food_type_add`">新增</el-button>
        <el-button type="primary"  size="small" @click="handleUpdatefoodType" v-auth = "`put_food_type_update`" :disabled="!selectedNode">编辑</el-button>
        <el-button type="danger"  size="small" @click="deletefoodTypeVisible = true" v-auth="`post_food_type_remove`" :disabled="!selectedNode">删除</el-button>
      </el-form-item>
    </el-form>
  </div>
  <el-row>
    <el-col :span="12">
      <div class="food-type-tree">
        <div style="z-index: 1;position: absolute;">暂无数据</div>
        <el-tree
         :data="foodTypeAll"
          show-checkbox
          default-expand-all
          @node-click="handleNodeClick"
          node-key="id"
          ref="foodTypeTree"
          :props="defaultProps"
          highlight-current
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          style="z-index:2;flex: 1;align-self: flex-start;">
        </el-tree>
      </div>
    </el-col>
      <el-col :span="11">
        <div class="food-type-detail">
          <el-form label-position="right" label-width="80px" >
            <el-form-item label="检测冲突" >
              <el-radio-group v-model="needConflictDetect">
                <el-radio :label="0"  size="small">否</el-radio>
                <el-radio :label="1"  size="small">是</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="配餐类型">
              <el-input  :value="selectedNode ? selectedNode.foodName : ''" disabled></el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
  </el-row>
  <food-type-dialog  :parent = "parent" :openFlag="dialogVisible" @close="close" ref="foodTypeDialog"
  @addItem="addItem" @updateItem="updateItem"></food-type-dialog>
  <el-dialog
      title="确认"
      custom-class="dialog-to-food"
      :visible.sync="deletefoodTypeVisible"
      width="400px">
      <span>确定删除选择的节点？</span><br />
      <span>
        <el-checkbox v-model="deleteRootWhenNotChild">当无子节点时删除根节点</el-checkbox>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deletefoodTypeVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="submitDeletefoodType" size="small">确 定</el-button>
      </span>
  </el-dialog>
</div>
</template>

<script>
import foodTypeDialog from './food-type-dialog'
import { deepCopy } from '@/util/util'
export default {
  components: {
    foodTypeDialog
  },
  data () {
    return {
      defaultProps: {
        children: 'children',
        label: 'foodName'
      },
      needConflictDetect: 0,
      selectedNode: null,
      id: null,
      addFlag: false,
      dialogVisible: false,
      parent: {
        foodName: null,
        id: null
      },
      deletefoodTypeVisible: false,
      deleteRootWhenNotChild: true,
      filters: null,
      foodTypeAll: []
    }
  },
  computed: {
    // ...mapGetters(['foodTypeAll'])
  },
  mounted () {
    this.getfoodTypeList()
  },
  methods: {
    handleSearchfoodType () {
      this.selectedNode = null
      this.needConflictDetect = 0
      this.$refs['foodTypeTree'].filter(this.filters)
    },
    filterNode (value, data) {
      if (!value) return true
      return data.foodName.indexOf(value) !== -1
    },
    getfoodTypeById () {
      let getfoodType = this.$auth('get_food_type')
      getfoodType({id: this.id}).then((res) => {
        this.dialogVisible = true
        this.$refs['foodTypeDialog'].toUpdate(res.data)
      })
    },
    getfoodTypeList () {
      let getfoodTypeList = this.$auth('get_food_type_list')
      if (getfoodTypeList) {
        getfoodTypeList().then((res) => {
          if (res.status === 1) {
            this.foodTypeAll = res.data
          }
        })
      } else {
        this.$fail('无权限访问！')
      }
    },
    handleNodeClick (data) {
      this.selectedNode = data
      this.needConflictDetect = data.needConflictDetect
    },
    close (data) {
      this.parent.foodName = null
      this.parent.id = null
      this.dialogVisible = false
    },
    handleAddfoodType () {
      this.dialogVisible = true
      if (this.selectedNode) {
        this.parent.foodName = this.selectedNode.foodName
        this.parent.id = this.selectedNode.id
      }
      this.$refs['foodTypeDialog'].toAdd()
    },
    handleUpdatefoodType () {
      if (!this.selectedNode) {
        this.$fail('请先选择一个节点')
        return
      }
      let ele = this.$refs['foodTypeTree'].getNode(this.selectedNode.id).parent
        ? this.$refs['foodTypeTree'].getNode(this.selectedNode.id).parent
        : ''
      this.parent.foodName = ele ? ele.data.foodName : ''
      this.parent.id = ele ? ele.data.id : ''
      this.id = this.selectedNode.id
      this.getfoodTypeById()
    },
    submitDeletefoodType () {
      let keys = this.$refs['foodTypeTree'].getCheckedKeys(!this.deleteRootWhenNotChild)
      if (!keys || keys.length === 0) {
        this.$fail('未选中节点')
        this.deletefoodTypeVisible = false
        return
      }
      let deleteItem = this.$auth('post_food_type_remove')
      deleteItem({ ids: keys }).then((res) => {
        if (res.status === 1) {
          this.$ok(res.food)
          this.deletefoodTypeVisible = false
          this.getfoodTypeList()
          this.removeData()
        }
      })
    },
    addItem (data) {
      let foodTypeAdd = this.$auth('post_food_type_add')
      foodTypeAdd(data).then((res) => {
        if (res.status === 1) {
          this.$ok(res.food)
          this.getfoodTypeList()
          this.removeData()
        } else {
          this.$fail(res.food)
        }
      })
    },
    updateItem (data) {
      let updateItem = this.$auth('put_food_type_update')
      updateItem(deepCopy(data)).then((res) => {
        if (res.status === 1) {
          this.$ok(res.food)
          this.getfoodTypeList()
          this.removeData()
        } else {
          this.$fail(res.food)
        }
      })
    },
    removeData () {
      this.selectedNode = null
      this.dialogVisible = false
      this.id = null
      this.needConflictDetect = 0
    }
  }
}
</script>

<style>
.food-type-tree,
.food-type-detail {
  border: 1px solid #ebebeb;
  border-radius: 3px;
  transition: 0.2s;
  box-shadow: 0 0 8px 0 rgba(232, 237, 250, 0.6),
    0 2px 4px 0 rgba(232, 237, 250, 0.5);
}
.food-type-tree{
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 38px;
}
.food-type-detail {
  margin: 0 10px;
  padding: 10px;
}
.food-type-detail .el-input__inner {
    color: #1f1f1f !important;
  }
</style>
