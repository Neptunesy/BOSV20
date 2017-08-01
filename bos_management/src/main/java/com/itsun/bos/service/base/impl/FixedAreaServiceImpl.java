package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.CourierRepository;
import com.itsun.bos.dao.base.FixedAreaRespository;
import com.itsun.bos.dao.base.TakeTimeRepository;
import com.itsun.domain.Courier;
import com.itsun.domain.FixedArea;
import com.itsun.domain.TakeTime;
import com.itsun.bos.service.base.FixedAreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SY on 2017-07-23.
 * on BOSV20
 * on 下午 01:00
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRespository fixedAreaRespository;


    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Override
    public void save(FixedArea model) {
        fixedAreaRespository.save(model);
    }

    @Override
    public Page<FixedArea> findAllByPage(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRespository.findAll(specification, pageable);
    }

    @Override
    public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer taketimeId) {
        FixedArea fixedAreaRespositoryOne = fixedAreaRespository.findOne(model.getId());
        Courier courierRepositoryOne = courierRepository.findOne(courierId);
        TakeTime takeTimeRepositoryOne = takeTimeRepository.findOne(taketimeId);

        //先将快递员和地区进行关联，由于都是持久态的对象所以不用发送Update语句
        fixedAreaRespositoryOne.getCouriers().add(courierRepositoryOne);

        courierRepositoryOne.setTakeTime(takeTimeRepositoryOne);

    }
}
