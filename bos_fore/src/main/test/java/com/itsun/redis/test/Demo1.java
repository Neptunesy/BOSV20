package com.itsun.redis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by SY on 2017-07-28.
 * on BOSV20
 * on 上午 10:12
 */
public class Demo1 {
    @Test
    public void test1() {
        Jedis jedis = new Jedis("localhost");

        jedis.setex("username", 5, "李四");


    }
}
