<script>
import DoubleLayerText from './DoubleLayerText'
// import debounce from 'throttle-debounce/debounce'
export default {
  props: {
    bodyStyle: {
      type: Object,
      default: () => {
        return {
          minWidth: `1920px`,
          minHeight: `1080px`,
          backgroundColor: `#212E5B`
        }
      }
    },
    rows: {},
    flightData: {}
  },
  components: {
    DoubleLayerText: DoubleLayerText
  },
  methods: {
    textRender (h, tag) {
      return h(
        'div',
        {
          style: {
            color: tag.transColor ? tag.transColor[this.flightData[tag.columnName]] : ''
          }
        },
        [
          this.flightData[tag.columnName]
        ]
      )
    },
    imageRender (h, tag) {
      return h(
        'img',
        {
          attrs: {
            src: this.flightData[tag.columnName]
          },
          style: tag.style
        }
      )
    },
    doubleLayerTextRender(h, tag) {
      return h(
        'double-layer-text',
        {
          attrs: {
            align: tag.content.align,
            translateX: tag.content.translateX,
            translateY: tag.content.translateY,
            topStyle: tag.content.topStyle,
            bottomStyle: tag.content.bottomStyle
          }
        },
        [
          h(
            'div',
            { slot: 'topSlot' },
            [
              tag.content.topContent && tag.content.topContent.length > 0
              ? this._l(tag.content.topContent, (content, index) => this.tagRender(h, content))
              : ''
            ]
          ),
          h(
            'div',
            { slot: 'bottomSlot' },
            [
              tag.content.bottomContent && tag.content.bottomContent.length > 0
              ? this._l(tag.content.bottomContent, (content, index) => this.tagRender(h, content))
              : ''
            ]
          )
        ]
      )
    },
    columnRender (h, tag) {
      return h(
        'el-col',
        {
          attrs: {
            span: tag.span
          }
        },
        [
          this._l(tag.children, (child, childIdx) => this.tagRender(h, child))
        ]
      )
    },
    rowRender (h, row) {
      return h(
        'el-row',
        {
          attrs: {
            type: 'flex',
            align: row.align,
            justify: row.justify
          }
        },
        [
          this._l(row.cols, (col, colIdx) => this.tagRender(h, col))
        ]
      )
    },
    tagRender (h, tag) {
      let result = []
      switch (tag.type) {
        case 'Row':
          result = this.rowRender(h, tag)
          break
        case 'Column':
          result = this.columnRender(h, tag)
          break
        case 'DoubleLayerText':
          result = this.doubleLayerTextRender(h, tag)
          break
        case 'Image':
          result = this.imageRender(h, tag)
          break
        default:
          result = this.textRender(h, tag)
      }
      return result
    },
    nonListRender (h) {
      return h(
        'div',
        {
          style: this.bodyStyle
        },
        [
          this._l(this.rows, (row, index) => this.tagRender(h, row))
        ]
      )
    }
  },
  render (h) {
    return this.nonListRender(h)
  },
  mounted () {}
}
</script>
<style>

</style>
