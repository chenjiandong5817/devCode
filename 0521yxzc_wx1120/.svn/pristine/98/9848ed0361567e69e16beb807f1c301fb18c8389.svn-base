package com.plugs.base;

import com.base.BaseApi;
import com.exception.BizException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserPassengerService;
import com.util.DateUtil;
import com.plugs.utils.SessionUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import com.util.Validate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Zoro on 2016/9/24.
 */
public class BaseExtendApi extends BaseApi {
    Logger LOG = Logger.getLogger(BaseExtendApi.class);
    @Autowired
    private UserPassengerService userPassengerService;



    protected UserPassengerDto getUserByOpenId(HttpServletRequest request, String openId) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("openId", openId);
        UserPassengerDto passenger = this.userPassengerService.findUser(params);
        if (null == passenger) {
            throw new BizException("未获取到用户信息", ReturnCodeConstants.ERR_2000_TOKEN_ERR);
        }
        if (passenger.getStatus() == 2 || passenger.getStatus() == 3) {
            throw new BizException(passenger.getAbortRemark(), ReturnCodeConstants.ERR_2004_ABORT);
        }
        return passenger;
    }

    protected Integer dealPageNum(Integer pageNum) {
        if (pageNum == null) {
            return 1;
        }
        return pageNum;
    }

    /**
     * getModel缺少double的判断，导致数据未映射
     */
    protected <T> T getModel(HttpServletRequest request, Class<T> model) {
        Field[] fields = this.getAllFields(model);
        T t = null;

        try {
            t = model.newInstance();
        } catch (Exception var17) {
            this.LOG.error("创建模型类实例失败！！！", var17);
            throw new RuntimeException(var17);
        }

        StringBuilder pageParam = new StringBuilder();
        Field[] var8 = fields;
        int var9 = fields.length;

        for (int var10 = 0; var10 < var9; ++var10) {
            Field field = var8[var10];
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
                String value = this.getParameter(request, field.getName());
                if (StringUtil.isEmpty(value) && field.getType() == String.class) {
                    Method method;
                    try {
                        method = model.getMethod("get" + StringUtil.firstCharToUpper(field.getName()), new Class[0]);
                        Object e = method.invoke(t, new Object[0]);
                        if (e != null) {
                            value = e.toString();
                        }
                    } catch (Exception var18) {
                        if (field.getType() != Boolean.class && field.getType() != Boolean.TYPE) {
                            this.LOG.warn(var18.getMessage());
                        } else {
                            try {
                                String e1 = field.getName();
                                method = model.getMethod("get" + StringUtil.firstCharToUpper(e1.startsWith("is") ? e1.replace("is", "") : e1), new Class[0]);
                                Object invokeValue = method.invoke(t, new Object[0]);
                                if (invokeValue != null) {
                                    value = invokeValue.toString();
                                }
                            } catch (Exception var16) {
                                this.LOG.warn(var16.getMessage());
                            }
                        }
                    }
                }

                this.LOG.debug(field.getName() + " --> " + value);
                if (StringUtil.isNotEmpty(value)) {
                    this.invokeMethod(model, t, value, field.getName(), field.getType());
                }
            }
        }

        this.invokeMethod(model, t, pageParam.toString(), "param", String.class);
        return t;
    }

    public Field[] getAllFields(Class clazz) {
        HashMap map = new HashMap();
        Field[] var3 = clazz.getDeclaredFields();
        int var4 = var3.length;

        int var5;
        Field field;
        for (var5 = 0; var5 < var4; ++var5) {
            field = var3[var5];
            map.put(field.getName(), field);
        }

        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            if (clazz == Object.class) {
                break;
            }

            var3 = clazz.getDeclaredFields();
            var4 = var3.length;

            for (var5 = 0; var5 < var4; ++var5) {
                field = var3[var5];
                if (!map.containsKey(field.getName())) {
                    map.put(field.getName(), field);
                }
            }
        }

        return (Field[]) map.values().toArray(new Field[map.size()]);
    }

    private <T> void invokeMethod(Class<T> modelClass, T object, String value, String fieldName, Class fieldType) {
        Method method = null;

        try {
            method = modelClass.getMethod("set" + StringUtil.firstCharToUpper(fieldName), new Class[]{fieldType});
        } catch (NoSuchMethodException var13) {
            if (fieldType != Boolean.class && fieldType != Boolean.TYPE) {
                this.LOG.warn(var13.getMessage());
            } else {
                try {
                    method = modelClass.getMethod("set" + StringUtil.firstCharToUpper(fieldName.startsWith("is") ? fieldName.replace("is", "") : fieldName), new Class[]{fieldType});
                } catch (NoSuchMethodException var11) {
                    this.LOG.warn(var11.getMessage());
                }
            }
        }

        try {
            if (null != method) {
                if (fieldType != Integer.TYPE && fieldType != Integer.class) {
                    if (fieldType == String.class) {
                        method.invoke(object, new Object[]{value});
                    } else if (fieldType == Boolean.class || fieldType == Boolean.TYPE) {
                        boolean e1;
                        try {
                            e1 = Boolean.parseBoolean(value);
                        } catch (Exception var9) {
                            e1 = false;
                        }

                        method.invoke(object, new Object[]{Boolean.valueOf(e1)});
                    } else if (fieldType == Double.class || fieldType == Double.TYPE) {
                        double e;
                        try {
                            e = Double.parseDouble(value);
                        } catch (NumberFormatException var10) {
                            e = 0;
                        }
                        method.invoke(object, new Object[]{Double.valueOf(e)});
                    } else if (fieldType == Float.class || fieldType == Float.TYPE) {
                        float e;
                        try {
                            e = Float.parseFloat(value);
                        } catch (NumberFormatException var10) {
                            e = 0;
                        }
                        method.invoke(object, new Object[]{Float.valueOf(e)});
                    } else if (fieldType == Date.class) {
                        Date e;
                        try {
                            e = DateUtil.getDateFromStr(value, DateUtil.DEFAULT_FORMAT);
                            if (StringUtil.isNull(e))
                                e = DateUtil.getDateFromStr(value, "yyyy-MM-dd HH:mm");
                            if (StringUtil.isNull(e))
                                e = DateUtil.getDateFromStr(value, "yyyy-MM-dd");
                        } catch (NumberFormatException var10) {
                            e = null;
                        }
                        method.invoke(object, new Object[]{e});
                    }
                } else {
                    int e;
                    try {
                        e = Integer.parseInt(value);
                    } catch (NumberFormatException var10) {
                        e = 0;
                    }

                    method.invoke(object, new Object[]{Integer.valueOf(e)});
                }
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }

    }

    protected PageBean getPageBean(HttpServletRequest request) throws Exception {
        String tableParam = getParameter(request, "tableParam");
        int sEcho = 1;// 记录操作的次数  每次加1
        int iDisplayStart = 1;// 起始
        int iDisplayLength = 10;// size
        if (StringUtil.isNotEmpty(tableParam)) {
            JSONArray jsonObj = JSONArray.fromObject(tableParam);
            for (int i = 0; i < jsonObj.size(); i++) {
                JSONObject obj = (JSONObject) jsonObj.get(i);
                if (obj.get("name").equals("sEcho"))
                    sEcho = obj.getInt("value");
                if (obj.get("name").equals("iDisplayStart"))
                    iDisplayStart = obj.getInt("value");
                if (obj.get("name").equals("iDisplayLength"))
                    iDisplayLength = obj.getInt("value");
            }
        }

        int pageNum = iDisplayStart / iDisplayLength + 1;
        PageBean pageBean = new PageBean(pageNum, iDisplayLength, sEcho);
        return pageBean;
    }

    protected Map<String, Object> getPageResult(PageList pageList, String listName) {
        Map<String, Object> result = MapUtil.buildMap();
        result.put(listName, pageList);
        result.put("pageBean", pageList.getPaginator());
        return result;
    }

    public static Map<String, Object> getUserByEnt(HttpServletRequest request)
    {
        return (Map<String, Object>) request.getSession().getAttribute(ConfigConstants.ENT_SESSION_KEY);
    }


}
