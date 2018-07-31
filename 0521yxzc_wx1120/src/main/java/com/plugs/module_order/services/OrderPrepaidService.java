package com.plugs.module_order.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.base.AjaxList;
import com.plugs.base.WsMsg;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_common.dtos.CommonPayLogDto;
import com.plugs.module_common.mappers.CommonPayLogMapper;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.dtos.OrderPrepaidDto;
import com.plugs.module_order.mappers.OrderPrepaidMapper;
import com.plugs.module_system.services.SysSeqService;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserAccountDetailMapper;
import com.plugs.module_user.services.UserAccountDetailService;
import com.plugs.module_user.services.UserPassengerService;
import com.plugs.timer.PrepaidOrderTimer;
import com.plugs.utils.*;
import com.plugs.utils.pay.tenpay.TenPay;
import com.plugs.utils.pay.tenpay.util.CommonUtil;
import com.plugs.utils.pay.tenpay.util.TenpayConfig;
import com.util.MapUtil;
import com.utils.log4j.QxLog;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 预支付订单业务逻辑处理
 *
 * @outhor qfHan
 * @create 2017-12-04 14:55
 */
@Service
public class OrderPrepaidService extends BaseServiceSupport<OrderPrepaidDto> {
	private static final Logger log = Logger.getLogger(UserAccountDetailService.class);
	private static final Logger qxLogger = Logger.getLogger(UserAccountDetailService.class);

	@Autowired
	private OrderPrepaidMapper<OrderPrepaidDto> orderPrepaidMapper;
	@Autowired
	private OrderService orderService;//订单
	@Autowired
	private UserAccountDetailMapper<UserAccountDetailDto> userAccountDetailMapper;//企业账号明细
	@Autowired
	private SysSeqService sysSeqService; //序列
	@Autowired
	private UserPassengerService userPassengerService;
	@Autowired
	private CommonPayLogMapper<CommonPayLogDto> commonPayLogMapper;//通用支付日志

