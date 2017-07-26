package com.itsun.bos.service.base;


import com.itsun.bos.domain.base.TakeTime;

import java.util.List;

/**
 * Created by SY on 2017-07-26.
 * on BOSV20
 * on 下午 07:18
 */

public interface TakeTimeService {

    List<TakeTime> findAll();
}
