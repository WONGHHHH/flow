package com.ts.flow.enums;
/**
 * @Title: CodeEnum.java
 * @Description: 业务响应结果枚举
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public enum ResponseEnum {
	SUCCESS("0000", "成功"),
	FAIL("9999", "失败");
	
	private ResponseEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private String code;
	
	private String msg;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

