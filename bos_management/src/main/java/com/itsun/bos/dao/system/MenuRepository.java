package com.itsun.bos.dao.system;

import com.itsun.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author SY
 * @date 2017-08-10
 * on BOSV20
 * on 18:30
 */
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    @Query("select m from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id = ?1 order by m.priority")
    List<Menu> findByUser(Integer id);
}
