
let conf = {
  Layout: {
    type: 'Layout',
    description: '请输入你的描述',
    bodyStyle: {
      width: 1920,
      height: 1080,
      backgroundColor: '#AEBAC3'
    },
    rows: []
  },
  Row: {
    type: 'Row',
    align: 'top',
    justify: 'start',
    bodyStyle: {
      width: 1400,
      height: 500,
      background: '#f6ff00'
    },
    cols: []
  },
  Column: {
    type: 'Column',
    span: 24,
    bodyStyle: {
      width: 1100,
      height: 450,
      background: '#51c400'
    },
    // dataSource: '',
    // singleData: true,
    // mainPager: false,
    children: []
  },
  Grid: {
    type: 'Grid',
    hiddenHeader: false,
    bodyStyle: {
      defaultRowStyle: {
        rowStripe: [ '#284682', '#1E2D5A' ],
        fontSize: 42,
        color: '#fff',
        height: 89,
        lineHeight: 89
      },
      defaultHeaderStyle: {
        fontSize: 50,
        color: '#FAC35A',
        backgroundColor: '#1E2D5A',
        textAlign: 'left',
        borderBottom: '1px solid',
        borderBottomColor: '#fff',
        lineHeight: 100,
        height: 100
      },
      style: {}
    },
    columns: []
  },
  GridColumn: {
    type: 'GridColumn',
    name: 'carrier',
    label: {
      'zh-cn': '中文',
      'en-ww': '英文'
    },
    style: {},
    headerStyle: {},
    children: []
  },
  DefaultMarqueeText: {
    type: 'DefaultMarqueeText',
    speed: 80,
    style: {
      width: 280
    },
    emptyReplace: [],
    transExist: '',
    value: '',
    property: 'key',
    textStyle: {},
    date: {
      formatFrom: 'yyyy-MM-dd HH:mm:ss',
      formatTo: 'HH:mm'
    },
    prefix: '',
    suffix: '',
    langLock: '',
    langExcept: [],
    special: [],
    transColor: {
      matchType: 'begin/like/end/equals',
      matchSet: {
        'YOU_KEY': 'COLOR_CODE'
      }
    },
    twinkle: {
      matchType: 'begin/like/end/equals',
      matchSet: {
        'YOU_KEY': true
      }
    },
    replace: [
      {
        key: 'pageNumber',
        columnName: 'pageNumber'
      },
      {
        key: 'pageCount',
        value: '1'
      }
    ],
    columnName: ''
  }
}

let template = (type) => {
  let obj = Object.assign({timestamp: type + '_' + new Date().getTime()}, conf[type])
  return obj
}
export default template
