package com.itsun.bos.mq;

import org.springframework.stereotype.Service;
import yudiUtils.MailUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by SY on 2017-07-29.
 * on BOSV20
 * on 下午 08:28
 */
@Service
public class EmailConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;

        try {
            String email = mapMessage.getString("email");
            String content = mapMessage.getString("content");
            MailUtils.sendMail("速运快递的激活邮件", content, email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败！");
        }
    }
}
