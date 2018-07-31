import { cityList } from '../cityData' // 全国城市数据
import { openCityData } from '../openCityData' // 厦漳泉三城地址数据
export default {
  openId: '',
  // openId: 'oukrA1dbbITe9sJSdTNW6JrARHL4',
  // openId: 'oUlRvwBioZUBvfGb7qC04KozBpH0',
  // openId: 'oukrA1c_TyRKTBZdLjssa4K0jaFo',
  userMsg: {},
  /* ------以上是用户信息------ */
  currentCity: {
    city: '福州市',
    areaUuid: 'cb33bbcc0e8c400f992c4d1acff274cb'
    // areaUuid: 'f807671564b0409aa647b7b80af555b6'
  },
  originAreaUuid: '',
  Geolocation: '',
  sysAreaConfigDto: {
    /* areaUuid: '472d3bc9ec0f4f78800c4cd3617a6d72',
    bannerStatus: 1,
    bannerTitle: 'dsgsd',
    bannerUrl: '555',
    createTime: 1508749171000,
    flightStatus: 1,
    flightVipExplain: 'sssssssssssss',
    flightVipName: 'hfghfdh',
    flightVipStatus: 1,
    levelType1Status: 1,
    levelType2Status: 1,
    levelType3Status: 0,
    prepaidPercent: 20,
    prepaidStatus: 1,
    prepaidTime: 20,
    rentStatus: 1,
    updateTime: 1514338624000,
    uuid: '53a68df80471471e869a3aebab22a9df',
    walkStatus: 1 */
  },
  // 路线回参存放容器
  disAndDuraResult: {},
  estimateMsg: {},

  loginMask: false,
  // photoImgUrl: 'http://img.hb.aicdn.com/cfa557bc48ac534027926f43c82a79927d4fe7f132b6-WUPPn5_fw658',
  // photoImgUrl: '',
  // 更换乘车人相关信息
  passenger: {
    phone: '', // 乘车人电话
    payOrNot: false // 是否由乘车人支付
  },
  // 订单信息
  order: {
    orderType: '2',
    applyTime: Date.now(),
    waitTime: 0,
    departTime: Date.now(),
    departTimeTXT: '现在出发',
    totalFee: 0, // 订单预估价
    costItems: []
  },
  showUserBar: false, // 是否展开用户侧边栏
  cityList: cityList, // 全国城市静态数据集
  openCityData: openCityData, // 元翔已开通城市数据
  locationFrom: '', // 上车地点
  locationTo: '', // 下车地点
  topService: true, // 是否需要头等舱服务
  VIPnum: 1, // 头等舱服务人数
  // 地址搜索组件涉及变量
  location: {
    startOrEnd: true,
    locationFrom: '',
    locationTo: '',
    cityFocus: false,
    cityInputing: false,
    cityKeyWord: '',
    selectCity: '福州市',
    addressFocus: false,
    addressInputing: false,
    addressKeyWord: '',
    tips: []
  },
  // 静态站点坐标数据
  staticData: {
    loca_data: [/* {
      'name': 'SM CITY厦门',
      'location': {
        'M': 118.12721099999999,
        'O': 24.501314,
        'lat': 24.501314,
        'lng': 118.127211
      }
    },
    {
      'name': '香格里拉酒店',
      'location': {
        'M': 118.19554099999999,
        'O': 24.491226,
        'lat': 24.491226,
        'lng': 118.195541
      }
    },
    {
      'name': '东渡码头',
      'location': {
        'M': 118.07737700000001,
        'O': 24.490118,
        'lat': 24.490118,
        'lng': 118.077377
      }
    },
    {
      'name': '厦门大学思明校区',
      'location': {
        'M': 118.102555,
        'O': 24.436341,
        'lat': 24.436341,
        'lng': 118.102555
      }
    } */
    ]
  },
  // 航班信息
  airport: {
    num: '',
    time: ''
  },
  carType: {/*
    name: '时尚型',
    value: 1 */
  },
  // 车型列表
  carTypeList: [/* {
    'name': '时尚型',
    'class': 'type1',
    'value': 1
  }, {
    'name': '经济七座',
    'class': 'type2',
    'value': 2
  }, {
    'name': '豪华商务',
    'class': 'type3',
    'value': 3
  } */],
  // 支付类型列表
  payTypeList: [/* {
    'name': '个人支付',
    'type': 0,
    'money': 200
  }, {
    'name': '企业支付',
    'type': 1,
    'money': 100
  } */],
  // 支付类型
  payType: {/*
    name: '个人支付',
    type: 0,
    money: 200 */
  },
  // 当前使用的优惠券
  coupon: {
    id: '',
    value: 0,
    money: 0,
    discount: 0,
    type: 0,
    title: '',
    des: '',
    date: 0
  },
  // 优惠券列表
  couponList: [/* {
    'id': 'sfs43453',
    'title': '全场满减券',
    'des': '用车类型不限，单笔满10元可用',
    'value': 18,
    'type': 1,
    'date': 1515378877000
  },
  {
    'id': '4353gtgtg',
    'title': '接送机6折券',
    'des': '用车类型不限，单笔满10元可用',
    'value': 3,
    'type': 0,
    'date': 1515378877000
  },
  {
    'id': '454365hhhdf',
    'title': '接送机6折券',
    'des': '用车类型不限，单笔满10元可用',
    'value': 5,
    'type': 0,
    'date': 1515378877000
  },
  {
    'id': '52534gfeh',
    'title': '全场满减券',
    'des': '用车类型不限，单笔满10元可用',
    'value': 9,
    'type': 1,
    'date': 1515378877000
  } */
  ]
}
