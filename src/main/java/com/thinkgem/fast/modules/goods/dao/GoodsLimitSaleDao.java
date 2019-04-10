package com.thinkgem.fast.modules.goods.dao;

import com.thinkgem.fast.common.persistence.CrudDao;
import com.thinkgem.fast.common.persistence.annotation.MyBatisDao;
import com.thinkgem.fast.modules.goods.entity.GoodsLimitSale;

/**
 * 商品限销DAO接口
 * @author shiao
 * @version 2019-03-20
 */
@MyBatisDao
public interface GoodsLimitSaleDao extends CrudDao<GoodsLimitSale> {
	
}