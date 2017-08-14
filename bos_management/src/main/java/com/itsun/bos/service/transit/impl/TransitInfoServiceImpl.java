package com.itsun.bos.service.transit.impl;

import com.itsun.bos.dao.base.WayBilRepository;
import com.itsun.bos.dao.transit.TransitRepository;
import com.itsun.bos.service.transit.TransitInfoService;
import com.itsun.domain.take_delivery.WayBill;
import com.itsun.domain.transit.TransitInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SY on 2017-08-11.
 * on BOSV20
 * on 21:18
 */
@Service
@Transactional
public class TransitInfoServiceImpl implements TransitInfoService {

    @Autowired
    private WayBilRepository wayBilRepository;

    @Autowired
    private TransitRepository transitRepository;

    @Override
    public void createTransits(String wayBillIds) {
        if (StringUtils.isNotBlank(wayBillIds)){
            String[] strings = wayBillIds.split(",");
            for (String wayBill : strings) {
                WayBill wayBilRepositoryOne = wayBilRepository.findOne(Integer.parseInt(wayBill));
                if (wayBilRepositoryOne.getSignStatus() == 0){
                    TransitInfo transitInfo  =  new TransitInfo();
                    transitInfo.setWayBill(wayBilRepositoryOne);
                    transitInfo.setStatus("出入库中转");

                    transitRepository.save(transitInfo);
                    wayBilRepositoryOne.setSignStatus(1);
                }
            }
        }
    }

    @Override
    public Page<TransitInfo> findAll(Pageable pageable) {
        return transitRepository.findAll(pageable);
    }
}
