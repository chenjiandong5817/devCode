/*
 * @Author: cdroid
 * @Date: 2017-12-27 15:28:51
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-12-29 15:49:01
 */

let deletePlaceholderNode = function (list) {
  for (let i = 0; i < list.length; i++) {
    let item = list[i]
    if (item.placeholder) {
      list.splice(i, 1)
      continue
    } else if (item.children) {
      deletePlaceholderNode(item.children)
    }
  }
}
export default {
  jsonSiblings: function (node, parentNode) {
    let list = parentNode.children || []
    let siblings = []
    for (let i = 0; i < list.length; i++) {
      if (list[i] === node) {
        continue
      }
      siblings.push(list[i])
    }
    return siblings
  },
  jsonIndex: function (node, list = []) {
    return list.findIndex(item => item === node)
  },
  dragInfo (node, parentNode) {
    // let jsonSiblings = this.jsonSiblings
    let jsonIndex = this.jsonIndex
    // let deletePlaceholderNode = this.deletePlaceholderNode
    return {
      source: node,
      index: jsonIndex(node, parentNode.children),
      siblings: parentNode.children,
      parent: parentNode,
      prev: function () {
        if (this.index > 0) {
          return this.siblings[this.index - 1]
        }
        return null
      },
      next: function () {
        if (this.index < this.siblings.length - 1) {
          return this.siblings[this.index + 1]
        }
        return null
      },
      moveTo: function (parent, siblings, index) {
        deletePlaceholderNode(this.parent.children)
        this.parent = parent
        this.siblings = siblings.slice(0)
        let i = this.siblings.indexOf(this.source)
        if (i > -1) {
          this.siblings.splice(i, 1)
          if (jsonIndex(this.source, this.parent) < index) {
            index--
          }
        }
        this.parent.children.splice(index, 0, Object.assign({}, this.source, {placeholder: true}))
        this.siblings = this.parent.children
        this.index = index
      },
      apply: function () {
        this.parent.children.splice(this.index, 1, this.source)
      }
    }
  },
  // 删除替换节点的placeholder属性
  removePlaceholderProperty (list = []) {
    for (let i = 0; i < list.length; i++) {
      let item = list[i]
      if (item.hidden) {
        delete item.hidden
        continue
      } else if (item.children) {
        this.removeHiddenProperty(item.children)
      }
    }
  },

  offset (element) {
    var boundingClientRect = element.getBoundingClientRect()
    return {
      width: element.offsetWidth,
      height: element.offsetHeight,
      top: boundingClientRect.top + (window.pageYOffset || document.body.scrollTop || document.documentElement.scrollTop),
      left: boundingClientRect.left + (window.pageXOffset || document.body.scrollLeft || document.documentElement.scrollLeft)
    }
  },
  positionStarted (e, target) {
    let pos = {}
    let pageX = e.pageX
    let pageY = e.pageY

    // Check to set correct data for TouchEvents
    if (e.originalEvent && e.originalEvent.touches && (e.originalEvent.touches.length > 0)) {
      pageX = e.originalEvent.touches[0].pageX
      pageY = e.originalEvent.touches[0].pageY
    }
    pos.offsetX = pageX - this.offset(target).left
    pos.offsetY = pageY - this.offset(target).top
    pos.startX = pos.lastX = pageX
    pos.startY = pos.lastY = pageY
    pos.nowX = pos.nowY = pos.distX = pos.distY = pos.dirAx = 0
    return pos
  },
  positionMoved (e, pos) {
    let pageX = e.pageX
    let pageY = e.pageY
    let newAx
    // If there are multiple touch points, choose one to use as X and Y.
    if (e.originalEvent && e.originalEvent.touches && (e.originalEvent.touches.length > 0)) {
      pageX = e.originalEvent.touches[0].pageX
      pageY = e.originalEvent.touches[0].pageY
    }
    let spacing = 10
    // Mouse position last event.
    if (Math.abs(pageX - pos.lastX) > spacing) {
      pos.lastX = pos.nowX
    }
    if (Math.abs(pageY - pos.lastY) > spacing) {
      pos.lastY = pos.nowY
    }
    // pos.lastX = pos.nowX
    // pos.lastY = pos.nowY
    // Mouse position this event.
    pos.nowX = pageX
    pos.nowY = pageY
    // Distance mouse moved between events.
    pos.distX = Math.abs(pos.nowX - pos.lastX) >= spacing ? pos.nowX - pos.lastX : 0
    pos.distY = Math.abs(pos.nowY - pos.lastY) >= spacing ? pos.nowY - pos.lastY : 0
    // Axis mouse is now moving on.
    newAx = Math.abs(pos.distX) > Math.abs(pos.distY) ? 1 : 0
    pos.dirAx = newAx
  },
  deepCopy (o) {
    if (o instanceof Function) {
      return o
    } else if (o instanceof Array) {
      var array = []
      for (var i = 0; i < o.length; ++i) {
        array[i] = this.deepCopy(o[i])
      }
      return array
    } else if (o instanceof Object) {
      var obj = {}
      for (var j in o) {
        obj[j] = this.deepCopy(o[j])
      }
      return obj
    } else {
      return o
    }
  }
}
