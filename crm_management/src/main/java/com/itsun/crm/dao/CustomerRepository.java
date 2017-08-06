package com.itsun.crm.dao;

import com.itsun.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by SY on 2017-07-25.
 * on BOSV20
 * on 上午 09:37
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    /**
     * Find by fixed area id is null list.
     *
     * @return the list
     */
    List<Customer> findByFixedAreaIdIsNull();

    /**
     * Find by fixed area id list.
     *
     * @param fixedAreaId the fixed area id
     * @return the list
     */
    List<Customer> findByFixedAreaId(String fixedAreaId);

    /**
     * Update fixed area id.
     *
     * @param fixedAreaId the fixed area id
     * @param id          the id
     */
    @Query(value = "update Customer set fixedAreaId = ?1 where id = ?2")
    @Modifying
    void updateFixedAreaId(String fixedAreaId, Integer id);

    /**
     * Clear fixed area id.
     *
     * @param fixedAreaId the fixed area id
     */
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?1")
    @Modifying
    void clearFixedAreaId(String fixedAreaId);

    @Query("update Customer set type  = 1 where telephone=?1")
    @Modifying
    void updateActive(String telephone);

    Customer findByTelephone(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

    @Query("select fixedAreaId from Customer where  address = ?1")
    String findFixedAreaIdByAddress(String address);
}
