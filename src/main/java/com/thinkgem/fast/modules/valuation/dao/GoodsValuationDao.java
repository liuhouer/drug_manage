package com.thinkgem.fast.modules.valuation.dao;

import com.thinkgem.fast.common.persistence.CrudDao;
import com.thinkgem.fast.common.persistence.annotation.MyBatisDao;
import com.thinkgem.fast.modules.valuation.entity.GoodsValuation;

/**
 * 商品定价DAO接口
 * @author renshuo
 * @version 2019-01-16
 */
@MyBatisDao
public interface GoodsValuationDao extends CrudDao<GoodsValuation> {
	
}