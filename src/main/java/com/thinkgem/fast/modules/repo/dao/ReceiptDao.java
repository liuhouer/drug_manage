package com.thinkgem.fast.modules.repo.dao;

import com.thinkgem.fast.common.persistence.CrudDao;
import com.thinkgem.fast.common.persistence.annotation.MyBatisDao;
import com.thinkgem.fast.modules.repo.entity.Receipt;

/**
 * 收货单DAO接口
 * @author shiao
 * @version 2019-03-10
 */
@MyBatisDao
public interface ReceiptDao extends CrudDao<Receipt> {
	
}