	@Override
	public IMapper<OrderPrepaidDto> getMapper() {
		return orderPrepaidMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

	public void addPrepaidOrder(OrderDto orderDto) {
		OrderPrepaidDto orderPrepaidDto = new OrderPrepaidDto(orderDto);
		orderPrepaidMapper.add(orderPrepaidDto);
		PrepaidOrderTimer.add(orderPrepaidDto);
	}

	public LinkedList<OrderPrepaidDto> orderPrepaidList(Map<String, Object> params) {
		return orderPrepaidMapper.orderPrepaidList(params);
	}

	@Transactional
	public AjaxList orderPrepaidPay(UserPassengerDto passenger, double money, String orderUuid, int type,
									HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> params = MapUtil.buildMap();
		params.put("uuid", orderUuid);
		OrderDto order = orderService.selInfo(params); //订单信息

		//订单状态以及金额判断
		if (order == null) {
			BizValidate.bizErr("订单异常", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
		} else {
			if (order.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_WAIT_MEET) {
				BizValidate.bizErr("订单状态异常", ReturnCodeConstants.ERR_ORDER_STAUTS_NOT_WAIT_PAY);
			}
		}
		if (money != order.getPrepaidFee()) {
			BizValidate.bizErr("金额异常", ReturnCodeConstants.ERR_ORDER_PRE_PAY_MONEY_ERROR);
		}

		params.clear();
		params.put("orderUuid", orderUuid);
		OrderPrepaidDto orderPrepaidDto = orderPrepaidMapper.selInfo(params);
		if (orderPrepaidDto.getPayStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS) {  //已支付
			BizValidate.bizErr("该预支付订单已支付", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
		}

		//开始进行预支付流程【企业用户无此需求】
		if (type == OrderPrepaidDto.PAY_TYPE_OWNE) { //个人余额支付
			if (passenger.getBalance() < money) {
				BizValidate.bizErr("余额不足", ReturnCodeConstants.ERR_ORDER_PASSENGER_BALANCE_NOT_ENOUGH);
			}
			this.doUserPay(passenger, money, order);
			return AjaxList.createSuccess("订单预支付成功，预付费用已从个人账户中扣取", order);

		} else if (type == OrderPrepaidDto.PAY_TYPE_WX) { //微信
			//预支付金额为0
			String serialNumber;
			if (money <= 0) {
				serialNumber = this.createAccoutDetail("YF_WX", passenger, money, "个人预支付车费", order.getUuid(), UserAccountDetailDto.TYPE_SEPND_3RD, UserAccountDetailDto.PAY_TYPE_WX, true);
				//推送
				WebSocketUtil.sendObjMessageByToken(order.getActualDriverUuid(), WsMsg.createSuccess4Order("预支付费用已从个人账户中扣取",
						order.toMap4WebSocket(),  WsMsg.OPERATE_CODE_DRIVER_PREPAID_PAY_TO_ORDER, WsMsg.To.DRIVER));
				this.updOrderOrPrepaid(order, serialNumber, OrderPrepaidDto.PAY_TYPE_WX, OrderPrepaidDto.PAY_STATUS_SUCCESS);
				return AjaxList.createSuccess("微信预支付费用成功");
			}
			serialNumber = this.createAccoutDetail("YF_WX", passenger, money, "个人预支付车费", order.getUuid(), UserAccountDetailDto.TYPE_SEPND_3RD, UserAccountDetailDto.PAY_TYPE_WX, false);
			return AjaxList.createSuccess("微信预支付ID生成成功", this.doWxPay(serialNumber, money, passenger, order, request, response));
		}

		return AjaxList.createError("服务器繁忙，请稍后支付", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
	}

	/**
	 * 微信预支付
	 * 1.生成微信交易平台JSON信息
	 * 2.创建通用支付流水记录
	 * 3.订单及预支付订单 预支付状态更新
	 *
	 * @param serialNumber 账户流水号
	 * @param money        预支付金额
	 * @param passenger    乘客
	 * @param order        订单详情
	 * @return 微信交易平台JSON
	 */
	private JSONObject doWxPay(String serialNumber, double money, UserPassengerDto passenger, OrderDto order,
							   HttpServletRequest request, HttpServletResponse response) {
		// 调用微信的开放平台创建、支付交易步骤
		JSONObject tradeJson = new JSONObject();//返回结果
		TenpayConfig tenpayConfig = new TenpayConfig(
				ConfigConstants.PASSENGER_ORDER_JS_TEN_PREPAID_PAY_NOTIFY_URL, //回调地址
				ConfigConstants.DRIVER_PREPAID_PAY_GOOD_NAME, //商品名称
				ConfigConstants.DRIVER_PREPAID_PAY_GOOD_INFO, //商品详情
				TenpayConfig.Type.WXUSER);//商品详情
		TenPay tenPay = new TenPay(tenpayConfig);
		try {
			tradeJson = tenPay.yxjsPayRequest(passenger.getOpenId(), serialNumber, money, CommonUtils.getRemoteHost(request), request, response);
		} catch (Exception e) {
			log.error("生成微信预支付错误", e);
		}
		if (!"0".equals(tradeJson.get("retcode"))) {
			// 生成微信支付订单失败
			BizValidate.bizErr("生成微信支付订单失败", ReturnCodeConstants.ERR_10013_WX_PREPAYMENT_ERR);
		}

		//第三方支付流水记录
		this.createCommonPayLog(serialNumber, money, passenger, "wx");
		//订单及预支付订单 预支付状态更新
		this.updOrderOrPrepaid(order, serialNumber, OrderPrepaidDto.PAY_TYPE_WX, OrderPrepaidDto.PAY_STATUS_ING);
		return tradeJson;
	}


	/**
	 * 个人支付
	 * 1.扣除乘客账户余额
	 * 2.创建个人账户明细
	 * 3.更新订单预支付状态，预支付订单支付状态
	 * 4.消息推送给司机
	 *
	 * @param passenger 乘客
	 * @param money     预支付金额
	 * @param orderDto  订单详情
	 */
	private void doUserPay(UserPassengerDto passenger, double money, OrderDto orderDto) {

		//扣除乘客余额
		UserPassengerDto updPassenger = new UserPassengerDto();
		updPassenger.setUuid(passenger.getUuid());
		updPassenger.setDecBalance(money);
		userPassengerService.edit(updPassenger);//更新余额

		//创建账户明细
		String serialNumber = this.createAccoutDetail("YF_YE", passenger, money, "个人预支付车费", orderDto.getUuid(), UserAccountDetailDto.TYPE_SEPND_BALANCE, UserAccountDetailDto.PAY_TYPE_BALANCE, true);
		this.updOrderOrPrepaid(orderDto, serialNumber, OrderPrepaidDto.PAY_TYPE_OWNE, OrderPrepaidDto.PAY_STATUS_SUCCESS);

		//推送
		WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), WsMsg.createSuccess4Order("预支付费用已从个人账户中扣取",
				orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_DRIVER_PREPAID_PAY_TO_ORDER, WsMsg.To.DRIVER));
	}


	/**
	 * 订单及预支付订单编辑
	 * 1.编辑订单预支付状态
	 * 2.预支付订单【流水号，支付方式，支付状态】
	 * 3.修改orderDto变量中的预支付状态
	 *
	 * @param orderDto     订单详情
	 * @param serialNumber 账户流水号
	 * @param payType      支付方式
	 * @param payStatus    支付状态
	 */
	private void updOrderOrPrepaid(OrderDto orderDto, String serialNumber, int payType, int payStatus) {
		OrderDto order = new OrderDto();
		order.setUuid(orderDto.getUuid());
		order.setPrepaidStatus(payStatus);
		orderService.edit(order);

		OrderPrepaidDto orderPrepaidDto = new OrderPrepaidDto();
		orderPrepaidDto.setOrderUuid(orderDto.getUuid());
		orderPrepaidDto.setSerialNumber(serialNumber); //流水号
		orderPrepaidDto.setPayType(payType); //支付方式
		orderPrepaidDto.setPayStatus(payStatus); //预支付状态
		orderPrepaidDto.setUpdateTime(new Date());
		orderPrepaidMapper.edit(orderPrepaidDto);

		orderDto.setPrepaidStatus(OrderPrepaidDto.PAY_STATUS_SUCCESS); //修改orderDto 变量中的值
	}


	/**
	 * 创建第三方通用支付流水
	 *
	 * @param serialNumber 账户流水号
	 * @param money        金额
	 * @param passenger    乘客
	 * @param platform     平台【ali,wx】
	 */
	private void createCommonPayLog(String serialNumber, double money, UserPassengerDto passenger, String platform) {
		// 新增支付日志，先检查是否有该订单号的记录 没有则新增
		Map<String, Object> params = MapUtil.buildMap();
		params.put("tradeNumber", serialNumber);
		List<CommonPayLogDto> payLogs = commonPayLogMapper.list(params);
		if (null == payLogs || payLogs.size() < 1) {
			int type = "ali".equals(platform) ? ConfigConstants.PAY_TYPE_ALIPAY : ConfigConstants.PAY_TYPE_TENPAY;
			String remark = "ali".equals(platform) ? "支付宝预支付车费" : "微信预支付车费";
			CommonPayLogDto payLog = CommonPayLogDto.create(
					CommonPayLogDto.FROM_TYPE_USER,
					serialNumber, //订单流水号
					remark,                        //支付用途
					1,                                  //类别 0：线下支付，1：线上支付
					type,//付款类型 0：线下支付，1：支付宝，2：微信
					passenger.getNickname(),
					type,//收款类型
					money,//总金额
					ConfigConstants.PAY_STATUS_NO,//支付状态
					passenger.getUuid());//创建人
			//保存到数据库
			commonPayLogMapper.add(payLog);
		}
	}


	/**
	 * 生成支付个人支付流水
	 *
	 * @param preSerialName 流水号 前缀
	 * @param passenger     乘客
	 * @param money         金额
	 * @param remark        备注
	 * @param orderUuid     订单UUID
	 * @param isPayd        是否支付
	 * @return 返回流水号
	 */
	private String createAccoutDetail(String preSerialName, UserPassengerDto passenger, double money, String remark, String orderUuid, int type, int payType, boolean isPayd) {
		UserAccountDetailDto userAccountDetail = UserAccountDetailDto.create(
				CommonUtils.generateSerialNumber(sysSeqService.getSerialSeqNextVal(), preSerialName),//流水单号
				passenger.getUuid(),//用户ID
				passenger.getMobile(),//用户手机号
				type,//明细类型:类型（1充值到账户，2,通过第三方消费，3余额消费，4后台金额变更）
				(money),//总金额
				0,//赠送金额
				payType,//支付类型（1支付宝，2微信，3信用卡，4苹果，5余额，6后台管理）
				passenger.getUuid(),
				remark,
				orderUuid);//创建者
		//个人余额支付：为已付款状态
		if (isPayd) {
			userAccountDetail.setPayed(UserAccountDetailDto.PAYED_OK);
			userAccountDetail.setStatus(UserAccountDetailDto.STATUS_SUCCESS);
		}
		//创建预支付订单
		userAccountDetailMapper.add(userAccountDetail);
		return userAccountDetail.getSerialNumber();
	}


	/**
	 * 预支付订单强制关
	 * @param orderUuid
	 * @return
	 */
	public AjaxList closeOrder(String orderUuid) {
		Map<String, Object> parms = MapUtil.buildMap();
		parms.put("uuid", orderUuid);
		OrderDto orderDto =  orderService.selInfo(parms);
		if (orderDto == null) {
			BizValidate.bizErr("订单异常", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
		}
		if (orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_CANCEL) {
			return AjaxList.createSuccess("订单取消成功", orderDto);
		}

		if (orderDto.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_WAIT_MEET) {
			BizValidate.bizErr("订单状态异常", ReturnCodeConstants.ERR_ORDER_STAUTS_NOT_WAIT_PAY);
		}

		parms.clear();
		parms.put("orderUuid", orderUuid);
		OrderPrepaidDto orderPrepaidDto =  orderPrepaidMapper.selInfo(parms);
		if (orderPrepaidDto == null) {
			return AjaxList.createError("无订单预支付信息");
		}

		Date nowDate = new Date();

		OrderDto updOrderDto = new OrderDto();
		updOrderDto.setUuid(orderDto.getUuid());
		updOrderDto.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
		updOrderDto.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_OWN);
		updOrderDto.setUpdateTime(nowDate);
		orderService.edit(updOrderDto);

		OrderPrepaidDto updOrderPrepaidDto = new OrderPrepaidDto();
		updOrderPrepaidDto.setUuid(orderPrepaidDto.getUuid());
		updOrderPrepaidDto.setStatus(2);
		updOrderPrepaidDto.setUpdateTime(nowDate);
		orderPrepaidMapper.edit(updOrderPrepaidDto);

		//通知司机
		if (orderDto.getActualDriverUuid() != null) {
			WsMsg wsMsg = WsMsg.createSuccess4Order("乘客取消订单", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_CANCEL, WsMsg.To.DRIVER);//取消订单
			WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), wsMsg);
		}
		return AjaxList.createSuccess("订单关闭成功", orderDto);
	}


