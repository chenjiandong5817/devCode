<template>
  <div class="pagination-fixed" :class="{'right': right, 'page-collapsed-x': right && pageCollapse, 'page-collapsed-y': !right && pageCollapse}">
      <el-row>
        <el-col :span="2" class="collapse-arrow" @click.native="togglePage">
          <i :class="['fa', {
              'fa-chevron-right': right && !pageCollapse,
              'fa-chevron-left': right && pageCollapse,
              'fa-chevron-down': !right && !pageCollapse,
              'fa-chevron-up': !right && pageCollapse
            }]"/>
        </el-col>
        <el-col :span="22">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="pageSizes"
            :page-size="pageSize"
            :layout="layout"
            :total="total">
          </el-pagination>
        </el-col>
      </el-row>
    </div>
</template>
<script>
export default {
  props: {
    right: Boolean,
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    pageSizes: {
      type: Array,
      default: () => {
        return [10, 20, 50, 100]
      }
    },
    pageSize: {
      type: Number,
      default: 10
    },
    total: Number,
    currentPage: {
      type: Number,
      default: 1
    }
  },
  data () {
    return {
      pageCollapse: false
    }
  },
  methods: {
    handleSizeChange (val) {
      this.$emit('update:pageSize', val)
      this.$emit('size-change', val)
    },
    handleCurrentChange (val) {
      this.$emit('update:currentPage', val)
      this.$emit('current-change', val)
    },
    togglePage () {
      this.pageCollapse = !this.pageCollapse
    }
  }
}
</script>
<style lang="scss" scoped>
@import '~@/styles/global.scss';
.pagination-fixed {
  position: fixed;
  bottom: calc(0px + #{$main-footer-height});
  box-sizing: border-box;
  border: 1px solid $content-border-color;
  border-radius: 6px;
  z-index: 90;
  background: $content-background-color;
  padding: $padding;
  height: 40px;
  transition: transform .3s ease-in;
  // box-shadow:-2px -1px 5px #cccccc;
  &.right {
    right: 3px;
    padding-right: 3px;
  }
  &.page-collapsed-x {
    transform: translateX(calc(100%));
    .collapse-arrow {
      transform: translateX(-27px);
      &:hover {
        color: $color-primary;
        transform: translateX(-30px) scale(1.3, 1.3);
      }
    }
  }
  &.page-collapsed-y {
    transform: translateY(41px);
    .collapse-arrow {
      transform: translateY(-41px);
      &:hover {
        color: $color-primary;
        transform: translateY(-41px) scale(1.3, 1.3);
      }
    }
  }

  .collapse-arrow {
    padding: 0 3px;
    width: 20px;
    line-height: 2;
    cursor: pointer;
    transition: transform .2s;
    &:hover {
      color: $color-primary;
      transform: scale(1.3, 1.3);
    }
  }
}
</style>
