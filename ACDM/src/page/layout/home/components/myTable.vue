<template>
  <div class="tableRed">
    <el-table :data="data" size="small"   stripe :max-height="height">
      <template v-for="colConfig in colConfigs">
        <slot v-if="colConfig.slot" :name="colConfig.slot"></slot>
        <el-table-column v-bind="colConfig" :key="colConfig.label" :width="colConfig.width"></el-table-column>
      </template>
    </el-table>
  </div>
</template>

<script>
export default {
  props: ['colConfigs', 'data', 'height']
}
</script>
<style>
.tableRed th{
    background-color: #ff9493;
    color: #bec5d2;
}
</style>
