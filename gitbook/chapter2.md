# 第2章 基础数据管理
## 2.1 基础管理
- 这个模块的作用是为整个系统提供基础数据的录入，删除，编辑与查询。该模块的存在支撑了整个系统的运行
### 2.1.1通航机场管理
 - 点击左边导航菜单里的基础管理下面的第一项，进入通航机场管理的页面
#### 2.1.1.1通航机场管理的显示

   - 通航机场的信息是按照中文名称的拼音首字母进行排序的，中文名称的每个汉字的首字母的序号越小越靠近前面。页面上主要显示IATA编码，ICAO编码，地区，一字简称，二字简称等等。页面右下方是分页，每页有五十条数据，具体如图2.1.1.1.1。
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/1.png) 
    >图2.1.1.1.1

   #### 2.1.1.2通航机场管理的查询
   - 我们可以通过页面左上方的操作栏查询想要的数据。可分别按地区，IATA编码，ICAO编码进行查询，也可进行组合查询，提供的信息越详细，所得到的结果越准确。在IATA编码和ICAO编码这两个输入框里输入的字母会自动转换为大写字母。并且在查询成功后，会自动清空IATA与ICAO编码。如图2.1.1.2.1与2.1.1.2.2所示。
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/2.png)                     
    >图2.1.1.2.1
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/3.png)                   
    >图2.1.1.2.2

   #### 2.1.1.3通航机场管理的新增
    - 点击页面左上方工具栏的新增按钮，即可进入到新增页面。新增页面的IATA编码与ICAO编码是必填的，地区代码是必选的，其他信息可依据实际情况进行填写。点击提交后，就在数据库里添加了一条信息。如实2.1.1.3.1所示。
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/4.png)              
    >图2.1.1.3.1

    #### 2.1.1.4通航机场管理的编辑
    - 当我们需要对某一条数据进行编辑的时候，只需要找到对应的数据，在这边数据的右边找到编辑按钮即可。点击编辑按钮进入到编辑框，其中IATA编码与ICAO编码以及地区代码不能为空。如图2.1.1.4.1所示。
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/5.png)                
    >图2.1.1.4.1

    #### 2.1.1.5通航机场管理的删除
    - 当我们要对某一条数据进行删除时，我们只需点击这条数据右边的删除按钮，然后点击确定即可。如图2.1.1.5.1所示。
    ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/6.png)                
    >图2.1.1.5.1

  ### 2.1.2 机型等级管理
 - 点击左边导航菜单里的基础管理下面的第二项，进入机型等级管理的页面
   #### 2.1.2.1 机型等级管理的显示
   - 机型等级管理是按照时间顺序进行排序的，离当前时间越远，排序越靠前。页面上主要显示等级编码，描述等等。在页面右下方是分页，我们给它每个页面50条数据。如图2.1.2.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/7.png)
   >图2.1.2.1.1

   #### 2.1.2.2 机型等级管理的查询
   - 我们可以通过页面左上方的工具栏来查找我们想要的数据，依据等级编码进行查询。在输入框中我们只需要输入对应的等级编码即可，不需要在意大小写，系统会自动转换字母为大写。并且在查询成功后，会将之前输入的等级编码清空。如图2.1.2.2.1与2.1.2.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/8.png)
   >图2.1.2.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/9.png)
   >图2.1.2.2.2

   #### 2.1.2.3 机型等级管理的新增
   - 点击页面左上方工具栏的新增，即可进入新增页面。在这个新增页面中，等级编码与描述是必填的，备注可以根据情况是否要填写。其中等级编码无论写入大写还是小写字母都会转换为大写字母。如图2.1.2.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/10.png)
   >图2.1.2.3.1

   #### 2.1.2.4 机型等级管理的编辑
   - 当我们需要对某一条机型等级的数据进行编辑的时候，我们只需要点击这条数据左边的编辑按钮即可进入到编辑页面。其中描述不能为空，等级编码与备注可以根据需要进行填写。其中等级编码无论输入大写还是小写字母都会自动转换为大写字母。如图2.1.2.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/11.png)
   >图2.1.2.4.1

   #### 2.1.2.5 机型等级管理的删除
   - 当我们需要对某一条数据进行删除的时候，只需要点击这条数据右边的删除按钮即可。如图2.1.2.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/12.png)
   >图2.1.2.5.1

  ### 2.1.3 异常代码管理
 - 点击左边导航菜单里的基础管理下面的第三项，进入异常代码管理的页面
