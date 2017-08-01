package com.itsun.bos.service.base;

import com.itsun.domain.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 06:23
 */
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findPageDate(Specification specification, Pageable pageable);

    void delBetch(String[] ids);

    List<Courier> findNoassociation();
}
