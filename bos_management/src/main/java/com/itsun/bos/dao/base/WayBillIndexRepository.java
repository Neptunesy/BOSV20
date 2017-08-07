package com.itsun.bos.dao.base;

import com.itsun.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by SY on 2017-08-07.
 * on BOSV20
 * on 18:54
 */
public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill, Integer> {
}
