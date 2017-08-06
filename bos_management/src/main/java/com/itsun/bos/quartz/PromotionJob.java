package com.itsun.bos.quartz;

import com.itsun.bos.service.base.PromotionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by SY on 2017-08-01.
 * on BOSV20
 * on 21:42
 */

public class PromotionJob implements Job {

    @Autowired
    private PromotionService promotionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        promotionService.updateStatus(new Date());
        System.out.println("过期活动任务处理");
    }
}
