package com.itsun.bos.dao.base;

import com.itsun.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 上午 12:21
 */
public interface PromotionResposlity extends JpaRepository<Promotion, Integer> {
}
