<template>
  <div class="pull-auto top-menu">
    <el-menu :default-active="activeIndex" mode="horizontal">
      <template v-for="(item, index) in items">
        <el-menu-item :key="index" :index="item.parentId+''">{{item.label}}</el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'top-menu',
  data () {
    return {
      activeIndex: '0',
      items: [
      ]
    }
  },
  created () {},
  computed: {
  },
  methods: {
  }
}
</script>

<style scoped="scoped" lang="scss">
.top-menu {
  padding: 0 50px;
  margin-top: -4px;
  box-sizing: border-box;
}
</style>
