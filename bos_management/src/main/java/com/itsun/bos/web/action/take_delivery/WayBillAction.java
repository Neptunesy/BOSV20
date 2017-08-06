package com.itsun.bos.web.action.take_delivery;

import com.itsun.bos.service.base.WayBilService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.domain.take_delivery.WayBill;
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
 * Created by SY on 2017-08-04.
 * on BOSV20
 * on 22:46
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class WayBillAction extends BaseAction<WayBill> {

    @Autowired
    private WayBilService wayBilService;

    @Action(value = "waybill_save", results = {@Result(type = "json")})
    public String save() {
        if (model.getOrder() != null && (model.getOrder().getId() == null || model.getOrder().getId() == 0)) {
            model.setOrder(null);
        }
        Map<String, Object> map = new HashMap<>();
        try {
            wayBilService.save(model);
            map.put("success", true);
            map.put("msg", "运单保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "保存运单失败!");
        }
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

    @Action(value = "waybill_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        Pageable pageable = this.getPageable();
        Page<WayBill> wayBills = wayBilService.findAll(pageable);
        this.encapsulationObject(wayBills);
        return SUCCESS;
    }

    @Action(value = "waybil_findByWayBillNum", results = {@Result(type = "json")})
    public String findByWayBillNum() {
        WayBill wayBill = wayBilService.findByWayBillNum(model.getWayBillNum());
        Map<String, Object> map = new HashMap<>();
        if (wayBill == null) {
            map.put("success", false);
        } else {
            map.put("success", true);
            map.put("wayBillData", wayBill);
        }
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

}
