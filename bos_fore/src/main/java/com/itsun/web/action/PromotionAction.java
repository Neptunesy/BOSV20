package com.itsun.web.action;

import com.itsun.domain.base.PageBean;
import com.itsun.domain.comman.Constant;
import com.itsun.domain.take_delivery.Promotion;
import com.itsun.web.action.comman.BaseAction;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Page query string.
     *
     * @return the string
     */
    @Action(value = "promotion_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        PageBean<Promotion> pageBean = WebClient.create(Constant.BOS_MANAGEMENT_URL + "services/promotionService/pageQuery?page=" + page + "&rows=" + rows).accept(MediaType.APPLICATION_JSON_TYPE).get(PageBean.class);
        ServletActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }

    /**
     * Show detil string.
     *
     * @return the string
     * @throws Exception the exception
     */
    @Action(value = "promotion_showDetail")
    public String showDetil() throws Exception {
        //先构建Id对应的Html文件
        String realPath = ServletActionContext.getServletContext().getRealPath("/freemark");
        File file = new File(realPath + "/" + model.getId() + ".html");
        if (!file.exists()) {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            configuration.setDirectoryForTemplateLoading(new File(ServletActionContext.getServletContext().getRealPath("/WEB-INF/freemarker_templates/")));

            Template template = configuration.getTemplate("promotion_detail.ftl");
            Promotion promotion = WebClient.create(Constant.BOS_MANAGEMENT_URL + "services/promotionService/promotion/" + model.getId()).accept(MediaType.APPLICATION_JSON_TYPE).get(Promotion.class);

            Map<String, Object> map = new HashMap<>();
            map.put("promotion", promotion);

            template.process(map, new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        }
        ServletActionContext.getResponse().setContentType("text/html:charset=utf-8");
        FileUtils.copyFile(file, ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }

}
