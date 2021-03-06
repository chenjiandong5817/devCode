/*
* @Author: cdroid
* @Date:   2018-01-05 18:55:15
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-04-04 15:38:51
*/

import * as login from './login'
import * as user from './system/user'
import * as role from './system/role'
import * as permission from './system/permission'
import * as station from './operationManagement/station'
import * as desClassify from './operationManagement/desClassify'
import * as sellStation from './operationManagement/sellStation'
import * as remoteControl from './operationManagement/remoteControl'
import * as orderStatistics from './operationManagement/orderStatistics'
import * as adEdit from './operationManagement/adEdit'
export default Object.assign({}, login, user, role, permission, station, desClassify, sellStation, remoteControl, orderStatistics, adEdit)
