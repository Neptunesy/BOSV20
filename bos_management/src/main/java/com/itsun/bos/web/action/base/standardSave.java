package com.itsun.bos.web.action.base;

import com.itsun.bos.domain.base.Standard;
import com.itsun.bos.service.base.StandardService;
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
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 上午 10:34
 */
@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class standardSave extends ActionSupport implements ModelDriven<Standard> {
    private Standard standard = new Standard();
    @Autowired
    private StandardService standardService;
    @Override
    public Standard getModel() {
        return standard;
    }
    @Action(value = "standard_save",results = {@Result(type = "redirect",location = "/pages/base/standard.html")})
    public String savdStandard(){
        standardService.save(standard);
        return  SUCCESS;
    }

    public String  findData(){

        return SUCCESS;
    }
}
