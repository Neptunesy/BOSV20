package com.itsun.bos.quartz;

import com.itsun.bos.dao.base.WayBilRepository;
import com.itsun.bos.elesticserach.WayBillIndexRepository;
import com.itsun.domain.take_delivery.WayBill;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * @author SY
 * @date 2017-08-13
 * on BOSV20
 * on 20:30
 */
public class SyncWay_BillJob  implements Job{

    @Autowired
    private WayBilRepository wayBilRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<WayBill> all = wayBilRepository.findAll();
        wayBillIndexRepository.save(all);
    }
}