#### 2.1.3.1 异常代码管理的显示
   - 异常代码管理的每条信息是按照异常编码的数字大小来排序的。其中页面上显示的每条数据的内容主要是异常编码，机场，优先级等等。其中每个页面显示五十条数据。如图2.1.3.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/13.png)
   >图2.1.3.1.1

   #### 2.1.3.2 异常代码管理的查询
   - 在页面的的左上方可以分别根据机场，异常代码的父级以及异常编码进行查询，也可以进行组合查询。所提供的信息越详细，查询到的数据就越精确。其中在查询后，异常编码父级与异常编码会清空。如图2.1.3.2.1与2.1.3.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/14.png)
   >图2.1.3.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/15.png)
   >图2.1.3.2.2

   #### 2.1.3.3 异常代码管理的新增
   - 点击右上方工具栏的新增按钮，即可进入新增页面。其中异常编码与描述是必须填写的，其他可以根据实际需要进行填写。机场分国内国外两组，优先级可以微调，它的调度为1。异常编码为一位到六位字符串的编码。如图2.1.3.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/16.png)
   >图2.1.3.3.1

   #### 2.1.3.4 异常代码管理的编辑
   - 当我们需要对某一条数据进行编辑的时候，只需要在这条数据的右边点击编辑按钮，就可以弹出编辑的页面。在这个页面中，异常编码与描述不能修改为空，其他可以根据实际需要进行改编。如图2.1.3.4.1。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/17.png)
   >图2.1.3.4.1

   #### 2.1.3.5 异常代码管理的删除
   - 当我们需要对某一条数据进行删除的时候，我们可以点击这条数据右边的删除按钮即可对这条数据进行删除。如图2.1.3.5.1。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/18.png)
   >图 2.1.3.5.1

 ### 2.1.4代理人代码管理
 - 点击左边导航菜单里的基础管理下面的第四项，进入代理人代码管理的页面
   #### 2.1.4.1 代理人代码管理的显示
   - 代理人代码管理里的每条数据是按照最后更新时间来排序的，离当前时间越近，排的越靠前。列表上主要显示代理人编码，运营机场，代理航空公司等等。代理人代码管理每页50条数据。如图2.1.4.1.1。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/19.png)
   >图2.1.4.1.1

   #### 2.1.4.2 代理人代码管理的查询
   - 我们可以通过页面左上方的工具栏来查询想要的数据，可分别按照运营机场与代理人代码来执行查询，也可以组合查询来得到更精确的数据。在查询后，代理人代码会被清空。如图2.1.4.2.1与2.1.4.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/20.png)
   >图2.1.4.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/21.png)
   >图2.1.4.2.2

   #### 2.1.4.3 代理人代码管理的新增
   - 点击页面左上方工具栏里的新增按钮，可以进入到新增页面。其中代理人编码，与描述是必填的，其他的可以根据实际情况来进行填写。其中代理人代码只能为一位大写字母，代理航空公司可以多选。如图2.1.4.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/22.png)
   >图2.1.4.3.1

   #### 2.1.4.4 代理人代码管理的编辑
   - 当我们需要对某一条数据进行编辑的时候，我们只需要点击这条数据右边的编辑按钮就会弹出编辑页面。其中代理人代码管理与描述不能清空，其他的数据可以根据实际情况进行修改。如图2.1.4.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/23.png)
   >图2.1.4.4.1

   #### 2.1.4.5 代理人代码管理的删除
   - 当我们要删除某一条代理人代码的数据的时候，只需要点击这条数据右边的删除按钮即可。如图2.1.4.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/24.png)
   >图2.1.4.5.1

 ### 2.1.5 航班任务代码管理
 - 点击左边导航菜单里的基础管理下面的第五项，进入航班任务代码管理的页面
