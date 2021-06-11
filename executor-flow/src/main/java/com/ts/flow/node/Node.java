package com.ts.flow.node;

import org.springframework.context.ConfigurableApplicationContext;

import com.ts.flow.context.DataContext;
import com.ts.flow.document.FlowResolver;
import com.ts.flow.enums.ResponseEnum;
import com.ts.flow.node.flow.Flow;
import com.ts.flow.part.BasePart;

/**
 * @Title: Node.java
 * @Description: 流程节点
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public class Node extends BaseNode {
	
	//后继节点
	private Node nextNode;
	//节点类型，默认为part
	private String type = "PART";
	//当前几点位置
	private int location;
	
	
	/**
	 * @return the nextNode
	 */
	public Node getNextNode() {
		return nextNode;
	}

	/**
	 * @param nextNode the nextNode to set
	 */
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public void doNode(DataContext context) throws Exception {
		if(nextNode != null && !context.isStop()) {
			execute(nextNode, context);
		}
	}

	/** 
	* @methodName  : execute
	* @param       : @param context
	* @param       : @param nextNode2
	* @return      : void 
	* @Description : 执行流程
	*/
	private void execute(Node nextNode, DataContext context) throws Exception {
		try {
			if("FLOW".equals(nextNode.type)) {
				//当前节点为子流程
				Flow flow = nextNode.getRefFlow();
				Node startNode = flow.getStartNode();
				execute(startNode, context);
				nextNode.doNode(context);
			}
			if("PART".equals(nextNode.type)) {
				//业务part由外部系统依靠Spring注入，所以使用Spring获取对象
				ConfigurableApplicationContext applicationContext = FlowResolver.getApplicationContext();
				BasePart part = (BasePart) applicationContext.getBean(nextNode.getRefClass());
				part.doPart(context,nextNode);
			}
		} catch (Exception e) {
			context.buildParam(ResponseEnum.FAIL);
			throw e;
		}
		
	}

	@Override
	public void stopNode(DataContext context) {
		context.stop(true);
	}

}

