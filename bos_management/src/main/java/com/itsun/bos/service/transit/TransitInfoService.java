package com.itsun.bos.service.transit;

import com.itsun.domain.transit.TransitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * Created by SY on 2017-08-11.
 * on BOSV20
 * on 21:17
 */

public interface TransitInfoService {
    void createTransits(String wayBillIds);

    Page<TransitInfo> findAll(Pageable pageable);
}
