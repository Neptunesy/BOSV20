package com.itsun.bos.web.action.take_delivery;

import com.itsun.domain.take_delivery.Promotion;
import com.itsun.bos.service.base.PromotionService;
import com.itsun.bos.utils.UUIDUtils;
import com.itsun.bos.web.action.base.comman.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

/**
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 上午 12:20
 */
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {
    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    @Autowired
    private PromotionService promotionService;

    @Action(value = "promotionform_save", results = {@Result(type = "redirect", location = "/pages/take_delivery/promotion.html")})
    public String save() throws IOException {
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload/");

        String fileUUID = UUIDUtils.getFileUUID(titleImgFileFileName);

        FileUtils.copyFile(titleImgFile, new File(savePath + "/" + fileUUID));


        model.setTitleImg(ServletActionContext.getRequest().getContextPath()
                + "/upload/" + fileUUID);

        promotionService.save(model);
        return SUCCESS;
    }

    @Action(value = "promotion_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        Pageable pageable = this.getPageable();
        Page<Promotion> promotions = promotionService.findAll(pageable);

        this.encapsulationObject(promotions);
        return SUCCESS;
    }
}
