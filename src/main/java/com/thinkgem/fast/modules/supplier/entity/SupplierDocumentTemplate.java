package com.thinkgem.fast.modules.supplier.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.fast.common.persistence.DataEntity;

/**
 * 供应商证件模板Entity
 * @author 任硕
 * @version 2018-11-13
 */
public class SupplierDocumentTemplate extends DataEntity<SupplierDocumentTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 证件名称
	private String supplierId;		// 供应商id
	private String certCode;		// 证书编码
	private String issuingOrgan;		// 发证机关
	private Date dateIssue;		// 发证日期
	private Date validityDate;		// 有效期至

	private String imgPath;		// 图片路径

	@Length(min=0, max=128, message="图片路径长度必须介于 0 和 128 之间")
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public SupplierDocumentTemplate() {
		super();
	}

	public SupplierDocumentTemplate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="证件名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="供应商id长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@Length(min=0, max=64, message="证书编码长度必须介于 0 和 64 之间")
	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	
	@Length(min=0, max=128, message="发证机关长度必须介于 0 和 128 之间")
	public String getIssuingOrgan() {
		return issuingOrgan;
	}

	public void setIssuingOrgan(String issuingOrgan) {
		this.issuingOrgan = issuingOrgan;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	
}