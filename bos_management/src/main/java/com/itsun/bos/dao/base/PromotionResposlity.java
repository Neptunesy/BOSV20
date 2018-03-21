package com.itsun.bos.dao.base;

import com.itsun.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 *
 * @author SY
 * @date 2017-07-30
 * on BOSV20
 * on 上午 12:21
 */
public interface PromotionResposlity extends JpaRepository<Promotion, Integer> {
    @Query("update Promotion set status = '2' where  endDate < ?1 and  status = '1'")
    @Modifying
    void updateStatus(Date now);
}
