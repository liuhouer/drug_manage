package com.thinkgem.fast.modules.goods.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.fast.common.persistence.DataEntity;

/**
 * 商品限销Entity
 * @author shiao
 * @version 2019-03-20
 */
public class GoodsLimitSale extends DataEntity<GoodsLimitSale> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品
	private String limitType;		// 限销类型            1、限制销售            2、允许销售
	private Date startDate;		// 开始限销时间
	private Date endDate;		// 结束限销时间
	
	public GoodsLimitSale() {
		super();
	}

	public GoodsLimitSale(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=1, message="限销类型")
	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}