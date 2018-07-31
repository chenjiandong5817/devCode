<template>
  <div style="padding: 5px 15px;">正在退出...</div>
</template>
<script>
export default {
  mounted () {
    let vm = this
    setTimeout(() => {
      vm.$store.dispatch('DestroyFrame')
      vm.$sso.logout()
    }, 1000)
  }
}
</script>
