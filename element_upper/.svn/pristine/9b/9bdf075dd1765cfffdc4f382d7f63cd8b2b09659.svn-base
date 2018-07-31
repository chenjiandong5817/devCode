/*
 * @Author: cdroid
 * @Date: 2017-04-17 15:03:48
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-18 14:08:21
 * @Description: Tab页操作集中类
 */

<template>
    <div id="Tabs" >
        <!--<div style="margin-bottom: 20px;" >
        <el-button
            size="small"
            @click="addTab(editableTabsValue)"
        >
            add tab
        </el-button>
        </div>-->
        <el-tabs v-model="editableTabsValue" type="card" @tab-remove="removeTab" >
        <el-tab-pane
            v-for="(item, index) in editableTabs"
            :label="item.title"
            :name="item.name"
            :key="item.name"
            :closable="item.closable"
        >
          <template>
            <router-view :name="item.name" :style="{minHeight: viewHeight}" ref="mainView"></router-view>
          </template>
        </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
  // import debounce from 'throttle-debounce/debounce'
  import throttle from 'throttle-debounce/throttle'
  export default {
    name: 'Tabs',
    data () {
      return {
        initStyleComplete: false,
        editableTabsValue: '',
        editableTabs: [],
        viewHeight: 0
      }
    },
    created () {
      this.$on('addTab', function (targetName) {
        this.addTab(targetName)
      })
      // 默认添加home
      if (this.editableTabs.length === 0) {
        this.addTab('/home')
      }
    },
    mounted () {
      // window.onresize = () => {
      //   return ((tmpFun) => {
      //     this.initStyleComplete = false
      //     setTimeout(tmpFun, 20)
      //   })(this.initTabsStyle)
      // }
      let resize = throttle(100, () => {
        this.initStyleComplete = false
        setTimeout(this.initTabsStyle, 20)
      })
      window.onresize = resize
      // window.addEventListener('resize', debounce(200, this.initTabsStyle))
      this.initTabsStyle()
      // 绑定页面的onresize事件, 350是由于菜单搜索延迟350毫秒
      this.$on('tabresize', () => setTimeout(resize, 350))
    },
    updated () {
      if (this.initStyleComplete) {
        setTimeout(this.initTabsStyle, 20)
      }
    },
    methods: {
      recurTitle (key, array) {
        var title = ''
        for (var k = 0; k < array.length; k++) {
          if (key === array[k].path) {
            title = array[k].name
          } else if (array[k].children) {
            title = this.recurTitle(key, array[k].children)
          }
          if (title !== '') {
            break
          }
        }
        return title
      },
      addTab (targetName) {
        var routes = this.$router.options.routes
        var key = targetName
        targetName = targetName.substring(1)
        var elCount = 0
        for (var i = 0; i < this.editableTabs.length; i++) {
          if (this.editableTabs[i].name === targetName) {
            elCount++
          }
        }
        if (elCount === 0) {
          var title = this.recurTitle(key, routes)
          var closable = true
          if (targetName === 'home') {
            closable = false
          }
          this.editableTabs.push({
            title: title,
            name: targetName,
            closable: closable
          })
          // setTimeout(this.initTabsStyle, 20)
        }
        this.editableTabsValue = targetName
      },
      removeTab (targetName) {
        let tabs = this.editableTabs
        let activeName = this.editableTabsValue
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1]
              if (nextTab) {
                activeName = nextTab.name
              }
            }
          })
        }
        this.editableTabsValue = activeName
        this.editableTabs = tabs.filter(tab => tab.name !== targetName)
      },
      initTabsStyle () {
        var tabHeader = this.$el.children[0].children[0]
        var tabBody = this.$el.children[0].children[1]
        var elWidth = this.$el.offsetWidth + 'px'
        // 自动滚动条出现影响了width
        tabHeader.style.width = tabHeader.style.width !== elWidth ? elWidth : tabHeader.style.width
        tabBody.style.width = tabBody.style.width !== elWidth ? elWidth : tabBody.style.width
        // test 固定 tab页的位置
        // console.log(this.$el.children[0])
        if (!this.initStyleComplete) {
          const bodyTop = tabHeader.offsetHeight > 42 ? tabHeader.offsetHeight : 42
          tabHeader.style.position = 'fixed'
          tabHeader.style.paddingTop = '3px'
          tabHeader.style.backgroundColor = '#fff'
          tabHeader.style.zIndex = tabBody.style.zIndex + 99
          tabBody.style.top = `${bodyTop}px`
          this.initStyleComplete = true
          // routeView 默认高度
          // this.viewHeight = this.$parent.$el.parentNode.parentNode.offsetHeight - tabHeader.offsetHeight + 'px'
          this.viewHeight = `${this.$parent.$parent.$el.offsetHeight - bodyTop}px`
          // 解决resize table高度不变的bug
          var vueChildren = this.$children[0].$children
          if (vueChildren.length > 1) {
            for (var j = 1; j < vueChildren.length; j++) {
              this.initTableHeight(this.$children[0].$children[j].$children[0])
            }
          }
        } else {
          // 非 resize 的操作， children的index 因为要去掉 header 所以不需要-1
          this.initTableHeight(this.$children[0].$children[this.editableTabs.length].$children[0])
          // console.log(this.$children[0].$children[this.editableTabs.length].$children[0])
        }
        // toolbar-bottom 的div 需要重新设定width, 否则会默认显示器宽度
        if (tabBody.children.length > 0) {
          for (var i = 0; i < tabBody.children.length; i++) {
            var bars = tabBody.children[i].getElementsByClassName('toolbar-bottom')
            if (bars.length > 0) {
              tabBody.children[i].children[0].style.bottom = bars[0].offsetHeight + 'px'
              bars[0].style.width = elWidth
            }
          }
        }
      },
      initTableHeight (self) {
        setTimeout(() => {
          if (!self || !self.$el) {
            return
          }
          let pageHeight = self.$el.offsetHeight
          let children = self.$el.children
          if (children.length > 0) {
            for (var i = 0; i < children.length; i++) {
              // 标记，后面可能需要在减去其他div的高度
              if (children[i].className.indexOf('toolbar') > -1) {
                pageHeight -= children[i].clientHeight
              }
            }
          }
          if (pageHeight > 0) {
            self['tableHeight'] = pageHeight
          }
          // 间隔时间尽量长，否则获取界面高度不能满足要求
        }, 150)
      }
    }
  }
</script>
<style>
  #Tabs section {
    background: #fff;
  }
  .el-tabs__header, .el-tabs__content, .el-table__body-wrapper, .el-tab-pane {
    transition: all .2s;
    -moz-transition: all .2s;
    -webkit-transition: all .2s;
    -o-transition: all .2s;
  }

</style>
