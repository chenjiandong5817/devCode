# CRUD组件说明文档
> Version: 0.1.8
>
> Author: cdroid
>
> Date: 2018-05-18

## Attributes

### > _tableData_

&emsp;类型: `Array`

&emsp;描述: table表格绑定的对象

### > _tableOption_

&emsp;类型: `Object`

&emsp;描述: 组件渲染的必要参数，主要参数如下：
```
border: Boolean类型，表格边框标志位, 默认为false。

stripe: Boolean类型，表格斑马纹标识位, 默认为false。

toolbar: Boolean类型，是否自定义工具栏标识位。默认为false。
  当值为true时，需要添加`toolbar`的具名Slot, 具体见Slot说明。
  当值为false时，会根据column的字段生成查询条件，并暴露出`btn_query`和`btn_opt`的具名Slot，方便插入自定义的template。

selection: Boolean类型，是否显示表格复选框标志位。默认为false。

index: Boolean类型，是否显示序号标志位。默认为true。

page: Boolean类型，是否显示分页标志位。默认为false。

formWidth: String类型或者Number类型，form表单的宽度。默认为50%。

labelWidth: Number类型，表单页面的标签宽度。默认为80。

column: Array类型，表格字段配置参数。
  * prop: 名称，String类型
  * label: 显示标签，String类型
  * group: 列分组，用于生成分组表头，Array类型，子元素为column变量
  * headerAlign: 列对齐方式，String类型
    - 可选类型
    - left / center /right
  * align: 列对齐方式，String类型
    - 可选类型
    - left / center /right
  * width: 宽度，Number类型
  * minWidth: 最小宽度， Number类型
  * height: 高度，Number类型，设置为'auto'则自动计算
  * maxHeight: 最大高度，Number类型
  * calcHeight: 计算高度，Number类型，会与当前高度相减计算差值
  * labelWidth: 表单编辑时的标签宽度，Number类型
  * hide: 是否隐藏， Boolean类型
  * fixed: 是否固定列，Boolean类型
  * sortable: 是否可排序，Boolean类型
  * addVisible: 表单添加时是否可以显示, Boolean类型，默认true
  * editVisible: 表单编辑时是否可以显示, Boolean类型，默认true
  * addDisabled: 表单添加时是否禁用，Boolean类型
  * editDisabled: 表单编辑时是否禁用，Boolean类型
  * type: 字段的类型，String类型。在查询或者表单页面自动匹配组件类型，默认为text 文本输入。
    - 可选类型
    - select 选择框
    - radio 单选框
    - number 数字输入框
    - cascader 级联选择框
    - date、datetime 日期或时间选择器
    - daterange、datetimerange 日期段或时间段选择器
    - year、month、week、dates 其他日期类型选择器
    - textarea 文本域
    - text 文本框
  * query: 是否用于查询，Number类型，根据数字大小排列搜索框，如果的数字相同，则按照配置顺序排列。配置该选项的字段在`toolbar`为false时会自动生成查询条件组件
  * queryType: 查询条件的类型，String类型，可选参数同`type`，优先级比`type`高，可以让查询条件的字段类型与表单页面的字段类型区分开来。
  * span: 字段在表单页面所占的行比例，默认为12（行总比例24）。
  * dicData: 字典参数，类型为String / Array / RemoteData
    - 参数为String时，从本地字典里面获取Key值为所配参数的字典组
    - 参数为Array时，直接拿来当做字典组
    - 参数为RemoteData时，根据authKey 从后台获取数据字典
  * slot: 是否使用Slot，Boolean类型。使用与字段`prop`属性相同名字的具名Slot实现table列的自定义模板。
  * formSlot: Boolean类型，是否使用表单子项的自定义Slot。默认为false。
  * rules: 校验规则集合，同Element-ui校验规则
  * meta： 字段的其他参数，类型为Object。根据所选`type`增加一些子组件需要的其他参数。

```

### > _tableLoading_

