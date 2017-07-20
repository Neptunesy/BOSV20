package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.StandardRespository;
import com.itsun.bos.domain.base.Standard;
import com.itsun.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 下午 07:16
 */
@Service
public class StandardServiceImpl implements StandardService {


    @Autowired
    private StandardRespository standardRepository;
    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);

    }

}
