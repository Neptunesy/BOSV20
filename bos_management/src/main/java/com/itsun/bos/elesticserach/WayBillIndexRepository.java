package com.itsun.bos.elesticserach;

import com.itsun.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author SY
 * @date 2017-08-07
 * on BOSV20
 * on 20:28
 */
public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill,Integer> {
}
