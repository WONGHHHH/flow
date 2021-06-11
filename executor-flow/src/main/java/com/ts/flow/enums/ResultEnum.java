package com.ts.flow.enums;
/**
 * @Title: ResultEnum.java
 * @Description: 返回结果集
 * @author wh
 * @date 2021年6月2日
 * @version V1.0
 */
public enum ResultEnum {
	
	SUCCESS("01", "SUCCESS"),//成功
	BREAK("02", "BREAK"),//跳出，如果是子流程，不会影响主流程继续进行
	FAIL("03", "FAIL");//失败终止流程
	
	private String code;
	
	private String result;
	
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
	 * @return the result
	 */
	public String getResult() {
		return result;
	}


	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}


	private ResultEnum(String code, String result) {
		this.code = code;
		this.result = result;
	}

	
}

