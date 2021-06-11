package com.ts.flow.part;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResponseEnum;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.node.Node;

/**
 * @Title: BasePart.java
 * @Description: 业务逻辑执行模块
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public abstract class BasePart {
	
	/** 
	* @methodName  : preProcess
	* @param       : @return
	* @return      : boolean 
	* @Description : 前置处理默认返回true，业务模块有需求的时候可以覆盖重写做前置处理，false跳过当前节点
	*/
	protected boolean preProcess(DataContext context) {
		return true;
	}
	
	/** 
	* @methodName  : afterProcess
	* @param       : 
	* @return      : void 
	* @Description : 后置处理，业务模块执行完主要业务流程，可以添加后置处理
	*/
	protected void afterProcess(DataContext context) {
	}
	

	/** 
	* @methodName  : doService
	* @param       : @param context
	* @param       : @return
	* @return      : ResultEnum 
	* @Description : 处理业务逻辑
	*/
	public abstract ResultEnum doService(DataContext context);
	
	/** 
	* @methodName  : doPart
	* @param       : @param context
	* @param       : @param node
	* @param       : @throws Exception
	* @return      : void 
	* @Description : 执行逻辑
	*/
	public void doPart(DataContext context, Node node) throws Exception {
		
		ResponseEnum response = ResponseEnum.FAIL;
		if(preProcess(context)) {
			//开始处理业务逻辑
			ResultEnum result = this.doService(context);
			if(result == ResultEnum.FAIL) {
				context.buildParam(response);
				node.stopNode(context);
			}
			
			if(result == ResultEnum.BREAK) {
				//跳过当前逻辑
			}
			if(result == ResultEnum.SUCCESS) {
				afterProcess(context);//执行后置处理
				node.doNode(context);//处理下一个节点
			}
			
		}else {
			//前置处理返回false，跳过当前节点执行下一个节点
			node.doNode(context);
		}
	}
}

