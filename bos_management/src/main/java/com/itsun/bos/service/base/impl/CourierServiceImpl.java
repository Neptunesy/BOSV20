package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.CourierRepository;
import com.itsun.bos.service.base.CourierService;

import com.itsun.domain.base.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Created by SY on 2017-07-21.
 * on BOSV20
 * on 下午 06:23
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Transactional
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPageDate(Specification specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void delBetch(String[] ids) {
        for (String idstr : ids) {
            Integer id = Integer.parseInt(idstr);
            courierRepository.updateBetch(id);
        }
    }

    @Override
    public List<Courier> findNoassociation() {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
                return predicate;
            }
        };
        return courierRepository.findAll(specification);
    }


}
