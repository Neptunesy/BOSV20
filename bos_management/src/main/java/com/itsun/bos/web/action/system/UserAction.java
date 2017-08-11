package com.itsun.bos.web.action.system;

import com.itsun.bos.service.system.UserService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
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

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 19:20
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {


    @Autowired
    private UserService userService;

    @Action(value = "user_login",results = {@Result(type = "redirect" ,location = "index.html"),@Result(name = "login",type = "redirect",location = "login.html")})
    public String login(){
        //先拿到Shiro的用户对象subject
        Subject subject = SecurityUtils.getSubject();

        AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());

        try {
            subject.login(token);
            return SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return LOGIN;
        }


    }
    @Action(value = "userAction_logout",results = {@Result(type = "redirect",location = "/login.html")})
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    @Action(value = "user_list",results = {@Result(type = "json")})
    public String user_list(){

        Pageable pageable = getPageable();
        Page<User> users = userService.findAll(pageable);
        encapsulationObject(users);
        return SUCCESS;
    }

}
