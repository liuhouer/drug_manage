package com.thinkgem.fast.modules.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.fast.common.persistence.Page;
import com.thinkgem.fast.common.service.CrudService;
import com.thinkgem.fast.modules.goods.entity.GoodsLimitSale;
import com.thinkgem.fast.modules.goods.dao.GoodsLimitSaleDao;

/**
 * 商品限销Service
 * @author shiao
 * @version 2019-03-20
 */
@Service
@Transactional(readOnly = true)
public class GoodsLimitSaleService extends CrudService<GoodsLimitSaleDao, GoodsLimitSale> {

	public GoodsLimitSale get(String id) {
		return super.get(id);
	}
	
	public List<GoodsLimitSale> findList(GoodsLimitSale goodsLimitSale) {
		return super.findList(goodsLimitSale);
	}
	
	public Page<GoodsLimitSale> findPage(Page<GoodsLimitSale> page, GoodsLimitSale goodsLimitSale) {
		return super.findPage(page, goodsLimitSale);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsLimitSale goodsLimitSale) {
		super.save(goodsLimitSale);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsLimitSale goodsLimitSale) {
		super.delete(goodsLimitSale);
	}
	
}