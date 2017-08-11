package com.itsun.bos.service.system.impl;

import com.itsun.bos.dao.system.PermissionRespository;
import com.itsun.bos.service.system.PermissionService;
import com.itsun.domain.system.Permission;
import com.itsun.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 20:42
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private PermissionRespository permissionRespository;

    @Override
    public List<Permission> findByUser(User user) {
        if (user.getUsername().equals("admin")){
            return permissionRespository.findAll();
        }else {
            return permissionRespository.findByUser(user.getId());
        }
    }

    @Override
    public Page<Permission> findAll(Pageable pageable) {
        return permissionRespository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
        permissionRespository.save(model);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRespository.findAll();
    }
}
