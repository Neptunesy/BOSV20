package com.itsun.bos.service.system.impl;

import com.itsun.bos.dao.system.MenuRepository;
import com.itsun.bos.dao.system.PermissionRespository;
import com.itsun.bos.dao.system.RoleRespository;
import com.itsun.bos.service.system.RoleService;
import com.itsun.domain.system.Menu;
import com.itsun.domain.system.Permission;
import com.itsun.domain.system.Role;
import com.itsun.domain.system.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 20:40
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PermissionRespository permissionRespository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Role> findByUser(User user) {
        if (user.getUsername().equals("admin")) {
            return roleRespository.findAll();
        } else {
            return roleRespository.findByUser(user.getId());
        }

    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRespository.findAll(pageable);
    }

    @Override
    public void save(Role role, String[] permissionIds, String treeNodes) {
        roleRespository.save(role);
        if (permissionIds != null) {
            for (String permissionId : permissionIds) {
                Permission one = permissionRespository.findOne(Integer.parseInt(permissionId));
                role.getPermissions().add(one);
            }
        }

        if (StringUtils.isNotBlank(treeNodes)){
            String[] split = treeNodes.split(",");
            for (String s : split) {
                Menu one = menuRepository.findOne(Integer.parseInt(s));
                role.getMenus().add(one);
            }
        }

    }

    @Override
    public List<Role> findAll() {
        return roleRespository.findAll();
    }
}
