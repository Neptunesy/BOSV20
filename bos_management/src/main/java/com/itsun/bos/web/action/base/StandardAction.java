package com.itsun.bos.web.action.base;

import com.itsun.bos.service.base.StandardService;
import com.itsun.domain.Standard;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 上午 10:34
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
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


    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value = "pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> standardPage = standardService.findAll(pageable);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", standardPage.getTotalElements());
        map.put("rows", standardPage.getContent());

        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

    @Action(value = "standard_findAll", results = {@Result(type = "json")})
    public String standard_findAll() {
        List<Standard> standardList = standardService.findAll();
        ActionContext.getContext().getValueStack().push(standardList);
        return SUCCESS;
    }


}
