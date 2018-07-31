<template>
  <span class="top-message-container">
    <el-tooltip class="item" effect="dark" content="消息" placement="bottom">
      <span class="icon" :class="{ 'count': !isUnreceivable && unconfirmedCount > 0 }" @click="handleToggleMsgBox">
        <i class="fa" :class="{'fa-bell': !isUnreceivable, 'fa-bell-slash': isUnreceivable}"/>
        <span class="top-message-count" v-if="unconfirmedCount > 0">{{ unconfirmedCount > 99 ? '99+' : unconfirmedCount }}</span>
      </span>
    </el-tooltip>
    <div class="top-message-body" :class="{'visible': visible}">
      <div class="title">
        <span class="label">消息内容</span>
        <span class="options">
          <el-popover
            ref="messageOptions"
            placement="bottom"
            trigger="click"
            :visible-arrow="false"
            popper-class="message-poper-options">
            <el-checkbox-group v-model="checkedMessageOptions" @change="handleCheckedMessageOptions">
              <el-checkbox label="require">需确认</el-checkbox><br />
              <el-checkbox label="confirmed">已确认</el-checkbox><br />
              <el-checkbox label="notConfirm">未确认</el-checkbox>
            </el-checkbox-group>
            <div class="divided"></div>
            <div class="button">
              <button @click="() => handleFilterMessage(true)">重置</button>
              <button @click="() => handleFilterMessage(false)" :disabled="!isFilterMessage" :class="{'is-disabled': !isFilterMessage}">筛选</button>
            </div>
            <i class="fa fa-ellipsis-h" slot="reference" />
          </el-popover>
        </span>
      </div>
      <div class="table message-table-scroll">
        <template v-for="(item, index) in messages">
          <div
            :key="index"
            @click="() => handleShowConfirmBox(item)"
            class="table-item"
            :class="{'require': checkMessageRequire(item), 'checked': checkMessageRequire(item) && checkMessageChecked(item)}">
            <span class="content">{{ showMessageContent(item.messageInfo.content) }}</span>
            <span class="time">{{ formatTimestamp(item.receiveTime) }}</span>
          </div>
        </template>
        <div class="loading">
          <template v-if="messages && messages.length > 0">
            <span v-if="notDataLoading" class="no-data">已经拉到底了</span>
            <span v-else>
              <i v-if="loading" class="el-icon-loading"/>
              <a v-else @click="handleLoadingMoreMessage">加载更多</a>
            </span>
          </template>
         <template v-else>
           <span>没有消息</span>
         </template>
        </div>
      </div>
      <div class="message-footer" @click="() => handleToggleMsgBox()">
        <i class="fa fa-angle-double-up fa-lg fa-fw"/>
      </div>
    </div>
    <el-dialog
      :title="checkMessageRequire(selectMsg) ? '消息确认' : '消息内容'"
      custom-class="dialog-to-message"
      :visible.sync="msgVisible">
      <div style="word-wrap: break-word; white-space: normal;">
        <span>{{ selectMsg && selectMsg.messageInfo ? showMessageContent(selectMsg.messageInfo.content) : '' }}</span>
        <span v-if="isLinkMessage || (isButtonMessage && checkMessageChecked(selectMsg))">
          <br />
          <a style="font-style: italic; text-decoration: underline; cursor: pointer;" @click="() => handleMessageAction()">
            {{ actionMessageInfo.actionValue.text || '点击跳转' }}
          </a>
        </span>
      </div>
      <span slot="footer">
        <span class="message-time-span create-time">
          {{ selectMsg ? '接收时间： ' + formatDate(selectMsg.receiveTime) : '' }}
        </span>
        <span class="message-time-span check-time">
          {{ checkMessageChecked(selectMsg) ? '确认时间： ' + formatDate(selectMsg.confirmTime) : '' }}
        </span>
        <el-button @click="msgVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleCheckMessage" size="small" v-if="checkMessageRequire(selectMsg) && !checkMessageChecked(selectMsg)">确 认</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script>
