package com.itsun.web.action;

import com.itsun.crm.domain.Customer;
import com.itsun.web.action.comman.BaseAction;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;


/**
 * Created by SY on 2017-07-27.
 * on BOSV20
 * on 下午 07:07
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class SendSmsAction extends BaseAction<Customer> {

    @Action(value = "customer_sendSms")
    public String getCheckCode() throws UnsupportedEncodingException {
        //生成短信验证码
        String randomNumeric = RandomStringUtils.randomNumeric(4);

        //将这个验证码保存到Session中

        ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), randomNumeric);

        String content = "尊敬的用户您好，您本次的验证码" + randomNumeric + ",服务电话：400-800-8820";

        System.out.println(randomNumeric);


        //调用短信发送服务
        //   String result = SmsUtils.sendSmsByHTTP(model.getTelephone(), content);

        String result = "000XXX";
        if (result.startsWith("000")) {
            //发送成功
            return NONE;
        } else {
            throw new RuntimeException("短信发送失败" + result);
        }
    }
}
