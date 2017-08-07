package com.itsun.bos.service.base;


import com.itsun.domain.base.PageBean;
import com.itsun.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

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

    @Path("/promotion/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Promotion findByid(@PathParam("id") Integer id);


    void updateStatus(Date date);
}
