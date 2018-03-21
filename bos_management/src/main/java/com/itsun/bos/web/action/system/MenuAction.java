package com.itsun.bos.web.action.system;

import com.itsun.bos.service.system.MenuService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.system.Menu;
import com.itsun.domain.system.User;
import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * on 18:26
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {

    @Autowired
    private MenuService menuService;

    @Action(value = "menu_list", results = {@Result(name = "success", type = "json")})
    public String menuList() {
        Pageable pageable = getPageable();
        Page<Menu> menus = menuService.findAll(pageable);
        encapsulationObject(menus);
        return SUCCESS;
    }

    @Action(value = "menu_listAll", results = {@Result(type = "json")})
    public String menuListAll() {
        List<Menu> menus = menuService.findAll();
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }

    @Action(value = "menu_save", results = {@Result(type = "redirect", location = "pages/base/system/menu.html")})
    public String saveMenu() {
        menuService.save(model);
        return SUCCESS;
    }
    @Action(value = "menu_showmenu", results = {@Result(type = "json")})
    public String showMenuList() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menus = menuService.findByUser(user);
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }

}