#### 2.1.5.1 航班任务代码管理的显示
   - 航班任务代码管理是根据时间进行排序的，离当前时间越近排的越靠前。页面主要显示飞行任务代码，一字简称，二字简称，优先级等等。一个页面是展示50条数据。如图2.1.5.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/25.png)
   >图2.1.5.1.1

   #### 2.1.5.2 航班任务代码管理的查询
   - 在页面左上方的工具栏里，我们可以根据飞行任务代码来查询我们想要的那条数据。当我们执行查询时，所输入的字母会转换成大小写，当我们执行查询后，输入框里的内容会清空。如图2.1.5.2.1与2.1.5.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/26.png)
   >图2.1.5.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/27.png)
   >图2.1.5.2.2

   #### 2.1.5.3 航班任务代码管理的新增
   - 当我们需要新增一条数据的时候，我们只需要在页面左上方的工具栏里点击新增按钮就会进入到新增页面。其中飞行任务代码与描述是必须要填写的，其他的可以根据实际情况来填写。优先级可以进行微调，它的调度为1。如图2.1.5.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/28.png)
   >图2.1.5.3.1

   #### 2.1.5.4 航班任务代码管理的编辑
   - 当我们需要对某一条数据进行修改的时候，我们只需要在这条数据的右边对编辑按钮进行点击，然后会弹出编辑框。其中飞行任务代码与描述不可以清空，其他的可以根据实际需要进行修改。优先级可以进行微调，它的调度为1。如图2.1.5.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/29.png)
   >图2.1.5.4.1

   #### 2.1.5.5 航班任务代码管理的删除
   - 当我们要删除某一条航班任务代码管理的数据时，我们只需要点击这条数据右边的删除按钮即可。如图2.1.5.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/30.png)
   >图2.1.5.5.1

 ### 2.1.6 航班状态代码管理
 - 点击左边导航菜单里的基础管理下面的第六项，进入航班状态代码管理的页面
#### 2.1.6.1 航班状态代码管理的显示
   - 航班状态代码管理的每条数据是根据时间来进行排序的，离当前时间越远排的越靠前。页面主要展示编码，进港/出港，状态分类等等。每个页面50条数据。如图2.1.6.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/31.png)
   >图2.1.6.1.1

   #### 2.1.6.2 航班状态代码管理的查询
   - 在页面左上方的工具栏可以根据编码进行查询，输入框只要输入字母即可，它会自动转换为大写字母。在执行完查询操作后，输入框会清空数据。如图2.1.6.2.1与2.1.6.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/32.png)
   >图2.1.6.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/33.png)
   >图2.1.6.2.2

   #### 2.1.6.3 航班状态代码管理的新增
   - 在页面左上方的工具栏里点击新增按钮即可进入新增页面，其中编码是必须要填写的，其他的可以根据实际情况来进行填写。输入字母到编码的输入框里会自动转换为大写字母。如图2.1.6.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/34.png)
   >图2.1.6.3.1

   #### 2.1.6.4 航班状态代码管理的编辑
   - 当我们要对某一条数据进行修改时，只需点击该条数据右边的编辑按钮即可进入到编辑页面。编辑页面里，编码不能被清空，其他的可以根据实际需要进行修改。如图2.1.6.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/35.png)
   >图2.1.6.4.1

   #### 2.1.6.5 航班状态代码管理的删除
   - 当我们需要对某一条数据进行删除的时候，只需要在这条数据的右边点击删除按钮即可。如图2.1.6.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/36.png)
   >图2.1.6.5.1

 ### 2.1.7 航空公司管理
 - 点击左边导航菜单里的基础管理下面的第七项，进入航空公司管理的页面
#### 2.1.7.1 航空公司管理的显示
   - 航空公司管理是根据时间顺序进行排序的，离当前时间越近排的越靠前。页面主要展示了IATA代码，ICAO代码等等。一个页面展示50条数据。如图2.1.7.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/37.png)
   >图2.1.7.1.1

   #### 2.1.7.2 航空公司管理的查询
   - 当我们需要执行查询工作时，可以在页面左上方的工具栏里分别依据IATA代码与ICAO代码进行查询，也可以进行组合查询获得更准确的信息。在执行查询前所输入的代码会自动转换为大写字母，执行成功后会自动清空掉输入框里的代码。如图2.1.7.2.1与2.1.7.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/38.png)                                                                                       
   >图2.1.7.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/39.png)
   >图2.1.7.2.2

   #### 2.1.7.3 航空公司管理的新增
   - 当我们需要新增一条数据的时候，只需要在页面左上方的工具栏里点击新增按钮就可以进入新增页面。其中IATA代码，ICAO代码，中文名称，中文简称，航空公司属性必须要填写，其他的可以根据实际情况来进行填写。在IATA代码与ICAO代码输入框里所输入的字母可以自动转换为大写代码。如图2.1.7.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/40.png)
   >图2.1.7.3.1

   #### 2.1.7.4 航空公司管理的编辑
   - 当我们需要对某一条数据进行进行修改的时候，只需要点击该条数据右边的编辑按钮就可以弹出编辑框。其中IATA代码，ICAO代码，中文名称，中文简称，航空公司属性不能为空，其他的可以根据实际情况进行修改。如图2.1.7.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/41.png)
   >图2.1.7.4.1

   #### 2.1.7.5 航空公司管理的删除
   - 当我们需要删除某一条数据的时候，只需要点击该条数据右边的删除按钮即可。如图2.1.7.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/42.png)
   >图2.1.7.5.1

 ### 2.1.8 机号信息管理
 - 点击左边导航菜单里的基础管理下面的第八项，进入机号信息管理的页面
