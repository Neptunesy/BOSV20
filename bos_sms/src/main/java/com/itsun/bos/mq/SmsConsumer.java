package com.itsun.bos.mq;

import org.springframework.stereotype.Service;
import yudiUtils.IndustrySMS;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by SY on 2017-07-29.
 * on BOSV20
 * on 下午 08:21
 */
@Service
public class SmsConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String telephone = mapMessage.getString("telephone");
            String content = mapMessage.getString("msg");
            IndustrySMS.execute(telephone, content);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("短信发送失败");
        }
    }
}
