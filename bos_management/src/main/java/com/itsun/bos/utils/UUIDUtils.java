package com.itsun.bos.utils;

import java.util.UUID;

/**
 * Created by SY on 2017-07-29.
 * on BOSV20
 * on 下午 11:29
 */
public class UUIDUtils {
    public static String getFileUUID(String fileName) {
        String substring = fileName.substring(fileName.lastIndexOf("."));
        String path = String.valueOf(UUID.randomUUID()).replace("-", "") + substring;
        return path;
    }
}