import Moment from 'moment'
import { mapState, mapGetters } from 'vuex'
import { adjustPage } from '@/util/util'
import { validatenull } from '@/util/validate'
import { getReadedMsg, getUnreadMsg, receiveMsg, confirmMsg } from '@/api/admin'
import AudioUtil from '@/util/audio'
export default {
  data () {
    return {
      visible: false,
      msgVisible: false,
      loading: false,
      notDataLoading: false,
      selectMsg: null,
      // 分页参数
      pager: {
        pageSize: 10,
        pageNumber: 1,
        total: 0
      },
      // 消息弹框提示
      messageAlert: null,
      messageInterval: 30000,
      // 消息轮询器
      messageTimer: null,
      unconfirmedCount: 0,
      // 消息过滤
      checkedMessageOptions: []
    }
  },
  computed: {
    ...mapGetters(['isLoading', 'isUnreceivable', 'tabHome']),
    ...mapState({
      routingKeys: state => state.user.routingKeys,
      stompClient: state => state.user.stompClient,
      messages: state => state.user.messages
    }),
    isFilterMessage () {
      return this.checkedMessageOptions && this.checkedMessageOptions.length > 0
    },
    actionMessageInfo () {
      if (this.selectMsg && this.selectMsg.messageInfo) {
        return this.splitMessageContent(this.selectMsg.messageInfo.content)
      }
      return null
    },
    isLinkMessage () {
      return this.actionMessageInfo && (this.actionMessageInfo.type === 'link')
    },
    isButtonMessage () {
      return this.actionMessageInfo && (this.actionMessageInfo.type === 'button')
    }
  },
  watch: {
    isLoading: {
      immediate: true,
      handler: function (val, oldVal) {
        if (!val) {
          // 查询已读消息
          this.getReadedMessages().then(() => {
            // 查询未读消息
            return this.getUnreadMessages()
          }).then(receiveCount => {
            // 没有新消息，则提醒当前消息
            if (receiveCount === 0) {
              this.notifyMessage(0)
            }
          })
        }
      }
    },
    isUnreceivable: {
      immediate: true,
      handler (val) {
        if (val) {
          this.stopMessageTimer()
        } else {
          this.initMessageTimer()
        }
        this.$message({
          type: 'success',
          duration: 1500,
          customClass: 'message-notify-status',
          message: `已${val ? '关闭' : '开启'}消息接收`
        })
      }
    }
  },
  mounted () {
    this.connectToMQServer()
  },
  methods: {
    /**
     * 获取已读消息, 把新消息加在原消息列表后面
     */
    getReadedMessages (more) {
      return new Promise(resolve => {
        getReadedMsg(Object.assign({}, {options: this.checkedMessageOptions.join(',')}, adjustPage(this.pager))).then(res => {
          console.log('readed', res.data)
          let messages = []
          if (res.data && res.data.page) {
            let page = res.data.page
            messages = page.content
            // 如果是最后一页，去掉加载更多
            if (page.totalPages - page.number === 1) {
              this.loadNotNewData()
            } else {
              this.rebuildLoadMore()
            }
          }
          if (!res.status || validatenull(messages)) {
            this.$store.commit('SET_MESSAGES', messages)
            resolve(false)
          } else {
            this.$store.commit('SET_MESSAGES', more ? this.messages.concat(messages) : messages)
            this.unconfirmedCount = res.data.unconfirmedCount || 0
            resolve(true)
          }
        })
      })
    },
    //
    /**
     * 获取未读信息, 把消息放在原消息列表前面，原消息列表尾部减少对应的条数，防止分页错误
     */
    getUnreadMessages () {
      return new Promise(resolve => {
        getUnreadMsg({typeCodes: this.routingKeys.join(',')}).then(res => {
          console.log('unread', res)
          if (res.status && !validatenull(res.data)) {
            let list = res.data || []
            let messageIds = list.map(item => item.id)
            this.reveiveUnreadMsg(messageIds, resolve)
          } else {
            resolve(0)
          }
        })
      })
    },
    // 使未读消息被接收
    reveiveUnreadMsg (messageIds, resolve) {
      receiveMsg({messageIds}).then(res => {
        console.log('receive', res.data)
        if (res.status && !validatenull(res.data)) {
          this.unconfirmedCount = res.data.unconfirmedCount || 0
          let list = res.data.messages || []
          let receiveCount = list.length
          let messages = this.messages
          if (list.length > this.pager.pageSize) {
            list.splice(this.pager.pageSize)
          }
          // 如果当前处于过滤时刻，不需要对消息列表进行截断，否则分页会出错
          let doSplice = !this.isFilterMessage
          if (doSplice && messages.length + list.length > this.pager.pageSize * this.pager.pageNumber) {
            let last = messages.length + list.length - this.pager.pageSize * this.pager.pageNumber
            messages.splice(messages.length - last)
            this.rebuildLoadMore()
          }
          messages.unshift(...list)
          this.$store.commit('SET_MESSAGES', messages)
          this.notifyMessage(receiveCount)
          resolve && resolve(receiveCount)
        } else {
          resolve && resolve(0)
        }
      })
    },
    // 显示消息的主体部分
    showMessageContent (content) {
      let result = this.splitMessageContent(content)
      return result.content
    },
    // 通过关键字分割字符串
    splitMessageContent (content) {
      const keys = [
        { key: '\n--button--\n', type: 'button', requireParam: ['action', 'path'] },
        { key: '\n--link--\n', type: 'link', requireParam: ['text', 'action', 'path'] }
      ]
      let result = {
        content,
        type: null,
        actionValue: {
          action: ''
        },
        params: {}
      }
      keys.forEach(keyObj => {
        if (content.includes(keyObj.key)) {
          result.type = keyObj.type
          let array = content.split(keyObj.key)
          if (array[0] !== undefined) {
            result.content = array[0]
          }
          if (array[1] !== undefined) {
            let params = array[1].split('\n')
            params.forEach(param => {
              let keyValue = param.split('=')
              if (keyObj.requireParam.includes(keyValue[0])) {
                result.actionValue[keyValue[0].trim()] = keyValue[1].trim()
              } else {
                result.params[keyValue[0].trim()] = keyValue[1].trim()
              }
            })
          }
        }
      })
      return result
    },
    // 处理消息事件
    handleMessageAction () {
      if (!this.actionMessageInfo) {
        return
      }
      let actionValue = this.actionMessageInfo.actionValue
      switch (actionValue.action) {
        case 'ADD_TAB':
          console.log('add tab')
          this.$router.push({ path: actionValue.path || this.tabHome.value, query: this.actionMessageInfo.params })
          break
        default:
          console.log('test')
      }
    },
    // 提示消息
    notifyMessage (receiveCount) {
      if (this.messageAlert) {
        this.messageAlert.close()
      }
      if (this.unconfirmedCount === 0) {
        return
      }
      const h = this.$createElement
      this.messageAlert = this.$message({
        duration: 0,
        showClose: true,
        iconClass: 'el-message__icon fa fa-bell-o animated shake infinite',
        customClass: 'message-notify-new',
        message: h(
          'a',
          {
            on: {
              click: () => {
                this.messageAlert && this.messageAlert.close()
                this.handleToggleMsgBox(true)
              }
            }
          },
          [
            receiveCount ? `您收到 ${receiveCount} 条消息，` : ``,
            `当前共 `,
            h('span', { style: this.unconfirmedCount > 0 ? {color: '#ff0000', fontWeight: 700} : {} }, this.unconfirmedCount),
            ` 条${receiveCount ? '' : '消息'}需要确认`
          ]
        )
      })
    },
    // 声音提示
    voiceNotify () {
      AudioUtil.voice()
    },
    // 初始化消息轮询
    initMessageTimer () {
      this.stopMessageTimer()
      this.messageTimer = setInterval(this.getUnreadMessages, this.messageInterval)
    },
    // 停止消息轮询
    stopMessageTimer () {
      this.messageTimer && clearInterval(this.messageTimer)
    },
    // 年月日时分格式化
    formatDate (timestamp) {
      if (!timestamp) {
        return ''
      }
      return Moment(timestamp).format('YYYY-MM-DD HH:mm')
    },
    // 转换时间戳为格式化时间
    formatTimestamp (timestamp) {
      if (!timestamp) {
        return ''
      }
      // 当天消息
      let now = Moment()
      let msgTime = Moment(timestamp)
      if (now.dayOfYear() === msgTime.dayOfYear()) {
        return msgTime.format('HH:mm')
      } else {
        return msgTime.fromNow()
      }
    },
    // 判断消息是否需要确认
    checkMessageRequire (message) {
      if (!message) {
        return false
      }
      return Boolean(message.messageInfo && message.messageInfo.whetherConfirm)
    },
    // 判断消息是否已经确认
    checkMessageChecked (message) {
      if (!message) {
        return false
      }
      return Boolean(message.confirmTime)
    },
    // 显示或隐藏消息列表
    handleToggleMsgBox (status) {
      if (status === undefined) {
        this.visible = !this.visible
      } else {
        this.visible = status
      }
    },
    // 显示消息确认框
    handleShowConfirmBox (message) {
      this.selectMsg = message
      this.msgVisible = true
    },
    // 点击确认按钮，确认消息
    handleCheckMessage () {
      this.$confirm(`是否确定确认该消息?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.msgVisible = false
        confirmMsg({id: this.selectMsg.id, messageStatus: 'confirmed'}).then(res => {
          if (res.status && res.data) {
            this.selectMsg.confirmTime = res.data.confirmTime
            this.unconfirmedCount > 1 && (this.unconfirmedCount -= 1)
          }
          // 加入确认事件
          if (this.isButtonMessage) {
            this.handleMessageAction()
          }
        })
      }).catch(() => {})
    },
    // 过滤消息checkbox事件
    handleCheckedMessageOptions (val) {
      let notConfirmIndex = this.checkedMessageOptions.indexOf('notConfirm')
      let confirmedIndex = this.checkedMessageOptions.indexOf('confirmed')
      if (notConfirmIndex > confirmedIndex) {
        this.checkedMessageOptions = this.checkedMessageOptions.filter(item => item !== 'confirmed')
      } else {
        this.checkedMessageOptions = this.checkedMessageOptions.filter(item => item !== 'notConfirm')
      }
    },
    // 过滤消息
    handleFilterMessage (reset) {
      this.pager.pageNumber = 1
      reset && this.checkedMessageOptions.splice(0)
      this.getReadedMessages(false)
      // 关闭窗口
      this.$refs['messageOptions'].doClose()
    },
    // 无更新的数据可以加载
    loadNotNewData () {
      this.notDataLoading = true
    },
    // 当进行截断操作的时候
    rebuildLoadMore () {
      this.notDataLoading = false
    },
    // 加载更多
    handleLoadingMoreMessage () {
      this.loading = true
      this.pager.pageNumber += 1
      this.getReadedMessages(true).then(flag => {
        this.loading = false
        !flag && this.loadNotNewData()
      })
    },
    // 连接到mq服务器
    connectToMQServer () {
      this.stompClient.bind(this.routingKeys, (message) => {
        console.log(message)
        if (this.isUnreceivable) {
          console.error('当前消息设置不可接收')
          return
        }
        // 声音提醒
        this.voiceNotify()
        // 收到消息, 重置轮询时间
        this.initMessageTimer()
        // 请求未读消息
        this.$nextTick(() => {
          this.getUnreadMessages()
        })
      })
    }
  },
  destroyed () {
    this.stopMessageTimer()
    this.messageAlert && this.messageAlert.close()
  }
}
</script>

<style lang="scss">
@import '~@/styles/global';
.message-notify-new {
  $height: 40px;
  font-size: 14px;
  padding: 5px 10px !important;
  user-select: none;
  background-color: $color-primary !important;
  color: $content-text-color;
  border-color: $color-primary !important;
  height: $height;
  top: calc(#{$main-header-height} - #{$height} / 2) !important;
  .animated {
    // 动画延迟
    animation-duration: 2s;
  }
}
.message-notify-status {
  height: 40px !important;
  top: 3px !important;
}
.top-message-container {
  .icon {
    position: relative;
    cursor: pointer;
    .top-message-count {
      color: red;
      font-weight: 700;
    }
    &.count {
      padding: 0 3px;
      i {
        animation: top-message-border 1s infinite steps(1, start);
      }
    }
  }
  .top-message-body {
    position: absolute;
    right: 50px;
    top: -330px;
    width: 285px;
    height: 320px;
    z-index: 1000;
    transition: top .5s ease;
    background: $content-background-color;
    border: 1px solid $content-border-color;
    border-radius: 4px;
    box-shadow: -3px 4px 8px 0 rgba(16,18,27,.6), 0 2px 4px 0 rgba(16,18,27,.5);
    &.visible {
      top: 2px;
    }
    .title {
      height: 20px;
      line-height: 1;
      padding: 5px;
      border-bottom: 1px solid $content-border-color;
      box-shadow: -3px 4px 8px 0 rgba(16,18,27,.6), 0 2px 4px 0 rgba(16,18,27,.5);
      .label {
        text-align: left;
        font-weight: 700;
      }
      .options {
        float: right;
        i {
          cursor: pointer;
          &:active {
            color: $color-primary;
          }
        }
        .el-checkbox {
          + .el-checkbox {
            margin-left: 2px;
          }
          .el-checkbox__label {
            padding-left: 2px;
          }
        }
      }
    }
    .table {
      height: 270px;
      font-size: 16px;
      border-bottom: 1px solid $content-border-color;;
      overflow-y: auto;
      .table-item {
        padding: 5px 3px;
        font-size: 14px;
        border-bottom: 1px solid $content-border-color;;
        &:hover {
          background: $color-hover;
          color: $color-hover-text;
          cursor: pointer;
        }
        &:active {
          background: $color-active;
        }
        &:before {
          content: ' ';
          margin-right: 2px;
          border: 3px solid $color-primary;
        }
        &.require {
          &::before {
            content: ' ';
            border: 3px solid red;
          }
        }
        &.checked {
          &::before {
            content: ' ';
            border: 3px solid #ffaa00;
          }
        }
        .content {
          display: inline-block;
          overflow: hidden;
          text-overflow: ellipsis;
          width: 205px;
        }
        .time {
          text-align: right;
          display: inline-block;
          font-size: 12px;
          width: 55px;
        }
      }
      .loading {
        overflow: hidden;
        text-align: center;
        vertical-align: middle;
        height: 30px;
        line-height: 30px;
        transition: height .2s ease;
        span {
          font-size: 12px;
          cursor: pointer;
        }
        i {
          font-size: 18px;
        }
        .no-data {
          cursor: inherit;
        }
      }
    }
    .message-footer {
      height: 20px;
      line-height: 1;
      text-align: center;
      cursor: pointer;
      &:hover {
        background: #212b34;
        i {
          transition: transform .2s;
          color: $color-primary;
          transform: scale(1.3, 1.3) translateY(-5px);
        }
      }
    }
  }
  .message-time-span {
    position: absolute;
    font-size: 12px;
    left: 15px;
    &.create-time {
      bottom: 24px;
    }
    &.check-time {
      bottom: 10px;
      background: #fcf45a
    }
  }
}
.message-poper-options {
  min-width: 50px !important;
  padding: 0 !important;
  &[x-placement^="bottom"] {
    margin-top: 9px;
  }
  .el-checkbox-group {
    padding: 12px;
  }
  .divided {
    margin-top: 2px;
    border-top: 1px solid $content-border-color;;
    &::before {
      height: 2px;
      background: #fff;
    }
  }
  .button {
    text-align: right;
    padding: 6px 12px;
    button {
      background: transparent;
      border: none;
      color: #c0c4cc;
      cursor: pointer;
      font-size: 13px;
      padding: 0 3px;
      &:hover {
        color: $color-primary;
      }
      &.is-disabled {
        color: #606266;
        cursor: not-allowed;
      }
    }
  }
}
@keyframes top-message-border {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
}
</style>