	/************************************************BEGIN*以下为支付回调使用*BEGIN**************************************************/

	/**
	 * 微信支付订单回调
	 *
	 * @param request request请求
	 * @return <xml><return_code>FAIL</return_code></xml>
	 * 或者 <xml><return_code>SUCCESS</return_code></xml>
	 */
	@Transactional(rollbackFor = Exception.class)
	public String orderPrepaidTenPayCallBack(HttpServletRequest request) throws Exception {
		qxLogger.log(QxLog.LEVEL, "微信预支付回调...");
		// xml请求解析
		Map<String, String> requestMap = CommonUtils.parseXmlFromRequest(request);
		String return_code = requestMap.get("return_code");// SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		String result_code = requestMap.get("result_code");// SUCCESS/FAIL
		String tradeNumber = requestMap.get("out_trade_no");//订单流水号 -> 商户订单号

//        double total_fee = Integer.parseInt(requestMap.get("total_fee"));// 订单总金额，单位为分
//        String transaction_id = requestMap.get("transaction_id");// 微信支付订单号

		// 微信支付返回结果
		String resFailXml = "<xml><return_code>FAIL</return_code></xml>";
		String resSuccessXml = "<xml><return_code>SUCCESS</return_code></xml>";

		// 验证该订单是否存在
		UserAccountDetailDto userAccountDetailDto = userAccountDetailMapper.selInfoBySerialNumber(tradeNumber);
		if (userAccountDetailDto == null || userAccountDetailDto.getUuid() == null) {
			// 向微信返回失败信息
			return resFailXml;
		} else {
			if (userAccountDetailDto.getPayed() == UserAccountDetailDto.PAYED_OK) {
				return resSuccessXml;
			}
		}

		//判断微信返回值
		if ("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)) {
			//todo;暂时撤销验证
//			TenpayConfig tenpayConfig = new TenpayConfig(
//					ConfigConstants.PASSENGER_ORDER_JS_TEN_PREPAID_PAY_NOTIFY_URL, //回调地址
//					ConfigConstants.DRIVER_PREPAID_PAY_GOOD_NAME, //商品名称
//					ConfigConstants.DRIVER_PREPAID_PAY_GOOD_INFO,
//					TenpayConfig.Type.WXUSER);//商品详情
			// 验证签名
//			if (CommonUtil.validSign(requestMap, tenpayConfig.getAPP_KEY())) {

				// 更新用户账户明细的支付状态,为付款完成
				updUserAccountDetailPayed(userAccountDetailDto.getUuid(), UserAccountDetailDto.PAYED_OK);

				// 检索支付信息
				Map<String, Object> srhPhParams = MapUtil.buildMap();
				srhPhParams.put("tradeNumber", userAccountDetailDto.getSerialNumber());
				List<CommonPayLogDto> payLogs = commonPayLogMapper.list(srhPhParams);
				Date sysTime = new Date();//系统时间
				// 新增前检查是否有该订单号的记录 没有则新增
				if (payLogs.isEmpty()) {
					//todo：可以不要
					CommonPayLogDto payLog = CommonPayLogDto.createUserTenpayRechargePayLog(userAccountDetailDto, requestMap);
					commonPayLogMapper.add(payLog);
				} else {
					CommonPayLogDto payLog = payLogs.get(0);// 修改支付表支付状态
					payLog.setStatus(ConfigConstants.PAY_STATUS_OK);
					payLog.setPayPlatformSerialNum(requestMap.get("transaction_id"));// 微信交易号
					payLog.setPayAccount(requestMap.get("appid"));// 付款账号（公众账号ID）
					payLog.setCollectAccount(requestMap.get("mch_id"));// 收款账号（微信商户号ID）
					payLog.setPaidTime(sysTime);// 支付日期
					payLog.setCallbackTime(sysTime);// 回调日期
					commonPayLogMapper.edit(payLog);
				}
				//获取用户信息
				orderPayCallbackSuccess(userAccountDetailDto);
				return "resSuccessXml";
//			}
//			return resFailXml;
		}
		return resFailXml;
	}


