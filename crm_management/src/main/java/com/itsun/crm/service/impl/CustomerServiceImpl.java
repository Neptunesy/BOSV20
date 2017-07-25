package com.itsun.crm.service.impl;

import com.itsun.crm.dao.CustomerRepository;
import com.itsun.crm.domain.Customer;
import com.itsun.crm.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-07-25.
 * on BOSV20
 * on 上午 09:36
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findNoAssocationCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findHasAssocationCustomers(String fixedAreaID) {
        return customerRepository.findByFixedAreaId(fixedAreaID);
    }

    @Override
    public void updateAssocationCustomers(String customerStrId, String fixedAreaID) {
        if (StringUtils.isBlank(customerStrId)) {
            return;
        }
        //解除关联状态
        customerRepository.clearFixedAreaId(fixedAreaID);
        String[] split = customerStrId.split(",");
        for (String s : split) {
            Integer parseInt = Integer.parseInt(s);
            customerRepository.updateFixedAreaId(fixedAreaID, parseInt);
        }

    }

}
