<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="配餐流程" name="food" class="food-main-class">
      <food></food>
    </el-tab-pane>
    <el-tab-pane label="住宿流程" name="accom" class="accm-main-class">
      <accom></accom>
    </el-tab-pane>
    <el-tab-pane label="车辆流程" name="vehicle" class="car-main-class">
      <vehicle></vehicle>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import accom from './accom'
import vehicle from './vehicle'
import Food from './food'
import { mapGetters } from 'vuex'
export default {
  components: {
    accom, vehicle, Food
  },
  data () {
    return {
      activeName: 'food'
    }
  },
  computed: {
    ...mapGetters(['service'])
  },
  methods: {
    handleClick (tab, event) {
    }
  },
  watch: {
    'service': {
      immediate: true,
      deep: true,
      handler (val) {
        if (val.activeName) {
          this.activeName = val.activeName
        }
      }
    },
    '$route.query': {
      immediate: true,
      handler (val) {
        if (val.id) {
          let ajax = this.$auth('get_service_item')
          if (!ajax) {
            return false
          } else {
            ajax({id: val.id}).then(res => {
              if (res.status && res.data) {
                let params = null
                switch (val.serviceType) {
                  case 'MEALS_SERVICE':
                  case 'ADD_MEALS_SERVICE':
                    this.activeName = 'food'
                    params = {
                      food: res.data,
                      activeName: 'food',
                      carrier: res.data.carrier,
                      flight: res.data.flight,
                      flightId: res.data.flightId
                    }
                    this.$store.dispatch('ChangeService', params)
                    break
                  case 'HOTEL_SERVICE':
                    this.activeName = 'accom'
                    params = {
                      accom: res.data,
                      activeName: 'accom',
                      carrier: res.data.carrier,
                      flight: res.data.flight,
                      flightId: res.data.flightId
                    }
                    this.$store.dispatch('ChangeService', params)
                    break
                  case 'VEHICLE_SERVICE':
                    this.activeName = 'vehicle'
                    params = {
                      vehicle: res.data,
                      activeName: 'vehicle',
                      carrier: res.data.carrier,
                      flight: res.data.flight,
                      flightId: res.data.flightId
                    }
                    this.$store.dispatch('ChangeService', params)
                    break
                }
              }
            })
          }
        } else {

        }
      }
    }
  }
}
</script>
<style lang="scss">
.carNumber {
  display: flex;
  justify-content:space-around;
  margin-top: 0px
}
.accm-main-class {
  .el-step.is-vertical .el-step__main {
    width: 100px;
  }
}
.car-main-class {
  .el-step.is-vertical .el-step__main {
    width: 100px;
  }
}
.food-main-class{
  .el-row {
    margin-bottom: 5px;
  }
  .title-setting {
    el-col {
      text-align: center;
    }
    span {
      font-size: 14px;
    }
  }
  .el-step__description.is-process {
    width: 76px; // 说明文字总是会换行 加大一点宽度
  }
  .right-and-bottom10 {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 10px;
  }
}
.el-step__title.is-process {
  color: #e6cc12;
}
.el-step__head.is-process {
  color: #e6cc12;
  border-color: #e6cc12;
}
.el-step__description.is-process {
  color: #e6cc12;
}
</style>
