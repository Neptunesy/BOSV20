package com.itsun.bos.service.base;

import com.itsun.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by SY on 2017-07-22.
 * on BOSV20
 * on 下午 06:08
 */
public interface AreaService {
    void saveBatch(List<Area> areaList);

    Page<Area> findAllSimplePage(Pageable pageable);

    void saveArea(Area area);

    Page<Area> findAllSpecialPage(Specification<Area> areaSpecification, Pageable pageable);

    List<Area> findAll();
}
