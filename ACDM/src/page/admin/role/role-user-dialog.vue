<template>
  <el-dialog title="设置人员角色" :visible.sync="visible" :close-on-click-modal="false" custom-class="role-user-dialog"
    @close="handleClose" width="580px">
    <div class="role-user-container">
      <el-transfer
        class="role-user-transfer"
        v-model="selectedUserIdArray"
        filterable
        :titles="['用户列表', '已选用户']"
        :props="{
          key: 'id',
          label: 'name'
        }"
        :data="userArray"
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
import { validatenull } from '@/util/validate'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      visible: false,
      loading: false,
      role: null,
      selectedUserIdArray: [],
      userArray: []
    }
  },
  computed: {
    ...mapGetters(['roleAll'])
  },
  methods: {
    show (row) {
      this.role = deepCopy(row)
      this.loading = true
      let getUserAjax = this.$auth('get_user_all')
      if (!getUserAjax) {
        this.$fail('您没有查询用户的权限')
      } else {
        this.visible = true
        getUserAjax().then(res => {
          this.userArray = res.data || []
          let role = this.roleAll.find(item => item.id === this.role.id)
          let selectedUserIds = (role.meta && !validatenull(role.meta.users)) ? role.meta.users : []
          if (!validatenull(selectedUserIds)) {
            this.userArray.forEach((user, index) => {
              if (selectedUserIds.includes(user.id)) {
                this.selectedUserIdArray.push(user.id)
              }
            })
          }
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleClose () {
      this.selectedUserIdArray.splice(0)
    },
    handleSubmit () {
      let params = {
        roleId: this.role.id,
        userIds: this.selectedUserIdArray
      }

      let ajax = this.$auth('put_role_save_users')
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
.role-user-dialog {
  .role-user-container {
    display: flex;
    justify-content: center;
  }
}
</style>
