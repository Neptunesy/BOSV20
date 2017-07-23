package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.CourierRepository;
import com.itsun.bos.domain.base.Courier;
import com.itsun.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private CourierRepository courierRepository;
    @Transactional
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPageDate(Specification specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void delBetch(String[] ids) {
        for (String idstr : ids) {
            Integer id = Integer.parseInt(idstr);
            courierRepository.updateBetch(id);
        }
    }
}
