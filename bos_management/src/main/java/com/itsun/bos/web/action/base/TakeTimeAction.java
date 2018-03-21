package com.itsun.bos.web.action.base;

import com.itsun.domain.base.TakeTime;
import com.itsun.bos.service.base.TakeTimeService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 *
 * @author SY
 * @date 2017-07-26
 * on BOSV20
 * on 下午 07:14
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class TakeTimeAction extends BaseAction<TakeTime> {

    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "taketime_findAll", results = {@Result(type = "json")})
    public String takeTimeFindAll() {
        List<TakeTime> takeTimes = takeTimeService.findAll();
        ActionContext.getContext().getValueStack().push(takeTimes);
        return SUCCESS;
    }
}
