package com.itsun.bos.service.base;

import com.itsun.domain.take_delivery.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by SY on 2017-08-03.
 * on BOSV20
 * on 20:25
 */
public interface OrderService {

    //保存订单的方法
    @Path("/order")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void saveOrder(Order order);

    Order finOrderNum(String orderNum);
}
