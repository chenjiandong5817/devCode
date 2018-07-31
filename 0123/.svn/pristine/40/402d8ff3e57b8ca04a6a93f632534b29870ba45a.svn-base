<script>
import Reflect from '@/common/js/reflect'
import Config from '../../static/data/config'
import NonListCmpt from '@/components/NonListCmpt'
import FlexImageCmpt from './FlexImage/FlexImageView'
// import { getConfig as getConfigAPI } from './../api/index'
export default {
  data () {
    return {
      confLoaded: false,
      type: 1,
      nonListConfig: Config.nonListConfig,
      gridConfig: {},
      flexImageConfig: Config.flexImageConfig
    }
  },
  watch: {
    gridConfig () {
      if (this.confLoaded) {
        this.convertJs()
      }
    }
  },
  methods: {
    convertJs() {
      // 转换
      for (let i = 0; i < this.gridConfig.columns.length; i++) {
        let column = this.gridConfig.columns[i]
        if (column['formatterjs']) {
          column['formatterjs'] = Reflect[column['formatterjs']]
        }
      }
    },
    getConfig () {
      // 模拟制定页面获取到相应模板id
      // let id = '12345678'
      // getConfigAPI(id).then((data) => {
      //   // data.attr.data
      //   console.log(data)
      //   this.gridConfig = Config.gridConfig
      //   this.confLoaded = true
      // }).catch(() => {
      //   console.log('连接api失败')
      // })

      this.gridConfig = Config.gridConfig
      this.confLoaded = true
    }
  },
  created() {
    this.getConfig()
  },
  render (h) {
    if (!this.confLoaded) {
      return
    }
    if (this.type === 0) {
      return h(NonListCmpt, { props: this.nonListConfig })
    } else if (this.type === 1) {
      return h(FlexImageCmpt, { props: this.flexImageConfig })
    } else if (this.type === 9) {
      return ''
    }
    // return h(NonListCmpt)
  }
}
</script>
