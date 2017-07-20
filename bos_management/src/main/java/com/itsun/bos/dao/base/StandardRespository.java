package com.itsun.bos.dao.base;

import com.itsun.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by SY on 2017-07-19.
 * on BOSV20
 * on 下午 08:02
 */
public interface StandardRespository extends JpaRepository<Standard,Integer> {
    List<Standard> findByName(String name);

    @Query(value = "from Standard where name =:n and minWeight=:m",nativeQuery = false)
    List<Standard> queryname(@Param("n") String n, @Param("m") Integer m);

}
