package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.TakeTimeRepository;
import com.itsun.bos.domain.TakeTime;
import com.itsun.bos.service.base.TakeTimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-07-26.
 * on BOSV20
 * on 下午 07:19
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTImeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTImeRepository.findAll();
    }
}
