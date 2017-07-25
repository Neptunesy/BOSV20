package com.itsun.crm.dao;

import com.itsun.crm.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * Created by SY on 2017-07-25.
 * on BOSV20
 * on 上午 10:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setAddress("西安市高新区丈八" + i + "路");
            customer.setBirthday(new Date());
            customer.setUsername("张三");
            customer.setSex(1);
            customerRepository.save(customer);
        }

    }

}