#### 2.1.8.1 机号信息管理的显示
   - 机号信息管理是根据时间来进行排序的，离当前时间越远，排的越前面。页面的每条数据主要展示机号，机型IATA代码，承运人代码等等。每个页面展示50条数据。如图2.1.8.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/43.png)
   >图2.1.8.1.1

   #### 2.1.8.2 机号信息管理的查询
   - 机号信息管理可以分别根据承运人代码，机号，机型IATA代码，也可以进行组合查询来获得更精确的数据。在机号与机型IATA代码里输入的字母会自动转换为大写字母。在执行完查询后，承运人代码，机号与机型IATA代码会自动清空。如图2.1.8.2.1与2.1.8.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/44.png)
   >图2.1.8.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/45.png)
   >图2.1.8.2.2

   #### 2.1.8.3 机号信息管理的新增
   - 当我们需要新增一条数据的时候，只需要在左上方的工具栏里点击新增就会进入新增页面。其中机型IATA代码输入框里输入代码会自动转换为大写字母，可供座位，最大座位，可供业载，最大业载，舱门尺寸，最大起飞重量，标准停场时间填写的数字，它们可以进行微调，微调幅度的大小为1。如图2.1.8.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/46.png)
   >图2.1.8.3.1

   #### 2.1.8.4 机号信息管理的编辑
   - 当我们需要对某一条数据进行编辑的时候，只需要在这条数据的右边点击编辑按钮就会进入到编辑页面。机型IATA代码需要输入字母，输入框会自动转换为大写字母。可供座位，最大座位，可供业载，最大业载，舱门尺寸，最大起飞重量，标准挺场时间可以进行微调，幅度为1。如图2.1.8.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/47.png)
   >图2.1.8.4.1

   #### 2.1.8.5 机号信息管理的删除
   - 当我们需要对某一条数据进行删除的时候，只需要在这条数据的右边点击删除按钮即可。如图2.1.8.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/48.png)
   >图2.1.8.5.1

 ### 2.1.9  vip类型管理
 - 点击左边导航菜单里的基础管理下面的第九项，进入vip类型管理的页面
#### 2.1.9.1 vip类型管理的显示
   - vip类型管理的每条数据是按照时间进行排序的，离当前时间越远，排的越靠前。页面上主要展示级别编码，优先级和运营机场等等。每页只显示50条数据。如图2.1.9.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/49.png)
   >图2.1.9.1.1

   #### 2.1.9.2 vip类型管理的查询
   - vip类型管理可以分别根据运营机场与级别编码进行查询，也可以进行组合查询以获得更精确的数据。在级别编码输入框无论输入大写字 母还是小写字母都会转换为大写字母。在查询成功后，级别编码输入框会被清空。如图2.1.9.2.1与2.1.9.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/50.png)
   >图2.1.9.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/51.png)
   >图2.1.9.2.2

   #### 2.1.9.3 vip类型管理的新增
   - 点击页面左上方的工具栏里的新增按钮，即可进入到新增页面。级别编码与描述是必须要填写的，其中在级别编码输入框里输入字母都会转换成大写字母，并且在这个输入框里只能输入一位字母，同时优先级只能填写数字，可以进行微调，它的调度为1，其他的可以根据实际情况进行微调。如图2.1.9.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/52.png)
   >图2.1.9.3.1

   #### 2.1.9.4 vip类型管理的编辑
   - 如果我们要编辑vip类型管理的某一条数据，我们只需要在这条数据的右边点击编辑按钮即可。其中级别编码与描述不能清空，优先级是数字，可以进行微调，它的调度为1。其他的可以根据实际情况进行填写。如图2.1.9.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/53.png)
   >图2.1.9.4.1

   #### 2.1.9.5 vip类型管理的删除
   - 如果我们要删除某一条数据，只需要在这条数据的右边点击删除按钮即可，就可以进入到删除界面，对数据进行删除。如图2.1.9.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/54.png)
   >图2.1.9.5.1

 ### 2.1.10 计划类型管理
 - 点击左边导航菜单里的基础管理下面的第十项，进入计划类型管理的页面
