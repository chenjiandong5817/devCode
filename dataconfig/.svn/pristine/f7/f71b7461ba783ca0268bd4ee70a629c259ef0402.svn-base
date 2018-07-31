
<script>
import Tools from './../../common/js/template-tools'
// 在这里引入datasource
import DataSource from './datasource'
import GridEditor from './editors/GridEditor'
export default {
  data () {
    return {
      currentEdit: {},
      specialPartContainer: null,
      childColumn: ['rows', 'cols', 'children', 'columns'],
      columnType: {
        select: ['dataSource', 'rowStripe'],
        radio: [],
        switch: ['singleData', 'mainPager', 'hiddenHeader'],
        textArea: ['bodyStyle']
      }
    }
  },
  components: {
    GridEditor
  },
  computed: {
    haveDataSource () {
      let result = false
      for (let key in this.currentEdit) {
        if (key === 'dataSource') {
          result = true
          break
        }
      }
      return result
    },
    mockDataSource () {
      let dataSource = Tools.deepCopy(DataSource)
      for (let i = 0; i < dataSource.length; i++) {
        if (dataSource[i].id === '17EF84121B8F41FC9C5D9C5E049E0956') {
          let exampleData = dataSource[i].exampleData[0]
          for (let j = 0; j < 9; j++) {
            dataSource[i].exampleData.push(Tools.deepCopy(exampleData))
          }
        }
      }
      return dataSource
    }
  },
  methods: {
    checkColumnType (column) {
      let result = 'input'
      for (let type in this.columnType) {
        if (this.columnType[type].includes(column)) {
          result = type
          break
        }
      }
      return result
    },
    handleAddDataSource () {
      console.log(1)
      this.$set(this.currentEdit, 'dataSource', '')
      this.$set(this.currentEdit, 'singleData', true)
      this.$set(this.currentEdit, 'mainPager', false)
      console.log(this.currentEdit)
    },
    handleDelDataSource () {
      this.$delete(this.currentEdit, 'dataSource')
      this.$delete(this.currentEdit, 'singleData')
      this.$delete(this.currentEdit, 'mainPager')
      console.log(this.currentEdit)
    },
    handleEdit (event) {
      console.log('正在修改模板')
      this.$emit('editTemplate', Tools.deepCopy(this.currentEdit))
    },
    handleDelete (event) {
      this.$emit('deleteTemplate', this.currentEdit)
      this.currentEdit = {}
    },
    changeCurrentEdit (currentEdit) {
      console.log(currentEdit)
      this.currentEdit = Tools.deepCopy(currentEdit)
    },
    specialPart (h) {
      if (!this.currentEdit || !this.currentEdit.type) return
      if (this.currentEdit.type === 'Grid') {
        let options = {
          multipleLimit: 0,
          valueKey: 'type',
          labelKey: 'description',
          placeholder: '请选择一种模板',
          data: [
            { type: 'type1', description: '模板1' },
            { type: 'type2', description: '模板2' },
            { type: 'type3', description: '模板3' }
          ]
        }
        return h(
          'div',
          [
            this.currentEdit['columns'].map(item => {
              return h(
                'el-select',
                {
                  props: {
                    value: this.specialPartContainer,
                    filterable: true,
                    placeholder: options.placeholder,
                    multiple: Boolean(options.multipleLimit),
                    multipleLimit: options.multipleLimit
                  },
                  on: {
                    input: (value) => {
                      console.log(value)
                      this.specialPartContainer = value
                    }
                  }
                },
                options.data.map(dataItem => {
                  return h(
                    'el-option',
                    {
                      props: {
                        label: dataItem[options.labelKey],
                        value: dataItem[options.valueKey]
                      }
                    }
                  )
                })
              )
            })
          ]
        )
      }
    },
    traverseConf (h, conf, depth = 0) {
      return Object.keys(conf || {}).map(key => {
        if (this.childColumn.includes(key)) { return }
        let item = conf[key]
        return h(
          'el-form-item',
          {
            props: {
              label: key,
              prop: key
            },
            style: {
              marginLeft: `${depth * 20}px`
            }
          },
          [
            () => {
              if (this.checkColumnType(key) === 'select') {
                let options = {
                  multipleLimit: 0,
                  valueKey: '',
                  labelKey: '',
                  data: []
                }
                if (key === 'dataSource') {
                  options = {
                    multipleLimit: 0,
                    valueKey: 'id',
                    labelKey: 'description',
                    placeholder: '请选择数据源',
                    data: this.mockDataSource
                  }
                } else if (key === 'rowStripe') {
                  options = {
                    multipleLimit: 2,
                    valueKey: 'color',
                    labelKey: 'description',
                    placeholder: '请选择两个颜色',
                    data: [
                      { color: '#284682', description: '颜色1' },
                      { color: '#1E2D5A', description: '颜色2' },
                      { color: '#ff3d00', description: '颜色3' }
                    ]
                  }
                }
                return h(
                  'el-select',
                  {
                    props: {
                      value: conf[key],
                      filterable: true,
                      placeholder: options.placeholder,
                      multiple: Boolean(options.multipleLimit),
                      multipleLimit: options.multipleLimit
                    },
                    on: {
                      input: (value) => {
                        console.log(value)
                        conf[key] = value
                      }
                    }
                  },
                  options.data.map(item => {
                    return h(
                      'el-option',
                      {
                        props: {
                          label: item[options.labelKey],
                          value: item[options.valueKey]
                        }
                      }
                    )
                  })
                )
              } else if (this.checkColumnType(key) === 'radio') {
                return h(
                  'el-radio-group',
                  {
                    props: { value: conf[key] },
                    on: {
                      input: (value) => {
                        console.log(value)
                        conf[key] = value
                      }
                    }
                  },
                  [
                    h('el-radio', { props: {label: true} }, '是'),
                    h('el-radio', { props: {label: false} }, '否')
                  ]
                )
              } else if (this.checkColumnType(key) === 'switch') {
                return h(
                  'el-switch',
                  {
                    props: {
                      value: conf[key],
                      activeColor: '#13ce66',
                      inactiveColor: '#ff4949'
                    },
                    on: {
                      input (value) {
                        console.log(value)
                        conf[key] = value
                      }
                    }
                  }
                )
              } else if (this.checkColumnType(key) === 'textArea') {
                return h(
                  'el-input',
                  {
                    props: {
                      value: JSON.stringify(conf[key], null, 2),
                      type: 'textarea',
                      rows: 5
                    },
                    on: {
                      input: (value) => {
                        try {
                          conf[key] = JSON.parse(value)
                        } catch (e) {
                          console.log('非法的数据格式')
                        }
                      }
                    }
                  }
                )
              } else if (typeof item === 'object') {
                // console.log(item)
                return this.traverseConf(h, item, depth + 1)
              } else {
                return h(
                  'el-input',
                  {
                    props: {
                      value: conf[key]
                    },
                    on: {
                      input: (value) => {
                        conf[key] = isNaN(value) ? value : Number(value)
                      }
                    }
                  }
                )
              }
            }
          ]
        )
      })
    }
  },
  render (h) {
    console.log('render')
    if (this.currentEdit.type === 'Grid') {
      return h(
        'grid-editor',
        {
          props: {
            conf: this.currentEdit
          },
          ref: 'editor'
        }
      )
    }
    return h(
      'el-form',
      {
        props: {
          model: this.currentEdit,
          labelWidth: '80px',
          labelPosition: 'top'
        },
        ref: 'templateEditForm'
      },
      this.traverseConf(h, this.currentEdit).concat(this.specialPart(h)).concat([
        h(
          'el-form-item',
          [
            h('el-button', { props: {type: 'primary'}, on: {click: this.handleEdit}, style: {display: Tools.emptyObject(this.currentEdit) ? 'none' : ''} }, '修改'),
            h('el-button', { props: {type: 'danger'}, on: {click: this.handleDelete}, style: {display: Tools.emptyObject(this.currentEdit) || this.currentEdit.timestamp.startsWith('Layout') ? 'none' : ''} }, '删除'),
            h('el-button', { props: {type: `${this.haveDataSource ? 'danger' : 'default'}`, plain: true}, on: {click: this.haveDataSource ? this.handleDelDataSource : this.handleAddDataSource}, style: {display: Tools.emptyObject(this.currentEdit) ? 'none' : ''} }, `${this.haveDataSource ? '删除' : '添加'}数据源`)
          ]
        )
      ])
    )
  }
}
</script>

