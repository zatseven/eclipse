package com.spdb.training.socket.trans.constant;

/** 
* @author 作者: 王腾蛟
* @version time：2019年6月21日 上午11:04:43 
*   类说明:
*/
public enum ResponseCode {

	//成功代码
    success("success", "交易成功"),
    //程序类错误
    /**
     * 系统异常
     */
    sys_error("systemerror", "系统异常"),
    /**
     * 输入姓名错误
     */
    name_error("0009", "系统异常"),
	
	/**
	 * 封装请求报文异常
	 */
    package_req_error("systemerror01", "封装请求报文异常"),
    
	/**
	 * 封装返回报文异常
	 */
    package_rsp_error("systemerror02", "封装返回报文异常");
	
    private String code;
	private String msg;
	
	ResponseCode(String code, String mString) {
		this.code = code;
		this.msg = mString;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
