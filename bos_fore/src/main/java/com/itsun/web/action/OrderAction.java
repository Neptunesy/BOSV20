package com.itsun.web.action;

import com.itsun.crm.domain.Customer;
import com.itsun.domain.Area;
import com.itsun.domain.comman.Constant;
import com.itsun.domain.take_delivery.Order;
import com.itsun.web.action.comman.BaseAction;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

/**
 * Created by SY on 2017-08-03.
 * on BOSV20
 * on 20:06
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class OrderAction extends BaseAction<Order> {

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "order_add", results = {@Result(name = "success", type = "redirect", location = "index.html")})
    public String Order_add() {

        Area sendArea = new Area();
        String[] split = sendAreaInfo.split("/");
        sendArea.setProvince(split[0]);
        sendArea.setCity(split[1]);
        sendArea.setDistrict(split[2]);

        Area recArea = new Area();
        String[] rec = recAreaInfo.split("/");
        recArea.setProvince(rec[0]);
        recArea.setCity(rec[1]);
        recArea.setDistrict(rec[2]);

        model.setSendArea(sendArea);
        model.setRecArea(recArea);

        Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");

        model.setCustomer_id(customer.getId());
        WebClient.create(Constant.BOS_MANAGEMENT_URL + "services/orderService/order").type(MediaType.APPLICATION_JSON).post(model);
        return SUCCESS;
    }

}
