package com.innov.common.util;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class DisplayTagUtil {
    public static String ORDER_DESC = "1";
    public static String ORDER_ASC = "2";
    public static Integer DEFAULT_PAGE_SIZE = 10;
    
    public static Integer getListStart(String id, HttpServletRequest request, Integer pageSize) {
        if (id != null && request != null) {
            if (pageSize == null) {
                pageSize = DEFAULT_PAGE_SIZE;
            }
            
            String page = getParamValue(id, TableTagParameters.PARAMETER_PAGE, request);
            
            if (page != null && !page.isEmpty()) {
                return (Integer.parseInt(page) - 1) * pageSize;
            }
        }
        
        return 0;
    }
    
    public static String getListSort(String id, HttpServletRequest request, String[] columns, String defaultSortColumn) {
        if (id != null && request != null) {
            String sort = getParamValue(id, TableTagParameters.PARAMETER_SORT, request);
            
            if (sort != null && !sort.isEmpty()) {
                int sortIndex = Integer.parseInt(sort);
                
                if (sortIndex < columns.length) {
                    return columns[sortIndex];
                }
            }
        }
        
        return defaultSortColumn;
    }
    
    public static String getListSort(String id, HttpServletRequest request) {
        if (id != null && request != null) {
            return getParamValue(id, TableTagParameters.PARAMETER_SORT, request);
        }
        
        return null;
    }
    
    public static Boolean getListDesc(String id, HttpServletRequest request, boolean defaultDesc) {
        if (id != null && request != null) {
            String order = getParamValue(id, TableTagParameters.PARAMETER_ORDER, request);
            
            if (order != null && !order.isEmpty()) {
                if (ORDER_DESC.equals(order)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        
        return defaultDesc;
    }
    
    public static String getParamValue(String id, String paramName, HttpServletRequest request) {
        String[] values = getParamValues(id, paramName, request);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }
    
    public static String[] getParamValues(String id, String paramName, HttpServletRequest request) {
        String param = getEncodedParamName(id, paramName);
        return request.getParameterValues(param);
    }
    
    public static String getEncodedParamName(String id, String paramName) {
        return new ParamEncoder(id).encodeParameterName(paramName);
    }
}

