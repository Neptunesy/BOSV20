package com.itsun.bos.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xuxiao on 2017/7/22.
 */
public class TypeUtils {

    public static boolean isBaseNumber(Object o) {
        Class c = o.getClass();
        if (c == int.class || c == long.class || c == short.class || c == byte.class || c == double.class || c == float.class) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isReferenceNumber(Object o) {
        if (o instanceof Number) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(Object o) {
        if (isBaseNumber(o) || isReferenceNumber(o)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBoolean(Object o) {
        if (o.getClass() == boolean.class || o.getClass() == Boolean.class) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCharacter(Object o) {
        if (o.getClass() == char.class || o.getClass() == Character.class) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isString(Object o) {
        if (o.getClass() == String.class) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEntity(Object o) {
        if (isNumber(o) || isBoolean(o) || isCharacter(o) || isString(o)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isCollections(Object o) {
        if (o.getClass().isArray() || o instanceof Collection || o instanceof Map) {
            return true;
        } else {
            return false;
        }
    }
}
