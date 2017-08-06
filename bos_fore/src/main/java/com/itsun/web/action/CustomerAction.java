package com.itsun.web.action;

import com.itsun.crm.domain.Customer;
import com.itsun.domain.comman.Constant;
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
 * on 10:19
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class CustomerAction extends BaseAction<Customer> {


    /**
     * Customer login string.
     * 用户在前台登陆时所需要的Action
     *
     * @return bhe string
     */
    @Action(value = "customer_login", results = {@Result(name = "success", type = "redirect", location = "index.html#/myhome"), @Result(name = "input", type = "redirect", location = "login.html")})
    public String customer_login() {
        Customer customer = WebClient.create(Constant.CRM_MANAGEMENT_URL + "services/customerService/customer_login?username=" + model.getTelephone() + "&password=" + model.getPassword()).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(Customer.class);
        if (customer != null) {
            ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
            return SUCCESS;
        } else {
            return INPUT;
        }

    }
}
