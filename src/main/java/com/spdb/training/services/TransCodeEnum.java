package com.spdb.training.services;
/**
 * 交易码枚举
 * @author wanglw2
 *
 */
public enum TransCodeEnum {
	InquireStock("OR01","查询库存"),
	PruchaseObjects("OR02","购买商品");
	/**
	 * 交易代码
	 */
	private String transCode;
	/**
	 * 交易名称
	 */
	private String transName;
	TransCodeEnum(String transCode,String transName){
		this.transCode = transCode;
		this.transName = transName;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
}
