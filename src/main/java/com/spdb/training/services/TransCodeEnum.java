package com.spdb.training.services;
/**
 * ������ö��
 * @author wanglw2
 *
 */
public enum TransCodeEnum {
	InquireStock("OR01","��ѯ���"),
	PruchaseObjects("OR02","������Ʒ");
	/**
	 * ���״���
	 */
	private String transCode;
	/**
	 * ��������
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
