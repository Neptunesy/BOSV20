package com.itsun.bos.dao.system;

import com.itsun.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author SY
 * @date 2017-08-08
 * on BOSV20
 * on 19:31
 */
public interface UserRespository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
