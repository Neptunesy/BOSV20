package com.itsun.web.action;

import com.itsun.crm.domain.Customer;
import com.itsun.utils.MailUtils;
import com.itsun.web.action.comman.BaseAction;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SY on 2017-07-28.
 * on BOSV20
 * on 上午 08:08
 */
@Namespace("/")
@Controller
@Scope("prototype")
@ParentPackage("json-default")
public class RegistAction extends BaseAction<Customer> {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "customer_regist", results = {@Result(type = "redirect", location = "signup-success.html"), @Result(name = "INPUT", type = "redirect", location = "signup.html")})
    public String registAction() {

        String resultCheckCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
        if (checkcode == null || !checkcode.equals(resultCheckCode)) {
            System.out.println("手机验证码错误");
            return INPUT;
        } else {

            WebClient.create("http://localhost:8078/services/customerService/saveCustomer").type(MediaType.APPLICATION_JSON).post(model);
            System.out.println("注册成功");

            String emailCheckCode = RandomStringUtils.randomNumeric(32);

            redisTemplate.opsForValue().set(model.getTelephone(), emailCheckCode, 24, TimeUnit.HOURS);

            String content = " <span>这是一分来自速运快递的激活软件请点击下面的链接进行激活\n" +
                    "<a href=\"" + MailUtils.activeUrl + "?telephone=" + model.getTelephone() + "&activecode=" + emailCheckCode +
                    "\">邮箱绑定地址</a>\n" +
                    "</span>";

            //MailUtils.sendMail("速运快递的激活邮件", content, model.getEmail());

            jmsTemplate.send("bos_email", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("email", model.getEmail());
                    mapMessage.setString("content", content);
                    return mapMessage;
                }
            });

            return SUCCESS;

        }


    }

    //non null key required
    private String activecode;

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    @Action(value = "customer_activeMail")
    public String customer_activeMail() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        //获取邮箱验证码
        String checkEmailcode = redisTemplate.opsForValue().get(model.getTelephone());
        //如果在Redis中获取的CheckEmailCode为空，或者通过属性驱动拿到的值与在Redis中获取到的值不相同
        if (checkEmailcode == null || !activecode.equals(checkEmailcode)) {
            ServletActionContext.getResponse().getWriter().write("验证码错误或者已过期");
        } else {
            //调用WebService来判断用户的激活状态
            Customer customer = WebClient.create("http://localhost:8078/services/customerService/findCustomerByTelephone/" + model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
            if (customer.getType() == null || customer.getType() != 1) {
                //该用户没有绑定 调用Crm_manganent的服务对其状态进行修改
                WebClient.create("http://localhost:8078/services/customerService/activeCustomer/" + model.getTelephone()).type(MediaType.APPLICATION_JSON).put(null);
                ServletActionContext.getResponse().getWriter().write("激活成功！请前去主页登陆!，5秒后跳转到登陆页面");
                ServletActionContext.getResponse().setHeader("Refresh", "5;URL=/bos_fore/login.html");
            } else {
                //用户已经进行过激活了
                ServletActionContext.getResponse().getWriter().write("您已经进行过激活了，即将跳转到个人主页");
                ServletActionContext.getResponse().setHeader("Refresh", "5;URL=/bos_fore/#/myhome");
            }
        }
        //删除客户对应的激活码
        redisTemplate.delete(model.getTelephone());
        return NONE;
    }

}
