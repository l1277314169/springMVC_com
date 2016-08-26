/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.comm.pagination;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class WebUtils {
    public static String getUrl(HttpServletRequest req, boolean skipPageParam,
                                Map<String, String> parameter) {
        Map skipParam = new HashMap();
        if (skipPageParam) {
            skipParam.put("page", "page");
            skipParam.put("perPageRecord", "perPageRecord");
        }
        return initUrl(req, parameter, skipParam);
    }

    public static String getPageUrl(HttpServletRequest req,
                                    Map<String, String> skipParam) {
        return initUrl(req, null, skipParam);
    }

    public static String getUrl(HttpServletRequest req,
                                Map<String, String> defaultV) {
        return getUrl(req, true, defaultV);
    }

    private static String initUrl(HttpServletRequest req,
                                  Map<String, String> parameter, Map<String, String> skipParam) {
        StringBuffer sb = new StringBuffer();
        Enumeration it = req.getParameterNames();
        int pos = 0;
        Set keys = new HashSet();
        while (it.hasMoreElements()) {
            String key = (String) it.nextElement();
            if ((skipParam != null) && (skipParam.containsKey(key))) {
                continue;
            }
            if ((parameter != null) && (parameter.containsKey(key))) {
                continue;
            }
            if (keys.contains(key)) {
                continue;
            }
            keys.add(key);
            String[] values = req.getParameterValues(key);
            if (!(ArrayUtils.isEmpty(values))) {
                for (String v : values) {
                    if (pos++ > 0) {
                        sb.append("&");
                    }
                    sb.append(key);
                    sb.append("=");
                    sb.append(v);
                }
            }
        }
        if ((parameter != null) && (!(parameter.isEmpty()))) {
            for (String key : parameter.keySet()) {
                if (pos++ > 0) {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append((String) parameter.get(key));
            }
        }
        if (sb.length() > 0) {
            sb.append("&");
        }
        sb.append("page=");
        StringBuffer uri = new StringBuffer();
        uri.append(req.getRequestURI());
        if (sb.length() > 1) {
            uri.append("?");
            uri.append(sb.toString());
        }

        String url = uri.toString().replaceAll("/{1,}", "/");

        return url;
    }

    public static List<RequestKV> getParameterMap(HttpServletRequest req,
                                                  Map<String, String> skipParam) {
        Enumeration it = req.getParameterNames();
        Set keys = new HashSet();
        List list = new ArrayList();
        while (it.hasMoreElements()) {
            String key = (String) it.nextElement();
            if ((skipParam != null) && (skipParam.containsKey(key))) {
                continue;
            }
            if (keys.contains(key)) {
                continue;
            }
            keys.add(key);
            String[] values = req.getParameterValues(key);
            if (!(ArrayUtils.isEmpty(values))) {
                for (String v : values) {
                    RequestKV kv = new RequestKV(key, v);
                    list.add(kv);
                }
            }
        }
        return list;
    }

    public static String getUrl(HttpServletRequest req) {
        return getUrl(req, true, null);
    }

    public static String getUrlByParam(String requestURI,
                                       boolean skipPageParam, Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        int pos = 0;
        for (String key : param.keySet()) {
            if (skipPageParam) {
                if ("page".equals(key))
                    continue;
                if ("perPageRecord".equals(key))
                    continue;
            }
            if (pos++ > 0) {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(param.get(key).toString());
        }
        if (sb.length() > 0) {
            sb.insert(0, "?");
        }
        sb.insert(0, requestURI);
        return sb.toString();
    }

    public static String getUrlByKeyValue(HttpServletRequest req,
                                          String[] fields) {
        if ((fields == null) || (fields.length == 0)) {
            return getUrl(req);
        }

        Map param = new HashMap();
        for (int i = 0; i < fields.length; i += 2)
            try {
                param.put(fields[i], fields[(i + 1)]);
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        return getUrl(req, true, param);
    }

    public static boolean hasUseAjax(HttpServletRequest req) {
        String useAjax = req.getParameter("useAjax");
        return StringUtils.equals("true", useAjax);
    }
}