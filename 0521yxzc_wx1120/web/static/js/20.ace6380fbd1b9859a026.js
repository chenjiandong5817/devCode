webpackJsonp([20],{"B6+5":function(e,t){},EV1k:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=s("Dd8w"),i=s.n(n),a=s("NYxO"),o={data:function(){return{phone:"",validate:"",time:60,checked:!0}},components:{comMask:function(){return s.e(1).then(s.bind(null,"FJar"))}},computed:i()({},Object(a.mapState)(["loginMask"]),{disabled:function(){return this.phone&&this.validate}}),watch:{validate:function(e,t){console.log("newValue",e),console.log("oldValue",t),e.length>4&&(this.validate=t)},phone:function(e,t){console.log("newValue",e),console.log("oldValue",t);/^[0-9]*$/.test(e)?this.phone=e:this.phone=t||""}},methods:i()({},Object(a.mapActions)({switch_loginMask:"switch_loginMask"}),{Countdown:function(){var e=this;0===e.time?e.time=60:(e.time--,setTimeout(function(){e.Countdown()},1e3))},clear:function(){this.phone=""},sendMsg:function(){var e=this;this.phone?this.$store.dispatch("sendMsgRequest",this.phone).then(function(t){t.success?e.Countdown():e.showToastTime(t.msg)}).catch(function(){return e.showToastTime("请求出错"),!1}):this.showToastTime("手机不能为空")},login:function(){var e=this;if(this.checked)if(this.phone&&this.validate){/^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/.test(this.phone)&&11===this.phone.length?this.$store.dispatch("loginRequest",{phone:this.phone,validate:this.validate}).then(function(t){t.success?window.location.reload():e.showToastTime(t.msg),console.log(t)}).catch(function(){return e.showToastTime("请求出错"),!1}):this.showToastTime("手机格式不正确")}else this.showToastTime("请填写手机和验证码");else this.showToastTime("请勾选用户协议")},showToastTime:function(e){this.$createToast({type:"warn",time:1e3,txt:e}).show()},default_event:function(){return!1}})},c={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",[s("transition",{attrs:{"enter-active-class":"animated bounceIn","leave-active-class":"animated bounceOut"}},[e.loginMask?s("div",{staticClass:"login-form"},[s("form",[s("p",{staticClass:"title"},[e._v("验证手机号，方便司机联系您")]),e._v(" "),s("div",{staticClass:"form-item phone"},[s("input",{directives:[{name:"model",rawName:"v-model.trim",value:e.phone,expression:"phone",modifiers:{trim:!0}}],attrs:{type:"tel",placeholder:"请输入手机号",maxlength:"11"},domProps:{value:e.phone},on:{input:function(t){t.target.composing||(e.phone=t.target.value.trim())},blur:function(t){e.$forceUpdate()}}}),e._v(" "),s("span",{directives:[{name:"show",rawName:"v-show",value:e.phone,expression:"phone"}],staticClass:"clearBtn",on:{click:e.clear}}),e._v(" "),e.time<60?s("span",{staticClass:"btn"},[e._v(e._s(e.time)+" 秒")]):s("span",{staticClass:"btn",on:{click:function(t){e.sendMsg()}}},[e._v("验证")])]),e._v(" "),s("div",{staticClass:"form-item validate"},[s("input",{directives:[{name:"model",rawName:"v-model.trim",value:e.validate,expression:"validate",modifiers:{trim:!0}}],attrs:{type:"number",placeholder:"请输入验证码",maxlength:"4"},domProps:{value:e.validate},on:{input:function(t){t.target.composing||(e.validate=t.target.value.trim())},blur:function(t){e.$forceUpdate()}}})]),e._v(" "),s("div",{staticClass:"form-item"},[s("cube-button",{staticClass:"submitBtn",attrs:{disabled:!e.disabled},on:{click:function(t){e.login()}}},[e._v("确定")])],1),e._v(" "),s("label",{staticClass:"style-label rule"},[s("input",{directives:[{name:"model",rawName:"v-model",value:e.checked,expression:"checked"}],attrs:{type:"checkbox",hidden:"hidden"},domProps:{checked:Array.isArray(e.checked)?e._i(e.checked,null)>-1:e.checked},on:{change:function(t){var s=e.checked,n=t.target,i=!!n.checked;if(Array.isArray(s)){var a=e._i(s,null);n.checked?a<0&&(e.checked=s.concat([null])):a>-1&&(e.checked=s.slice(0,a).concat(s.slice(a+1)))}else e.checked=i}}}),e._v(" "),s("span",{staticClass:"style-checkbox"},[s("span",{staticClass:"style-checkbox-inner"})]),e._v(" "),s("a",{attrs:{href:"/api/base/userAgreement"}},[e._v("我接受元翔专车用户协议")])])])]):e._e()]),e._v(" "),s("transition",{attrs:{"enter-active-class":"animated fadeIn","leave-active-class":"animated fadeOut"}},[s("com-mask",{attrs:{show:e.loginMask,event:e.default_event}})],1)],1)},staticRenderFns:[]};var l=s("VU/8")(o,c,!1,function(e){s("B6+5")},null,null);t.default=l.exports}});
//# sourceMappingURL=20.ace6380fbd1b9859a026.js.map