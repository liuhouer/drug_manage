package com.thinkgem.fast.modules.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.fast.common.persistence.CrudDao;
import com.thinkgem.fast.common.persistence.Page;
import com.thinkgem.fast.common.persistence.annotation.MyBatisDao;
import com.thinkgem.fast.modules.customer.entity.Customer;

/**
 * 客户资料DAO接口
 * @author 任硕
 * @version 2018-11-17
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {

    int findCount();

	List<Customer>  preSelectCustomers();

	List<Customer> findCustomerPage(@Param("page")Page<Customer> page, @Param("manageId")String manageId);

}