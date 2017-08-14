package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.StandardRespository;
import com.itsun.domain.base.Standard;
import com.itsun.bos.service.base.StandardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "standardCache",allEntries = true)
    public void save(Standard standard) {
        standardRepository.save(standard);

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "standardCache",key = "#pageable.pageNumber+'_'+#pageable.pageSize")
    public Page<Standard> findAll(Pageable pageable) {

        return standardRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "standardCache")
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }

}
