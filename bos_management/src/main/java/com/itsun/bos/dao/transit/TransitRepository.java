package com.itsun.bos.dao.transit;

import com.itsun.domain.transit.TransitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author SY
 * @date 2017-08-11
 * on BOSV20
 * on 21:23
 */
public interface TransitRepository extends JpaRepository<TransitInfo,Integer> {
}
