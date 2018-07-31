<template>
  <div></div>
</template>
<script>
export default {
  created () {
    // 获取token
    this.$sso.requestAccessToken().then(() => {
      // 去掉地址栏的code参数，然后重新加载
      this.$sso.reloadWithoutCode()
    }).catch((e) => {
      // 异常，跳转到login页面
      console.log(e)
      // this.$sso.login()
    })
  }
}
</script>
