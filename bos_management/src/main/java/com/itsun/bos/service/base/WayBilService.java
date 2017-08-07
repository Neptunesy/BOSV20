package com.itsun.bos.service.base;

import com.itsun.domain.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by SY on 2017-08-04.
 * on BOSV20
 * on 23:14
 */
public interface WayBilService {
    void save(WayBill model);

    Page<WayBill> findAll(WayBill wayBill, Pageable pageable);

    WayBill findByWayBillNum(String wayBillNum);
}
