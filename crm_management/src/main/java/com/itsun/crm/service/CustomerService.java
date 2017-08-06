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

    //查询该定区绑定的客户
    @Path("/hasassocationcustomers/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Customer> findHasAssocationCustomers(
            @PathParam("id") String fixedAreaID
    );

    //为用户关联定区
    @Path("/assocationcustomerstofixedarea")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    void updateAssocationCustomers(
            @QueryParam("customerStrId") String customerStrId,
            @QueryParam("fixedAreaID") String fixedAreaID
    );

    //保存客户
    @Path("/saveCustomer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    void saveCustomers(Customer model);

    //激活邮箱时根据电话号码进行修改状态
    @Path("/activeCustomer/{telephone}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    void activeMail(
            @PathParam("telephone") String telephone);


    //激活时查询客户激活状态
    @Path("/findCustomerByTelephone/{telephone}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Customer findCustomerByTelephone(
            @PathParam("telephone") String telephone);


    //用户登录时调用该服务
    @Path("customer_login")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Customer loginCustomer(
            @QueryParam("username") String telephone,
            @QueryParam("password") String password
    );

    //根据用户的地址匹配定区
    @Path("/customer/findFixedAreaIdByAddress")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    String findFixedAreaIdByAddress(@QueryParam("address") String address);
}
