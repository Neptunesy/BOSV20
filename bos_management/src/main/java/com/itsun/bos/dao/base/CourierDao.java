package com.itsun.bos.dao.base;

import com.itsun.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 06:24
 */
public interface CourierDao extends JpaRepository<Courier, Integer> {

}