#### 2.1.10.1 计划类型管理的显示
   - 计划类型管理的每条数据是按照时间进行排序的，离当前时间越远排的月前面。每天数据主要展示区域代码，描述，备注以及最后更新时间。每个页面展示50条数据。如图2.1.10.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/55.png)
   >图2.1.10.1.1所示

   #### 2.1.10.2 计划类型管理的查询
   - 计划类型管理可以根据区域代码进行查询。在页面左上方的工具栏里只要输入字母就会自动转换为大写字母，并且在查询成功后，会对输入框里的值进行清空。如图2.1.10.2.1与2.1.10.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/56.png)
   >图2.1.10.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/57.png)
   >图2.1.10.2.2

   #### 2.1.10.3 计划类型管理的新增
   - 在页面左上方的工具栏里有新增按钮，当我们点击新增按钮的时候会进入到新增的页面，其中区域代码是必须填写的，而且在区域代码里只要输入字母就会转换为大写字母，并且只能输入一位字母。其他的可以根据实际需要来进行填写。如图2.1.10.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/58.png)
   >图2.1.10.3.1

   #### 2.1.10.4 计划类型管理的编辑
   - 当我们点击某一条数据右边的编辑按钮时，就会弹出编辑框，可以对这条数据进行修改，其中区域代码不能清空，并且区域代码这个输入框只要输入字母就会转换为大写字母，并且只能输入一位字母，其他的可以根据实际情况进行编辑。如图2.1.10.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/59.png)
   >图2.1.10.4.1

   #### 2.1.10.5 计划类型管理的删除
   - 点击某一条数据右边的删除按钮即可弹出删除框，对该条数据进行删除。如图2.1.10.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/60.png)
   >图2.1.10.5.1

  #### 2.1.11 资源状态管理
   - 点击左边导航菜单里的基础管理下面的第十一项，进入资源状态管理的页面
   #### 2.1.11.1 资源状态管理的显示
    - 资源状态管理的每条数据是根据时间进行排序的，离当前时间越近排的越靠前。在页面上主要展示设备状态编码，描述，备注与最后更新时间。每个页面能够显示50条数据。如图2.1.11.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/61.png)
   >图2.1.11.1.1

   #### 2.1.11.2 资源状态管理的查询
    - 在页面左上方的工具栏里，可以根据设备状态编码来查询数据。在设备状态输入框只要输入字母就会转换为大写字母。并且在查询成功后，输入框的值会被清空。如图2.1.11.2.1与2.1.11.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/62.png)
   >图2.1.11.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/63.png)
   >图2.1.11.2.2

   #### 2.1.11.3 资源状态管理的新增
    - 点击页面左上方工具栏的新增按钮，即可进入新增页面。在新增页面里设备状态编码是必须填写的，其他的可以根据实际情况进行填写。其中设备状态编码只能输入一位大写字母，在这个输入框里输入字母会被自动转换为大写字母。如图2.1.11.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/64.png)
   >图2.1.11.3.1

   #### 2.1.11.4 资源状态管理的编辑
    - 点击某一条数据右边的编辑按钮，会进入到编辑页面。其中设备状态编码不能请空，并且只能是一位大写字母，在这个输入框里输入字母的时候会自动转换为大写字母。其他的可以根据实际情况进行填写。如图2.1.11.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/65.png)
   >图2.1.11.4.1

   #### 2.1.11.5 资源状态管理的删除  
    - 当我们要删除某一条数据的时候，只需要点击这条数据右边的删除按钮就会进入到删除界面，点击确认就会对该条数据进行删除。如图2.1.11.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/66.png)
   >图2.1.11.5.1

 ### 2.1.12 资源属性管理
 - 点击左边导航菜单里的基础管理下面的第十二项，进入资源属性管理的页面
