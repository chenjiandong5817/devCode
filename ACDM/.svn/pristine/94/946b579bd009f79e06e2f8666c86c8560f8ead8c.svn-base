<template>
    <el-tabs v-model="activeName">
        <el-tab-pane v-for="item in items" :label="item.label" :name="item.name" :key="item.name">
            <tab-content :list="list" @update="updateList" :activeName="activeName"></tab-content>
        </el-tab-pane>
  </el-tabs>
</template>
<script>
import tabContent from './tabContent'
import { adjustPage } from '@/util/util'
export default {
  components: {
    tabContent
  },
  data () {
    return {
      activeName: 'MEALS_SERVICE',
      list: [],
      items: [
        {label: '配餐服务', name: 'MEALS_SERVICE', type: 'MEALS_SERVICE'},
        {label: '住宿服务', name: 'HOTEL_SERVICE', type: 'HOTEL_SERVICE'},
        {label: '派车服务', name: 'VEHICLE_SERVICE', type: 'VEHICLE_SERVICE'},
        {label: '追加配餐', name: 'ADD_MEALS_SERVICE', type: 'ADD_MEALS_SERVICE'}
      ],
      pager: {
        pageSize: 15,
        pageNumber: 1,
        total: 0
      }
    }
  },
  mounted () {
    this.getList()
  },
  watch: {
    activeName () {
      this.list = []
      this.getList()
    }
  },
  methods: {
    updateList () {
      this.getList()
    },
    getList () {
      let ajax = this.$auth('get_progress_role_list_by_type')
      if (ajax) {
        ajax(Object.assign({serviceType: this.activeName}, adjustPage(this.pager))).then((res) => {
          if (res.status === 1) {
            this.list = res.data
          }
        })
      }
    }
  }
}
</script>
