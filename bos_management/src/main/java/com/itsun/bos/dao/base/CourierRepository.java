package com.itsun.bos.dao.base;

import com.itsun.bos.domain.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 06:24
 */

public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

    @Query(value = "update Courier set deltag='1' where id = ?1")
    @Modifying
    void updateBetch(Integer id);
}
