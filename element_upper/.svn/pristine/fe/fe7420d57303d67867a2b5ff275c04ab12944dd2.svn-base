/*
 * @Author: chenyang
 * @Date: 2017-11-24 17:07:15
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-30 17:35:21
 */

 <template>
 <section>
  <el-col :span="3" class="left">
      </br>
      <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
      </el-input>
      </br>
      </br>
      <el-tree
        class="filter-tree treeHeight"
        :data="dataSourceData"
        :props="defaultProps"
        default-expand-all
        v-loading='listLoading'
        @node-click='nodeClick'
        :filter-node-method="filterNode"
        ref="datasourceTree">
      </el-tree>
    </el-col>
    </br>
    <el-col :span="20" class="tableLeft">
      <div style="font-size:2em;font-family:Tahoma;"><span>SQL语句预览</span></div>
      </br>
      <div id="myCanvas" style="border:1px solid #c3c3c3;width:100%;height:850px;overflow:auto;position:ralatve;">
        <div style="text-align:center;font-size:1.5em;"><span style="background-color:#f5eccd;font-family:Tahoma;">{{typeShow}}</span></div>
        <div id="afterSql" style="font-size:1.0em;" class="sansserif1"></div>
        <el-button type="primary" style="position:absolute;right:50px;bottom:30px;" @click="sqlSubmit()">提交</el-button>
        <div id="overlay"></div>
        <div id="div_load" style="border:1px solid #ffffff;display:none;font-size:2em;color:red"></div>
      </div>
    </el-col>
  </section>
 </template>

 <script>
 import Util from '../../common/js/util'
 import API from '../../api'
 import Pagination from '../../components/Pagination'
 import commonAddOrUpdate from './../../components/CommAddOrUpdate'
 import commonDelete from './../../components/CommDelete'
 import {axios, base} from '../../../src/api/raiis-axios'

 export default {
   components: {
    commonDelete: commonDelete,
    pagination: Pagination,
    commonAddOrUpdate: commonAddOrUpdate
   },
   watch: {
      filterText (val) {
        this.$refs.datasourceTree.filter(val)
      }
   },
   data () {
     return {
      //  showList: [],
       typeShow: '',
       selectValueList: [],
       inputValueList: [],
       stringDataList: [],
       funcList: [],
       dynamicSelectList: [], // 动态的Select控件
       dynamicInputList: [], // 动态的输入控件
       dynamicCheckBoxList: [], // 动态CheckBox
       log: console.log.bind(console),
       color: ['color1', 'color2', 'color3', 'color4', 'color5', 'color6', 'color7', 'color8', 'color9', 'color10'],
       nodeColor: {}, // 记录每个节点的颜色,格式为 {12, 'css的颜色'}
       API: API,
       colorMap: {},
       tableHeight: 900,
       sourceData: [], // 每个Id对应的元素列表
       desAndIdMap: {},
       dataSourceList: [],
       listLoading: false,
       flightTaskFirstIndex: 0,
       filterText: '',
       sourceId: '',
       checkList: [],
       dataSourceData: [],
       defaultProps: {
          children: 'children',
          label: 'label'
        },
        fields: [
          { name: 'des', value: '', label: '描述', type: 'text', rules: null, placeholder: '请输入描述' },
          { name: 'field', value: '', label: '字段', type: 'textarea', placeholder: '请输入字段' },
          { name: 'value', value: '', label: '字段值', type: 'text', placeholder: '请输入字段值' },
          { name: 'id', value: '', label: '节点编号', type: 'text', placeholder: '请输入节点编号' },
          { name: 'parentid', value: '', label: '父节点编号', type: 'text', placeholder: '请输入父节点编号' },
          { name: 'replaceflag', value: '', label: '是否需要替换', type: 'text', placeholder: '是否替换' },
          { name: 'deepth', value: '', label: '层数', type: 'text', placeholder: '请输入字段值' }
        ]
      }
   },
   methods: {
    ShowLoad (text) {
      var w = document.documentElement ? (document.documentElement.scrollWidth || 0) : (document.body.scrollWidth || 0)
      // var h = document.documentElement ? (document.documentElement.scrollHeight || 0) : (document.body.scrollHeight || 0)
      var o = document.getElementById('overlay')
      o.style.width = (document.getElementById('myCanvas').offsetWidth - 10) + 'px'
      o.style.height = document.getElementById('myCanvas').offsetHeight + 'px'
      o.style.background = '#ffffff'
      o.style.left = 0 + document.getElementsByClassName('el-tree filter-tree treeHeight').offsetWidth
      o.style.top = 0 + 30
      o.style.filter = 'Alpha(Opacity=45)'
      o.style.MozOpacity = '0.45'
      o.style.opacity = '1.0'
      o.style.display = 'block'
      o.style.position = 'absolute'
      o.style.zIndex = 101
      var load = document.getElementById('div_load')
      var h1 = document.documentElement ? (document.documentElement.scrollTop || 0) : (document.body.scrollTop || 0)
      h1 = Math.max(h1, (window.scrollY || 0))
      var h2 = document.documentElement ? (document.documentElement.clientHeight || 0) : (document.body.clientHeight || 0)
      load.innerHTML = text
      load.style.zIndex = 102
      load.style.position = 'absolute'
      load.style.display = 'block'
      var h3 = load.clientHeight
      var w2 = load.clientWidth
      load.style.left = parseInt((w - w2) / 2) + 'px'
      load.style.top = parseInt((h2 / 2) + h1 - h3) + 'px'
    },
    getSelectValue () {
      this.selectValueList = []
      // this.dynamicInputList
      // this.dynamicSelectList
      for (let i = 0; i < this.dynamicSelectList.length; i++) {
        let mySelect = document.getElementById(`select_${i}`)
        console.log(i, mySelect)
        let index = mySelect.selectedIndex
        // console.log(index)
        let value = mySelect.options[index].value
        let str = ''
        for (let i = 0; i < value.length; i++) {  // 刨掉中文
          if (value[i] === '(') {
            break
          }
          str += value[i]
        }
        // console.log(value)
        this.selectValueList.push({'itemNum': i, 'index': index, 'value': str})
      }
      console.log(this.selectValueList)
    },
    getInputValue () {
      this.inputValueList = []
      for (let i = 0; i < this.dynamicInputList.length; i++) {
        let myInput = document.getElementById(`input_${i}`)
        let value = myInput.value
        this.inputValueList.push({'itemNum': i, 'value': value})
      }
      console.log(this.inputValueList)
    },
    searchFlightTaskLogo () {  // 寻找flightTask的标识
      let index = 0
      for (let i = 0; i < this.sourceData.length; i++) {
        if (this.sourceData[i] === '$' && this.sourceData[i + 1] === 'F' && this.sourceData[i + 2] === 'L' && this.sourceData[i + 3] === 'I' && this.sourceData[i + 4] === 'G' && this.sourceData[i + 5] === 'H' && this.sourceData[i + 6] === 'T' && this.sourceData[i + 7] === 'T') {
          index = i
          break
        }
      }
      let temp = 0
      for (let j = index; j > 0; j--) {
        if (this.sourceData[j] === '(') {
          temp = j
          break
        }
      }
      let finalStr = ''
      for (let z = temp + 1; z < this.sourceData.length; z++) {
        if (this.sourceData[z] !== 'k') {
          finalStr += this.sourceData[z]
        } else {
          finalStr += this.sourceData[z]
          break
        }
      }
      return finalStr // 返回例如c.flighttask
    },
    getFlightTaskString () {
      let checkBoxStr = ''
      let prefix = this.searchFlightTaskLogo()
      for (let i = 0; i < this.checkList.length; i++) {
        if (this.checkList[i] === 1) { // 选中,添加到字符串中
          let str = ''
          let temp = document.getElementById(`span_${i}`).innerHTML
          for (let j = 0; j < temp.length; j++) {
            if (temp[j] === '(') {
              break
            } else {
              str += temp[j]
            }
          }
          checkBoxStr += prefix + ' ' + '=' + ' ' + `'${str}'` + ' ' + 'OR' + ' '
        }
      }
      let str = ''
      for (let i = 0; i < checkBoxStr.length - 4; i++) {
        str += checkBoxStr[i]
      }
      // console.log(str)
      return str
    },
    finalSql () {   // 修改后最终拼成的sql
      // let str = ''
      let flightTaskCnt = 0
      let startatindex = 0 // flighttask第一个@所在
      let flag = true
      this.fetchCheckBoxCheck()
      let flightTaskString = this.getFlightTaskString()
      console.log(flightTaskString)  // 获取选中的checkbox拼接而成的字符串，最终再进行拼接返给后端,记得判空
      let selectCount = 0
      let inputCount = 0
      let finalStr = ''
      for (let i = 0; i < this.sourceData.length; i++) {
        if (this.sourceData[i] === '\n') {
          finalStr += '\n'
        } else if (this.sourceData[i] === ' ') {
          finalStr += ' '
        } else {
          let str = ''
          if (this.sourceData[i] === '$') {
            // selectCnt++
            while (this.sourceData[i] !== '@') {
              str += this.sourceData[i]  // 拼接$和@之间的字符串
              i++
              if (i >= this.sourceData.length) {
                break
              }
            }
            if (this.sourceData[i] === '@') {  // 在这里替换的时候顺便请求api
              str += '@'
              i++
              let afterKeyUrl = this.getTypeString(str)  // key后面的url
              if (afterKeyUrl === '/flights/getdistinct?key=flighttask') {
                flightTaskCnt++
                finalStr += '&'
                if (flag === true) {
                  startatindex = i
                  flag = false
                }
                continue
              }
              if (afterKeyUrl !== 'Time') {
                if (selectCount < this.dynamicSelectList.length) {
                  finalStr += '\'' + this.selectValueList[selectCount++].value + '\''
                }
              } else {  // 是Time就用输入的
                finalStr += this.inputValueList[inputCount++].value
              }
            }
          }
          finalStr += this.sourceData[i]  // 把finalStr拆成三部分,前面部分 + flightTaskString + 后面部分即可
        }
      }
      let startIndex = 0
      for (let i = startatindex; i > 0; i--) {
        if (finalStr[i] === '(') {
          startIndex = i
          console.log(startIndex)
          break
        }
      }
      let endIndex = 0
      for (let z = startIndex; z < finalStr.length; z++) {
        if (finalStr[z] === '&') {
          endIndex = z
        }
      }
      console.log(endIndex)
      let returnStr = ''
      for (let i = 0; i < startIndex; i++) {
        returnStr += finalStr[i]
      }
      returnStr += '('
      returnStr += flightTaskString
      returnStr += ')'
      for (let i = endIndex + 1; i < finalStr.length; i++) {
        returnStr += finalStr[i]
      }
      if (flightTaskCnt === 0) {  // 没有flightTask
        console.log(finalStr)
      } else {
        console.log(returnStr)
      }
    },
    sqlSubmit () {
      this.getSelectValue()   // 获取select的值
      this.getInputValue()  // 获取input的值
      this.finalSql() // 把最后的结果拼接出来
    },
    compare (obj1, obj2) {
      var var1 = obj1.id
      var var2 = obj2.id
      if (parseInt(var1) < parseInt(var2)) {
        return -1
      } else if (parseInt(var1) > parseInt(var2)) {
        return 1
      } else {
        return 0
      }
    },
    isAllNull (obj) {
      if (obj.des === null && obj.field === null && obj.value === null) {  // 都为空
        return true
      }
      return false
    },
    getSelectList (url) {
      console.log(url)
      return new Promise(function (resolve, reject) {
        let data = []
        let getUrl = base + url
        axios.get(getUrl).then(function (response) {
          data = response.data.attr.data
          if (data.length > 0) {
            resolve(data)
          } else {
            reject(new Error('没有数据!'))
          }
        })
        })
      },
      fetchCheckBoxCheck () {
        this.checkList = [] // 存放各个位置有没有被勾选，1勾选，0没被勾选
        for (let i = 0; i < this.dynamicCheckBoxList.length; i++) {
          let obj = document.getElementById(`checkbox_${i}`)
          let isCheck = Number(obj.checked)
          this.checkList.push(isCheck)
          console.log(isCheck)
        }
      },
      showHtml () {
        this.getSql()
        this.funcList = []
        for (let i = 0; i < this.stringDataList.length; i++) {
          this.funcList.push(this.getSelectList(this.stringDataList[i]))
        }
        Promise.all(this.funcList).then(value => {
          let selectCnt = 0
          let checkboxCount = 0
          // console.log('value', value)
          for (let i = 0; i < value.length; i++) {
            if (value[i][0] === 'S/F(试飞飞行)') {  // 判断是否有这个值
              for (let c = 0; c < value[i].length; c++) {
                let alphaValue = ''
                for (let g = 0; g < value[i][c].length; g++) {
                  if (value[i][c][g] !== '(') {
                    alphaValue += value[i][c][g]
                  } else {
                    break
                  }
                }
                if (c === value[i].length - 1) {
                  this.dynamicCheckBoxList.push(`<input id="checkbox_${checkboxCount}" value = “${alphaValue}” type="checkbox" name="checkbox"><span id="span_${checkboxCount++}" style="color:#caa222;font-weight:bold;font-style:oblique">${value[i][c]}</span>)`)
                } else {
                  this.dynamicCheckBoxList.push(`<input id="checkbox_${checkboxCount}" value = “${alphaValue}” type="checkbox" name="checkbox"><span id="span_${checkboxCount++}" style="color:#caa222;font-weight:bold;font-style:oblique">${value[i][c]}</span>&nbsp;&nbsp;OR&nbsp;&nbsp;`)
                }
              }
              break
            }
          }
          for (let i = 0; i < value.length; i++) {
            // console.log(value)
            let options = ''
            if (value[i][0] === 'S/F(试飞飞行)') {
              continue
            }
            for (let j = 0; j < value[i].length; j++) {
              options += `<option>${value[i][j]}</option>`
            }
            this.dynamicSelectList.push(`<select id="select_${selectCnt++}">${options}</select>`)
          }
          this.traverseForBuildInnerHtml()
        })
      },
    getTypeString (str) {  // 获取Url [key] 后面的字符串,传入参数为$xxxxx@
      // console.log(str)
      let resStr = ''
      let equalindex = 0
      for (let i = str.length - 3; i > 0; i--) {
        if (str[i] === '=') {
          equalindex = i
          break
        }
        resStr += str[i]
      }
      resStr = resStr.split('').reverse().join('')
      let temp = ''
      for (let j = equalindex; j > 0; j--) {
        if (str[j] === '(') {
          break
        } else {
          temp += str[j]
        }
      }
      temp = temp.split('').reverse().join('')
      if (resStr === 'Time(时间') {
        return 'Time'
      } else {
        return temp + resStr
      }
    },
    getSql () {  // 还要有个函数重新遍历，因为这个函数是异步获取的
      this.stringDataList = []
      // let selectCnt = 0
      let inputCnt = 0
      console.log(this.sourceData)
      for (let i = 0; i < this.sourceData.length; i++) {
        if (this.sourceData[i] === '\n') {
          // document.getElementById('afterSql').innerHTML += '</br>'
        } else if (this.sourceData[i] === ' ') {
          // document.getElementById('afterSql').innerHTML += '&nbsp;&nbsp;'
        } else {
          let str = ''
          if (this.sourceData[i] === '$') {
            // selectCnt++
            while (this.sourceData[i] !== '@') {
              str += this.sourceData[i]  // 拼接$和@之间的字符串
              i++
              if (i >= this.sourceData.length) {
                break
              }
            }
            if (this.sourceData[i] === '@') {  // 在这里替换的时候顺便请求api
              str += '@'
              i++
              let afterKeyUrl = this.getTypeString(str)  // key后面的url
              if (afterKeyUrl !== 'Time') {
                // var __this = this
                this.stringDataList.push(afterKeyUrl)
                // this.getSelectList(`/flights/getdistinct?key=${afterKeyUrl}`).then(function (data) {  // 测试,key前面不变后面变了
                //   // console.log(data)
                //   let options = ''
                //   for (let i = 0; i < data.length; i++) {
                //     options += `<option>${data[i]}</option>`
                //   }
                //   __this.dynamicSelectList.push(`<select id="select_${selectCnt++}">${options}</select>`)
                //   // console.log(__this.dynamicSelectList)
                //   // document.getElementById('afterSql').innerHTML += `<select id="${selectCnt}">${options}</select>`
                // })
              } else {  // 是Time就用输入的
                this.dynamicInputList.push(`<input id="input_${inputCnt}" type="text" required name="input_${inputCnt++}" />`)
                // document.getElementById('afterSql').innerHTML += `<select id="${selectCnt}"><option>巴啦啦小魔仙</option><option>哦啊哦哦啊</option></select> `
              }
            }
          }
          // document.getElementById('afterSql').innerHTML += this.sourceData[i]
        }
      }
      // this.traverseForBuildInnerHtml()
    },
    traverseForBuildInnerHtml () {  // 把异步获取的数据拼接InnerHtml
      let selectCount = 0
      let inputCount = 0
      console.log(this.dynamicSelectList.length)
      // console.log(this.dynamicInputList)
      for (let i = 0; i < this.sourceData.length; i++) {
        if (this.sourceData[i] === '\n') {
          document.getElementById('afterSql').innerHTML += '</br>'
        } else if (this.sourceData[i] === ' ') {
          document.getElementById('afterSql').innerHTML += '&nbsp;&nbsp;'
        } else {
          let str = ''
          if (this.sourceData[i] === '$') {
            // selectCnt++
            while (this.sourceData[i] !== '@') {
              str += this.sourceData[i]  // 拼接$和@之间的字符串
              i++
              if (i >= this.sourceData.length) {
                break
              }
            }
            if (this.sourceData[i] === '@') {  // 在这里替换的时候顺便请求api
              str += '@'
              i++
              let afterKeyUrl = this.getTypeString(str)  // key后面的url
              if (afterKeyUrl !== 'Time') {
                if (afterKeyUrl === '/flights/getdistinct?key=flighttask') {  // flightTask特殊用复选框
                  let str = ''
                  for (let z = 0; z < this.dynamicCheckBoxList.length; z++) {
                    str += this.dynamicCheckBoxList[z]
                  }
                  document.getElementById('afterSql').innerHTML += str
                  // console.log(this.dynamicSelectList[selectCount])
                  for (let index = i; ;index++) {
                    if (this.sourceData[index] === '@' && this.sourceData[index + 1] === ')') {  // 到flightTask结尾了
                      while (this.sourceData[index] !== '\n') {
                        index++
                      }
                      i = index - 1
                      break
                    }
                  }
                  continue
                }
                // console.log('aaa:   ' + this.dynamicSelectList[selectCount])
                document.getElementById('afterSql').innerHTML += this.dynamicSelectList[selectCount++]
              } else {  // 是Time就用输入的
                // console.log('Time长度为:' + this.dynamicInputList.length)
                 document.getElementById('afterSql').innerHTML += this.dynamicInputList[inputCount++]
              }
            }
          }
          if (typeof this.sourceData[i] === 'undefined') {
            continue
          } else {
            document.getElementById('afterSql').innerHTML += this.sourceData[i]
          }
        }
      }
      this.hidden()
    },
    hidden () {
      document.getElementById('overlay').style.display = 'none'
      document.getElementById('div_load').style.display = 'none'
    },
    isBothNull (obj) {
      if (obj.des === null && obj.field === null && obj.value === null) {
        return true
      }
      return false
    },
     handleEdit: function (index, row) {
      // console.log(index, row)
      let editFields = Util.deepCopy(this.fields)
      for (let i = 0; i < editFields.length; i++) {
        editFields[i].value = row[editFields[i].name]
      }
      this.$refs['editForm'].show(editFields)
    },
    nodeClick (data) {
       this.ShowLoad('内容正在加载中,请稍候……')
       this.dynamicSelectList = []
       this.dynamicInputList = []
       this.dynamicCheckBoxList = []
       document.getElementById('afterSql').innerHTML = ''
       this.colorMap = new Map()
       this.setTreeData = []
       this.typeShow = data.label
       let idValue = this.desAndIdMap.get(data.label)
       let para = Object.assign({sourceid: idValue}, {pageSize: 100000})
       API.getSourceData().go(para).then((data) => {
          if (data.ok) {
            this.sourceData = data.attr.data
            // console.log(this.sourceData)
            this.sourceId = this.sourceData[0].sourceid
            // this.getSql()
            // var __this = this
            this.showHtml()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
     },
     filterNode (value, data) {
       if (!value) return true
       return data.label.indexOf(value) !== -1
     },
     setTreeData () {
        let para = Object.assign({})
        this.listLoading = true
        API.getSource().go(para).then((data) => {
          if (data.ok) {
            this.dataSourceList = data.attr.data.list
            for (let i = 0; i < this.dataSourceList.length; i++) {
              this.dataSourceData.push({id: i, label: this.dataSourceList[i].des, children: []})
              this.desAndIdMap.set(this.dataSourceList[i].des, this.dataSourceList[i].id)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
     },
     init () {
       this.listLoading = false
       this.dataSourceList = []
       this.dataSourceData = []
       this.desAndIdMap = new Map()
     }
   },
   mounted () {
     this.init()
     this.setTreeData()
   }
 }
 </script>

 <style>
  .left {
    margin-left: 5px;
  }
  .tableLeft {
    margin-left: 20px;
  }
  .right {
    margin-right: 5px;
  }
  .treeHeight {
    height: 850px;
    overflow: auto;
  }
  .fontCenter {
    text-align: center;
  }
  .sansserif1
  {
    width:auto;
    float:left; font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
    font-size:7px;
    color:#501b63;
    line-height:25px;
    letter-spacing:1px
  }
  select {
   width: 132px;
   height: 21px;
   border: solid 2px green;
   /*为下拉小箭头留出一点位置，避免被文字覆盖*/
   padding-right:34px;
   padding-left: 10px;
   /*将默认的select选择框样式清除*/
   appearance:none;
   -moz-appearance:none;
   -webkit-appearance:none;
   background: url("../../../static/pulldown.png") no-repeat scroll right center transparent;
  }
  /*清除ie的默认选择框样式清除，隐藏下拉箭头*/
  select::-ms-expand { display: none; }
  input{
    /* margin: 10px 0; */
  }
  /*无效输入时的样式*/
  input:required:invalid{
      background-image: url("../../../static/error.png");
    background-position: right center;
    background-repeat: no-repeat;
    border-color: red;
    box-shadow: none;
      /* 兼容FF13以前版本 */
    -moz-box-shadow: none;
  }
  /*有效输入时的样式*/
  input:required:valid{
    background-image: url("../../../static/true.png");
    background-position: right center;
    background-repeat: no-repeat;
    border-color: green;
    box-shadow: none;
      /* 兼容FF13以前版本 */
    -moz-box-shadow: none;
  }
 </style>
