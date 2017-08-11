package com.itsun.bos.service.system;

import com.itsun.domain.system.Permission;
import com.itsun.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 20:42
 */
public interface PermissionService {
    List<Permission> findByUser(User user);

    Page<Permission> findAll(Pageable pageable);

    void save(Permission model);

    List<Permission> findAll();
}
