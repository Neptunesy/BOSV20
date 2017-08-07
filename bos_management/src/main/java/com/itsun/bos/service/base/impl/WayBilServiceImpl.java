package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.WayBilRepository;
import com.itsun.bos.elesticserach.WayBillIndexRepository;
import com.itsun.bos.service.base.WayBilService;
import com.itsun.domain.take_delivery.WayBill;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.elasticsearch.index.query.*;
/**
 * Created by SY on 2017-08-04.
 * on BOSV20
 * on 23:15
 */
@Service
@Transactional
public class WayBilServiceImpl implements WayBilService {
    @Autowired
    private WayBilRepository wayBilRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill model) {
        WayBill one = wayBilRepository.findByWayBillNum(model.getWayBillNum());
        if ( one == null||one.getId() == null ) {
            wayBilRepository.save(model);
            wayBillIndexRepository.save(model);

        } else {
            try {
                Integer id = one.getId();
                BeanUtils.copyProperties(one, model);
                one.setId(id);
                wayBillIndexRepository.save(one);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public Page<WayBill> findAll(WayBill wayBill, Pageable pageable) {
        if (StringUtils.isBlank(wayBill.getWayBillNum()) && StringUtils.isBlank(wayBill.getSendAddress())
                && StringUtils.isBlank(wayBill.getRecAddress())
                &&StringUtils.isBlank(wayBill.getSendProNum())
                && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)
                ) {
            return wayBilRepository.findAll(pageable);
        }
        else {
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            if (StringUtils.isNotBlank(wayBill.getWayBillNum())){
                QueryBuilder temp = new TermQueryBuilder("wayBillNum",wayBill.getWayBillNum());
                boolQueryBuilder.must(temp);
            }
            if (StringUtils.isNotBlank(wayBill.getSendAddress())){
                QueryBuilder temp = new WildcardQueryBuilder("sendAddress","*"+wayBill.getSendAddress()+"*");
                boolQueryBuilder.must(temp);
            }
            if (StringUtils.isNotBlank(wayBill.getRecAddress())){
                QueryBuilder temp = new WildcardQueryBuilder("recAddress","*"+wayBill.getRecAddress()+"*");
                boolQueryBuilder.must(temp);
            }
            if (StringUtils.isNotBlank(wayBill.getSendProNum())){
                QueryBuilder tmp = new TermQueryBuilder("sendProNum",wayBill.getSendProNum());
                boolQueryBuilder.must(tmp);
            }
            if (wayBill.getSignStatus()!=0&&wayBill.getSignStatus()!=null){
                QueryBuilder tmp = new TermQueryBuilder("signStatus",wayBill.getSignStatus());
                boolQueryBuilder.must(tmp);
            }
            SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
            searchQuery.setPageable(pageable);
            return wayBillIndexRepository.search(searchQuery);

        }
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBilRepository.findByWayBillNum(wayBillNum);
    }
}
