package com.itsun.bos.service.base;


import com.itsun.bos.domain.PageBean;
import com.itsun.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 上午 12:21
 */
public interface PromotionService {
    void save(Promotion model);

    Page<Promotion> findAll(Pageable pageable);


    @Path("/pageQuery")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    PageBean<Promotion> findPageData(@QueryParam("page") int page,
                                     @QueryParam("rows") int rows);
}
