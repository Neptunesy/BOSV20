package com.itsun.bos.service.system;

import com.itsun.domain.system.Role;
import com.itsun.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 20:39
 */
public interface RoleService {
    List<Role> findByUser(User user);

    Page<Role> findAll(Pageable pageable);

    void save(Role role, String[] permissionIds, String treeNodes);

    List<Role> findAll();
}