#### 2.1.12.1 资源属性管理的显示
   - 在资源属性管理里的每条数据是按照时间进行排序的，离当前时间越近，排的越靠前。主要展示区域代码，描述与备注。每个页面展示50条数据。如图2.1.12.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/67.png)
   >图2.1.12.1.1

   #### 2.1.12.2 资源属性管理的查询
   - 在页面左上方的工具栏里，可以根据区域代码进行查询。在区域代码输入框里输入字母会自动转换为大写字母，在查询成功后，会清空输入框里面的值。如图2.1.12.2.1与2.1.12.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/68.png)
   >图2.1.12.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/69.png)
   >图2.1.12.2.2

   #### 2.1.12.3 资源属性管理的新增
   - 点击页面左上方工具栏里的新增按钮，即可进入到新增页面。输入框区域代码只能填写一位大写字母，并且只要输入字母就会转换为大写字母，这个输入框是必须填写的。描述与备注可以根据实际情况进行填写。如图2.1.12.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/70.png)
   >图2.1.12.3.1

   #### 2.1.12.4 资源属性管理的编辑
   - 当我们要对某一条数据进行修改的时候，只需要点击这条数据右边的编辑按钮就会进入到编辑页面。其中区域代码不可清空，在这个输入框只要输入字母就会转换为大写字母，在这个输入框里只能输入一位大写字母。描述与备注可以根据实际情况进行填写。如图2.1.12.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/71.png)
   >图2.1.12.4.1

   #### 2.1.12.5 资源属性管理的删除
   - 点击某条数据右边的删除按钮，即可进入到删除界面对该数据进行删除。如图2.1.12.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/72.png)
   >图2.1.12.5.1

 ### 2.1.13 航班属性管理
 - 点击左边导航菜单里的基础管理下面的第十三项，进入航班属性管理的页面
#### 2.1.13.1 航班属性管理的显示
   - 航班属性管理的每条数据是根据时间进行排序的，离当前时间越远排在越前面。每条数据在页面上有编码，描述，备注与最后更新时间这几个字段。每个页面显示50条数据。如图2.1.13.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/73.png)
   >图2.1.13.1.1

   #### 2.1.13.2 航班属性管理的查询
   - 在页面左上方的工具栏里，可以根据编码查询数据。在编码输入框里只要输入字母就会转换为大写字母，并且在查询成功后，会自动清空输入框里的值。如图2.1.13.2.1与2.1.13.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/74.png)
   >图2.1.13.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/75.png)
   >图2.1.13.2.2

   #### 2.1.13.3 航班属性管理的新增
   - 点击页面左上方工具栏里的新增按钮，进入到新增页面。编码与描述不能为空，其中编码只能为一位大写字母，在编码输入框里输入字母会自动转换为大写字母。备注可以根据实际需要进行填写。如图2.1.13.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/76.png)
   >图2.1.13.3.1

   #### 2.1.13.4 航班属性管理的编辑
   - 当我们需要修改某一条数据的时候，只需要在这条数据的右边点击编辑按钮就会进入到编辑页面。编码与描述不能清空，并且编码只能为一位大写字母。在编码输入框里输入字母的时候，会自动转换为大写字母。备注根据实际情况进行修改。如图2.1.13.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/77.png)
   >图2.1.13.4.1

   #### 2.1.13.5 航班属性管理的删除
   - 点击某条数据右边的删除按钮，即可对该条数据进行删除。如图2.1.13.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/78.png)
   >图2.1.13.5.1

 ### 2.1.14 方向代码管理
 - 点击左边导航菜单里的基础管理下面的第十四项，进入方向代码管理的页面
