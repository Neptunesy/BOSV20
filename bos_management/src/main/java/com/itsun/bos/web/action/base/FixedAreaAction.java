package com.itsun.bos.web.action.base;

import com.itsun.domain.FixedArea;
import com.itsun.bos.service.base.FixedAreaService;
import com.itsun.bos.web.action.base.comman.BaseAction;
import com.itsun.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

import static org.apache.cxf.jaxrs.client.WebClient.create;

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

    @Action(value = "queryBypage_fixed_area", results = {@Result(type = "json")})
    public String queryBypage_fixed_area() {
        Specification<FixedArea> specification = this.getSpecification(model);
        Pageable pageable = this.getPageable();
        Page<FixedArea> fixedAreas = fixedAreaService.findAllByPage(specification, pageable);
        this.encapsulationObject(fixedAreas);
        return SUCCESS;
    }


    @Action(value = "fixedArea_findNoAssocationcustomers", results = {@Result(type = "json")})
    public String fixedArea_findNoAssocationcustomers() {
        Collection<? extends Customer> collection = create("http://localhost:8080/crm_management/services/customerService/noassocationcustomers").accept(MediaType.APPLICATION_JSON_TYPE).getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);
        return SUCCESS;
    }

    @Action(value = "fixedArea_findHasAssocationcustomers", results = {@Result(type = "json")})
    public String fixedArea_findHasAssocationcustomers() {
        if (model.getId() != null) {
            System.out.println(model.getId());
            Collection<? extends Customer> collection = create("http://localhost:8080/crm_management/services/customerService/hasassocationcustomers/" + model.getId()).accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
            ActionContext.getContext().getValueStack().push(collection);
            return SUCCESS;
        }
        return NONE;
    }


    private String[] customerIds;

    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "decidedzone_assigncustomerstodecidedzone", results = {@Result(type = "redirect", location = "./pages/base/fixed_area.html")})
    public String decidedzone_assigncustomerstodecidedzone() {
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient.create("http://localhost:8080/crm_management/services/customerSer" +
                "vice/assocationcustomerstofixedarea?customerStrId=" + customerIdStr + "&fixedAreaID=" + model.getId()).put(null);
        return SUCCESS;
    }

    private Integer courierId;
    private Integer taketimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTaketimeId(Integer taketimeId) {
        this.taketimeId = taketimeId;
    }

    @Action(value = "fixedArea_associationCourierToFixedArea", results = {@Result(type = "redirect", location = "/pages/base/fixed_area.html")})
    public String fixedArea_associationCourierToFixedArea() {

        fixedAreaService.associationCourierToFixedArea(model, courierId, taketimeId);

        return SUCCESS;
    }
}

