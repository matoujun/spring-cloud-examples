package org.matoujun.cloud.common.http;

import org.matoujun.cloud.common.JsonUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author matoujun

 */
public class AjaxResponseUtil {
    public static final int HTTPCODE_SUCCESS = 0;
    public static final int HTTPCODE_FAILED = 201;
    public static final int HTTPCODE_FAILED_MIS = 202;
    public static final int HTTPCODE_NOT_LOGIN = 301;
    public static final int HTTPCODE_SIGNATURE_ERROR = 302;
    public static final int HTTPCODE_NOPERM = 303;
    public static final int HTTPCODE_NOAUTH = 403;
    public static final int HTTPCODE_TOO_MANY_REQUESTS = 429;

    /**
     * jsonp请求 callback参数名
     */
    private static final String CALLBACK_PARAMETER_NAME = "callback";

    private AjaxResponseUtil() {

    }

    public static String getAjaxJson(int status, String message, Object obj) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errno", status);
            resultMap.put("errmsg", message);
            resultMap.put("data", obj);

            return JsonUtil.object2Json(resultMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回值类型都是String的Json字符串
     *
     * @param status
     * @param message
     * @param ret
     * @return
     */
    private static String getAjaxJsonUseStringValue(int status, String message, String logId,
                                                    Object ret) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errno", status);
            resultMap.put("errmsg", message);
            resultMap.put("logId", logId);
            resultMap.put("data", ret);

            return JsonUtil.object2JsonUseStringValue(resultMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为接口请求提供返回值
     * 返回值类型都是String的Json字符串
     */
    public static void responseJsonForInterface(HttpServletResponse response, int status, String
            message, String logId, Object ret) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(getAjaxJsonUseStringValue(status, message, logId, ret));
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回前端字符串
     * 返回类型为String
     */
    public static void responseString(HttpServletResponse response, String message) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(message);
            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回前端 Ajax 请求的结果
     */
    public static void responseJson(HttpServletResponse response, int status, String message,
                                    Object ret) {

        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(getAjaxJson(status, message, ret));
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ajax 跨域jsonp格式
     */
    public static void responseJsonp(HttpServletRequest request, HttpServletResponse response,
                                     int status, String message, Object ret) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(request.getParameter(CALLBACK_PARAMETER_NAME) + "(" +
                    getAjaxJson(status, message, ret) + ");");
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ajax 请求，兼容jsonp（根据request中是否有callback参数来判断是否为jsonp请求）
     */
    public static void responseJsonOrJsonp(HttpServletRequest request, HttpServletResponse
            response, int status, String message, Object ret) {
        String callback = request.getParameter(CALLBACK_PARAMETER_NAME);

        if (StringUtils.isNotBlank(callback)) {
            responseJsonp(request, response, status, message, ret);
        } else {
            responseJson(response, status, message, ret);
        }
    }

    public static void responseJson(HttpServletResponse response, int status) {

        responseJson(response, status, null, (Object) null);
    }

    public static void responseJson(HttpServletResponse response, int status, String message) {

        responseJson(response, status, message, (Object) null);
    }

    public static void responseJson(HttpServletResponse response, int status, Object ret) {

        responseJson(response, status, null, ret);
    }
}
