package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.StandardRespository;

import com.itsun.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 下午 08:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestDemo {
    @Autowired
    private StandardRespository standardRespository;
    @Test
    public void demo1(){
//        List<Standard> byName = standardRespository.findByName("20-30公斤");
//        System.out.println(byName);

        List<Standard> queryname = standardRespository.queryname("20-30公斤", 20);
        System.out.println(queryname);
    }

}
