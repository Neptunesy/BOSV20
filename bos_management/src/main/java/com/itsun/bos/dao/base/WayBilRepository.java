package com.itsun.bos.dao.base;

import com.itsun.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SY on 2017-08-04.
 * on BOSV20
 * on 23:15
 */
public interface WayBilRepository extends JpaRepository<WayBill, Integer> {
    WayBill findByWayBillNum(String wayBillNum);
}
