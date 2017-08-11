package com.itsun.bos.service.system;

import com.itsun.domain.system.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SY on 2017-08-10.
 * on BOSV20
 * on 18:29
 */
public interface MenuService {
    Page<Menu> findAll(Pageable pageable);

    List<Menu> findAll();

    void save(Menu menu);
}
