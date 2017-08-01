package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.AreaRepository;
import com.itsun.bos.service.base.AreaService;
import com.itsun.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-07-22.
 * on BOSV20
 * on 下午 06:08
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveBatch(List<Area> areaList) {
        areaRepository.save(areaList);
    }

    @Override
    public Page<Area> findAllSimplePage(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public void saveArea(Area area) {
        areaRepository.save(area);
    }

    @Override
    public Page<Area> findAllSpecialPage(Specification<Area> areaSpecification, Pageable pageable) {
        return areaRepository.findAll(areaSpecification, pageable);
    }

    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
}
