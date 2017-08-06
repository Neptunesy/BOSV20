package com.itsun.bos.web.action.take_delivery;

import com.itsun.bos.service.base.OrderService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SY on 2017-08-05.
 * on BOSV20
 * on 00:07
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class OrderAction extends BaseAction<Order> {

    @Autowired
    private OrderService orderService;

    @Action(value = "order_findByOrderNum", results = {@Result(type = "json")})
    public String findByOrderNum() {

        Order order = null;
        try {
            order = orderService.finOrderNum(model.getOrderNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        if (order == null) {
            map.put("success", false);
        } else {
            map.put("success", true);
            map.put("orderData", order);
        }
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }
}
