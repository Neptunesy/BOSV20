package com.itsun.bos.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SY on 2017-07-29.
 * on BOSV20
 * on 下午 11:32
 */
public class UUIDUtilsTest {
    @Test
    public void getFileUUID() throws Exception {
        String fileUUID = UUIDUtils.getFileUUID("a.txt");
        System.out.println(fileUUID);

        System.out.println("342a44266f9b43aea0fd348e0a71e2d7".length());
    }

}