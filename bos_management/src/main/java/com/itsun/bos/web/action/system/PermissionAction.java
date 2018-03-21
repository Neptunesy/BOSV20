package com.itsun.bos.web.action.system;

import com.itsun.bos.service.system.PermissionService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.system.Permission;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 *
 * @author SY
 * @date 2017-08-10
 * on BOSV20
 * on 20:20
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission> {

    @Autowired
    private PermissionService permissionService;

    @Action(value = "permission_list",results = {@Result(type = "json")})
    public String list(){
        Pageable pageable = getPageable();
        Page<Permission> permissions = permissionService.findAll(pageable);
        encapsulationObject(permissions);
        return SUCCESS;
    }
    @Action(value = "permission_list_All",results = {@Result(type = "json")})
    public String listAll(){
        List<Permission> permissions = permissionService.findAll();
        ActionContext.getContext().getValueStack().push(permissions);
        return SUCCESS;
    }

    @Action(value = "permission_save",results = {@Result(type = "redirect",location = "/pages/system/permission.html")})
    public String permission_save(){
        permissionService.save(model);
        return SUCCESS;
    }
}
