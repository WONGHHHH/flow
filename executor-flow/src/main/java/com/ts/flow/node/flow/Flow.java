package com.ts.flow.node.flow;

import com.ts.flow.node.Node;

/**
 * @Title: Flow.java
 * @Description: 业务流程
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public class Flow {
	
	private String id;
	
	private String name;
	
	private String desc;
	
	private Node startNode;

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
	 * @return the startNode
	 */
	public Node getStartNode() {
		return startNode;
	}

	/**
	 * @param startNode the startNode to set
	 */
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	
}

