package com.itsun.bos.service.system.impl;

import com.itsun.bos.dao.system.RoleRespository;
import com.itsun.bos.dao.system.UserRespository;
import com.itsun.bos.service.system.UserService;
import com.itsun.domain.system.Role;
import com.itsun.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 19:32
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RoleRespository roleRespository;

    @Override
    public User findByUsername(String username) {
        return userRespository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRespository.findAll(pageable);
    }

    @Override
    public void save(User model, String[] roleIds) {
        userRespository.save(model);
        if (roleIds!=null){
            for (String roleId : roleIds) {
                Role one = roleRespository.findOne(Integer.parseInt(roleId));
                model.getRoles().add(one);
            }
        }
    }
}
