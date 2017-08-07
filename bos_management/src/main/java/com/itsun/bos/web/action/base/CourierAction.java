package com.itsun.bos.web.action.base;

import com.itsun.domain.base.Courier;
import com.itsun.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 01:43
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier courier = new Courier();

    @Autowired
    private CourierService courierService;

    @Override
    public Courier getModel() {
        return courier;
    }

    /**
     * Save courier string.
     *
     * @return the string
     */
    @Action(value = "courier_save", results = {@Result(type = "redirect", location = "./pages/base/courier.html")})
    public String save_courier() {
        courierService.save(courier);
        return SUCCESS;
    }

    private int page;
    private int rows;

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Sets rows.
     *
     * @param rows the rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    private String deleteid;

    /**
     * Sets deleteid.
     *
     * @param deleteid the deleteid
     */
    public void setDeleteid(String deleteid) {
        this.deleteid = deleteid;
    }

    /**
     * Courier query bypage string.
     *
     * @return the string
     */
    @Action(value = "courier_queryBypage", results = {@Result(type = "json"), @Result(name = "delSuccess", type = "redirect", location = "./pages/base/courier.html")})
    public String courier_queryBypage() {
        if (deleteid == null) {
            Specification<Courier> courierSpecification = new Specification<Courier>() {
                @Override
                public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    List<Predicate> predicateList = new ArrayList<>();

                    if (StringUtils.isNotBlank(courier.getCourierNum())) {
                        Predicate courierNum = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
                        predicateList.add(courierNum);
                    }
                    if (StringUtils.isNotBlank(courier.getCompany())) {
                        Predicate company = cb.like(root.get("company").as(String.class), courier.getCompany() + "%");
                        predicateList.add(company);
                    }
                    if (StringUtils.isNotBlank(courier.getType())) {
                        Predicate type = cb.equal(root.get("type").as(String.class), courier.getType());
                        predicateList.add(type);
                    }
                    Join<Object, Object> standardRoot = root.join("standard", JoinType.INNER);
                    if (courier.getStandard() != null && StringUtils.isBlank(courier.getStandard().getName())) {
                        Predicate predicate = cb.like(standardRoot.get("name").as(String.class), courier.getStandard().getName() + "%");
                        predicateList.add(predicate);
                    }
                    return cb.and(predicateList.toArray(new Predicate[0]));
                }
            };
            Pageable pageable = new PageRequest(page - 1, rows);
            Page<Courier> couriers = courierService.findPageDate(courierSpecification, pageable);
            Map<String, Object> map = new HashMap<>();
            map.put("total", couriers.getTotalElements());
            map.put("rows", couriers.getContent());
            ActionContext.getContext().getValueStack().push(map);
            return SUCCESS;

        } else {
            String[] split = deleteid.split(",");
            courierService.delBetch(split);
            return "delSuccess";
        }
    }

    /**
     * Findnoassociation string.
     *
     * @return the string
     */
    @Action(value = "courier_findnoassociation", results = {@Result(type = "json")})
    public String findnoassociation() {
        List<Courier> couriers = courierService.findNoassociation();
        ActionContext.getContext().getValueStack().push(couriers);
        return SUCCESS;
    }


}