	/**
	 * 更新用户账户明细的支付状态
	 *
	 * @param uuid  账户流水UUID
	 * @param payed 支付状态
	 */
	private void updUserAccountDetailPayed(String uuid, int payed) {
		UserAccountDetailDto updUserAccountDetail = new UserAccountDetailDto();
		updUserAccountDetail.setUuid(uuid);
		updUserAccountDetail.setStatus(UserAccountDetailDto.STATUS_SUCCESS);
		updUserAccountDetail.setPayed(payed); //已支付
		updUserAccountDetail.setUpdateTime(new Date());
		userAccountDetailMapper.edit(updUserAccountDetail);
	}


	/**
	 * 订单支付回调成功
	 *
	 * @param userAccountDetailDto 账户明细
	 */
	private void orderPayCallbackSuccess(UserAccountDetailDto userAccountDetailDto) {
		qxLogger.log(QxLog.LEVEL, "订单预支付回调成功...");
		Map<String, Object> params = MapUtil.buildMap();
		params.put("uuid", userAccountDetailDto.getOrderUuid());
		OrderDto order = orderService.selInfo(params);
		if (order == null) {
			BizValidate.bizErr("订单不存在", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
		}

		//todo:判断订单状态 如果为已取消状态 则将钱给【可能会与每秒定时操作订单产生同步操作问题】
		/**
		 * 1->订单已取消 ：钱转入乘客余额
		 * 2->正常流程 ：订单预支付,消息推送司机
		 */
		//订单
		OrderDto updOrder = new OrderDto();
		updOrder.setUuid(userAccountDetailDto.getOrderUuid());
		//预支付订单
		OrderPrepaidDto updOrderPrepaidDto = new OrderPrepaidDto();
		updOrderPrepaidDto.setOrderUuid(userAccountDetailDto.getOrderUuid());

		if (order.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_CANCEL) {
			updOrder.setPrepaidStatus(OrderPrepaidDto.PAY_STATUS_CANCEL);
			updOrderPrepaidDto.setPayStatus(OrderPrepaidDto.PAY_STATUS_CANCEL);

			//将金额返还至乘客账户
			UserPassengerDto userPassengerDto = new UserPassengerDto();
			userPassengerDto.setUuid(userAccountDetailDto.getUserUuid());
			userPassengerDto.setInsBalance(userAccountDetailDto.getMoney());
			userPassengerDto.setUpdateTime(new Date());
			userPassengerService.edit(userPassengerDto);

			//推送
			WebSocketUtil.sendObjMessageByToken(order.getActualDriverUuid(), WsMsg.createSuccess4Order("超时支付，预支付费用已存入账户",
					order.toMap4WebSocket(), WsMsg.OPERATE_CODE_PASSENGER_PREPAID_LATE_TO_ORDER, WsMsg.To.PASSENGER));
		} else {
			updOrder.setPrepaidStatus(OrderPrepaidDto.PAY_STATUS_SUCCESS);
			updOrderPrepaidDto.setPayStatus(OrderPrepaidDto.PAY_STATUS_SUCCESS);

			//推送
			WebSocketUtil.sendObjMessageByToken(order.getActualDriverUuid(), WsMsg.createSuccess4Order("乘客已预支付车费",
					order.toMap4WebSocket(), WsMsg.OPERATE_CODE_DRIVER_PREPAID_PAY_TO_ORDER, WsMsg.To.DRIVER));
		}
		orderService.edit(updOrder);
		orderPrepaidMapper.edit(updOrderPrepaidDto);
	}



	/************************************************END*以上为支付回调使用*END**************************************************/

}
