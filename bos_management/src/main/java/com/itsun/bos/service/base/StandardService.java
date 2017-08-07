package com.itsun.bos.service.base;


;

import com.itsun.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 下午 07:15
 */

public interface StandardService {
    void save(Standard standard);

    Page<Standard> findAll(Pageable pageable);

    List<Standard> findAll();
}
