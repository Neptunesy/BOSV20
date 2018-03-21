package com.itsun.bos.dao.base;

import com.itsun.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author SY
 * @date 2017-07-22
 * on BOSV20
 * on 下午 06:09
 */
public interface AreaRepository extends JpaRepository<Area, String>, JpaSpecificationExecutor<Area> {
    Area findByProvinceAndCityAndDistrict(String provice, String city, String district);
}
