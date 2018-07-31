# Crud组件更新日志

_2018-05-25_ 增加`table`的高度、最大高度和计算高度
```
  Version: 0.1.9

  Log:
    1、新增tableOption的height/maxheight/calcHeight参数
    2、修改加载完成后延迟100ms重置模板状态（dic获取失败的bug）
```

_2018-05-24_ `select`组件增加`meta.template`属性;修改table多表头的的嵌套slot的bug
```
  Version: 0.1.8

  Log:
    1、新增select组件可设置option列表的样式
    2、修改嵌套slot无法获取跨级slot的问题
```

_2018-05-18_ `column`的`dicData`属性的可选类型增加`RemoteData`
```
  Version: 0.1.7

  Log:
    1、增加远程获取数据字典
    2、修改进入加载得效果
```

_2018-05-17_ 增加`column`的`group`属性，用于分组表头
```
  Version: 0.1.7

  Log:
    1、增加column的group属性，用于分组表头
    2、修改如果tableOption为空，则不渲染页面
```

_2018-05-16_ 修改`formSlot`的参数, 增加`Crud-input`的`Convert`转换参数
```
  Version: 0.1.6

  Log:
    1、使用formSlot的插槽可以使用{form, column, dic} 三个属性， 之前的value 改成了form对象
    2、Crud-input 增加转换参数Convert
    3、修改字典匹配的时候，无视原数据类型，统一转成String进行比较
```

_2018-05-15_ 修改控件参数
```
  Version: 0.1.5

  Log:
    1、增加日期控件和级联选择器的clearable属性。
    2、增加表格的border属性
    3、修改控件新增默认值的设置
```

_2018-05-14_ 修改dialog内部Form表单字段默认值无法清空的bug
```
  Version: 0.1.4

  Log: 修改dialog内部Form表单字段默认值无法清空的bug
```

_2018-05-14_ 修改Crud组件的 `query`参数名称改为 `filters`; 修改`column`的`query`用于搜索条件的排序
```
  Version: 0.1.3

  Log:
    1、修改Crud组件的 `query`参数名称改为 `filters`, 与`column`的 `query`区分开来。
    2、修改 `column`的`query`数字用于确定工具栏搜索框的排列顺序
    3、移除`btn_query`的Slot
    4、修改dialog内部Form表单字段默认值无法清空的bug
```

_2018-05-11_ 完成Crud组件
```
  Version: 0.1.2
```
