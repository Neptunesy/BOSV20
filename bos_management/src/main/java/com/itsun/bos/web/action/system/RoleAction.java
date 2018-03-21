package com.itsun.bos.web.action.system;

import com.itsun.bos.service.system.RoleService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.system.Role;
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
 * on 20:39
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

    @Autowired
    private RoleService roleService;

    @Action(value = "role_list",results = {@Result(type = "json")})
    public String roleList(){
        Pageable pageable = getPageable();
        Page<Role> roles = roleService.findAll(pageable);
        encapsulationObject(roles);
        return SUCCESS;
    }
    @Action(value = "role_list_all",results = {@Result(type = "json")})
    public String roleListAll(){
       List<Role> roles = roleService.findAll();
        ActionContext.getContext().getValueStack().push(roles);
        return SUCCESS;
    }

    private String[] permissionIds;
    private String menuIds ;

    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    @Action(value = "role_save",results = {@Result(type = "redirect",location = "/pages/system/role.html")})
    public  String roleSave(){
        roleService.save(model, permissionIds, menuIds);
        return SUCCESS;
    }

}
