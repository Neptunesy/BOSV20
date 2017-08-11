package com.itsun.bos.service.system;

import com.itsun.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by SY on 2017-08-08.
 * on BOSV20
 * on 19:32
 */
public interface UserService {
    User findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
