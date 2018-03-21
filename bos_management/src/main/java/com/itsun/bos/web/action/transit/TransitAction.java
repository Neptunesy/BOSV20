package com.itsun.bos.web.action.transit;

import com.itsun.bos.service.transit.TransitInfoService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.transit.TransitInfo;
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

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author SY
 * @date 2017-08-11
 * on BOSV20
 * on 21:07
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class TransitAction extends BaseAction<TransitInfo> {
    private String wayBillIds;

    public void setWayBillIds(String wayBillIds) {
        this.wayBillIds = wayBillIds;
    }

    @Autowired
    private TransitInfoService transitInfoService;

    @Action(value = "transit_create", results = {@Result(type = "json")})
    public String create() {
        // 调用业务层， 保存transitInfo信息
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            transitInfoService.createTransits(wayBillIds);
            // 成功
            result.put("success", true);
            result.put("msg", "开启中转配送成功");
        } catch (Exception e) {
            e.printStackTrace();
            // 失败
            result.put("success", false);
            result.put("msg", "开启中转配送失败，异常：" + e.getMessage());
        }

        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    @Action(value = "transit_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        Pageable pageable = this.getPageable();
        Page<TransitInfo> transitInfos = transitInfoService.findAll(pageable);
        encapsulationObject(transitInfos);
        return SUCCESS;
    }

}