#### 2.1.14.1 方向代码管理的显示
   - 方向代码管理的每条数据根据时间进行排序，离当前时间越近排的越靠前。每条数据在页面上主要显示编码，描述，备注与最后更新时间。每个页面显示50条数据。如图2.1.14.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/79.png)
   >图2.1.14.1.1

   #### 2.1.14.2 方向代码管理的查询
   - 在页面左上方工具栏里可以根据编码进行查询，在编码输入框里输入字母会自动转换为大写字母。在查询成功后，会清空输入框里的值。如图2.1.14.2.1与2.1.14.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/80.png)
   >图2.1.14.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/81.png)
   >图2.1.14.2.2

   #### 2.1.14.3 方向代码管理的新增
   - 点击页面左上方的新增按钮，即可进入到新增页面。其中编码与描述不可为空，备注可以根据实际情况进行填写。编码只能是一位大写字母，在编码输入框里输入字母会自动转换为大写字母。如图2.1.14.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/82.png)
   >图2.1.14.3.1

   #### 2.1.14.4 方向代码管理的编辑
   - 当我们需要对某一条数据进行编辑的时候，只需要点击这条数据右边的编辑按钮就会进入到编辑页面。其中编码与描述不能为空，备注可以根据实际情况进行修改。编码只能是一位大写字母，在编码输入框里输入字母会自动转换为大写字母。如图2.1.14.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/83.png)
   >图2.1.14.4.1

   #### 2.1.14.5 方向代码管理的删除
   - 当我们需要删除某一条数据的时候，只需要点击这条数据右边的删除按钮进入到删除界面就可以对该条数据进行删除。如图2.1.14.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/84.png)
   >图2.1.14.5.1

 ### 2.1.15 柜台分配模式管理 
 - 点击左边导航菜单里的基础管理下面的第十五项，进入柜台分配模式管理的页面
#### 2.1.15.1 柜台分配模式管理的显示
   - 柜台分配模式管理的每条数据是根据时间进行排序的，离当前时间越近，排的
越后面。每条柜台分配模式的数据显示编码，描述，备注与最后更新时间。每个页
面显示50条数据。如图2.1.15.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/85.png)
   >图2.1.15.1.1

   #### 2.1.15.2 柜台分配模式管理的查询
   - 在页面左上方的工具栏里可以根据编码查询数据，在编码输入框里输入字母会自动转换为大写字母，在查询成功后会清空输入框的数据。如图2.1.15.2.1与2.1.15.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/86.png)
   >图2.1.15.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/87.png)
   >图2.1.15.2.2

   #### 2.1.15.3 柜台分配模式管理的新增
   - 点击页面左上方工具栏里的新增按钮即可进入到新增页面。编码与描述不能为空，备注可以根据实际情况进行编写。编码只能为一位大写字母，并且在编码输入框里输入字母会自动转换为大写字母。如图2.1.15.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/88.png)
   >图2.1.15.3.1

   #### 2.1.15.4 柜台分配模式管理的编辑
   - 当我们要编辑某一条数据的时候，只需要点击这条数据右边的编辑按钮就会进入到编辑页面。编码与描述不能为空，备注可以根据实际情况进行修改。其中编码只能是一位大写字母，在编码输入框里输入字母会自动转换为大写字母。如图2.1.15.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/89.png)
   >图2.1.15.4.1

   #### 2.1.15.5 柜台分配模式管理的删除
   - 当需要删除某条数据的时候，只需要点击这条数据右边的删除按钮，就可以对该条数据进行删除。如图2.1.15.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/90.png)
   >图2.1.15.5.1

 ### 2.1.16 柜台服务类型管理
 - 点击左边导航菜单里的基础管理下面的第十六项，进入柜台服务类型管理的页面
#### 2.1.16.1 柜台服务类型管理的显示
   - 柜台服务类型管理的每条数据是按照时间进行排序的，离当前时间越远，排序的越靠前。每一条数据主要展示服务类型代码，值机柜台模板与描述等等，每个页面展示50条数据。如图2.1.16.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/91.png)
   >图2.1.16.1.1

   #### 2.1.16.2 柜台服务类型管理的查询
   - 在页面左上方的工具栏里，可以支持分别按柜台分配模式与服务类型代码进行查询，也可以进行组合查询以便获得更详细的信息。其中服务类型代码里可以把字母转换为大写字母，并且查询成功执行后，输入框里的值会被清空。如图2.1.16.2.1与2.1.16.2.2。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/92.png)
   >图2.1.16.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/93.png)
   >图2.1.16.2.2

   #### 2.1.16.3 柜台服务类型管理的新增
   - 点击页面左上方工具栏里的新增按钮，可以进入新增页面。服务类型代码与描述不能为空，其他的可以根据实际需要进行填写。其中在服务类型代码输入框里输入字母会自动转换为大写字母，并且只能输入一位字母。如图2.1.16.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/94.png)
   >图2.1.16.3.1

   #### 2.1.16.4 柜台服务类型管理的编辑
   - 当我们要编辑某一条数据的时候，只需要点击这条数据右边的编辑按钮进入到编辑页面。其中服务类型代码与描述不能清空，其他的可以根据实际情况进行修改。在服务类型代码输入框里只能输入一位字母，并且在这个输入框里输入字母会自动转换为大写字母。如图2.1.16.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/95.png)
   >图2.1.16.4.1

   #### 2.1.16.5 柜台服务类型管理的删除
   - 当我们要删除某一条数据的时候，只需要点击这条数据右边的删除按钮即可，会进入到删除页面。如图2.1.16.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/96.png)
   >图2.1.16.5.1

 ### 2.1.17 机场联系人管理
 - 点击左边导航菜单里的基础管理下面的第十七项，进入机场联系人管理的页面
