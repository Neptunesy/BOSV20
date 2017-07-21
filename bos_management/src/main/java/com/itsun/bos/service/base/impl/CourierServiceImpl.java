package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.CourierDao;
import com.itsun.bos.domain.base.Courier;
import com.itsun.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 06:23
 */
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierDao courierDao;

    @Transactional
    @Override
    public void save(Courier courier) {
        courierDao.save(courier);
    }
}
