package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.PromotionResposlity;
import com.itsun.domain.PageBean;
import com.itsun.domain.take_delivery.Promotion;
import com.itsun.bos.service.base.PromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 上午 12:33
 */

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionResposlity promotionResposlity;

    @Override
    public void save(Promotion model) {
        promotionResposlity.save(model);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
        return promotionResposlity.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> findPageData(int page, int rows) {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Promotion> all = promotionResposlity.findAll(pageable);

        PageBean<Promotion> promotionPageBean = new PageBean<>();
        promotionPageBean.setTotalCount(all.getTotalElements());
        promotionPageBean.setPageDate(all.getContent());
        return promotionPageBean;
    }

    @Override
    public Promotion findByid(Integer id) {
        return promotionResposlity.findOne(id);
    }

    @Override
    public void updateStatus(Date now) {
        promotionResposlity.updateStatus(now);
    }
}