#### 2.1.17.1 机场联系人的显示
   - 机场联系人管理的每条数据是按照时间进行排序的，离当前时间越近，排序的越靠前。每条数据主要显示职务，联系方式与机场等等。每个页面展示50条数据。如图2.1.17.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/97.png)
   >图2.1.17.1.1

   #### 2.1.17.2 机场联系人的查询
   - 在页面左上方的工具栏里进行执行查询操作，可以分别按照机场与职务进行查询，也可以进行组合查询以获得更精确的信息。在执行查询成功后，职务输入框里的值会被清空。如图2.1.17.2.1与2.1.17.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/98.png)
   >图2.1.17.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/99.png)
   >图2.1.17.2.2

   #### 2.1.17.3 机场联系人的新增
   - 点击页面左上方工具栏里的新增按钮，可以进入新增界面执行新增操作。其中职务是必须要填写的，机场与联系方式根据实际情况进行填写。如图2.1.17.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/100.png)
   >图2.1.17.3.1

   #### 2.1.17.4 机场联系人的编辑
   - 当要编辑某一条机场联系人的数据时，点击这条数据右边的编辑按钮进入到编辑页面。其中职务不能被清空，机场与联系方式根据实际情况进行编辑。如图2.1.17.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/101.png)
   >图2.1.17.4.1

   #### 2.1.17.5 机场联系人的删除
   - 当要删除某一条数据的时候，只要点击这条数据右边的删除按钮进入到删除页面进行操作即可。如图2.1.17.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/102.png)
   >图2.1.17.5.1

 ### 2.1.18 枚举信息管理
 - 点击左边导航菜单里的基础管理下面的第十八项，进入枚举信息管理的页面
#### 2.1.18.1 枚举信息管理的显示
   - 枚举信息管理的每一条数据在页面上显示类型，枚举编码，显示值，备注与更新时间。如图2.1.18.1.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/103.png)
   >图2.1.18.1.1

   #### 2.1.18.2 枚举信息管理的查询
   - 在页面左上方工具栏里可以执行查询操作，可以分别按照类型与枚举编码进行查询，也可以进行组合查询以便获得更准确的信息。其中枚举编码在查询成功后会被清空。如图2.1.18.2.1与2.1.18.2.2所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/104.png)
   >图2.1.18.2.1
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/105.png)
   >图2.1.18.2.2

   #### 2.1.18.3 枚举信息管理的新增
   - 点击页面左上方工具栏里的新增执行新增操作。其中类型，枚举编码，显示值是必须要填写的，而备注根据实际情况进行填写。其中类型输入框输入的字母会自动转换为大写字母。如图2.1.18.3.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/106.png)
   >图2.1.18.3.1

   #### 2.1.18.4 枚举信息管理的编辑
   - 当要对某一条数据进行编辑的时候，只需要点击该条数据右边的编辑按钮然后进入到编辑页面。类型，枚举编码，显示值不能为空。而备注根据实际情况进行填写。其中类型输入框输入的字母会自动转换为大写字母。如图2.1.18.4.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/107.png)
   >图2.1.18.4.1

   #### 2.1.18.5 枚举信息管理的删除
   - 当要删除某一条数据的时候，只需要点击该条数据右边的删除按钮进入到删除界面执行删除操作即可。如图2.1.18.5.1所示。
   ![](https://raw.githubusercontent.com/chenjiandong5817/firstbranch/second/108.png)
   >图2.1.18.5.1