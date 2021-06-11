package com.ts.flow.node;


import com.ts.flow.context.DataContext;
import com.ts.flow.node.flow.Flow;


/**
 * @Title: Node.java
 * @Description: 抽象业务节点
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public abstract class BaseNode {
	
	//节点id（全局唯一）
	private String id;
	//节点描述
	private String desc;
	//节点类路径
	private String refClass;
	//引用的子流程
	private Flow refFlow;
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}


	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


	/**
	 * @return the refClass
	 */
	public String getRefClass() {
		return refClass;
	}


	/**
	 * @param refClass the refClass to set
	 */
	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}

	/**
	 * @return the refFlow
	 */
	public Flow getRefFlow() {
		return refFlow;
	}


	/**
	 * @param refFlow the refFlow to set
	 */
	public void setRefFlow(Flow refFlow) {
		this.refFlow = refFlow;
	}


	/** 
	* @methodName  : execute
	* @param       : DataContext
	* @return      : void 
	* @Description : 执行节点
	*/
	public abstract void doNode(DataContext context) throws Exception;
	
	/** 
	* @methodName  : stop
	* @param       : DataContext
	* @return      : void 
	* @Description : 节点出现异常停止
	*/
	public abstract void stopNode(DataContext context);
}

