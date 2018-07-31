<template>
  <el-row type="flex" justify="center">
    <div>
      <select v-model="ip">
        <option value="10.1.16.65:9091">服务器</option>
        <option value="localhost:8080">本地测试</option>
      </select>
    </div>
    <ul>
      <li v-for="item in list" :key="item.deviceId" class="list-click-li">
        <a :href="`http://${ip}/#/${item.deviceId}/view`" class="list-click-a">{{ item.text }}</a>
      </li>
    </ul>
  </el-row>
</template>
<script>
export default {
  data () {
    return {
      ip: '10.1.16.65:9091',
      list: [
        {deviceId: '2251', text: '国内进港'},
        {deviceId: '10262', text: '出发航班'},
        {deviceId: '3393', text: '登机口候机引导'},
        {deviceId: '10370', text: '登机口'},
        {deviceId: '606', text: '登机引导'},
        {deviceId: '4051', text: '值机引导'},
        {deviceId: '10313', text: '全开柜台'},
        {deviceId: '10063', text: '普通柜台'},
        {deviceId: '10221', text: '专柜'},
        {deviceId: '10245', text: '行李转盘'},
        {deviceId: '601', text: '行李提取引导'},
        {deviceId: '1201', text: '福州-出港'},
        {deviceId: '1202', text: '福州-进港'}
      ]
    }
  }
}
</script>

<style scoped>
.list-click-li {
  font-size: 25px;
  color: #000;
  list-style-type: decimal;
}
.list-click-a {
  font-size: 25px;
  color: #000;
}
</style>
