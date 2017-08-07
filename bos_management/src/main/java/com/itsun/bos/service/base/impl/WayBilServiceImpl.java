package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.WayBilRepository;
import com.itsun.bos.elesticserach.WayBillIndexRepository;
import com.itsun.bos.service.base.WayBilService;
import com.itsun.domain.take_delivery.WayBill;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SY on 2017-08-04.
 * on BOSV20
 * on 23:15
 */
@Service
@Transactional
public class WayBilServiceImpl implements WayBilService {
    @Autowired
    private WayBilRepository wayBilRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill model) {
        WayBill one = wayBilRepository.findOne(model.getId());
        if (one.getId() == null || one == null) {
            wayBilRepository.save(model);
            wayBillIndexRepository.save(model);

        } else {
            try {
                Integer id = one.getId();
                BeanUtils.copyProperties(one, model);
                one.setId(id);
                wayBillIndexRepository.save(one);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public Page<WayBill> findAll(Pageable pageable) {
        return wayBilRepository.findAll(pageable);
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBilRepository.findByWayBillNum(wayBillNum);
    }
}
