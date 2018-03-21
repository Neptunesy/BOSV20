package com.itsun.bos.dao.system;

import com.itsun.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 *
 * @author SY
 * @date 2017-08-08
 * on BOSV20
 * on 20:40
 */
public interface RoleRespository extends JpaRepository<Role,Integer> {

    @Query("SELECT  distinct r  from Role r inner join fetch r.users u where u.id=?1")
    List<Role> findByUser(int id);
}
