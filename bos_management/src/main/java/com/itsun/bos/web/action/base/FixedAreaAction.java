package com.itsun.bos.web.action.base;

import com.itsun.bos.domain.base.FixedArea;
import com.itsun.bos.service.base.FixedAreaService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by SY on 2017-07-23.
 * on BOSV20
 * on 下午 12:09
 */
@ParentPackage("json-default")
@Namespace("/")
@Scope("propertyType")
@Controller
public class FixedAreaAction extends BaseAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;


    @Action(value = "fixedAreaForm_save", results = {@Result(type = "redirect", location = "/pages/base/fixed_area.html")})
    public String saveFixedArea() {
        System.out.println(model.getId());
        fixedAreaService.save(model);

        return SUCCESS;
    }
}
