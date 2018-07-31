<template>
  <el-dialog :title="`设置服务权限 > ${target ? target.name : '请先选择服务...'}`" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="580px">
    <div class="service-client-container">
      <el-transfer
        class="service-client-transfer"
        v-model="selectedKeyArray"
        filterable
        :titles="['服务列表', '已选服务']"
        :props="{
          key: 'id',
          label: 'name'
        }"
        :data="dataArray"
        v-loading="loading"
      >
      </el-transfer>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { deepCopy } from '@/util/util'
// import { validatenull } from '@/util/validate'
export default {
  data () {
    return {
      visible: false,
      loading: false,
      target: null,
      selectedKeyArray: [],
      dataArray: []
    }
  },
  methods: {
    show (row) {
      this.target = deepCopy(row)
      this.loading = true
      let getDataAjax = this.$auth('get_service_all')
      let getExistAjax = this.$auth('get_service_clients')
      if (!getDataAjax || !getExistAjax) {
        this.$fail('您没有查询服务的权限')
      } else {
        this.visible = true
        Promise.all([getDataAjax(), getExistAjax({id: this.target.id})]).then(result => {
          console.log(result)
          this.dataArray = result[0].status ? result[0].data : []
          this.selectedKeyArray.push(...(result[1].status ? result[1].data.map(item => item.id) : []))
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleClose () {
      this.selectedKeyArray.splice(0)
    },
    handleSubmit () {
      let params = {
        id: this.target.id,
        clients: this.selectedKeyArray.join(',')
      }
      let ajax = this.$auth('put_service_clients')
      if (ajax) {
        this.loading = true
        ajax(params).then((res) => {
          this.loading = false
          if (res.status) {
            this.$ok(res.message)
            this.$emit('afterSave')
            this.$nextTick(() => {
              this.visible = false
            })
          }
        }).catch(() => {
          this.loading = false
          this.visible = false
        })
      }
    }
  }
}
</script>
<style lang="scss">
.service-client-dialog {
  .service-client-container {
    display: flex;
    justify-content: center;
  }
}
</style>
