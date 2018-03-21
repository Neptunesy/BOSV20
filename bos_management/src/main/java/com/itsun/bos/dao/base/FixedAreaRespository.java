package com.itsun.bos.dao.base;

import com.itsun.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author SY
 * @date 2017-07-23
 * on BOSV20
 * on 下午 01:01
 */
public interface FixedAreaRespository extends JpaRepository<FixedArea, String>, JpaSpecificationExecutor<FixedArea> {
}
