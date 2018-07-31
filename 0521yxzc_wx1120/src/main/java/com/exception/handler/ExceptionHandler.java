package com.exception.handler;


import com.exception.AdminBizException;
import com.plugs.base.AjaxList;
import com.plugs.base.Result;
import com.plugs.constants.ExceptionConstants;
import com.exception.BizException;
import com.exception.SessionTimeOutException;
import com.exception.ValidateException;
import com.plugs.constants.ReturnCodeConstants;
import com.util.HttpUtil;
import com.util.JsonUtil;
import com.util.StringUtil;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private static Logger validateLogger = LoggerFactory.getLogger(ExceptionConstants.EXCEPTION_VALIDATE);
    private static Logger bizLogger = LoggerFactory.getLogger(ExceptionConstants.EXCEPTION_BIZ);
    private static Logger runtimeLogger = LoggerFactory.getLogger(ExceptionConstants.EXCEPTION_RUNTIME);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        ModelAndView mv = null;

        ex.printStackTrace();

        String requestInfo = getRequestInfo(request);        //获取请求详情
        String exceptionInfo = ex.getMessage();                //异常信息
        String stackInfo = getStackTrace(ex);    //异常堆栈休息

        StringBuilder logInfoBuilder = new StringBuilder();
        logInfoBuilder.append("request:").append(ExceptionConstants.LOG_INFO_SPLIT)
                .append(requestInfo).append(ExceptionConstants.LOG_INFO_SPLIT)
                .append("exception:").append(ExceptionConstants.LOG_INFO_SPLIT).append(exceptionInfo).append(ExceptionConstants.LOG_INFO_SPLIT)
                .append("stack:").append(ExceptionConstants.LOG_INFO_SPLIT).append(stackInfo); //日志信息

        boolean isNeedResp = true;    //是否需要响应


        //判断具体异常-业务异常
        if (ex instanceof BizException) {
            bizLogger.error(logInfoBuilder.toString());
            //mv = new ModelAndView("redirect:" + DomainConstants.BIZ_EXCEPTION);
            //mv.addObject("msg", ex.getMessage());
            int errcode = ReturnCodeConstants.ERR_10006_LOGIC_ERR;
            if (((BizException) ex).getErrCode() != null) {
                errcode = ((BizException) ex).getErrCode();
            }
            mv = getAjaxMV(ex.getMessage(), errcode);
            mv.addObject("callback", request.getParameter("callback"));

        } else if (ex instanceof AdminBizException) {//后台业务异常
            bizLogger.error(logInfoBuilder.toString());
            mv.addObject("msg", ex.getMessage());
            //mv = new ModelAndView("redirect:" + DomainConstants.BIZ_EXCEPTION);
            //mv = getAjaxMV( ex.getMessage(),((BizException) ex).getErrCode());
            //mv.addObject("callback", request.getParameter("callback"));

        } else if (ex instanceof ValidateException) {//验证异常
            validateLogger.error(logInfoBuilder.toString());
            String msg = ex.getLocalizedMessage();
          /*  String[] msgArr = msg.split(":");
            if (msgArr.length == 1) {
                //mv = new ModelAndView("redirect:" + DomainConstants.BIZ_EXCEPTION);
                mv.addObject("msg", msgArr[0]);
            }
            if (msgArr.length == 2) {*/
            // ajax错误
            //  if ("ajax".equals(msgArr[0])) {
            mv = getAjaxMV(msg);
            mv.addObject("callback", request.getParameter("callback"));
            // } else {
            //   mv = getRedirectMV(request, msgArr[1], msgArr[0]);
            // }
            // }
        }
       /* else if (ex instanceof SessionTimeOutException)
        {
            //mv = new ModelAndView("redirect:" + DomainConstants.DOMAIN_LOGIN);
            CookieUtil.setCookie(response, ConfigConstants.LOGIN_REDIRECT, getForwardUrl(request));
        }*/
        else {
            runtimeLogger.error(logInfoBuilder.toString());
        }
        //Result result = Result.createError(ex.getMessage());
        //if (isNeedResp) {
        //   respActionResult(response,result);
        //}
        return mv;
    }

    /**
     * 获取请求详情
     *
     * @param request
     * @return
     */
    private String getRequestInfo(HttpServletRequest request) {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("url:").append(request.getRequestURI()).append(ExceptionConstants.LOG_INFO_SPLIT)
                .append("clientInfo:").append(request.getRemoteHost()).append(ExceptionConstants.LOG_INFO_SPLIT)
                .append("serverInfo:").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(ExceptionConstants.LOG_INFO_SPLIT)
                .append("parameter:").append(ExceptionConstants.LOG_INFO_SPLIT).append(getParamterInfo(request));

        return infoBuilder.toString();
    }

    /**
     * 根据reques获取参数详情
     *
     * @param request
     * @return
     */
    private String getParamterInfo(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();

        StringBuilder paramterBuilder = new StringBuilder();
        String temp = "";
        while (names.hasMoreElements()) {
            temp = names.nextElement();
            paramterBuilder.append(temp).append("=").append(request.getParameter(temp)).append("&");
        }

        //去除最后一个字符
        if (paramterBuilder.length() > 0) {
            return paramterBuilder.substring(0, paramterBuilder.length() - 1);
        }

        String paramters = paramterBuilder.toString().equals("") ? "no parameter" : paramterBuilder.toString();

        return paramters;
    }

    /**
     * 获取异常堆栈信息
     *
     * @param e
     * @return
     */
    public static String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));

        return writer.toString();
    }

    private ModelAndView getRedirectMV(HttpServletRequest request, String uri,
                                       String msg) {
        ModelAndView mv = new ModelAndView("");
        //ModelAndView mv = new ModelAndView(DomainUrlUtil.getRedirectUrl(uri));
        //HttpUtil.setParam(request, mv);
        mv.addObject("msg", msg);

        return mv;
    }

    /**
     * 响应结果
     */
    private void respActionResult(HttpServletResponse response, Result result) {
        JSONObject json = JsonUtil.toJSON(result);

        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
            printWriter.flush();
        } catch (IOException e) {
            runtimeLogger.error(getStackTrace(e));
        }
    }

    /**
     * 跳转地址
     *
     * @param request
     * @return
     */
    private String getForwardUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        //url.append(DomainUrlUtil.getUrl(request.getRequestURI()));

        String paramInfo = HttpUtil.getParamStr(request);
        if (!StringUtil.isEmpty(paramInfo)) {
            url.append("?").append(HttpUtil.getParamStr(request));
        }

        return url.toString();
    }

    private ModelAndView getAjaxMV(String msg) {
        ObjectMapper om = new ObjectMapper();
        ModelAndView mv = new ModelAndView("common/talkScript");
        AjaxList ajaxList = new AjaxList();
        ajaxList.setMsg(msg);
        ajaxList.setSuccess(false);

        try {
            mv.addObject("json", om.writeValueAsString(ajaxList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }


    private ModelAndView getAjaxMV(String msg, int errCode) {
        ObjectMapper om = new ObjectMapper();
        ModelAndView mv = new ModelAndView("common/talkScript");
        AjaxList ajaxList = new AjaxList();
        ajaxList.setMsg(msg);
        ajaxList.setSuccess(false);
        ajaxList.setErrCode(errCode);
        try {
            mv.addObject("json", om.writeValueAsString(ajaxList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
}
