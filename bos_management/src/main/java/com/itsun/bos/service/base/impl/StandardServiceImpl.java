package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.StandardRespository;
import com.itsun.domain.Standard;
import com.itsun.bos.service.base.StandardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 下午 07:16
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {


    @Autowired
    private StandardRespository standardRepository;
    @Override
    @Transactional
    public void save(Standard standard) {
        standardRepository.save(standard);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Standard> findAll(Pageable pageable) {

        return standardRepository.findAll(pageable);
    }

    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }

}
