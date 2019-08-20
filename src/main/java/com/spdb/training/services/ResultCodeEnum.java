package com.spdb.training.services;


public enum ResultCodeEnum {
	
	TRAINPBEMPTY("",""),
	TRAINPBSYSERROR("system_error","系统异常"),
	TRAINPB0000("000000000000","交易成功"),
	TRAINPB0001("TRAINPB000001","用户代码:%s重复"),
	;
	
    private String code;
    private String msg;
    
    ResultCodeEnum(String code, String msg) {
    	this.code = code;
    	this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void init(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public boolean equals(String code) {
		return this.code.equals(code);
	}

    /**  
     * <p>Title: main</p>  
     * <p>Description: 使用示例，如果描述中有多个占位符，可传入多个替换信息</p>  
     * @param args  
     */  
    public static void main(String[] args) {
	}
}