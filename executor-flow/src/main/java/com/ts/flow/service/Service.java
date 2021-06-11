package com.ts.flow.service;

import java.util.Map;

import com.ts.flow.node.flow.Flow;

/**
 * @Title: Service.java
 * @Description: 业务模块
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class Service {
	
	private String id;
	
	private String name;
	
	private String desc;
	
	private String source;
	
	private Map<String, Flow> flows;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the flows
	 */
	public Map<String, Flow> getFlows() {
		return flows;
	}

	/**
	 * @param flows the flows to set
	 */
	public void setFlows(Map<String, Flow> flows) {
		this.flows = flows;
	}

	
	
}

