package com.itsun.bos.dao.base;

import com.itsun.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SY on 2017-08-03.
 * on BOSV20
 * on 20:30
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderNum(String orderNum);
}
