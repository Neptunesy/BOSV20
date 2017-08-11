package com.itsun.bos.dao.system;

import com.itsun.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 20:41
 */
public interface PermissionRespository extends JpaRepository<Permission,Integer> {
    @Query("SELECT distinct p from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id = ?1")
    List<Permission> findByUser(Integer id);
}
