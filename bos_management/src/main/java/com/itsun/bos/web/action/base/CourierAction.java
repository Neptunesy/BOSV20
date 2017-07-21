package com.itsun.bos.web.action.base;

import com.itsun.bos.domain.base.Courier;
import com.itsun.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 01:43
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier courier = new Courier();

    @Autowired
    private CourierService courierService;

    @Override
    public Courier getModel() {
        return courier;
    }

    @Action(value = "courier_save", results = {@Result(type = "redirect", location = "./pages/base/courier.html")})
    public String save_courier() {
        courierService.save(courier);
        return SUCCESS;
    }
}
