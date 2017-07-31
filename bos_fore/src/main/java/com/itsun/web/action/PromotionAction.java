package com.itsun.web.action;

import com.itsun.domain.PageBean;
import com.itsun.domain.take_delivery.Promotion;
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
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 19:57
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {

    @Action(value = "promotion_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        PageBean<Promotion> pageBean = WebClient.create("http://localhost:8077/bos_mangement/services/promotionService/pageQuery?page=" + page + "&rows=" + rows).accept(MediaType.APPLICATION_JSON_TYPE).get(PageBean.class);
        ServletActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }
}
