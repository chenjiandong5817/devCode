# CRUD 子组件说明文档
> Version: 0.1.4
>
> Author: cdroid
>
> Date: 2018-05-24

> Crud 子组件的个性化参数可以通过`column` 里面的`meta`属性进行插入

## Crud-Input

&emsp;描述： 文本输入框和文本域输入框，会根据`column`的`type`（text 或者 textarea）自动判断

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
clearable: 是否可清除， Boolean类型，默认true
convert: 文本转换，String类型，默认为空
  - 可选参数
  - toUpperCase / toLowerCase
minRows: 最小行数，Number类型，默认 3
maxRows: 最大行数，Number类型，默认 4
```

## Crud-Input-Number

&emsp;描述： 数字输入框

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
min: 最小值，Number类型，默认 -Infinity
max: 最大值，Number类型，默认 Infinity
```

## Crud-Select

&emsp;描述： 选择框

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
clearable: 是否可清除， Boolean类型，默认true
filterable: 是否可输入过滤， Boolean类型，默认true
multiple: 是否可多选， Boolean类型，默认false
props: 选项参数，Objectl类型
  - label: 文本名称， String类型，默认`label`
  - value: 值名称， String类型，默认`value`
template: 是否使用个性化模版, Boolean类型或者Function类型
  - 如果设置成true, 需要增加一个`${column.prop}Crud`的slot插槽，例如字段flight, 对应插槽名称为flightCrud
  - 如果设置成Function, 接收参数target, 包含字典对象的所有信息
```

## Crud-Cascader

&emsp;描述： 级联选择框

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
clearable: 是否可清除， Boolean类型，默认true
props: 选项参数，Objectl类型
  - label: 文本名称， String类型，默认`label`
  - value: 值名称， String类型，默认`value`
  - children: 子集合名称，String类型，默认`children`
```

## Crud-Radio

&emsp;描述： 单选框

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
border: 是否显示边框，Boolean类型，默认false
```

## Crud-Chechbox

&emsp;描述： 复选框

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
border: 是否显示边框，Boolean类型，默认false
```

## Crud-Date

&emsp;描述： 各种各样的日期时间选择框，会根据`column`的`type`（year / month / date / dates / week / datetime / datetimerange / daterange）自动判断

&emsp;参数：
```
size: 组件大小，String类型，默认small
  - 可选参数
  - medium / small / mini
clearable: 是否可清除， Boolean类型，默认true
```

## RemoteData 类型
&emsp;描述： 使用authKey获取于后台数据交互的api。参数可以不按照顺序填写。

&emsp;参数：
```
key: 权限键，String类型，在菜单权限控件（menuControl）的键值。
params: axios参数，Object类型，可以包含过滤条件和分页参数。
getDataFn(res): 回调方法，Function类型，在获取数据完成后，根据自定义的方法从载体内获取数据。
  - 参数
  - res: axios获取回来的原始值
```
