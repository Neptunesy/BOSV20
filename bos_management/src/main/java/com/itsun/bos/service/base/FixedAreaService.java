package com.itsun.bos.service.base;

import com.itsun.bos.domain.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by SY on 2017-07-23.
 * on BOSV20
 * on 下午 01:00
 */

public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> findAllByPage(Specification<FixedArea> specification, Pageable pageable);

    void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer taketimeId);
}