&emsp;类型: `Boolean`

&emsp;描述: table的加载状态绑定。

### > _page_

&emsp;类型: `Object`

&emsp;描述: 分页参数的绑定，必须的参数如下：
```
pageNumber: 页码， Number类型。
pageSize: 每页大小，Number类型。
pageSizes: 可选的分页大小，Array类型。
total: 总数量， Number类型。
```

### > _filters_

类型: `Object`

描述: 查询参数的回调对象绑定，必须使用`.sync`修饰符进行双向绑定。ps: 如果没有使用自动生成的查询条件，这个功能基本可以弃用。


## Methods

### > _rowAdd_

&emsp;参数: `-`

&emsp;描述: 打开新增表单页面。

### > _rowEdit_

&emsp;参数: `row`

&emsp;描述: 打开编辑表单页面。

### > _rowDel_

&emsp;参数: `row`

&emsp;描述: 触发`@row-del`事件回调。


## Events

### @ _row-click_

&emsp;参数: `row, event, column`

&emsp;描述: 行点击事件回调。

### @ _row-dblclick_

&emsp;参数: `row, event`

&emsp;描述: 行双击事件回调。

### @ _row-contextmenu_

&emsp;参数: `row, event`

&emsp;描述: 行右键事件回调。

### @ _selection-change_

&emsp;参数: `selection`

&emsp;描述: 表格选中事件回调。

### @ _sort-change_

&emsp;参数: `object`， 包含 `{ column, prop, order }`

&emsp;描述: 表格排序事件回调。

### @ _current-change_

&emsp;参数: `pageNumber`

&emsp;描述: 分页页码改变事件回调。

### @ _size-change_

&emsp;参数: `pageSize`

&emsp;描述: 分页大小改变事件回调。

### @ _row-save_

&emsp;参数: `row, function(done)`

&emsp;描述: 表单保存事件回调。`done`可用于关闭窗口。

### @ _row-update_

&emsp;参数: `row, function(done)`

&emsp;描述: 表单编辑事件回调。`done`可用于关闭窗口。

### @ _row-del_

&emsp;参数: `row`

&emsp;描述: 行删除事件回调

### @ _beforeOpen_

&emsp;参数: `function(done)`

&emsp;描述: 表单窗口打开之前事件回调。`done`可用于打开窗口。

### @ _beforeClose_

&emsp;参数: `function(done)`

&emsp;描述: 表单窗口关闭之前事件回调。`done`可用于关闭窗口。

## Slot

ps: 在`crud`组件里面使用的具名插槽，即配置`<template slot="slot-name"></template>`
### - _toolbar_

&emsp;描述: `tableOption`的`toolbar`设置为`true`时可以使用该Slot, 替换自动生成的搜索工具栏。

### - _btn_opt_

&emsp;描述: `tableOption`的`toolbar`未配置 或者设置为`false`时可以使用该Slot。插入自定义的模板内容。

### - _表格字段的Slot_

&emsp;描述：`column`的`slot`设置为`true`时可以使用该Slot。Slot的名称为`column`的`prop`属性值。

&emsp;例如：
```
<template>
  <crud>
    <template slot-scope="scope" slot="username">
      ...
    </template>
  </crud>
</template>

<script>
  export default {
    data () {
      return {
        tableOption: [
          { prop: 'username', slot: true }
        ]
      }
    }
  }
</script>
```
### - _表单子项的Slot_

&emsp;描述：`column`的`formSlot`设置为`true`时可以使用该Slot。Slot的名称为`column`的`prop`属性值加上`Form后缀`。自定义的组件会接收`form, column, dic`三个`props参数`。

&emsp;例如：
```
<template>
  <crud>
    <template slot="usernameForm">
      ...
    </template>
  </crud>
</template>

<script>
  export default {
    data () {
      return {
        tableOption: [
          { prop: 'username', formSlot: true }
        ]
      }
    }
  }
</script>
```
