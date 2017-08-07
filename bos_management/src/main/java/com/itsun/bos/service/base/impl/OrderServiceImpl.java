package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.AreaRepository;
import com.itsun.bos.dao.base.FixedAreaRespository;
import com.itsun.bos.dao.base.OrderRepository;
import com.itsun.bos.dao.base.WorkBillRespository;
import com.itsun.bos.service.base.OrderService;
import com.itsun.domain.base.Area;
import com.itsun.domain.base.Courier;
import com.itsun.domain.base.FixedArea;
import com.itsun.domain.base.SubArea;
import com.itsun.domain.comman.Constant;
import com.itsun.domain.take_delivery.Order;
import com.itsun.domain.take_delivery.WorkBill;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.MapMessage;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by SY on 2017-08-03.
 * on BOSV20
 * on 20:29
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private FixedAreaRespository fixedAreaRespository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WorkBillRespository workBillRespository;

    @Qualifier("jmsQueueTemplate")
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void saveOrder(Order order) {
        Area sendArea = order.getSendArea();
        Area sendFixedArea = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
        Area recArea = order.getSendArea();
        Area persistRecArea = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
        order.setSendArea(sendFixedArea);
        order.setRecArea(persistRecArea);
        String fixed = WebClient.create(Constant.CRM_MANAGEMENT_URL + "services/customerService/customer/findFixedAreaIdByAddress?address=" + order.getSendAddress()).type(MediaType.APPLICATION_JSON).get(String.class);
        if (fixed != null) {
            FixedArea fixedAreaRespositoryOne = fixedAreaRespository.findOne(fixed);
            Iterator<Courier> iterator = fixedAreaRespositoryOne.getCouriers().iterator();
            if (iterator.hasNext()) {
                Courier next = iterator.next();
                System.out.println("通过第一种方式自动分单成功!");
                saveOrder(order, next);
                generateWorkBill(order);
                return;
            }
        }


        for (SubArea subArea : persistRecArea.getSubareas()) {
            if (order.getSendAddress().contains(subArea.getAssistKeyWords()) || order.getSendAddress().contains(subArea.getKeyWords())) {
                Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
                if (iterator.hasNext()) {
                    Courier next = iterator.next();
                    if (next != null) {
                        System.out.println("自动分单成功....");
                        saveOrder(order, next);
                        generateWorkBill(order);
                    }
                    return;

                }

            }
        }
        //进入人工分单
        order.setOrderType("2");
        orderRepository.save(order);
    }

    @Override
    public Order finOrderNum(String orderNum) {
        return orderRepository.findByOrderNum(orderNum);
    }


    private void saveOrder(Order order, Courier courier) {
        // 将快递员关联订单上
        order.setCourier(courier);
        // 设置自动分单
        order.setOrderType("1");
        // 保存订单
        orderRepository.save(order);
    }

    private void generateWorkBill(final Order order) {

        // 生成工单
        WorkBill workBill = new WorkBill();
        workBill.setType("新");
        workBill.setPickstate("新单");
        workBill.setBuildtime(new Date());
        workBill.setRemark(order.getRemark());
        final String smsNumber = RandomStringUtils.randomNumeric(4);
        workBill.setSmsNumber(smsNumber); // 短信序号
        workBill.setOrder(order);
        workBill.setCourier(order.getCourier());
        workBillRespository.save(workBill);

        jmsTemplate.send("bos_sms", session -> {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("telephone", order.getCourier().getTelephone());
            mapMessage.setString("msg", "短信序号" + smsNumber + "取件地址" + order.getSendAddress() + ",联系人:" + order.getSendName() + "联系电话" + order.getSendMobile() + "给快递员捎话" + order.getSendMobileMsg());
            return mapMessage;
        });

        workBill.setType("已通知!");
    }

}
