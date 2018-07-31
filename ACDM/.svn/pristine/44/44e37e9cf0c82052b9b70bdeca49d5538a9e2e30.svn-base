<template>
    <div>
      <button @click="getData">user all</button>
      <button @click="getMe">me</button>
      <button @click="getData2">logout</button>
      <button @click="getData3">refresh_token</button>
      <button @click="getData4">login</button>
    </div>
</template>
<script>
import { GET } from '@/router/axios'
export default {
  methods: {
    getData4 () {
      this.$sso.login()
    },
    getData3 () {
      this.$sso.refreshAccessToken()
    },
    getData2 () {
      this.$sso.logout()
    },
    getData () {
      GET('/base/user/all').then(res => {
        console.log(res)
      })
    },
    getMe () {
      GET('/acdm/user/me').then(res => {
        console.log(res)
      })
    }
  }
}
</script>
