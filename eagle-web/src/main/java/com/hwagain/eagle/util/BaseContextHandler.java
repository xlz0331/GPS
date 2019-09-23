package com.hwagain.eagle.util;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service("baseContextHandler")
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();

    public BaseContextHandler() {
    }
    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }
    public static void set(String key, Object value) {
        Map<String, Object> map = (Map)threadLocal.get();
        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
        }

        ((Map)map).put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = (Map)threadLocal.get();
        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
        }

        return ((Map)map).get(key);
    }

    public static String getUserID() {
        Object value = get("currentUserId");
        return returnObjectValue(value);
    }

    public static String getUsername() {
        Object value = get("currentUserName");
        return getObjectValue(value);
    }

    public static String getToken() {
        Object value = get("currentUserToken");
        return getObjectValue(value);
    }
    
    public static String getMobile() {
        Object value = get("currentMobile");
        return getObjectValue(value);
    }
    
    public static String getUserType() {
        Object value = get("currentUserType");
        return getObjectValue(value);
    }
    
    public static String getPlateNumber() {
        Object value = get("currentPlateNumber");
        return getObjectValue(value);
    }
    
    public static String getSupplierId() {
        Object value = get("currentSupplierId");
        return getObjectValue(value);
    }

    public static void setToken(String token) {
        set("currentUserToken", token);
    }

    public static void setUsername(String name) {
        set("currentUserName", name);
    }

    public static void setUserID(String userID) {
        set("currentUserId", userID);
    }
    
    public static void setMobile(String mobile) {
        set("currentMobile", mobile);
    }
    
    public static void setUserType(String userType) {
        set("currentUserType", userType);
    }
    
    public static void setPlateNumber(String plateNumber) {
        set("currentPlateNumber", plateNumber);
    }
    
    public static void setSupplierId(Long supplierId){
    	set("currentSupplierId", supplierId);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }
}