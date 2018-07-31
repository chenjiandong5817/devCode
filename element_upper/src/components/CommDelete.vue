/*
 * @Author: cdroid
 * @Date: 2017-04-18 14:34:30
 * @Last Modified by: llf
 * @Last Modified time: 2017-07-05 16:19:42
 * @Description: 通用删除界面
 */

<template>
</template>
<script>
import util from './../common/js/util.js'
import NProgress from 'nprogress'
export default {
  props: {
    title: {
      type: String,
      default: '提示'
    },
    content: {
      type: String,
      default: '确认删除该记录吗?'
    },
    type: {
      type: String,
      default: 'warning'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    }

  },
  methods: {
    del: function (row) {
      this.$confirm(this.content, this.title, {
        type: this.type
      }).then(() => {
        // this.listLoading = true
        NProgress.start()
        // let para = { id: row.id}
        // alert(JSON.stringify(para))
        this.to(row).then((res) => {
          // this.listLoading = false
          NProgress.done()
          this.$notify(util.notifyBody(res.ok, res.msg))
          this.callback()
      })
      }).catch(() => {
      })
    }
  }
}
</script>
<style></style>
