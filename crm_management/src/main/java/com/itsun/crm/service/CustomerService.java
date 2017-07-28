package com.itsun.crm.service;

import com.itsun.crm.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by SY on 2017-07-25.
 * on BOSV20
 * on 上午 08:57
 */
public interface CustomerService {
    //查询所有未关联的客户信息
    @Path("/noassocationcustomers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Customer> findNoAssocationCustomers();


    @Path("/hasassocationcustomers/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Customer> findHasAssocationCustomers(
            @PathParam("id") String fixedAreaID
    );

    @Path("/assocationcustomerstofixedarea")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    void updateAssocationCustomers(
            @QueryParam("customerStrId") String customerStrId,
            @QueryParam("fixedAreaID") String fixedAreaID
    );

    @Path("/saveCustomer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    void saveCustomers(Customer model);

    @Path("/activeCustomer/{telephone}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    void activeMail(
            @PathParam("telephone") String telephone);

    @Path("/findCustomerByTelephone/{telephone}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Customer findCustomerByTelephone(
            @PathParam("telephone") String telephone);
}
